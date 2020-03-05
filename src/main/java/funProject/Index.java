package funProject;

import java.sql.Connection;

public class Index {

    public static void main(String[] args) throws Exception {

        String testPW = "Test";
        String testUser = "Joshua";

        String database_url = System.getenv("DATABASE_URL");
        String host = "";
        String user = "";
        String password = "";
        String database = "";

        if(database_url != null && !database_url.isEmpty()){
            host = database_url.substring(91, 131);
            user = database_url.substring(11, 25);
            password = database_url.substring(26, 90);
            database = database_url.substring(137, 151);
        } else {
            host = System.getenv("DBHOST");
            user = System.getenv("DBUSER");
            password = System.getenv("DBPWD");
            database = System.getenv("DB");
        }

        if(host == null || host.isEmpty() || user == null || user.isEmpty() || password == null || password.isEmpty() || database == null || database.isEmpty()){
            System.err.println("Missing environment variables");
            System.exit(1);
        }
        System.out.printf("Starting app with host: %s, user: %s, database: %s \n",host,user,database);

        PostgreSQL postgreSQL = new PostgreSQL(host, user, password, database);
        Connection connection = postgreSQL.connect();

        ReadDatabase readDatabase = new ReadDatabase(connection);
        HashPassword hashPassword = new HashPassword();
        InsertCredentials insertCredentials = new InsertCredentials(connection, hashPassword);
        Login login = new Login(hashPassword, readDatabase);
        System.out.println("Test with Password: '" + testPW + "' and User: '" + testUser + "'");
        System.out.println("Password right?: " + login.validateCredentials(testPW,testUser));

        Jetty jetty = new Jetty(hashPassword,readDatabase, connection);
        jetty.startJetty();
    }
}