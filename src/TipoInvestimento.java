public enum TipoInvestimento {
    CDB(0.8, 90),             // 0.8% ao mês, 90 dias mínimo
    TESOURO_DIRETO(0.6, 180), // 0.6% ao mês, 180 dias mínimo
    LCI(0.7, 90),             // 0.7% ao mês, 90 dias mínimo
    LCA(0.7, 90),             // 0.7% ao mês, 90 dias mínimo
    FUNDO_RENDA_FIXA(0.5, 30); // 0.5% ao mês, 30 dias mínimo

    private final double taxaMensal;
    private final int diasCarencia;

    TipoInvestimento(double taxa, int dias) {
        this.taxaMensal = taxa;
        this.diasCarencia = dias;
    }

    public double getTaxaMensal() {
        return taxaMensal;
    }

    public int getDiasCarencia() {
        return diasCarencia;
    }
}
