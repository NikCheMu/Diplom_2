package requestModels;

import java.util.ArrayList;

public class CreateOrderModel {

    private ArrayList<String> ingredients;

    public CreateOrderModel(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public CreateOrderModel() {
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
