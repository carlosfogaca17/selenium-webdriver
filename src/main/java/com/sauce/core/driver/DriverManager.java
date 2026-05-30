package com.sauce.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.sauce.core.utils.ConfigManager;

import java.util.HashMap;
import java.util.Map;
import java.time.Duration;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ConfigManager config = ConfigManager.getInstance();
    
    public static synchronized WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }
    
    private static void initializeDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        String env = config.getCurrentEnvironment();
        
        // Ativa headless se for solicitado por propriedade OU se o ambiente for "ci"
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false")) 
                || "ci".equalsIgnoreCase(env);
        
        System.out.println("🚀 Inicializando browser: " + browser + " | Ambiente: " + env + " | Headless: " + headless);
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-features=PasswordLeakDetection");
                options.addArguments("--remote-allow-origins=*"); // Evita problemas de handshake no CI
                
                // PENTE FINO: Desativa frentes do gerenciador e validações de segurança de senha
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);
                prefs.put("smart_bubble.enabled", false);
                options.setExperimentalOption("prefs", prefs);
                
                // Se o modo Headless for ativado (via comando ou por ser ambiente CI)
                if (headless) {
                    if ("ci".equalsIgnoreCase(env)) {
                        System.out.println("🤖 Configurações extras de ambiente CI aplicadas ao Chrome.");
                    }
                    options.addArguments("--headless=new"); // Padrão moderno estável para o Chrome
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--window-size=1920,1080");
                }
                driver.set(new ChromeDriver(options));
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                // Opcional: Adicionar lógica headless para Firefox futuramente se precisar
                driver.set(new FirefoxDriver());
                driver.get().manage().window().maximize();
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                driver.get().manage().window().maximize();
                break;
                
            default:
                throw new IllegalArgumentException("Browser não suportado: " + browser);
        }
        
        // Configurar timeouts
        driver.get().manage().timeouts().implicitlyWait(
            Duration.ofSeconds(config.getImplicitWait()));
        driver.get().manage().timeouts().pageLoadTimeout(
            Duration.ofSeconds(config.getPageLoadTimeout()));
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            System.out.println("🔚 Driver finalizado");
        }
    }
}