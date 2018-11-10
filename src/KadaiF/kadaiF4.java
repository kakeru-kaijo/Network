package KadaiF;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class kadaiF4 {
	public static void main(String[] args)throws Exception{
		int n=10000,m=15935,step=10000000 ;
		int [][]pairList=new int[m][2];
		int[]degreeList=new int [n];
		int[]neighborList=new int[2*m];
		int[]addressList=new int[n];
		int[]cursor=new int[n];
		ArrayList<Integer>walkList=new ArrayList<Integer>();

		Scanner fileScanner=null;
		fileScanner=new Scanner(new File("scalefreenetworkN10000(Mac).txt"));
		//file scan

		PrintWriter fileprinter1=null;
		fileprinter1=new PrintWriter(new File("KadaiF4 Nk.dat"));
		PrintWriter fileprinter2=null;
		fileprinter2=new PrintWriter(new File("KadaiF4 Nkave.dat"));

		for(int i=0;i<n;i++)degreeList[i]=0;
		//degree=0

		fileScanner.nextLine();
		while(fileScanner.hasNextInt()){
			for(int i=0;i<m;i++){
				for(int j=0;j<2;j++){
					pairList[i][j]=fileScanner.nextInt();
					for(int k=0;k<n;k++){
						if(pairList[i][j]==k){
							degreeList[k]++;
						}
					}
				}
				System.out.println(i);
			}
		}
		fileScanner.close();
		//pair,degree

		addressList[0]=0;
		for(int i=1;i<n;i++){
			addressList[i]=addressList[i-1]+degreeList[i-1];
		}
		//address

		for(int i=0;i<n;i++){
			cursor[i]=addressList[i];
		}
		for(int i=0;i<m;i++){
			neighborList[cursor[pairList[i][0]]]=pairList[i][1];
			cursor[pairList[i][0]]++;
			neighborList[cursor[pairList[i][1]]]=pairList[i][0];
			cursor[pairList[i][1]]++;
		}
		//cursor,neighbor

		int degreemax=0;
		for(int i=0;i<n;i++){
			if(degreeList[i]>degreemax){
				degreemax=degreeList[i];
			}
		}
		//degreemax

		int[]degreeListN=new int [degreemax+1];
		for(int i=0;i<degreemax+1;i++){
			degreeListN[i]=0;
		}
		for(int i=0;i<n;i++){
			degreeListN[degreeList[i]]+=1;
		}

		double[]Pk=new double[degreemax+1];
		for(int i=0;i<degreemax+1;i++){
			Pk[i]=(double)degreeListN[i]/n;
			System.out.println(i+"\t"+Pk[i]);
		}//Pk
		//F-(2)




		int b,n1;
		int[]RandomwalkN=new int[n];//訪問回数
		for(int i=0;i<n;i++){
			RandomwalkN[i]=0;//初期化
		}
		walkList.clear();
		int vs=(int)(n*Math.random());//出発点
		walkList.add(vs);
		RandomwalkN[vs]++;
		for(int i=0;i<step;i++){
			b=walkList.get(i);
			n1=(int)(degreeList[b]*Math.random());
			walkList.add(neighborList[addressList[b]+n1]);
			RandomwalkN[neighborList[addressList[b]+n1]]++;
		}
		for(int i=0;i<n;i++){
			System.out.println(i+"\t"+RandomwalkN[i]);
		}
		//ランダムウォーク回数
		//F-(3)



		System.out.println();
		for(int i=0;i<n;i++){
			System.out.println(degreeList[i]+"\t"+RandomwalkN[i]);
			fileprinter1.println(degreeList[i]+"\t"+RandomwalkN[i]);
		}fileprinter1.close();
		//次数とウォーカーの訪問回数の散布図

		int[]Randomwalkave=new int [degreemax+1];
		int[]RandomwalkNk=new int [degreemax+1];
		for(int i=0;i<degreemax+1;i++){
			Randomwalkave[i]=0;
			RandomwalkNk[i]=0;
		}
		for(int i=0;i<n;i++){
			Randomwalkave[degreeList[i]]+=RandomwalkN[i];
			RandomwalkNk[degreeList[i]]++;
		}
		System.out.println();
		for(int i=0;i<degreemax+1;i++){
			if(RandomwalkNk[i]!=0){
				Randomwalkave[i]/=RandomwalkNk[i];
				System.out.println(i+"\t"+Randomwalkave[i]);
				fileprinter2.println(i+"\t"+Randomwalkave[i]);
			}
		}fileprinter2.close();
		//次数とウォーカーの訪問回数の平均の散布図
		//F-(4)

	}

}
