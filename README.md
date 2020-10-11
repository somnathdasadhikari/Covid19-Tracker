# Covid19-Tracker

# Prerequisite:

1.	Java 11
2.	Maven
3.	MySql

# Required changes :

Need to update database details in application.properties file.

server.port=${PORT:8080}
vaadin.compatibilityMode = false
logging.level.org.atmosphere = warn
spring.jpa.generate-ddl=false
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/<db_name>?useSSL=false
spring.datasource.username=<user>
spring.datasource.password=<password>
spring.datasource.initialization-mode=always
spring.jpa.hibernate.use-new-id-generator-mappings=false


# Command to run :
1.	mvn clean install
2.	mvn spring-boot:run



