package com.sauce.steps; // Mantenha o pacote onde ele está agora

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.sauce.core.driver.DriverManager;
import com.sauce.core.utils.ConfigManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Hooks {
    private ConfigManager config;
    
    @Before
    public void setUp() {
        config = ConfigManager.getInstance();
        // Inicializa o driver direto na thread do teste atual
        DriverManager.getDriver();
        System.out.println("🚀 Iniciando teste no ambiente: " + config.getCurrentEnvironment());
    }
    
    @After
    public void tearDown(Scenario scenario) {
        // Buscamos a instância viva e ativa do Driver diretamente do Gerenciador de Threads
        WebDriver activeDriver = DriverManager.getDriver();
        
        System.out.println("🔄 Executando pós-condição (Hooks)... Status da falha: " + scenario.isFailed());

        // Forçamos o print se o cenário falhar, independente das chaves de propriedades, para validar a pasta física
        if (scenario.isFailed() && activeDriver != null) {
            try {
                System.out.println("📸 Cenário falhou! Capturando evidências...");
                
                // 1. Relatório embutido do Cucumber HTML
                byte[] screenshotBytes = ((TakesScreenshot) activeDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "screenshot_" + scenario.getName());

                // 2. Criação da pasta física na raiz do projeto
                String pastaEvidencias = "evidencias";
                Path path = Paths.get(pastaEvidencias);
                
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                    System.out.println("📁 Pasta 'evidencias' criada com sucesso na raiz do projeto!");
                }

                // Sanitiza o nome do arquivo para o Linux Ubuntu
                String nomeArquivoSanitizado = scenario.getName()
                        .replaceAll("[^a-zA-Z0-9\\s]", "")
                        .replaceAll("\\s+", "_");
                
                // Salva o arquivo físico .png
                File srcFile = ((TakesScreenshot) activeDriver).getScreenshotAs(OutputType.FILE);
                File destFile = new File(pastaEvidencias + "/" + nomeArquivoSanitizado + "_FALHOU.png");
                Files.copy(srcFile.toPath(), destFile.toPath());
                
                System.out.println("✅ Evidência física salva com sucesso em: " + destFile.getAbsolutePath());

            } catch (Exception e) {
                System.err.println("❌ Erro crítico ao manipular o screenshot: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // Finaliza o navegador de forma limpa
        DriverManager.quitDriver();
        System.out.println("🏁 Teste finalizado: " + scenario.getName());
    }
}