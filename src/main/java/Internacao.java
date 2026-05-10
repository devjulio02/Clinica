public class Internacao {

	private TipoLeito tipoLeito;
	private int qtdeDias;

	public Internacao(TipoLeito tipoLeito, int qtdeDias) {
		this.tipoLeito = tipoLeito;
		this.qtdeDias = qtdeDias;
	}

	TipoLeito getTipoLeito() {
		return this.tipoLeito;
	}

	int getQtdeDias() {
		return this.qtdeDias;
	}

	public float calcularValorTotal(){
		return this.tipoLeito.calcularValorDiarias(this.qtdeDias);
	} //Método especialista (Internacao que é reponsável por calcular o valor da interção)

	public String getDetalhes (){
		String sufixoDias = this.qtdeDias > 1 ? "s" : "";
		String nomeLeito = this.tipoLeito == TipoLeito.APARTAMENTO ? "apartamento" : "enfermaria";
		return this.qtdeDias + " diária" + sufixoDias + " em " + nomeLeito;
	}
}
