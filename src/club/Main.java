package club;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import club.Socio.Tipo;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int op;
        Club c = new Club();

        do{
            System.out.println("1. Afiliar un socio al club.");
            System.out.println("2. Registrar una persona autorizada por un socio.");
            System.out.println("3. Pagar una factura.");
            System.out.println("4. Registrar un consumo en la cuenta de un socio");
            System.out.println("5. Aumentar fondos de la cuenta de un socio");
            System.out.println("6. Calcular total de consumos de socio");
            System.out.println("7. Determinar si se puede eliminar Socio");
            System.out.println("8. Eliminar autorizado por socio");
            System.out.println("9. Salir");
            System.out.print("Ingrese una opcion: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op){
                case 1:{
                    boolean valido = false;
                    int tipoOp = 1;

                    System.out.println("\n--- AFILIAR SOCIO ---");
                    System.out.print("Ingrese la cédula del socio: ");
                    String cedula = sc.nextLine();
                    System.out.print("Ingrese el nombre del socio: ");
                    String nombre = sc.nextLine();

                    while (!valido) {
                        try {
                            System.out.print("Tipo de suscripción (1=REGULAR, 2=VIP): ");
                            tipoOp = sc.nextInt();
                            sc.nextLine();
                            if (tipoOp >= 1 && tipoOp <= 2) {
                                valido = true;
                            } else {
                                System.out.println("Error: Debe ingresar un número entre " + 1 + " y " + 2 + ".");
                            }
                        } catch (Exception e) {
                            System.out.println("Error: Debe ingresar un número entero válido.");
                            sc.nextLine();
                        }
                    }

                    Tipo tipo = (tipoOp == 2) ? Tipo.VIP : Tipo.REGULAR;
                    c.afiliarSocio(cedula, nombre, tipo);
                    System.out.println("Socio afiliado correctamente");

                }break;

                case 2:{
                    System.out.println("\n--- REGISTRAR AUTORIZADO ---");
                    System.out.print("Ingrese la cédula del socio: ");
                    String cedula = sc.nextLine();

                    Socio socio = c.buscarSocio(cedula);
                    if(socio == null){
                        System.out.println("No existe un socio con esa cédula");
                        break;
                    }

                    System.out.print("Ingrese el nombre del autorizado: ");
                    String nombreAutorizado = sc.nextLine();
                    c.agregarAutorizadoSocio(cedula, nombreAutorizado);
                    System.out.println("Autorizado agregado correctamente.");

                }break;

                case 3:{
                    boolean valido = false;

                    System.out.println("\n--- PAGAR FACTURA ---");
                    System.out.print("Ingrese la cédula del socio: ");
                    String cedula = sc.nextLine();

                    Socio socio = c.buscarSocio(cedula);
                    if(socio == null){
                        System.out.println("No existe un socio con esa cédula");
                        break;
                    }

                    ArrayList<Factura> facturas = c.darFacturasSocio(cedula);
                    if(facturas.isEmpty()){
                        System.out.println("El socio no tiene facturas pendientes");
                        break;
                    }

                    System.out.println("\nFacturas pendientes:");
                    for(int i = 0; i < facturas.size(); i++){
                        System.out.println(i + ". " + facturas.get(i));
                    }

                    int fOp = 0;
                    while (!valido) {
                        try {
                            System.out.print("Ingrese el índice de la factura a pagar: ");
                            fOp = sc.nextInt();
                            sc.nextLine();
                            if (fOp >= 0 && fOp <= facturas.size()-1) {
                                valido = true;
                            } else {
                                System.out.println("Error: Debe ingresar un número entre " + 0 + " y " + (facturas.size()-1) + ".");
                            }
                        } catch (Exception e) {
                            System.out.println("Error: Debe ingresar un número entero válido.");
                            sc.nextLine();
                        }
                    }

                    c.pagarFacturaSocio(cedula, fOp);
                    System.out.println("¡Factura pagada exitosamente!");

                }break;

                case 4:{
                    boolean valido = false;

                    System.out.println("\n--- REGISTRAR CONSUMO ---");
                    System.out.print("Ingrese la cédula del socio: ");
                    String cedula = sc.nextLine();

                    Socio socio = c.buscarSocio(cedula);
                    if(socio == null){
                        System.out.println("No existe un socio con esa cédula");
                        break;
                    }

                    ArrayList<String> autorizados = c.darAutorizadosSocio(cedula);
                    System.out.println("\nPersonas autorizadas:");
                    for(int i = 0; i < autorizados.size(); i++){
                        System.out.println(i + ". " + autorizados.get(i));
                    }

                    int aOp = 0;
                    while (!valido) {
                        try {
                            System.out.print("Ingrese el índice de quien realizó el consumo: ");
                            aOp = sc.nextInt();
                            sc.nextLine();
                            if (aOp >= 0 && aOp <= autorizados.size()-1) {
                                valido = true;
                            } else {
                                System.out.println("Error: Debe ingresar un número entre " + 0 + " y " + (autorizados.size()-1) + ".");
                            }
                        } catch (Exception e) {
                            System.out.println("Error: Debe ingresar un número entero válido.");
                            sc.nextLine();
                        }
                    }

                    String nombreCliente = autorizados.get(aOp);
                    System.out.print("Ingrese el concepto del consumo: ");
                    String concepto = sc.nextLine();

                    valido = false;
                    double cOp = 0;
                    while (!valido) {
                        try {
                            System.out.print("Ingrese el valor del consumo: $");
                            cOp = sc.nextDouble();
                            sc.nextLine();
                            if (cOp >= 0) {
                                valido = true;
                            } else {
                                System.out.println("Error: Debe ingresar un número superior a 0");
                            }
                        } catch (Exception e) {
                            System.out.println("Error: Debe ingresar un número decimal válido.");
                            sc.nextLine();
                        }
                    }

                    c.registrarConsumo(cedula, nombreCliente, concepto, cOp);
                    System.out.println("¡Consumo registrado exitosamente!");

                }break;

                case 5:{
                    boolean valido = false;

                    System.out.println("\n--- AUMENTAR FONDOS ---");
                    System.out.print("Ingrese la cédula del socio: ");
                    String cedula = sc.nextLine();

                    Socio socio = c.buscarSocio(cedula);
                    if(socio == null){
                        System.out.println("No existe un socio con esa cédula");
                        break;
                    }

                    System.out.println("Fondos actuales: $" + socio.darFondos());

                    double fOp = 0;
                    while (!valido) {
                        try {
                            System.out.print("Ingrese el valor a aumentar: $");
                            fOp = sc.nextDouble();
                            sc.nextLine();
                            if (fOp >= 0) {
                                valido = true;
                            } else {
                                System.out.println("Error: Debe ingresar un número superior a 0");
                            }
                        } catch (Exception e) {
                            System.out.println("Error: Debe ingresar un número decimal válido.");
                            sc.nextLine();
                        }
                    }

                    c.aumentarFondosSocio(cedula, fOp);
                    System.out.println("Nuevos fondos: $" + socio.darFondos());

                }break;

                case 6:{
                    System.out.println("\n--- TOTAL CONSUMIDO POR SOCIO ---");
                    System.out.print("Ingrese la cédula del socio: ");
                    String cedula = sc.nextLine();

                    Socio socio = c.buscarSocio(cedula);
                    if(socio == null){
                        System.out.println("No existe un socio con esa cédula");
                        break;
                    }

                    double consumo = c.calcularTotalConsumosSocio(cedula);
                    System.out.println("Total consumido:" + consumo);

                }break;

                case 7:{
                    System.out.println("\n--- DETERMINAR SI SE PUEDE ELIMINAR SOCIO ---");
                    System.out.print("Ingrese la cédula del socio: ");
                    String cedula = sc.nextLine();

                    Socio socio = c.buscarSocio(cedula);
                    if(socio == null){
                        System.out.println("No existe un socio con esa cédula");
                        break;
                    }

                    if(c.sePuedeEliminarSocio(cedula)){
                        System.out.println("Socio se puede eliminar");
                    } else{
                        System.out.println("El socio no puede ser eliminado");
                    }

                } break;

                case 8:{
                    System.out.println("\n--- ELIMINAR AUTORIZADO POR SOCIO ---");
                    System.out.print("Ingrese la cédula del socio: ");
                    String cedula = sc.nextLine();

                    Socio socio = c.buscarSocio(cedula);
                    if(socio == null){
                        System.out.println("No existe un socio con esa cédula");
                        break;
                    }

                    System.out.println("Ingrese el nombre del autorizado a eliminar");
                    String nombre = sc.nextLine();

                    c.eliminarAutorizadoSocio(cedula, nombre);

                }break;

                case 9:{
                    System.out.println("Saliendo del sistema");
                }break;

                default:
                    System.out.println("opcion invalida");
            }

        }while(op!=9);

        sc.close();
    }
}