public class ContaCorrente extends Conta {

    private double limiteChequeEspecial;
    private int diasEmDebito;

    public ContaCorrente(int numero, double saldo, Cliente titular, double limite) {
        super(numero, saldo, titular);
        this.limiteChequeEspecial = limite;
        this.diasEmDebito = 0;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void setLimiteChequeEspecial(double limite) {
        this.limiteChequeEspecial = limite;
    }

    public int getDiasEmDebito() {
        return diasEmDebito;
    }

    public double calcularJurosDebito(int dias) {
        return Math.abs(getSaldo()) * (0.01 * dias);
    }

    public void sacar(double valor) throws SaldoInsuficienteException, ValidacaoException {
        if (valor <= 0) {
            throw new ValidacaoException("O valor do saque deve ser positivo.");
        }

        double saldoDisp = this.getSaldo() + this.limiteChequeEspecial;

        if (valor > saldoDisp) {
            throw new SaldoInsuficienteException(
                    "Saldo e limite insuficientes para este saque. Disponível: R$ " + saldoDisp
            );
        }

        this.setSaldo(this.getSaldo() - valor);

        if (this.getSaldo() < 0) {
            diasEmDebito++;
        }

        Transacao t = new Transacao(
                TipoTransacao.SAQUE,
                valor,
                "Saque em conta " + this.getNumero(),
                this,
                null
        );

        registrarTransacao(t);
    }

    public double calcularTarifa() {
        return getTitular().getTipoCliente().equals("premium") ? 0.0 : 15.0;
    }
}
