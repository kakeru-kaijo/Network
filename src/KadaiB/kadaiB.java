package KadaiB;//ネットワーク講義

import java.util.ArrayList;

public class kadaiB {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ


		//Barabasi Albertモデル
		int N=1000, m0=3,m=2;
		int[][] pairList= new int[(N-m0)*2+3][2];
		int[]degreeList=new int[N];
		int[]degreeListsum=new int[N];
		pairList[0][0]=0;pairList[0][1]=1;
		pairList[1][0]=0;pairList[1][1]=2;
		pairList[2][0]=1;pairList[2][1]=2;
		degreeList[0]=2;
		degreeList[1]=2;
		degreeList[2]=2;
		for(int i=3;i<N;i++) {

			degreeList[i]=0;
		}



		int degreesum,v1=0,v2;
		for(int i=3;i<N;i++) {
			degreesum=0;
			for(int j=0;j<i;j++) {
				degreesum+= degreeList[j];
				degreeListsum[j]=degreeList[j];

			}
			double R1=Math.random();
			if(R1<(double)degreeList[0]/degreesum) {

				pairList[2*i-3][0]=0;pairList[2*i-3][1]=i;
				degreeList[0]++;
				degreeList[i]++;
				v1=0;
			}
			for(int j=1;j<i;j++) {
				degreeListsum[j]+=degreeListsum[j-1];
				if((double)degreeListsum[j-1]/degreesum<=R1&&R1<(double)degreeListsum[j]/degreesum) {
					pairList[2*i-3][0]=j;pairList[2*i-3][1]=i;
					degreeList[j]++;
					degreeList[i]++;
					v1=j;
				}

			}//一本目の接続

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
				}//二本目の接続
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
			//System.out.println(Pk[i]);
		}//degreemax,Pk


		int vi,vj;
		double Length=0;
		int[] dist=new int [N];
		double[] length=new double[N];
		ArrayList<Integer>queue=new ArrayList<Integer>();
		boolean[]visitQ=new boolean[N];
		for(int i=0;i<N;i++) length[i]=0.0;

		for(int i=0;i<N;i++) {
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
		System.out.println(Length);
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
		System.out.println(clusteringCoefficient);
		//clusteringCoefficient,

		System.out.println(N+"\t"+Length);
		System.out.println(N+"\t"+clusteringCoefficient);
	}

}