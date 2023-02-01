package primerapracticaipc1;

//No se utilizo porque en la factura se colocó la fecha 10 de febrero por default
// import java.util.Calendar;

import java.util.Scanner;

public class  PrimeraPracticaIPC1{
    
    //constantes que almacenan las credenciales para el inicio de sesion
    final static String usuario = "cajero_202200349";
    final static String contraseña = "ipc1_202200349";
    
    //scanner
    static Scanner consola = new Scanner(System.in);

    //Variable contador utilizada para llevar el control en el vector productos
    static int contadorProductos = 0;
    static int contadorCupones = 0;

    //Array para prodcutos columnas: id Nombre Precio cantidadVendida
    static String[][] productos = new String[20][4];
    //Array para cupones columnas: id Codigo Porcentaje
    static String[][] cupones = new String[20][3];
    //Array para almacenar datos temporales de la factura
    static String factura[][] = new String[20][4];

    //funcion principal
    public static void main(String[] args) {
        boolean verificacion;
        
        /*funcion para rellenar todas las filas de la columna cantidad vendida 
        del arreglo productos con ceros para no tener problema con la funcion que ordena el arreglo el resto se 
        djan vacias como null*/
        rellenarCeros();

        System.out.println("------Sistema de ventas-------\n");

        // Ciclo para verificar credenciales de inicio de sesion
        while (true) {
            verificacion = inicioDeSesión();

            if (verificacion == true) {
                System.out.println("\n********Bienvenido********");
                mostrarMenu();
                break;
            } else {
                System.out.println("\n*Credenciales incorrectas, intentelo nuevamente\n");
            }

        }

    }

    //verificar credenciales del usuario
    public static boolean inicioDeSesión() {
        String user;
        String pass;

        System.out.print("Ingresa el nombre de usuario: ");
        user = consola.nextLine();
        System.out.print("Ingresa la contraseña: ");
        pass = consola.nextLine();

        if (user.equals(usuario) && pass.equals(contraseña)) {
            return true;
        } else {
            return false;
        }
    }

    //Mostrar menu y seleccionar la opcion a la que se desea ingresar
    public static void mostrarMenu() {
        String opcion;
        boolean condicion = false;

        while (condicion == false) {

            //imprimir texto
            System.out.println("\n----------Menu----------");
            System.out.println("1. Agregar nuevos productos.");
            System.out.println("2. Agregar cupones de descuento.");
            System.out.println("3. Realizar venta.");
            System.out.println("4. Realizar reporte");
            System.out.println("5. Salir del programa");

            //leer opcion elegida por el usuario
            System.out.print("\nIngrese el *numero* de la opción a la que desea ingresar: ");
            opcion = consola.next();

            //enviar al usuario al modulo seleccionado
            if (opcion.equals("1")) {
                mostrarProductos();
                condicion = true;
            } else if (opcion.equals("2")) {
                mostrarCupones();
                condicion = true;
            } else if (opcion.equals("3")) {
                realizarVenta();
                condicion = true;
            } else if (opcion.equals("4")) {
                realizarReporte();
                condicion = true;
            } else if (opcion.equals("5")) {
                System.out.println("\nSaliendo del programa...");
                System.exit(0);
                condicion = true;
            } else {
                System.out.println("\n*Por favor selecciona una opcion correcta.");
            }
        }
    }
    
