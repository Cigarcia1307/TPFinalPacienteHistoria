package main;

import Service.PacienteService;
import dao.PacienteDao;
import dao.HistoriaClinicaDao;
import entities.Paciente;
import entities.HistoriaClinica;
import entities.GrupoSanguineo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final PacienteService pacienteService;
    private final Scanner sc;

    public AppMenu() {
        this.pacienteService = new PacienteService(new PacienteDao(), new HistoriaClinicaDao());
        this.sc = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 7) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 -> crearPaciente(); // FALTA
                    case 2 -> leerPacientePorId();
                    case 3 -> listarPacientes();
                    case 4 -> actualizarPaciente(); 
                    case 5 -> eliminarPaciente(); //?
                    case 6 -> buscarPacientePorDni();
                    case 7 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }

    private void mostrarMenu() {
        System.out.println("\n--- MENÚ PACIENTES ---");
        System.out.println("1. Crear paciente");
        System.out.println("2. Leer paciente por ID");
        System.out.println("3. Listar todos los pacientes");
        System.out.println("4. Actualizar paciente");
        System.out.println("5. Eliminar paciente");
        System.out.println("6. Buscar paciente por DNI");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void crearPaciente() throws Exception {
        System.out.println("\n--- CREAR PACIENTE ---");
        System.out.print("ID paciente: ");
        Long id = Long.parseLong(sc.nextLine());
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Apellido: ");
        String apellido = sc.nextLine();
        System.out.print("DNI: ");
        String dni = sc.nextLine();
        System.out.print("Fecha de nacimiento (yyyy-MM-dd): ");
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(sc.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido.");
            return;
        }

        System.out.println("\n--- CREAR HISTORIA CLÍNICA ---");
        System.out.print("ID historia clínica: ");
        Long idHc = Long.parseLong(sc.nextLine());
        System.out.print("Número de historia: ");
        String nroHistoria = sc.nextLine();
        System.out.print("Grupo sanguíneo (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
        GrupoSanguineo grupo;
        try {
            grupo = GrupoSanguineo.valueOf(sc.nextLine().toUpperCase()
                    .replace("+", "_POSITIVO").replace("-", "_NEGATIVO"));
        } catch (IllegalArgumentException e) {
            System.out.println("Grupo sanguíneo inválido.");
            return;
        }
        System.out.print("Antecedentes: ");
        String antecedentes = sc.nextLine();
        System.out.print("Medicación actual: ");
        String medicacion = sc.nextLine();
        System.out.print("Observaciones: ");
        String observaciones = sc.nextLine();

        HistoriaClinica hc = new HistoriaClinica(idHc, nroHistoria, antecedentes, medicacion, observaciones, false, grupo);
        Paciente p = new Paciente(id, nombre, apellido, dni, fecha, false, hc);

        pacienteService.insertar(p);
        System.out.println("Paciente creado con éxito!");
    }

    private void leerPacientePorId() throws Exception {
        System.out.print("\nIngrese ID del paciente: ");
        Long id = Long.parseLong(sc.nextLine());
        Paciente p = pacienteService.getById(id);
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("No se encontró paciente con ID " + id);
        }
    }

    private void listarPacientes() throws Exception {
        System.out.println("\n--- LISTA DE PACIENTES ---");
        List<Paciente> lista = pacienteService.getAll();
        if (lista.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            for (Paciente p : lista) {
                System.out.println(p);
            }
        }
    }

    private void actualizarPaciente() throws Exception {
        System.out.print("\nIngrese ID del paciente a actualizar: ");
        Long id = Long.parseLong(sc.nextLine());
        Paciente p = pacienteService.getById(id);
        if (p == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        System.out.println("Deje vacío para mantener valor actual.");

        System.out.print("Nombre (" + p.getNombre() + "): ");
        String nombre = sc.nextLine();
        if (!nombre.isBlank()) p.setNombre(nombre);

        System.out.print("Apellido (" + p.getApellido() + "): ");
        String apellido = sc.nextLine();
        if (!apellido.isBlank()) p.setApellido(apellido);

        System.out.print("DNI (" + p.getDni() + "): ");
        String dni = sc.nextLine();
        if (!dni.isBlank()) p.setDni(dni);

        System.out.print("Fecha de nacimiento (" + p.getFechaNacimiento() + "): ");
        String fechaStr = sc.nextLine();
        if (!fechaStr.isBlank()) {
            try {
                p.setFechaNacimiento(LocalDate.parse(fechaStr));
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Mantiene fecha anterior.");
            }
        }

        // Historia Clínica
        HistoriaClinica hc = p.getHistoriaClinica();
        System.out.print("Número de historia (" + hc.getNroHistoria() + "): ");
        String nroHistoria = sc.nextLine();
        if (!nroHistoria.isBlank()) hc.setNroHistoria(nroHistoria);

        System.out.print("Grupo sanguíneo (" + hc.getGrupoSanguineo() + "): ");
        String grupoStr = sc.nextLine();
        if (!grupoStr.isBlank()) {
            try {
                hc.setGrupoSanguineo(GrupoSanguineo.valueOf(grupoStr.toUpperCase()
                        .replace("+", "_POSITIVO").replace("-", "_NEGATIVO")));
            } catch (IllegalArgumentException e) {
                System.out.println("Grupo sanguíneo inválido. Mantiene valor anterior.");
            }
        }

        System.out.print("Antecedentes (" + hc.getAntecedentes() + "): ");
        String antecedentes = sc.nextLine();
        if (!antecedentes.isBlank()) hc.setAntecedentes(antecedentes);

        System.out.print("Medicación actual (" + hc.getMedicacionActual() + "): ");
        String medicacion = sc.nextLine();
        if (!medicacion.isBlank()) hc.setMedicacionActual(medicacion);

        System.out.print("Observaciones (" + hc.getObservaciones() + "): ");
        String obs = sc.nextLine();
        if (!obs.isBlank()) hc.setObservaciones(obs);

        pacienteService.actualizar(p);
        System.out.println("Paciente actualizado con éxito!");
    }

    private void eliminarPaciente() throws Exception {
        System.out.print("\nIngrese ID del paciente a eliminar: ");
        Long id = Long.parseLong(sc.nextLine());
        pacienteService.eliminar(id);
        System.out.println("Paciente eliminado (baja lógica) con éxito.");
    }
    
        
       private void buscarPacientePorDni() throws Exception {
        System.out.print("\nIngrese DNI del paciente: ");
        String dni = sc.nextLine();
        Paciente p = pacienteService.getByDni(dni);
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("No se encontró paciente con ese DNI.");
        }
    }
    

    // Método main para arrancar la app
    public static void main(String[] args) {
        new AppMenu().iniciar();
    }
}