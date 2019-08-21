environments{
    Unit {
        database.url = 'jdbc:h2:mem:comics'
        database.username = 'sa'
        database.driver = 'org.h2.Driver'
        file = './Unit.csv'
    }
    Integration {
        database.url = 'jdbc:h2:mem:comics'
        database.username = 'sa'
        database.driver = 'org.h2.Driver'
        file = '/data/Integration.csv'
    }
}