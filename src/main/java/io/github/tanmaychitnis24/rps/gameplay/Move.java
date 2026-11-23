package io.github.tanmaychitnis24.rps.gameplay;

import java.util.Optional;

public enum Move {
	
	ROCK("R", "ROCK"),
	PAPER("P", "PAPER"),
	SCISSORS("S", "SCISSORS");
	
	private final String key;
	private final String description;
	
	Move(String move, String description) {
		this.key = move;
		this.description = description;
	}
	
	public String description() {
		return this.description;
	}
	
	public static Optional<Move> fromKey(String key) {
	    for (Move m : values()) {
	        if (m.key.equals(key)) return Optional.of(m);
	    }
	    return Optional.empty();
	}
	
	public boolean beats (Move opponent) {
		return switch (this) {
		case ROCK -> opponent == SCISSORS;
		case PAPER -> opponent == ROCK;
		case SCISSORS -> opponent == PAPER;
		};
	}
	
}
