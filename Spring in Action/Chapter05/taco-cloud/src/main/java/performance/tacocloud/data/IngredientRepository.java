package performance.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import performance.tacocloud.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
