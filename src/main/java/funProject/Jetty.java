package funProject;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.json.JSONObject;

public class Jetty {

    private final Connection connection;

    public Jetty(Connection connection){
        this.connection = connection;
    }

    public void startJetty() throws Exception {

        final ContextHandler root = new ContextHandler();
        final ContextHandler health = new ContextHandler("/health");

        root.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                response.getWriter().println("Hello " + request.getRemoteAddr() + "!");
                response.getWriter().println("Current time: " + LocalDateTime.now());
                baseRequest.setHandled(true);
                System.out.println("Normal page is running...");
            }
        });

        health.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                JSONObject json = new JSONObject().put("Status", "up");
                response.getWriter().print(json);
                baseRequest.setHandled(true);
                System.out.println("Health Page is running...");
            }
        });

        ContextHandlerCollection contexts = new ContextHandlerCollection(root, health);
        final String port = System.getenv("PORT");
        System.out.println("PORT: " + port);
        final Server server = new Server(Integer.parseInt(port));
        server.setHandler(contexts);
        server.start();
        server.join();
    }
}
