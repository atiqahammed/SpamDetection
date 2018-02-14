package test;

public class Main {
	public static void main(String[] args) {
		//System.out.println("Hello world");
		SpamDetector detector = new SpamDetector(4601, 58, "spamData.txt");
		detector.inputData();
		detector.test();
		
		
		
	}
}
