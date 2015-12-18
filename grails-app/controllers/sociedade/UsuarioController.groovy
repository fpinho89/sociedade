package sociedade

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UsuarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        respond new Usuario(params)
    }    

    def logar(Usuario usuario) {

        def user = Usuario.findByLoginAndSenha(usuario.login, usuario.senha)
        
        if (user) {
            flash.message = "Bem vindo, ${user.login}!"
            redirect controller: "aluno", action:"index", method:"GET"
        } else {
            flash.message = "Acesso Negado!"
            redirect action:"index"
        }
    }    
}
