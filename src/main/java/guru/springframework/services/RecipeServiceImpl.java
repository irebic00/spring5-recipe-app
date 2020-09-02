package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converter.ModelConverter;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final ModelConverter modelConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ModelConverter modelConverter) {
        this.recipeRepository = recipeRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("this is lombok thingies");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return new HashSet<Recipe>(recipes);
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = modelConverter.convertValue(command, Recipe.class);
        return modelConverter.convertValue(recipeRepository.save(detachedRecipe), RecipeCommand.class);
    }

    @Override
    public RecipeCommand findCommandById(Long id) {
        RecipeCommand recipeCommand = modelConverter.convertValue(recipeRepository.findById(id).orElse(null), RecipeCommand.class);
        return recipeCommand;
    }
}
