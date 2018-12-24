package br.com.santos.william.todolist;

import br.com.santos.william.todolist.task.TaskResources;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            LOGGER.error(String.format("Error in Thread %s", thread));
            LOGGER.error(exception.getMessage(), exception);
        });

        Server jettyServer = new Server(8090);
        try {
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");

            jettyServer.setHandler(context);

            ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
            jerseyServlet.setInitOrder(0);

            jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", TaskResources.class.getCanonicalName());

            jettyServer.start();
            jettyServer.join();
        } catch (Exception ex) {
            LOGGER.error("Error in MainThread", ex);
        } finally {
            jettyServer.destroy();
        }
    }
}
