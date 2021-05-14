package dxp190051;

public class RMQHybridOne implements RMQStructure{
    RMQSparseBlock blk;
    RMQSparseTable rmwSparseTbl;
    @Override
    public void preProcess(int[] arr) {
        blk = new RMQSparseBlock();
        blk.preProcess(arr);
        int[] blockMin = blk.blockArray;
        rmwSparseTbl = new RMQSparseTable();
        rmwSparseTbl.preProcess(blockMin);
    }

    @Override
    public int query(int[] arr, int left, int right) {
//        int left = i;
//        int right = j;
        int leftBlock = left/blk.blockSize;
        int rightBlock = right/blk.blockSize;
        int min = Integer.MAX_VALUE;

        if(leftBlock == rightBlock){
            for(int i = left; i <= right; i++){
                min = Math.min(min, arr[i]);
            }
            return min;
        }

        //get blocks min except left and right blocks
//        for(int i = leftBlock + 1; i <= rightBlock - 1; i++){
//            min = Math.min(min, blk.blockArray[i]);
//        }
        min = rmwSparseTbl.query(blk.blockArray, leftBlock+1, rightBlock);

        //get left block min
        for(int i = left; i < (leftBlock + 1) * blk.blockSize; i++){
            min = Math.min(min,arr[i]);
        }

        //get right block min
        for(int i = rightBlock * blk.blockSize; i <= right; i++){
            min = Math.min(min,arr[i]);
        }

        return min;

        //        int k = (int) Math.floor(log2(j - i + 1));
//        int left = sparseTable[i][k];
//        int right = sparseTable[j - (int) Math.pow(2, k) + 1][k];
//        return Math.min(left, right);
//        return 0;
    }
}
