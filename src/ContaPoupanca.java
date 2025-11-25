public class ContaPoupanca extends Conta {

    private double taxaRendimento;

    public ContaPoupanca(int numero, double saldo, Cliente titular, double taxa) {
        super(numero, saldo, titular);
        this.taxaRendimento = taxa;
    }

    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(double taxa) {
        this.taxaRendimento = taxa;
    }

    public void renderJuros() throws ValidacaoException {
        if (this.taxaRendimento <= 0) {
            throw new ValidacaoException("Taxa de rendimento deve ser positiva para aplicar.");
        }

        double rendimento = this.getSaldo() * (this.taxaRendimento / 100);
        this.setSaldo(this.getSaldo() + rendimento);

        Transacao t = new Transacao(
                TipoTransacao.RENDIMENTO_POUPANCA,
                rendimento,
                "Rendimento de poupança (" + taxaRendimento + "%)",
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
                    "Saldo insuficiente para este saque. Disponível: R$ " + this.getSaldo()
            );
        }

        this.setSaldo(this.getSaldo() - valor);

        Transacao t = new Transacao(
                TipoTransacao.SAQUE,
                valor,
                "Saque em conta poupança " + this.getNumero(),
                this,
                null
        );

        registrarTransacao(t);
    }

    public double calcularTarifa() {
        return 0.0;
    }
}
