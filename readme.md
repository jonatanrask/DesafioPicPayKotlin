# Desafio Backend PicPay

Este projeto tem como objetivo resolver o desafio proposto pelo [PicPay](https://github.com/PicPay/picpay-desafio-backend?tab=readme-ov-file) utilizando Kotlin e SpringBoot.

## Funcionalidades

Fugindo do escopo do desafio, foi criado um CRUD completo para aa entidades, proporcionando a capacidade de buscar, criar, editar e excluir através de requisições.

### Visualização de Usuários

É possível visualizar todos os usuários cadastrados de forma paginada pelo endpoint:

```
GET {{host}}/users
```

#### Exemplo de Resposta:

```json
200
{
    "content": [
        {
            "id": "45946501-a8db-4a8f-9e80-2ddef6f17ed1",
            "name": "João Silva",
            "document": "111.222.333-44",
            "email": "joao@example.com",
            "password": null,
            "balance": 1500.00,
            "userType": "COMMON"
        },
        {
            // outros usuários...
        }
    ],
    // outras informações de paginação...
}
```

### Busca de Usuários

É possível buscar um usuário utilizando os endpoints abaixo, passando como parâmetro o ID, email ou documento:

```
GET {{host}}/users/id/45946501-a8db-4a8f-9e80-2ddef6f17edb
```

```
GET {{host}}/users/email/empresa_pqr@example.com
```

```
GET {{host}}/users/document/12.345.678/0001-32
```

#### Exemplo de Resposta:
``` Json
200
{
    "id": "45946501-a8db-4a8f-9e80-2ddef6f17edb",
    "name": "Empresa PQR",
    "document": "12.345.678/0001-32",
    "email": "empresa_pqr@example.com",
    "password": null,
    "balance": 6500.00,
    "userType": "MERCHANT"
}
```


### Inserção de Usuários

A inserção é feita pelo endpoint:

```
POST {{host}}/users
```

É obrigatório passar email e documento no corpo da requisição.
#### Exemplo:

```json
{
    "name": "User",
    "document": "111.111.111-12",
    "email": "User@example.co",
    "password": 1234,
    "balance": 1500.00,
    "userType": "COMMON"
}
```

### Atualização de Usuários

A atualização é feita pelo endpoint:

```
PUT {{host}}/users/6552741e-9472-4f03-bff4-acb55ff1c850
```

```json
{
    "name": "User2",
    "document": "111.111.111-12",
    "email": "User@example.co",
    "password": null,
    "balance": 1500.00,
    "userType": "COMMON"
}
```

Caso sejá passada uma chave que não existe no banco de dados será retornado erro na requisição
#### Exemplo de Resposta:
``` JSON
404
{
    "timeStamp": "2024-05-05T15:40:09.818963571",
    "status": 404,
    "message": "User with ID 6552741e-9472-4f03-bff4-acb55ff1c910 not found",
    "error": "Resource not found",
    "path": "http://localhost:8080/users/6552741e-9472-4f03-bff4-acb55ff1c910"
}
```

### Remoção de Usuários

A remoção é feita pelo endpoint:

```
DELETE {{host}}/users/6552741e-9472-4f03-bff4-acb55ff1c850
```
Caso houver uma transação relacionada a esse usuário será retornado erro por violação de integridade:
#### Exemplo de Resposta:
``` JSON
400
{
    "timeStamp": "2024-05-05T15:47:47.818963571",
    "status": 400,
    "message": "Data error occurred while trying to delete a user 6552741e-9472-4f03-bff4-acb55ff1c910",
    "error": "Database violation",
    "path": "http://localhost:8080/users/6552741e-9472-4f03-bff4-acb55ff1c910"
}
```

### Transações

#### Visualização de Transações

É possível visualizar todas as transações cadastradas de forma paginada pelo endpoint:

```
GET {{host}}/transactions
```

#### Exemplo de Resposta:
``` JSON
200
{
    "content": [
        {
            "id": "45946501-a8db-9a8f-9e80-2ddef6f17ed9",
            "userPayer": {
                "id": "45946501-a8db-4a8f-9e80-2ddef6f17edb",
                "name": "Empresa PQR",
                "document": "12.345.678/0001-32",
                "email": "empresa_pqr@example.com",
                "password": "senhaPQR",
                "balance": 6500.00,
                "userType": "MERCHANT"
            },
            "userPayee": {
                "id": "45946501-a8db-4a8f-9e80-2ddef6f17ee2",
                "name": "Juliana Pereira",
                "document": "999.000.111-22",
                "email": "juliana@example.com",
                "password": "senha111",
                "balance": 2900.00,
                "userType": "COMMON"
            },
            "balance": 100.00,
            "createdAt": "2024-05-04T10:00:00"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 20,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "sorted": false,
        "empty": true,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
```

#### Busca de Transação

É possível buscar uma transação pelo seu ID utilizando o endpoint:

```
GET {{host}}/transactions/45946501-a8db-9a8f-9e80-2ddef6f17ed9
```

#### Exemplo de Resposta:


``` JSON
{
200
    "id": "45946501-a8db-9a8f-9e80-2ddef6f17ed9",
    "userPayer": {
        "id": "45946501-a8db-4a8f-9e80-2ddef6f17edb",
        "name": "Empresa PQR",
        "document": "12.345.678/0001-32",
        "email": "empresa_pqr@example.com",
        "password": "senhaPQR",
        "balance": 6500.00,
        "userType": "MERCHANT"
    },
    "userPayee": {
        "id": "45946501-a8db-4a8f-9e80-2ddef6f17ee2",
        "name": "Juliana Pereira",
        "document": "999.000.111-22",
        "email": "juliana@example.com",
        "password": "senha111",
        "balance": 2900.00,
        "userType": "COMMON"
    },
    "balance": 100.00,
    "createdAt": "2024-05-04T10:00:00"
}
```

Caso não exista uma trasação com o ID informado retorna erro
#### Exemplo de Resposta:
```JSON
404
{
    "timeStamp": "2024-05-05T15:59:19.794469925",
    "status": 404,
    "message": "Transaction with id 45946501-a8db-9a8f-9e80-2ddef6f99ed9 not found",
    "error": "Resource not found",
    "path": "http://localhost:8080/transactions/45946501-a8db-9a8f-9e80-2ddef6f99ed9"
```

#### Inserção de Transação

A inserção de transação é feita pelo endpoint:

```
POST {{host}}/transactions
```

É necessário passar o ID do pagador, do recebedor e o valor da transação no corpo da requisição. Exemplo:
#### Exemplo de Resposta:
```json
{
    "userPayee": {
        "id": "45946501-a8db-4a8f-9e80-2ddef6f17edb"
    },
    "userPayer": {
        "id": "45946501-a8db-4a8f-9e80-2ddef6f17ee2"
    },
    "balance": 100.00
}
```
Caso hajá a tentativa de enviar dinheiro com uma empresa retornará erro já que não é permitido esse tipo de transaçao
para esse tipo de usuario
#### Exemplo de Resposta:
``` JSON
405
{
    "timeStamp": "2024-05-05T16:04:03.702832689",
    "status": 405,
    "message": "Merchant only receive transfers!",
    "error": "Cannot transfer",
    "path": "http://localhost:8080/transactions"
}
```

#### Remoção de Transação

A remoção de uma transação é feita pelo endpoint:

```
DELETE {{host}}/transactions/45946501-a8db-9a8f-9e80-2ddef6f99ed9
```
