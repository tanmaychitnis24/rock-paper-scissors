package io.github.tanmaychinis24.rps.gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Shuffler {
	
	private final List<String> bagItems = List.of("R","P","S");
	private final int copyOfItemsInEachBag = 3;
	private final int refillAtSize = 4;
	private Random randomizer;
	
	public Shuffler(Random random) {
		this.randomizer = random;
	}
	
	private List<String> shuffleBag = new ArrayList<>();
	
	public String nextItem() {
		if(shuffleBag.size() <= refillAtSize) {
			refillAndShuffle();
		}
		return shuffleBag.remove(shuffleBag.size() -1);
	}
	
	private void refillAndShuffle() {
		shuffleBag.clear();
		for(int i=0; i<copyOfItemsInEachBag; i++) {
			shuffleBag.addAll(this.bagItems);
		}
		Collections.shuffle(shuffleBag, randomizer);
	}
}
