/**
 LP5 - RMQFischerHeun.java
 @author Umar Khalid (uxk150630)
 @author Dhara Patel (dxp190051)
 @author Reetish Chand (rxg190006)
 @author Rohan Vannala (rxv190003)

 Implements Fischer-Heun Algorithm for RMQ
 */

package dxp190051;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class RMQFischerHeun implements RMQStructure {
    RMQIndexSparseTable rmwSparseTbl;
    int[] blkArray;
    int blkSize;

    int[] cartesians;
    int numBlocks;
    HashMap<Integer, RMQIndexSparseTable> cartesianRMQs;

    /**
     * Method: preProcess()
     * Pre process for Fischer-Heun
     * @param arr: input arr
     * @return
     */
    @Override
    public void preProcess(int[] arr) {
        blkSize = (int)Math.ceil(Math.sqrt(arr.length));
        blkArray = new int[blkSize];
        numBlocks = arr.length/ blkSize + 1;
        Arrays.fill(blkArray, Integer.MAX_VALUE);

        for(int i = 0; i < arr.length; i++){
            blkArray[i/ blkSize] = Math.min(arr[i], blkArray[i/ blkSize]);
        }

        cartesians = new int[numBlocks];
        cartesianRMQs = new HashMap<Integer, RMQIndexSparseTable>();

        for(int i = 0; i < numBlocks ; i++) {

            int last = Math.min((i+1) * blkSize, arr.length);
            int c = CartesianNumber(arr, i * blkSize, last);
            cartesians[i] = c;

            if( cartesianRMQs.get(c) == null) {
                RMQIndexSparseTable spt = new RMQIndexSparseTable();
                int[] part = Arrays.copyOfRange(arr, i * blkSize, (i+1) * blkSize);
                spt.preProcess(part);
                cartesianRMQs.put(c, spt);
            }
        }

        int[] blockMin = blkArray;
        rmwSparseTbl = new RMQIndexSparseTable();
        rmwSparseTbl.preProcess(blockMin);
    }

    /**
     * Method: query()
     * Query for Fischer-Heun
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

        //get blocks min except left and right blocks

        if (rightBlock - leftBlock > 1) {
            min = rmwSparseTbl.query(blkArray, leftBlock + 1, rightBlock - 1);
        }

        //get left block min
        int leftC = cartesians[leftBlock];
        RMQIndexSparseTable sptLeft = cartesianRMQs.get(leftC);

        int[] leftpart = Arrays.copyOfRange(arr, leftBlock * blkSize, (leftBlock+1) * blkSize);

        int minLeft =  sptLeft.query(leftpart, left % blkSize, blkSize -1);
        min = Math.min(minLeft, min);


        //get right block min
        int rightC = cartesians[rightBlock];
        RMQIndexSparseTable sptRight = cartesianRMQs.get(rightC);

        int[] rightpart = Arrays.copyOfRange(arr, rightBlock * blkSize, (rightBlock+1) * blkSize);

        int minRight =  sptRight.query(rightpart, 0, right % blkSize);
        min = Math.min(minRight, min);
        return min;
    }

    /**
     * Method: addToRight()
     * Add 0 to Right
     * @param x
     * @return
     */
    private int addToRight(int x) {
        return 2*x;
    }

    /**
     * Method: addToLeft()
     * Add 1 to Left
     * @param x
     * @return
     */
    private int addToLeft(int x) {
        return 2*x + 1;
    }

    /**
     * Method: CartesianNumber()
     * @param elements: array
     * @param i: start index
     * @param j: end index
     * @return
     */
    private int CartesianNumber(int[] elements, int i, int j) {
        int crtNum = 0;
        crtNum = addToLeft(crtNum);

        Stack<Integer> stack = new Stack<Integer>();
        stack.push(i);

        for (int k = i+1; k < j; k++) {
            while ( (!stack.isEmpty()) && (elements[k] < elements[stack.peek()]) ) {
                crtNum = addToRight(crtNum);
                stack.pop();
            }
            crtNum = addToLeft(crtNum);
            stack.push(k);
        }

        for(int k = 0; k < stack.capacity(); k++) {
            if (!stack.isEmpty()) {
                stack.pop();
                crtNum = addToRight(crtNum);
            }
        }
        return crtNum;
    }
}

