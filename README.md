# Todo-List 1.0.0

## Sumário

<!-- TOC depthFrom:1 depthTo:1 orderedList:false withLinks:false anchorMode:gitlab.com -->
- 1 - Definição do projeto
- 2 - Execução
<!-- /TOC -->

# 1 - Definição do projeto
Trata-se de um projeto de exemplo, para demonstrar a publicação de uma API Rest com acesso a uma base de dados real.
Aplicativo publica a API /tasks/ com os método POST/GET/PUT/DELETE, permintindo ao usuário fazer uma gerência simples das tasks que tem à fazer, podendo abrir novas e também fechar as já existentes.

# 2 - Execução
A execução do aplicativo pode ser feita pelo comand `./gradlew run`,  assim iniciando a API na porta 8080 da máquina.
A execução dos testes com o newman também pode ser feita pela linha de comando, porém é importante ressaltar que será necessário ter o npm instalado para que seja possível executar os testes. Outro ponto importante destes testes é que ocorrerá uma conexão com a base de dados e também o insert de registros, portanto se for utilizado a mesma base de dados para execução é importante limpar antes. Após a execução os testes iram criar um relatório na pasta `tests_reports`. Para executar os testes para utilizar o comando `./run_tests.sh`.