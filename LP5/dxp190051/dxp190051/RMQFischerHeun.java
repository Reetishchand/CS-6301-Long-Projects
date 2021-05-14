//package dxp190051;
//
//public class RMQFischerHeun implements RMQStructure{
//    private int[] top;
//    private int[] bottom;
//    private float[] elements;
//    private int[] logs;  // compute once for time efficiency
//    private ArrayList<Integer> powers;  // compute once for time efficiency
//    private int[][] sparseTable;
//    private int n;  // size of array
//    private int b;  // size of blocks
//    private int blocks;
//    private RMQStructure[] cartesianRMQs;
//    private int[] cartesians;
//
//
//    @Override
//    public void preProcess(int[] arr) {
//        RMQBlock blk = new RMQBlock();
//        blk.preProcess(arr);
//        int[] blockMin = blk.arrBlock;
//        RMQSparseTable rmwSparseTbl = new RMQSparseTable();
//        rmwSparseTbl.preProcess(blockMin);
//
//        n = elems.length;
//        if (n == 0) return;
//        // Copy elems to permanent storage for MinIndex function in rmq
//        elements = new float[n];
//        System.arraycopy(elems, 0, elements, 0, n);
//        b = (int)(Math.log(n) / (4*Math.log(2)));
//        // If b = 0, just linear pass through it
//        if (b < 1) return;
//        blocks = (int) Math.ceil((double)(n)/b);
//
//        // initialize arrays and fill in bottom one
//        InitializeTopAndBottom();
//        CalculateLogs();
//        CalculatePowers();
//        BuildSparseTable();
//        InitializeCartesians();
//
//    }
//
//    @Override
//    public int query(int[] arr, int i, int j) {
//        return 0;
//    }
//}
