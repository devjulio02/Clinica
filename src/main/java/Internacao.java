public class Internacao {

	private TipoLeito tipoLeito;
	private int qtdeDias;

	public Internacao(TipoLeito tipoLeito, int qtdeDias) {
		this.tipoLeito = tipoLeito;
		this.qtdeDias = qtdeDias;
	}

	TipoLeito getTipoLeito() {
		return tipoLeito;
	}

	int getQtdeDias() {
		return qtdeDias;
	}

	public float calcularValorTotal(){
		return tipoLeito.calcularValorDiarias(qtdeDias);
	} //Padrão especialista (GRASP), A internação resolve o cálculo pq tem os dados;

	public String getDetalhes (){
		String sufixoDias = qtdeDias > 1 ? "s" : "";
		String nomeLeito = tipoLeito == TipoLeito.APARTAMENTO ? "apartamento" : "enfermaria";
		return qtdeDias + " diária" + sufixoDias + " em " + nomeLeito;
	} //Padrão SRP (SOLID): A internação cuida da sua própria descrição;
}
