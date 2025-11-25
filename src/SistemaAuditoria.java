import java.util.ArrayList;
import java.util.List;

public class SistemaAuditoria {

    private List<Transacao> transacoesAuditadas;
    private List<String> alertas;

    public SistemaAuditoria() {
        this.transacoesAuditadas = new ArrayList<>();
        this.alertas = new ArrayList<>();
    }

    public void auditarTransacao(Transacao transacao) {
        transacoesAuditadas.add(transacao);

        // Verificar se requer documentação
        if (transacao.requerDocumentacao()) {
            String alerta = String.format(
                    "ALERTA: Transação %d de valor R$ %.2f requer documentação",
                    transacao.getId(), transacao.getValor()
            );
            alertas.add(alerta);
        }

        // Verificar padrões suspeitos
        if (transacao.getValor() >= 50000) {
            String alerta = String.format(
                    "ALERTA CRÍTICO: Transação %d de alto valor: R$ %.2f",
                    transacao.getId(), transacao.getValor()
            );
            alertas.add(alerta);
        }
    }

    public boolean clienteEmConformidade(Cliente cliente) {
        List<Transacao> transacoes = cliente.getHistoricoTransacoes();

        for (int i = 0; i < transacoes.size(); i++) {
            Transacao t = transacoes.get(i);
            if (t.requerDocumentacao() && !verificarTransacaoAuditada(t)) {
                return false;
            }
        }

        return true;
    }

    private boolean verificarTransacaoAuditada(Transacao transacao) {
        return transacoesAuditadas.contains(transacao);
    }

    public List<Transacao> getTransacoesAuditadas() {
        return new ArrayList<>(transacoesAuditadas);
    }

    public List<String> getAlertas() {
        return new ArrayList<>(alertas);
    }

    public int getTotalOperacoesAuditadas() {
        return transacoesAuditadas.size();
    }

    public int contarTransacoesPorValorMinimo(double valor) {
        int count = 0;

        for (int i = 0; i < transacoesAuditadas.size(); i++) {
            if (transacoesAuditadas.get(i).getValor() >= valor) {
                count++;
            }
        }

        return count;
    }

    public void limparAlertas() {
        alertas.clear();
    }
}
