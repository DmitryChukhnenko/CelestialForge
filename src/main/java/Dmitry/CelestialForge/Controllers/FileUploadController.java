package Dmitry.CelestialForge.Controllers;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Dmitry.CelestialForge.Entities.BlogPost;
import Dmitry.CelestialForge.Entities.Milestone;
import Dmitry.CelestialForge.Entities.User;
import Dmitry.CelestialForge.Services.BlogPostService;
import Dmitry.CelestialForge.Services.FileStorageService;
import Dmitry.CelestialForge.Services.MilestoneService;
import Dmitry.CelestialForge.Services.UserService;
import io.minio.errors.MinioException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private MilestoneService milestoneService;

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);   

    @PostMapping("/{entityType}/{entityId}/image")
    public String uploadImage(@PathVariable String entityType, 
                              @PathVariable Long entityId,
                              @RequestParam("image") MultipartFile image,
                              HttpServletRequest request) {
        try {
            // Загружаем изображение в MinIO
            String imageUrl = fileStorageService.uploadFile(image, entityType, entityId);
            logger.info("Получен URL изображения: {}", imageUrl);

            switch (entityType.toLowerCase()) {
                case "user" -> {
                    User user = userService.findById(entityId);
                    if (user != null) {
                        user.setPictureUrl(imageUrl);
                        userService.updateUser(user, new User(entityId, imageUrl)); // Сохраняем обновленного пользователя
                        logger.info("Сохранён пользователь: {}", user.toString());
                    } else {
                        throw new IllegalArgumentException("Пользователь с ID " + entityId + " не найден");
                    }
                }
                case "blog" -> {
                    BlogPost blogPost = blogPostService.findById(entityId);
                    if (blogPost != null) {
                        blogPost.setPictureUrl(imageUrl);
                        blogPostService.addBlogPost(blogPost); // Сохраняем обновленный пост блога
                    } else {
                        throw new IllegalArgumentException("BlogPost с ID " + entityId + " не найден");
                    }
                }
                case "milestone" -> {
                    Milestone milestone = milestoneService.findById(entityId);
                    if (milestone != null) {
                        milestone.setPictureUrl(imageUrl);
                        milestoneService.addMilestone(milestone); // Сохраняем обновленный Milestone
                    } else {
                        throw new IllegalArgumentException("Milestone с ID " + entityId + " не найден");
                    }
                }
                default -> throw new IllegalArgumentException("Сущность с типом " + entityType + " не найдена");
            }

            return "redirect:" + request.getHeader("Referer"); // Перенаправление на предыдущую страницу

        } catch (MinioException | IOException e) {
            logger.error(e.getMessage());
            return "error";
        } catch (IllegalArgumentException | InvalidKeyException | NoSuchAlgorithmException e) {
            return "error"; // Общая ошибка
        }
    }
}


