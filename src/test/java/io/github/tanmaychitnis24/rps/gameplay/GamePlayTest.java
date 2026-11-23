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

import io.github.tanmaychinis24.rps.gameplay.GamePlay;
import io.github.tanmaychinis24.rps.gameplay.Shuffler;

@ExtendWith(MockitoExtension.class)
public class GamePlayTest {
	
	@Mock
	Shuffler shuffler;
	
	@Mock
	BufferedReader reader;
	
	@InjectMocks
	GamePlay game;
	
	private ByteArrayOutputStream outputCapture;
	
	@AfterEach
	void resetSystemIn() {
		System.setIn(System.in);
		System.setOut(System.out);
	}
	
	@Test
	public void testHappyPathGamePlay_withOneInvalidTurnInput() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
		when(shuffler.nextItem()).thenReturn("S");
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
		
		when(shuffler.nextItem()).thenReturn("S");
		when(reader.readLine()).thenReturn("R", "R", "Q");
		game.play(5);
		
		verify(shuffler, times(2)).nextItem();

		
		String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).contains("You- 2 Computer- 0");
		assertThat(printedText).contains("You beat your oponent fair and square  !!!!!");
	}
	
	@Test
	public void testInvalidNumberOfTurns() throws IOException {
		
		outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
		
        when(reader.readLine()).thenReturn("R", "R", "4");
        game.askUserForNumberOfTurns();
        
        verify(reader, times(3)).readLine();
        
        String printedText = outputCapture.toString(StandardCharsets.UTF_8);
		assertThat(printedText).contains("You entered an invalid number, lets try again..");
		assertThat(printedText).contains("Lets play 4 rounds");
		
        
		
	}

}
