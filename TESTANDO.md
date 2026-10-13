# Como aplicar e testar

## 1. Aplicar no projeto

**Apague** (não existem mais no modelo novo):
- `domain/conteudo/` (toda a pasta) e `controller/ConteudoController.java`
- `domain/usuario/DadosListagemUsuario.java`
- `domain/chat/SessaoChatRepository.java`
- `domain/desafio/SubmissaoDesafioDTO.java`
- `db/migration/V1` até `V9` (todas)

**Copie** o conteúdo desta pasta por cima do projeto (`src/main/java/...`, `src/main/resources/...`, `src/test/...`).
`ApiApplication.java` continua igual.

**pom.xml**: adicione as duas dependências abaixo (se ainda não existirem):

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

**Banco** (o Flyway só roda a nova V1 em banco vazio):
```sql
DROP DATABASE emi;
CREATE DATABASE emi;
```

**Variáveis de ambiente**: `DB_USER`, `DB_PASSWORD`, `GEMINI_API_KEY` (e `CORS_ORIGENS` se o front não estiver em localhost:5173 ou :3000).

## 2. Roteiro de testes (Insomnia/Postman), nesta ordem

1. `POST /trilhas`
   `{"nomeTrilha":"Português"}`
2. `POST /modulos`
   `{"nomeModulo":"Gramática","descricao":"Neste módulo você aprenderá...","idTrilha":1}`
3. `POST /aulas` (faça duas: ordem 1 e ordem 2)
   ```json
   {"idModulo":1,"nomeAula":"Como usar pontuação","previaDoConteudo":"Entenda como se usa...",
    "texto":"Texto completo da aula...","videoUrl":"https://www.youtube.com/watch?v=xxxx","ordem":1}
   ```
4. `POST /desafios` (ordem 3: só libera depois das aulas 1 e 2)
   ```json
   {"idModulo":1,"nomeDesafio":"Questões sobre pontuação","notaDeCorte":60,"recompensaXp":300,"ordem":3,
    "questoes":[
      {"enunciado":"Qual sinal encerra uma pergunta?","alternativaA":"Ponto final","alternativaB":"Interrogação",
       "alternativaC":"Vírgula","alternativaD":"Dois-pontos","respostaCorreta":"B"},
      {"enunciado":"Segunda questão...","alternativaA":"a","alternativaB":"b","alternativaC":"c","alternativaD":"d","respostaCorreta":"A"}
    ]}
   ```
5. `POST /insignias`
   `{"nomeInsignia":"Mestre da Gramática","descricao":"Conclua a missão X","iconeUrl":null,"raridade":"RARO"}`
6. `POST /missoes`
   ```json
   {"titulo":"Começando a jornada","descricao":"Conclua 2 aulas do módulo","tipo":"AULAS_CONCLUIDAS",
    "meta":2,"idModulo":1,"recompensaXp":100,"idInsigniaRecompensa":1}
   ```
7. `POST /usuarios`
   `{"nomeUsuario":"Ana","email":"ana@email.com","senha":"123456","dataNascimento":"2008-05-10"}`
8. `POST /auth/login` → `{"email":"ana@email.com","senha":"123456"}`

### O que verificar
- `GET /modulos/1/progresso?idUsuario=1` → desafio com `"liberado": false`
- `POST /aulas/1/concluir?idUsuario=1` → ganha 250 XP; repetir devolve `xpGanho: 0`
- `POST /aulas/2/concluir?idUsuario=1` → `missoesConcluidas` traz a missão, +100 XP e a insígnia
- `GET /modulos/1/progresso?idUsuario=1` → desafio agora `"liberado": true`
- `GET /desafios/1` → questões **sem** `respostaCorreta`
- `POST /desafios/1/responder`
  `{"idUsuario":1,"respostas":[{"idQuestao":1,"resposta":"B"},{"idQuestao":2,"resposta":"A"}]}`
  → nota 100, aprovado, +300 XP. Repita: aprovado de novo, mas `xpGanho: 0`.
- `GET /usuarios/1` → sem campo `senha`; `GET /usuarios/1/insignias`; `GET /missoes/usuario/1` → `progresso 2 de 2`
- `POST /chat/mensagens` → `{"idUsuario":1,"mensagem":"Pode me ajudar com vírgula?"}`; depois `GET /chat/historico?idUsuario=1`

### Erros esperados (todos no formato `{"status":..., "erro":"..."}`)
| Situação | Status |
|---|---|
| Campo obrigatório vazio | 400 com `campos` |
| Id inexistente | 404 |
| E-mail repetido / ordem repetida no módulo | 409 |
| Responder desafio ainda bloqueado | 422 |
| Login errado | 401 |
| Gemini fora do ar | 502 |

## 3. Pendências conscientes (documente no TCC)
- `idUsuario` ainda vem na requisição (`/concluir`, `/responder`, `/progresso`, `/chat`). Vira token JWT na etapa de segurança.
- Qualquer pessoa ainda cria/apaga conteúdo: falta o perfil PROFESSOR/ADMIN.
- Sem Spring Security ativo, `/auth/login` devolve só o perfil, sem token.
