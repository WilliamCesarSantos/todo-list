# Todo-List 1.0.0

## Sumário

<!-- TOC depthFrom:1 depthTo:2 orderedList:false withLinks:false anchorMode:gitlab.com -->
- 1 - Tecnologias
	- 1.1 Spring-Boot - http://spring.io/projects/spring-boot
	- 1.2 JUnit - https://junit.org/junit5/
	- 1.3 PostgreSQL - https://www.postgresql.org/
	- 1.4 Newman - https://www.getpostman.com/docs/v6/postman/collection_runs/command_line_integration_with_newman
- 2 - Definição do projeto
- 3 - Execução
<!-- /TOC -->

# 1 - Tecnologias
Projeto todo baseado em Spring com a utilização do Spring-Boot para configuração e inicialização do projeto. O projeto disponibiliza uma API Rest, esta sendo construída com base no Spring.
 
## 1.1 - Spring-Boot
Utilizado para configuração do projeto, Rest API, acesso a base de dados. Toda a parte Java do projeto é baseado em Spring.

## 1.2 - JUnit
Utilizado ferramenta de testes unitário para validar a lógica envolvida.

## 1.3 - PostgreSQL
Base de dados relacional gratuita. Utilizada devido a ser completa e ter uma facilidade de uso/configuração.

## 1.4 - Newman
Utilizado para fazer validação da API publicada, assim é possível realizar as ações que o usuário realizaria e validar que as mesmas estejam de acordo com o esperado.

# 2 - Definição do projeto
Trata-se de um projeto de exemplo, para demonstrar o uso do Spring-Boot em uma aplicação, configurações necessárias e tudo que é necessário fazer para publicar uma API Rest com acesso a uma base de dados real.
Aplicativo publica a API /tasks/ com os método POST/GET/PUT/DELETE, permintindo ao usuário fazer uma gerência simples das tasks que tem à fazer, podendo abrir novas e também fechar as já existentes.

# 3 - Execução
A execução do aplicativo pode ser feita pelo comand `./gradlew run`,  assim iniciando a API na porta 8080 da máquina.
A execução dos testes com o newman também pode ser feita pela linha de comando, porém é importante ressaltar que será necessário ter o npm instalado para que seja possível executar os testes. Outro ponto importante destes testes é que ocorrerá uma conexão com a base de dados e também o insert de registros, portanto se for utilizado a mesma base de dados para execução é importante limpar antes. Após a execução os testes iram criar um relatório na pasta `tests_reports`. Para executar os testes para utilizar o comando `./run_tests.sh`.