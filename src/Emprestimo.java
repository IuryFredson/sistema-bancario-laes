import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Emprestimo {

    private double valorTotal;
    private double taxaJuros;
    private int numeroParcelas;
    private List<Parcela> parcelas;
    private Cliente cliente;
    private LocalDate dataContratacao;

    public Emprestimo(double valor, int numParcelas, double taxa, Cliente cli) {
        this.valorTotal = valor;
        this.numeroParcelas = numParcelas;
        this.taxaJuros = taxa;
        this.cliente = cli;
        this.dataContratacao = LocalDate.now();
        this.parcelas = new ArrayList<>();

        gerarParcelas();
    }

    private void gerarParcelas() {
        double montanteTotal = calcularMontanteComJuros();
        double valorParcela = montanteTotal / numeroParcelas;

        LocalDate dataVenc = dataContratacao.plusMonths(1);

        for (int i = 1; i <= numeroParcelas; i++) {
            Parcela p = new Parcela(i, valorParcela, dataVenc);
            parcelas.add(p);
            dataVenc = dataVenc.plusMonths(1);
        }
    }

    private double calcularMontanteComJuros() {
        double taxaDecimal = taxaJuros / 100;
        return valorTotal * Math.pow(1 + taxaDecimal, numeroParcelas / 12.0);
    }

    public void pagarParcela(int numeroParcela, Conta conta)
            throws SaldoInsuficienteException, ValidacaoException {

        if (numeroParcela < 1 || numeroParcela > numeroParcelas) {
            throw new ValidacaoException("Número de parcela inválido.");
        }

        Parcela parcela = parcelas.get(numeroParcela - 1);

        if (parcela.isPaga()) {
            throw new ValidacaoException("Parcela já foi paga.");
        }

        double valorTotal = parcela.getValorTotal();

        conta.sacar(valorTotal);

        parcela.marcarComoPaga();
    }

    public boolean parcelaFoiPaga(int num) {
        if (num < 1 || num > numeroParcelas) {
            return false;
        }
        return parcelas.get(num - 1).isPaga();
    }

    public double getValorParcela(int num) {
        if (num < 1 || num > numeroParcelas) {
            return 0;
        }
        return parcelas.get(num - 1).getValorTotal();
    }

    private int contarParcelasPagas() {
        int count = 0;
        for (int i = 0; i < parcelas.size(); i++) {
            if (parcelas.get(i).isPaga()) {
                count++;
            }
        }
        return count;
    }

    private double calcularSaldoDevedor() {
        double total = 0;
        for (int i = 0; i < parcelas.size(); i++) {
            if (!parcelas.get(i).isPaga()) {
                total += parcelas.get(i).getValor();
            }
        }
        return total;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public List<Parcela> getParcelas() {
        return new ArrayList<>(parcelas);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public double getSaldoDevedor() {
        return calcularSaldoDevedor();
    }

    public int getParcelasPagas() {
        return contarParcelasPagas();
    }

    public boolean isQuitado() {
        return getParcelasPagas() == numeroParcelas;
    }
}
