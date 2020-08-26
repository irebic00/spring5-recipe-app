package guru.springframework;

import guru.springframework.model.Category;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Spring5RecipeAppApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Spring5RecipeAppApplication.class, args);

		RecipeRepository recipeRepository = ctx.getBean(RecipeRepository.class);
		CategoryRepository categoryRepository = ctx.getBean(CategoryRepository.class);

		System.out.println(recipeRepository.findById(1L).orElse(new Recipe()).getCategories().toString());
		System.out.println(categoryRepository.findByDescription("Mexican").orElse(new Category()).getRecipes());
	}
}
