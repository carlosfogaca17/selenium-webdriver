package com.sauce.pages.login;

import org.openqa.selenium.WebDriver;

public class LoginPage {
    private LoginActions actions;
    
    public LoginPage(WebDriver driver) {
        this.actions = new LoginActions(driver);
    }
    
    // Ações principais
    public void acessarLogin() {
        actions.acessarPaginaDeLogin();
    }
    
    public void fazerLogin(String usuario, String senha) {
        actions.fazerLogin(usuario, senha);
    }
    
    public void fazerLoginPadrao() {
        actions.fazerLoginComCredenciaisPadrao();
    }
    
    public void fazerLoginUsuarioBloqueado() {
        actions.fazerLoginComUsuarioBloqueado();
    }
    
    // Validações
    public String obterMensagemErro() {
        return actions.getMensagemErro();
    }
    
    public boolean validarLogo() {
        return actions.isLogoVisible();
    }
    
    public String obterTextoLogo() {
        return actions.getLogoText();
    }
    
    public boolean validarRedirecionamentoParaInventario() {
        return actions.estouNaPaginaDeInventario();
    }
    
    public String obterUrlAtual() {
        return actions.getCurrentUrl();
    }
    
    public String obterAmbienteAtual() {
        return actions.getCurrentEnvironment();
    }
    
    public void fecharMensagemErro() {
        actions.fecharMensagemErro();
    }
}