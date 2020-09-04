package guru.springframework.services;

import guru.springframework.exception.NotFoundException;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile image) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("Recipe not found"));

        try {
            Byte[] imageToSave = ArrayUtils.toObject(image.getBytes());
            recipe.setImage(imageToSave);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Image for recipe with id:{} could not be saved!", id, e);
        }
    }
}
