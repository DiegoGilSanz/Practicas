package gui.enumerados;

public enum TipoLiga {
    BOXEO("BOXEO"),
    JUDO("JUDO"),
    MMA("MMA"),
    K1("K1");

    private String valor;

    TipoLiga(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}