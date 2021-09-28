package vistatextualqytetet;

import controladorqytetet.*;
import java.util.ArrayList;
import java.util.Scanner;
import modeloqytetet.*;

/**
 * Clase VistaTextualQytetet, encargada de ofrecer la vista textual en terminal
 * al usuario.
 *
 * @author cazz & sara
 */
public class VistaTextualQytetet {

    Scanner in = new Scanner(System.in);
    ControladorQytetet controlador = ControladorQytetet.getInstance();

    /**
     * Metodo encargado de obtener el nombre de los jugadores desde el terminal.
     *
     * @return Un arrayList de String el cual contiene el nombre de los
     * jugadores.
     */
    public ArrayList<String> obtenerNombreJugadores() {
        ArrayList<String> nombres = new ArrayList();
        int numero_jugadores = 0;

        do {
            System.out.print("Introduce un numero de jugadores(2-4): ");
            numero_jugadores = in.nextInt();
        } while (!(numero_jugadores >= 2 && numero_jugadores <= 4));

        System.out.println("Introduce el nombre de los jugadores: ");
        for (int i = 0; i < numero_jugadores; i++) {
            System.out.print("(Jugador " + (i + 1) + "): ");
            in.reset();
            nombres.add(in.next()); //Next en vez de nextLine para que funcione
        }

        System.out.println("");
        return nombres;
    }

    /**
     * Metodo encargado de elegir una casilla valida la cual apareceran en el
     * terminal.
     *
     * @param opcionMenu Dependiendo de la opcion de menu escogida nos
     * apareceran unas casillas u otras.
     * @return Un entero con el numero de casilla escogida.
     */
    public int elegirCasilla(int opcionMenu) {
        ArrayList<Integer> casillas_validas = controlador.obtenerCasillasValidas(opcionMenu);
        ArrayList<String> valoresCorrectos = new ArrayList();

        if (casillas_validas.isEmpty()) { 
            System.out.println("\n¡No hay ninguna casilla valida a la cual realizar la operacion!");
            return -1;
        } else {
            System.out.println("\n------------Elige una casilla------------");
            for (Integer actual : casillas_validas) {
                Calle calle_actual = (Calle) modeloqytetet.Qytetet.getInstance().obtenerCasillasTablero().get(actual);
                System.out.println(actual + ": " + calle_actual.getTitulo().getNombre());
                valoresCorrectos.add(Integer.toString(actual));
            }

            String eleccion = leerValorCorrecto(valoresCorrectos);

            return Integer.parseInt(eleccion);
        }
    }

    /**
     * Metodo encargado de introducir correctamente un valor correcto que este
     * contenido en el ArrayList que se pasa por parametro.
     *
     * @param valoresCorretos ArrayList de String el cual contiene los valores.
     * @return Un string con la opcion valida.
     */
    public String leerValorCorrecto(ArrayList<String> valoresCorretos) {

        String opcion;
        boolean correcto = false;
        do {
            System.out.print("Introduce una opcion: ");
            opcion = in.next();
            for (String actual : valoresCorretos) {
                if (opcion.equals(actual)) {
                    correcto = true;
                }
            }
            if (!correcto) {
                System.out.println("¡Opcion incorrecta, introduce otra opcion correcta!");
            }
        } while (!correcto);

        return opcion;
    }

    /**
     * Metodo encargado de imprimir el Menu y encargado de elegir una de las
     * operaciones validas.
     *
     * @return Un entero con la opcion elegida.
     */
    public int elegirOperacion() {
        System.out.println("[------------MENU-------------]");
        ArrayList<Integer> operaciones = controlador.obtenerOperacionesJuegoValidas();
        ArrayList<String> valoresCorrectos = new ArrayList();

        for (Integer actual : operaciones) {
            System.out.println(actual + ": " + OpcionMenu.values()[actual]);
            valoresCorrectos.add(Integer.toString(actual));
        }

        String eleccion = leerValorCorrecto(valoresCorrectos);

        return Integer.parseInt(eleccion);
    }

    /**
     * Codigo el cual ejecuta el juego, y encargado de la vistaTextual
     *
     * @param args
     */
    public static void main(String[] args) {
        VistaTextualQytetet ui = new VistaTextualQytetet();
        ui.controlador.setNombreJugadores(ui.obtenerNombreJugadores());
        int operacionElegida, casillaElegida = 0;
        boolean necesitaElegirCasilla;
        do {
            operacionElegida = ui.elegirOperacion();
            necesitaElegirCasilla = ui.controlador.necesitaElegirCasilla(operacionElegida);
            if (necesitaElegirCasilla) {
                casillaElegida = ui.elegirCasilla(operacionElegida);
            }
            if (!necesitaElegirCasilla || casillaElegida >= 0) {
                System.out.println(ui.controlador.realizarOperacion(operacionElegida, casillaElegida));
            }
        } while (operacionElegida != 13); //Para terminar juego, la operacion 13 es TERMINARJUEGO
    }

}
