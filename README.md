# Todo-List 1.0.0

## Sum�rio

<!-- TOC depthFrom:1 depthTo:1 orderedList:false withLinks:false anchorMode:gitlab.com -->
- 1 - Defini��o do projeto
- 2 - Execu��o
<!-- /TOC -->

# 1 - Defini��o do projeto
Trata-se de um projeto de exemplo, para demonstrar a publica��o de uma API Rest com acesso a uma base de dados real.
Aplicativo publica a API /tasks/ com os m�todo POST/GET/PUT/DELETE, permintindo ao usu�rio fazer uma ger�ncia simples das tasks que tem � fazer, podendo abrir novas e tamb�m fechar as j� existentes.

# 2 - Execu��o
A execu��o do aplicativo pode ser feita pelo comand `./gradlew run`,  assim iniciando a API na porta 8080 da m�quina.
A execu��o dos testes com o newman tamb�m pode ser feita pela linha de comando, por�m � importante ressaltar que ser� necess�rio ter o npm instalado para que seja poss�vel executar os testes. Outro ponto importante destes testes � que ocorrer� uma conex�o com a base de dados e tamb�m o insert de registros, portanto se for utilizado a mesma base de dados para execu��o � importante limpar antes. Ap�s a execu��o os testes iram criar um relat�rio na pasta `tests_reports`. Para executar os testes para utilizar o comando `./run_tests.sh`.