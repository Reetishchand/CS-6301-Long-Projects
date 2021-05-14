package dxp190051;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;


public class BinarySearchTree<T extends Comparable<? super T>> {
    Entry<T> root;
    Map<Entry<T>,Entry<T>> parents;
    Map<Entry<T>,Entry<T>> uncles;
    Entry<T> splicedChild;
    Entry<T> splicedEntry;
    int size;
    //Stack that stores the parent of the current node during traversal
    Stack<Entry<T>> s;

    /***
     * Binary Search Tree Entry
     * @param <T> Generic Type T extends Comparable
     */
    static class Entry<T> {
        T element;
        Entry<T> left, right;
        boolean isLeftChild;

        /***
         * Constructor
         * @param x element to be stored in the binary search tree
         * @param left entry to the left of the bst
         * @param right entry to the right of the bst
         */
        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
        /***
         * Contructor
         * @param x element to be stored in the binary search tree
         */
        public Entry(T x) {
            this(x,null,null);
        }

        public boolean isLeftChild() {
            return isLeftChild;
        }

        public boolean isRightChild() {
            return !isLeftChild;
        }
    }

    /**
     * Constructor
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /***
     * Helper method to initialize map datastructres for storing parent and uncle
     */
    private void initMap(){
        s  = new Stack<>();
        parents= new HashMap<>();
        uncles= new HashMap<>();
        parents.put(root, null);
        uncles.put(root, null);
        s.push(null);
    }

    /***
     * Find the element x in the bst
     * @param x element to find
     * @return the entry of the element if exists in the BST, otherwise null
     */
    protected Entry<T> find(T x){
        initMap();
        return find(root,x);
    }

    /***
     * Get the parent of the given entry
     * @param child child entry
     * @return parent entry of the child, null if child is root
     */
    protected Entry<T> parent(Entry<T> child){
        return parents.get(child);
    }

    /***
     * Get the Uncle of the given entry
     * @param x entry
     * @return the child of the x's grand parent which is not the parent(x)
     */
    protected Entry<T> uncle(Entry<T> x){
        return uncles.get(x);
    }

    /***
     * Add parent to the parent map for the given child key
     * @param child key entry
     * @param parent value entry
     */
    protected void addParent(Entry<T> child, Entry<T> parent){
        if(child != null)
            parents.put(child,parent);
    }

    /***
     * Compute and add uncle to the given child entry
     * @param child key entry
     * @param parent parent of the given child entry
     */
    void addUncle(Entry<T> child, Entry<T> parent){
        if(child != null)
        {
            Entry<T> grandParent = parent(parent);
            if(grandParent == null){
                return;
            }

            if(parent.isLeftChild)
                uncles.put(child, grandParent.right);
            else
                uncles.put(child, grandParent.left);
        }
    }

    /***
     * Check if the x is null or value of the node is null
     * @param x entry
     * @return true of x is null or x.element is null, otherwise false
     */
    private boolean isNull(Entry<T> x){
        return x == null || x.element == null;
    }

    /***
     * Find the Entry x in the subtree at root t
     * @param t root node of the subtree
     * @param x element to find
     * @return the entry of the x if exists in the BST, otherwise null
     */
    private Entry<T> find(Entry<T> t, T x){
        if(isNull(t) || t.element.compareTo(x) == 0)
            return t;
        while(true){
            if(x.compareTo(t.element) < 0){
                if(isNull(t.left)) break;
                s.push(t);
                addParent(t.left, t);
                addParent(t.right, t);
                addUncle(t.left, t);
                t = t.left;
            }
            else if(x.compareTo(t.element) == 0) break;
            else {
                if(isNull(t.right)) break;
                s.push(t);
                addParent(t.right, t);
                addParent(t.left, t);
                addUncle(t.right, t);
                t = t.right;
            }
        }
        return t;
    }


    /**
     * Checks if the element x exists in the tree
     * @param x element to search
     * @return true if the tree contains x, otherwise false.
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        return t != null && t.element.equals(x);
    }

    /***
     * Searches for the element and returns the same element that is equal to x in the tree
     * @param x element to search
     * @return x if tree contains x, otherwise null;
     */
    public T get(T x) {
        Entry<T> t = find(x);
        if(!isNull(t) && t.element.equals(x))
            return x;
        return null;
    }


    /***
     * Adds the entry to the tree
     * @param entry entry element to be added
     * @return true if entry is added to the tree, false if entry already exists
     */
    protected boolean add(Entry<T> entry) {
        if(size == 0)
        {
            initMap();
            root = entry;
            size++; return true;
        }
        else{
            Entry<T> t = find(entry.element);
            if(t.element.compareTo(entry.element) == 0) return false;
            if(entry.element.compareTo(t.element)<0)
            {
                entry.isLeftChild = true;
                t.left = entry;
            }
            else
            {
                entry.isLeftChild = false;
                t.right = entry;
            }
            size++;
            s.push(t);
            addParent(entry, t);
            addUncle(entry, t);
            return true;
        }
    }
    /***
     * Adds x to the tree
     * @param x element to be added.
     * @return true if x is added to the tree, false if x already exists
     */
    public boolean add(T x) {
        return  add(new Entry<>(x));
    }


