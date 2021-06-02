/**
 LP3 - RMQSparseTable.java
 @author Umar Khalid (uxk150630)
 @author Dhara Patel (dxp190051)
 @author Reetish Chand (rxg190006)
 @author Rohan Vannala (rxv190003)

 Implements Sparse table for hybrid one RMQ
 */
package dxp190051;

public class RMQSparseTable implements RMQStructure{
    int[][] sparseTbl;
    /**
     * Method: preProcess()
     * Pre process for Hybrid One Sparse table
     * @param arr: input arr
     * @return
     */
    @Override
    public void preProcess(int[] arr) {
        int size = arr.length;
        int n = (int) Math.ceil(log2(size)) + 1;
        sparseTbl = new int[size][n];

        for (int i = 0; i < size; i++) {
            sparseTbl[i][0] = arr[i];
        }

        for (int j = 1; Math.pow(2, j) <= size;  j++){
            for (int i = 0; i + Math.pow(2, j) - 1 < size; i++){
                Integer min = Math.min(sparseTbl[i + (int)Math.pow(2, j-1)][j - 1],sparseTbl[i][j-1]);
                sparseTbl[i][j] = min;
            }
        }
    }

    /**
     * Method: printSparseTable()
     * Print table for Fischer Heun Sparse Table
     * @return
     */
    private void printSparseTable(){
        for (int i = 0; i < sparseTbl.length;  i++){
            for (int j = 0; j < sparseTbl[0].length; j++){
                System.out.print(sparseTbl[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int query(int[] arr, int i, int j) {
        int rnd = (int) Math.floor(log2(j - i + 1));
        int next = sparseTbl[j - (int) Math.pow(2, rnd) + 1][rnd];
        int prev = sparseTbl[i][rnd];
        return Math.min(prev, next);
    }

    public double log2(double val){
        return Math.log10(val) / Math.log10(2);
    }

    public static void main(String[] args) {
        RMQSparseTable sparseTableRMQ = new RMQSparseTable();
        int[] arr = new int[]{24, 32, 58, 6, 94, 86, 16, 20, 39, 2, 46, 78};
        sparseTableRMQ.preProcess(arr);
        sparseTableRMQ.printSparseTable();
        System.out.println("----------");
        System.out.println((int)Math.ceil(sparseTableRMQ.log2(1)));
        System.out.println((int)Math.ceil(sparseTableRMQ.log2(2)));
        System.out.println((int)Math.ceil(sparseTableRMQ.log2(3)));
        System.out.println((int)Math.ceil(sparseTableRMQ.log2(4)));
        System.out.println((int)Math.ceil(sparseTableRMQ.log2(5)));
        System.out.println((int)Math.ceil(sparseTableRMQ.log2(6)));
        System.out.println((int)Math.ceil(sparseTableRMQ.log2(7)));
        System.out.println((int)Math.ceil(sparseTableRMQ.log2(8)));
        System.out.println("----------");
        System.out.println(sparseTableRMQ.query(arr,1,6));
        System.out.println(sparseTableRMQ.query(arr,2,7));
        System.out.println(sparseTableRMQ.query(arr,3,4));
        System.out.println(sparseTableRMQ.query(arr,0,7));
        System.out.println(sparseTableRMQ.query(arr,6,7));
        System.out.println(sparseTableRMQ.query(arr,5,7));
        System.out.println(sparseTableRMQ.query(arr,1,4));
        System.out.println(sparseTableRMQ.query(arr,0,5));
        System.out.println(sparseTableRMQ.query(arr,0,2));
        System.out.println(sparseTableRMQ.query(arr,0,1));
        System.out.println(sparseTableRMQ.query(arr,4,4));
    }
}
