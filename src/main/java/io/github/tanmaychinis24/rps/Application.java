package io.github.tanmaychinis24.rps;

import io.github.tanmaychinis24.rps.gameplay.GamePlay;

public class Application {

	public static void main(String[] args) {
		System.out.println(" ==================================== ");
		System.out.println("| Lets Play some Paper Rock Scissors |");
		System.out.println(" ==================================== ");
		System.out.println();
		
		GamePlay game = new GamePlay();
		int turns = game.askUserForNumberOfTurns();
		game.play(turns);
		System.exit(0);				
	}

}
