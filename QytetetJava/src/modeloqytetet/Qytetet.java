package modeloqytetet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Clase Qytetet encargada de gestionar todo lo referente al juego. Solo hay una
 * unica instancia de la clase (SINGLETON), solo puede haber un unico juego.
 *
 * @author cazz & sara
 */
public class Qytetet {

    public static final int MAXJUGADORES = 4;
    static final int NUM_SORPRESAS = 10;
    public static final int NUM_CASILLAS = 20;
    static final int PRECIO_LIBERTAD = 200;
    final int SALDO_SALIDA = 1000;
    private static final Qytetet instance = new Qytetet();
    //Variables de referencia
    private Sorpresa cartaActual;
    private ArrayList<Jugador> jugadores;
    private Jugador jugadorActual;
    private ArrayList<Sorpresa> mazo;
    private Tablero tablero;
    private Dado dado = Dado.getInstance();
    private EstadoJuego estadoJuego;

    /**
     * Metodo el cual actua si se cae en una casilla de tipo edificable(CALLE)
     */
    void actuarSiEnCasillaEdificable() {
        boolean deboPagar = jugadorActual.deboPagarAlquiler();

        if (deboPagar) {
            jugadorActual.pagarAlquiler();
            if (jugadorActual.getSaldo() <= 0) {
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
        }

        Casilla casilla = obtenerCasillaJugadorActual();
        boolean tengoPropietario = ((Calle) casilla).tengoPropietario();

        if (estadoJuego != EstadoJuego.ALGUNJUGADORENBANCARROTA) {
            if (tengoPropietario) {
                setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
            } else {
                setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
            }
        }
    }

    /**
     * Metodo el cual actua si se cae en una casilla de tipo no
     * edificable(OTRACASILLA)
     */
    void actuarSiEnCasillaNoEdificable() {
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        Casilla casillaActual = jugadorActual.getCasillaActual();

        if (casillaActual.getTipo() == TipoCasilla.IMPUESTO) {
            jugadorActual.pagarImpuesto();
            if (jugadorActual.getSaldo() <= 0) {
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }
        } else {
            if (casillaActual.getTipo() == TipoCasilla.JUEZ) {
                encarcelarJugador();
            } else if (casillaActual.getTipo() == TipoCasilla.SORPRESA) {
                cartaActual = mazo.remove(0);
                setEstadoJuego(EstadoJuego.JA_CONSORPRESA);
            }
        }
    }

    /**
     * Metodo el cual aplica una sorpresa si hemos caido en una casilla de este
     * tipo. Segun el tipo de sorpresa que se obtenga de la cartaActual del
     * mazo, realizaremos una accion u otra.
     */
    public void aplicarSorpresa() {
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        if (cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL) {
            jugadorActual.setCartaLibertad(cartaActual);
        } else {
            mazo.add(cartaActual);
            if (cartaActual.getTipo() == TipoSorpresa.PAGARCOBRAR) {
                jugadorActual.modificarSaldo(cartaActual.getValor());
                if (jugadorActual.getSaldo() <= 0) {
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                }
            } else if (cartaActual.getTipo() == TipoSorpresa.IRACASILLA) {
                int valor = cartaActual.getValor();
                boolean casillaCarcel = tablero.esCasillaCarcel(valor);
                if (casillaCarcel) {
                    encarcelarJugador();
                } else {
                    mover(valor);
                }
            } else if (cartaActual.getTipo() == TipoSorpresa.PORCASAHOTEL) {
                int cantidad = cartaActual.getValor();
                int numeroTotal = jugadorActual.cuantasCasasHotelesTengo();
                jugadorActual.modificarSaldo(cantidad * numeroTotal);
                if (jugadorActual.getSaldo() <= 0) {
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                }

            } else if (cartaActual.getTipo() == TipoSorpresa.PORJUGADOR) {
                for (Jugador actual : jugadores) {
                    if (actual != jugadorActual) {
                        actual.modificarSaldo(-cartaActual.getValor());
                        if (actual.getSaldo() <= 0) {
                            setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                        }
                        jugadorActual.modificarSaldo(cartaActual.getValor());
                        if (jugadorActual.getSaldo() <= 0) {
                            setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                        }
                    }
                }
            } else if (cartaActual.getTipo() == TipoSorpresa.CONVERTIRME) {
                int posicion = jugadores.indexOf(jugadorActual);
                Especulador especulador = jugadorActual.convertirme(cartaActual.getValor());
                jugadores.set(posicion, especulador);
                jugadorActual = especulador;
            }
        }

    }

    /**
     * Cancela la hipoteca de un titulo de propiedad
     *
     * @param numeroCasilla numero de la casilla la cual se quiere cancelar la
     * hipoteca
     * @return true si se ha podido cancelar, false en caso contrario
     */
    public boolean cancelarHipoteca(int numeroCasilla) {
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = ((Calle) casilla).getTitulo();
        boolean cancelacion = jugadorActual.cancelarHipoteca(titulo);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        return cancelacion;
    }

    /**
     * Compra el titulo de propiedad, donde ha caido el jugadorActual
     *
     * @return true si se ha podido comprar, false si no se ha podido comprar
     */
    public boolean comprarTituloPropiedad() {
        boolean comprado = jugadorActual.comprarTituloPropiedad();
        if (comprado) {
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
        return comprado;
    }

    /**
     * Edifica una casa en un titulo propiedad
     *
     * @param numeroCasilla numero de la casilla la cual se quiere edificar una
     * casa
     * @return true si se ha podido edificar una casa, false en caso contrario.
     */
    public boolean edificarCasa(int numeroCasilla) {
        boolean edificada = false;
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = ((Calle) casilla).getTitulo();
        edificada = jugadorActual.edificarCasa(titulo);

        if (edificada) {
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }

        return edificada;
    }

    /**
     * Edifica un hotel en un titulo de propiedad
     *
     * @param numeroCasilla numero de la casilla la cual se quiere edificar una
     * casa
     * @return true si se ha podido edificar un hotel, false en caso contrario.
     */
    public boolean edificarHotel(int numeroCasilla) {
        boolean edificada = false;
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = ((Calle) casilla).getTitulo();
        edificada = jugadorActual.edificarHotel(titulo);

        if (edificada) {
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }

        return edificada;
    }

    /**
     * Metodo el cual encarcela al jugadorActual, ya que le ha tocado una
     * casilla de tipo JUEZ, o una carta Sorpresa la cual lo mande a la carcel
     */
    private void encarcelarJugador() {
        if (jugadorActual.deboIrACarcel()) {
            Casilla casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        } else {
            Sorpresa carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
    }

    /**
     * Metodo getCartaActual
     *
     * @return obtiene la cartaActual que se ha obtenido del arrayList mazo
     */
    public Sorpresa getCartaActual() {
        return cartaActual;
    }

    /**
     * Devuelve el objeto dado
     *
     * @return la unica instancia del dado creada.
     */
    Dado getDado() {
        return dado;
    }

    /**
     * Metodo getJugadorActual
     *
     * @return devuelve el jugador que tiene el turno, osea el actual.
     */
    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    /**
     * Metodo getJugadores
     *
     * @return devuelve el arraylist jugadores, que contiene a todos los
     * jugadores que estan jugando a qytetet
     */
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Metodo getMazo
     *
     * @return devuelve el arraylist mazo, el cual contiene todas las cartas
     * Sorpresa creadas
     */
    ArrayList<Sorpresa> getMazo() {
        return mazo;
    }

    /**
     * Metodo getEstadoJuego
     *
     * @return devuelve un enum, el cual es el estado actual del juego.
     */
    public EstadoJuego getEstadoJuego() {
        return estadoJuego;
    }

    /**
     * Metodo getValorDado
     *
     * @return devuelve el ultimo valor de haber tirado el dado.
     */
    public int getValorDado() {
        return dado.getValor();
    }

    /**
     * Hipoteca una propiedad, del jugadorActual
     *
     * @param numeroCasilla numero de casilla la cual se quiere hipotecar su
     * titulo.
     */
    public void hipotecarPropiedad(int numeroCasilla) {
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = ((Calle) casilla).getTitulo();
        jugadorActual.hipotecarPropiedad(titulo);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
    }

    /**
     * Inicializa el arrayList mazo, y lo rellena con Cartas Sorpresa creadas.
     */
    private void inicializarCartasSorpresa() {
        mazo = new ArrayList();
        //CONVERTIRME
        mazo.add(new Sorpresa("¡Te has convertido en especulador!", 3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("¡¡Te has convertido en especulador!!", 5000, TipoSorpresa.CONVERTIRME));
        //PAGARCOBRAR
        mazo.add(new Sorpresa("Recibes una herencia, Gringotts te reporta 1000€", 1000, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Te han pillado conduciendo el coche volador, paga la multa de 2000€", -2000, TipoSorpresa.PAGARCOBRAR));
        //IRACASILLA
        mazo.add(new Sorpresa("Los dementores te han pillado, ¡debes ir a Azkaban!", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Hagrid, te envia de mision al ", 9, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Encuentras un portal y apareces en ", 19, TipoSorpresa.IRACASILLA));
        //PORCASAHOTEL
        mazo.add(new Sorpresa("El ejercito oscuro ataco tus propiedades, reforma, pague por cada edificio 250€", -250, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Sus edificios reportan un beneficio de 100€ por cada uno", 100, TipoSorpresa.PORCASAHOTEL));
        //PORJUGADOR
        mazo.add(new Sorpresa("Has ganado el torneo de ajedrez magico, recibe de cada jugador 500€", 500, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa("Has sido encantado, das 250€ a cada jugador", -250, TipoSorpresa.PORJUGADOR));
        //SALIRCARCEL
        mazo.add(new Sorpresa("Sirius te envia la capa de invisibilidad y has podido escapar de Azkaban", 0, TipoSorpresa.SALIRCARCEL));

        Collections.shuffle(mazo); //Para mezclar el mazo
    }

    /**
     * Inicializa el juego, sus jugadores, el tablero, las cartas sorpresa y
     * situa a los jugadores en la casilla salida, determinando el jugador
     * actual.
     *
     * @param nombres arraylist de los nombres de los jugadores
     */
    public void inicializarJuego(ArrayList<String> nombres) {
        inicializarJugadores(nombres);
        inicializarTablero();
        inicializarCartasSorpresa();
        salidaJugadores();
    }

    /**
     * Inicializa la variable jugadores, creando instancias de jugador a partir
     * del nombre de los jugadores pasado por argumento, y los añade a jugadores
     *
     * @param nombres nombres de los jugadores
     */
    private void inicializarJugadores(ArrayList<String> nombres) {
        this.jugadores = new ArrayList();

        for (String nombre : nombres) {
            jugadores.add(new Jugador(nombre));

        }
    }

    /**
     * Inicializa la variable tablero, creando todas las casillas, de tipo CALLE
     * y OtraCasilla
     */
    private void inicializarTablero() {
        tablero = new Tablero();
    }

    /**
     * Metodo para intentarSalir de la carcel segun el metodo escogido
     *
     * @param metodo enumerado el cual sera el metodo escogido para salir de la
     * carcel
     * @return true si se ha conseguido salir de la carcel, false en caso
     * contrario.
     */
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo) {
        if (metodo == MetodoSalirCarcel.TIRANDODADO) {
            int resultado = tirarDado();
            if (resultado >= 5) {
                jugadorActual.setEncarcelado(false);
            }
        } else if (metodo == MetodoSalirCarcel.PAGANDOLIBERTAD) {
            jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
        }

        boolean encarcelado = jugadorActual.getEncarcelado();
        if (encarcelado) {
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        } else {
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
        }

        return encarcelado; //Aunque pone un ! en el diagrama
    }

    /**
     * Metodo que simula el jugarl, tira el dado y se mueve a la casilla destino
     */
    public void jugar() {
        Casilla casillaFinal = tablero.obtenerCasillaFinal(jugadorActual.getCasillaActual(), tirarDado());
        mover(casillaFinal.getNumeroCasilla());
    }

    /**
     * Metodo auxiliar usado en jugar, para mover al jugadorActual a la casilla
     * deseada(destino)
     *
     * @param numCasillaDestino
     */
    public void mover(int numCasillaDestino) {
        Casilla casillaInicial = jugadorActual.getCasillaActual();
        Casilla casillaFinal = tablero.obtenerCasillaNumero(numCasillaDestino);
        jugadorActual.setCasillaActual(casillaFinal);

        if (numCasillaDestino < casillaInicial.getNumeroCasilla()) {
            jugadorActual.modificarSaldo(SALDO_SALIDA);
        }

        if (casillaFinal.soyEdificable()) {
            actuarSiEnCasillaEdificable();
        } else {
            actuarSiEnCasillaNoEdificable();
        }
    }

    /**
     * Obtiene la casilla en la que esta posicionado el jugadorActual
     *
     * @return Casilla en la que se situa el jugadorActual
     */
    public Casilla obtenerCasillaJugadorActual() {
        return jugadorActual.getCasillaActual();
    }

    /**
     * Devuelve el tablero
     *
     * @return devuelve todas las casillas del tablero
     */
    public ArrayList<Casilla> obtenerCasillasTablero() {
        return tablero.getCasillas();
    }

    /**
     * Obtiene todas las propiedades que tiene el JugadorActual en su posesion
     *
     * @return el numero de aquellas casillas que son propiedad del
     * jugadorActual
     */
    public ArrayList<Integer> obtenerPropiedadesJugador() {
        ArrayList<Integer> propiedades = new ArrayList();

        for (Casilla actual : tablero.getCasillas()) {
            if (actual.soyEdificable()) {
                if (((Calle) actual).getTitulo().getPropietario() == jugadorActual) {
                    propiedades.add(actual.getNumeroCasilla());
                }
            }

        }

        return propiedades;
    }

    /**
     * Obtiene todas las propiedades que tiene el JugadorActual segun el estado
     * de la hipoteca pasado por paramtro
     *
     * @param estadoHipoteca true si estado del titulo es hipotecado, false si
     * el estado no es hipotecado
     * @return el numero de aquellas casillas que son propiedad del jugador
     * segun el estadoHipoteca
     */
    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca) {
        ArrayList<Integer> propiedades_segun_hipoteca = new ArrayList();

        for (Casilla actual : tablero.getCasillas()) {
            if (actual.soyEdificable()) {
                if (((Calle) actual).getTitulo().getPropietario() == jugadorActual && ((Calle) actual).getTitulo().isHipotecada() == estadoHipoteca) {
                    propiedades_segun_hipoteca.add(actual.getNumeroCasilla());
                }
            }
        }

        return propiedades_segun_hipoteca;
    }

    /**
     * Obtiene el ranking de los jugadores, ordenandolos, segun su capital, y
     * determina a un ganador.
     */
    public void obtenerRanking() {
        Collections.sort(jugadores);
        System.out.println("\nJUEGO TERMINADO: (CLASIFICACION): ");
        System.out.println(jugadores.toString());
        System.out.println("\n¡GANAD@R: " + jugadores.get(0).getNombre() + "!\n");
    }

    /**
     * Obtiene el saldo del jugadorActual
     *
     * @return el saldo del jugadorActual
     */
    public int obtenerSaldoJugadorActual() {
        return jugadorActual.getSaldo();
    }

    /**
     * Situa a todos los jugadores en la casilla de SALIDA, y se elige
     * aleatoriamente al jugador que jugara primero.
     */
    private void salidaJugadores() {
        for (Jugador actual : jugadores) {
            actual.setCasillaActual(tablero.obtenerCasillaNumero(0));
        }

        Random r = new Random();
        int aleatorio = r.nextInt(jugadores.size());
        jugadorActual = jugadores.get(aleatorio);

        estadoJuego = EstadoJuego.JA_PREPARADO;
    }

    /**
     * Metodo setCartaActual
     *
     * @param cartaActual establece la cartaActual, que sale del mazo
     */
    private void setCartaActual(Sorpresa cartaActual) {
        this.cartaActual = cartaActual;
    }

    /**
     * Metodo setEstadoJuego
     *
     * @param estadoJuego establece el estadoJuego
     */
    public void setEstadoJuego(EstadoJuego estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    /**
     * Siguiente jugador, en el array de jugadores, que le toque jugar.
     */
    public void siguienteJugador() {
        int posicion = jugadores.indexOf(jugadorActual);
        jugadorActual = jugadores.get((posicion + 1) % jugadores.size());

        estadoJuego = (jugadorActual.getEncarcelado()) ? EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD : EstadoJuego.JA_PREPARADO;
    }

    /**
     * Simula tirar el dado
     *
     * @return valor del dado
     */
    int tirarDado() {
        return dado.tirar();
    }

    /**
     * Vende un titulo propiedad del jugadorActual
     *
     * @param numeroCasilla numero de la casilla que se quiere vender
     */
    public void venderPropiedad(int numeroCasilla) {
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        jugadorActual.venderPropiedad(casilla);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
    }

    /**
     * Metodo getInstance el cual devuelve la unica instancia de Qytetet, ya que
     * solo puede haber un unico juego.
     *
     * @return la instancia unica Qytetet
     */
    public static Qytetet getInstance() {
        return instance;
    }

    /**
     * Metodo toString, devuelve la información sobre el juego.
     *
     * @return String con información
     */
    @Override
    public String toString() {
        return "\nQYTETET: "
                + "\nJUGADORES: " + jugadores.toString()
                + "\nMAZO: " + mazo.toString()
                + "\nTABLERO: " + tablero.toString();
    }

}
