//day2 problem 2 of AOC
import java.util.*;
import java.io.*;

public class problem2 {
	public static void main(String[] args) {
			String currentDir = System.getProperty("user.dir");
			File file = new File(currentDir + "/input2.txt");
			BufferedReader br = null;
			try{
				br = new BufferedReader(new FileReader(file));
				//should proably be using HashSet for quicker lookup but oof
				List<Integer> magnitude = new ArrayList<Integer>();
				List<String> direction = new ArrayList<String>();
				String line;
				while((line = br.readLine()) != null) {
					String[] in = line.split(" ");
					direction.add(in[0].toLowerCase());
					magnitude.add(Integer.parseInt(in[1]));
				}
				int newPos = calculateNewPos(direction, magnitude);
				System.out.println("The new pos of the submarine is: " + newPos);
				int newPosWithAim = calcNewPosWithAim(direction, magnitude);
				System.out.println("The new pos with aim considered in the calculation is: " + newPosWithAim);

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

	public static int calculateNewPos(List<String> direction, List<Integer> magnitude) {
		int vertical = 0, horizontal = 0;
		for(int i = 0; i < direction.size(); i++) {
			String dir = direction.get(i);
			int mag = magnitude.get(i);
			switch (dir) {
				case "up":
					vertical -= mag;
					break;
				case "down":
					vertical += mag;
					break;
				case "forward":
					horizontal += mag;
					break;

			}
		}
		return vertical * horizontal;

	}
	public static int calcNewPosWithAim(List<String> direction, List<Integer> magnitude) { 
		int horizontal = 0, vertical = 0, aim = 0;
		for(int i = 0; i < direction.size(); i++) {
			String dir = direction.get(i);
			int mag = magnitude.get(i);
			switch(dir) {
				case "up":
					aim -= mag;
					break;
				case "down":
					aim += mag;
					break;
				case "forward":
					horizontal += mag;
					vertical += aim * mag;

			}
	
		}
		return vertical * horizontal;
		
	}



}

