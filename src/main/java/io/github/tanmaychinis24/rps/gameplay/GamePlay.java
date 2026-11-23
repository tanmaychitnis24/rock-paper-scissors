package io.github.tanmaychinis24.rps.gameplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Set;

public class GamePlay {
	
	private int userScore;
	private int computerScore;
	private Shuffler shuffler;
	
	private static final Set<String> VALID_USER_INPUT = Set.of("R", "P", "S", "Q");
	private static final String UNDETERMINED = "U";
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
				turns = Integer.parseInt(reader.readLine());
				System.out.println("Lets play "+ turns+" rounds");
			} catch (NumberFormatException e) {
				System.out.println("You entered an invalid number, lets try again..");
				continue;
			} catch (IOException e) {
				System.out.println("Sorry something went wrong trying to read the input. Exiting ...");
				System.exit(1);
			}
			return turns;
		}
	}
	
	public void play(int numberOfRoundsThisGame) {
		for(int turn=0; turn<numberOfRoundsThisGame; turn++) {
			String userSelection =  eachTurnDisplay();
			String computerSelection = shuffler.nextItem();
			
			System.out.println("Your "+ userSelection +" clashed with "+ computerSelection);
			
			if(userSelection.equals(computerSelection)) {
				System.out.println("This turn was a tie !!!");
				continue;
			}
			
			if(isUserWinningThisTurn(userSelection,computerSelection)) {
				userScore++;
				System.out.println("You win this turn.... !!!");
			} else {
				computerScore++;
				System.out.println("You loose this turn.... :(  !!!");
			}
		}
		endOfGameDisplay();
	}
	
	private String eachTurnDisplay() {
		System.out.println(" ================Score================= ");
		System.out.println("|\t\t\t You- "+ this.userScore +" Computer- "+ this.computerScore +"\t\t\t|");
		System.out.println(" ======================================= ");
		
		System.out.println(" ==================================== ");
		System.out.println("|\t\t What is your next choice\t\t |");
		System.out.println(" ==================================== ");
		System.out.println();
		
		String turnInput=UNDETERMINED;
		
		while(true) {
			System.out.println("Please enter a single character from following choices");
			System.out.println(" R- Rock | P - Papper | S - Scissors | Q - Quit game");
			
			try {
				turnInput = reader.readLine();
				if(!VALID_USER_INPUT.contains(turnInput)) {
					System.out.println("You entered "+turnInput+ " you can only choose (R, P, S or Q). Please try again.");
					continue;
				}
				
				if("Q".equalsIgnoreCase(turnInput)) {
					System.exit(0);
				}
				
			} catch (IOException e) {
				//TODO: throw a custom exception and log error
			}
			return turnInput;
		}
		
	}
	
	private boolean isUserWinningThisTurn(String userSelection, String computerSelection) {
		if("R".equals(userSelection)) {
			return ("S".equals(computerSelection)); 
		}
		
		if("P".equals(userSelection)) {
			return ("R".equals(computerSelection)); 
		}

		if("S".equals(userSelection)) {
			return ("P".equals(computerSelection)); 
		}
		
		return true;
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

}
