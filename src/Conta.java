import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

    private int numero;
    private double saldo;
    private Cliente titular;
    private List<Transacao> historicoTransacoes;

    public Conta(int num, double s, Cliente tit) {
        this.numero = num;
        this.titular = tit;
        this.saldo = s;
        this.historicoTransacoes = new ArrayList<>();
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int num) {
        this.numero = num;
    }

    public double getSaldo() {
        return this.saldo;
    }

    protected void setSaldo(double s) {
        this.saldo = s;
    }

    public Cliente getTitular() {
        return this.titular;
    }

    public void setTitular(Cliente tit) {
        this.titular = tit;
    }

    public List<Transacao> getHistoricoTransacoes() {
        return new ArrayList<>(this.historicoTransacoes);
    }

    protected void registrarTransacao(Transacao transacao) {
        this.historicoTransacoes.add(transacao);
        this.titular.registrarTransacao(transacao);
    }

    public void depositar(double valor) throws ValidacaoException {
        if (valor <= 0) {
            throw new ValidacaoException("O valor do depósito deve ser positivo.");
        }

        this.saldo += valor;

        Transacao t = new Transacao(
                TipoTransacao.DEPOSITO,
                valor,
                "Depósito em conta " + this.numero,
                null,
                this
        );

        registrarTransacao(t);
    }

    public abstract void sacar(double valor)
            throws SaldoInsuficienteException, ValidacaoException;

    public abstract double calcularTarifa();

    public void cobrarTarifa() throws ValidacaoException {
        double tarifa = calcularTarifa();

        if (tarifa > saldo) {
            throw new ValidacaoException("Saldo insuficiente para cobrança de tarifa.");
        }

        this.saldo -= tarifa;

        Transacao t = new Transacao(
                TipoTransacao.COBRANCA_TARIFA,
                tarifa,
                "Tarifa mensal",
                this,
                null
        );

        registrarTransacao(t);
    }
}
