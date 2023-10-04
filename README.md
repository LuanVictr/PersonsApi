# Api Persons

## Api criada para controle de pessoas

### Endpoints de Persons

#### Criação de uma Pessoa

- Importe o arquivo insomnia_endpoints.json para ter acesso a todos os endpoints e testar as funcionalidades

- **Endpoint:** `/persons`
- **Método:** `POST`
- **Descrição:** Cria uma nova pessoa com as informações fornecidas.
- **Corpo da Requisição (JSON):**
- 
  ```json
  {
    "name": "Nome da Pessoa",
    "birthDate": "Data de Nascimento (formato: yyyy-MM-dd)",
    "address": [
      {
        "publicPlace": "Nome da Rua",
        "postalCode": "CEP",
        "number": "Número da Casa",
        "city": "Cidade"
      }
    ]
  }

**Resposta de Sucesso(Status 201 Created)**:Retorna os detalhes da pessoa criada.

```json
{
  "id": 1,
  "name": "Nome da Pessoa",
  "birthDate": "Data de Nascimento",
  "address": [
    {
      "publicPlace": "Nome da Rua",
      "postalCode": "CEP",
      "number": "Número da Casa",
      "city": "Cidade"
    }
  ]
}
```

### Consulta de Pessoa por ID

- **Endpoint**: `/persons/{personId}`
- **Método**: GET
- **Descrição**: Recupera os detalhes de uma pessoa pelo ID.
- **Parâmetros da URL**: `{personId}` - O ID da pessoa a ser recuperada.

**Resposta de Sucesso (Status 200 OK):** Retorna os detalhes da pessoa.

```json
{
  "id": 1,
  "name": "Nome da Pessoa",
  "birthDate": "Data de Nascimento",
  "address": [
    {
      "publicPlace": "Nome da Rua",
      "postalCode": "CEP",
      "number": "Número da Casa",
      "city": "Cidade"
    }
  ]
}
```

### Consulta de Todas as Pessoas

**Endpoint:** `/persons`

**Método:** GET

**Descrição:** Recupera a lista de todas as pessoas cadastradas.

**Resposta de Sucesso (Status 200 OK):** Retorna uma lista de pessoas.

```json
[
  {
    "id": 1,
    "name": "Nome da Pessoa 1",
    "birthDate": "Data de Nascimento 1",
    "address": [
      {
        "publicPlace": "Rua 1",
        "postalCode": "CEP 1",
        "number": "Número 1",
        "city": "Cidade 1"
      }
    ]
  },
  {
    "id": 2,
    "name": "Nome da Pessoa 2",
    "birthDate": "Data de Nascimento 2",
    "address": [
      {
        "publicPlace": "Rua 2",
        "postalCode": "CEP 2",
        "number": "Número 2",
        "city": "Cidade 2"
      }
    ]
  }
]
```
### Editar uma Pessoa por ID

**Endpoint:** `/persons/{personId`
**Método:** `PUT`
**Descrição:** Atualiza os detalhes de uma pessoa pelo ID.

**Parâmetros da URL:**
- `{personId}` - O ID da pessoa a ser atualizada.

**Corpo da Requisição (JSON):**
```json
{
  "name": "Novo Nome da Pessoa",
  "birthDate": "Nova Data de Nascimento (formato: yyyy-MM-dd)",
  "address": [
    {
      "publicPlace": "Novo Nome da Rua",
      "postalCode": "Novo CEP",
      "number": "Novo Número da Casa",
      "city": "Nova Cidade"
    }
  ]
}
```

## Endpoints de Address

### Criação de Endereço para Pessoa

**Endpoint:** `/address/{personId}`  
**Método:** POST  
**Descrição:** Cria um novo endereço para a pessoa especificada.  

**Parâmetros da URL:**  
- `{personId}` - O ID da pessoa à qual o endereço será adicionado.  

**Corpo da Requisição (JSON):**
```json
{
  "publicPlace": "Nome da Rua",
  "postalCode": "CEP",
  "number": "Número da Casa",
  "city": "Cidade"
}
```

**Resposta de Sucesso (Status 201 Created):** Retorna a lista atualizada de endereços da pessoa.

```json
[
  {
    "publicPlace": "Nome da Rua 1",
    "postalCode": "CEP 1",
    "number": "Número 1",
    "city": "Cidade 1"
  },
  {
    "publicPlace": "Nome da Rua 2",
    "postalCode": "CEP 2",
    "number": "Número 2",
    "city": "Cidade 2"
  }
]
```

### Consulta de Todos os Endereços de Pessoa

**Endpoint:** `/address/{personId}`  
**Método:** GET  
**Descrição:** Recupera a lista de todos os endereços associados a uma pessoa.  

**Parâmetros da URL:**  
- `{personId}` - O ID da pessoa da qual os endereços serão recuperados.  

**Resposta de Sucesso (Status 200 OK):** Retorna uma lista de endereços.
```json
[
  {
    "publicPlace": "Nome da Rua 1",
    "postalCode": "CEP 1",
    "number": "Número 1",
    "city": "Cidade 1"
  },
  {
    "publicPlace": "Nome da Rua 2",
    "postalCode": "CEP 2",
    "number": "Número 2",
    "city": "Cidade 2"
  }
]
```

### Marca Endereço como Principal

**Endpoint:** `/address/main/{personId}/{addressId}`  
**Método:** POST  
**Descrição:** Define um endereço como o endereço principal de uma pessoa.  

**Parâmetros da URL:**  
- `{personId}` - O ID da pessoa para a qual o endereço será definido como principal.
- `{addressId}` - O ID do endereço que será definido como principal.

**Resposta de Sucesso (Status 200 OK):** Retorna o endereço que foi definido como principal.
```json
{
  "publicPlace": "Nome da Rua Principal",
  "postalCode": "CEP Principal",
  "number": "Número Principal",
  "city": "Cidade Principal"
}
```

**Resposta de Erro (Status 404 Not Found):** Retorna uma mensagem de erro se a pessoa ou o endereço com os IDs especificados não forem encontrados.

```json
{
  "error": "Pessoa não encontrada"
}
```
ou

```json
{
  "error": "Endereço não encontrado"
}
```
**Exemplo de Uso:**  
Para definir um endereço como o endereço principal de uma pessoa, 
faça uma requisição POST para `/address/main/{personId}/{addressId}`. Certifique-se de fornecer os IDs válidos da pessoa e do endereço.
Em caso de sucesso, você receberá o endereço definido como principal como resposta.

### Observações
Faltam algumas otimizações dos testes para que cubra 100% da aplicação, mas para simular uma necessidade real, eu respeitei
o tempo de 48h fornecido para realizar esse projeto com algumas horas de folga para ceder esse tempo para o code review.
Certifiquei que tudo funcionaria em produção.























