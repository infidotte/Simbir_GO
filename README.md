# Simbir_GO
1. В файле src/main/resources/application.properties поменять следующие поля:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```
2. ./mvnw clean package
3. cd target
4. java -jar Simbir_GO-0.0.1-SNAPSHOT.jar
## URL: http://localhost:8080/api/swagger/index.html
