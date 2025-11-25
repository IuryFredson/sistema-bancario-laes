import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Investimento {

    private TipoInvestimento tipo;
    private double valorInicial;
    private LocalDate dataAplicacao;
    private boolean resgatado;
    private LocalDate dataResgate;

    public Investimento(TipoInvestimento t, double valor) {
        this.tipo = t;
        this.valorInicial = valor;
        this.dataAplicacao = LocalDate.now();
        this.resgatado = false;
        this.dataResgate = null;
    }

    public TipoInvestimento getTipo() {
        return tipo;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public boolean isResgatado() {
        return resgatado;
    }

    public LocalDate getDataResgate() {
        return dataResgate;
    }

    private long calcularDiasInvestidos() {
        LocalDate dataFim = resgatado ? dataResgate : LocalDate.now();
        return ChronoUnit.DAYS.between(dataAplicacao, dataFim);
    }

    public long getDiasInvestidos() {
        return calcularDiasInvestidos();
    }

    private double calcularValorAtual() {
        long dias = resgatado
                ? ChronoUnit.DAYS.between(dataAplicacao, dataResgate)
                : ChronoUnit.DAYS.between(dataAplicacao, LocalDate.now());

        double meses = dias / 30.0;
        double taxaDecimal = tipo.getTaxaMensal() / 100.0;

        return valorInicial * Math.pow(1 + taxaDecimal, meses);
    }

    public double getValorAtual() {
        return calcularValorAtual();
    }

    public double getRendimento() {
        return getValorAtual() - valorInicial;
    }

    public boolean podeResgatar() {
        return !resgatado && getDiasInvestidos() >= tipo.getDiasCarencia();
    }

    public double resgatar() throws ValidacaoException {
        if (resgatado) {
            throw new ValidacaoException("Investimento já foi resgatado.");
        }

        if (!podeResgatar()) {
            throw new ValidacaoException(
                    "Período de carência não cumprido. Necessário " +
                            tipo.getDiasCarencia() + " dias, investido apenas " +
                            getDiasInvestidos() + " dias."
            );
        }

        this.resgatado = true;
        this.dataResgate = LocalDate.now();

        return getValorAtual();
    }

    public String getResumo() {
        return String.format(
                "%s - Valor Inicial: R$ %.2f | Valor Atual: R$ %.2f | Rendimento: R$ %.2f | Dias: %d | Status: %s",
                tipo, valorInicial, getValorAtual(), getRendimento(),
                getDiasInvestidos(), resgatado ? "RESGATADO" : "ATIVO"
        );
    }
}
