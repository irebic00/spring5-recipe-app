package guru.springframework.controllers;

import guru.springframework.exception.NotFoundException;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;

    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));

        return "recipe/imageUploadForm";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public String showUploadForm(@PathVariable Long id, HttpServletResponse response) throws IOException {

        response.setContentType("image/jpeg");
        byte[] image = ArrayUtils.toPrimitive(recipeService.findRecipeCommandById(id).getImage());
        if (image == null) {
            throw new NotFoundException("No image found for recipe with id=" + id);
        }
        InputStream is = new ByteArrayInputStream(image);
        IOUtils.copy(is, response.getOutputStream());

        return "recipe/imageUploadForm";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }
}
