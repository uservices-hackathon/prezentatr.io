package pl.uservices.prezentatr.rest;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Random;

/**
 * Created by i303811 on 05/11/15.
 */
@RequestMapping("/present")
@RestController
public class PresentationService {


    public static final Random RANDOM = new Random();

    @RequestMapping(method = RequestMethod.POST, value = "/order")
    public Ingredients order(){


        int malt = drawInt();
        int water= drawInt();
        int hop= drawInt();
        int yiest= drawInt();


        final Ingredients ingredients = new Ingredients();
//        ingredients.ingredients = new Ingredient[] {new Ingredient(IngredientType.MALT, malt), water, hop, yiest};
        ingredients.ingredients = Lists.newArrayList();
        ingredients.ingredients.add(new Ingredient(IngredientType.MALT, malt));
        ingredients.ingredients.add(new Ingredient(IngredientType.WATER, water));
        ingredients.ingredients.add(new Ingredient(IngredientType.HOP, hop));
        ingredients.ingredients.add(new Ingredient(IngredientType.YIEST, yiest));
        return ingredients;

    }

    public class Ingredients {

        public Collection<Ingredient> ingredients;
    }

    public class Ingredient{

        public final IngredientType type;
        public final int quantity;

        public Ingredient(IngredientType type, int quantity) {
            this.type = type;
            this.quantity = quantity;
        }

        public IngredientType getType() {
            return type;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    public enum IngredientType {
        WATER,MALT,HOP,YIEST
    }

    private int drawInt() {

        return Math.abs(RANDOM.nextInt());

    }


}
