# TodoList

Projeto desenvolvido utilizado Jetty e Jax-RS para desenvolvimento de API Rest.

É utilizado um Server Jetty embarcado para executar, o acesso deve ser feito pela porta 8090.
Não existe nenhuma task do gradle para gerar o jar do modulo, é necessário utilizar a IDE para compilar e executar pela classe br.com.santos.william.todolist.Main
Configuração atual utiliza base de dados Postgres, porém é apenas necessário trocar o nome do persist unit para "todo-list-main-mysql" na classe JpaEntityManager

Também é possível executar o app pelo comando ./gradlew run