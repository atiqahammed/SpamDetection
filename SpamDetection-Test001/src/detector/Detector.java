package detector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Detector {

	private ArrayList<String> allWords;
	HashMap<String, Double> probability;// = new HashMap<>();
	ArrayList<String> allSpam = new ArrayList<>();
	ArrayList<String> allHam = new ArrayList<>();

	
	public void train2(String filePath) throws IOException{
		File dataFile = new File(filePath);
		FileReader fr = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(dataFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int i = 0;
		String sCurrentLine = null;
		while ((sCurrentLine = br.readLine()) != null) {
			i++;
			sCurrentLine = sCurrentLine.toLowerCase();
			String newString = sCurrentLine.replaceAll("[^a-zA-Z0-9\\s\\s]", "");
			String[] array = newString.split(" ");
			String[] classes = array[0].split("\t");
			try {
				array[0] = classes[1];
				if(classes[0].equals("ham")) allHam.add(newString);
				else allSpam.add(newString);
				
			} catch (Exception e) {
				continue;
				// System.out.println(count+" "+classes);
			}
			
			//if(classes[0].equals("ham")) System.out.println(i+" got a ham");
			//else System.out.println(i + " got a spam");
		}
		
		
		
		// System.out.println(allHam.size());
		// System.out.println(allSpam.size());
	}
	
	
	
	
	
	public void train(String filePath) {
		File dataFile = new File(filePath);

		HashMap<String, Integer> spamCount = new HashMap<>();
		HashMap<String, Integer> hamCount = new HashMap<>();
		allWords = new ArrayList<>();
		HashMap<String, Double> probability = new HashMap<>();

		FileReader fr = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(dataFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String sCurrentLine = null;
		try {
			int count = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				count++;

				sCurrentLine = sCurrentLine.toLowerCase();
				String newString = sCurrentLine.replaceAll("[^a-zA-Z0-9\\s\\s]", "");
				// System.out.println(newString);
				// Scanner scanner = new Scanner(newString);
				String[] array = newString.split(" ");
				// System.out.println(array.length);
				String[] classes = array[0].split("\t");
				// System.out.println(classes[0]);
				try {
					array[0] = classes[1];
				} catch (Exception e) {
					continue;
					// System.out.println(count+" "+classes);
				}
				for (int x = 0; x < array.length; x++) {
					// System.out.println(count +" "+array[x]);

					if (classes[0].equals("ham")) {
						if (hamCount.get(array[x]) == null)
							hamCount.put(array[x], 1);
						else
							hamCount.put(array[x], hamCount.get(array[x]) + 1);
					} else {
						if (spamCount.get(array[x]) == null)
							spamCount.put(array[x], 1);
						else
							spamCount.put(array[x], spamCount.get(array[x]) + 1);
					}
					if (!allWords.contains(array[x]))
						allWords.add(array[x]);
				}

			}
			sCurrentLine = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ) != null) {

		System.out.println(allWords.size());
		File countFile = new File("count.txt");

		try {
			PrintWriter printWriter = new PrintWriter(countFile);

			double totalHam = hamCount.size();
			double totalSpam = spamCount.size();
			// printWriter.println("Hello world");
			for (int k = 0; k < allWords.size(); k++) {
				double hum = 0.0, spum = 0.0;
				String key = allWords.get(k);

				if (hamCount.get(key) != null)
					hum = hamCount.get(key);
				if (spamCount.get(key) != null)
					spum = spamCount.get(key);

				double pOfHam = hum / totalHam;
				double pOfSpam = spum / totalSpam;
				if (pOfSpam == 0.0)
					printWriter.println(key + " " + 0.0);
				else
					printWriter.println(key + " " + pOfHam / pOfSpam);
			}
			System.out.println("completed");
			printWriter.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void inputTrainData() throws IOException {
		File file = new File("count.txt");
		
		
		probability = new HashMap<>();
		
		FileReader fr = null;
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(file));
			String cString = null;
			
			while((cString = br.readLine()) != null){
				String []arr = cString.split(" ");
				double value = Double.parseDouble(arr[1]);
				if(value == 0.0) probability.put(arr[0], 1.0);
				else  probability.put(arr[0], value);
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String sCurrentLine = null;
		System.out.println("train data has been inputed");
		//System.out.println(probability.get("go"));

	}

	public void test(String string) {
		string = string.toLowerCase();
		string = string.replaceAll("[^a-zA-Z0-9\\s\\s]", "");
		//System.out.println(string);
		String []arr = string.split(" ");
		double ans = 1.0;
		
		for(int i = 0; i < arr.length; i++)
		{
			if(probability.containsKey(arr[i])) ans *= probability.get(arr[i]);
		}
		if(ans < 1.00) System.out.println("Spam");
		else System.out.println("Hum");
		//System.out.println(ans);
		
		//System.out.println(arr[arr.length -1]);
		
	}

}
