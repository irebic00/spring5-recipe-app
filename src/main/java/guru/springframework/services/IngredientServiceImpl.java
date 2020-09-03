package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converter.ModelConverter;
import guru.springframework.exception.NotFoundException;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    private final ModelConverter modelConverter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, ModelConverter modelConverter) {
        this.recipeRepository = recipeRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        RecipeCommand recipeCommand = modelConverter.convertValue(recipe.orElse(null), RecipeCommand.class);
        if (recipeCommand == null) {
            throw new NotFoundException("Recipe not found!");
        }

        Set<IngredientCommand> ingredients = recipeCommand.getIngredients();
        if (ingredients.isEmpty()) {
            throw new NotFoundException("Ingredients not found!");
        }

        List<IngredientCommand> ingredientCommands = ingredients
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .collect(Collectors.toList());
        try {
            return ingredientCommands.get(0);
        } catch (IndexOutOfBoundsException exception){
            throw new NotFoundException("Ingredient not found!");
        }
    }
}
