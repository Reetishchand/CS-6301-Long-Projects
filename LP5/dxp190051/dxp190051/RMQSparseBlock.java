package dxp190051;

import java.util.Arrays;
public class RMQSparseBlock implements RMQStructure{
    int[] blockArray;
    int blockSize;
    @Override
    public void preProcess(int[] arr) {
        blockSize = (int)Math.ceil(Math.sqrt(arr.length));
        //System.out.println(blockSize);
        blockArray = new int[blockSize];
        Arrays.fill(blockArray, Integer.MAX_VALUE);

        for(int i = 0; i < arr.length; i++){
            blockArray[i/blockSize] = Math.min(arr[i], blockArray[i/blockSize]);
        }

    }

    private void printBlockArray(){
        for(int i = 0; i < blockArray.length; i++)
            System.out.print(blockArray[i] + " ");
        System.out.println();
    }

    @Override
    public int query(int[] arr, int left, int right) {
        int leftBlock = left/blockSize;
        int rightBlock = right/blockSize;
        int min = Integer.MAX_VALUE;

        if(leftBlock == rightBlock){
            for(int i = left; i <= right; i++){
                min = Math.min(min, arr[i]);
            }
            return min;
        }

        //get blocks min except left and right blocks
        for(int i = leftBlock + 1; i <= rightBlock - 1; i++){
            min = Math.min(min, blockArray[i]);
        }

        //get left block min
        for(int i = left; i < (leftBlock + 1) * blockSize; i++){
            min = Math.min(min,arr[i]);
        }

        //get right block min
        for(int i = rightBlock * blockSize; i <= right; i++){
            min = Math.min(min,arr[i]);
        }

        return min;
    }

    public double log2(double val){
        return Math.log10(val) / Math.log10(2);
    }

    public static void main(String[] args) {
        RMQSparseBlock rmqSparseBlock = new RMQSparseBlock();
        int[] arr = new int[]{24, 32, 58, 6, 94, 86, 16, 20};
        rmqSparseBlock.preProcess(arr);
        rmqSparseBlock.printBlockArray();
        System.out.println("----------");
        System.out.println((int)Math.ceil(rmqSparseBlock.log2(1)));
        System.out.println((int)Math.ceil(rmqSparseBlock.log2(2)));
        System.out.println((int)Math.ceil(rmqSparseBlock.log2(3)));
        System.out.println((int)Math.ceil(rmqSparseBlock.log2(4)));
        System.out.println((int)Math.ceil(rmqSparseBlock.log2(5)));
        System.out.println((int)Math.ceil(rmqSparseBlock.log2(6)));
        System.out.println((int)Math.ceil(rmqSparseBlock.log2(7)));
        System.out.println((int)Math.ceil(rmqSparseBlock.log2(8)));
        System.out.println("----------");
        System.out.println(rmqSparseBlock.query(arr,1,5));
        System.out.println(rmqSparseBlock.query(arr,2,7));
        System.out.println(rmqSparseBlock.query(arr,3,4));
        System.out.println(rmqSparseBlock.query(arr,0,7));
        System.out.println(rmqSparseBlock.query(arr,6,7));
        System.out.println(rmqSparseBlock.query(arr,5,7));
        System.out.println(rmqSparseBlock.query(arr,1,4));
        System.out.println(rmqSparseBlock.query(arr,0,5));
        System.out.println(rmqSparseBlock.query(arr,0,2));
        System.out.println(rmqSparseBlock.query(arr,0,1));
        System.out.println(rmqSparseBlock.query(arr,4,4));
    }
}

/*
public class RMQBlock implements RMQStructure{
    int[] arrBlock;
    int size;
    @Override
    public void preProcess(int[] arr) {
        size = (int)Math.ceil(Math.sqrt(arr.length));
        arrBlock = new int[size];
        Arrays.fill(arrBlock, Integer.MAX_VALUE);

        int i = 0;
        while (i < arr.length) {
            int n = i/size;
            arrBlock[n] = Math.min(arr[i], arrBlock[n]);
            i++;
        }
    }

    private void printBlockArray(){
        for(int i = 0; i < arrBlock.length; i++) {
            System.out.print(arrBlock[i] + " ");
        }
    }

    @Override
    public int query(int[] arr, int left, int right) {
        int blkLeft = left/size;
        int blkRight = right/size;
        int min = Integer.MAX_VALUE;

        if(blkLeft == blkRight){
            int i = 0;
            while (i <= right) {
                min = Math.min(min, arr[i]);
                i++;
            }
            return min;
        }

        //get blocks min except left and right blocks
        int i = blkLeft + 1;
        while (i <= blkRight - 1) {
            min = Math.min(min, arrBlock[i]);
            i++;
        }

        //get left block min
        i = left;
        while (i <= (blkLeft + 1) * size) {
            min = Math.min(min,arr[i]);
            i++;
        }

        //get right block min
        i = blkRight * size;
        while (i <= right) {
            min = Math.min(min,arr[i]);
            i++;
        }

        return min;
    }

    public double log2(double val){
        return Math.log10(val) / Math.log10(2);
    }

    public static void main(String[] args) {
        RMQBlock rmqBlock = new RMQBlock();
        int[] arr = new int[]{24, 32, 58, 6, 94, 86, 16, 20};
//        int[] arr = new int[]{44, 42, 57, 8, 91, 83, 18, 27};
        rmqBlock.preProcess(arr);
        rmqBlock.printBlockArray();
        System.out.println("----------");
//        System.out.println((int)Math.ceil(rmqBlock.log2(1)));
//        System.out.println((int)Math.ceil(rmqBlock.log2(2)));
//        System.out.println((int)Math.ceil(rmqBlock.log2(3)));
//        System.out.println((int)Math.ceil(rmqBlock.log2(4)));
//        System.out.println((int)Math.ceil(rmqBlock.log2(5)));
//        System.out.println((int)Math.ceil(rmqBlock.log2(6)));
//        System.out.println((int)Math.ceil(rmqBlock.log2(7)));
//        System.out.println((int)Math.ceil(rmqBlock.log2(8)));
        System.out.println("----------");
//        System.out.println(rmqBlock.query(arr,1,5));
//        System.out.println(rmqBlock.query(arr,2,7));
//        System.out.println(rmqBlock.query(arr,3,4));
//        System.out.println(rmqBlock.query(arr,0,7));
        System.out.println(rmqBlock.query(arr,6,7));
        System.out.println(rmqBlock.query(arr,5,7));
        System.out.println(rmqBlock.query(arr,1,4));
        System.out.println(rmqBlock.query(arr,0,5));
        System.out.println(rmqBlock.query(arr,0,2));
        System.out.println(rmqBlock.query(arr,0,1));
        System.out.println(rmqBlock.query(arr,4,4));
    }
}
*/