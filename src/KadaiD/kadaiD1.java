package KadaiD;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class kadaiD1 {

	public static void main(String[] args) throws Exception{
		//PrintWriter fileprinter=null;
		//fileprinter=new PrintWriter("");

		Scanner fileScanner=null;
		fileScanner=new Scanner(new File("Beauty and the Beast.txt"));
		String lineBuffer;
		lineBuffer=fileScanner.nextLine();
		System.out.println(lineBuffer);
		fileScanner.close();
		//Scanner

		int Nlines=0;
		int Nword=0;
		int a;
		ArrayList<String>queueword=new ArrayList<String>();
		ArrayList<Integer>queueNword=new ArrayList<Integer>();
		queueword.add("");
		queueNword.add(0);
		
		String[]token=lineBuffer.split("\\.");
		for(String lines : token) {
			System.out.println(lines);
			String[]token2=lines.split(" ");
			for(String word1 : token2) {
				//System.out.println(word1);
				a=0;
				for(int i=0;i<=Nword;i++) {
					if(word1.equals(queueword.get(i))) {
						a=queueNword.get(i);
						a++;
						queueNword.set(i, a);
						//System.out.println();
						break;
					}
				}
				if(a==0) {
					queueword.add(word1);
					queueNword.add(1);
					Nword++;
				}
			}
		}//wordsplit  Nword
		int N=Nword;
		
		
		queueword.remove(0);
		queueNword.remove(0);
		for(int i=0;i<Nword;i++) {
			System.out.println(queueword.get(i)+"\t"+queueNword.get(i));
		}//単語に対する出現回数
		
		
		Collections.sort(queueNword);
		int queueN=1;
		int Ni=0;
		for(int i=1;i<=Nword;i++) {
			if(queueN<queueNword.get(i-1)) {
				System.out.println(queueNword.get(i-2)+"\t"+(i-1-Ni));
				queueN=queueNword.get(i-1);
				Ni=i-1;
			}
		}System.out.println(queueNword.get(Nword-1) +"\t"+(Nword-Ni));
		//N回出現した単語数
	}

}
