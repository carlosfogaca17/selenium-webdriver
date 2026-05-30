package com.sauce.pages.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sauce.core.utils.ConfigManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class LoginActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginElements elements;
    private ConfigManager config;
    private Logger logger;
    
    public LoginActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(
            ConfigManager.getInstance().getExplicitWait()));
        this.elements = new LoginElements();
        this.config = ConfigManager.getInstance();
        this.logger = LogManager.getLogger(this.getClass());
        PageFactory.initElements(driver, elements);
    }
    
    public void acessarPaginaDeLogin() {
    String url = config.getBaseUrl();
    System.out.println("🌐 Tentando acessar: " + url);
    driver.get(url);
    System.out.println("📍 URL atual: " + driver.getCurrentUrl());
    System.out.println("📄 Título da página: " + driver.getTitle());
    wait.until(ExpectedConditions.visibilityOf(elements.logo));
}
    
    public void preencherUsuario(String usuario) {
        wait.until(ExpectedConditions.elementToBeClickable(elements.usernameField));
        elements.usernameField.click();
        elements.usernameField.clear();
        elements.usernameField.sendKeys(usuario);
        logger.info("👤 Usuário preenchido: " + usuario);
    }
    
    public void preencherSenha(String senha) {
        elements.passwordField.click();
        elements.passwordField.clear();
        elements.passwordField.sendKeys(senha);
        logger.info("🔒 Senha preenchida");
    }
    
    public void clicarBotaoLogin() {
        elements.loginButton.click();
        logger.info("🖱️ Botão Login clicado");
    }
    
    public void fazerLogin(String usuario, String senha) {
        preencherUsuario(usuario);
        preencherSenha(senha);
        clicarBotaoLogin();
    }
    
    public void fazerLoginComCredenciaisPadrao() {
        fazerLogin(config.getUsername(), config.getPassword());
    }
    
    public void fazerLoginComUsuarioBloqueado() {
        fazerLogin(config.getLockedUser(), config.getPassword());
    }
    
    public String getMensagemErro() {
        wait.until(ExpectedConditions.visibilityOf(elements.errorMessage));
        String mensagem = elements.errorMessage.getText();
        logger.info("📝 Mensagem de erro: " + mensagem);
        return mensagem;
    }
    
    public void fecharMensagemErro() {
        elements.errorCloseButton.click();
        logger.info("❌ Mensagem de erro fechada");
    }
    
    public boolean isMensagemErroVisible() {
        try {
            return elements.errorContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isLogoVisible() {
        return elements.logo.isDisplayed();
    }
    
    public String getLogoText() {
        return elements.logoHome.getText();
    }
    
    public boolean estouNaPaginaDeInventario() {
        return wait.until(ExpectedConditions.urlContains("inventory.html"));
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public String getCurrentEnvironment() {
        return config.getCurrentEnvironment();
    }
}