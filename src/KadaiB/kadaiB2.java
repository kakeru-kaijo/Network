package KadaiB;
//模範解答

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
public class kadaiB2 {
	public static void main(String[] args)throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		PrintWriter fileprinter1=null;
		fileprinter1=new PrintWriter(new File("KadaiB3 L32000.dat"));
		PrintWriter fileprinter2=null;
		fileprinter2=new PrintWriter(new File("KadaiB3 C32000.dat"));

		//Barabasi Albertモデル
		int N=100, m0=3, m=2;
		int[][] pairList= new int[(N-m0)*2+3][2];
		int[]degreeList=new int[N];
		int[]degreeListsum=new int[N];
		pairList[0][0]=0; pairList[0][1]=1;
		pairList[1][0]=0; pairList[1][1]=2;
		pairList[2][0]=1; pairList[2][1]=2;

		 for(int i=0; i<m0; i++) degreeList[i]=m0-1;
		 for(int i=m0; i<N; i++) degreeList[i]=0;
		/*
		 * コメント：下のように書いても良い。3としているところはm0としておくほうが値変更が楽になる。
		 * for(int i=0; i<m0; i++) degreeList[i]=m0-1;
		 * for(int i=m0; i<N; i++) degreeList[i]=0;
		 */

		int degreesum=6,v1=0,v2;
		for(int i=3;i<N;i++) {
			for(int j=0;j<i;j++) {
				degreeListsum[j]=degreeList[j];
			}
			/*
			 * これだとdegreesumを0から再計算しなくてはいけなくなる。
			 * 33行目の時点でのdegreesumを計算して、35行目から始まるfor文では
			 * 頂点の追加のたびにdegreesum+=4;としたほうが良い。
			 * （+4なのは、頂点追加のたびに辺が二本追加→接続先２頂点の次数+1＆新規頂点の次数+2からくる）
			 */
			double R1=degreesum*Math.random();
			if(R1<degreeList[0]) {

				pairList[2*i-3][0]=0;pairList[2*i-3][1]=i;
				degreeList[0]++;
				degreeList[i]++;
				v1=0;
			}
			for(int j=1;j<i;j++) {
				degreeListsum[j]+=degreeListsum[j-1];
				if((double)degreeListsum[j-1]<=R1&&R1<(double)degreeListsum[j]) {
					pairList[2*i-3][0]=j;pairList[2*i-3][1]=i;
					degreeList[j]++;
					degreeList[i]++;
					v1=j;
				}
			}degreesum+=2;
			//一本目の接続
			/*
			 * コメント：割り算は四則演算の中では時間がかかる（とされる）ので、避ける。
			 * また、57行の条件付けだと、j=i-1まではfor文が実行されるので、無駄が多い。breakでやめるかwhile文を使う。
			 * 例えば、
			 		double R1=degreesum*Math.random();
					for(int j=0;j<i;j++) {
						if (R1<degreeList[j] ){v1=j; break;}
						else R1-=degreeList[j];
					}
					
			 		double R2=degreesum*Math.random();
					for(int j=0;j<i;j++) {
						if (R2<degreeList[j] ){v2=j; break;}
						else R2-=degreeList[j];
					}
			 * としてv1とv2を決め、（v1 == v2 ならやり直すようにして）v1とv2を確定する。
			 * 確定後にpairListへの代入、degreeList更新、degreesum更新とするのが良い。
			 */
			
			degreesum=0;
			for(int j=0;j<i;j++) {
				degreesum+= degreeList[j];
				degreeListsum[j]=degreeList[j];

			}
			v2=v1;
			for(int k=0;v1==v2;k++) {
				double R2=Math.random();
				if(R2<(double)degreeList[0]/degreesum) {
					v2=0;
					if(v1!=v2) {
						pairList[2*i-2][0]=0;pairList[2*i-2][1]=i;
				    	degreeList[0]++;
				    	degreeList[i]++;
					}
				}
				for(int j=1;j<i;j++) {
					degreeListsum[j]+=degreeListsum[j-1];
					if((double)degreeListsum[j-1]/degreesum<=R2&&R2<(double)degreeListsum[j]/degreesum) {
						v2=j;
						if(v1!=v2) {
							pairList[2*i-2][0]=j;pairList[2*i-2][1]=i;
							degreeList[j]++;
							degreeList[i]++;
						}
					}
				}degreesum+=2;
				//二本目の接続
			}
		}
		for(int i=0;i<N;i++) {
			//System.out.println(degreeList[i]);
		}
		
