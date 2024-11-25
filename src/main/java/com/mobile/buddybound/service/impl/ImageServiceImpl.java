package com.mobile.buddybound.service.impl;

import com.mobile.buddybound.service.ImageService;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService  {
    private final ImageKit imageKit;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

        FileCreateRequest fileCreateRequest = new FileCreateRequest(
                base64Image,
                file.getOriginalFilename()
        );

        fileCreateRequest.setUseUniqueFileName(true);
        fileCreateRequest.setTags(Arrays.asList(new String[]{"BuddyBound"}));
        fileCreateRequest.setFolder("/uploads/buddy_bound");

        try {
            Result result = imageKit.upload(fileCreateRequest);
            return result.getUrl();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to upload image: " + ex.getMessage());
        }
    }
}
