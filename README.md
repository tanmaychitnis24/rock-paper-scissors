# rock-paper-scissors
Rock paper scissor game with computer

## Requires
- Java 21+
- Maven 3.9+

## Build
```bash
mvn clean package
```
Compiles the code, runs all tests, and creates `target/rock-paper-scissors.jar`.
The build fails if any test fails.

## Play
```bash
java -jar target/rock-paper-scissors.jar
```

## Run tests only
```bash
mvn test
```

## Other mvn command as applicable