    public static void mostrarProductos() {

        boolean condicion = true;
        boolean existe = false;
        String nombre;
        double precio;

        //Ciclo que se repetira hasta que el usuario desee salir o hasta que se agregue un producto correctamente
        while (condicion == true) {

            //imprimir texto
            System.out.println("\n------Agregar producto------");
            System.out.println("*Si desea volver al menu deje el campo nombre en blanco y el precio en 0\n");

            //leer nombre y precio del producto
            System.out.print("Ingrese el nombre del producto: ");
            //limpiando el scanner
            consola.nextLine();
            nombre = consola.nextLine();
            System.out.print("Ingrese el precio del producto (unicamente valor numerico sin separador de miles): ");
            precio = consola.nextDouble();

            //Verificando si el usuario desea salir
            if (nombre.equals("") && precio == 0) {
                condicion = false;
                System.out.println("\n**Volviendo al menu...");
                mostrarMenu();
                break;
                //Verificando que el precio no sea 0 y que ambos campos tengan texto   
            } else if (nombre.equals("") || precio == 0) {

                System.out.println("\n***Ambos campos deben tener informacion y el precio no puede ser 0");

                //Si ambos campos tienen datos y el usuario no desea salir, verificar para agregar el nuevo producto    
            } else {

                //ciclo para revisar que el producto ingresado no exista aun en el vector
                for (int i = 0; i <= 19; i++) {

                    if (nombre.equals(productos[i][1])) {
                        System.out.println("\n**El producto ya existía en el sistema por lo que NO se agrego");
                        existe = true;
                        break;
                    } else {
                        existe = false;
                    }
                }

                //Si el producto no existe aun, agregarlo
                if (existe == false) {
                    productos[contadorProductos][0] = Integer.toString(contadorProductos);
                    productos[contadorProductos][1] = nombre;
                    productos[contadorProductos][2] = Double.toString(precio);
                    productos[contadorProductos][3] = "0";

                    //Se cambia el estado de la condicion que mantiene al bucle ejecutandose
                    condicion = false;
                    //se agrega una unidad mas al contador
                    contadorProductos = contadorProductos + 1;
                    System.out.println("\n**Producto agregado correctamente. Volviendo al menu");
                    //Imprimiendo tabla con los productos que existen actualmente
                    imprimirProductosSinCantidad();
                    //volviendo al menu
                    mostrarMenu();

                }

            }

        }

    }

    public static void mostrarCupones() {
        boolean condicion = true;
        boolean caracteres;
        boolean existe = false;
        String cupon;
        double descuento;
        double decimal;

        //Ciclo que se repetira hasta que el usuario desee salir o hasta que se agregue un cupon valido
        while (condicion == true) {
            System.out.println("\n------Agregar cupon------");
            System.out.println("*Si desea volver al menu coloque un numero 0 en el campo cupon y porcentaje\n");

            //leer nombre y porcentaje del cupon
            System.out.print("Ingrese el codigo del cupon (unicamente 4 caracteres): ");
            //limpiando el scanner
            consola.nextLine();
            cupon = consola.nextLine();
            System.out.print("Ingrese el valor numerico (Entre 0 y 100) del porcentaje de descuento que otorga este cupon (sin simbolo %): ");
            descuento = consola.nextDouble();

            //verificando restricciones
            if (cupon.equals("0") && descuento == 0) {
                condicion = false;
                System.out.println("\n**Volviendo al menu...");
                mostrarMenu();
                break;
            } else {
                caracteres = contarCaracteres(cupon);
                decimal = obtenerDecimal(descuento);
            }
            //Si ambas restricciones se cumplen proseguir con el proceso de agregar un cupon
            if (caracteres == true && decimal > 0 && decimal < 1) {
                
                for (int i = 0; i <= 19; i++) {
                    //Revisar si el cupon ya existe
                    if (cupon.equals(cupones[i][1])) {
                        System.out.println("\n**El cupon ya existía en el sistema por lo que NO se agrego");
                        existe = true;

                        break;
                    } else {
                        existe = false;

                    }
                }
                //Si no existe, agregarlo
                if (existe == false) {

                    cupones[contadorCupones][0] = Integer.toString(contadorCupones);
                    cupones[contadorCupones][1] = cupon;
                    cupones[contadorCupones][2] = Double.toString(descuento);

                    //Se cambia el estado de la condicion que mantiene al bucle ejecutandose
                    condicion = false;
                    //se agrega una unidad mas al contador
                    contadorCupones = contadorCupones + 1;
                    System.out.println("\n**Cupon agregado correctamente. Volviendo al menu");
                    //Imprimiendo tabla con los productos que existen actualmente

                    imprimirCupones();
                    //volviendo al menu
                    mostrarMenu();

                }

            } else {
                System.out.println("\n**El codigo del cupon no tiene 4 caracteres o el porcentaje no esta entre 0 y 100");
            }

        }
    }

