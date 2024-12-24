package gui.enumerados;

public enum TipoEntrenador {
    BOXEO("BOXEO"),
    JUDO("JUDO"),
    MMA("MMA"),
    K1("K1");

    private String valor;

    TipoEntrenador(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}