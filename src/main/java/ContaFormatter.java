import java.text.NumberFormat;

public class ContaFormatter {

    public static String formatarConta(Prontuario prontuario) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String conta = "----------------------------------------------------------------------------------------------";

        float valorDiarias = 0.0f;
        if (prontuario.getInternacao() != null) {
            valorDiarias = prontuario.getInternacao().calcularValorTotal();
        }

        float valorTotalProcedimentos = 0.00f;
        int qtdeProcedimentosBasicos = 0;
        int qtdeProcedimentosComuns = 0;
        int qtdeProcedimentosAvancados = 0;

        for (Procedimento procedimento : prontuario.getProcedimentos()) {
            valorTotalProcedimentos += procedimento.getPreco();
            switch (procedimento.getTipoProcedimento()){
                case BASICO: qtdeProcedimentosBasicos++; break;
                case COMUM: qtdeProcedimentosComuns++; break;
                case AVANCADO: qtdeProcedimentosAvancados++; break;
            }
        }

        conta += "\nA conta do(a) paciente " + prontuario.getNomePaciente() + " tem valor total de __ " + formatter.format(valorDiarias + valorTotalProcedimentos) + " __";
        conta += "\n\nConforme os detalhes abaixo:";

        if (prontuario.getInternacao() != null) {
            conta += "\n\nValor Total Diárias:\t\t\t" + formatter.format(valorDiarias);
            
            // A regra de texto que ficava em Internacao.java agora pertence ao formatador visual:
            String sufixoDias = prontuario.getInternacao().getQtdeDias() > 1 ? "s" : "";
            String nomeLeito = prontuario.getInternacao().getTipoLeito() == TipoLeito.APARTAMENTO ? "apartamento" : "enfermaria";
            String detalhes = prontuario.getInternacao().getQtdeDias() + " diária" + sufixoDias + " em " + nomeLeito;
            
            conta += "\n\t\t\t\t\t" + detalhes; 
        }

        if (prontuario.getProcedimentos().size() > 0) {
            conta += "\n\nValor Total Procedimentos:\t\t" + formatter.format(valorTotalProcedimentos);

            if (qtdeProcedimentosBasicos > 0) {
                conta += "\n\t\t\t\t\t" + qtdeProcedimentosBasicos + " procedimento" + (qtdeProcedimentosBasicos > 1 ? "s" : "")
                        + " básico" + (qtdeProcedimentosBasicos > 1 ? "s" : "");
            }

            if (qtdeProcedimentosComuns > 0) {
                conta += "\n\t\t\t\t\t" + qtdeProcedimentosComuns + " procedimento" + (qtdeProcedimentosComuns > 1 ? "s" : "")
                        + " comu" + (qtdeProcedimentosComuns > 1 ? "ns" : "m");
            }

            if (qtdeProcedimentosAvancados > 0) {
                conta += "\n\t\t\t\t\t" + qtdeProcedimentosAvancados + " procedimento" + (qtdeProcedimentosAvancados > 1 ? "s" : "")
                        + " avançado" + (qtdeProcedimentosAvancados > 1 ? "s" : "");
            }
        }

        conta += "\n\nVolte sempre, a casa é sua!";
        conta += "\n----------------------------------------------------------------------------------------------";

        return conta;
    }
}