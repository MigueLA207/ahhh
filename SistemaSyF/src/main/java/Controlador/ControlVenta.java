/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
//Ejecuta consultas de sql de manera segura y eficiente

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
//utilizada para gestionar datos de una Jtable
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author maria
 */
public class ControlVenta {

    //                       objeto que contiene nombre / JTable donde se mostraran los resultados     
    public void BuscarProducto(JTextField nombreProducto, JTable tablaproductos) {

        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        ModelosUsu.ModeloProducto objetoProducto = new ModelosUsu.ModeloProducto();

        //Creamos un nuevo modelo de la tabla, esto se utiliza para definir las columnas que hay en la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("precioProducto");
        modelo.addColumn("Stock");

        //Aqui asignamos el modelo que creamos atras a la tablaproductos en la cual se establecieron las columnas
        tablaproductos.setModel(modelo);
        //SIEMPRE UTILIZAMOS TRY CATCH PARA MANEJAR LOS POSIBLES ERRORES DURANTE LA INTERACCION CON LA BASE DE DATOS
        try {
            //Define la consulta SQL para buscar productos.
            String consulta = "select*from producto where producto.nombre like concat('%',?,'%');";

            //Lo que nos permite hacer la consulta esel PreparedStament, creamos el objeto que se va a utilizar despues.
            //prepareStatement(consulta); toma como argumento una cadena String, El objetivo de PrepareStament es preparar la consulta sql para la ejecucion.
            PreparedStatement ps = objetoConexion.establecerConexion().prepareStatement(consulta);

            //asignamos el texto que el usuario ingresó en el campo nombreProducto
            ps.setString(1, nombreProducto.getText());
            //aqui ejecutamos la consulta sql y la guardamos en un objeto Resulset llamado rs, y es ExecuteQuery porque solo ejecuta consultas de tipo SELECT
            ResultSet rs = ps.executeQuery();

            //iteramos sobre cada fila del resultset, con el metodo next nos vamos moviendo entre filas.
            while (rs.next()) {
                //aqui en idProducto va a ver un dato, se extrae con Get y se guarda en la fila que estamos del rs, y lo establece en el atributo idproducto
                objetoProducto.setIdProducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));
                objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
                objetoProducto.setStockProducto(rs.getInt("stock"));

                //despues de recuperar los datos del rs, vamos a crear una nueva linea la cual va a almacenar los datos recuperados
                modelo.addRow(new Object[]{
                    objetoProducto.getIdProducto(),
                    objetoProducto.getNombreProducto(),
                    objetoProducto.getPrecioProducto(),
                    objetoProducto.getStockProducto()
                });

            }
            //asignamos el modelo ya llenado a la tablaproductos
            tablaproductos.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error no muestra" + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
        //Codigo de aqui para inhabilitar la edicion de los campos, (?) clase de cualquier tipo.(StackOverflow osea ni idea de como funciona, solo funciona)
        for (int column = 0; column < tablaproductos.getColumnCount(); column++) {
            Class<?> columClass = tablaproductos.getColumnClass(column);
            tablaproductos.setDefaultEditor(columClass, null);
        }

    }

    //Método para seleccionar un producto de la tabla y mostrar sus detalles en los campos correspondientes
    public void seleccionarProductoVenta(JTable tablaProducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField Stock, JTextField PrecioFinal) {
        //Obtenemos el índice de la fila seleccionada en la tabla
        int fila = tablaProducto.getSelectedRow();
        try {

            if (fila >= 0) {
                //Si hay una fila seleccionada, extraemos los datos de cada columna y los asignamos a los campos correspondientes
                id.setText(tablaProducto.getValueAt(fila, 0).toString());
                nombres.setText(tablaProducto.getValueAt(fila, 1).toString());
                precioProducto.setText(tablaProducto.getValueAt(fila, 2).toString());
                Stock.setText(tablaProducto.getValueAt(fila, 3).toString());
                PrecioFinal.setText(tablaProducto.getValueAt(fila, 2).toString());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de seleccion" + e.toString());
        }

    }

    //                    Objeto que contiene el nombre/ tabla donde se van a mostrar los resultados             
    public void BuscarCliente(JTextField nombreCliente, JTable tablaClientes) {
        //realizamos las debidas conexiones
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        ModelosUsu.ModeloCliente objetoCliente = new ModelosUsu.ModeloCliente();

        //creamos el modelo de la tabla y le asignamos las columnas
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Primer Apellido");
        modelo.addColumn("Segundo Apellido");

        // ↑↑↑ le asignamos el modelo anterior a la tablaClientes
        tablaClientes.setModel(modelo);

        try {
            String consulta = "select*from cliente where cliente.nombres like concat('%',?,'%');";

            //Lo que nos permite hacer la consulta es el PreparedStament, creamos el objeto que se va a utilizar despues.
            //prepareStatement(consulta); toma como argumento una cadena String, El objetivo de PrepareStament es preparar la consulta sql para la ejecucion.
            PreparedStatement ps = objetoConexion.establecerConexion().prepareStatement(consulta);
            //asignamos el texto que el usuario ingresó en el campo nombresCliente
            ps.setString(1, nombreCliente.getText());
            //aqui ejecutamos la consulta sql y la guardamos en un objeto Resulset llamado rs, y es ExecuteQuery porque solo ejecuta consultas de tipo SELECT
            ResultSet rs = ps.executeQuery();

            //iteramos sobre cada fila del resultset, con el metodo next nos vamos moviendo entre filas.
            while (rs.next()) {
                //aqui en idProducto va a ver un dato, se extrae con Get y se guarda en la fila que estamos del rs, y lo establece en el atributo idproducto
                objetoCliente.setIdCliente(rs.getInt("idcliente"));
                objetoCliente.setNombres(rs.getString("nombres"));
                objetoCliente.setApPaterno(rs.getString("appaterno"));
                objetoCliente.setApMaterno(rs.getString("apmaterno"));

                //despues de recuperar los datos del rs, vamos a crear una nueva linea la cual va a almacenar los datos recuperados
                modelo.addRow(new Object[]{
                    objetoCliente.getIdCliente(),
                    objetoCliente.getNombres(),
                    objetoCliente.getApPaterno(),
                    objetoCliente.getApMaterno()
                });

            }
            //asignamos el modelo ya llenado a la tablaproductos
            tablaClientes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error, no muestra: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }

    }

    //Método para seleccionar un cliente de la tabla y mostrar sus datos en los campos correspondientes
    public void seleccionarClienteVenta(JTable tablaCliente, JTextField id, JTextField nombres, JTextField apPaterno, JTextField apMaterno) {
        //Obtenemos el índice de la fila seleccionada en la tabla
        int fila = tablaCliente.getSelectedRow();
        try {

            if (fila >= 0) {
                //Si hay una fila seleccionada, extraemos los datos de cada columna y los asignamos a los campos correspondientes
                id.setText(tablaCliente.getValueAt(fila, 0).toString());
                nombres.setText(tablaCliente.getValueAt(fila, 1).toString());
                apPaterno.setText(tablaCliente.getValueAt(fila, 2).toString());
                apMaterno.setText(tablaCliente.getValueAt(fila, 3).toString());

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de seleccion" + e.toString());
        }
    }

    //Método para agregar productos seleccionados a la tabla de resumen de venta
    public void pasarProductosVenta(JTable tablaResumen, JTextField idproducto, JTextField nombreProducto, JTextField precioProducto, JTextField cantidadVenta, JTextField stock) {
        //Obtenemos el modelo actual de la tabla de resumen
        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();

        //Convertimos los valores de texto a tipos numéricos para trabajar con ellos
        int stockDisponible = Integer.parseInt(stock.getText());
        String idProducto = idproducto.getText();

        //Verificamos si el producto ya está agregado en la tabla de resumen
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String idExistente = (String) modelo.getValueAt(i, 0);
            if (idExistente.equals(idProducto)) {
                JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                return;

            }

        }
        //Obtenemos el resto de datos del producto para agregarlo a la tabla
        String nombProducto = nombreProducto.getText();
        double precioUnitario = Double.parseDouble(precioProducto.getText());
        int cantidad = Integer.parseInt(cantidadVenta.getText());

        //Verificamos que la cantidad a vender no sea mayor al stock disponible
        if (cantidad > stockDisponible) {
            JOptionPane.showMessageDialog(null, "La cantidad de venta no puede ser mayor al stock disponible");
            return;
        }

        //Calculamos el subtotal (precio * cantidad) y agregamos la fila a la tabla de resumen
        double subTotal = precioUnitario * cantidad;
        modelo.addRow(new Object[]{idProducto, nombProducto, precioUnitario, cantidad, subTotal});
    }

    //Método para eliminar un producto seleccionado de la tabla de resumen de venta
    public void eliminarProductosRV(JTable tablaResumen) {
        try {
            //Obtenemos el modelo de la tabla y el índice de la fila seleccionada
            DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
            int indiceSeleccionado = tablaResumen.getSelectedRow();

            //Si hay una fila seleccionada (-1 indica que no hay selección), la eliminamos
            if (indiceSeleccionado != -1) {
                modelo.removeRow(indiceSeleccionado);
            } else {
                JOptionPane.showMessageDialog(null, "Error al seleccionar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar" + e.toString());
        }
    }

    //Método para calcular el total a pagar, sumando subtotales y aplicando IVA
    public void calcularTotalPagar(JTable tablaResumen, JLabel IVA, JLabel totalPagar) {
        //Obtenemos el modelo de la tabla de resumen
        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();

        //Inicializamos variables para cálculos
        double totalSubtotal = 0;
        double iva = 0.18; //IVA del 18%
        double totaliva = 0;

        //Formato para redondear a dos decimales
        DecimalFormat formato = new DecimalFormat("#.##");

        //Recorremos todas las filas de la tabla sumando los subtotales
        for (int i = 0; i < modelo.getRowCount(); i++) {
            totalSubtotal = Double.parseDouble(formato.format(totalSubtotal + (double) modelo.getValueAt(i, 4)));
            totaliva = Double.parseDouble(formato.format(iva * totalSubtotal));
        }

        //Actualizamos los textos de los labels con los totales calculados
        totalPagar.setText(String.valueOf(totalSubtotal + totaliva));
        IVA.setText(String.valueOf(totaliva));
    }

    //Método para crear una nueva factura en la base de datos
    public void crearFactura(JTextField codCliente) {
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();
        ModelosUsu.ModeloCliente objetoCliente = new ModelosUsu.ModeloCliente();
        //Consulta SQL para insertar una nueva factura con la fecha actual y el ID del cliente
        String consulta = "INSERT INTO factura(fechaFactura, fkcliente) values (curdate(),?);";

        try {
            //Establecemos el ID del cliente en el modelo
            objetoCliente.setIdCliente(Integer.parseInt(codCliente.getText()));

            //Preparamos y ejecutamos la consulta con un CallableStatement
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, objetoCliente.getIdCliente());

            cs.execute();

            JOptionPane.showMessageDialog(null, "se ha creado la factura");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear factura" + e.toString());
        } finally {
            //Cerramos la conexión para liberar recursos
            objetoConexion.cerrarConexion();
        }

    }

    //Método para registrar los detalles de la venta y actualizar el inventario
    public void realizarVenta(JTable tablaResumenVenta) {
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();

        //Consulta SQL para insertar detalles de venta, asociando con la última factura creada
        String consultaDetalle = "INSERT INTO detalle (fkfactura, fkproducto, cantidad, precioVenta) values ((SELECT max(idfactura)from factura),?,?,?);";

        //Consulta SQL para actualizar el stock de los productos vendidos
        String consultaStock = "UPDATE producto SET producto.stock = stock - ? WHERE idproducto = ?;";

        try {
            //Preparamos ambas consultas
            PreparedStatement psDetalle = objetoConexion.establecerConexion().prepareStatement(consultaDetalle);
            PreparedStatement psStock = objetoConexion.establecerConexion().prepareStatement(consultaStock);

            //Obtenemos el número de filas en la tabla de resumen (productos a vender)
            int filas = tablaResumenVenta.getRowCount();

            //Procesamos cada producto de la venta
            for (int i = 0; i < filas; i++) {
                //Extraemos los datos necesarios de cada fila
                int idProducto = Integer.parseInt(tablaResumenVenta.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(tablaResumenVenta.getValueAt(i, 3).toString());
                double precioVenta = Double.parseDouble(tablaResumenVenta.getValueAt(i, 2).toString());

                //Insertamos el detalle de venta en la base de datos
                psDetalle.setInt(1, idProducto);
                psDetalle.setInt(2, cantidad);
                psDetalle.setDouble(3, precioVenta);
                psDetalle.executeUpdate();

                //Actualizamos el stock del producto vendido
                psStock.setInt(1, cantidad);
                psStock.setInt(2, idProducto);
                psStock.executeUpdate();

            }

            JOptionPane.showMessageDialog(null, "Venta Realizada");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la Venta" + e.toString());
        } finally {
            //Cerramos la conexión para liberar recursos
            objetoConexion.cerrarConexion();
        }
    }

    //Método para limpiar todos los campos y tablas después de una venta o al iniciar una nueva
    public void LimpiarCampos(JTextField buscarClientes, JTable tablaCliente, JTextField buscarProducto, JTable tablaProducto,
            JTextField selectIdCliente, JTextField selectNombreCliente, JTextField selectApPaterno, JTextField selectApMaterno,
            JTextField selectIdProducto, JTextField selectNombreProducto, JTextField selectStockProducto, JTextField selectPrecioProducto,
            JTextField precioVenta, JTextField cantidadVenta, JTable tablaResumen, JLabel IVA, JLabel total) {

        //Limpiamos y enfocamos el campo de búsqueda de clientes
        buscarClientes.setText("");
        buscarClientes.requestFocus();

        //Limpiamos la tabla de clientes
        DefaultTableModel modeloCliente = (DefaultTableModel) tablaCliente.getModel();
        modeloCliente.setRowCount(0);

        //Limpiamos y enfocamos el campo de búsqueda de productos
        buscarProducto.setText("");
        buscarProducto.requestFocus();
        //Limpiamos la tabla de productos
        DefaultTableModel modeloProducto = (DefaultTableModel) tablaProducto.getModel();
        modeloProducto.setRowCount(0);

        //Limpiamos todos los campos del cliente seleccionado
        selectIdCliente.setText("");
        selectNombreCliente.setText("");
        selectApPaterno.setText("");
        selectApMaterno.setText("");

        //Limpiamos todos los campos del producto seleccionado
        selectIdProducto.setText("");
        selectNombreProducto.setText("");
        selectStockProducto.setText("");
        selectPrecioProducto.setText("");

        //Limpiamos y deshabilitamos el campo de precio de venta
        precioVenta.setText("");
        precioVenta.setEnabled(false);

        //Limpiamos el campo de cantidad
        cantidadVenta.setText("");

        //Limpiamos la tabla de resumen de venta
        DefaultTableModel modeloResumenVenta = (DefaultTableModel) tablaResumen.getModel();
        modeloResumenVenta.setRowCount(0);

        //Reiniciamos los labels de IVA y total
        IVA.setText("****");
        total.setText("****");

    }

    //Método para obtener y mostrar el número de la última factura generada
    public void numerofactura(JLabel ultimaFactura) {
        ConfiguracionBD.ConexionDB objetoConexion = new ConfiguracionBD.ConexionDB();

        try {
            //Consulta SQL para obtener el ID de la última factura registrada
            String consulta = "SELECT max(idfactura) as ultimaFactura from factura";

            //Preparamos y ejecutamos la consulta
            PreparedStatement ps = objetoConexion.establecerConexion().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();

            //Si hay resultado, actualizamos el label con el número de factura
            if (rs.next()) {
                ultimaFactura.setText(String.valueOf(rs.getInt("ultimaFactura")));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar la ultima factura: " + e.toString());
        } finally {
            //Cerramos la conexión para liberar recursos
            objetoConexion.cerrarConexion();
        }
    }
}
