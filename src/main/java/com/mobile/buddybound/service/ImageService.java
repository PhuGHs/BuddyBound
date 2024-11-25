package com.mobile.buddybound.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {
    String uploadImage(MultipartFile file) throws IOException;
}
