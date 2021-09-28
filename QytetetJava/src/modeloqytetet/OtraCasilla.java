package modeloqytetet;

/**
 * Clase OtraCasilla la cual hereda de la SuperClase Casilla, la cual determina
 * aquellas casillas que no son de tipo Calle
 *
 * @author cazz & sara
 */
public class OtraCasilla extends Casilla {

    /**
     * Contructor el cual inicializa todas las variables de OtraCasilla
     *
     * @param numeroCasilla numero de casilla
     * @param tipo tipo de la casilla
     */
    OtraCasilla(int numeroCasilla, TipoCasilla tipo) {
        this.numeroCasilla = numeroCasilla;
        this.coste = (tipo == TipoCasilla.IMPUESTO) ? -300 : 0; //Si el tipo de la casilla es de impuesto, se asigna un coste de -300, si no de 0
        this.tipo = tipo;
    }

    /**
     * Metodo toString el cual devuelve información referente a OtraCasilla
     * @return String con informacion
     */
    @Override
    public String toString() {
        if (tipo == TipoCasilla.SALIDA) {
            return "\n<ANDEN 9 3/4>"
                    + "\n\t•Numero de casilla = " + numeroCasilla
                    + "\n\t•Tipo = " + tipo;

        } else if (tipo == TipoCasilla.CARCEL) {
            return "\n<AZKABAN>"
                    + "\n\t•Numero de casilla = " + numeroCasilla
                    + "\n\t•Tipo = " + tipo;

        } else if (tipo == TipoCasilla.PARKING) {
            return "\n<SALA COMUN DE GRYFFINDOR>"
                    + "\n\t•Numero de casilla = " + numeroCasilla
                    + "\n\t•Tipo = " + tipo;

        } else {
            return "\n<CASILLA>"
                    + "\n\t•Numero de casilla = " + numeroCasilla
                    + "\n\t•Tipo = " + tipo;
        }
    }

}
