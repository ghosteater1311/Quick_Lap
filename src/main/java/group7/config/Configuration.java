package group7.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Configuration {
    private final Properties properties;

    public Configuration(Path configFilePath)  {
        properties = new Properties();
        try (InputStream is = Files.newInputStream(configFilePath)) {
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getApiUrl() {
        return properties.getProperty("api.url", "http://localhost:5000/embed");
    }

    public String getApiKey() {
        return properties.getProperty("api.key");
    }

    public String getApiEndpoint() {
        return properties.getProperty("api.endpoint", "https://openrouter.ai/api/v1/chat/completions");
    }
}