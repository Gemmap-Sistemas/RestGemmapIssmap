package br.com.gemmap.RestGemmap.util;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

@Component
public class SqlReader {
    private final ResourceLoader resourceLoader;

    public SqlReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String load(String path) {
        try {
            var resource = resourceLoader.getResource("classpath:" + path);
            return new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar SQL: " + path, e);
        }
    }
}
