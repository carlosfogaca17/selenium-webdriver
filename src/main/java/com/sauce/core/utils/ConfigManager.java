package com.sauce.core.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager instance;
    private Properties properties;
    private String currentEnvironment;
    
    private ConfigManager() {
        loadProperties();
    }
    
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    
    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties não encontrado!");
            }
            properties.load(input);
            
            // Pega o ambiente (prioridade: argumento Maven > arquivo)
            currentEnvironment = System.getProperty("environment", 
                properties.getProperty("environment", "dev"));
                
            System.out.println("🔧 Ambiente carregado: " + currentEnvironment);
                
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar config.properties", e);
        }
    }
    
    // URLs por ambiente
    public String getBaseUrl() {
        String key = currentEnvironment + ".base.url";
        String url = properties.getProperty(key);
        if (url == null) {
            throw new RuntimeException("URL não encontrada para o ambiente: " + currentEnvironment);
        }
        return url;
    }
    
    public String getApiUrl() {
        return properties.getProperty(currentEnvironment + ".api.url");
    }
    
    // Credenciais por ambiente
    public String getUsername() {
        return properties.getProperty(currentEnvironment + ".username");
    }
    
    public String getPassword() {
        return properties.getProperty(currentEnvironment + ".password");
    }
    
    public String getLockedUser() {
        return properties.getProperty(currentEnvironment + ".locked_user");
    }
    
    public String getProblemUser() {
        return properties.getProperty(currentEnvironment + ".problem_user");
    }
    
    // Timeouts
    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }
    
    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "20"));
    }
    
    public int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
    }
    
    // Report
    public boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(properties.getProperty("screenshot.on.failure", "true"));
    }
    
    public String getCurrentEnvironment() {
        return currentEnvironment;
    }
}