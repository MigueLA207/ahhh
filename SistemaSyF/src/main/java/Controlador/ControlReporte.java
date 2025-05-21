/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

//Importamos las clases necesarias para manejar consultas SQL, formatos de números y componentes gráficos
import com.toedter.calendar.JDateChooser;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author maria
 */
public class ControlReporte {

    //Método para buscar y mostrar los datos del cliente asociados a una factura específica
    public void bucarDatosCliente(JTextField numeroFactura, JLabel numeroFacturaEncontrado, JLabel fechaFactura, JLabel nombreCliente, JLabel apPaterno, JLabel apMaterno) {
        //Creamos el objeto de conexión para interactuar con la base de datos
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();

        try {
            //Consulta SQL con JOIN para obtener datos de factura y cliente en una sola consulta
            String consulta = "SELECT factura.idfactura, factura.fechaFactura, cliente.nombres, cliente.appaterno, cliente.apmaterno from factura "
                    + "INNER JOIN cliente ON cliente.idcliente = fkcliente WHERE factura.idfactura = ?;";

            //Preparamos la consulta y asignamos el número de factura ingresado como parámetro
            PreparedStatement ps = objetoConexion.establecerConexion().prepareStatement(consulta);
            ps.setInt(1, Integer.parseInt(numeroFactura.getText()));

            //Ejecutamos la consulta y recibimos los resultados
            ResultSet rs = ps.executeQuery();

            //Verificamos si hay resultados para la factura buscada
            if (rs.next()) {
                //Si existe la factura, mostramos los datos en los JLabel correspondientes
                numeroFacturaEncontrado.setText(String.valueOf(rs.getInt("idfactura")));
                fechaFactura.setText(rs.getDate("fechaFactura").toString());
                nombreCliente.setText(rs.getString("nombres"));
                apPaterno.setText(rs.getString("appaterno"));
                apMaterno.setText(rs.getString("apmaterno"));

            } else {
                //Si no existe la factura, limpiamos todos los campos y mostramos un mensaje
                numeroFacturaEncontrado.setText("");
                fechaFactura.setText("");
                nombreCliente.setText("");
                apPaterno.setText("");
                apMaterno.setText("");

                JOptionPane.showMessageDialog(null, "no se encontro la factura");
            }

        } catch (Exception e) {
            //Capturamos cualquier error que ocurra durante la consulta
            JOptionPane.showMessageDialog(null, "No se encontro la factura" + e.toString());
        } finally {
            //Aquí debería cerrarse la conexión, pero falta implementar ese código
        }
    }

