package guru.springframework.bootstrap;

import guru.springframework.model.Category;
import guru.springframework.model.Ingredient;
import guru.springframework.model.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Component
@Profile({"development", "production"})
public class DataLoaderMySQL implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;

    public DataLoaderMySQL(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                           IngredientRepository ingredientRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (categoryRepository.count() == 0L){
            log.debug("Loading Categories");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0L){
            log.debug("Loading UOMs");
            loadUom();
        }

        createRelationsForIngredientsToUom();
    }

    private void createRelationsForIngredientsToUom() {

        Optional<Ingredient> ingredient1 = StreamSupport.stream(ingredientRepository.findAll().spliterator(), false)
                .filter(ingredient -> ingredient.getDescription().equals("clean guacamole")).findFirst();
        if (ingredient1.isPresent()) {
            ingredient1.get().setUom(unitOfMeasureRepository.findByUom("Tablespoon").orElse(null));
            ingredientRepository.save(ingredient1.get());
        }

        Optional<Ingredient> ingredient2 = StreamSupport.stream(ingredientRepository.findAll().spliterator(), false)
                .filter(ingredient -> ingredient.getDescription().equals("clean chicken")).findFirst();
        if (ingredient2.isPresent()) {
            ingredient2.get().setUom(unitOfMeasureRepository.findByUom("Tablespoon").orElse(null));
            ingredientRepository.save(ingredient2.get());
        }
    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("Mexican");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setDescription("Fast Food");
        categoryRepository.save(cat2);
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setUom("Teaspoon");
        unitOfMeasureRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setUom("Tablespoon");
        unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setUom("Cup");
        unitOfMeasureRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setUom("Pinch");
        unitOfMeasureRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setUom("Ounce");
        unitOfMeasureRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setUom("Each");
        unitOfMeasureRepository.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setUom("Pint");
        unitOfMeasureRepository.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setUom("Dash");
        unitOfMeasureRepository.save(uom8);
    }
}
