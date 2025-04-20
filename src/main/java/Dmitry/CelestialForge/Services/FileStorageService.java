package Dmitry.CelestialForge.Services;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;

@Service
public class FileStorageService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);    

    public static void createBucketIfNotExists(MinioClient minioClient, String bucketName) throws MinioException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException, IOException {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    public String getBaseUrl(String fullUrl) {
        logger.info(fullUrl);
        int queryIndex = fullUrl.indexOf("?");
        if (queryIndex != -1) {
            return fullUrl.substring(0, queryIndex).replace("minio:9000", "localhost:8000/files");
        }
        return fullUrl;
    }
    
    public String uploadFile(MultipartFile file, String entityType, Long entityId) throws MinioException, IOException, InvalidKeyException, NoSuchAlgorithmException {
        logger.info("Starting file upload for entityType: {}, entityId: {}", entityType, entityId);
        
        try {
            createBucketIfNotExists(minioClient, bucketName);            
        } catch (MinioException e) {
            logger.error("Error opening MinIO bucket", e);
            throw e;
        }

        String fileName = entityType + "/" + entityId + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        logger.info("File name: {}, File size: {}", fileName, file.getSize());
        
        try (InputStream fileInputStream = file.getInputStream()) {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(fileInputStream, fileInputStream.available(), -1)
                    .build()
            );
            logger.info("File was put");
            
            return getBaseUrl(
                minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(fileName)
                .build()
            ));

        } catch (Exception e) {
            logger.error("Error uploading file to MinIO", e);
            throw e;
        }        
    }
}
