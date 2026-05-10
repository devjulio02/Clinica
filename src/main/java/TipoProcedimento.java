public enum TipoProcedimento {
	BASICO(50.00f), 
	COMUM(150.00f), 
	AVANCADO(500.00f);
	//Definição de valores para eliminar a dependencia do switch case;

	private final float preco;
	//Para guarar o preço de cada tipo de procedimento;

	TipoProcedimento(float preco){
		this.preco = preco;
	}
	//Construtor para inicializar o preço de cada procedimento;

	public float getPreco() {
		return this.preco;
	}
}
