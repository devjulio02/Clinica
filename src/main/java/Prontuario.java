import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

public class Prontuario {

	private String nomePaciente;
	private Internacao internacao;
	private Set<Procedimento> procedimentos = new HashSet<>();

	public Prontuario(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public String getNomePaciente() {
		return this.nomePaciente;
	}

	public void setInternacao(Internacao internacao) {
		this.internacao = internacao;
	}

	public Internacao getInternacao() {
		return this.internacao;
	}

	public void addProcedimento(Procedimento procedimento) {
		this.procedimentos.add(procedimento);
	}

	public Set<Procedimento> getProcedimentos() {
		return this.procedimentos;
	}

	public String imprimaConta() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		String conta = "----------------------------------------------------------------------------------------------";

		float valorDiarias = 0.0f;

		// Primeira alteração, agora Internação que lida com toda a parte de calculo que aqui existia;
		// Padrão Especialista: Prontuário delega o cálculo para quem tem os dados da estadia;
		if (internacao != null) {
			valorDiarias = internacao.calcularValorTotal();
		}

		float valorTotalProcedimentos = 0.00f;
		int qtdeProcedimentosBasicos = 0;
		int qtdeProcedimentosComuns = 0;
		int qtdeProcedimentosAvancados = 0;

		//Segunda Alteração, agora quem lida com as condições que aqui existiam é a própria classe especialista Procedimento 
		for (Procedimento procedimento : procedimentos) { //Baixo Acoplamento: Prontuário não sabe preços, apenas acumula o que o Procedimento retorna;
			valorTotalProcedimentos += procedimento.getPreco(); //Somando o valor que o procedimento que sabe quanto custa;
			//switch case usado para contar a quantidade de cada procedimento para o relatório de texto;
			switch (procedimento.getTipoProcedimento()){
				case BASICO:
					qtdeProcedimentosBasicos++;
					break;
				case COMUM:
					qtdeProcedimentosComuns++;
					break;
				case AVANCADO:
					qtdeProcedimentosAvancados++;
					break;
			}
		}

		conta += "\nA conta do(a) paciente " + nomePaciente + " tem valor total de __ " + formatter.format(valorDiarias + valorTotalProcedimentos) + " __";
		conta += "\n\nConforme os detalhes abaixo:";

		if (internacao != null) {
			conta += "\n\nValor Total Diárias:\t\t\t" + formatter.format(valorDiarias);// Padrão Especialista/SRP: O Prontuário apenas "monta" o texto que a Internação já formatou;
			conta += "\n\t\t\t\t\t" + internacao.getDetalhes(); //substituição de formatação por chamada de método criado na classe Internacao
		}

		if (procedimentos.size() > 0) {
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

	public Prontuario carregueProntuario(String arquivoCsv) throws IOException {
		//Prontuário mantém a assinatura do método, mas transfere a lógica para o Repository;
        ProntuarioRepository repository = new ProntuarioRepository();
        return repository.carregueProntuario(arquivoCsv);
    }

    public String salveProntuario() throws IOException {
		//SRP: Separação da lógica de Domínio da lógica de Persistência em arquivos;
        ProntuarioRepository repository = new ProntuarioRepository();
        return repository.salveProntuario(this); // Passa o próprio prontuário ("this") para ser salvo
    }
}
