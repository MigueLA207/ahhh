/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ControlProducto {
    public void MostrarProductos(JTable tablaTotalProductos) {
        // Creamos un objeto para establecer conexión con la base de datos
        ConfiguracionBD.ConexionDB objetoConexionDB = new ConfiguracionBD.ConexionDB();

        // Creamos un objeto del modelo producto para almacenar temporalmente los datos
        ModelosUsu.ModeloProducto objetoProducto = new ModelosUsu.ModeloProducto();

        // Creamos un modelo para la tabla donde definiremos su estructura
        DefaultTableModel modelo = new DefaultTableModel();

        String sql = "";
        //con esto le asigno las columnas al modelo
        modelo.addColumn("id");
        modelo.addColumn("Nombre Producto");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");

        //Aqui a la tabla total productos le asignamos el modelo que definimos anteriormente
        tablaTotalProductos.setModel(modelo);

        //instruccion para hacer la consulta de los productos, con el select decimos que queremos recuperar datos "From" osea desde la tabla productos
        sql = "select producto.idproducto, producto.nombre, producto.precioProducto, producto.stock from producto;   ";

        //Asi se ejecuten try y catch tiene que pasar algo, si o si. 
        try {
            /*aqui lo que hacemos es abrir una conexion con la base de datos y con el metodo que tiene el Statement (createStatement();) nos permite crear 
            un objeto de este tipo el cual nos permite enviar la instruccion a la base de datos este resultado se va a guardar en un objeto Resulset*/
            Statement st = objetoConexionDB.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            //Aqui ya con el rs y el .next vamos a navegar entre toda la tabla productos y vamos a obtener los valores con los getInt y getString
            while (rs.next()) {
                // Asignamos los valores de cada columna al objeto producto
                objetoProducto.setIdProducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));
                objetoProducto.setStockProducto(rs.getInt("stock"));
                objetoProducto.setPrecioProducto(rs.getDouble("precioproducto"));

                // Añadimos una nueva fila al modelo con los datos del producto actual
                modelo.addRow(new Object[]{
                    objetoProducto.getIdProducto(),
                    objetoProducto.getNombreProducto(),
                    objetoProducto.getPrecioProducto(),
                    objetoProducto.getStockProducto()
                });
            }
            tablaTotalProductos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error almostrar usuarios, " + e.toString());
        } finally {
            // Independientemente del resultado, cerramos la conexión a la base de datos
            objetoConexionDB.cerrarConexion();
        }
    }
    
    //Aqui el parametro son los datos que le envian desde el FormuProducto.
    public void AgregarProducto(JTextField nombre,JTextField precioProducto, JTextField stock) {
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        ModelosUsu.ModeloProducto objetoProducto = new ModelosUsu.ModeloProducto();
        
        //Esta es una consulta preparada donde los ? son marcadores de posición que serán reemplazados por los valores reales 
        String consulta = "insert producto (nombre,precioProducto,stock)values(?,?,?);";
        
        try {
            /*aqui agarramos el texto que se digito en cada campo y se le asigna al objeto producto. con el set establecemos el valor del 
            atributo nombre(Parametro) en el objeto producto y con el get obtenesmo el texto que el susuario digitó*/
            objetoProducto.setNombreProducto(nombre.getText());
            objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));
            
            //Con el callableStament podemos comunicarnos con la base de datos, y al preparecall le asiganmos consulta que fue lo que definimos anteriormente. 
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            /*hacemos uso de el objeto para realizar las consultas y con el set asignamos el tipo de valor que se va a asignar, ya el numero
            es la posicion de izquiera a derecha segun la tabla, y objetoProductos.getNombres() aqui se devuelve el valor que fue asignado anteriormente(Linea 82)*/
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());
            
            //hacemos que la accion suceda en la Base de Datos
            cs.execute();
            
            JOptionPane.showMessageDialog(null,"se guardo correctamente");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al guardarse: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
        
    }
    
    //recibe cinco parametros enviados desde FormuProductos
    public void Seleccionar(JTable totalproducto, JTextField id, JTextField nombreProducto, JTextField precioProducto, JTextField stock){
        //totalProducto.getSelectedRow() es un método del objeto JTable que devuelve el índice o numero de la fila que el usuario ha seleccionado en la tabla.
        int fila = totalproducto.getSelectedRow();
        //aqui no utlizamos finally porque no conlleva una consulta
        try {
            //en caso tal de que no haya consultas fila=-1, por eso miramos que la fila sea mayorigual que 0 para ejecutar el codigo
            if(fila>=0){
                /*totalProducto.getValueAt(fila, 0) devuelve un objeto (representa el valor de la celda), este objeto el toString lo convierte en una
                cadena de texto, finalmente con el id.settext se muestra el id del cliente seleccionado en el campo id tambien gracias al 0*/
                id.setText(totalproducto.getValueAt(fila, 0).toString());
                nombreProducto.setText(totalproducto.getValueAt(fila, 1).toString());
                precioProducto.setText(totalproducto.getValueAt(fila, 2).toString());
                stock.setText(totalproducto.getValueAt(fila, 3).toString());

            
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar: "+ e.toString());
        }
            
        
    }
    
    //este es casi el mismo codigo de arriba pero se cambia el orden de los parametros mas abajo porque asi lo requiere la consulta
    public void ModificarProducto(JTextField id, JTextField nombre, JTextField precioProducto, JTextField stock){
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        ModelosUsu.ModeloProducto objetoProducto = new ModelosUsu.ModeloProducto();
        
        String consulta = "update producto set producto.nombre=?, producto.precioProducto=?,producto.stock=? where producto.idproducto=?;";
        
        try {
            objetoProducto.setIdProducto(Integer.parseInt(id.getText()));
            objetoProducto.setNombreProducto(nombre.getText());
            objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());
            cs.setInt(4, objetoProducto.getIdProducto());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se actualizo correctamente: "+e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
    
    public void LimpiarCamposProducto(JTextField id, JTextField nombre, JTextField precioProducto, JTextField stock){
        id.setText("");
        nombre.setText("");
        precioProducto.setText("");
        stock.setText("");
        
    }
    
    public void EliminarProducto(JTextField id){
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        ModelosUsu.ModeloProducto objetoProducto = new ModelosUsu.ModeloProducto();
        
        String consulta = "delete from producto where producto.idproducto=?";
        
        try {
            objetoProducto.setIdProducto(Integer.parseInt(id.getText()));
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, objetoProducto.getIdProducto());
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se elimino correctamente");

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se elimino correctamente: "+ e.toString());
        } finally {
        }
                
                
    }
}
