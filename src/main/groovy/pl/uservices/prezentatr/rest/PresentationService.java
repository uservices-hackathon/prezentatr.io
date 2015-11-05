package pl.uservices.prezentatr.rest;

import com.ofg.infrastructure.discovery.ServiceAlias;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.uservices.prezentatr.dto.BottlePackage;
import pl.uservices.prezentatr.dto.WortPackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@RestController
public class PresentationService
{


	private AtomicInteger bottlesCount = new AtomicInteger(0);
	private int dojrzewatrCount;
	private int bottlesQueueCount;

	private final ServiceRestClient serviceRestClient;
	private static final Logger LOG = Logger.getLogger(PresentationService.class.getName());

	@Autowired

	public PresentationService(final ServiceRestClient serviceRestClient)
	{
		this.serviceRestClient = serviceRestClient;
	}

    @RequestMapping(method = RequestMethod.POST, value = "/present/order")
    public IngredientsDto order(final @RequestBody Items items) {
        final IngredientsAggregator request = getIngredientsAggregator(items);
        final Ingredients aggregatr = serviceRestClient.forService(new ServiceAlias("aggregatr")).post().onUrl("/order")
                .body(request).withHeaders().contentTypeJson().andExecuteFor().anObject().ofType(Ingredients.class);
        return new IngredientsDto(aggregatr);
    }

    private IngredientsAggregator getIngredientsAggregator(final Items items) {
        final IngredientsAggregator request = new IngredientsAggregator();

        if(items != null && items.items != null)
        {
            for (final String item : items.items) {
                request.ingredients.add(transformYeast(item));
            }
        }
        return request;
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

    public static class Items {

        private Collection<String> items;

        public Collection<String> getItems() {
            return items;
        }

        public void setItems(final Collection<String> items) {
            this.items = items;
        }
    }



    public class IngredientsDto {

        public Collection<Ingredient> ingredients = new ArrayList<>();

        public IngredientsDto(final Ingredients aggregatr) {
            if (aggregatr.stock != null) {
                for (Map.Entry<String, Integer> entry : aggregatr.stock.entrySet()) {
                    ingredients.add(new Ingredient(transformYeast(entry.getKey()), entry.getValue()));
                }
            }
        }


    }

    private IngredientType transformYeast(final String code) {
        IngredientType key = IngredientType.valueOf(code);

        switch (key) {
            case YIEST:
                return IngredientType.YEAST;
            case YEAST:
                return IngredientType.YIEST;
            default:
                return key;
        }
    }

	public static class IngredientsAggregator
	{

		public Collection<IngredientType> ingredients = new ArrayList<>();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/present/butelkatr")
	public Integer presentButelkatr()
	{
		return bottlesQueueCount;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/present/dojrzewatr")
	public Integer presentDojrzewatr()
	{

		return dojrzewatrCount;
	}

    @RequestMapping(method = RequestMethod.GET, value = "/present/bottles")
	public Integer presentBottles()
	{
		return bottlesCount.get();
	}


	@RequestMapping(method = RequestMethod.POST, value = "/bottle", consumes = {"application/prezentator.v1+json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void provideBottles(final @RequestBody BottlePackage bottlePackage)
	{
		LOG.info("Bottles provided, count: " + bottlePackage.getQuantity());
		bottlesCount.addAndGet(bottlePackage.getQuantity());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bottleQueue", consumes = {"application/prezentator.v1+json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void provideBottlesQueue(final @RequestBody BottlePackage bottlePackage) {
        LOG.info("Bottle queue, count: " + bottlePackage.getQuantity());
		bottlesQueueCount = bottlePackage.getQuantity();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/wort", consumes = {"application/prezentator.v1+json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void provideWort(final @RequestBody WortPackage wortPackage)
	{
		LOG.info("Wort queue, count: " + wortPackage.getWarehouseState());
		dojrzewatrCount = wortPackage.getWarehouseState();
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

	public static enum IngredientType
	{
		WATER, MALT, HOP, YIEST, YEAST
	}
}
