package sociedade

class Usuario {

	String login
	String senha

    static constraints = {
    	login nullable:false, blank:false, maxSize: 10
    	senha nullable:false, blank:false, maxSize: 6, password:true
    }
}