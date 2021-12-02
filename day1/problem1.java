//This is for the first day of advent of code - will be doing the following problem in java - text input is in input1.txt and will output the answer to console

import java.io.*;
import java.util.*;

public class problem1 {
	public static void main(String[] args) {
		String currentDir = System.getProperty("user.dir");
		File file = new File(currentDir + "/input1.txt");
		BufferedReader br = null;
	        try{
			br = new BufferedReader(new FileReader(file));
			List<Integer> fileInput = new ArrayList<Integer>();
			String line;
			while((line = br.readLine()) != null) {
				fileInput.add(Integer.parseInt(line));
			}
			int numIncreases = largerThanPrev(fileInput);
			System.out.println("There are " + numIncreases + " measurements that are larger than the previous measurement");

			int numWindowIncreases = largerThanPrevSlidingWindow(fileInput);
			System.out.println("There are " + numWindowIncreases + " three-measurement sliding window increases");
		}

		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				if(br != null){
					br.close();
				}
			}
			catch(IOException ioe) {
				System.out.println("Error closing the BR");
			}
		}

	}
	//this method takes in the input file as an array list (because they are dynamic) and will count the number of times the next number is larger than the prev
	//then return it to the main where it will be printed
	public static int largerThanPrev(List<Integer> input) {
		int prev = input.get(0);
		int ret = 0;
		for(int i = 1; i < input.size(); i++) {
			int curr = input.get(i);
			if(curr > prev) ret += 1;
			prev = input.get(i);
		}
		return ret;
	}
	public static int largerThanPrevSlidingWindow(List<Integer> input) {
		int ret = 0;
		int prev = input.get(0) + input.get(1) + input.get(2);
		for(int i = 1; i < input.size() - 2; i++){
			int curr = input.get(i) + input.get(i + 1) + input.get(i + 2);
			if(curr > prev) ret += 1;
			prev = curr;

		}
		return ret;

	}


}

