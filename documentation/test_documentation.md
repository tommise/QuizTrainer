# Test Documentation

Software has been tested with unit- and integration tests with JUnit unit testing framework and 

## Unit- and integration tests

The main functionality which is located in the _quiztrainer.domain_ package is tested with the class [QuizTrainerServiceTest](https://github.com/tommise/ot-harjoitustyo/blob/master/QuizTrainer/src/test/java/quiztrainer/domain/QuizTrainerServiceTest.java). The integration tests create a temporary database file _quiztrainerTest.db_ and use the QuizTrainerService as the app would use it with the normal _quiztrainer.db_ database. After the test are done, or namely in teardown phase, the database file is removed.

### DAO

Class [QuizTrainerService](https://github.com/tommise/ot-harjoitustyo/blob/master/QuizTrainer/src/main/java/quiztrainer/domain/QuizTrainerService.java) acts as a intermediary between the SQLite database and UI. With this in mind, [QuizTrainerServiceTest](https://github.com/tommise/ot-harjoitustyo/blob/master/QuizTrainer/src/test/java/quiztrainer/domain/QuizTrainerServiceTest.java) holds all the propriety test to functionally test the classes in _quiztrainer.dao_ as it's method call the dao class in question.

### Logic

The main utils of the app, namely Leitner system and Card interval classes, have been tested accordingly in [LeitnerTest](https://github.com/tommise/ot-harjoitustyo/blob/master/QuizTrainer/src/test/java/quiztrainer/domain/QuizTrainerServiceTest.java) and [CardIntervalTest](https://github.com/tommise/ot-harjoitustyo/blob/master/QuizTrainer/src/test/java/quiztrainer/domain/QuizTrainerServiceTest.java).

## Test coverage

When we run the command "mvn test jacoco:report" and create the test coverage raport shown here:

![Test coverage](images/test_coverage.png "Test coverage")

We can see that with _quiztrainer.logic_ we have a total test coverage of 98% in missed instructions, _quiztrainer.dao_ 91% and _quiztrainer.domain_ 87% respectively. That makes it 183 instructions missed from the total of 1852 bringing the total coverage of missed instructions to 90%.

As what comes to missed branches, the coverage of _quiztrainer.logic_ is 86%, _quiztrainer.dao_ 88% and _quiztrainer.domain_ 76%. Bringing the total coverage of missed branches to 82%.

Note that UI has been excluded from the raport, as per project instructions.

### Installation and configuration

The software has been tested following [User Instructions](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/user_instructions.md) in a Linux environment. For a broader test coverage multiple users have been added during testing.

### Functionalities

The functionalities has been tested as in [Requirements specification](https://github.com/tommise/ot-harjoitustyo/blob/master/documentation/requirements_specifications.md) document states them. Package _quiztrainer.ui_ holds a class named GUIHelper which tested successfully the invalid inputs like empty fields during testing.

## Left to improve
