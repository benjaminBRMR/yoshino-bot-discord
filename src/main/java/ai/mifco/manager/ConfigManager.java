package ai.mifco.manager;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class ConfigManager {

    private final Properties configProp = new Properties();

    public ConfigManager() {
        var fileName = "config.properties";
        try (InputStream inputStream = new FileInputStream(fileName)) {
            configProp.load(inputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public String getProperty(@NotNull String key) {
        return configProp.getProperty(key);
    }

    public Set<String> getAllPropertyNames() {
        return configProp.stringPropertyNames();
    }

    public boolean containsKey(@NotNull String key) {
        return configProp.containsKey(key);
    }

    @SneakyThrows
    public void setProperty(@NotNull String key, @NotNull String value) {
        configProp.setProperty(key, value);
        flush();
    }

    private void flush() throws IOException {
        var fileName = "config.properties";
        try (final OutputStream outputstream = new FileOutputStream(fileName)) {
            configProp.store(outputstream, "Yoshino Config - pesoxyz");
        }
    }

}