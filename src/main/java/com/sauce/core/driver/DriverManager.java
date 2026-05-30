package com.sauce.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.sauce.core.utils.ConfigManager;

import org.openqa.selenium.edge.EdgeDriver;

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
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        
        System.out.println("🚀 Inicializando browser: " + browser + " | Headless: " + headless);
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-features=PasswordLeakDetection");
                
                // 💡 PENTE FINO: Desativa todas as frentes do gerenciador e validações de segurança de senha
                java.util.Map<String, Object> prefs = new java.util.HashMap<>();
                prefs.put("credentials_enable_service", false); // Desativa o serviço de credenciais
                prefs.put("profile.password_manager_enabled", false); // Desativa o gerenciador de senhas
                prefs.put("profile.password_manager_leak_detection", false); // 💥 Força o Leak Detection como falso no perfil
                prefs.put("smart_bubble.enabled", false); // Evita balões de notificação inteligentes do Chrome
                options.setExperimentalOption("prefs", prefs);
                
                if (headless) {
                    options.addArguments("--headless");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--window-size=1920,1080");
                }
                driver.set(new ChromeDriver(options));
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
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
            java.time.Duration.ofSeconds(config.getImplicitWait()));
        driver.get().manage().timeouts().pageLoadTimeout(
            java.time.Duration.ofSeconds(config.getPageLoadTimeout()));
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            System.out.println("🔚 Driver finalizado");
        }
    }
}