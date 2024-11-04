# M3P-BackEnd-Squad3

# Lab Inc

A gestão do inventário de médicos é uma tarefa complexa e crítica no setor de saúde. Muitos consultórios e clínicas enfrentam desafios ao administrar informações sobre pacientes, consultas e exames, o que pode levar a erros, perda de dados e dificuldades na comunicação entre os profissionais de saúde.

O software **Lab Inc** aborda essas questões, proporcionando uma solução eficiente para cadastrar, editar e deletar informações de pacientes, além de gerenciar suas consultas e exames. Ele também permite que os profissionais de saúde acessem prontuários detalhados, que incluem todas as consultas e exames associados a cada paciente.

A relevância deste software está na sua capacidade de melhorar a organização e a eficiência na gestão de informações médicas. Com um sistema centralizado:

- **Redução de Erros**: A automação na gestão de dados reduz a probabilidade de erros humanos, como a duplicação de registros ou informações desatualizadas.

- **Acesso Rápido e Eficiente**: Os profissionais de saúde podem acessar rapidamente o histórico médico dos pacientes, o que é crucial para a tomada de decisões informadas durante atendimentos.

- **Melhora na Qualidade do Atendimento**: Com um gerenciamento eficiente, os profissionais podem se concentrar mais no atendimento ao paciente e menos na administração, resultando em um atendimento mais humanizado e eficaz.

- **Facilidade de Relatórios e Análises**: O software pode facilitar a geração de relatórios sobre a saúde da população atendida, permitindo que as clínicas identifiquem tendências e melhorem continuamente seus serviços.

Em suma, este software é uma ferramenta indispensável para a gestão do inventário médico, contribuindo significativamente para a melhoria da qualidade e eficiência dos serviços de saúde.

## Técnicas e Tecnologias Utilizadas

- **Java 21**: Linguagem de programação base utilizada para desenvolver o software
- **Spring Boot**: Estrutura de aplicação baseada em Java que simplifica o desenvolvimento de aplicativos. Permite criar APIs para listar, cadastrar, atualizar e deletar dados
- **Maven**: Ferramenta que automatiza a compilação de projetos Java e a gestão de dependências
- **Git**: Sistema de controle de versão
- **GitHub**: Plataforma para gestão de projetos com controle de versão Git
- **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional

## Documentação da API
- A documentação completa da API pode ser acessada através do Swagger: http://localhost:8080/swagger-ui/index.html.

## Como Executar


```bash
# Comando para clonar o repositório
git clone git@github.com:FullStack-Health/M3P-BackEnd-Squad3.git

# Acesse o diretório
cd M3P-BackEnd-Squad3/Lab-Inc

# Execute o software com a ferramenta Maven
./mvnw spring-boot:run
```
