public class Funcionario extends Pessoa {

    private int matricula;
    private String cargo;
    private double salario;

    public Funcionario(String nome, String cpf, String endereco, String telefone,
                       int mat, String c, double sal) {
        super(nome, cpf, endereco, telefone);
        this.matricula = mat;
        this.cargo = c;
        this.salario = sal;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int mat) {
        this.matricula = mat;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String c) {
        this.cargo = c;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double sal) {
        this.salario = sal;
    }

    public double calcularBonificacao() {
        return this.salario * 0.10;
    }

    public String getDescricao() {
        return "Funcionário (" + this.cargo + ") - " + this.getNome();
    }
}
