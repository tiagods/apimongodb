## Executar o docker-compose para subir as aplicações

- api para listagem de cliente na porta 8080 => localhost:8080/swagger-ui.html
- api para listagem de produto na porta 8081 => localhost:8081/swagger-ui.html
- api para listagem de cliente x produtos na porta 8082 => localhost:8082/swagger-ui.html
- formulario, porem a tempo so consegui criar a funcionalidade do 1 endpoint do cliente

Todos as tarefas abaixo foram realizadas, com exceção da funcionalidade de listar produtos por usuario, infelizmente não deu tempo


# Teste para desenvolvedor Java

Esse é um teste para que possamos avaliar seus conhecimentos técnicos em Java e qualidade do código. O mais importante não é terminá-lo por completo, mas a qualidade do que foi feito :) 


## 1ª parte (Feito)
Criar um cadastro de usuários com os seguintes campos:
- Nome Composto (Obrigatório)
- CPF (Obrigatório e único)
- Endereço (Obrigatório)
- Telefone (Opcional)
- Email (Obrigatório)

Deve ser possível cadastrar, editar, buscar ou excluir um usuário. Com exceção da funcionalidade de cadastro, o usuário deve ser identificado por chave primária autogerada pela base de dados.

## 2ª Parte (Feito)
Dado o [banco de dados de produtos fornecido](Produtos/Dockerfile), criar um endpoint para que seja possível listá-los

## 3ª Parte (Feito)
Criar um endpoint para que seja possível vincular um usuário já cadastrado a um dos produtos disponíveis. Ao realizar a vinculação, gerar um número de mídia único que represente o vínculo entre usuário e produto. Caso seja feita a combinação de um usuário e produtos já previamente vinculados, cancelar a anterior, porém manter os dados no banco para fim de histórico.

## Requisitos
- Seguir o padrão RESTFUL (Feito)
- Utilizar as funcionalidades do Java 8 quando possível (Streams, lambda, method reference) (Ok)

## Diferenciais

### Funcionalidades
- Listagem de usuários para um determinado tipo de produto. Caso nenhum produto seja espeficado, listar todos. Aqui não devem ser listados os produtos de determinado usuário que não estejam ativos. (Não conclui essa fase)
- Lista de produtos que um determinado usuário já possui ou possuiu, com status (Não conclui essa fase)

### Estrutura do código
- Testes unitários (Feito)
- Clean Code (Feito)
- Docker e microsserviços (Feito) 
- Documentação Swagger (Feito)
