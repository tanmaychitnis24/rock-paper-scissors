package io.github.tanmaychitnis24.rps.gameplay;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GamePlayTest {
	
	@Mock
	Shuffler shuffler;
	
	@Mock
	BufferedReader reader;
	
	@InjectMocks
	GamePlay game;
	
	private static final PrintStream ORIGINAL_OUT = System.out;
	private ByteArrayOutputStream outputCapture;
	
	@AfterEach
	void resetSystemIn() {
		System.setIn(System.in);
		System.setOut(ORIGINAL_OUT);
	}
	
	
	@Test
	public void testHappyPathGamePlay_AllRockCombos() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
		when(shuffler.nextItem()).thenReturn(Move.ROCK);
		when(reader.readLine()).thenReturn("R","p ","S");
		game.play(3);
		
		verify(shuffler, times(3)).nextItem();
		
		String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).containsSubsequence(
			    "Your " + Move.ROCK.description() + " clashed with " + Move.ROCK.description(),
			    "This turn was a tie !!!",
			    "Your " + Move.PAPER.description() + " clashed with " + Move.ROCK.description(),
			    "You win this turn.... !!!",
			    "Your " + Move.SCISSORS.description() + " clashed with " + Move.ROCK.description(),
			    "You loose this turn.... :(  !!!"
			    );
	}
	
	@Test
	public void testHappyPathGamePlay_AllPaperCombos() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
		when(shuffler.nextItem()).thenReturn(Move.PAPER);
		when(reader.readLine()).thenReturn(" r","P","S");
		game.play(3);
		
		verify(shuffler, times(3)).nextItem();
		
		String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).containsSubsequence(
			    "Your " + Move.ROCK.description() + " clashed with " + Move.PAPER.description(),
			    "You loose this turn.... :(  !!!",
			    "Your " + Move.PAPER.description() + " clashed with " + Move.PAPER.description(),
			    "This turn was a tie !!!",
			    "Your " + Move.SCISSORS.description() + " clashed with " + Move.PAPER.description(),
			    "You win this turn.... !!!"
			    );
	}
		
	@Test
	public void testHappyPathGamePlay_AllScissorsCombos() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
		when(shuffler.nextItem()).thenReturn(Move.SCISSORS);
		when(reader.readLine()).thenReturn("R","P"," s ");
		game.play(3);
		
		verify(shuffler, times(3)).nextItem();
		
		String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).containsSubsequence(
			    "Your " + Move.ROCK.description() + " clashed with " + Move.SCISSORS.description(),
			    "You win this turn.... !!!",
			    "Your " + Move.PAPER.description() + " clashed with " + Move.SCISSORS.description(),
			    "You loose this turn.... :(  !!!",
			    "Your " + Move.SCISSORS.description() + " clashed with " + Move.SCISSORS.description(),
			    "This turn was a tie !!!"
			    );
	}	
	
	@Test
	public void testHappyPathGamePlay_withOneInvalidTurnInput() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
		when(shuffler.nextItem()).thenReturn(Move.SCISSORS);
		when(reader.readLine()).thenReturn("R","Z", "R", "S","P","S");
		game.play(5);
		
		verify(shuffler, times(5)).nextItem();

		
		String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).contains("You entered Z you can only choose (R, P, S or Q). Please try again.");
		assertThat(printedText).contains("This turn was a tie !!!");
		assertThat(printedText).contains("You- 2 Computer- 1");
		assertThat(printedText).contains("You beat your oponent fair and square  !!!!!");
	}
	
	@Test
	public void testQuitInBetweenGamePlay() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
		when(shuffler.nextItem()).thenReturn(Move.SCISSORS);
		when(reader.readLine()).thenReturn("R", "R", "Q");
		game.play(5);
		
		verify(shuffler, times(2)).nextItem();

		
		String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).contains("You- 2 Computer- 0");
		assertThat(printedText).contains("You beat your oponent fair and square  !!!!!");
		assertThat(printedText).contains("Final Score");
		assertThat(printedText).contains("You choose to quit the game");
	}
	
	@Test
	public void testNullReadLineInputDuringTurns() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
		when(shuffler.nextItem()).thenReturn(Move.SCISSORS);
		when(reader.readLine()).thenReturn("R", null);
		game.play(3);
		
		verify(shuffler, times(1)).nextItem();

		
		String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).contains("You- 1 Computer- 0");
		assertThat(printedText).contains("Final Score");
	}
	
	@Test
	public void testExceptionDuringTurns() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
		when(shuffler.nextItem()).thenReturn(Move.SCISSORS);
		when(reader.readLine()).thenReturn("R").thenThrow(new IOException());
		game.play(3);
		
		verify(shuffler, times(1)).nextItem();

		
		String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).contains("You- 1 Computer- 0");
		assertThat(printedText).contains("Final Score");
	}
	
	@Test
	public void testInvalidNumberOfTurns_followedBySuccess() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
        when(reader.readLine()).thenReturn("R", "R", "4");
        game.askUserForNumberOfTurns();
        
        verify(reader, times(3)).readLine();
        
        String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).containsSubsequence("You entered an invalid character, lets try again.. Please enter a number",
													"Lets play 4 rounds");
			
	}
	
	@Test
	public void testNullReadLine_followedByFailure() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
        when(reader.readLine()).thenReturn("R", "-3", null);
        game.askUserForNumberOfTurns();
        
        verify(reader, times(3)).readLine();
        
        String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).containsSubsequence("You entered an invalid character, lets try again.. Please enter a number",
													"You must choose a number greater than 0 for number of turns ..",
													"Something went wrong while playing the game. Sorry exiting !!!");
	
	}

}
