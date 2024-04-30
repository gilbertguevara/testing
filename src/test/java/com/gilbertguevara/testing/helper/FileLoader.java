package com.gilbertguevara.testing.helper;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.util.ResourceUtils;

public class FileLoader {
    public static String read(String filePath) {
        try {
            var file = ResourceUtils.getFile(filePath);
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
