package ConfiguracionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConexionDB {
    Connection conectar = null;
    
    String usuario = "root";
    String Contraseña = "1263";
    String bd = "dbpos";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection establecerConexion(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,Contraseña);
//            JOptionPane.showMessageDialog(null,"Se conecto a la base de datos correctamente");
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al conectar con la base de datos "+e.toString());
        }
    return conectar;
    }
    
    public void cerrarConexion(){
    
         try {
            if(conectar != null && !conectar.isClosed()){
                conectar.close();
//                JOptionPane.showMessageDialog(null,"Conexion cerrada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se cerro la conexion"+e.toString());
        }
    }
}