    //Método para mostrar los productos detallados de una factura específica y calcular totales
    public void mostrarDatosfactura(JTextField numeroFactura, JTable tablaProductos, JLabel IVA, JLabel total) {
        //Creamos el objeto de conexión para interactuar con la base de datos
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        //Creamos un nuevo modelo para la tabla donde mostraremos los productos
        DefaultTableModel modelo = new DefaultTableModel();

        //Definimos las columnas que tendrá la tabla
        modelo.addColumn("N.Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("PrecioVenta");
        modelo.addColumn("Subtotal");

        //Asignamos el modelo a la tabla de productos
        tablaProductos.setModel(modelo);

        try {
            //Consulta SQL con múltiples JOIN para obtener detalles de productos en la factura
            String consulta = "SELECT producto.nombre, detalle.cantidad, detalle.precioVenta from detalle "
                    + "INNER JOIN factura ON factura.idfactura = detalle.fkfactura "
                    + "INNER JOIN producto ON producto.idproducto = detalle.fkproducto "
                    + "where factura.idfactura=?;";

            //Preparamos la consulta y asignamos el número de factura como parámetro
            PreparedStatement ps = objetoConexion.establecerConexion().prepareStatement(consulta);
            ps.setInt(1, Integer.parseInt(numeroFactura.getText()));

            //Ejecutamos la consulta y recibimos los resultados
            ResultSet rs = ps.executeQuery();

            //Inicializamos variables para cálculos financieros
            double totalFactura = 0.0;
            double valorIVA = 0.19; //IVA del 19%
            //Formato para mostrar números con dos decimales
            DecimalFormat formato = new DecimalFormat("#.##");

            //Iteramos sobre cada producto en el resultado de la consulta
            while (rs.next()) {
                //Extraemos los datos de cada fila del resultado
                String nombreProducto = rs.getString("nombre");
                int Cantidad = rs.getInt("cantidad");
                double precioVenta = rs.getDouble("precioVenta");
                //Calculamos el subtotal para cada producto (cantidad * precio)
                double subtotal = Cantidad * precioVenta;

                //Acumulamos el total de la factura y formateamos a dos decimales
                totalFactura = Double.parseDouble(formato.format(totalFactura + subtotal));

                //Agregamos cada producto como una fila en la tabla
                modelo.addRow(new Object[]{nombreProducto, Cantidad, precioVenta, subtotal});

            }

            //Calculamos el IVA sobre el total y lo formateamos a dos decimales
            double totalIVA = Double.parseDouble(formato.format(totalFactura * valorIVA));

            //Actualizamos los labels de IVA y total con los valores calculados
            IVA.setText(String.valueOf(totalIVA));
            total.setText(String.valueOf(totalFactura));

        } catch (Exception e) {
            //Capturamos cualquier error que ocurra durante la consulta o procesamiento
            JOptionPane.showMessageDialog(null, "Error al mostrar los productos: " + e.toString());
        } finally {
            //Cerramos la conexión para liberar recursos
            objetoConexion.cerrarConexion();
        }
    }
    
    public void mostrarTotalVentaFecha (JDateChooser desde,JDateChooser hasta, JTable tablaventas, JLabel totalGeneral){
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("idfactura");
        modelo.addColumn("FechaFactura");
        modelo.addColumn("NProducto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precioventa");
        modelo.addColumn("Subtotal");
        
        tablaventas.setModel(modelo);
        
        try {
            String consulta = "SELECT factura.idfactura, factura.fechaFactura, producto.nombre, detalle.cantidad, detalle.precioVenta " +
                  "FROM detalle " +
                  "INNER JOIN factura ON factura.idfactura = detalle.fkfactura " +
                  "INNER JOIN producto ON producto.idproducto = detalle.fkproducto " +
                  "WHERE factura.fechaFactura BETWEEN ? AND ?";
            
            PreparedStatement ps = objetoConexion.establecerConexion().prepareStatement(consulta);
            
            java.util.Date fechaDesde = desde.getDate();
            java.util.Date fechaHasta = hasta.getDate();
            
            java.sql.Date fechaDesdeSql = new java.sql.Date(fechaDesde.getTime());
            java.sql.Date fechaHastaSql = new java.sql.Date(fechaHasta.getTime());

            ps.setDate(1, fechaDesdeSql);
            ps.setDate(2, fechaHastaSql);
            
            ResultSet rs = ps.executeQuery();
            double totalFactura = 0.0;
            DecimalFormat formato = new DecimalFormat("#.##");
            while(rs.next()){
                int idFactura = rs.getInt("idfactura");
                Date fechaFactura = rs.getDate("fechaFactura");
                String nombreProducto = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double precioVenta = rs.getDouble("precioVenta");
                double subtotal = cantidad * precioVenta;
                
                totalFactura = Double.parseDouble(formato.format(totalFactura+subtotal));
                
                modelo.addRow(new Object[] {idFactura, fechaFactura, nombreProducto, cantidad, precioVenta, subtotal});
            }
            totalGeneral.setText(String.valueOf(totalFactura));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error albuscar los ingresos por fechas: "+ e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
        
        for (int column = 0; column < tablaventas.getColumnCount();column++){
            Class<?> columClass = tablaventas.getColumnClass(column);
            tablaventas.setDefaultEditor(columClass, null);
        }
    }
}
