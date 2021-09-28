package controladorqytetet;

import java.util.ArrayList;
import java.util.Collections;
import modeloqytetet.Calle;
import modeloqytetet.MetodoSalirCarcel;
import modeloqytetet.Qytetet;

/**
 * Clase ControladorQytetet intermediaria entre la vista y el modelo, para su
 * control (SINGLETON)
 *
 * @author cazz & sara
 */
public class ControladorQytetet {

    private ArrayList<String> nombreJugadores;
    private static final ControladorQytetet instance = new ControladorQytetet();
    //Variables de referencia
    private Qytetet modelo;

    /**
     * Constructor de la clase ControladorQytetet, el cual es privado ya que es
     * Singleton(solamente puede tener una instancia de la clase), inicializa
     * las demas variables.
     */
    private ControladorQytetet() {
        this.nombreJugadores = new ArrayList();
        this.modelo = Qytetet.getInstance();
    }

    /**
     * Devuelve la unica instancia de la clase ControladorQytetet, ya que es un
     * singleton.
     *
     * @return la unica instancia del controlador
     */
    public static ControladorQytetet getInstance() {
        return instance;
    }

    /**
     * Establece el nombre de los jugadores
     *
     * @param nombreJugadores un ArrayList de String conteniendo el nombre de
     * los jugadores
     */
    public void setNombreJugadores(ArrayList<String> nombreJugadores) {
        this.nombreJugadores = nombreJugadores;
    }

