package io.github.tanmaychitnis24.rps.gameplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Random;

public class GamePlay {
	
	private int userScore;
	private int computerScore;
	private Shuffler shuffler;
	private BufferedReader reader;
	
	public GamePlay() {
		this.userScore = 0;
		this.computerScore = 0;
		this.shuffler = new Shuffler(new Random());
		this.reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public int askUserForNumberOfTurns() {
		while(true) {
			System.out.println("How many rounds will we play ?");
			int turns = 0;
			try {
				
				String inputLne = reader.readLine();
				if(inputLne == null) {
					exitGameAbruptly();
					return 0;
				}
				turns = Integer.parseInt(inputLne.trim());
				if(turns <=  0) {
					System.out.println("You must choose a number greater than 0 for number of turns ..");
					continue;
				}
				System.out.println("Lets play "+ turns+" rounds");
			} catch (NumberFormatException e) {
				System.out.println("You entered an invalid character, lets try again.. Please enter a number");
				continue;
			} catch (IOException e) {
				exitGameAbruptly();
				return 0;
			}
			return turns;
		}
	}
	
	public void play(int numberOfRoundsThisGame) {
		for(int turn=0; turn<numberOfRoundsThisGame; turn++) {
			
			Optional<Move> capturedUserMove =  captureUsersMove();
			if(capturedUserMove.isEmpty()) {
				System.out.println("Exiting ...");
				break;
			}
			Move userMove = capturedUserMove.get();
			Move computerMove = shuffler.nextItem();
			
			System.out.println("Your "+ userMove.description() +" clashed with "+ computerMove.description());
			
			if(userMove.equals(computerMove)) {
				System.out.println("This turn was a tie !!!");
				continue;
			}
			if(userMove.beats(computerMove)) {
				userScore++;
				System.out.println("You win this turn.... !!!");
			} else {
				computerScore++;
				System.out.println("You loose this turn.... :(  !!!");
			}
		}
		endOfGameDisplay();
	}
	
	private Optional<Move> captureUsersMove() {
		eachTurnDisplay();
		
		while(true) {
			System.out.println("Please enter a single character from following choices");
			System.out.println(" R- Rock | P - Papper | S - Scissors | Q - Quit game");
			
			try {
				String inputLine = reader.readLine();
				if(inputLine == null) {
					exitGameAbruptly();
					return Optional.empty();
				}
				
				String turnInput = inputLine.trim().toUpperCase();
				
				if("Q".equals(turnInput)) {
					userQuitGame();
					return Optional.empty();
				}
				
				Optional<Move> captureMove = Move.fromKey(turnInput); 
				if (captureMove.isEmpty()) {
					System.out.println("You entered "+turnInput+ " you can only choose (R, P, S or Q). Please try again.");
					continue;
				} else {
					return captureMove;
				}				
				
			} catch (IOException e) {
				exitGameAbruptly();
				return Optional.empty();
			}
		}
		
	}
	
	private void eachTurnDisplay() {
		System.out.println(" ================Score================= ");
		System.out.println("|\t\t\t You- "+ this.userScore +" Computer- "+ this.computerScore +"\t\t\t|");
		System.out.println(" ======================================= ");
		
		System.out.println(" ==================================== ");
		System.out.println("|\t\t What is your next choice\t\t |");
		System.out.println(" ==================================== ");
		System.out.println();
	}
	
	private void endOfGameDisplay() {
		System.out.println(" ================Final Score================= ");
		System.out.println("|\t\t\t You- "+ this.userScore +" Computer- "+ this.computerScore +" \t\t\t|");
		System.out.println(" ============================================ ");
		
		if(this.userScore == this.computerScore) {
			System.out.println("The scores are tied !!!!!");
		} else if (this.userScore > this.computerScore) {
			System.out.println("You beat your oponent fair and square  !!!!!");
		} else {
			System.out.println("You lost :( :( ");
		}
		
		System.out.println(" ================--------------------====== ");
		System.out.println("| Thanks for Playing :) See you next time  |");
		System.out.println(" ========================================== ");
		
	}
	
	private void exitGameAbruptly() {
		System.out.println("Something went wrong while playing the game. Sorry exiting !!!");
	}
	
	private void userQuitGame() {
		System.out.println("You choose to quit the game....");
	}

}
