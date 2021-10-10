# gocity Assessment

The backend service for the management of product information.

###### Database
The application requires  a MariDB or MySQL database,  information can be found on these at the following link:
[MySQL](https://www.mysql.com)https://www.mysql.com/downloads/

Create the schema gocitydb and grant a user all privileges to that schema.
the use name and password must be set on the corresponding properties in the 
application condiguration file. See below.

## Setup

Download the application using github, the application can be accessed at the following link:
[GitHub](https://github.com/barnettt/gocity)

The application can be built with [Maven](https://maven.apache.org/download.cgi) and executed in your favourite ide.
After the build, there will be an executable jar in the **gocity/target** directory that can be executed as follows:
>    java -jar target/gocity-0.0.1-SNAPSHOT.jar

###### Startup

At startup the application will create the required tables in the local database.
The following properties are set in the **applicatiion.properties** file:
> logging.level.org.springframework: INFO
> 
> logging.level.org.hibernate:INFO
 
> spring.jpa.generate-ddl=true
 
> spring.jpa.show-sql=true

> spring.datasource.url=jdbc:mysql://localhost:3306/gocitydb
 
> spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
 
> spring.datasource.username=<user>
 
> spring.datasource.password=<password>

> spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
 
> flyway.url= jdbc:mysql://localhost:3306/gocitydb
 
> flyway.schemas=gocitydb

> loadFromFile=true

The database tables and Category data is loaded via [Flyway](https://flywaydb.org/)
and is Flyway under version control.

## Product data.
The product data, is loaded when the application starts up. The application.properties has the **loadFromFile**
property to enable this. After the first execution of the application set this to false.

the property can also be set on the command line with the '-D' prefix:
> java -jar -DloadFromFile=false target/gocity-0.0.1-SNAPSHOT.jar

The product data is loaded from the following file:

> fileName=src/main/resources/products_csv.csv

## Implemented  Requirements.

* As a user, I want to see all products
* As a user, I want to be able to sort the products by Name
* As a user, I want to be able to filter by Category

* As a user, I want to be able to add a new product

* Challenge: Add validation so that the ‘Last Purchased Date’ cannot be set to be less than the ‘Creation Date’
  * As a user, I want to be able to update a product
  * As a user, I want to be able to delete a product
## Rest Apis

The following apis are available. You can use postman or similar tool to exercise the Apis as Follows:
> prefix with http://localhost:port default port is 8080

* Find all products: /gocity/v1.0/products
* Delete product: /gocity/v1.0/products/{id}
* Update product: /gocity/v1.0/products
* Add new product:/gocity/v1.0/products
* Filter by Category: /gocity/v1.0/products/category/{categoryId}
* Sort by product name /gocity/v1.0/products/sort

Please also look at **NOTES** for thoughts and assumptions
