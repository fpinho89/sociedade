package sociedade

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class JogadorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Jogador.list(params), model:[jogadorCount: Jogador.count()]
    }

    def show(Jogador jogador) {
        respond jogador
    }

    def create() {
        respond new Jogador(params)
    }

    @Transactional
    def save(Jogador jogador) {
        if (jogador == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (jogador.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond jogador.errors, view:'create'
            return
        }

        jogador.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'jogador.label', default: 'Jogador'), jogador.id])
                redirect jogador
            }
            '*' { respond jogador, [status: CREATED] }
        }
    }

    def edit(Jogador jogador) {
        respond jogador
    }

    @Transactional
    def update(Jogador jogador) {
        if (jogador == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (jogador.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond jogador.errors, view:'edit'
            return
        }

        jogador.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'jogador.label', default: 'Jogador'), jogador.id])
                redirect jogador
            }
            '*'{ respond jogador, [status: OK] }
        }
    }

    @Transactional
    def delete(Jogador jogador) {

        if (jogador == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        jogador.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'jogador.label', default: 'Jogador'), jogador.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'jogador.label', default: 'Jogador'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
