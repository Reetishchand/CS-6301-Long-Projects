package dxp190051;

import java.lang.Math;

public class RMQNoPP implements RMQStructure {
	
	boolean DEBUG = false;
	
    public void preProcess(int[] arr) {
		return;
	}	
	public int query(int[] arr, int i, int j){
		int min = arr[i];
		for (int k = i; k <= j; k++)
			if (min > arr[k]) min = arr[k];
		if (DEBUG)
			System.out.println("min = " + min + " range " + i + " " + j);
		return min;
	}
}
    
 

