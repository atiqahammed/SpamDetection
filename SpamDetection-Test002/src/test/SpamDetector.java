package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SpamDetector {
	private String[][] allData;						// stores all data
	private int row;								// size of data
	private int column;								// size of attributes
	private File dataFile;							// file that content all data
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private Set<Integer> takenIndex;
	private int testDataSize;
	private int trainDataSize;
	private ArrayList<Integer> testDataIndex;
	private int totalHam;
	private int totalSpam;
	
	
	
	public SpamDetector(int row, int column, String filename) {
		this.row = row;
		this.column = column;
		dataFile = new File(filename);
		testDataSize = row / 10;
		trainDataSize  = row- testDataSize;
		//takenIndex = new HashSet<Integer>();
	}

	public void inputData() {
		initializeDataStructure();
		String s  = null;
		int count = 0;
		try {
			while((s = bufferedReader.readLine()) != null) {
				allData[count] = s.split(",");
				//System.out.println(count+1 + " >> "+allData[0].length);
				//System.out.println(count + " >> " +s);
				count++;
			}
			
		} catch (IOException e) {
			System.out.println("got error in reading data using bufferReader");
			//e.printStackTrace();
		}
		
		
	}

	private void initializeDataStructure() {
		allData = new String[row][];/*
		for(int i = 0; i < row; i++)
			allData[i] = new double[column];*/	
		
		try {
			fileReader = new FileReader(dataFile);
		} catch (FileNotFoundException e) {
			System.out.println("file not Found");
			//e.printStackTrace();
		}
		bufferedReader = new BufferedReader(fileReader);
		
	}
	
	public void test() {
		calculateAndDetect();
	}
	
	private void calculateAndDetect() {
		//takenIndex = new HashSet<Integer>();
		//System.out.println(testDataSize + " " + trainDataSize);
		testDataIndex = new ArrayList<Integer>();
		while(testDataIndex.size() < testDataSize) {
			int newIndex = new Random().nextInt(row);
			if(!testDataIndex.contains(newIndex)) testDataIndex.add(newIndex);
		}
		
		totalSpam = 0;
		totalHam = 0;
		for(int i = 0; i < row; i++)
			if(!testDataIndex.contains(i)){
				if(allData[i][column - 1].equals("1")) totalSpam++;
				else totalHam++;
			}
		
		
		
		
		System.out.println(totalHam + " "+ totalSpam);
		
		
		double totalRight = 0.0;
		
		for(int i = 0; i < testDataSize; i++){
			System.out.println(i);
			String ll= detectOnTestIndex(testDataIndex.get(i));
			if(ll.equals(allData[testDataIndex.get(i)][column-1])) totalRight++;
		}
		
		System.out.println(totalRight/testDataSize);
			//System.out.println(testDataIndex.get(i));
		//for(int k = 0; k < testDataSize; k++){
		
		//}
		//System.out.println(probabilityOfHamGivenFeatures +" "+ probabilityOfSpamGivenFeatures);
		
		
		/*
		System.out.println(allData[testDataIndex.get(0)][57]);
		if(probabilityOfHamGivenFeatures/ probabilityOfSpamGivenFeatures> 0.15) System.out.println(0);
		else System.out.println(1);
		//System.out.println(probabilityOfHamGivenFeatures/ probabilityOfSpamGivenFeatures);
		*/
		//if()
		
		//int countOfValue[] = countAttributeValue(allData[testDataIndex.get(0)][0], 0); 
		
		
		
		//System.out.println(countOfValue[0] + " / "+ totalHam +",,," + countOfValue[1]+" / "+totalSpam);
		//System.out.println(totalHam + " " + totalSpam);
		//System.out.println(testDataIndex.size());
		//for(int i=0; i < testDataIndex.size(); i++)
		//	System.out.print(testDataIndex.get(i) + " ");
		//for(int i = 0; i < testDataSize; i++) {
			//testDataIndex.add();
		//}
		
		
		
		
	}

	private String detectOnTestIndex(int index) {
		double probabilityOfSpamGivenFeatures = 1.0;
		double probabilityOfHamGivenFeatures = 1.0;
		
		for(int i = 0; i < column - 1; i++){
			int countOfValue[] = countAttributeValue(allData[index][i], i);
			
			probabilityOfHamGivenFeatures *= (double)countOfValue[0]/totalHam;
			probabilityOfSpamGivenFeatures *= (double)countOfValue[1]/totalSpam;
			//System.out.println("hamcount > "+ countOfValue[0] +" pp > "+probabilityOfHamGivenFeatures +" spamcount> "+countOfValue[1]+" pp > "+probabilityOfSpamGivenFeatures);
			//if(countOfValue[0] > 0) probabilityOfHamGivenFeatures *= (double) countOfValue[0]/totalHam;
			//if(countOfValue[1] > 0) probabilityOfHamGivenFeatures *= (double) countOfValue[1]/totalSpam;
		}
		
		if(probabilityOfHamGivenFeatures > probabilityOfSpamGivenFeatures) return "0";
		//else "1";
		
		return "1";
		
		///*System.out.print("spam > ");*/
		//System.out.println(allData[index][57]);
		
	}

	private int[] countAttributeValue(String string, int index) {
		int []arr = new int[2];
		arr[0] = 0;
		arr[1] = 0;
		for(int i = 0; i < row; i++)
		{
			if(!testDataIndex.contains(i))
			{
				if(allData[i][index].equals(string) && allData[i][column -1].equals("1")) arr[1]++;
				if(allData[i][index].equals(string) && allData[i][column -1].equals("0")) arr[0]++;
			}
		}
		
		if(arr[0] == 0) arr[0] = 1;
		if(arr[1] == 0) arr[1] = 1;
		return arr;
	}
	
}