    /***
     * Remove x from the tree
     * @param x element to be removed
     * @return x if it exists in the tree and removed successfully, otherwise null
     */
    public T remove(T x) {
        if(size == 0) return null;
        Entry<T> t = find(x);
        if(t.element.compareTo(x) != 0) return null;
        return remove(t);
    }

    /***
     * Remove the entry t from the tree
     * @param t entry to be removed
     * @return element stored in entry t if it exists, otherwise null
     */
    public T remove(Entry<T> t){
        T x = t.element;
        if(isNull(t.left)|| isNull(t.right))
        {
            splice(t);
        }
        else
        {
            s.push(t);
            Entry<T> minRight = find(t.right,x);
            copy(t,minRight);

            addParent(t.right, t);
            addParent(t.left, t);

            splice(minRight);
        }
        size--;
        return x;
    }

    /***
     * Copies element value from source entry to destination entry
     * @param dest destination entry
     * @param src source entry
     */
    protected void copy(Entry<T> dest, Entry<T> src) {
        dest.element = src.element;
    }

    /***
     * Splice the subtree at root t such that t is replaced with child of t.
     * Precondition: t should have only one child
     * @param t root of the subtree, the entry to be reomved
     */
    public void splice(Entry<T> t){

        Entry<T> parent = s.peek();
        Entry<T> child = isNull(t.left) ? t.right : t.left;
        splicedEntry = t;
        splicedChild = child;
        if(isNull(parent))
            root = child;
        else if(parent.left == t)
        {
            if(child != null) child.isLeftChild = true;
            parent.left = child;
            addParent(child, parent);
        }
        else
        {
            if(child != null) child.isLeftChild = false;
            parent.right = child;
            addParent(child, parent);
        }

    }

    /***
     * Return the elment  with minimum value in the tree
     * @return the element with minimum value in the BST, null if the tree has no element.
     */
    public T min() {
        if(size == 0)
            return null;
        Entry<T> t = root;
        while(!isNull(t.left))
            t = t.left;
        return t.element;
    }
    /***
     * Return the elment  with ,maximum value in the tree
     * @return the element with maximum value in the BST, null if the tree has no element.
     */
    public T max() {
        if(size == 0)
            return null;
        Entry<T> t = root;
        while(!isNull(t.right))
            t = t.right;
        return t.element;
    }

    /***
     * Creates an array with elements in in-order traversal of the tree
     * @return the array of elments from the tree in in-order (ascending order) traversal
     */
    public Comparable[] toArray() {
        if(size == 0)
            return null;
        Comparable[] arr = new Comparable[size];
        List<T> lst = new ArrayList<>();
        Entry<T> t = root;
        inOrder(t, lst);
        return lst.toArray(arr);
    }

    /***
     * Performs in-order traversal of the tree and stores the path in t
     * @param t root of the subtree
     * @param lst list where the inorder traversal is stored
     */
    public void inOrder(Entry<T> t, List<T> lst){
        if(t!=null) {
            inOrder(t.left, lst);
            lst.add(t.element);
            inOrder(t.right, lst);
        }
    }


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
     Solve this problem without creating an array using in-order traversal (toArray()).
     */
//    public Iterator<T> iterator() {
//        return new BinarySearchTreeIterator<T>(root);
//    }
//
//    public static class BinarySearchTreeIterator<T> implements Iterator<T> {
//        Stack<Entry<T>> stack;
//        int state = 0;
//        public BinarySearchTreeIterator(Entry<T> root){
//            stack = new Stack<>();
//            pushLeftChildrenToStack(root);
//        }
//        @Override
//        public boolean hasNext() {
//            return !stack.empty();
//        }
//
//        @Override
//        public T next() {
//            Entry<T> t = stack.pop();
//            T result = t.element;
//            if(t.right!=null && t.right.element!=null){
//                pushLeftChildrenToStack(t.right);
//            }
//            return result;
//        }
//
//        private void pushLeftChildrenToStack(Entry<T> root){
//            Entry<T> t = root;
//            while(t !=null && t.element!=null){
//                stack.push(t);
//                t = t.left;
//            }
//        }
//    }

    /***
     * Main method for testing merge topological sort
     * @param args optional arguments
     * @throws Exception
     */
    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }
        }
    }


    /***
     * Prints the size and inorder of the tree from the root
     */
    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    /***
     * Performs in order traversal of the subtree and prints the tree in ascendeing order of the value
     * @param node root of the sub tree
     */
    void printTree(Entry<T> node) {
        if(node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }
}