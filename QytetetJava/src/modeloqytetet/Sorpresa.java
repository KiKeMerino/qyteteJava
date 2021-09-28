package modeloqytetet;

/**
 * Clase Sorpresa, la cual representa las cartas de tipo Sorpresa
 *
 * @author cazz & sara
 */
public class Sorpresa {

    private String texto;
    private TipoSorpresa tipo;
    private int valor;

    /**
     * Constructor el cual inicializa una instancia de la clase Sorpresa
     *
     * @param texto Descripcion de la carta
     * @param valor Valor que tomara
     * @param tipo Tipo de sorpresa que sera
     */
    Sorpresa(String texto, int valor, TipoSorpresa tipo) {
        this.texto = texto;
        this.valor = valor;
        this.tipo = tipo;
    }

    /**
     * Metodo getTexto
     *
     * @return devuelve la descripcion de la carta sorpresa
     */
    String getTexto() {
        return texto;
    }

    /**
     * Metodo getTipo
     *
     * @return devuelve el tipo de la carta sorpresa
     */
    TipoSorpresa getTipo() {
        return tipo;
    }

    /**
     * Metodo getValor
     *
     * @return devuelve el valor de la carta sorpresa
     */
    int getValor() {
        return valor;
    }

    /**
     * Metodo toString, el cual devuelve la informacion referete a una carta
     * sorpresa
     *
     * @return un String con informacion
     */
    @Override
    public String toString() {
        return "\nSORPRESA:"
                + "\n\t•Descripcion: " + texto
                + "\n\t•Tipo: " + tipo
                + "\n\t•Valor: " + valor + "\n";
    }

}
