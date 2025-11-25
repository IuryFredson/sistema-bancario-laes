import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private static int contador = 0;

    private int id;
    private String tipoCliente;
    private double renda;

    private List<Conta> contas;
    private List<Transacao> historicoTransacoes;
    private List<Emprestimo> emprestimos;

    public Cliente(String nome, String cpf, String endereco, String telefone,
                   String tipo, double r) {
        super(nome, cpf, endereco, telefone);
        contador++;
        this.id = contador;
        this.tipoCliente = tipo;
        this.renda = r;
        this.contas = new ArrayList<>();
        this.historicoTransacoes = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipo) {
        this.tipoCliente = tipo;
    }

    public double getRenda() {
        return renda;
    }

    public void setRenda(double r) {
        this.renda = r;
    }

    public String getDescricao() {
        return "Cliente nº " + this.id + " - " + this.getNome();
    }

    public void adicionarConta(Conta conta) {
        if (conta != null && !contas.contains(conta)) {
            this.contas.add(conta);
        }
    }

    public List<Conta> getContas() {
        return new ArrayList<>(this.contas);
    }

    public void registrarTransacao(Transacao transacao) {
        if (transacao != null) {
            this.historicoTransacoes.add(transacao);
        }
    }

    public List<Transacao> getHistoricoTransacoes() {
        return new ArrayList<>(this.historicoTransacoes);
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo != null) {
            this.emprestimos.add(emprestimo);
        }
    }

    public List<Emprestimo> getEmprestimos() {
        return new ArrayList<>(this.emprestimos);
    }

    private double calcularLimiteCredito() {
        double multiplicador = tipoCliente.equals("premium") ? 5.0 : 3.0;

        double totalEmprestimosAtivos = 0;
        for (int i = 0; i < emprestimos.size(); i++) {
            Emprestimo emp = emprestimos.get(i);
            if (!emp.isQuitado()) {
                totalEmprestimosAtivos += emp.getSaldoDevedor();
            }
        }

        double limiteBase = renda * multiplicador;
        double limiteDisponivel = limiteBase - totalEmprestimosAtivos;

        return Math.max(0, limiteDisponivel);
    }

    public double getLimiteCredito() {
        return calcularLimiteCredito();
    }

    public boolean emConformidade() {
        return true;
    }

    public double calcularSaldoTotal() {
        double total = 0;
        for (int i = 0; i < contas.size(); i++) {
            total += contas.get(i).getSaldo();
        }
        return total;
    }
}
