package gui.enumerados;

public enum EstilosPeleador  {
    FAJADOR("FAJADOR"),
    GRAPLER("GRAPLER"),
    PATADAS("PATADAS"),
    CLOSE("CLOSE");

    private String valor;

    public String getValor() {
        return valor;
    }

   EstilosPeleador(String valor) {
        this.valor = valor;
    }
}
