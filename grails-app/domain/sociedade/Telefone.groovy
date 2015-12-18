package sociedade

class Telefone {

	String ddd
	String numero

	String toString() {
		"$ddd $numero"
	}

	static belongsTo = [aluno:Aluno]

    static constraints = {
    	ddd nullable:false, blank:false, maxSize: 3
    	numero nullable:false, blank:false, maxSize: 9
    }
}
