/**
 LP5 - RMQHybridOne.java
 @author Umar Khalid (uxk150630)
 @author Dhara Patel (dxp190051)
 @author Reetish Chand (rxg190006)
 @author Rohan Vannala (rxv190003)

 Implements Hybrid One Approach for RMQ
 */

package dxp190051;

import java.util.ArrayList;
import java.util.Arrays;

public class RMQHybridOne implements RMQStructure{
    RMQSparseTable rmwSparseTbl;
    int[] blkArray;
    int blkSize;
    ArrayList<RMQSparseTable> sptArray;

    /**
     * Method: preProcess()
     * Pre process for Hybrid One Approac
     * @param arr: input arr
     * @return
     */
    @Override
    public void preProcess(int[] arr) {
        blkSize = (int)Math.ceil(Math.sqrt(arr.length));
        blkArray = new int[blkSize];
        Arrays.fill(blkArray, Integer.MAX_VALUE);

        for(int i = 0; i < arr.length; i++){
            blkArray[i/ blkSize] = Math.min(arr[i], blkArray[i/ blkSize]);
        }

        sptArray = new ArrayList<RMQSparseTable>();

        for(int i = 0; i < arr.length/ blkSize + 1; i++) {
            RMQSparseTable spt = new RMQSparseTable();

            int[] part = Arrays.copyOfRange(arr, i * blkSize, (i+1) * blkSize);
            spt.preProcess(part);
            sptArray.add(spt);
        }

        int[] blockMin = blkArray;
        rmwSparseTbl = new RMQSparseTable();
        rmwSparseTbl.preProcess(blockMin);
    }

    /**
     * Method: query()
     * Query for Hybrid One Approach
     * @param arr: input arr
     * @param left: start index for finding min
     * @param right: end index for finding min
     * @return
     */
    @Override
    public int query(int[] arr, int left, int right) {
        int leftBlock = left/ blkSize;
        int rightBlock = right/ blkSize;
        int min = Integer.MAX_VALUE;

        if(leftBlock == rightBlock){
            for(int i = left; i <= right; i++){
                min = Math.min(min, arr[i]);
            }
            return min;
        }

        //get min for other blocks
        min = rmwSparseTbl.query(blkArray, leftBlock+1, rightBlock-1);

        //get min for left block
        RMQSparseTable sptLeft = sptArray.get(leftBlock);
        int minLeft =  sptLeft.query(arr, left % blkSize, blkSize -1);
        min = Math.min(minLeft, min);

        //get min for right block
        RMQSparseTable sptRight = sptArray.get(rightBlock);
        int minRight =  sptRight.query(arr, 0, right % blkSize);
        min = Math.min(minRight, min);

        return min;
    }
}
