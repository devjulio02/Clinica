public enum TipoLeito {
    ENFERMARIA (40.00f, 35.00f, 30.00f),
    APARTAMENTO (100.00f, 90.00F, 80.00f);
    //Preços passados aqui para corrigir o problema das estruturas de repetição; 

    private final float precoAte3Dias;
    private final float precoAte8Dias;
    private final float precoAcima8Dias;
    //Para guardar o preço de cada faixa;

    TipoLeito(float precoAte3Dias, float precoAte8Dias, float precoAcima8Dias){
        this.precoAte3Dias = precoAte3Dias;
        this.precoAte8Dias = precoAte8Dias;
        this.precoAcima8Dias = precoAcima8Dias;
    }
    
    public float calcularValorDiarias(int qtdeDias){
        if(qtdeDias <= 3) {
            return this.precoAte3Dias * qtdeDias;
        } else if(qtdeDias <= 8) {
            return this.precoAte8Dias * qtdeDias;
        } else {
            return this.precoAcima8Dias * qtdeDias;
        }
    } //Método recebe a quantidade de dias e calcula pelo preço da ENFRMARIA ou APARTAMENTO; 
}