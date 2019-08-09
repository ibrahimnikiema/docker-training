import groovy.sql.Sql
import spock.lang.Specification

/**
 * Created by req88092 on 2019-08-09
 */
class GroovySampleAppSpec extends Specification{

    GroovySampleApp groovySampleApp = new GroovySampleApp()
    Sql sql = Mock(Sql)

    def 'test application run' () {
        when: 'Calling to run application'
        groovySampleApp.run()

        then: 'Successful application run'
        true
    }

    def 'test getApplicationProperties() method' () {
        when: 'Getting application properties for Unit environment'
        def result = groovySampleApp.getApplicationProperties('Unit')

        then: 'Expected results are returned'
        result instanceof ConfigObject
        result.database.username == 'sa'
    }

    def 'test createTable()' () {
        when: 'Calling to create database tables'
        def result = groovySampleApp.createTable(sql)

        then: 'Return expected mocked response'
        1 * sql.execute(*_) >> {return  true}
        0 * _

        and: 'Expected result is returned'
        result instanceof Boolean
        result
    }

    def 'test insertData()' () {
        when: 'Calling to insert data in to database tables'
        def result = groovySampleApp.insertData(sql)

        then: 'Return expected mocked response'
        1 * sql.execute(*_) >> {return  true}
        0 * _

        and: 'Expected result is returned'
        result instanceof Boolean
        result
    }

    def 'test getAllData()' () {
        when: 'Calling to get all data from database tables'
        def result = groovySampleApp.getAllData(sql)

        then: 'Return expected mocked response'
        1 * sql.rows(*_) >> [[DC_ID:1, DC_NAME: 'Test']]
        0 * _

        and: 'Expected result is returned'
        result.DC_NAME == ['Test']
    }
}
