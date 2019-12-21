# QuizTrainer

QuizTrainer is a Java based flashcard studying application used in a desktop setting. User can make their own cards and rehearse them accordingly. The cards will be chosen from a deck using using slightly modified [Leitner system](https://en.wikipedia.org/wiki/Leitner_system) with probabilities related to the boxes.

## Documentation
[Architecture](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/architecture.md)

[Requirements specifications](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/requirements_specifications.md)

[User instructions](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/user_instructions.md)

[Test documentation](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/test_documentation.md)

[Timekeeping](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/tuntikirjanpito.md) (in Finnish)

## Releases

[All releases](https://github.com/tommise/ot-harjoitustyo/releases/)

[Latest release: Loppupalautus](https://github.com/tommise/ot-harjoitustyo/releases/tag/loppupalautus)

## Command line instructions

### Running the program
Run from the root folder:
```
mvn compile exec:java -Dexec.mainClass=quiztrainer.ui.UserInterface
```
### Running the latest release
```
java -jar quiztrainer.jar
```
Run from the same folder where the latest release is located.
### Running tests
```
mvn test
```
### Test coverage report
```
mvn test jacoco:report
```
The report will be found from /target/site/jacoco/index.html
### Build and run jar
```
mvn package
```
Generated jar will be found from /target/ under the name QuizTrainer-1.0-SNAPSHOT

```
java -jar target/QuizTrainer-1.0-SNAPSHOT.jar
```
Run generated jar
### Checkstyle
```
mvn jxr:jxr checkstyle:checkstyle
```
The report will be found from /target/site/checkstyle.html
### JavaDoc
```
mvn javadoc:javadoc
```
The report will be found from /target/site/apidocs/index.html

