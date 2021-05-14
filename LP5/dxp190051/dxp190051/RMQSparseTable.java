package dxp190051;

public class RMQSparseTable implements RMQStructure{
    int[][] sparseTable;
    @Override
    public void preProcess(int[] arr) {
        int len = arr.length;
        int k = (int) Math.ceil(log2(len)) + 1;
        sparseTable = new int[len][k];

        for (int i = 0; i < len; i++){
            sparseTable[i][0] = arr[i];
        }

        for (int j = 1; Math.pow(2, j) <= len;  j++){
            for (int i = 0; i + Math.pow(2, j) - 1 < len; i++){
                sparseTable[i][j] = Math.min(sparseTable[i][j-1],
                        sparseTable[i + (int)Math.pow(2, j-1)][j - 1]);
            }
        }
    }

    private void printSparseTable(){
        for (int i = 0; i < sparseTable.length;  i++){
            for (int j = 0; j < sparseTable[0].length; j++){
                System.out.print(sparseTable[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public int query(int[] arr, int i, int j) {
        int k = (int) Math.floor(log2(j - i + 1));
        int left = sparseTable[i][k];
        int right = sparseTable[j - (int) Math.pow(2, k) + 1][k];
        return Math.min(left, right);
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
        System.out.println(sparseTableRMQ.query(arr,1,11));
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


/*public class RMQSparseTable implements RMQStructure{
    int[][] sparseTbl;
    @Override
    public void preProcess(int[] arr) {
        int size = arr.length;
        int n = (int) Math.ceil(log2(size)) + 1;
        sparseTbl = new int[size][n];

        for (int i = 0; i < size; i++) {
            sparseTbl[i][0] = arr[i];
        }

        int j = 0,k = 0;
        while(Math.pow(2, j) <= size) {
            while(k < (Math.pow(2, j) - 1)) {
                Integer min = Math.min(sparseTbl[k][j-1],sparseTbl[k + (int)Math.pow(2, j-1)][j - 1]);
                sparseTbl[k][j] = min;
                k++;
            }
            j++;
        }
    }

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
//        int[] arr = new int[]{34, 32, 58, 6, 94, 86, 16, 20};
        int[] arr = new int[]{44, 42, 57, 8, 91, 83, 18, 27};
        sparseTableRMQ.preProcess(arr);
        sparseTableRMQ.printSparseTable();
    }
}
*/