package com.mobile.buddybound.config;

import java.security.SecureRandom;

public class OtpGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateOtp(int length) {
        StringBuilder sb = new StringBuilder();
        String numbers = "0123456789";

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(numbers.length());
            sb.append(numbers.charAt(index));
        }

        return sb.toString();
    }
}
