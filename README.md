# Spring boot, Spring Data, Spring Security REST API

A simple Spring boot application that demonstrates the usage of RESTful API using Spring Framework and MySQL.

## Tools and Technologies used

* Java 17
* Spring boot 3.1.1
* MySQL
* Minio
* JPA
* Hibernate
* Maven
* Intellij idea
* Lombok
* mysql.cj.jdbc.Driver
* Postman
* Git

## Steps to install

**1. Clone the application**

```bash
git clone https://github.com/zheki4sh05/bpms-enterprise.git
```

**2. Create MySQL database**

```sql
CREATE
DATABASE library
```

**3. Run the SQL script file**

**4. Change MySQL Username and Password as per your MySQL Installation**

+ open `src/main/resources/application.properties` file.

+ change `spring.datasource.username` and `spring.datasource.password` as per your installation

**5. Run the app**

You can run the spring boot app by typing the following command -

```bash
mvn spring-boot:run
```

You can also package the application in the form of a `jar` file and then run it like so -

```bash
mvn package
java -jar bpms-enterprise-0.0.1-SNAPSHOT.jar
```

The server will start on port 8080.