    /**
     * Obtiene las operaciones validas de juego,(opciones de menu que se pueden
     * realizar), segun el EstadoJuego actual.
     *
     * @return Devuelve un ArrayList de enteros, con el numero de las
     * operaciones que se pueden realizar ordenadas.
     */
    public ArrayList<Integer> obtenerOperacionesJuegoValidas() {
        ArrayList<Integer> operaciones = new ArrayList();

        if (modelo.getJugadores() == null) { //Si no hay jugadores no ha empezado
            operaciones.add(OpcionMenu.INICIARJUEGO.ordinal());
        } else {
            switch (modelo.getEstadoJuego()) {
                case JA_PREPARADO:
                    operaciones.add(OpcionMenu.JUGAR.ordinal());
                    break;
                case JA_PUEDEGESTIONAR:
                    operaciones.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    operaciones.add(OpcionMenu.EDIFICARCASA.ordinal());
                    operaciones.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    operaciones.add(OpcionMenu.PASARTURNO.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
                case JA_PUEDECOMPRAROGESTIONAR:
                    operaciones.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
                    operaciones.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                    operaciones.add(OpcionMenu.EDIFICARCASA.ordinal());
                    operaciones.add(OpcionMenu.EDIFICARHOTEL.ordinal());
                    operaciones.add(OpcionMenu.PASARTURNO.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
                case JA_CONSORPRESA:
                    operaciones.add(OpcionMenu.APLICARSORPRESA.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
                case ALGUNJUGADORENBANCARROTA:
                    operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
                    break;
                case JA_ENCARCELADO:
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    operaciones.add(OpcionMenu.PASARTURNO.ordinal());
                    break;
                case JA_ENCARCELADOCONOPCIONDELIBERTAD:
                    operaciones.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
                    operaciones.add(OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                    operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                    operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
                    break;
                default:
                    break;
            }
        }
        Collections.sort(operaciones); //Ordeno segun numero de OPCION DE MENU
        return operaciones;
    }

    /**
     * Metodo el cual determina si una opcion elegida del menu, necesita o no
     * elegir una casilla. Por ejemplo en el caso de EDIFICAR UNA CASA se
     * necesita elegir una casilla.
     *
     * @param opcionMenu Opcion del menu elegida en la VistaTextualQytetet
     * @return Un booleano el cual es true si se necesita elegir casilla para
     * dichas opciones, si no false.
     */
    public boolean necesitaElegirCasilla(int opcionMenu) {
        OpcionMenu mi_opcion = OpcionMenu.values()[opcionMenu]; //Opcion escogida

        return mi_opcion == OpcionMenu.CANCELARHIPOTECA
                || mi_opcion == OpcionMenu.EDIFICARCASA
                || mi_opcion == OpcionMenu.EDIFICARHOTEL
                || mi_opcion == OpcionMenu.HIPOTECARPROPIEDAD
                || mi_opcion == OpcionMenu.VENDERPROPIEDAD;
    }

    /**
     * Metodo el cual obtiene las casillas de tipo CALLE, las cuales son validas
     * para algunas operaciones del menu. Por ejemplo para cancelar una hipoteca
     * deben aparecer aquellas casillas que estan hipotecadas, para construir
     * una casa u hotel deben aparecer las calles las cuales no estan
     * hipotecadas
     *
     * @param opcionMenu Opcion del menu elegida en la VistaTextualQytetet
     * @return Un ArrayList de enteros con el numero de casillas que son validas
     * para la opcionMenu
     */
    public ArrayList<Integer> obtenerCasillasValidas(int opcionMenu) {
        ArrayList<Integer> casillas_validas = null;
        OpcionMenu mi_opcion = OpcionMenu.values()[opcionMenu];

        if (null != mi_opcion) {
            switch (mi_opcion) {
                case CANCELARHIPOTECA:
                    casillas_validas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
                    break;
                case EDIFICARCASA:
                case EDIFICARHOTEL:
                case HIPOTECARPROPIEDAD:
                    casillas_validas = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
                    break;
                case VENDERPROPIEDAD:
                    casillas_validas = modelo.obtenerPropiedadesJugador();
                    break;
                default:
                    break;
            }
        }

        return casillas_validas;
    }

    /**
     * Metodo el cual realiza las operaciones del modelo Qytetet segun la
     * opcionElegida y si hace falta la casillaElegida
     *
     * @param opcionElegida opcion del menu que se ha elegido en VistaTextual.
     * @param casillaElegida casilla que se ha elegido o no, para realizar la
     * operacion.
     * @return Un string informando de lo que se esta haciendo en todo momento.
     */
    public String realizarOperacion(int opcionElegida, int casillaElegida) {
        OpcionMenu opcion = OpcionMenu.values()[opcionElegida];
        String mensaje = "";

        if (modelo.getJugadorActual() != null && opcion != OpcionMenu.PASARTURNO && opcion != OpcionMenu.TERMINARJUEGO
                && opcion != OpcionMenu.OBTENERRANKING && opcion != OpcionMenu.APLICARSORPRESA
                && opcion != OpcionMenu.INTENTARSALIRCARCELTIRANDODADO && opcion != OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD) {
            mensaje += "\n•|Turno de: " + modelo.getJugadorActual().getNombre();
        }

        switch (opcion) {
            case INICIARJUEGO:
                modelo.inicializarJuego(nombreJugadores);
                mensaje += "\n¡JUGADORES CREADOS!\n";
                mensaje += modelo.getJugadores().toString();
                mensaje += "\n\n•|Turno de: " + modelo.getJugadorActual().getNombre() + ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                break;
            case APLICARSORPRESA:
                mensaje += "\n" + modelo.getJugadorActual().getNombre() + " ¡HAS CAIDO EN UNA CASILLA SORPRESA!";
                mensaje += modelo.getCartaActual().toString();
                modelo.aplicarSorpresa();
                mensaje += modelo.getJugadorActual().toString();
                break;
            case CANCELARHIPOTECA: {
                boolean cancelada = modelo.cancelarHipoteca(casillaElegida);
                mensaje += ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                Calle calle_actual = (Calle) modeloqytetet.Qytetet.getInstance().obtenerCasillasTablero().get(casillaElegida);
                if (cancelada) {
                    mensaje += " ¡Se ha cancelado con exito la hipoteca de: " + calle_actual.getTitulo().getNombre();
                } else {
                    mensaje += " ¡No se ha podido cancelar la hipoteca de:  " + calle_actual.getTitulo().getNombre();
                }
                break;
            }
            case COMPRARTITULOPROPIEDAD:
                boolean comprado = modelo.comprarTituloPropiedad();
                mensaje += ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                if (modelo.getJugadorActual().getCasillaActual().soyEdificable()) {
                    Calle calle_actual = (Calle) modelo.getJugadorActual().getCasillaActual();
                    if (comprado) {
                        mensaje += " ¡Enhorabuena has comprado el titulo: " + calle_actual.getTitulo().getNombre();
                    } else {
                        mensaje += " ¡No se ha podido comprar el titulo: " + calle_actual.getTitulo().getNombre();
                    }
                }
                break;
            case EDIFICARCASA: {
                boolean edificar_casa = modelo.edificarCasa(casillaElegida);
                mensaje += ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                Calle calle_actual = (Calle) modeloqytetet.Qytetet.getInstance().obtenerCasillasTablero().get(casillaElegida);
                if (edificar_casa) {
                    mensaje += " ¡Has edificado una casa en: " + calle_actual.getTitulo().getNombre() + " (Numero de casas: " + calle_actual.getTitulo().getNumCasas() + ")";
                } else {
                    mensaje += " ¡No se ha podido edificar una casa en: " + calle_actual.getTitulo().getNombre();
                }
                break;
            }
            case EDIFICARHOTEL: {
                boolean edificar_hotel = modelo.edificarHotel(casillaElegida);
                mensaje += ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                Calle calle_actual = (Calle) modeloqytetet.Qytetet.getInstance().obtenerCasillasTablero().get(casillaElegida);
                if (edificar_hotel) {
                    mensaje += " ¡Has edificado un hotel en: " + calle_actual.getTitulo().getNombre() + " (Numero de hoteles: " + calle_actual.getTitulo().getNumHoteles() + ")";
                } else {
                    mensaje += " ¡No se ha podido edificar un hotel en: " + calle_actual.getTitulo().getNombre();
                }
                break;
            }
            case HIPOTECARPROPIEDAD: {
                modelo.hipotecarPropiedad(casillaElegida);
                mensaje += ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                Calle calle_actual = (Calle) modeloqytetet.Qytetet.getInstance().obtenerCasillasTablero().get(casillaElegida);
                if (calle_actual.getTitulo().isHipotecada()) {
                    mensaje += " Has hipotecado la propiedad: " + calle_actual.getTitulo().getNombre();
                } else {
                    mensaje += " No se ha podido hipotecar la propiedad: " + calle_actual.getTitulo().getNombre();
                }
                break;
            }
            case INTENTARSALIRCARCELPAGANDOLIBERTAD:
                modelo.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
                if (modelo.getJugadorActual().getEncarcelado()) {
                    mensaje += "\n¡No se ha podido pagar la libertad (saldo insuficiente), sigues encarcelado!";
                } else {
                    mensaje += "\n¡Has pagado la LIBERTAD, y sales de la carcel";
                }
                modelo.siguienteJugador();
                mensaje += "\n¡Se ha pasado de turno!";
                mensaje += "\n•|Turno de: " + modelo.getJugadorActual().getNombre() + ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                break;
            case INTENTARSALIRCARCELTIRANDODADO:
                modelo.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
                mensaje += "¡\nSe ha tirado el dado para salir de la carcel, con valor: " + modelo.getValorDado();
                if (modelo.getJugadorActual().getEncarcelado()) {
                    mensaje += "\nValor inferior a 5, no se ha podido salir de la carcel";
                } else {
                    mensaje += "\n¡HAS SALIDO DE LA CARCEL!";
                }
                modelo.siguienteJugador();
                mensaje += "\n¡Se ha pasado de turno!";
                mensaje += "\n•|Turno de: " + modelo.getJugadorActual().getNombre() + ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                break;
            case MOSTRARJUGADORACTUAL:
                System.out.println(modelo.getJugadorActual());
                break;
            case MOSTRARJUGADORES:
                System.out.println(modelo.getJugadores());
                break;
            case MOSTRARTABLERO:
                System.out.println(modelo.obtenerCasillasTablero());
                break;
            case OBTENERRANKING:
                mensaje += "\n¡EL JUEGO HA TERMINADO!";
                modelo.obtenerRanking();
                break;
            case VENDERPROPIEDAD: {
                modelo.venderPropiedad(casillaElegida);
                mensaje += ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                Calle calle_actual = (Calle) modeloqytetet.Qytetet.getInstance().obtenerCasillasTablero().get(casillaElegida);
                if (calle_actual.getTitulo().getPropietario() == modelo.getJugadorActual()) {
                    mensaje += " ¡No se ha podido vender el titulo: " + calle_actual.getTitulo().getNombre();
                } else {
                    mensaje += " ¡Has vendido el titulo: " + calle_actual.getTitulo().getNombre();
                }
                break;
            }
            case PASARTURNO:
                modelo.siguienteJugador();
                mensaje += "\n•|Turno de: " + modelo.getJugadorActual().getNombre() + ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                break;
            case TERMINARJUEGO:
                modelo.obtenerRanking();
                break;
            case JUGAR:
                modelo.jugar();
                mensaje += ", Saldo: " + modelo.getJugadorActual().getSaldo() + "|•";
                mensaje += " ¡Se ha tirado el dado y ha salido: " + modelo.getValorDado();
                mensaje += ", (El jugador se ha posicionado en): " + modelo.getJugadorActual().getCasillaActual().toString();
                if (modelo.getJugadorActual().getEncarcelado()) {
                    mensaje += "\n\n(¡HAS SIDO ENCARCELADO!): " + modelo.getJugadorActual().getNombre();
                }
                break;
            default:
                break;
        }

        return mensaje;
    }

}
