# Projeto EMI API

Este projeto é uma API Spring Boot para gerenciamento de usuários.

## Requisitos

- Java 21
- Maven ou Maven Wrapper
- MySQL instalado e em execução
- Git (opcional)

## Configuração do ambiente

### 1. Instalar Java 21
No Ubuntu/Debian, execute:

```bash
sudo apt update
sudo apt install -y openjdk-21-jdk
java -version
```

### 2. Criar o banco de dados MySQL
O projeto utiliza as seguintes configurações no arquivo `src/main/resources/application.properties`:

- URL: `jdbc:mysql://localhost/tccteste_api`
- Usuário: `emi`
- Senha: `emi`

Crie o banco com:

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS tccteste_api;"
```

Adicione o usuário com:
```bash
mysql -u root -p -e CREATE USER IF NOT EXISTS 'emi'@'%' IDENTIFIED BY 'emi'; GRANT ALL PRIVILEGES ON *.* TO 'emi'@'%' WITH GRANT OPTION; FLUSH PRIVILEGES;
```

Se o MySQL não estiver rodando, inicie o serviço antes.

### 3. Rodar o projeto
Entre na pasta do projeto e execute:

```bash
cd /home/wa59/Projetos/Projeto-EMI
chmod +x mvnw
./mvnw spring-boot:run
```

### 4. Testar a API
Após a aplicação subir, acesse:

```bash
http://localhost:8088/hello
```

Resposta esperada:

```text
Hello world spring 2
```

## Comandos úteis

### Executar testes

```bash
./mvnw test
```

### Compilar o projeto

```bash
./mvnw -DskipTests compile
```

## Observações

- O projeto usa Spring Boot 3.5.14.
- A versão do Java definida no arquivo `pom.xml` é 21.
