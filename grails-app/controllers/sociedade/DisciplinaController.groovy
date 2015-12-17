package sociedade

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DisciplinaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Disciplina.list(params), model:[disciplinaCount: Disciplina.count()]
    }

    def show(Disciplina disciplina) {
        respond disciplina
    }

    def create() {
        respond new Disciplina(params)
    }

    @Transactional
    def save(Disciplina disciplina) {
        if (disciplina == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (disciplina.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond disciplina.errors, view:'create'
            return
        }

        disciplina.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'disciplina.label', default: 'Disciplina'), disciplina.id])
                redirect disciplina
            }
            '*' { respond disciplina, [status: CREATED] }
        }
    }

    def edit(Disciplina disciplina) {
        respond disciplina
    }

    @Transactional
    def update(Disciplina disciplina) {
        if (disciplina == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (disciplina.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond disciplina.errors, view:'edit'
            return
        }

        disciplina.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'disciplina.label', default: 'Disciplina'), disciplina.id])
                redirect disciplina
            }
            '*'{ respond disciplina, [status: OK] }
        }
    }

    @Transactional
    def delete(Disciplina disciplina) {

        if (disciplina == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        disciplina.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'disciplina.label', default: 'Disciplina'), disciplina.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'disciplina.label', default: 'Disciplina'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
