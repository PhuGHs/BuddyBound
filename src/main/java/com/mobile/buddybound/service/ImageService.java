package com.mobile.buddybound.service;

import com.mobile.buddybound.model.constants.ImageDirectory;
import com.mobile.buddybound.model.entity.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public interface ImageService {
    CompletableFuture<String> uploadImage(MultipartFile file, String folder) throws IOException;
    Image uploadImageAsync(MultipartFile file, String baseUrl);
}
