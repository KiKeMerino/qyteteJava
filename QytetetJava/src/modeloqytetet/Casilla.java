package modeloqytetet;

/**
 * Clase abstracta Casilla, Superclase de Calle y OtraCasilla
 *
 * @author cazz & sara
 */
public abstract class Casilla {

    protected int numeroCasilla;
    protected int coste;
    //Variables de referencia
    protected TipoCasilla tipo;

    /**
     * Metodo getCoste
     *
     * @return un entero el coste de la Casilla
     */
    int getCoste() {
        return coste;
    }

    /**
     * Metodo getNumeroCasilla
     *
     * @return un entero el numero de la Casilla
     */
    int getNumeroCasilla() {
        return numeroCasilla;
    }

    /**
     * Metodo setCoste
     *
     * @param coste para establecer el coste de la Casilla
     */
    public void setCoste(int coste) {
        this.coste = coste;
    }

    /**
     * Metodo getTipo
     *
     * @return un enumerado TipoCasilla, el cual contiene de que tipo es
     */
    TipoCasilla getTipo() {
        return tipo;
    }

    /**
     * Metodo determina si un Casilla es de tipo Calle(Contiene titulo de
     * propiedad) o no.
     *
     * @return booleano si es de tipo Calle
     */
    public boolean soyEdificable() {
        return tipo == TipoCasilla.CALLE;
    }

}
