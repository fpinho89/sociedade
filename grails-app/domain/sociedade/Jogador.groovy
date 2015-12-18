package sociedade

class Jogador {

	String nome
	String posicao

    static constraints = {
    	nome nullable:false, blank:false
    	posicao nullable:false, blank:false
    }
}
