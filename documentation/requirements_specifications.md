# Requirements specification

## Introduction

This application is a flashcard based studying application used in a desktop setting. User can make their own cards and rehearse the cards accordingly. The cards will be chosen from a deck using SRS (Spaced repetition system). Only registered users can use the application.

## Users

There is one type of user which is a registered user. Unregistered users can register to the application through signup form. Upon opening the app, the user will be provided with two options: Login or Signup. Registered users can login with their credentials, unregistered users can signup by pressing the signup button and they will be forwarded to the signup form view.

## Basic functionality

### Before login

- User can create an account by providing a username which is not taken.
- User can login to the application using previously made username.

### After login

User will see three options “Rehearse, My cards and Log out”:

#### Rehearse:

- User will be given a flashcard from the default deck using spaced repetition.
- User has few possible answers to choose from. After the user has answered, correct answer will be shown. Depending on the answer, the card “difficulty” will be updated. If the answer is incorrect, the card will be rehearsed more frequently than the card which was correcly answered.

#### My cards:

- User can add a new flashcard. While adding a new flashcard, it is required to add a question, a correct answer and x amount of possible other choices. The flashcard will be added to a default deck.
- User can view what cards are in the default deck.

#### Log out:

- User can log out which will bring the view back to the default screen.

## Further development

- After login, user will see fourth option: Statistics. Statistics will show different statistics related to practising for example how many cards the user has practised.
- Pre-made deck will be taken from the cloud and users can use them like a normal deck.
- User can create different decks and modify them. One unique card can be placed in many different decks simultaneously.
- The users have a password.
