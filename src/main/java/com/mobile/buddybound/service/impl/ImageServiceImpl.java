package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.model.constants.ImageDirectory;
import com.mobile.buddybound.service.ImageService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService  {
    private final ImageKit imageKit;

    private final static String baseFolder = "/uploads/buddy_bound";

    @Override
    @Async
    public CompletableFuture<String> uploadImage(MultipartFile file, String folder) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

        FileCreateRequest fileCreateRequest = new FileCreateRequest(
                base64Image,
                file.getOriginalFilename()
        );

        fileCreateRequest.setUseUniqueFileName(true);
        fileCreateRequest.setTags(Arrays.asList(new String[]{"BuddyBound"}));
        fileCreateRequest.setFolder(baseFolder.concat(folder));

        try {
            Result result = imageKit.upload(fileCreateRequest);
            return CompletableFuture.completedFuture(result.getUrl());
        } catch (Exception ex) {
            return CompletableFuture.failedFuture(new RuntimeException("Failed to upload image: " + ex.getMessage()));
        }
    }
}
