package com.mobile.buddybound.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ContentTypeValidator implements ConstraintValidator<ContentType, List<MultipartFile>> {
    List<String> contentTypes;

    @Override
    public void initialize(ContentType constraintAnnotation) {
        if (constraintAnnotation.value().length > 0) {
            this.contentTypes = Arrays.asList(constraintAnnotation.value());
        }
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFiles.stream()
                .allMatch(file -> {
                    log.debug("{} - {}", file.getOriginalFilename(), file.getContentType());
                    return !CollectionUtils.isEmpty(contentTypes) && contentTypes.contains(file.getContentType());
                });
    }
}
