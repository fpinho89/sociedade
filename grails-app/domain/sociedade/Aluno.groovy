package sociedade

class Aluno {

	String nome
	String sobrenome

	String toString() {
		"$nome $sobrenome"
	}

	static hasMany = [telefones:Telefone]

	static constraints = {
		nome nullable:false, blank:false, maxSize:128
		sobrenome nullable:false, blank:false, maxSize:128
    }
}
