package net.pixelatedd3v.bossmessenger.utils;

import java.util.Random;

public class RandomUtils {
	public static final Random RANDOM = new Random();

	public static int randInt(int max) {
		return RANDOM.nextInt(max);
	}
}
