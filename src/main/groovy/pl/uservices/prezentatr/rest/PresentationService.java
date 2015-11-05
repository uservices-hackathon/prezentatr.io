package pl.uservices.prezentatr.rest;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import pl.uservices.prezentatr.dto.BottlePackage;
import pl.uservices.prezentatr.dto.WortPackage;

/**
 * Created by i303811 on 05/11/15.
 */
@RestController
public class PresentationService
{

	public static final Random RANDOM = new Random();

	private AtomicInteger dojrzewatrCount = new AtomicInteger(0);
	private AtomicInteger bottlesCount = new AtomicInteger(0);

	@RequestMapping(method = RequestMethod.POST, value = "/present/order")
	public Ingredients order()
	{


		int malt = drawInt();
		int water = drawInt();
		int hop = drawInt();
		int yiest = drawInt();


		final Ingredients ingredients = new Ingredients();
//        ingredients.ingredients = new Ingredient[] {new Ingredient(IngredientType.MALT, malt), water, hop, yiest};
		ingredients.ingredients = Lists.newArrayList();
		ingredients.ingredients.add(new Ingredient(IngredientType.MALT, malt));
		ingredients.ingredients.add(new Ingredient(IngredientType.WATER, water));
		ingredients.ingredients.add(new Ingredient(IngredientType.HOP, hop));
		ingredients.ingredients.add(new Ingredient(IngredientType.YIEST, yiest));
		return ingredients;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/present/butelkatr")
	public Integer presentBottles()
	{
		return bottlesCount.get();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/present/dojrzewatr")
	public Integer presentDojrzewatr()
	{
		return dojrzewatrCount.get();
	}


	@RequestMapping(method = RequestMethod.POST, value = "/bottle", consumes = {"application/prezentator.v1+json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void provideBottles(final @RequestBody BottlePackage bottlePackage)
	{
		bottlesCount.addAndGet(bottlePackage.getQuantity());
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/wort", consumes = {"application/prezentator.v1+json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void provideWort(final @RequestBody WortPackage wortPackage)
	{
		dojrzewatrCount.addAndGet(wortPackage.getWarehouseState());
	}

	public class Ingredients
	{

		public Collection<Ingredient> ingredients;
	}

	public class Ingredient
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

	public enum IngredientType
	{
		WATER, MALT, HOP, YIEST
	}

	private int drawInt()
	{

		return Math.abs(RANDOM.nextInt());
	}
}
