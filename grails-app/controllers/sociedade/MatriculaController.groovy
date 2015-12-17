package sociedade

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MatriculaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Matricula.list(params), model:[matriculaCount: Matricula.count()]
    }

    def show(Matricula matricula) {
        respond matricula
    }

    def create() {
        respond new Matricula(params)
    }

    @Transactional
    def save(Matricula matricula) {
        if (matricula == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (matricula.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond matricula.errors, view:'create'
            return
        }

        matricula.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'matricula.label', default: 'Matricula'), matricula.id])
                redirect matricula
            }
            '*' { respond matricula, [status: CREATED] }
        }
    }

    def edit(Matricula matricula) {
        respond matricula
    }

    @Transactional
    def update(Matricula matricula) {
        if (matricula == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (matricula.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond matricula.errors, view:'edit'
            return
        }

        matricula.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'matricula.label', default: 'Matricula'), matricula.id])
                redirect matricula
            }
            '*'{ respond matricula, [status: OK] }
        }
    }

    @Transactional
    def delete(Matricula matricula) {

        if (matricula == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        matricula.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'matricula.label', default: 'Matricula'), matricula.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'matricula.label', default: 'Matricula'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
