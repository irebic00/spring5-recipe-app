package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        Mockito.when(recipeService.findRecipeCommandById(Mockito.anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        //then
        Mockito.verify(recipeService, Mockito.times(1)).findRecipeCommandById(Mockito.anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        Mockito.when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients/2/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));
    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        Mockito.when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(ingredientCommand);
        Mockito.when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients/2/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        //when
        Mockito.when(ingredientService.saveIngredientCommand(Mockito.any())).thenReturn(command);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/ingredients"));

    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        //when
        Mockito.when(recipeService.findRecipeCommandById(Mockito.anyLong())).thenReturn(recipeCommand);
        Mockito.when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomList"));

        Mockito.verify(recipeService, Mockito.times(1)).findRecipeCommandById(Mockito.anyLong());

    }

    @Test
    public void testDeleteIngredient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/recipe/1/ingredients/1/delete"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(ingredientService, Mockito.times(1)).deleteIngredientById(Mockito.anyLong(), Mockito.anyLong());
    }
}
