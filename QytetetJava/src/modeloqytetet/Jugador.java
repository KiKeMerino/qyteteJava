package modeloqytetet;

import java.util.ArrayList;

/**
 * Clase Jugador, la cual representa a un jugador de Qytetet, implementa ademas
 * la interfaz Comparable, para comparar dos jugadores.
 *
 * @author cazz & sara
 */
public class Jugador implements Comparable {

    private boolean encarcelado;
    private String nombre;
    private int saldo;
    //Variables de referencia
    private Casilla casillaActual;
    private Sorpresa cartaLibertad;
    private ArrayList<TituloPropiedad> propiedades;

    /**
     * Contructor de Jugador, inicializa todas las variables
     *
     * @param nombre
     */
    Jugador(String nombre) {
        this.nombre = nombre;
        this.saldo = 7500;
        this.encarcelado = false;
        casillaActual = null;
        cartaLibertad = null;
        propiedades = new ArrayList();
    }

    /**
     * Contructor de copia de Jugador, el cual es utilizado por Especulador a la
     * hora de convertirlo(crearlo)
     *
     * @param jugador jugador el cual quiere ser copiado.
     */
    protected Jugador(Jugador jugador) {
        this.nombre = jugador.nombre;
        this.saldo = jugador.saldo;
        this.encarcelado = jugador.encarcelado;
        this.casillaActual = jugador.casillaActual;
        this.cartaLibertad = jugador.cartaLibertad;
        this.propiedades = jugador.propiedades;
    }

    /**
     * Metodo que simula convertir un jugador en especulador, se crea un nuevo
     * especulador usando al jugador y la fianza.
     *
     * @param fianza Este parametro es obtenido de la carta del mazo, de tipo
     * CONVERTIRME, la cual sera la fianza del Especulador.
     * @return un objeto Especulador.
     */
    protected Especulador convertirme(int fianza) {
        Especulador especulador = new Especulador(this, fianza);

        return especulador;
    }

    /**
     * Determina si el Jugador debe ir a la carcel
     *
     * @return true si no tiene carta de liberta, false en caso contrario.
     */
    protected boolean deboIrACarcel() {
        return !tengoCartaLibertad();
    }

    /**
     * Metodo getCartaLibertad
     *
     * @return devuelve la cartaLiberta de tipo Sorpresa
     */
    Sorpresa getCartaLibertad() {
        return cartaLibertad;
    }

    /**
     * Metodo getCasillaActual
     *
     * @return devuelve la casilla actual donde se encuentra el Jugador
     */
    public Casilla getCasillaActual() {
        return casillaActual;
    }

    /**
     * Metodo getEncarcelado
     *
     * @return true si esta encarcelado, false si esta libre.
     */
    public boolean getEncarcelado() {
        return encarcelado;
    }

