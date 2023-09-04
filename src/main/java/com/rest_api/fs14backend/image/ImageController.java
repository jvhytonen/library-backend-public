package com.rest_api.fs14backend.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@CrossOrigin(origins = {"https://stately-starship-e19365.netlify.app/", "http://127.0.0.1:5173/"}, allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    @Autowired
    private S3Client s3Client;

    //Constructor. Name of the bucket comes from environment variables.
    public ImageController(@Value("${BUCKETNAME}") String bucketName) {
        this.bucketName = bucketName;
    }
    private final String bucketName;

    @PostMapping("/upload/")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();

            // This method uploads the image.
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(filename)
                            .contentType(contentType)
                            .build(),
                    RequestBody.fromBytes(file.getBytes()));

            return "Image uploaded successfully!";
        } catch (Exception e) {
            return "Image upload failed: " + e.getMessage();
        }
    }
}