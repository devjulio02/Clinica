public class Procedimento {

	private TipoProcedimento tipoProcedimento;

	public Procedimento(TipoProcedimento tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	public TipoProcedimento getTipoProcedimento() {
		return tipoProcedimento;
	}

	public float getPreco() {
		return tipoProcedimento.getPreco();
	} //Baixo Acoplamento: Prontuário não vê o Enum, só pede o preço ao Procedimento;
}