		int M=(N-m0)*2+3;
		int[]addressList=new int[N];
		int[]neighborList=new int [2*M];
		int[]cursor=new int [N];
		addressList[0]=0;
		for(int i=1;i<N;i++) {
			addressList[i]=addressList[i-1]+degreeList[i-1];
		}
		for(int i=0;i<N;i++) cursor[i]=addressList[i];
		for(int i=0;i<M;i++) {
			neighborList[cursor[pairList[i][0]]]=pairList[i][1];
			neighborList[cursor[pairList[i][1]]]=pairList[i][0];
			cursor[pairList[i][0]]++;
			cursor[pairList[i][1]]++;
		}
		//address,neighbor
		
		int degreemax=0;
		for(int i=0;i<N;i++) {
			if(degreeList[i]>degreemax) {
				degreemax=degreeList[i];
			}
		}
		int [] degreeListN=new int[degreemax+1];
		for(int i=0;i<degreemax+1;i++) {
			degreeListN[i]=0;
		}
		for(int i=0;i<N;i++) {
			degreeListN[degreeList[i]]++;
		}
		double[]Pk=new double [degreemax+1];
		for(int i=0;i<degreemax+1;i++) {
			Pk[i]=(double)degreeListN[i]/N;
			//System.out.println(i+"\t"+Pk[i]);
		}//degreemax,Pk
		
		
		int vi,vj;
		double Length=0;
		int[] dist=new int [N];
		double[] length=new double[N];
		ArrayList<Integer>queue=new ArrayList<Integer>();
		boolean[]visitQ=new boolean[N];
		for(int i=0;i<N;i++) length[i]=0.0;
		
		for(int i=0;i<N;i++) {
			System.out.println(i);
			for(int j=0;j<N;j++) visitQ[j]=false;
			dist[i]=0;
			queue.add(i);
			visitQ[i]=true;
			for(int j=0;queue.isEmpty()==false;j++) {
				vi=queue.get(0)	;		
				for(int k=addressList[vi];k<addressList[vi]+degreeList[vi];k++) {
					vj=neighborList[k];
					if(visitQ[vj]==false) {
						queue.add(vj);
						visitQ[vj]=true;
						dist[vj]=dist[vi]+1;
						length[i]+=dist[vj];
					}
				}
				queue.remove(0);				
			}
			length[i]/=(double)(N-1);
			Length+=length[i];
			
		}
		Length/=(double)N;
		System.out.println("平均距離"+Length);
		//Length,length
		
		
		int n1,n2,clusterN=0;
		double clusteringCoefficient=0;
		double[]clusteringCoefficientList=new double[N];
		for(int n0=0;n0<N;n0++) {
			int TriangleN=0;
			for(int pos1=addressList[n0];pos1<addressList[n0]+degreeList[n0]-1;pos1++) {
				n1=neighborList[pos1];
				for(int pos2=pos1+1;pos2<addressList[n0]+degreeList[n0];pos2++) {
					n2=neighborList[pos2];
					for(int pos3=addressList[n1];pos3<addressList[n1]+degreeList[n1];pos3++) {
						if(neighborList[pos3]==n2)TriangleN++;
					}
				}
				clusteringCoefficientList[n0]=2*(double)TriangleN/(degreeList[n0]*(degreeList[n0]-1));
				clusterN++;
				clusteringCoefficient+=clusteringCoefficientList[n0];
			}
			clusteringCoefficient/=clusterN;
			
			
		}
		System.out.printf("クラスター係数=%f",clusteringCoefficient);
		//clusteringCoefficient,
		
		System.out.println(N+"\t"+Length);
		fileprinter1.println(N+"\t"+Length);
		System.out.println(N+"\t"+clusteringCoefficient);
		fileprinter2.println(N+"\t"+clusteringCoefficient);

		fileprinter1.close();
		fileprinter2.close();
	}




}
