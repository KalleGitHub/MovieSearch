# Getting Started
- Simple Spring Boot application that provides a REST API for searching movies.

### External Dependencies
- Was built with Java 21 but should be compatible with Java 11 and up
- Does not have any other external dependencies

### Running the application
- Clone the repository
- Run the application using the following command:
  - `./gradlew bootRun`
  - The application will start on port 8081

### Database
- The application uses simple an in-memory H2 database.
    - Currently, H2 is also in in-memory mode, so the data will be lost when the application is stopped.
    - To change this, you can modify the key "spring.datasource.url" in `application.properties` file to use a file-based database. Example: spring.datasource.url=jdbc:h2:file:./data/demo
    - Database console is enabled and can be accessed at http://localhost:8081/h2-console
        - User Name: sa
        - Password: password

### java/io/kalle/demo/movie/search
- Search side of the application handles the search for movies.

### java/io/kalle/demo/movie/favorite
- Favorite side of the application handles saving and retrieving favorite movies.