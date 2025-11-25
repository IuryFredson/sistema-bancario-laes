import java.time.LocalDateTime;

public class Transacao {
    private static int contadorId = 0;

    private int id;
    private TipoTransacao tipo;
    private double valor;
    private LocalDateTime dataHora;
    private Conta contaOrigem;
    private Conta contaDestino;
    private String descricao;

    public Transacao(TipoTransacao tipo, double valor, String descricao) {
        contadorId++;
        this.id = contadorId;
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
        this.descricao = descricao;
    }

    public Transacao(TipoTransacao tipo, double valor, String descricao,
                     Conta origem, Conta destino) {
        this(tipo, valor, descricao);
        this.contaOrigem = origem;
        this.contaDestino = destino;
    }

    public int getId() {
        return id;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean requerDocumentacao() {
        return valor > 10000;
    }

    @Override
    public String toString() {
        return String.format("[ID:%d] %s - R$ %.2f em %s - %s",
                id, tipo, valor, dataHora, descricao);
    }
}