    /**
     * Metodo getNombre
     *
     * @return devuelve el nombre del Jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo getPropiedades
     *
     * @return devuelve un arrayList de las propiedades que posee el Jugador
     */
    ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }

    /**
     * Metodo getSaldo
     *
     * @return devuelve el saldo del Jugador
     */
    public int getSaldo() {
        return saldo;
    }

    /**
     * Metodo setCartaLibertad
     *
     * @param cartaLibertad asigna la cartaLibertad
     */
    void setCartaLibertad(Sorpresa cartaLibertad) {
        this.cartaLibertad = cartaLibertad;
    }

    /**
     * Metodo setCasillaActual
     *
     * @param casillaActual asigna la casillaActual
     */
    void setCasillaActual(Casilla casillaActual) {
        this.casillaActual = casillaActual;
    }

    /**
     * Meotdo setEncarcelado
     *
     * @param encarcelado asigna la variable encarcelado
     */
    void setEncarcelado(boolean encarcelado) {
        this.encarcelado = encarcelado;
    }

    /**
     * Cancela un titulo de propiedad hipotecado
     *
     * @param titulo titulo el cual se desea cancelar la hipoteca
     * @return true si se ha podido, false en caso contrario
     */
    boolean cancelarHipoteca(TituloPropiedad titulo) {
        int costeCancelar = titulo.calcularCosteCancelarHipoteca();
        if (saldo < costeCancelar) {
            return false;
        } else {
            modificarSaldo(-costeCancelar);
            titulo.cancelarHipoteca();
            return true;
        }
    }

    /**
     * Compra el titulo de propiedad de la Calle actual en la cual esta
     * posicionado el Jugador
     *
     * @return true si lo ha podido comprar, false en caso contrario
     */
    boolean comprarTituloPropiedad() {
        boolean comprado = false;
        int costeCompra = casillaActual.getCoste();

        if (costeCompra < saldo && !((Calle) casillaActual).tengoPropietario()) {
            TituloPropiedad titulo = ((Calle) casillaActual).asignarPropietario(this);
            comprado = true;
            propiedades.add(titulo);
            modificarSaldo(-costeCompra);
        }

        return comprado;
    }

    /**
     * Cuenta cuantas casas y hoteles tiene en todas sus propiedades
     *
     * @return el numero total de casas y hoteles.
     */
    int cuantasCasasHotelesTengo() {
        int total_viviendas = 0;

        for (TituloPropiedad actual : propiedades) {
            total_viviendas += (actual.getNumCasas() + actual.getNumHoteles());
        }

        return total_viviendas;
    }

    /**
     * Metodo el cual determina si tiene que pagarAlquiler si esta situado en
     * una casilla de tipo calle LA CONDICION ES, QUE NO SEA DE MI PROPIEDAD,
     * QUE TENGA PROPIETARIO, QUE NO ESTE ENCARCELADO, Y QUE NO ESTE HIPOTECADA
     *
     * @return true si se cumple la condicion, false en caso contrario
     */
    boolean deboPagarAlquiler() {
        boolean estaHipotecada = false;
        boolean encarcelad = false;
        boolean tienePropietario = false;

        TituloPropiedad titulo = ((Calle) casillaActual).getTitulo();
        boolean esDeMiPropiedad = esDeMiPropiedad(titulo);
        if (!esDeMiPropiedad) {
            tienePropietario = titulo.tengoPropietario();
            if (tienePropietario) {
                encarcelad = titulo.propietarioEncarcelado();
                if (!encarcelad) {
                    estaHipotecada = titulo.isHipotecada();
                }
            }
        }

        boolean deboPagar = !esDeMiPropiedad && tienePropietario && !encarcelad && !estaHipotecada;
        return deboPagar;
    }

    /**
     * Simula devolver la cartaLibertad al mazo
     *
     * @return Carta libertad
     */
    Sorpresa devolverCartaLibertad() {
        Sorpresa aux = cartaLibertad;
        cartaLibertad = null;

        return aux;
    }

    /**
     * Metodo el cual edifica una casa en un titulo de propiedad
     *
     * @param titulo al cual se va a edificar la casa
     * @return true si se pudo edificar, false en caso contrario.
     */
    boolean edificarCasa(TituloPropiedad titulo) {
        boolean edificada = false;

        if (puedoEdificarCasa(titulo)) {
            int costeEdificarCasa = titulo.getPrecioEdificar();
            boolean tengoSaldo = tengoSaldo(costeEdificarCasa);
            if (tengoSaldo) {
                titulo.edificarCasa();
                modificarSaldo(-costeEdificarCasa);
                edificada = true;
            }
        }
        return edificada;
    }

    /**
     * Metodo el cual edifica un hotel en un titulo de propiedad
     *
     * @param titulo al cual se va a edificar el hotel
     * @return true si se pudo edificar, false en caso contrario.
     */
    boolean edificarHotel(TituloPropiedad titulo) {
        boolean edificada = false;

        if (puedoEdificarHotel(titulo)) {
            int costeEdificarHotel = titulo.getPrecioEdificar();
            boolean tengoSaldo = tengoSaldo(costeEdificarHotel);
            if (tengoSaldo) {
                titulo.edificarHotel();
                modificarSaldo(-costeEdificarHotel);
                edificada = true;
            }
        }
        return edificada;
    }

    /**
     * Meotodo el cual determina si se puede edificar dicha casa, es un metodo
     * auxiliar de edificarCasa, puedo seguir edificando casas hasta un tope de
     * 4
     *
     * @param titulo Al cual se desea edificar la casa
     * @return true si puedo edificarla, false en caso contrario
     */
    protected boolean puedoEdificarCasa(TituloPropiedad titulo) {
        return (titulo.getNumCasas() < 4);
    }

    /**
     * Meotodo el cual determina si se puede edificar dicho hotel, es un metodo
     * auxiliar de edificarHotel, Puedo seguir edificando hoteles si tengo 4
     * casas y hasta un tope de 4 hoteles
     *
     * @param titulo Al cual se desea edificar el hotel
     * @return true si puedo edificarla, false en caso contrario
     */
    protected boolean puedoEdificarHotel(TituloPropiedad titulo) {
        return (titulo.getNumCasas() == 4 && titulo.getNumHoteles() < 4);
    }

    /**
     * Elimina del array de propiedades un titulo
     *
     * @param titulo titulo el cual sera eliminado de las propiedades del
     * Jugador
     */
    private void eliminarDeMisPropiedades(TituloPropiedad titulo) {
        propiedades.remove(titulo);
        titulo.setPropietario(null);
    }

    /**
     * Determina si un titulo es de mi propiedad (si esta contenido en mi
     * arrayList de propiedades)
     *
     * @param titulo titulo a determinar
     * @return true si es de mi propiedad, falso en caso contrario.
     */
    private boolean esDeMiPropiedad(TituloPropiedad titulo) {
        return propiedades.contains(titulo);
    }

    /**
     * Metodo el cual hipoteca un titulo pasado por argumento.
     *
     * @param titulo titulo a hipotecar
     */
    void hipotecarPropiedad(TituloPropiedad titulo) {
        int costeHipoteca = titulo.hipotecar();
        modificarSaldo(costeHipoteca);
    }

    /**
     * Metodo el cual envia al Jugador a la casilla de Carcel, lo establece como
     * encarcelado(true)
     *
     * @param casilla casilla de e la carcel, la cual se establecera como
     * casillaActual del Jugador
     */
    void irACarcel(Casilla casilla) {
        setCasillaActual(casilla);
        setEncarcelado(true);
    }

    /**
     * Modifica el atributo saldo del Jugador, sumandole la cantidad pasada por
     * argumento
     *
     * @param cantidad a sumar al sado
     * @return el saldo modificado
     */
    int modificarSaldo(int cantidad) {
        return saldo += cantidad;
    }

    /**
     * Calcula el capital que tiene un jugador, este capital se utiliza para
     * determinar el ranking de jugadores, quien posea mas capital es el
     * ganador.
     *
     * @return un entero el cual es el capital.
     */
    int obtenerCapital() {
        int capital = saldo;

        for (TituloPropiedad actual : propiedades) {
            int numero_viviendas_casilla = actual.getNumCasas() + actual.getNumHoteles();
            capital += actual.getPrecioCompra() + actual.getPrecioEdificar() * numero_viviendas_casilla;

            if (actual.isHipotecada()) {
                capital -= actual.getHipotecaBase();
            }
        }

        return capital;
    }

    /**
     * Obtiene aquellas propiedades, las cuales estan o no estan hipotecadas
     * segun el parametro hipotecada
     *
     * @param hipotecada true para obtener propiedades hipotecadas, false para
     * obtener propiedades sin hipotecar
     * @return un ArrayList de TituloPropiedad con aquellas propiedades que
     * cumplen lo anterior.
     */
    ArrayList<TituloPropiedad> obtenerPropiedades(boolean hipotecada) {
        ArrayList<TituloPropiedad> propiedades_segun_hipoteca = new ArrayList();

        for (TituloPropiedad actual : propiedades) {
            if (actual.isHipotecada() == hipotecada) {
                propiedades_segun_hipoteca.add(actual);
            }
        }

        return propiedades_segun_hipoteca;
    }

    /**
     * Paga el alquiler de una calle donde esta posicionado el Jugador, y se le
     * modifica el saldo
     */
    void pagarAlquiler() {
        int costeAlquiler = ((Calle) casillaActual).PagarAlquiler();
        modificarSaldo(-costeAlquiler);
    }

    /**
     * Si cae en una casilla de tipo Impuesto, se le modificara el saldo,
     * restandole dicho impuesto
     */
    void pagarImpuesto() {
        modificarSaldo(casillaActual.getCoste());
    }

    /**
     * Metodo el cual nos libera de la carcel, si pagamos el precio de la
     * Libertad
     *
     * @param cantidad PRECIO DE LA LIBERTAD, EL CUAL ES 200
     */
    void pagarLibertad(int cantidad) {
        boolean tengoSaldo = tengoSaldo(cantidad);
        if (tengoSaldo) {
            setEncarcelado(false);
            modificarSaldo(-cantidad);
        }
    }

    /**
     * Metodo el cual determina si tengo la cartaLibertad
     *
     * @return true si la tengo, false en caso contrario
     */
    boolean tengoCartaLibertad() {
        return cartaLibertad != null;
    }

    /**
     * Metodo que determina si tengo un saldo superior al pasado por argumento
     *
     * @param cantidad la cual compara con nuestro saldo
     * @return true si mi saldo supera tal cantidad, en caso contrario false
     */
    protected boolean tengoSaldo(int cantidad) {
        return saldo > cantidad;
    }

    /**
     * Vende una propiedad, a partir de su casilla, se extrae el titulo, se
     * elimina de mis propiedades, y se suma a mi saldo el precio al cual lo he
     * vendido
     *
     * @param casilla la cual se quiere vender
     */
    void venderPropiedad(Casilla casilla) {
        TituloPropiedad titulo = ((Calle) casilla).getTitulo();
        eliminarDeMisPropiedades(titulo);
        int precioVenta = titulo.calcularPrecioVenta();
        titulo.setNumCasas(0);
        titulo.setNumHoteles(0);
        modificarSaldo(precioVenta);
    }

    /**
     * Metodo CompareTo sobrecargado, para así comparar Jugadores por su
     * capital, utilizado en obtenerRanking
     *
     * @param otroJugador otroJugador con el que se desea comparar.
     * @return 1, si es mayor, 0 si son iguales, -1 si es menor
     */
    @Override
    public int compareTo(Object otroJugador) {
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return otroCapital - obtenerCapital();
    }

    /**
     * Metodo toString, el cual se encarga de devolver la información referente
     * al Jugador
     *
     * @return String con información.
     */
    @Override
    public String toString() {
        return "\nJUGADOR: " + nombre
                + "\n•Encarcelado: " + ((encarcelado) ? "SI" : "NO")
                + "\n•Saldo: " + saldo
                + "\n•Capital del jugador: " + obtenerCapital()
                + "\n•Casilla actual: " + ((casillaActual == null) ? "NINGUNA" : casillaActual)
                + "\n•CartaLibertad: " + ((cartaLibertad == null) ? "NO" : "SI")
                + "\n•Propiedades: " + ((propiedades.size() == 0) ? "No tiene propiedades" : "\n" + propiedades.toString()) + "\n";
    }

}
