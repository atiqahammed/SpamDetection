package main;

import detector.SpamDetector;

public class Test {
	public static void main(String[] args) {
		SpamDetector detector = new SpamDetector("spamData.txt");
		detector.inputData();
		detector.test();
	}
}
