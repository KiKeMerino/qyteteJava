package modeloqytetet;

/**
 * Clase Calle la cual hereda de la superclase Casilla, la cual determina aquellas casillas que son de tipo Calle.
 *
 * @author cazz & sara
 */
public class Calle extends Casilla {

    //Variables de referencia
    private TituloPropiedad titulo;

    /**
     * Contructor de calle encargado de inicializar los atributos
     *
     * @param numeroCasilla Casilla de la calle
     * @param titulo Titulo de propiedad correspondiente a la calle
     */
    Calle(int numeroCasilla, TituloPropiedad titulo) {
        this.numeroCasilla = numeroCasilla;
        this.coste = titulo.getPrecioCompra();
        this.tipo = TipoCasilla.CALLE;
        setTitulo(titulo);
    }

    /**
     * Metodo getTitulo
     *
     * @return el titulo de propiedad de la calle
     */
    public TituloPropiedad getTitulo() {
        return titulo;
    }

    /**
     * Meotodo setTitulo
     *
     * @param titulo titulo a establecer
     */
    private void setTitulo(TituloPropiedad titulo) {
        this.titulo = titulo;
    }

    /**
     * Asigna a un titulo un propietario(Jugador)
     *
     * @param jugador El jugador que se asignara al titulo
     * @return El titulo el cual es asignado
     */
    TituloPropiedad asignarPropietario(Jugador jugador) {
        titulo.setPropietario(jugador);
        return titulo;
    }

    /**
     * Metodo que el cual devuelve el coste de pagar el alquiler de un titulo
     *
     * @return Un entero el cual es el coste del alquiler
     */
    int PagarAlquiler() {
        int costeAlquiler = titulo.pagarAlquiler();
        return costeAlquiler;
    }

    /**
     * Determina si el propietario del titulo esta encarcelado
     *
     * @return un booleano true si esta encarcelado, false en el otro caso
     */
    boolean propietarioEncarcelado() {
        return titulo.propietarioEncarcelado();
    }

    /**
     * Determina si el titulo asociado a la calle tiene propietario o no
     *
     * @return booleano true si tiene propietario, false en el otro caso
     */
    boolean tengoPropietario() {
        return titulo.tengoPropietario();
    }

    /**
     * Impresion de la Calle
     *
     * @return un String con toda la informacion referente a la Calle
     */
    @Override
    public String toString() {
        return "\n<CALLE: " + titulo.getNombre() + ">"
                + "\n\t•Numero: " + numeroCasilla
                + "\n" + titulo.toString();
    }

}
