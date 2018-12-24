Projeto desenvolvido utilizar node.js na versão v8.11.3 e npm version 4.6.1.
Utilizado Framework react para o desnvolvimento e npm para execução.

Fron-end desenvolvido de forma há ter foco no mobile.

Houve um problema com o desnvolvimento do front-end, onde as request estavam sendo paradas pelo Chrome devido a https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Controle_Acesso_CORS, sendo necessário instalar o plugin Allow-Control-Allow-Origin: * version 1.0.3 para o Google Chrome, assim possibilitando que as request cheguem a servidor.

A execução do app pode ser feita pelo comando "npm start", sendo iniciado um server node na porta 3000 para acesso web. O endereço para acesso ao servidor esta fixo em código na clase src/App.js, utilizando o endereço localhost:8090. Caso o server esteja em outra máquina deve ser atualizada a classe com o endereço correto.