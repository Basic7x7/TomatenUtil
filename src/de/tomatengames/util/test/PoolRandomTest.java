package de.tomatengames.util.test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import de.tomatengames.util.pool.LinkedPool;
import de.tomatengames.util.pool.Pooled;

class PoolRandomTest {
	
	public static final Random RND = new Random();
	
	private static void sleep(long millis) {
		if (millis <= 0)
			return;
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	private static void sleepFree() {
		sleep(RND.nextInt(2));
	}
	private static void sleepClaimed() {
		sleep(RND.nextInt(2));
	}
	
	public static void main(String[] args) {
		
		LinkedPool<Element> pool = new LinkedPool<>(Element::new);
		
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				long opcount = 0;
				while (true) {
					sleepFree();
					try (Pooled<Element> pooled = pool.claim()) {
						if (!pooled.get().claimed.compareAndSet(false, true))
							throw new AssertionError("Element already claimed");
						sleepClaimed();
						if (!pooled.get().claimed.compareAndSet(true, false))
							throw new AssertionError("Element already free");
					}
					if ((++opcount & 0xFFF) == 0) {
						System.out.println(Thread.currentThread().getName() +
								" has done " + opcount + " operations");
					}
				}
			}).start();
		}
		
	}
	
	private static class Element {
		private AtomicBoolean claimed = new AtomicBoolean(false);
	}
	
}
