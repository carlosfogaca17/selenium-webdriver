package com.sauce.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.sauce.core.driver.DriverManager;
import com.sauce.pages.login.LoginPage;

public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    
    public LoginSteps() {
    }

    // Método que garante a inicialização correta antes de cada passo
    private void inicializarElementos() {
        if (this.driver == null) {
            this.driver = DriverManager.getDriver();
        }
        if (this.loginPage == null) {
            this.loginPage = new LoginPage(this.driver);
        }
    }
    
    @Dado("que estou na página de login")
    public void queEstouNaPaginaDeLogin() {
        inicializarElementos(); // 
        loginPage.acessarLogin();
        Assert.assertTrue("Logo não está visível", loginPage.validarLogo());
        System.out.println("✅ Ambiente: " + loginPage.obterAmbienteAtual());
    }
    
    @Quando("faço login com credenciais padrão")
    public void facoLoginComCredenciaisPadrao() {
        inicializarElementos();
        loginPage.fazerLoginPadrao();
    }
    
    @Quando("faço login com usuário bloqueado")
    public void facoLoginComUsuarioBloqueado() {
        inicializarElementos();
        loginPage.fazerLoginUsuarioBloqueado();
    }
    
    @Quando("faço login com usuário {string} e senha {string}")
    public void facoLoginComUsuarioESenha(String usuario, String senha) {
        inicializarElementos();
        loginPage.fazerLogin(usuario, senha);
    }
    
    @Então("devo ser redirecionado para página de inventário")
    public void devoSerRedirecionadoParaPaginaDeInventario() {
        inicializarElementos();
        boolean redirecionou = loginPage.validarRedirecionamentoParaInventario();
        Assert.assertTrue("Não foi redirecionado para o inventário", redirecionou);
        Assert.assertTrue("URL não contém inventory", 
            loginPage.obterUrlAtual().contains("inventory.html"));
    }
    
    @Então("devo ver o logo da aplicação")
    public void devoVerOLogoDaAplicacao() {
        inicializarElementos();
        Assert.assertEquals("Texto do logo incorreto", "Swag Labs", loginPage.obterTextoLogo());
    }
    
    @Então("devo ver a mensagem de erro {string}")
    public void devoVerAMensagemDeErro(String mensagemEsperada) {
        inicializarElementos();
        String mensagemAtual = loginPage.obterMensagemErro();
        Assert.assertTrue(
            String.format("Mensagem esperada: '%s' | Mensagem atual: '%s'", 
                mensagemEsperada, mensagemAtual),
            mensagemAtual.contains(mensagemEsperada)
        );
    }
}