    //Contar si el cupon tiene exactamente cuatro caracteres
    public static boolean contarCaracteres(String cupon) {
        boolean estado;

        if (cupon.length() == 4) {
            estado = true;
        } else {
            estado = false;
        }

        return estado;
    }
    
    //obtener el numero decimal que representa el porcentaje del cupon
    public static double obtenerDecimal(double descuento) {
        double porcentaje;

        porcentaje = descuento / 100;

        return porcentaje;
    }
    
    public static void realizarVenta() {
        //Limpiar los arrays utilizados para facturar y almacenar datos temporales
        limpiarCarrito();
        
        if(productos[0][0] != null){
            
        String nombreCliente;
        String nit;
        double totalParcial = 0;
        System.out.println("\n------Realizar venta------");

        //obtener datos del cliente
        System.out.print("Ingrese el nombre del cliente: ");
        consola.nextLine();
        nombreCliente = consola.nextLine();
        
        System.out.print("Ingrese el nit del cliente (deje en blanco para facturar como c/f): ");
        nit = consola.nextLine();
        
        if (nit.equals("")) {
            nit = "Consumidor final";
        }
        if (nombreCliente.equals("")) {
            nombreCliente = "Consumidor final";
        }
        
        //imprimir tabla de productos disponibles
        System.out.println("\n******Productos disponibles******");
        imprimirProductosSinCantidad();

        boolean condicion = true;

        System.out.println("\nA continuacion escriba el numero de id del producto que desea agregar al carrito seguido de la cantidad que se desea comprar de este");
        System.out.println("Cuando haya terminado con todos los productos teclee **continuar** en el campo id y confirme que haya terminado\n");
        
        String id;
        int cantidad;
        String producto;
        double precio;
        int contadorCarrito = 0;

        //iniciar bucle que consultara los productos que se desean comprar
        while (condicion == true) {
            
            System.out.print("Ingrese el numero de id correspondiente al producto que desea agregar (Se muestra en la tabla): ");
            id = consola.next();

            //consultar nombre y precio del producto con la id dada
            producto = buscarNombreProducto(id);
            precio = buscarPrecioProducto(id);

            //Condicional para verificar si el usuario a terminado de tabular los datos
            if (id.equals("continuar") || id.equals("Continuar")) {
                String confirmacion;
                System.out.print("Desea continuar con el siguiente paso de la factura (y/n): ");
                confirmacion = consola.next();
                if (confirmacion.equals("y") || confirmacion.equals("Y")) {
                    break;
                }
            }
            
            //Si el producto con la id dada existe agregarlo a la factura
            if (producto != null) {
                
                //Iniciar trycatch para verificar que la cantidad ingresada sea un numero
                //entero para evitar errores
                try{
                //consultar cantidad que se desea comprar
                System.out.print("Ingrese la cantidad de \"" + producto + "\" que desea agregar: ");
                cantidad = consola.nextInt();
                

                if (cantidad > 0) {

                    //Si la antidad es mayor que 0 entonces se procederá a agregar el producto a la factura
                    factura[contadorCarrito][0] = producto;
                    factura[contadorCarrito][1] = Double.toString(precio);
                    factura[contadorCarrito][2] = Integer.toString(cantidad);
                    factura[contadorCarrito][3] = Double.toString(precio * cantidad);
                    System.out.println("\n***Agregado al carrito con exito\n");
                    contadorCarrito = contadorCarrito + 1;
                    totalParcial = totalParcial + (precio * cantidad);

                    //Sumar a cantidades vendidas la cantida vendida en la factura actual
                    for (int i = 0; i <= 19; i++) {

                        if (productos[i][0] != null) {

                            if (productos[i][0].equals(id)) {
                                productos[i][3] = Integer.toString(Integer.parseInt(productos[i][3]) + cantidad);

                            }
                        }

                    }

                } else {
                    System.out.println("\n***La cantidad debe ser mayor a 0\n");
                }
                }catch(Exception e){
                //Limpiando el scaner para consumir el salto de linea causado por el error
                    consola.nextLine();
                    System.out.println("\n**La cantidad debe ser un numero entero\n");
                    
                }
            } else {
                System.out.println("\n**No se encuentra ningun producto con la id " + id + "\n");
            }

        }

        System.out.println("\nEl subtotal de la factura es Q" + totalParcial);

        //ciclo para verificar cupon
        boolean condicionCupon = false;
        boolean cuponExiste = true;
        double porcentajeDescuento = 0;

        while (condicionCupon == false) {

            System.out.print("\nSi tiene un codigo de descuento agregelo ahora (Si no cuenta con uno ingrese \"-\": ");
            String codigoCupon = consola.next();

            if (codigoCupon.equals("-")) {
                break;
            }

            boolean estado = contarCaracteres(codigoCupon);

            if (estado == true) {

                for (int i = 0; i <= 19; i++) {

                    if (codigoCupon.equals(cupones[i][1])) {

                        cuponExiste = true;
                        condicionCupon = true;
                        porcentajeDescuento = Double.parseDouble(cupones[i][2]) / 100;
                        break;
                    } else {
                        cuponExiste = false;

                    }
                }
            } else {
                System.out.println("\n**El codigo debe tener 4 caracteres");
            }

            if (cuponExiste == false) {
                System.out.println("\n**El cupon ingresado no existe");
            }
        }

        double totalFinal;
        if (porcentajeDescuento != 0) {

            totalFinal = totalParcial - totalParcial * porcentajeDescuento;

        } else {

            totalFinal = totalParcial;

        }

        generarFactura(totalFinal, totalParcial, porcentajeDescuento * 100, nombreCliente, nit);
        //Continuacion de la factura
        }else{
            System.out.println("\n*No hay ningun producto creado, no se puede continuar con la venta*");
            mostrarMenu();
        }
    }

