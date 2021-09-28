package modeloqytetet;

/**
 * Clase TituloPropiedad, representa un titulo de propiedad que tendra una
 * Casilla tipo Calle
 *
 * @author cazz & sara
 */
public class TituloPropiedad {

    private String nombre;
    private boolean hipotecada;
    private int precioCompra;
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;
    private int precioEdificar;
    private int numHoteles;
    private int numCasas;
    //Variables de referencia
    private Jugador propietario;

    /**
     * Contructor para inicializar todas las variables de una instancia de
     * TituloPropiedad
     *
     * @param nombre nombre que tendra el titulo
     * @param precioCompra precio de comprar del titulo
     * @param alquilerBase alquilerbase que se le cobrara a otros jugadores por
     * caer en la calle
     * @param factorRevalorizacion factor, el cual influira al vender la
     * propiedad
     * @param hipotecaBase precio de hipotecar
     * @param precioEdificar precio al edificar una casa u hotel
     */
    TituloPropiedad(String nombre, int precioCompra, int alquilerBase, float factorRevalorizacion, int hipotecaBase, int precioEdificar) {
        this.nombre = nombre;
        this.hipotecada = false;
        this.precioCompra = precioCompra;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
        this.numHoteles = 0;
        this.numCasas = 0;
        this.propietario = null;
    }

    /**
     * Metodo getNombre
     *
     * @return devuelve el nombre del titulo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que devuelve si tenemos la propiedad hipotecada
     *
     * @return true si esta hipotecada, false si no.
     */
    public boolean isHipotecada() {
        return hipotecada;
    }

    /**
     * Metodo getPrecioCompra
     *
     * @return devuelve el precio de compra del titulo
     */
    int getPrecioCompra() {
        return precioCompra;
    }

    /**
     * Metodo getAlquilerBase
     *
     * @return devuele el alquiler base del titulo
     */
    int getAlquilerBase() {
        return alquilerBase;
    }

    /**
     * Metodo getFactorRevalorizacion
     *
     * @return devuelve el factor de revalorizacion del titulo
     */
    float getFactorRevalorizacion() {
        return factorRevalorizacion;
    }

    /**
     * Metodo getHipotecaBase
     *
     * @return devuelve la hipoteca base del titulo
     */
    int getHipotecaBase() {
        return hipotecaBase;
    }

    /**
     * Metodo getPrecioEdificar
     *
     * @return devuelve el precio de edificar una casa u hotel
     */
    int getPrecioEdificar() {
        return precioEdificar;
    }

    /**
     * Metodo getNumHoteles
     *
     * @return devuelve el numero de hoteles edificados en el titulo
     */
    public int getNumHoteles() {
        return numHoteles;
    }

    /**
     * Metodo getNumCasas
     *
     * @return devuelve el numero de casas edificadas en el titulo
     */
    public int getNumCasas() {
        return numCasas;
    }

    /**
     * Metodo getPropietario
     *
     * @return devuelve el jugador propietario del titulo, si no tiene null
     */
    public Jugador getPropietario() {
        return propietario;
    }

    /**
     * Metodo setHipotecada
     *
     * @param hipotecada establece la variable hipotecada
     */
    void setHipotecada(boolean hipotecada) {
        this.hipotecada = hipotecada;
    }

    /**
     * Metodo setPropietario
     *
     * @param propietario establece un Jugador a la variable propietario del
     * titulo
     */
    void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    /**
     * Meotodo setNumHoteles
     *
     * @param numHoteles establece el numero de hoteles que tiene el titulo
     */
    void setNumHoteles(int numHoteles) {
        this.numHoteles = numHoteles;
    }

    /**
     * Metodo setNumCasas
     *
     * @param numCasas establece el numero de casas que tiene el titulo
     */
    void setNumCasas(int numCasas) {
        this.numCasas = numCasas;
    }

    /**
     * Calcula el coste de cancelar la hipoteca, poner hipotecada = false
     *
     * @return coste de cancelar hipoteca
     */
    int calcularCosteCancelarHipoteca() {
        int costeCancelar = (int) (1.10 * calcularCosteHipotecar());
        return costeCancelar;
    }

    /**
     * Calcula el coste de hipotecar el titulo, poner hipotecada = true
     *
     * @return coste de hipotecar
     */
    int calcularCosteHipotecar() {
        int costeHipoteca = (int) (hipotecaBase + numCasas * 0.5 * hipotecaBase + numHoteles * hipotecaBase);

        return costeHipoteca;
    }

    /**
     * Calcula el importe que pagara otro jugador que caiga en la calle que
     * contenga el tituloPropiedad, influyen el numero de casas y hoteles
     * contruidos.
     *
     * @return importe de alquiler
     */
    int calcularImporteAlquiler() {
        int costeAlquiler = alquilerBase + (int) (numCasas * 0.5 + numHoteles * 2);

        return costeAlquiler;
    }

    /**
     * Calcula el precio de vender la propiedad.
     *
     * @return precio de venta
     */
    int calcularPrecioVenta() {
        int precioVenta = (int) (precioCompra + (numCasas + numHoteles) * precioEdificar * factorRevalorizacion);
        return precioVenta;
    }

    /**
     * Cancela la hipoteca, establece la variable hipotecada a false
     */
    void cancelarHipoteca() {
        hipotecada = false;
    }

    /**
     * Edifica una casa en el titulo propiedad, suma una casa mas
     */
    void edificarCasa() {
        numCasas += 1;
    }

    /**
     * Edifica un hotel en el titulo propiedad, suma un hotel mas, y se
     * destruyen 4 casas, segun las reglass
     */
    void edificarHotel() {
        numCasas -= 4;
        numHoteles += 1;
    }

    /**
     * Hipoteca el titulo propiedad
     *
     * @return devuelve el coste de hipotecar
     */
    int hipotecar() {
        setHipotecada(true);
        int costeHipoteca = calcularCosteHipotecar();

        return costeHipoteca;
    }

    /**
     * Si cae otro jugador en nuestra Calle la cual poseemos el titulo de
     * propiedad, se le restara a el el coste de alquiler, y se nos sumara a
     * nosotros
     *
     * @return el coste de alquiler
     */
    int pagarAlquiler() {
        int costeAlquiler = calcularImporteAlquiler();
        propietario.modificarSaldo(costeAlquiler);

        return costeAlquiler;
    }

    /**
     * Devuelve si el propietario del titulo esta en la carcel
     *
     * @return true si esta en la carcel, false en caso contrario.
     */
    boolean propietarioEncarcelado() {
        return propietario.getEncarcelado();
    }

    /**
     * Devuelve si el titulo tiene o no propietario
     *
     * @return true si no tiene, false si tiene propietario
     */
    boolean tengoPropietario() {
        return propietario != null;
    }

    /**
     * Metodo toString, el cual devuelve toda la informacion referente al titulo
     * de propiedad
     *
     * @return String con informacion
     */
    @Override
    public String toString() {
        return "\nTITULO DE PROPIEDAD: "
                + "\n\t•Nombre: " + nombre
                + "\n\t•Propietario: " + ((propietario == null) ? "NINGUNO" : propietario.getNombre())
                + "\n\t•Hipotecada: " + ((hipotecada) ? "SI" : "NO")
                + "\n\t•Precio de compra: " + precioCompra
                + "\n\t•Alquiler: " + alquilerBase
                + "\n\t•Revalorizacion: " + factorRevalorizacion
                + "\n\t•Hipoteca base: " + hipotecaBase
                + "\n\t•Precio de edificar: " + precioEdificar
                + "\n\t•Hoteles: " + numHoteles
                + "\n\t•Casas: " + numCasas;
    }

}
