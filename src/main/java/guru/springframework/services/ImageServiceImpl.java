package guru.springframework.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Override
    public void saveImageFile(Long id, MultipartFile image) {
        try {
            log.info("Recived file: {}", image.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
