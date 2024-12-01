package Dmitry.CelestialForge.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final MinioClient minioClient;

    @Autowired
    public FileStorageService(MinioClient minioClient) {
        this.minioClient = minioClient;
        try {
            String bucketName = "test-bucket";
            // Проверяем, существует ли бакет, если нет, то создаем
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                logger.info("Test bucket created successfully.");
            } else {
                logger.info("Test bucket already exists.");
            }
        } catch (MinioException e) {
            logger.error("Error connecting to MinIO: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
        }
    }

    // Загрузка файла в MinIO
    public void uploadFile(String bucketName, String objectName, String filePath) throws MinioException, IOException, InvalidKeyException, NoSuchAlgorithmException {
        // Проверяем существует ли бакет
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            // Если бакет не существует, создаем его
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // Создаем поток для файла
        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath))) {
            // Загружаем файл
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(fileInputStream, fileInputStream.available(), -1)
                    .build()
            );
        }
    }

    // Получение файла из MinIO
    public InputStream getFile(String bucketName, String objectName) throws MinioException, IOException, InvalidKeyException, NoSuchAlgorithmException {
        return minioClient.getObject(
            GetObjectArgs.builder()
            .bucket(bucketName)
            .object(objectName)
            .build()
        );
    }

    // Удаление файла из MinIO
    public void deleteFile(String bucketName, String objectName) throws MinioException, IOException, InvalidKeyException, NoSuchAlgorithmException {
        minioClient.removeObject(
            RemoveObjectArgs.builder()
            .bucket(bucketName)
            .object(objectName)
            .build()
        );
    }

    // Проверка существования файла
    public boolean doesFileExist(String bucketName, String objectName) throws MinioException, IOException, InvalidKeyException, NoSuchAlgorithmException {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return true;
        } catch (ErrorResponseException e) {
            return false;
        }
    }
}
