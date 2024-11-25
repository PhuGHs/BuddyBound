package com.mobile.buddybound.config;

import io.imagekit.sdk.ImageKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageKitConfig {
    @Value("${imagekit.private-key}")
    private String privateKey;

    @Value("${imagekit.public-key}")
    private String publicKey;

    @Value("${imagekit.url-endpoint}")
    private String urlEndpoint;

    @Bean
    public ImageKit imageKit() {
        ImageKit imageKit = ImageKit.getInstance();
        io.imagekit.sdk.config.Configuration config = new io.imagekit.sdk.config.Configuration(
                publicKey,
                privateKey,
                urlEndpoint
        );
        imageKit.setConfig(config);
        return imageKit;
    }
}
