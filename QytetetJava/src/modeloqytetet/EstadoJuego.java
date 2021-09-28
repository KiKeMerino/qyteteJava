package modeloqytetet;

/**
 * Enumerado del Estado de Juego Qytetet en todo momento. Es utilizado para
 * obtener opciones de menu validas en la clase ControladorQytetet
 *
 * @author cazz & sara
 */
public enum EstadoJuego {
    JA_CONSORPRESA,
    ALGUNJUGADORENBANCARROTA,
    JA_PUEDECOMPRAROGESTIONAR,
    JA_PUEDEGESTIONAR,
    JA_PREPARADO,
    JA_ENCARCELADO,
    JA_ENCARCELADOCONOPCIONDELIBERTAD
}
