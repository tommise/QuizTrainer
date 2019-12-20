# Requirements specification

## Introduction

This application is a flashcard based studying application used in a desktop setting. User can make their own cards and rehearse the cards accordingly. In the rehearse mode, the cards will be chosen using modified [Leitner system](https://en.wikipedia.org/wiki/Leitner_system) with probabilities related to the boxes. Only registered users can use the application.

## Users

There is one type of user which is a registered user. Unregistered users can register to the application through signup form. Upon opening the app, the user will be provided with two options: Login or Signup. Registered users can login with their credentials, unregistered users can signup by pressing the signup button and they will be forwarded to the signup form view.

## Functionality

### Before login

- User can create an account by providing a username which is not taken.
- User can login to the application using previously made username.

### After login

User will see six options “Add a new Deck, Add a new QuizCard, My QuizCards, Statistics, Rehearse and Log out”:

#### Add a new Deck:
- User can add a new flashcard. While adding a new flashcard, it is required to add a question, a correct answer and x amount of possible other choices. The flashcard will be added to a default deck.

#### Add a new QuizCard:
- User can add a new QuizCard. While adding a new card, it is required to add a question, a correct answer, x amount of possible other choices and the information for which deck the QuizCard is to be added to.

#### My QuizCards:

- User can view what cards are in each decks.
- User can remove a specific QuizCard from the software using provided list.

#### Statistics:

- User can view what card has been the most rehearsed
- User can view what card has been most answered right
- User can view what card has been most answered wrong

#### Rehearse:

- User has the possibility to choose the Deck to rehearse from.
- User will be given a random flashcard from the chosen deck using Leitner system logic.
- User has three possible answers to choose from. After the user has answered, correct answer will be shown. Depending on the answer, the card “difficulty” will be updated and the card will be moved to a different box. If the answer is incorrect, the card will be rehearsed more frequently (moved to a lower box) than the card which was correctly answered.

#### Log out:

- User can log out which will bring the view back to the starting scene.

## Future ideas for development
- QuizCards can be moved within different decks.
- One unique card can be placed in many different decks simultaneously.
- Pre-made deck will be taken from the cloud and users can use them like a normal deck.
- The users have a password.
