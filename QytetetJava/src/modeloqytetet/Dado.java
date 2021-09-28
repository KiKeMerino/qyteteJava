package modeloqytetet;

import java.util.Random;

/**
 * Clase Dado encargada de simular el dado de nuestro juego (SINGLETON)
 *
 * @author cazz & sara
 */
class Dado {

    private int valor;
    private static final Dado instance = new Dado();

    /**
     * Constructor de la clase Dado, el cual es privado ya que es
     * Singleton(solamente puede tener una instancia de la clase), inicializa
     * las demas variables.
     */
    private Dado() {
        valor = 0;
    }

    /**
     * Metodo el cual devuelve la unica instancia creada de Dado
     *
     * @return la unica instancia de Dado
     */
    public static Dado getInstance() {
        return instance;
    }

    /**
     * Metodo el cual simula el movimiento tirar del dado
     *
     * @return un numero comprendido entre 1 y 6
     */
    int tirar() {
        Random r = new Random();
        valor = r.nextInt(6) + 1;
        return valor;
    }

    /**
     * Metodo getValor
     *
     * @return devuelve el ultimo valor de tirar el dado.
     */
    int getValor() {
        return valor;
    }
}
