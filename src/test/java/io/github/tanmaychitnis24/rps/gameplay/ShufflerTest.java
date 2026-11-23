package io.github.tanmaychitnis24.rps.gameplay;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class ShufflerTest {
	
	private final int copyOfItemsInEachBag = 3;
	private final int refillAtSize = 4;
	
	@Test
	public void testRandomnessBoundsOfShufflerAtRefillBoundary() {
		/*So the way to test randomness is by testing the bounds
		Because we know our ShufflerTest has 3 copies of R P and S;
		which get refilled when 4 item remain in the bag
		
		our first bound to test is upper bound - 
		 no matter what random 5 items we have pulled out,
		 because there can be max 3 Rs Ps or Ss .. we can
		 never have 4 of any of them in the 5 items we will draw
		 before a refill kicks in
		 
		our second bound test is lower bound - 
		 no matter what random 5 items we pull out,
		 because after 3 of any have have been drawn,
		 a different one will always be drawn
		 so the number of distinct draws can never be less than 2
		*/
		
		Shuffler shuffler = new Shuffler(new Random(123));
		
		List<Move> drawnItems = new ArrayList<Move>();
		
		for (int i=0; i<refillAtSize; i++) {
			drawnItems.add(shuffler.nextItem());
		}
		
		//upper bound
		assertThat(drawnItems).filteredOn(Move.ROCK::equals).hasSizeLessThanOrEqualTo(copyOfItemsInEachBag);
		assertThat(drawnItems).filteredOn(Move.PAPER::equals).hasSizeLessThanOrEqualTo(copyOfItemsInEachBag);
		assertThat(drawnItems).filteredOn(Move.SCISSORS::equals).hasSizeLessThanOrEqualTo(copyOfItemsInEachBag);
		
		Set<Move> drawnSet = new HashSet<>(drawnItems);
		//lower bound
		assertThat(drawnSet).hasSizeGreaterThanOrEqualTo(2); // 2 can be converted to a formula
		
	}
	
	@Test 
	public void testUpperBoundsOfShufflerBeyondRefillBoundary() {
		
		Shuffler shuffler = new Shuffler(new Random(123));
		List<Move> drawnItems = new ArrayList<Move>();
		int numberOfRefillsToTest = 5;
		
		int itemsToDraw = (copyOfItemsInEachBag*3 - refillAtSize) * numberOfRefillsToTest;
		
		for(int i=0; i<itemsToDraw; i++) {
			drawnItems.add(shuffler.nextItem());
		}
		
		//upper bound
		assertThat(drawnItems).filteredOn(Move.ROCK::equals).hasSizeLessThanOrEqualTo(copyOfItemsInEachBag*numberOfRefillsToTest);
		assertThat(drawnItems).filteredOn(Move.PAPER::equals).hasSizeLessThanOrEqualTo(copyOfItemsInEachBag*numberOfRefillsToTest);
		assertThat(drawnItems).filteredOn(Move.SCISSORS::equals).hasSizeLessThanOrEqualTo(copyOfItemsInEachBag*numberOfRefillsToTest);
		
		
	}
	
	//also the lower bound test
	@Test 
	public void testFairDistributionOfShufflerBeyondRefillBoundary() {
		
		Shuffler shuffler = new Shuffler(new Random(123));
		List<Move> drawnItems = new ArrayList<Move>();
		int totalDraws = 500;
		
		for(int i=0; i<totalDraws; i++) {
			drawnItems.add(shuffler.nextItem());
		}
		
		int minExpectedPerItem = (int) (totalDraws * 0.30); // ideally we would want 33.33% but 30% is still giving us 90% accuracy
		
		assertThat(drawnItems).filteredOn(Move.ROCK::equals).hasSizeGreaterThanOrEqualTo(minExpectedPerItem);
		assertThat(drawnItems).filteredOn(Move.PAPER::equals).hasSizeGreaterThanOrEqualTo(minExpectedPerItem);
		assertThat(drawnItems).filteredOn(Move.SCISSORS::equals).hasSizeGreaterThanOrEqualTo(minExpectedPerItem);
		System.out.println(drawnItems.stream().filter(Move.ROCK::equals).count()+ " vs "+minExpectedPerItem);
		
	}

}
