package util;

import excepciones.ExcepcionesSistema.ArchivoInvalidoException;
import excepciones.ExcepcionesSistema.ColaDeEsperaVaciaException;
import excepciones.ExcepcionesSistema.CupoLlenoException;
import excepciones.ExcepcionesSistema.EstudianteNoEncontradoException;
import excepciones.ExcepcionesSistema.HorarioConflictivoException;
import excepciones.ExcepcionesSistema.PilaDeshacerVaciaException;
import excepciones.ExcepcionesSistema.PreRequisitoNoAprobadoException;
import java.util.Scanner;
import modelo.Aulas;
import modelo.Estudiante;
import modelo.Facultad;
import modelo.Materia;
import modelo.Profesor;
import modelo.Reporte;

// Clase Main: menu principal en consola para interactuar con todas las funciones del sistema
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static GestorSistema gestor = new GestorSistema();

    // Ciclo principal del programa: muestra el menu y ejecuta la opcion elegida hasta que el usuario digite 0
    public static void main(String[] args) {
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            System.out.println();

            switch (opcion) {
                case 1:
                    cargarDatosInicialesCSV();
                    break;
                case 2:
                    registrarEstudiante();
                    break;
                case 3:
                    buscarEstudiantePorId();
                    break;
                case 4:
                    eliminarEstudiante();
                    break;
                case 5:
                    registrarProfesor();
                    break;
                case 6:
                    registrarFacultad();
                    break;
                case 7:
                    registrarMateria();
                    break;
                case 8:
                    registrarAula();
                    break;
                case 9:
                    listarTodo();
                    break;
                case 10:
                    agregarPreRequisito();
                    break;
                case 11:
                    mostrarPreRequisitos();
                    break;
                case 12:
                    agregarMateriaHistorial();
                    break;
                case 13:
                    inscribirEstudiante();
                    break;
                case 14:
                    retirarEstudiante();
                    break;
                case 15:
                    gestionarColaEspera();
                    break;
                case 16:
                    cargarInscripcionesCSV();
                    break;
                case 17:
                    registrarNota();
                    break;
                case 18:
                    reservarOLiberarAula();
                    break;
                case 19:
                    consultarHorarioAula();
                    break;
                case 20:
                    deshacerOperacion();
                    break;
                case 21:
                    rehacerOperacion();
                    break;
                case 22:
                    generarReporte();
                    break;
                case 23:
                    navegarReportes();
                    break;
                case 24:
                    gestionarCampus();
                    break;
                case 25:
                    guardarMateriasCSV();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema universitario. Hasta pronto!");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
            System.out.println();
        }

        scanner.close();
    }

    // Imprime todas las opciones del menu organizadas por categoria
    private static void mostrarMenu() {
        System.out.println("===================================================");
        System.out.println("         SISTEMA DE GESTION UNIVERSITARIA          ");
        System.out.println("===================================================");
        System.out.println("--- CARGA Y GESTION DE ENTIDADES ---");
        System.out.println(" 1. Cargar estudiantes y materias desde CSV (data/)");
        System.out.println(" 2. Registrar estudiante");
        System.out.println(" 3. Buscar estudiante por ID");
        System.out.println(" 4. Eliminar estudiante (baja definitiva)");
        System.out.println(" 5. Registrar profesor");
        System.out.println(" 6. Registrar facultad (max 5)");
        System.out.println(" 7. Registrar materia");
        System.out.println(" 8. Registrar aula");
        System.out.println(" 9. Listar entidades registradas");
        System.out.println("--- INSCRIPCIONES, PRE-REQUISITOS Y COLA ---");
        System.out.println("10. Agregar pre-requisito a una materia");
        System.out.println("11. Mostrar pre-requisitos de una materia");
        System.out.println("12. Registrar materia aprobada en historial de estudiante");
        System.out.println("13. Inscribir estudiante en materia");
        System.out.println("14. Retirar estudiante de materia");
        System.out.println("15. Ver / atender cola de espera de una materia");
        System.out.println("16. Carga masiva de inscripciones desde CSV (Batch)");
        System.out.println("--- NOTAS Y HORARIOS DE AULAS ---");
        System.out.println("17. Registrar nota a estudiante");
        System.out.println("18. Reservar o liberar aula");
        System.out.println("19. Consultar disponibilidad / horario de aula");
        System.out.println("--- HISTORIAL DE OPERACIONES (PILAS) ---");
        System.out.println("20. Deshacer ultima operacion");
        System.out.println("21. Rehacer operacion");
        System.out.println("--- REPORTES, CAMPUS Y ARCHIVOS ---");
        System.out.println("22. Generar reporte academico");
        System.out.println("23. Navegar reportes (anterior / siguiente)");
        System.out.println("24. Mapa del campus y ruta mas corta (Dijkstra)");
        System.out.println("25. Guardar materias actuales en CSV");
        System.out.println(" 0. Salir");
        System.out.println("===================================================");
    }

    // =========================================================
    // METODOS DEL MENU
    // =========================================================

    // Opcion 1: carga estudiantes.csv y materia.csv desde la carpeta data/
    private static void cargarDatosInicialesCSV() {
        try {
            int estCargados = gestor.cargarEstudiantesCSV("data/estudiantes.csv");
            int matCargadas = gestor.cargarMateriasCSV("data/materia.csv");
            System.out.println("Estudiantes cargados: " + estCargados);
            System.out.println("Materias cargadas: " + matCargadas);
        } catch (ArchivoInvalidoException e) {
            System.out.println("Error al cargar CSV: " + e.getMessage());
        }
    }

    // Opcion 2: pide los datos por consola y registra un nuevo estudiante
    private static void registrarEstudiante() {
        System.out.print("ID del estudiante: ");
        String id = scanner.nextLine();
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String email = scanner.nextLine();
        int semestre = leerEntero("Semestre actual (1-10): ");

        Estudiante est = new Estudiante(nombre, id, email, semestre);
        if (gestor.registrarEstudiante(est)) {
            System.out.println("Estudiante registrado exitosamente.");
        } else {
            System.out.println("No se pudo registrar (ID vacio o ya existente).");
        }
    }

    // Opcion 3: busca un estudiante por ID y muestra su informacion y promedio
    private static void buscarEstudiantePorId() {
        System.out.print("ID del estudiante a buscar: ");
        String id = scanner.nextLine();

        try {
            Estudiante est = gestor.buscarEstudiante(id);
            System.out.println("=== RESULTADO ENCONTRADO ===");
            est.mostrarInformacion();
            if (est.tieneNotasRegistradas()) {
                System.out.printf("Promedio acumulado: %.2f%n", est.calcularPromedioAcumulado());
            } else {
                System.out.println("Promedio acumulado: 0.0 (sin notas aun)");
            }
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opcion 4: da de baja a un estudiante del sistema (se puede deshacer)
    private static void eliminarEstudiante() {
        System.out.print("ID del estudiante a eliminar: ");
        String id = scanner.nextLine();

        try {
            Estudiante eliminado = gestor.eliminarEstudiante(id);
            System.out.println("Estudiante eliminado exitosamente: " + eliminado.getNombre()
                    + " (ID: " + eliminado.getId() + ").");
            System.out.println("Nota: Puede revertir esta accion desde 'Deshacer ultima operacion'.");
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opcion 5: pide los datos y registra un profesor
    private static void registrarProfesor() {
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("ID del profesor: ");
        String id = scanner.nextLine();
        System.out.print("Correo: ");
        String email = scanner.nextLine();
        System.out.print("Departamento: ");
        String departamento = scanner.nextLine();
        double salario = leerDouble("Salario: ");

        Profesor prof = new Profesor(nombre, id, email, departamento, salario);
        if (gestor.registrarProfesor(prof)) {
            System.out.println("Profesor registrado exitosamente.");
        } else {
            System.out.println("No se pudo registrar (ID vacio o ya existente).");
        }
    }

    // Opcion 6: registra una facultad (hasta un maximo de 5)
    private static void registrarFacultad() {
        System.out.print("Codigo de la facultad: ");
        String codigo = scanner.nextLine();
        System.out.print("Nombre de la facultad: ");
        String nombre = scanner.nextLine();

        Facultad fac = new Facultad(codigo, nombre);
        if (gestor.registrarFacultad(fac)) {
            System.out.println("Facultad registrada exitosamente.");
        } else {
            System.out.println("No se pudo registrar (limite de 5 facultades alcanzado o codigo duplicado).");
        }
    }

    // Opcion 7: registra una materia nueva con creditos y cupo maximo
    private static void registrarMateria() {
        System.out.print("Codigo de la materia: ");
        String codigo = scanner.nextLine();
        System.out.print("Nombre de la materia: ");
        String nombre = scanner.nextLine();
        int creditos = leerEntero("Numero de creditos: ");
        int cupo = leerEntero("Cupo maximo: ");

        Materia mat = new Materia(codigo, nombre, creditos, cupo);
        if (gestor.registrarMateria(mat)) {
            System.out.println("Materia registrada exitosamente.");
        } else {
            System.out.println("No se pudo registrar (codigo vacio o duplicado).");
        }
    }

    // Opcion 8: registra un aula nueva con su capacidad
    private static void registrarAula() {
        System.out.print("Nombre del aula (ej. A101): ");
        String nombre = scanner.nextLine();
        int capacidad = leerEntero("Capacidad: ");

        Aulas aula = new Aulas(nombre, capacidad);
        if (gestor.registrarAula(aula)) {
            System.out.println("Aula registrada exitosamente.");
        } else {
            System.out.println("No se pudo registrar (nombre vacio o duplicado).");
        }
    }

    // Opcion 9: submenu para listar cualquiera de las entidades registradas
    private static void listarTodo() {
        System.out.println("1. Estudiantes | 2. Profesores | 3. Facultades | 4. Materias | 5. Aulas");
        int sub = leerEntero("Que desea listar?: ");
        switch (sub) {
            case 1:
                gestor.listarEstudiantes();
                break;
            case 2:
                gestor.listarProfesores();
                break;
            case 3:
                gestor.listarFacultades();
                break;
            case 4:
                gestor.listarMaterias();
                break;
            case 5:
                gestor.listarAulas();
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }

    // Opcion 10: agrega un pre-requisito a una materia
    private static void agregarPreRequisito() {
        System.out.print("Codigo de la materia: ");
        String codMat = scanner.nextLine();
        System.out.print("Codigo del pre-requisito: ");
        String codPre = scanner.nextLine();

        if (gestor.agregarPreRequisitoAMateria(codMat, codPre)) {
            System.out.println("Pre-requisito agregado.");
        }
    }

    // Opcion 11: muestra los pre-requisitos de una materia
    private static void mostrarPreRequisitos() {
        System.out.print("Codigo de la materia: ");
        String codMat = scanner.nextLine();
        gestor.mostrarPreRequisitosMateria(codMat);
    }

    // Opcion 12: registra una materia como aprobada en el historial de un estudiante
    private static void agregarMateriaHistorial() {
        System.out.print("ID del estudiante: ");
        String idEst = scanner.nextLine();
        System.out.print("Codigo de la materia aprobada: ");
        String codMat = scanner.nextLine();

        try {
            gestor.agregarMateriaHistorialEstudiante(idEst, codMat);
            System.out.println("Materia agregada al historial del estudiante.");
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opcion 13: inscribe un estudiante en una materia validando pre-requisitos y cupos
    private static void inscribirEstudiante() {
        System.out.print("ID del estudiante: ");
        String idEst = scanner.nextLine();
        System.out.print("Codigo de la materia: ");
        String codMat = scanner.nextLine();

        try {
            if (gestor.inscribirEstudianteEnMateria(idEst, codMat)) {
                System.out.println("Inscripcion realizada con exito.");
            } else {
                System.out.println("No se pudo inscribir (el estudiante ya estaba inscrito o materia invalida).");
            }
        } catch (EstudianteNoEncontradoException | PreRequisitoNoAprobadoException | CupoLlenoException e) {
            System.out.println("Aviso: " + e.getMessage());
        }
    }

    // Opcion 14: retira un estudiante de una materia (y si hay cola, entra el siguiente automaticamente)
    private static void retirarEstudiante() {
        System.out.print("ID del estudiante: ");
        String idEst = scanner.nextLine();
        System.out.print("Codigo de la materia: ");
        String codMat = scanner.nextLine();

        try {
            if (gestor.retirarEstudianteDeMateria(idEst, codMat)) {
                System.out.println("Estudiante retirado de la materia.");
            } else {
                System.out.println("El estudiante no estaba inscrito en esa materia.");
            }
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opcion 15: permite ver la cola de espera de una materia o atender al siguiente
    private static void gestionarColaEspera() {
        System.out.print("Codigo de la materia: ");
        String codMat = scanner.nextLine();
        System.out.println("1. Ver cola de espera | 2. Atender siguiente en cola");
        int sub = leerEntero("Seleccione: ");

        if (sub == 1) {
            gestor.mostrarColaDeEspera(codMat);
        } else if (sub == 2) {
            try {
                Estudiante atendido = gestor.atenderColaDeEspera(codMat);
                if (atendido != null) {
                    System.out.println("Estudiante inscrito desde la cola: " + atendido.getNombre());
                }
            } catch (ColaDeEsperaVaciaException | CupoLlenoException e) {
                System.out.println("Aviso: " + e.getMessage());
            }
        } else {
            System.out.println("Opcion no valida.");
        }
    }

    // Opcion 16: ejecuta la carga masiva de inscripciones desde un CSV usando cola FIFO
    private static void cargarInscripcionesCSV() {
        System.out.print("Ruta del archivo (Enter para usar data/inscripciones.csv): ");
        String ruta = scanner.nextLine().trim();
        if (ruta.isEmpty()) {
            ruta = "data/inscripciones.csv";
        }

        try {
            gestor.cargarInscripcionesCSV(ruta);
        } catch (ArchivoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opcion 17: registra una nota a un estudiante en un semestre y posicion de materia
    private static void registrarNota() {
        System.out.print("ID del estudiante: ");
        String idEst = scanner.nextLine();
        int semestre = leerEntero("Semestre (1-10): ");
        int indiceMat = leerEntero("Numero de materia en el semestre (1-20): ");
        double nota = leerDouble("Nota (0.0 a 5.0): ");

        try {
            if (gestor.registrarNota(idEst, semestre, indiceMat, nota)) {
                System.out.println("Nota registrada correctamente.");
            }
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opcion 18: submenu para reservar o liberar un bloque de horario en un aula
    private static void reservarOLiberarAula() {
        System.out.println("1. Reservar aula | 2. Liberar aula");
        int sub = leerEntero("Seleccione: ");
        if (sub != 1 && sub != 2) {
            System.out.println("Opcion no valida.");
            return;
        }

        System.out.print("Nombre del aula: ");
        String nombre = scanner.nextLine();
        int dia = leerEntero("Dia (0=Domingo, 1=Lunes ... 6=Sabado): ");
        int hora = leerEntero("Hora inicio (0-23): ");
        int duracion = leerEntero("Duracion en horas: ");

        if (sub == 1) {
            try {
                if (gestor.reservarAula(nombre, dia, hora, duracion)) {
                    System.out.println("Aula reservada exitosamente.");
                }
            } catch (HorarioConflictivoException e) {
                System.out.println("Conflicto de horario: " + e.getMessage());
            }
        } else {
            if (gestor.liberarAula(nombre, dia, hora, duracion)) {
                System.out.println("Aula liberada exitosamente.");
            }
        }
    }

    // Opcion 19: permite ver el horario completo de un aula o consultar una hora puntual
    private static void consultarHorarioAula() {
        System.out.print("Nombre del aula: ");
        String nombre = scanner.nextLine();
        System.out.println("1. Ver horario completo | 2. Consultar dia y hora especifica");
        int sub = leerEntero("Seleccione: ");

        if (sub == 1) {
            gestor.mostrarHorarioAula(nombre);
        } else if (sub == 2) {
            int dia = leerEntero("Dia (0=Domingo, 1=Lunes ... 6=Sabado): ");
            int hora = leerEntero("Hora (0-23): ");
            boolean disponible = gestor.consultarDisponibilidadAula(nombre, dia, hora);
            System.out.println(disponible ? "El aula esta DISPONIBLE." : "El aula esta OCUPADA o no existe.");
        } else {
            System.out.println("Opcion no valida.");
        }
    }

    // Opcion 20: deshace la ultima operacion guardada en la pila
    private static void deshacerOperacion() {
        try {
            System.out.println(gestor.deshacerUltimaOperacion());
        } catch (PilaDeshacerVaciaException e) {
            System.out.println("Aviso: " + e.getMessage());
        }
    }

    // Opcion 21: rehace la ultima operacion que se deshizo
    private static void rehacerOperacion() {
        try {
            System.out.println(gestor.rehacerOperacion());
        } catch (PilaDeshacerVaciaException e) {
            System.out.println("Aviso: " + e.getMessage());
        }
    }

    // Opcion 22: genera el reporte completo, por semestre o de reprobadas de un estudiante
    private static void generarReporte() {
        System.out.print("ID del estudiante: ");
        String idEst = scanner.nextLine();
        System.out.println("1. Reporte completo | 2. Reporte por semestre | 3. Materias reprobadas");
        int sub = leerEntero("Seleccione tipo de reporte: ");

        try {
            Reporte rep = null;
            if (sub == 1) {
                rep = gestor.generarReporteCompleto(idEst);
            } else if (sub == 2) {
                int sem = leerEntero("Semestre (1-10): ");
                rep = gestor.generarReporteSemestre(idEst, sem);
            } else if (sub == 3) {
                rep = gestor.generarReporteReprobadas(idEst);
            } else {
                System.out.println("Opcion no valida.");
                return;
            }

            if (rep != null) {
                System.out.println(rep);
            }
        } catch (EstudianteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Opcion 23: navega hacia atras o adelante entre los reportes ya generados
    private static void navegarReportes() {
        System.out.println("1. Reporte anterior | 2. Reporte siguiente");
        int sub = leerEntero("Seleccione: ");
        Reporte rep;

        if (sub == 1) {
            rep = gestor.irReporteAnterior();
        } else if (sub == 2) {
            rep = gestor.irReporteSiguiente();
        } else {
            System.out.println("Opcion no valida.");
            return;
        }

        if (rep != null) {
            System.out.println(rep);
        } else {
            System.out.println("No hay mas reportes en esa direccion.");
        }
    }

    // Opcion 24: submenu para ver edificios, mapa, calcular rutas con Dijkstra o agregar conexiones
    private static void gestionarCampus() {
        System.out.println("1. Ver edificios | 2. Ver mapa completo | 3. Ruta mas corta (Dijkstra)");
        System.out.println("4. Agregar edificio | 5. Conectar edificios");
        int sub = leerEntero("Seleccione: ");

        if (sub == 1) {
            gestor.mostrarEdificiosCampus();
        } else if (sub == 2) {
            gestor.mostrarMapaCampus();
        } else if (sub == 3) {
            System.out.print("Edificio origen: ");
            String origen = scanner.nextLine();
            System.out.print("Edificio destino: ");
            String destino = scanner.nextLine();
            System.out.println(gestor.calcularRutaMasCorta(origen, destino));
        } else if (sub == 4) {
            System.out.print("Nombre del nuevo edificio: ");
            String nombre = scanner.nextLine();
            System.out.println(gestor.agregarEdificioCampus(nombre) ? "Edificio agregado." : "No se pudo agregar.");
        } else if (sub == 5) {
            System.out.print("Edificio origen: ");
            String origen = scanner.nextLine();
            System.out.print("Edificio destino: ");
            String destino = scanner.nextLine();
            int dist = leerEntero("Distancia en metros: ");
            System.out.println(gestor.conectarEdificiosCampus(origen, destino, dist) ? "Conexion creada." : "Error al conectar.");
        } else {
            System.out.println("Opcion no valida.");
        }
    }

    // Opcion 25: exporta las materias actuales del sistema a un archivo CSV
    private static void guardarMateriasCSV() {
        System.out.print("Ruta destino (Enter para usar data/materia.csv): ");
        String ruta = scanner.nextLine().trim();
        if (ruta.isEmpty()) {
            ruta = "data/materia.csv";
        }

        try {
            gestor.guardarMateriasCSV(ruta);
            System.out.println("Materias guardadas correctamente en " + ruta);
        } catch (ArchivoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================================================
    // UTILIDADES PARA LEER NUMEROS SIN ERRORES DE SCANNER
    // =========================================================

    // Lee un entero validando que el usuario no ingrese letras y evita saltos de linea del Scanner
    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un numero entero valido.");
            }
        }
    }

    // Lee un decimal aceptando punto o coma para evitar errores al ingresar notas o salarios
    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un numero decimal valido (ej. 4.5).");
            }
        }
    }
}