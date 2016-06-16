package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by tassyosantana on 16/06/16.
 */
public class Banco {

    private String driver ="com.mysql.jdbc.Driver";
    private String url ="jdbc:mysql://localhost:3306/gestor_financas_development";
    private String user = "root";
    private Connection conn;

    public Banco() throws SQLException, ClassNotFoundException{
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(url,user,"");
    }

    public Connection getConn(){
        return conn;
    }

    public void fecharConexao() throws SQLException{
        conn.close();
    }

}
