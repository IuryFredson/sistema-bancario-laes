public abstract class Pessoa {

    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;

    public Pessoa(String n, String c, String end, String tel) {
        this.nome = n;
        this.cpf = c;
        this.endereco = end;
        this.telefone = tel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String c) {
        this.cpf = c;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String end) {
        this.endereco = end;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String tel) {
        this.telefone = tel;
    }

    public abstract String getDescricao();
}
