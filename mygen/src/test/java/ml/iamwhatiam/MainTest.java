package ml.iamwhatiam;

import org.hsqldb.server.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class MainTest {

    public static void main(String[] args) throws Exception{
        Class.forName("org.hsqldb.jdbcDriver");
        String[] dbArgs = {"-database.0", "db", "-dbname.0", "xdb"};
        Server.main(dbArgs);
        Properties props = new Properties();
//        props.put("user", "SA");
//        props.put("password", "");
        Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/xdb", props);
        con.setAutoCommit(false);
        Statement s = con.createStatement();
        s.execute("CREATE TABLE T_TEST (ID BIGINT, BEFORE CLOB, AFTER CLOB, CREATOR VARCHAR(16), creation_date TIMESTAMP, revisor VARCHAR(16), revision_date TIMESTAMP)");
        s.execute("INSERT INTO T_TEST VALUES (1, 'hehe', 'haha', 'derby', null, 'derby', null)");
        ResultSet rs = s.executeQuery("SELECT * FROM T_TEST");
        while (rs.next()) {
            System.out.println(rs.getLong(1));
        }
        s.execute("DROP TABLE T_TEST");
        rs.close();
        s.close();
        con.commit();
        con.close();
        Connection c = DriverManager.getConnection(
                "jdbc:hsqldb:file:db/xdb;shutdown=true", "SA", "");
    }
}