    public static void generarFactura(double totalFinal, double subtotal, double porcentajeDescuento, String nombreCliente, String nit) {
        //Imprimir factura
        System.out.println("\n***************SUPER-25***************");
        System.out.println("Nombre del cajero: Irving Alvarado");
        System.out.println("Nombre del cliente: " + nombreCliente);
        System.out.println("Numero de nit: " + nit);
        System.out.println("Fecha: 10/02/2023");
        imprimirFacturaP();
        System.out.println("");
        System.out.println("Subtotal: Q" + subtotal);
        System.out.println("Porcentaje de descuento: " + porcentajeDescuento + "%");
        System.out.println("Total: Q" + totalFinal);
        mostrarMenu();
    }

    //Metodo para limpiar el array de factura
    public static void limpiarCarrito() {

        for (int i = 0; i <= 19; i++) {
            for (int j = 0; j <= 3; j++) {
                factura[i][j] = null;
            }
        }

    }
    //Metodo para buscar el nombre de un producto segun su id
    public static String buscarNombreProducto(String id) {
        String nombre = null;

        for (int i = 0; i <= 19; i++) {

            if (id.equals(productos[i][0])) {
                nombre = productos[i][1];
                break;
            }

        }

        return nombre;
    }
    
    //metodo para buscar el precio de un producto segun su id
    public static double buscarPrecioProducto(String id) {
        double precio = 0;

        for (int i = 0; i <= 19; i++) {

            if (id.equals(productos[i][0])) {
                precio = Double.parseDouble(productos[i][2]);
                break;
            }

        }

        return precio;
    }
    
    
    public static void realizarReporte() {
        System.out.println("\n------Reporte------");
        ordenarArray(productos);
        imprimirProductosConCantidad();
        mostrarMenu();
    }

