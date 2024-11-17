# API de Gerenciamento de Reservas de Salas

Esta é uma API RESTful desenvolvida com **Spring Boot**, **Java JDK 22** e **MySQL**. Ela permite gerenciar reservas de salas, usuários e regras associadas.
---

## Tecnologias Usadas

-**Intellij IDEA 2024.2.4**:IDEA que foi usanda para fazer os codigos desse projeto.
-**MySQL Workbench 8.0.38**:Foi usando para visualizar os dados do banco de dados.
- **Java JDK 22**: Linguagem principal da aplicação.
- **Spring Boot**: Framework para criar aplicações Java com rapidez.
- **Spring Data JPA**: Para integração com o banco de dados.
- **MySQL**: Banco de dados relacional para armazenar as informações.
- **Swagger/OpenAPI**: Documentação interativa da API.
- **Maven**: Gerenciador de dependências.

---

## Como Rodar a Aplicação

### 1. Pré-requisitos
- **Java JDK 22** e **Maven** instalados.
- **MySQL Server** em execução.

### 2. Configuração do Banco de Dados
1. Crie um banco chamado `sala_ressalva` no MySQL:
   ```sql
   CREATE DATABASE sala_ressalva;
Atualize o arquivo application.properties com as seguintes configurações:
properties
Copiar código
# URL de conexão ao banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/sala_ressalva
# Observação: Caso já esteja usando este endereço (localhost), crie um novo e substitua 
# o endereço nas respectivas ordens de hostname e port.

# Usuário do banco de dados
spring.datasource.username=SEU_USUARIO
# Observação: Substitua "SEU_USUARIO" pelo nome do usuário correto. No padrão, geralmente é "root".

# Senha do banco de dados
spring.datasource.password=SUA_SENHA
# Observação: Substitua "SUA_SENHA" pela senha do usuário do banco.
3. Executar a Aplicação
No terminal, dentro da pasta do projeto, execute:

bash
Copiar código
mvn spring-boot:run
Acesse a API em:

Swagger: http://localhost:8080/swagger-ui/index.html.
🧪 Testar a API
Use o Postman ou outro cliente para enviar requisições HTTP.

Exemplos fictícios:
Criar Usuário (POST /usuarios):

json
Copiar código
{
  "nome": "Pedro Machado",
  "email": "pedro@email.com",
  "fone": "00234567891",
  "cpf": "12435789736"
}
Criar Sala (POST /salas):

json
Copiar código
{
  "nome": "Sala Reunião",
  "departamento": "Negociações",
  "ativa": true
}
🚨 Como Reportar Problemas (Issues)
Clique na aba Issues neste repositório.
Descreva o problema com o máximo de detalhes:
O que você tentou fazer.
Passos para reproduzir o erro.
Logs ou mensagens de erro, se disponíveis.
Adicione uma etiqueta apropriada (ex.: bug, enhancement).
