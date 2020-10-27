package algorithm;



/**
 * 
 * @author yuezh2
 *
 * @date 2020年10月27日 下午5:36:27  
 *
 */
public class QuickSort {
	
	
	private static void quicksort(int[] arr , int leftIndex , int rightIndex) {
		if(leftIndex >= rightIndex) {
			return;
		}
		
		int left = leftIndex;
		int right = rightIndex;
		
		int key = arr[left];
		
		while(left<right) {
			while(right>left && arr[right] >= key) {
				right--;
			}
			
			arr[left] = arr[right];
			
			while(left<right && arr[left]<=key) {
				left++;
			}
			
			arr[right] = arr[left];
		}
		
		arr[left] = key;
		quicksort(arr, leftIndex, left-1);
		quicksort(arr, right+1, rightIndex);
	}
	
	
	
	
	public static void main(String[] args) {
		int[] arr = {5,1,7,3,1,6,9,4};
		
		quicksort(arr, 0, arr.length-1);
		
		for (int i : arr) {
            System.out.print(i + "\t");
        }
	}
	

}
