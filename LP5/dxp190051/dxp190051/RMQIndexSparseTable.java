/**
 LP5 - RMQIndexSparseTable.java
 @author Umar Khalid (uxk150630)
 @author Dhara Patel (dxp190051)
 @author Reetish Chand (rxg190006)
 @author Rohan Vannala (rxv190003)

 Implements Sparse table for Fischer-Heun algorithm RMQ
 */
package dxp190051;

public class RMQIndexSparseTable implements RMQStructure{

    int[][] spt;

    /**
     * Method: preProcess()
     * Pre process for Fischer Heun Sparse Table
     * @param arr: input arr
     * @return
     */
    @Override
    public void preProcess(int[] arr) {
        int len = arr.length;
        int k = (int) Math.ceil(log2(len)) + 1;
        spt = new int[len][k];

        for (int i = 0; i < len; i++){
            spt[i][0] = i;
        }

        for (int j = 1; Math.pow(2, j) <= len;  j++){
            for (int i = 0; i + Math.pow(2, j) - 1 < len; i++){
                int ind1 = spt[i][j-1];
                int ind2 = spt[i + (int)Math.pow(2, j-1)][j - 1];
                if(arr[ind1] < arr[ind2]) {
                    spt[i][j] = ind1;
                } else {
                    spt[i][j] = ind2;
                }
            }
        }
    }

    /**
     * Method: printSparseTable()
     * Print table for Fischer Heun Sparse Table
     * @return
     */
    private void printSparseTable(){
        for (int i = 0; i < spt.length; i++){
            for (int j = 0; j < spt[0].length; j++){
                System.out.print(spt[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int query(int[] arr, int i, int j) {
        int k = (int) Math.floor(log2(j - i + 1));
        int left = spt[i][k];
        int right = spt[j - (int) Math.pow(2, k) + 1][k];
        return Math.min(arr[left], arr[right]);
    }

    public double log2(double val){
        return Math.log10(val) / Math.log10(2);
    }

    public static void main(String[] args) {
        RMQIndexSparseTable sparseTableRMQ = new RMQIndexSparseTable();
        int[] arr = new int[]{24, 32, 58, 6, 94, 86, 16, 20};
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
        System.out.println(sparseTableRMQ.query(arr,1,7));
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
