# Problem statement
Paper-Rock-Scissors is a game for two players. Each player simultaneously opens his/her hand to display a symbol:
• Fist equals rock
• Open hand equals paper
• Showing the index and middle finger equals scissors.

The winner is determined by the following schema:
• Paper beats (wraps) rock
• Rock beats (blunts) scissors
• Scissors beats (cuts) paper.

Write a program that plays Paper-Rock-Scissors between the computer and a real player. You should be able
to play the game n times before the program exits.

# Requirements
## Functional requirements
- The user is asked how many rounds of 'fight' will decide the game and allowed to enter a number. The game then can run for n rounds before it exits declaring the winner
- The user is displayed his choice of rock/paper/scissor on each turn
- The user enter r for rock, p for paper and s for scissors in each turn
- The user can enter q for quitting the game at any point during turns
- the current score should also be displayed each round along with user choices

## Non functional requirements 
- the user experience should be seam-less and the computer game play should be displayed almost instantaneously along with what the user choice
- the computer choice must be fairly random and not a round-robbin of predictable iteration of choices
- if the user inputs his choices fast there should be a minimal to no lag in user taking each turn


## Future scope (out of scope for now)
- an arrow based list picker instead user inputting R P S or Q
- three modes of game easy, medium and hard. Easy where user may be eventually be able to predict the computer move. medium where the computer move is fairly random, hard where the computer can try and predict the user move
- a user profile maintaining at least last 10 user scores
- a high score board
- a count down from 3 to 0 where at 0 the user must have made a choice and the computer choice appears simultaneously


# Design
## Components

### Welcome Display 
A welcome message as soon as the game is loaded, summarizing rules and asking from user the number of fights/turns user wishes in this game play.

### End of game Display 
An exit message before quitting. Shows the final score if turns finish or just exist message if user used Q

### Each turn Display 
Once the user has entered the number of turns the user wishes to play this will be displayed each turn. It will hav the current score standing and the choices user has R/P/S/Q

### Random picker for computer turn
Because we want a fairly random choice to be played against the users choice we need a randomizer.
Java's Random.nextInt is truly random by nature but for a smaller game play of 5/10 or even a 100 turns may bring streaks of same choice and droughts of certain plays (like rock appearing continuously for 6/7 times and paper not appearing even once) which could degrade user experience in some game plays.
A shuffle bag is the general short term solution but with just 3 choices it can soon become predictable especially near bag refils if timed by the user.
We must therefore use best of both worlds by using a shuffle bag but with multiple copies and we refill at half instead of at bag empty.


## High Level game flow
Welcome Display -> User inputs no of turn value -> Each Turn Flow -> End of Game Display

##Each turn flow
Each turn Display -> User input choice -> random picker for computer turn -> compare who won; increment their score -> return score and the two picks for display

