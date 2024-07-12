package responseModels.getIngredientsPositiveResponse;

public class GetIngredientPositiveResponse {

    private boolean success;

    private Ingredient[] data;

    public GetIngredientPositiveResponse(boolean success, Ingredient[] data) {
        this.success = success;
        this.data = data;
    }

    public GetIngredientPositiveResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Ingredient[] getData() {
        return data;
    }

    public void setData(Ingredient[] data) {
        this.data = data;
    }
}
