# language: pt
# mvn test -Pdev -Dbrowser=chrome -Dcucumber.filter.tags="@teste"

Funcionalidade: Login no SauceDemo
  Como usuário do sistema
  Quero realizar login na aplicação
  Para acessar o inventário de produtos

  Contexto:
    Dado que estou na página de login

  @regression @web @ID01
  Cenário: ID01 - Login com sucesso
    Quando faço login com credenciais padrão
    Então devo ser redirecionado para página de inventário
    E devo ver o logo da aplicação

  @regression @web @ID02
  Cenário: ID02 - Login com usuário bloqueado
    Quando faço login com usuário bloqueado
    Então devo ver a mensagem de erro "Epic sadface: Sorry, this user has been locked out."

  @regression @web @ID03
  Cenário: ID03 - Login com senha inválida
    Quando faço login com usuário "standard_user" e senha "senha_errada"
    Então devo ver a mensagem de erro "Username and password do not match any user in this service"

  @regression @web @ID04
  Cenário: ID04 - Login com usuário vazio
    Quando faço login com usuário "" e senha "secret_sauce"
    Então devo ver a mensagem de erro "Username is required"

  @regression @web @ID05
  Cenário: ID05 - Login com senha vazia
    Quando faço login com usuário "standard_user" e senha ""
    Então devo ver a mensagem de erro "Password is required"