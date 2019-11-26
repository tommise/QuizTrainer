# QuizTrainer

QuizTrainer is a Java based flashcard studying application used in a desktop setting. User can make their own cards and rehearse them accordingly. The cards will be chosen from a deck using spaced repetition. 

## Documentation
[Architecture](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/architecture.md)

[Requirements specifications](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/requirements_specifications.md)

[Työaikakirjanpito](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/tuntikirjanpito.md)

## Instructions

### Running the program
Run from the root folder:
```
mvn compile exec:java -Dexec.mainClass=quiztrainer.ui.UserInterface

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
Generated jar will be found from /target/ under the name QuizTrainer-1.0-SNAPSHOT
### JavaDoc
```
mvn javadoc:javadoc
```
The report will be found from /target/site/apidocs/index.html
### Checkstyle
```
mvn jxr:jxr checkstyle:checkstyle
```
The report will be found from /target/site/checkstyle.html

