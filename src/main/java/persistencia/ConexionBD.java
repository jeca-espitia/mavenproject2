/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;        

public class ConexionBD {
    private final String conectorInstalado = "jdbc:mysql:";
    private final String host = "localhost:3306";
    private final String baseDatos = "dbusuarios";
    private final String username = "root";
    private final String password = "admin";
    private Connection conexion;
    private Statement ejecutor;
    
    public ConexionBD() {
        conectar();
    }
    
    public boolean isConectado() {
        return (this.conexion != null);
    }
        
    public void conectar()
    {
        try
        {
            String cadenaConexion = conectorInstalado + "//" + host + "/" + baseDatos;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(cadenaConexion, username, password);
            ejecutor = conexion.createStatement();
            ejecutor.setQueryTimeout(30);  // set timeout to 30 sec.
            //System.out.println("conexión creada: "+conexion);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public ResultSet ejecutarQuery(String sql)
    {
        ResultSet rs = null;
        try
        {
            rs = ejecutor.executeQuery(sql);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return rs;
    }
    
    
        public ResultSet ejecutarInsert(String sql) {
        ResultSet rs = null;
        try
        {
            int cant = ejecutor.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (cant > 0) {
                rs = ejecutor.getGeneratedKeys();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return rs;
    }
    
    /**
     * Ejecuta una sentencia SQL de modificación
     * @param sql la consulta SQL que se arma para modificar un registro
     * @return la cantidad de registros que se han modificado
     */
    public int ejecutarUpdate(String sql) {
        int cant = 0;
        try
        {
            cant = ejecutor.executeUpdate(sql);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return cant;
    }
    
    public void desconectar()
    {
        try {
            conexion.close();
            conexion=null;
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    
}
