package sociedade

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TelefoneController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Telefone.list(params), model:[telefoneCount: Telefone.count()]
    }

    def show(Telefone telefone) {
        respond telefone
    }

    def create() {
        respond new Telefone(params)
    }

    @Transactional
    def save(Telefone telefone) {
        if (telefone == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (telefone.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond telefone.errors, view:'create'
            return
        }

        telefone.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'telefone.label', default: 'Telefone'), telefone.id])
                redirect telefone
            }
            '*' { respond telefone, [status: CREATED] }
        }
    }

    def edit(Telefone telefone) {
        respond telefone
    }

    @Transactional
    def update(Telefone telefone) {
        if (telefone == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (telefone.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond telefone.errors, view:'edit'
            return
        }

        telefone.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'telefone.label', default: 'Telefone'), telefone.id])
                redirect telefone
            }
            '*'{ respond telefone, [status: OK] }
        }
    }

    @Transactional
    def delete(Telefone telefone) {

        if (telefone == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        telefone.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'telefone.label', default: 'Telefone'), telefone.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'telefone.label', default: 'Telefone'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
