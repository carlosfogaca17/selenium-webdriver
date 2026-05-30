🛠️ Funcionalidades Implementadas

    Evidências Inteligentes: Screenshots automáticos capturados em caso de falha de cenários, salvando o arquivo físico .png na pasta /evidencias (sanitizado para Linux/Ubuntu) e embutindo a imagem diretamente no relatório HTML.

    Multi-ambiente Nativo: Suporte para alternância dinâmica de ambientes (dev, hml, prd, ci) através de propriedades passadas via linha de comando do Maven.

    Gerenciador de Configuração Centralizado: ConfigManager implementado sob o padrão de projeto Singleton, garantindo leitura única e segura do arquivo config.properties.

💻 Como Executar o Projeto Localmente
Pré-requisitos

    Java JDK 11 ou superior instalado (Recomendado JDK 17).

    Apache Maven instalado e configurado nas variáveis de ambiente.

    Google Chrome instalado no sistema operacional.

Comando para Executar os Testes

Para rodar os cenários de teste filtrados por tags e definindo o perfil de ambiente de desenvolvimento, execute o comando abaixo no terminal:
Bash

mvn test -Pdev -Dcucumber.filter.tags="@teste"

Limpar os Artefatos de Build Antigos

Caso mude arquivos de pacotes ou queira limpar o cache local do Maven:
Bash

mvn clean

📊 Relatórios de Testes

Após a execução dos testes, o framework gera relatórios nativos detalhados dentro do diretório /reports:

    cucumber-report.html: Relatório visual interativo completo. Pode ser arrastado e aberto diretamente em qualquer navegador (Google Chrome, Firefox, etc.).

    cucumber.json / cucumber.xml: Arquivos estruturados utilizados para integração e plotagem de gráficos em esteiras de CI/CD.

🤖 Integração Contínua (CI/CD) - GitHub Actions Preview

O framework está preparado para execução headless (sem interface gráfica) integrado ao GitHub Actions. O arquivo de workflow utiliza os relatórios gerados em formato JSON/XML para publicar os resultados a cada Pull Request ou Push na branch main.
"""

with open("README.md", "w", encoding="utf-8") as f:
f.write(readme_content)

print("Arquivo README.md gerado com sucesso!")