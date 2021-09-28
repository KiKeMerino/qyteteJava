package modeloqytetet;

/**
 * Clase Especulador la cual hereda de la Superclase Jugador
 *
 * @author cazz & sara
 */
public class Especulador extends Jugador {

    private int fianza;

    /**
     * Contructor de la clase Especulador el cual llama al de la de Jugador
     * mediante super, inicializando los atributos.
     *
     * @param jugador
     * @param fianza
     */
    protected Especulador(Jugador jugador, int fianza) {
        super(jugador);
        this.fianza = fianza;
    }

    /**
     * Metodo sobrescrito de Jugador, paga la mitad del impuesto, ya que es
     * Especulador
     */
    @Override
    protected void pagarImpuesto() {
        int impuesto = getCasillaActual().getCoste();
        impuesto /= 2;
        modificarSaldo(impuesto);
    }

    /**
     * Metodo sobrescrito de Jugador, el cual llama al de Jugador mediante
     * super, si no se tiene la cartaLibertad y no se puede pagar la fianza se
     * debe ir a la carcel
     *
     * @return un booleano si se cumple lo descrito ira a la carcel (true) en
     * cambio no (false)
     */
    @Override
    protected boolean deboIrACarcel() {
        return super.deboIrACarcel() && !pagarFianza();
    }

    /**
     * Metodo sobrescrito de Jugador, el cual se convierte en Especulador, pero
     * como ya es Especulador se devuelve así mismo
     *
     * @param fianza en este metodo no sirve de nada
     * @return al propio especulador
     */
    @Override
    protected Especulador convertirme(int fianza) {
        return this;
    }

    /**
     * Metodo el cual determina si puede pagar la fianza, y se le resta de su
     * saldo.
     *
     * @return si puede pagarla (true) si no (false)
     */
    private boolean pagarFianza() {
        boolean pagar_fianza = false;

        if (tengoSaldo(fianza)) {
            modificarSaldo(-fianza);
            pagar_fianza = true;
        }

        return pagar_fianza;
    }

    /**
     * Metodo sobrescrito de Jugador, el cual determina la condicion de se puede
     * edificar una casa en un titulo de propiedad mientras el numero de casas
     * sea menor que 8 se puede seguir edificando casas
     *
     * @param titulo El titulo en el cual se desea consultar si se puede
     * edificar la casa
     * @return true si se puede, false si no.
     */
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad titulo) {
        return (titulo.getNumCasas() < 8);
    }

    /**
     * Metodo sobrescrito de Jugador, el cual determina la condicion de se puede
     * edificar un hotel en un titulo de propiedad mientras el numero de casas
     * sea mayor que 4 y el de hoteles menor que 8 se puede seguir edificando
     * hoteles
     *
     * @param titulo El titulo en el cual se desea consultar si se puede
     * edificar el hotel
     * @return true si se puede, false si no.
     */
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad titulo) {
        return (titulo.getNumCasas() > 4 && titulo.getNumHoteles() < 8);
    }

    /**
     * Metodo toString el cual imprime toda la información sobre Especulador,
     * llama al toString de Jugador mediante super
     *
     * @return String con información
     */
    @Override
    public String toString() {
        return "\n-----------\nESPECULADOR\n-----------"
                + "\nFianza: " + fianza
                + super.toString();
    }

}
