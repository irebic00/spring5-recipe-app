package guru.springframework.bootstrap;

import guru.springframework.model.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipe> bootstrappedData() {

        List<Recipe> recipes = new ArrayList<>();

        Category mexican = categoryRepository.findByDescription("Mexican").orElse(null);
        Category fastFood = categoryRepository.findByDescription("Fast Food").orElse(null);

        Notes perfectGuacamoleNotes = new Notes();
        perfectGuacamoleNotes.setRecipeNotes("Easy to cook");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setAmount(BigDecimal.valueOf(1));
        ingredient1.setUom(unitOfMeasureRepository.findByUom("Tablespoon").orElse(null));
        ingredient1.setDescription("clean guacamole");

        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.getCategories().add(mexican);
        perfectGuacamole.setCookTime(10);
        perfectGuacamole.setPrepTime(5);
        perfectGuacamole.setDescription("Traditional Mexican dish.");
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        perfectGuacamole.setDirections("Just cook it");
        perfectGuacamole.setServings(2);
        perfectGuacamole.setSource("Coolinarika");
        perfectGuacamole.setUrl("www.coolinarika.hr");
        perfectGuacamole.setNotes(perfectGuacamoleNotes);
        perfectGuacamoleNotes.setRecipe(perfectGuacamole);
        perfectGuacamole.getIngredients().add(ingredient1);
        ingredient1.setRecipe(perfectGuacamole);


        Ingredient ingredient2 = new Ingredient();
        ingredient2.setAmount(BigDecimal.valueOf(1));
        ingredient2.setUom(unitOfMeasureRepository.findByUom("Tablespoon").orElse(null));
        ingredient2.setDescription("clean chicken");

        Notes spicyGrilledChickenTacosNotes = new Notes();
        spicyGrilledChickenTacosNotes.setRecipeNotes("Kind off Easy to cook");
        Recipe spicyGrilledChickenTacos = new Recipe();
        spicyGrilledChickenTacos.getCategories().add(fastFood);
        spicyGrilledChickenTacos.setCookTime(120);
        spicyGrilledChickenTacos.setPrepTime(15);
        spicyGrilledChickenTacos.setDescription("Super hot chick.");
        spicyGrilledChickenTacos.setDifficulty(Difficulty.EASY);
        spicyGrilledChickenTacos.setDirections("Just cook it");
        spicyGrilledChickenTacos.setServings(5);
        spicyGrilledChickenTacos.setSource("Coolinarika");
        spicyGrilledChickenTacos.setUrl("www.coolinarika.hr");
        spicyGrilledChickenTacos.setNotes(spicyGrilledChickenTacosNotes);
        spicyGrilledChickenTacosNotes.setRecipe(spicyGrilledChickenTacos);
        spicyGrilledChickenTacos.getIngredients().add(ingredient2);
        ingredient2.setRecipe(spicyGrilledChickenTacos);

        recipes.add(perfectGuacamole);
        recipes.add(spicyGrilledChickenTacos);
        return recipes;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(bootstrappedData());
    }
}
