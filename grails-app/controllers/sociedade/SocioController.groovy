package sociedade

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SocioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Socio.list(params), model:[socioCount: Socio.count()]
    }

    def show(Socio socio) {
        respond socio
    }

    def create() {
        respond new Socio(params)
    }

    @Transactional
    def save(Socio socio) {
        if (socio == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (socio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond socio.errors, view:'create'
            return
        }

        socio.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'socio.label', default: 'Socio'), socio.id])
                redirect socio
            }
            '*' { respond socio, [status: CREATED] }
        }
    }

    def edit(Socio socio) {
        respond socio
    }

    @Transactional
    def update(Socio socio) {
        if (socio == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (socio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond socio.errors, view:'edit'
            return
        }

        socio.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'socio.label', default: 'Socio'), socio.id])
                redirect socio
            }
            '*'{ respond socio, [status: OK] }
        }
    }

    @Transactional
    def delete(Socio socio) {

        if (socio == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        socio.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'socio.label', default: 'Socio'), socio.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'socio.label', default: 'Socio'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
