package com.example.zona_fit;

import com.example.zona_fit.modelo.Cliente;
import com.example.zona_fit.servicio.IClienteServicio;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

  @Autowired
  private IClienteServicio clienteServicio;


  private static final Logger logger =
      LoggerFactory.getLogger(ZonaFitApplication.class);
  String nl = System.lineSeparator();

  public static void main(String[] args) {
    logger.info("Iniciando la aplicación: ");
    SpringApplication.run(ZonaFitApplication.class, args);
    logger.info("Aplicación finalizada");
  }

  @Override
  public void run(String... args) throws Exception {
    zonaFitApp();
  }

  private void zonaFitApp() {
    logger.info(nl + "*** Aplicación zona fit GYM ***" + nl);
    boolean salir = false;
    Scanner teclado = new Scanner(System.in);
    while (!salir) {
      int opcion = mostrarMenu(teclado);
      salir = ejecutarOpciones(teclado, opcion);
      logger.info(nl);
    }
  }


  private int mostrarMenu(Scanner teclado) {
    logger.info("""
        1. Listar clientes.
        2. Buscar cliente.
        3. Agregar cliente.
        4. Modificar cliente
        5. Eliminar cliente
        6. Salir
        Elige una opción:\s
        """);
    int opcion = teclado.nextInt();
    return opcion;
  }

  private boolean ejecutarOpciones(Scanner teclado, int opcion) {

    boolean salir = false;
    switch (opcion) {
      case 1 -> {
        logger.info(nl + "--- Listado de Clientes ---" + nl);
        List<Cliente> clientes = clienteServicio.listarClientes();
        clientes.forEach(cliente -> logger.info(cliente + nl));
      }
      case 2 -> {
        logger.info(nl + "---Buscar cliente por ID ---" + nl);
        logger.info("Id cliente a buscar: ");
        int idCliente = teclado.nextInt();
        Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
        if (cliente != null) {
          logger.info("Cliente encontrado: " + cliente + nl);
        } else {
          logger.info("Cliente NO encontrado: " + cliente + nl);
        }
      }
      case 3 -> {
        logger.info("--- Agregar cliente ---" + nl);
        logger.info("Nombre: ");
        String nombre = teclado.next();
        logger.info("Apellido: ");
        String apellido = teclado.next();
        logger.info("Membresia: ");
        int membresia = teclado.nextInt();

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setMembresia(membresia);
        clienteServicio.guardarCliente(cliente);
        logger.info("Cliente guardado correctamente: " + cliente + nl);
      }
      case 4 -> {
        logger.info("--- Modificar cliente ---" + nl);
        logger.info("Ingrese el Id cliente: ");
        int idCliente = teclado.nextInt();
        Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
        if (cliente != null) {
          logger.info("Nombre:");
          String nombre = teclado.next();
          logger.info("Apellido");
          String apellido = teclado.next();
          logger.info("Membresia");
          int membresia = teclado.nextInt();
          cliente.setNombre(nombre);
          cliente.setApellido(apellido);
          cliente.setMembresia(membresia);
          clienteServicio.guardarCliente(cliente);
          logger.info("Cliente modificado de manera exitosa: " + cliente);

        } else {
          logger.info("Cliente NO encontrado:" + cliente);
        }


      }
      case 5 -> {
        logger.info("--- Eliminar cliente ---");
        logger.info("Ingrese el Id cliente: ");
        int idCliente = teclado.nextInt();
        Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
        if (cliente != null) {
          clienteServicio.eliminarCliente(cliente);
          logger.info("Cliente eliminado exitosamente." + cliente + nl);
        } else {
          logger.info("Cliente NO encontrado: " + cliente);
        }

      }
      case 6 -> {
        logger.info("Hasta pronto !!" + nl + nl);
        salir = true;
      }
      default -> logger.info("Opcion erronea, ingresa una opcón valida");
    }
    return salir;
  }
}
