package com.cdrossi



import org.junit.*
import grails.test.mixin.*

@TestFor(MedicoController)
@Mock(Medico)
class MedicoControllerTests {

	def MedicoDerivacionesCSVReaderService

    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/medico/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.medicoInstanceList.size() == 0
        assert model.medicoInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.medicoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.medicoInstance != null
        assert view == '/medico/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/medico/show/1'
        assert controller.flash.message != null
        assert Medico.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/medico/list'


        populateValidParams(params)
        def medico = new Medico(params)

        assert medico.save() != null

        params.id = medico.id

        def model = controller.show()

        assert model.medicoInstance == medico
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/medico/list'


        populateValidParams(params)
        def medico = new Medico(params)

        assert medico.save() != null

        params.id = medico.id

        def model = controller.edit()

        assert model.medicoInstance == medico
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/medico/list'

        response.reset()


        populateValidParams(params)
        def medico = new Medico(params)

        assert medico.save() != null

        // test invalid parameters in update
        params.id = medico.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/medico/edit"
        assert model.medicoInstance != null

        medico.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/medico/show/$medico.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        medico.clearErrors()

        populateValidParams(params)
        params.id = medico.id
        params.version = -1
        controller.update()

        assert view == "/medico/edit"
        assert model.medicoInstance != null
        assert model.medicoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/medico/list'

        response.reset()

        populateValidParams(params)
        def medico = new Medico(params)

        assert medico.save() != null
        assert Medico.count() == 1

        params.id = medico.id

        controller.delete()

        assert Medico.count() == 0
        assert Medico.get(medico.id) == null
        assert response.redirectedUrl == '/medico/list'
    }
}
