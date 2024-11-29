package Dmitry.CelestialForge.Services;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String UPLOAD_DIR = "files"; // Укажите путь для хранения файлов

    public String storeFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IOException("File name is null");
        }

        String extension = FilenameUtils.getExtension(fileName);
        String newFileName = System.currentTimeMillis() + "." + extension;  // Генерация уникального имени для файла
        Path targetPath = Paths.get(UPLOAD_DIR, newFileName);

        FileUtils.copyInputStreamToFile(file.getInputStream(), targetPath.toFile());

        return targetPath.toString(); 
    }
}
