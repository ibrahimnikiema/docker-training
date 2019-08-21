import groovy.sql.Sql
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by req88092 on 2019-08-08
 */
class GroovySampleApp {

    private static Logger logger = LoggerFactory.getLogger(GroovySampleApp.class)

    static void main(String[] args) {
      new GroovySampleApp().run()
    }

    def run() {
        logger.info('Application starting up....')
        String env = System.getenv('WAS_ENV') ?: 'Unit'
        def config = getApplicationProperties(env)
        def sql = Sql.newInstance(config.database.url, config.database.username, '', config.database.driver)
        def tableCreated = createTable(sql)
        logger.info('Table created in database: {}', tableCreated)
        def insertIntoTable = insertData(sql)
        logger.info('Inserted data in to table in database: {}', insertIntoTable)
        def rows = getAllData(sql)
        def writer = new StringWriter()
        rows.each { row ->
            writer.append("${row.NAME},${row.CATEGORY}\r\n")
        }
        writer.buffer.insert(0,'name,category\r\n')
        def file = new FileOutputStream(new File(config.file))
        file.write(writer.toString().bytes)
        sql.close()
    }

    def getApplicationProperties(String environment) {
        logger.info('Getting application properties for environment: {}', environment)
        return new ConfigSlurper(environment).parse(this.class.getResource('properties.groovy'))
    }

    def createTable(Sql sql) {
        logger.info('Creating a table in the database')
        return sql.execute('''CREATE TABLE T_DC
                            (
                                DC_ID           int          NOT NULL,
                                NAME            varchar(max) NOT NULL,
                                CATEGORY        varchar(11) NOT NULL,
                                GENDER          varchar(6)  NULL,
                                IDENTITY        varchar(11) NOT NULL,
                                ROW_CREATE_TSP  datetime     NOT NULL
                            );''')
    }

    def insertData(Sql sql) {
        logger.info('Inserting data in to a database table')
        return sql.execute('''INSERT INTO T_DC
                            (DC_ID, NAME, CATEGORY, GENDER, IDENTITY, ROW_CREATE_TSP)
                            VALUES
                            (1, 'BATMAN', 'HERO', 'MALE', 'SECRET', CURRENT_TIMESTAMP),
                            (2, 'SUPERMAN', 'HERO', 'MALE', 'SECRET', CURRENT_TIMESTAMP),
                            (3, 'WONDER WOMAN', 'HERO', 'FEMALE', 'PUBLIC', CURRENT_TIMESTAMP),
                            (4, 'TALIA AL GHUL', 'VILLAIN', 'FEMALE', 'SECRET', CURRENT_TIMESTAMP),
                            (5, 'CATWOMAN', 'HERO', 'FEMALE', 'SECRET', CURRENT_TIMESTAMP),
                            (6, 'AQUAMAN', 'HERO', 'MALE', 'PUBLIC', CURRENT_TIMESTAMP),
                            (7, 'LEX LUTHOR', 'VILLAIN', 'MALE', 'PUBLIC', CURRENT_TIMESTAMP),
                            (8, 'JOKER', 'VILLAIN', 'MALE', 'SECRET', CURRENT_TIMESTAMP),
                            (9, 'GREEN LANTERN', 'HERO', 'MALE', 'SECRET', CURRENT_TIMESTAMP),
                            (10, 'FLASH', 'HERO', 'MALE', 'SECRET', CURRENT_TIMESTAMP);''')
    }

    def getAllData(Sql sql) {
        logger.info('Getting all table data from the database')
        return sql.rows('SELECT * FROM T_DC')
    }
}
