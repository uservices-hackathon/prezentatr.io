package pl.uservices.prezentatr.rest;

import com.google.common.collect.Lists;
import com.ofg.infrastructure.discovery.ServiceAlias;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;


@RequestMapping("/present")
@RestController
public class PresentationService
{


	public static final Random RANDOM = new Random();

	@Autowired
	public PresentationService(final ServiceRestClient serviceRestClient)
	{
		this.serviceRestClient = serviceRestClient;
	}

	private final ServiceRestClient serviceRestClient;

	@RequestMapping(method = RequestMethod.POST, value = "/order")
	public IngredientsDto order()
	{
		final IngredientsAggregator request = new IngredientsAggregator();
		request.ingredients = Lists.newArrayList();
		request.ingredients.add(IngredientType.MALT);
		request.ingredients.add(IngredientType.WATER);
		request.ingredients.add(IngredientType.HOP);
		request.ingredients.add(IngredientType.YEAST);
		final Ingredients aggregatr = serviceRestClient.forService(new ServiceAlias("aggregatr")).post().onUrl("/order")
				.body(request).withHeaders().contentTypeJson().andExecuteFor().anObject().ofType(Ingredients.class);
		return new IngredientsDto(aggregatr);
	}



	public static class Ingredients
	{
		public Map<String, Integer> stock;

		public Map<String, Integer> getStock()
		{
			return stock;
		}

		public void setStock(final Map<String, Integer> stock)
		{
			this.stock = stock;
		}
	}

	public static  class IngredientsDto
	{

		public Collection<Ingredient> ingredients = new ArrayList<>();

		public IngredientsDto(final Ingredients aggregatr)
		{
			if (aggregatr.stock != null)
			{
				for (Map.Entry<String, Integer> entry : aggregatr.stock.entrySet())
				{
					ingredients.add(new Ingredient(transformYeast(entry.getKey()), entry.getValue()));
				}
			}
		}

		private IngredientType transformYeast(final String code)
		{
            IngredientType key = IngredientType.valueOf(code);

			switch (key)
			{
				case YEAST:
					return IngredientType.YIEST;
				default:
					return key;
			}
		}
	}

	public static class IngredientsAggregator
	{

		public Collection<IngredientType> ingredients;
	}

	public static class Ingredient
	{

		public final IngredientType type;
		public final int quantity;

		public Ingredient(IngredientType type, int quantity)
		{
			this.type = type;
			this.quantity = quantity;
		}

		public IngredientType getType()
		{
			return type;
		}

		public int getQuantity()
		{
			return quantity;
		}
	}

	public static enum IngredientType
	{
		WATER, MALT, HOP, YIEST, YEAST
	}
}
