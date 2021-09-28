package modeloqytetet;

import java.util.ArrayList;

/**
 * Clase Tablero la cual es encargada de gestionar el tablero, contendra todas
 * las casillas.
 *
 * @author cazz & sara
 */
public class Tablero {

    private ArrayList<Casilla> casillas;
    private Casilla carcel;

    /**
     * Inicializa el arraylist de casillas, con un total de 20 casillas creadas.
     */
    private void inicializar() {
        casillas = new ArrayList();
        carcel = new OtraCasilla(5, TipoCasilla.CARCEL);

        casillas.add(new OtraCasilla(0, TipoCasilla.SALIDA));
        casillas.add(new Calle(1, new TituloPropiedad("Número 4 de Privet Drive", 350, 50, 10, 150, 250)));
        casillas.add(new OtraCasilla(2, TipoCasilla.SORPRESA));
        casillas.add(new Calle(3, new TituloPropiedad("Callejon Diagon", 500, 55, 12, 230, 250)));
        casillas.add(new Calle(4, new TituloPropiedad("Colegio de Hogwarts", 650, 60, 14, 310, 350)));
        casillas.add(carcel);
        casillas.add(new Calle(6, new TituloPropiedad("La Madriguera", 800, 65, 16, 390, 350)));
        casillas.add(new Calle(7, new TituloPropiedad("Hogsmeade", 950, 70, 18, 470, 450)));
        casillas.add(new OtraCasilla(8, TipoCasilla.SORPRESA));
        casillas.add(new Calle(9, new TituloPropiedad("Bosque Prohibido", 1100, 75, 20, 550, 450)));
        casillas.add(new OtraCasilla(10, TipoCasilla.PARKING));
        casillas.add(new Calle(11, new TituloPropiedad("Biblioteca", 1250, 80, -10, 630, 550)));
        casillas.add(new Calle(12, new TituloPropiedad("Camara de los secretos", 1400, 85, -12, 710, 550)));
        casillas.add(new OtraCasilla(13, TipoCasilla.IMPUESTO));
        casillas.add(new Calle(14, new TituloPropiedad("Despacho de Dumblendore", 1550, 90, -14, 790, 650)));
        casillas.add(new OtraCasilla(15, TipoCasilla.JUEZ));
        casillas.add(new Calle(16, new TituloPropiedad("Campo de Quidditch", 1700, 95, -16, 870, 650)));
        casillas.add(new OtraCasilla(17, TipoCasilla.SORPRESA));
        casillas.add(new Calle(18, new TituloPropiedad("Mansion Riddle", 1850, 100, -18, 950, 750)));
        casillas.add(new Calle(19, new TituloPropiedad("Ministerio de Magia", 200, 100, -20, 1000, 750)));

    }

    /**
     * Constructor de Tablero, el cual llama al metodo inicializar, inicializa
     * el tablero, todas sus casillas.
     */
    Tablero() {
        inicializar();
    }

    /**
     * Determina si el numero de la casilla pasado por argumento es de tipo
     * carcel
     *
     * @param numeroCasilla numero de casilla a comprobar
     * @return true si coincide con el numero casilla de la carcel, false en
     * caso contrario.
     */
    boolean esCasillaCarcel(int numeroCasilla) {
        return (carcel.getNumeroCasilla() == numeroCasilla);
    }

    /**
     * Obtiene una casillaFinal, a raiz de una casilla, y su desplazamiento.
     *
     * @param casilla Casilla actual de donde parte al desplazar
     * @param desplazamiento numero entero, el cual representa cuantas casillas
     * desplazarse
     * @return Una nueva casilla, destino.
     */
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento) {
        int numero_nueva_casilla = (casilla.getNumeroCasilla() + desplazamiento) % casillas.size();
        return obtenerCasillaNumero(numero_nueva_casilla);
    }

    /**
     * Obtiene una casilla mediante su numero
     *
     * @param numeroCasilla numero de casilla
     * @return Una Casilla
     */
    Casilla obtenerCasillaNumero(int numeroCasilla) {
        return casillas.get(numeroCasilla);
    }

    /**
     * Devuelve el tablero entero
     *
     * @return Un arraylist de casillas
     */
    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    /**
     * Metodo getCarcel
     *
     * @return la casilla carcel.
     */
    public Casilla getCarcel() {
        return carcel;
    }

    /**
     * Metodo toString, devuelve la información del tablero, osea de todas las
     * casillas creadas, ya que devuelve tambien la informacion de cada una de
     * las casillas
     *
     * @return String con informacion
     */
    @Override
    public String toString() {
        return "\nTABLERO: "
                + casillas.toString();
    }

}
