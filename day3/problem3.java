//day3 AOC
import java.io.*;
import java.util.*;
import java.lang.*;

public class problem3 {
	public static void main(String[] args) {
		String currentDir = System.getProperty("user.dir");
		File file = new File(currentDir + "/input3.txt");
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(file));
			List<String> input = new ArrayList();
			String line;
			while((line = br.readLine()) != null) {
				input.add(line);
			}
			int gammaMultEpsilon = gammaTimesEpsilon(input);
			System.out.println("The power consumption of the submarine is: " + gammaMultEpsilon);
			int lifeSupportRating = oxygenCO2Rating(input);
			System.out.println("The life support rating of the submarin is: " + lifeSupportRating);

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

	public static int gammaTimesEpsilon(List<String> in) {
		String gamma = "";
		String epsilon = "";
		for(int i = 0; i < in.get(0).length(); i++){
			int num1 = 0;
			int num0 = 0;
			for(String s : in) {
				if(s.charAt(i) == '0') num0+=1;
				else num1+=1;

			}
			gamma += num0 > num1 ? 0 : 1;
			epsilon += num0 > num1 ? 1 : 0;
		}
		int g = 0;
		int e = 0;
		for(int i = 0; i < gamma.length(); i++) {
			int exp = gamma.length() - 1 - i;
			g += gamma.charAt(i) == '1' ? Math.pow(2,exp) : 0;
			e += epsilon.charAt(i) == '1' ? Math.pow(2,exp) : 0;
		}
		return g*e;
	}

	public static int oxygenCO2Rating(List<String> in) {
		String oxy = recur(in, 0, true).get(0);
		String carb = recur(in,0,false).get(0);
		int one = 0;
		int two = 0;
		for(int i = 0; i < carb.length(); i++) {
			int exp = carb.length() - 1 - i;
			one += carb.charAt(i) == '1' ? Math.pow(2,exp) : 0;
			two += oxy.charAt(i) == '1' ? Math.pow(2,exp) : 0;
		}
		return one*two;

		
	}
	public static List<String> recur(List<String> list, int id, boolean isOxygen) {
		if(list.size() == 1) return list;
		List<String> ones = new ArrayList();
		List<String> zeros = new ArrayList();
		for(String s : list) {
			if(s.charAt(id) == '1'){
				ones.add(s);
			}
			else{
				zeros.add(s);

			}		
		}
		if(isOxygen) return ones.size() >= zeros.size() ? recur(ones, id+1, isOxygen) : recur(zeros, id+1, isOxygen);
		else return zeros.size() <= ones.size() ? recur(zeros, id+1, isOxygen) : recur(ones, id+1, isOxygen);
	}
}



