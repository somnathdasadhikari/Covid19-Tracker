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


# Signup page :
![signup](https://user-images.githubusercontent.com/17508328/95684813-aa88c700-0c11-11eb-8375-8355ed12365e.png)



# Login page :
![login](https://user-images.githubusercontent.com/17508328/95684879-06535000-0c12-11eb-80e5-d6c2a3b078f5.png)


# Dashboard page :
![dashboard](https://user-images.githubusercontent.com/17508328/95684910-3864b200-0c12-11eb-97d7-9ba3afae494b.png)



