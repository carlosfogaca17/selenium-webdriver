package com.sauce.pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginElements {
    
    // ===== Formulário de Login =====
    @FindBy(id = "user-name")
    public WebElement usernameField;
    
    @FindBy(id = "password")
    public WebElement passwordField;
    
    @FindBy(id = "login-button")
    public WebElement loginButton;
    
    // ===== Mensagens de Erro =====
    @FindBy(css = ".error-message-container")
    public WebElement errorContainer;
    
    @FindBy(css = ".error-message-container h3")
    public WebElement errorMessage;
    
    @FindBy(css = ".error-button")
    public WebElement errorCloseButton;
    
    // ===== Header e Elementos Visuais =====
    @FindBy(className = "login_logo")
    public WebElement logo;

    @FindBy(className = "app_logo")
    public WebElement logoHome;
    
    @FindBy(css = ".bot_column")
    public WebElement robotImage;
    
    // ===== Links =====
    @FindBy(linkText = "Twitter")
    public WebElement twitterLink;
    
    @FindBy(linkText = "Facebook")
    public WebElement facebookLink;
    
    @FindBy(linkText = "LinkedIn")
    public WebElement linkedinLink;
}