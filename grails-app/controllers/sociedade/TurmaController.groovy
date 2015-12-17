package sociedade

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TurmaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Turma.list(params), model:[turmaCount: Turma.count()]
    }

    def show(Turma turma) {
        respond turma
    }

    def create() {
        respond new Turma(params)
    }

    @Transactional
    def save(Turma turma) {
        if (turma == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (turma.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond turma.errors, view:'create'
            return
        }

        turma.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'turma.label', default: 'Turma'), turma.id])
                redirect turma
            }
            '*' { respond turma, [status: CREATED] }
        }
    }

    def edit(Turma turma) {
        respond turma
    }

    @Transactional
    def update(Turma turma) {
        if (turma == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (turma.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond turma.errors, view:'edit'
            return
        }

        turma.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'turma.label', default: 'Turma'), turma.id])
                redirect turma
            }
            '*'{ respond turma, [status: OK] }
        }
    }

    @Transactional
    def delete(Turma turma) {

        if (turma == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        turma.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'turma.label', default: 'Turma'), turma.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'turma.label', default: 'Turma'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
