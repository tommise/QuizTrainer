# QuizTrainer

QuizTrainer is a Java based flashcard studying application used in a desktop setting. User can make their own cards and rehearse them accordingly. The cards will be chosen from a deck using spaced repetition. 

## Documentation

[Requirements specifications](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/requirements_specifications.md)

[Työaikakirjanpito](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/tuntikirjanpito.md)

## Instructions

### Running the program
Run from the root folder:
```
mvn compile exec:java -Dexec.mainClass=Main
```
### Running tests
```
mvn test
```
### Test coverage report
```
mvn test jacoco:report
```
The report will be found from /target/site/jacoco/index.html
### Build jar
```
mvn package
```

