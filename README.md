# 🥓 Six Degrees of Bacon – Spring Boot Application

![Java](https://img.shields.io/badge/java-%23151737.svg?style=for-the-badge&logo=openjdk&logoColor=white)  
![Spring Boot](https://img.shields.io/badge/spring_boot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)  
![OpenCSV](https://img.shields.io/badge/OpenCSV-00ADD8.svg?style=for-the-badge)  
![Jackson](https://img.shields.io/badge/Jackson-%234F3BCE.svg?style=for-the-badge)


Este projeto implementa a “Lei de Bacon” (Six Degrees of Kevin Bacon) usando Spring Boot. A aplicação constrói um grafo de atores a partir do arquivo `tmdb_5000_credits.csv` e expõe um endpoint REST para encontrar o caminho mais curto — em termos de filmes e co-atores — entre dois atores.

---

## 🎯 Objetivo do Projeto

- Parsear o dataset do TMDB (créditos de filme) e montar um grafo não-direcionado de atores e filmes.
- Implementar um algoritmo de busca em largura (BFS) para encontrar a conexão mínima entre dois atores.
- Expor um endpoint **POST** `​/connect` que recebe dois nomes de atores e retorna o “caminho de Bacon”.

---

## 📚 Contexto do Desafio

> “Seis Graus de Kevin Bacon… o jogo parte do pressuposto de que qualquer pessoa envolvida na indústria cinematográfica de Hollywood pode ser conectada a Bacon em seis etapas…”  
> Fonte: Wikipédia, 2025

1. **Passo 1**: Construa o algoritmo para verificar se existe cadeia de filmes entre dois atores no dataset.
2. **Passo 2**: Implemente este algoritmo usando Spring Boot.

---

## ⚙️ Funcionalidades

- **buildGraphFromCsv()**:
    - Lê `tmdb_5000_credits.csv` (via OpenCSV + Jackson).
    - Para cada filme, conecta todos os pares de atores no grafo.
- **findConnection(start, end)**:
    - Executa BFS no grafo para encontrar o caminho mínimo.
    - Reconstrói a lista alternando “Filme >> Ator >> Filme >> …”.
- **Endpoint**
    - **POST** `/connect`
        - Request Body:
          ```json
          {
            "startActor": "Tom Hanks",
            "endActor": "Kevin Bacon"
          }
          ```  
        - Response (sucesso):
          ```json
          {
            "message": "Conexão encontrada!",
            "path": [
              "Tom Hanks",
              " << Apollo 13 >> ",
              "Kevin Bacon"
            ]
          }
          ```

---

## 📈 Complexidade de Tempo e Espaço

| Parte                          | Tempo                     | Espaço          |
|--------------------------------|---------------------------|-----------------|
| **Construção do Grafo**        | \(O(A + E)\) | \(O(A + E)\)    |
| **Busca em Largura (BFS)**     | \(O(N + E)\)              | \(O(N)\)        |

- \(M\): número de filmes no CSV
- \(A\): número total de atores
- \(E\): número total de conexões
- \(N=A\)

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **OpenCSV** (leitura de CSV)
- **Jackson Databind** (parsing JSON embutido)
- **Maven** (gerenciamento de dependências)

---

## 📂 Estrutura do Projeto

