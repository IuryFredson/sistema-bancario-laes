import java.time.LocalDate;

public class Parcela {

    private int numero;
    private double valor;
    private LocalDate dataVencimento;
    private boolean paga;
    private LocalDate dataPagamento;

    public Parcela(int num, double val, LocalDate vencimento) {
        this.numero = num;
        this.valor = val;
        this.dataVencimento = vencimento;
        this.paga = false;
        this.dataPagamento = null;
    }

    public int getNumero() {
        return numero;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public boolean isPaga() {
        return paga;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void marcarComoPaga() {
        this.paga = true;
        this.dataPagamento = LocalDate.now();
    }

    public double calcularMultaAtraso() {
        if (paga || LocalDate.now().isBefore(dataVencimento)) {
            return 0;
        }

        long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(
                dataVencimento, LocalDate.now()
        );

        double multa = valor * 0.02;         // multa de 2%
        double juros = valor * 0.001 * diasAtraso; // juros diários de 0,1%

        return multa + juros;
    }

    public double getValorTotal() {
        return valor + calcularMultaAtraso();
    }
}
