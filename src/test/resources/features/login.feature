# language: pt
# mvn clean && mvn test -Pdev -Dbrowser=chrome -Dcucumber.filter.tags="@regression"
# mvn clean && mvn test -Pdev -Dcucumber.filter.tags="@regression" -Dheadless=true

Funcionalidade: Login no SauceDemo
  Como usuário do sistema
  Quero realizar login na aplicação
  Para acessar o inventário de produtos

  Contexto:
    Dado que estou na página de login

  @regression @web @ID01 @teste
  Cenário: ID01 - Login com sucesso
    Quando faço login com credenciais padrão
    Então devo ser redirecionado para página de inventário
    E devo ver o logo da aplicação

  @regression @web @ID02 @teste
  Cenário: ID02 - Login com usuário bloqueado
    Quando faço login com usuário bloqueado
    Então devo ver a mensagem de erro "Epic sadface: Sorry, this user has been locked out."

  @regression @web @ID03 @teste
  Cenário: ID03 - Login com usuário e senha inválida
    Quando faço login com usuário "usuario_errado" e senha "senha_errada"
    Então devo ver a mensagem de erro "Username and password do not match any user in this servici"

#  @regression @web @ID04 @teste
#  Cenário: ID04 - Forcando a falha para usuario bloqueado
#    Quando faço login com usuário bloqueado
#    Então devo ver a mensagem de erro "Erro bloqueado"
#
#  @regression @web @ID05 @teste
#  Cenário: ID05 - Forcando a falha para usuario e senha invalida
#    Quando faço login com usuário "usuario_errado" e senha "senha_errada"
#    Então devo ver a mensagem de erro "Erro inválido"  