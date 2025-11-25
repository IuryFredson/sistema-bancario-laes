import java.util.ArrayList;
import java.util.List;

public class ContaInvestimento extends Conta {

    private List<Investimento> carteira;

    public ContaInvestimento(int numero, double saldo, Cliente titular) {
        super(numero, saldo, titular);
        this.carteira = new ArrayList<>();
    }

    public List<Investimento> getCarteira() {
        return new ArrayList<>(this.carteira);
    }

    private double calcularValorTotalInvestido() {
        double total = 0;
        for (int i = 0; i < carteira.size(); i++) {
            Investimento inv = carteira.get(i);
            if (!inv.isResgatado()) {
                total += inv.getValorInicial();
            }
        }
        return total;
    }

    private double calcularValorTotalAtual() {
        double total = 0;
        for (int i = 0; i < carteira.size(); i++) {
            Investimento inv = carteira.get(i);
            if (!inv.isResgatado()) {
                total += inv.getValorAtual();
            }
        }
        return total;
    }

    public double getValorTotalInvestido() {
        return calcularValorTotalInvestido();
    }

    public double getValorTotalAtual() {
        return calcularValorTotalAtual();
    }

    public double getRendimentoTotal() {
        return getValorTotalAtual() - getValorTotalInvestido();
    }

    public void investir(TipoInvestimento tipo, double montante) throws ValidacaoException {
        if (montante <= 0) {
            throw new ValidacaoException("Montante deve ser positivo.");
        }

        if (montante > getSaldo()) {
            throw new ValidacaoException(
                    "Saldo insuficiente. Disponível: R$ " + getSaldo()
            );
        }

        Investimento inv = new Investimento(tipo, montante);
        carteira.add(inv);

        this.setSaldo(this.getSaldo() - montante);

        Transacao t = new Transacao(
                TipoTransacao.APLICACAO_INVESTIMENTO,
                montante,
                "Investimento em " + tipo,
                this,
                null
        );

        registrarTransacao(t);
    }

    public void resgatar(int indice) throws ValidacaoException {
        if (indice < 0 || indice >= carteira.size()) {
            throw new ValidacaoException("Índice de investimento inválido.");
        }

        Investimento inv = carteira.get(indice);

        if (inv.isResgatado()) {
            throw new ValidacaoException("Investimento já foi resgatado.");
        }

        double valorResgate = inv.resgatar();

        this.setSaldo(this.getSaldo() + valorResgate);

        Transacao t = new Transacao(
                TipoTransacao.RESGATE_INVESTIMENTO,
                valorResgate,
                "Resgate de investimento " + inv.getTipo(),
                null,
                this
        );

        registrarTransacao(t);
    }

    public void sacar(double valor) throws SaldoInsuficienteException, ValidacaoException {
        if (valor <= 0) {
            throw new ValidacaoException("O valor do saque deve ser positivo.");
        }

        if (valor > this.getSaldo()) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente. Disponível: R$ " + this.getSaldo()
            );
        }

        this.setSaldo(this.getSaldo() - valor);

        Transacao t = new Transacao(
                TipoTransacao.SAQUE,
                valor,
                "Saque em conta investimento " + this.getNumero(),
                this,
                null
        );

        registrarTransacao(t);
    }

    public double calcularTarifa() {
        return getTitular().getTipoCliente().equals("premium") ? 0.0 : 20.0;
    }
}
