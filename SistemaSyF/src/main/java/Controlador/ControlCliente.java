/*
CODIGO PARA MOSTRAR EN LA TABLA TBCLIENTES LOS CLIENTES QUE HAY EN LA BASE DE DATOS.
 */
package Controlador;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nosotros
 */
public class ControlCliente {

    //recibimos como parametro la tabla que 
    public void MostrarClientes(JTable tablaTotalClientes) {
        // Creamos un objeto para establecer conexión con la base de datos
        ConfiguracionBD.ConexionDB objetoConexionDB = new ConfiguracionBD.ConexionDB();

        // Creamos un objeto del modelo cliente para almacenar temporalmente los datos
        ModelosUsu.ModeloCliente objetoCliente = new ModelosUsu.ModeloCliente();

        // Creamos un modelo para la tabla donde definiremos su estructura
        DefaultTableModel modelo = new DefaultTableModel();

        String sql = "";
        //con esto le asigno las columnas al modelo
        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Primer Apellido");
        modelo.addColumn("Segundo Apellido");

        //Aqui a la tabla total clientes le asignamos el modelo que definimos anteriormente
        tablaTotalClientes.setModel(modelo);

        //instruccion para hacer la consulta de los clientes, con el select decimos que queremos recuperar datos "From" osea desde la tabla cliente
        sql = "select cliente.idcliente,cliente.nombres,cliente.appaterno,cliente.apmaterno from cliente;";

        //Asi se ejecuten try y catch tiene que pasar algo, si o si. 
        try {
            /*aqui lo que hacemos es abrir una conexion con la base de datos y con el metodo que tiene el Statement (createStatement();) nos permite crear 
            un objeto de este tipo el cual nos permite enviar la instruccion a la base de datos este resultado se va a guardar en un objeto Resulset*/
            Statement st = objetoConexionDB.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            //Aqui ya con el rs y el .next vamos a navegar entre toda la tabla Clientes y vamos a obtener los valores con los getInt y getString
            while (rs.next()) {
                // Asignamos los valores de cada columna al objeto cliente
                objetoCliente.setIdCliente(rs.getInt("idcliente"));
                objetoCliente.setNombres(rs.getString("nombres"));
                objetoCliente.setApPaterno(rs.getString("appaterno"));
                objetoCliente.setApMaterno(rs.getString("apmaterno"));

                // Añadimos una nueva fila al modelo con los datos del cliente actual
                modelo.addRow(new Object[]{
                    objetoCliente.getIdCliente(),
                    objetoCliente.getNombres(),
                    objetoCliente.getApPaterno(),
                    objetoCliente.getApMaterno()
                });
            }
            tablaTotalClientes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error almostrar usuarios, " + e.toString());
        } finally {
            // Independientemente del resultado, cerramos la conexión a la base de datos
            objetoConexionDB.cerrarConexion();
        }

    }
    //Aqui el parametro son los datos que le envian desde el FormuClientes.
    public void AgregarCliente(JTextField nombres,JTextField appaterno, JTextField apmaterno) {
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        ModelosUsu.ModeloCliente objetoCliente = new ModelosUsu.ModeloCliente();
        
        //Esta es una consulta preparada donde los ? son marcadores de posición que serán reemplazados por los valores reales 
        String consulta = "insert into cliente (nombres,appaterno,apmaterno) values (?,?,?)";
        
        try {
            /*aqui agarramos el texto que se digito en cada campo y se le asigna al objeto cleinte. con el set establecemos el valor del 
            atributo nombres(Parametro) en el objeto cliente y con el get obtenesmo el texto que el susuario digitó*/
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setApPaterno(appaterno.getText());
            objetoCliente.setApMaterno(apmaterno.getText());
            
            //Con el callableStament podemos comunicarnos con la base de datos, y al preparecall le asiganmos consulta que fue lo que definimos anteriormente. 
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            /*hacemos uso de el objeto para realizar las consultas y con el set asignamos el tipo de valor que se va a asignar, ya el numero
            es la posicion de izquiera a derecha segun la tabla, y objetoCliente.getNombres() aqui se devuelve el valor que fue asignado anteriormente(Linea 87)*/
            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());
            
            //hacemos que la accion suceda en la Base de Datos
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"se guardo correctamente");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al guardarse: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
        
    }
    //recibe cinco parametros enviados desde FormuClientes
    public void Seleccionar(JTable totalcliente, JTextField id, JTextField nombres, JTextField appaterno, JTextField appmaterno){
        //totalcliente.getSelectedRow() es un método del objeto JTable que devuelve el índice o numero de la fila que el usuario ha seleccionado en la tabla.
        int fila = totalcliente.getSelectedRow();
        //aqui no utlizamos finally porque no conlleva una consulta
        try {
            //en caso tal de que no haya consultas fila=-1, por eso miramos que la fila sea mayorigual que 0 para ejecutar el codigo
            if(fila>=0){
                /*totalcliente.getValueAt(fila, 0) devuelve un objeto (representa el valor de la celda), este objeto el toString lo convierte en una
                cadena de texto, finalmente con el id.settext se muestra el id del cliente seleccionado en el campo id tambien gracias al 0*/
                id.setText(totalcliente.getValueAt(fila, 0).toString());
                nombres.setText(totalcliente.getValueAt(fila, 1).toString());
                appaterno.setText(totalcliente.getValueAt(fila, 2).toString());
                appmaterno.setText(totalcliente.getValueAt(fila, 3).toString());

            
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar: "+ e.toString());
        }
            
        
    }
    //este es casi el mismo codigo de arriba pero se cambia el orden de los parametros mas abajo porque asi lo requiere la consulta
    public void ModificarCliente(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno){
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        ModelosUsu.ModeloCliente objetoCliente = new ModelosUsu.ModeloCliente();
        
        String consulta = "update cliente SET cliente.nombres = ?, cliente.appaterno = ? , cliente.apmaterno = ? where cliente.idcliente = ?;  ";
        
        try {
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setApPaterno(appaterno.getText());
            objetoCliente.setApMaterno(apmaterno.getText());
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            
            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());
            cs.setInt(4, objetoCliente.getIdCliente());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se actualizo correctamente: "+e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
    public void LimpiarCamposclientes(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno){
        id.setText("");
        nombres.setText("");
        appaterno.setText("");
        apmaterno.setText("");
        
    }
    
    public void EliminarClientes(JTextField id){
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        ModelosUsu.ModeloCliente objetoCliente = new ModelosUsu.ModeloCliente();
        
        String consulta = "delete from cliente where cliente.idcliente=?;";
        
        try {
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, objetoCliente.getIdCliente());
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se elimino correctamente");

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se elimino correctamente: "+ e.toString());
        } finally {
        }
                
                
    }
}
