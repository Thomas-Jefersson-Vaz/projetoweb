# Projeto de Desenvolvimento Web Servidor

Participantes:
- Thomas Jefersson Vaz
- Rafael Martins Schrot
- Higor Ribeiro de Freitas

---

## Como rodar o projeto do zero

Siga os passos abaixo para configurar e executar o projeto em sua máquina local:

### 1. Pré-requisitos
- **Java 21**
- **Maven**
- **PostgreSQL** (banco de dados)
- Um servidor de aplicação Web (ex: **Apache Tomcat**)

### 2. Variáveis de Ambiente
O projeto utiliza um arquivo `.env` para gerenciar as credenciais de banco de dados.

1. Acesse a pasta `src/main/resources/`.
2. Renomeie (ou copie) o arquivo `example.env` para `.env`.
3. Abra o arquivo `.env` e preencha as informações do seu banco de dados PostgreSQL:
   ```env
   DB_URL=jdbc:postgresql://localhost:5432/nome_do_seu_banco
   DB_USER=seu_usuario
   DB_PASSWORD=sua_senha
   ```
4. Crie o banco de dados (com o nome escolhido acima) no seu servidor PostgreSQL.

### 3. Banco de Dados e Migrations (Flyway)
O projeto utiliza o **Flyway** para controle de versão do banco de dados. Os scripts de criação das tabelas encontram-se em `src/main/resources/db/migration`.

Antes de rodar a aplicação, é necessário aplicar as migrations. Você pode fazer isso de algumas formas:
- **Usando o Maven:** Adicione o `flyway-maven-plugin` no seu `pom.xml` (junto com o driver do PostgreSQL) e execute `mvn flyway:migrate`.
- **Usando a CLI do Flyway:** Execute o Flyway apontando para a pasta das migrations e para a URL do seu banco de dados.

### 4. Compilando o Projeto
Abra o terminal na raiz do projeto e execute o comando abaixo para compilar e empacotar a aplicação:

```bash
mvn clean package
```
Isso fará o download das dependências e gerará o arquivo `ROOT.war` dentro da pasta `target/`.

### 5. Executando a Aplicação
1. Copie o arquivo `target/ROOT.war`.
2. Cole o arquivo na pasta `webapps` da instalação do seu **Tomcat** (ou servidor de sua preferência).
3. Inicie o servidor.
4. Acesse a aplicação pelo navegador, geralmente no endereço: `http://localhost:8080/`.
