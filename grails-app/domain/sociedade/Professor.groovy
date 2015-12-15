package sociedade

class Professor {

	String nome
	String sobrenome
	String cpf
	String matricula

	String toString () {
		"$nome $sobrenome"
	}

    static constraints = {
    	matricula nullable:false, blank: false, maxSize:12
    }
}
