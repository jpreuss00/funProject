package funProject;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
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

    private final HashPassword hashPassword;
    private final ReadDatabase readDatabase;
    private final Connection connection;

    public Jetty(HashPassword hashPassword, ReadDatabase readDatabase, Connection connection){
        this.hashPassword = hashPassword;
        this.readDatabase = readDatabase;
        this.connection = connection;
    }

    public void startJetty() throws Exception {

        final ContextHandler root = new ContextHandler();
        final ContextHandler health = new ContextHandler("/health");
        final ContextHandler register = new ContextHandler("/register");
        final ContextHandler login = new ContextHandler("/login");

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

        register.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                String username = "";
                String password = "";
                if(request.getParameter("password") != null){
                    password = request.getParameter("password");
                }
                if(request.getParameter("username") != null){
                    username = request.getParameter("username");
                }
                InsertCredentials insertCredentials = new InsertCredentials(connection, hashPassword);
                try {
                    insertCredentials.insertInDB(username, password);
                } catch (NoSuchAlgorithmException | SQLException e) {
                    e.printStackTrace();
                }
                baseRequest.setHandled(true);
                System.out.println("register Page is running...");
            }
        });

        login.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                String password = "";
                String username = "";
                if(request.getParameter("password") != null){
                    password = request.getParameter("password");
                }
                System.out.println(username);
                if(request.getParameter("username") != null){
                    username = request.getParameter("username");
                }
                Login login = new Login(hashPassword, readDatabase);
                try {
                    response.getWriter().println(login.validateCredentials(password, username));
                } catch (NoSuchAlgorithmException | SQLException e) {
                    e.printStackTrace();
                }
                baseRequest.setHandled(true);
                System.out.println("register Page is running...");
            }
        });

        ContextHandlerCollection contexts = new ContextHandlerCollection(root, health, register, login);
        final String port = System.getenv("PORT");
        System.out.println("PORT: " + port);
        final Server server = new Server(Integer.parseInt(port));
        server.setHandler(contexts);
        server.start();
        server.join();
    }
}