       //imprimir el aray productos sin mostrar la cantidad vendida
    public static void imprimirProductosConCantidad() {

        System.out.println();
        //Imprimiendo encabezado
        System.out.println("ID\t\tNombre\t\tPrecio\t\tCantidad Vendida");
        //Ciclos aninados para imprimir la matriz
        for (int i = 0; i <= 19; i++) {
            for (int j = 0; j <= 3; j++) {
                if (productos[i][j] != null) {
                    System.out.print(productos[i][j] + "\t\t");
                } else {
                    //Saliendo del metodo si aun no se han agregado datos en las celdas actuales del array
                    return;
                }

            }
            System.out.println();
        }

    }

    
    //imprimir el array productos mostrando la cantidad vendidad
    public static void imprimirProductosSinCantidad() {

        System.out.println();
        //Imprimiendo encabezado
        System.out.println("ID\t\tNombre\t\tPrecio");
        //Ciclos aninados para imprimir la matriz
        for (int i = 0; i <= 19; i++) {
            for (int j = 0; j <= 2; j++) {
                if (productos[i][j] != null) {
                    System.out.print(productos[i][j] + "\t\t");
                } else {
                    //Saliendo del metodo si aun no se han agregado datos en las celdas actuales del array
                    return;
                }

            }
            System.out.println();
        }

    }

    //imprimir la lista de todos los cupones
    public static void imprimirCupones() {

        System.out.println();
        //Imprimiendo encabezado
        System.out.println("ID\t\tCupon\t\tDescuento %");
        //Ciclos aninados para imprimir la matriz
        for (int i = 0; i <= 19; i++) {
            for (int j = 0; j <= 2; j++) {
                if (cupones[i][j] != null) {
                    System.out.print(cupones[i][j] + "\t\t");
                } else {
                    //Saliendo del metodo si aun no se han agregado datos en las celdas actuales del array
                    return;
                }

            }
            System.out.println();
        }

    }
      
    //imprimir la tabla con los productos vendidos junto a la cantidad y el total
    public static void imprimirFacturaP() {

        System.out.println();
        //Imprimiendo encabezado
        System.out.println("Nombre\t\tPrecio\t\tCant.\t\tTotal");
        //Ciclos aninados para imprimir la matriz
        for (int i = 0; i <= 19; i++) {
            for (int j = 0; j <= 3; j++) {
                if (factura[i][j] != null) {
                    System.out.print(factura[i][j] + "\t\t");
                } else {
                    //Saliendo del metodo si aun no se han agregado datos en las celdas actuales del array
                    return;
                }

            }
            System.out.println();
        }

    }

    
    //Metodo para ordenar el array de productos en forma descendente basado en el ordenamiento de burbuja
    public static void ordenarArray(String[][] a) {

        String idTemporal, nombreTemporal, precioTemporal, vendidosTemporal, cantidadTemporal;

        for (int i = 0; i < a.length - 1; i++) {

            for (int j = 0; j < a.length - i - 1; j++) {

                if (Integer.parseInt(a[j + 1][3]) > Integer.parseInt(a[j][3])) {

                    idTemporal = a[j + 1][0];
                    nombreTemporal = a[j + 1][1];
                    precioTemporal = a[j + 1][2];
                    cantidadTemporal = a[j + 1][3];

                    a[j + 1][0] = a[j][0];
                    a[j + 1][1] = a[j][1];
                    a[j + 1][2] = a[j][2];
                    a[j + 1][3] = a[j][3];

                    a[j][0] = idTemporal;
                    a[j][1] = nombreTemporal;
                    a[j][2] = precioTemporal;
                    a[j][3] = cantidadTemporal;
                }

            }
        }
    }

    //Rellenar con 0 todas las celdas de cantidad vendida null del array productos
    //para evitar problemas con el ordenamiento
    public static void rellenarCeros() {
        for (int i = 0; i <= 19; i++) {
            productos[i][3] = "0";
        }
    }

    //funcion que devuelve la fecha actual - No se utilizo para evitar el uso de librerias
    
  /*  public static String obtenerFecha() {

        String unido;
        Calendar c = Calendar.getInstance();

        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH)+1);
        String anio = Integer.toString(c.get(Calendar.YEAR));

        unido = dia + "/" + (mes) + "/" + anio;

        return unido;
    }
*/
}
