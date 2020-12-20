package algorithm;



/**
 * 
 * @author yuezh2
 * @date 2020/12/03 10:56
 *
 */
public class MergeSortDemo {

	public static void main(String[] args) {
		int[] arr = {11,44,23,67,88,65,34,48,9,12};
		int[] tmp = new int[arr.length];
		mergeSort(arr, 0, arr.length-1, tmp);
		for(int i = 0 ; i < tmp.length ; i++) {
			System.out.print(tmp[i]+"   ");
		}
	}
	
	public static void merge(int[] arr , int low , int mid , int high , int[] tmp) {
		int i=0;
		int j = low , k = mid+1;//左边序列和右边序列索引
		
		while(j<=mid && k<=high) {
			if(arr[j]<arr[k]) {
				tmp[i++] = arr[j++];
			}else {
				tmp[i++]=arr[k++];
			}
		}
		
		//如果左边序列还有剩余,全部拷贝进tmp[]中
		while(j<=mid) {
			tmp[i++]=arr[j++];
		}
		
		while(k<=high) {
			tmp[i++]=arr[k++];
		}
		
		for(int t=0;t<i;t++) {
			arr[low+t]=tmp[t];
		}
		
	}
	
	
	public static void mergeSort(int[] arr , int low , int high , int[] tmp) {
		if(low<high) {
		
			int mid = (low+high)/2;
			mergeSort(arr, low, mid, tmp);//对左边进行归并排序
			mergeSort(arr, mid+1, high, tmp);//对右边进行归并排序
			merge(arr, low, mid, high , tmp);//合并两个有序排序
		}
	}
	
	
}
