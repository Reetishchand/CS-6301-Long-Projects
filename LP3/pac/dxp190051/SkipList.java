package dxp190051;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class SkipList<T extends Comparable<? super T>> {
    private Entry<T> head, tail;
    public int size, maxLevel;
    private Entry<T>[] lastElement;
    private int[] distanceTraveled;
    private Random randomNumber;
    static final int maxLevels = 32;

    static class Entry<E> {
        E element;
        Entry<E>[] next;
        Entry<E> prev;
        private int level;

        int[] totalSpan;

        /***
         * @param x: element to be stored in the SkipList
         * @param lev: level where element is entered
         */
        public Entry(E x, int lev) {
            element = x;
            next = new Entry[lev];
            level = lev;

            totalSpan = new int[lev];
        }
    }

    private class SkipListIterator implements Iterator<T> {
        Entry<T> position, previous;
        boolean elementReady;

        SkipListIterator() {
            position = head;
            previous = null;
            elementReady = false;
        }

        /**
         * Method: hasNext()
         * returns true id there is a next element in the Skip List
         * @return: boolean
         * */
        public boolean hasNext() {
            return (position.next[0].element != null && position.next[0] != null);
        }

        /**
         * Method: next()
         * returns the next element from the Skip List
         * @return: E
         * */
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("There is no next element.");

            previous = position;
            position = position.next[0];
            elementReady = true;
            return position.element;
        }

        /**
         * Method: remove()
         * removes element from the Skip List
         * */
        public void remove() {
            if (!elementReady)
                throw new NoSuchElementException("Illegal State.");

            findElement(position.element);

            int i=0;

            while (i < position.level) {
                lastElement[i].next[i] = position.next[i];
                lastElement[i].totalSpan[i] = lastElement[i].totalSpan[i] + position.totalSpan[i] - 1;
                i++;
            }

            while (lastElement[i] != null) {
                lastElement[i].totalSpan[i]--;
                i++;
            }

            for(i = 0; i < maxLevels; i++){
                if(head.next[i] == tail)
                    head.totalSpan[i] = size + 1;
            }
            size--;

            position = previous;

            elementReady = false;
        }
    }

    /***
     * Constructor
     */
    public SkipList() {
        head = new Entry<T>(null, maxLevels);
        tail = new Entry<T>(null, maxLevels);

        size = 0;
        maxLevel = 1;

        lastElement = new Entry[maxLevels];
        distanceTraveled = new int[maxLevels];

        randomNumber = new Random();

        for (int i = 0; i < maxLevels; i++) {
            head.next[i] = tail;
            head.totalSpan[i] = 1;
        }

        tail.prev = head;
    }

    /***
     * Method: add()
     * @param x: element to be added in the SkipList
     * Add x to list. If x already exists, reject it.
     * @returns: boolean, returns true if new node is added to list
     */
    public boolean add(T x) {
        if (contains(x) ==  true)
            return false;

        int i = 0;
        int level = getLevel();
        Entry<T> entry = new Entry(x, level);

        int previousPosition = 0, newPosition = 0;

        for (i = 0; i < distanceTraveled.length; i++) {
            previousPosition = previousPosition + distanceTraveled[i];
        }
        newPosition = previousPosition + 1;

        for (i = 0; i < level; i++) {

            if (lastElement[i] == null)
                break;

            entry.next[i] = lastElement[i].next[i];
            lastElement[i].next[i] = entry;

            entry.totalSpan[i] = previousPosition + lastElement[i].totalSpan[i] - newPosition + 1;
            lastElement[i].totalSpan[i] = newPosition - previousPosition;

            previousPosition = previousPosition - distanceTraveled[i];
        }
        entry.level = i;

        entry.next[0].prev = entry;
        entry.prev = lastElement[0];

        while (i < lastElement.length){
            if (lastElement[i] == null)
                break;

            lastElement[i].totalSpan[i]++;
            i++;
        }
        size++;

        for (int j = 0; j < maxLevels; j++){
            if (head.next[j] == tail)
                head.totalSpan[j] = size + 1;
        }

        return true;
    }

    /***
     * Method: remove()
     * @param : x
     * Removes x from list and removed element is returned. Return null if x not in list
     * @return : E
     */
    public T remove(T x) {
        if (!contains(x)) return null;

        Entry<T> elem = lastElement[0].next[0];

        int k = 0;
        while (k < elem.level) {
            lastElement[k].next[k] = elem.next[k];
            lastElement[k].totalSpan[k] = lastElement[k].totalSpan[k] + elem.totalSpan[k] - 1;
            k++;
        }

        while (lastElement[k] != null) {
            lastElement[k].totalSpan[k]--;
            k++;
        }

        for(k = 0; k < maxLevels; k++){
            if(head.next[k] == tail)
                head.totalSpan[k] = size + 1;
        }
        size--;

        return elem.element;
    }

    /***
     * Method: contains()
     * Does list contain x?
     * @returns: boolean, return true if the list contains element
     */
    public boolean contains(T x) {
        if (x == null)
            return false;

        findElement(x);

        if(lastElement[0].next[0].element != null && lastElement[0].next[0].element.compareTo(x) == 0)
            return true;

        return false;
    }

    /***
     * Method: findElement()
     * @param : x - element to be found
     * Finds the element in the list
     */
    private void findElement(T x) {
        Entry<T> p = head;

        distanceTraveled = new int[maxLevels];

        for (int i = 0; i < maxLevel; i++) {
            int in = maxLevel - 1 - i;

            while(	p.next[in] != null &&
                    p.next[in].element != null &&
                    p.next[in].element.compareTo(x) < 0) {

                distanceTraveled[in] += p.totalSpan[in];
                p = p.next[in];
            }
            lastElement[in] = p;
        }
    }

    /***
     * Method: first()
     * @return : E, Return first element of list
     */
    public T first() {
        return head.next[0].element;
    }

    /***
     * Method: first()
     * @return : E, Return last element of list
     */
    public T last() {
        return tail.prev.element;
    }

    /***
     * Method: floor()
     * Find largest element that is less than or equal to x
     * @return : E, returns the largest element
     */
    public T floor(T x) {
        if (!contains(x))
            return lastElement[0].element;

        if (x.compareTo(last()) > 0)
            return last();

        if (x.compareTo(first()) < 0)
            return null;

        return x;
    }

    /***
     * Method: ceiling()
     * @param x: element from the SkipList for which ceiling is to be found
     * Find smallest element that is greater or equal to x
     * @returns: E
     */
    public T ceiling(T x) {
        if (!contains(x))
            return lastElement[0].next[0].element;

        if (x.compareTo(first()) < 0)
            return first();

        if (x.compareTo(last()) > 0)
            return null;

        return x;
    }

    /***
     * Method: size()
     * Return the number of elements in the list
     * @return : Integer
     */
    public int size() {
        return this.size;
    }

    /***
     * Method: getLevel()
     * get the level
     * @returns: E
     */
    public int getLevel() {
        int lev = 1 + Integer.numberOfLeadingZeros(randomNumber.nextInt());

        lev = Math.min(lev,  maxLevel + 1);

        if (maxLevel < lev)
            maxLevel = lev;

        return lev;
    }

    /***
     * Method: get()
     * @param: n, integer n that is index for the list
     * Return element at index n of list.  First element is at index 0
     * @return : E, returns element at index
     */
    public T get(int n) {
        boolean linearCheck = false;

        if (linearCheck)
            return getLinear(n);

        return getLog(n);
    }

    /***
     * Method: getLinear()
     * @param: n, integer n that is index for the list
     * O(n) algorithm for get(n)
     * @return : E, returns element
     */
    public T getLinear(int n) throws NoSuchElementException {
        Entry<T> entry = head;

        if (n < 0 || size - 1 < n)
            throw new NoSuchElementException();

        for (int i = 0; i < n; i++)
            entry = entry.next[0];

        return entry.next[0].element;
    }

    /***
     * Method: getLog()
     * @param: n, integer n that is index for the list
     * O(log n) expected time for get(n).
     * @return : E, returns element
     */
    public T getLog(int n) throws NoSuchElementException {
        int position = n + 1;

        if (n < 0 || size - 1 < n)
            throw new NoSuchElementException();

        int visitedPosition = 0;
        Entry<T> p = head;

        int max = maxLevels - 1;

        boolean trainStarted = false;

        while (!trainStarted) {
            if(p.next[max].element != null) {
                trainStarted = true;
            }
            else
                max--;
        }

        while (max > -1) {
            while ((p.totalSpan[max] + visitedPosition) < position) {
                visitedPosition += p.totalSpan[max];
                p = p.next[max];
            }
            max--;
        }
        return p.next[0].element;
    }

    /***
     * Method: isEmpty()
     * Checks if the list is empty or not
     * @return : Integer
     */
    public boolean isEmpty() {
        if (size() < 1) {
            return true;
        }
        return false;
    }

    /***
     * Method: getPosition()
     * @param: x
     * O(log n) expected time for get(n).
     * @return : Integer, the position of the element in the list
     */
    private int getPosition(T x) {
        Entry<T> headElement = head;

        int dist = 0;

        for (int k = 0; k < maxLevel; k++) {
            int in = maxLevel - 1 - k;
            while(	headElement.next[in] != null &&
                    headElement.next[in].element != null &&
                    headElement.next[in].element.compareTo(x) < 0) {

                dist += headElement.totalSpan[in];
                headElement = headElement.next[in];
            }
            lastElement[in] = headElement;
        }
        return (dist + 1);
    }

    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
        return new SkipListIterator();
    }

    private void printLevels() {
        System.out.println("The max level is given as " + maxLevel + ".\n");

        System.out.print("Level \t\t Elements");
        for (int i = 0; i < maxLevel; i++) {
            int index = maxLevel - 1 - i;

            System.out.print((index+1)+":\t\t");

            Entry<T> elem = head;

            while (elem.next[index] != null) {
                System.out.print(elem.element+" ");
                elem = elem.next[index];
            }
            System.out.println();

        }
    }

    private void printList() {
        System.out.println("The max level is given as " + maxLevel + ".\n");
        System.out.println("Index of element \t Current element \t Next element");
        Entry<T> elem = head;
        int k = 0;

        while (k < size + 2) {
            System.out.print((k-1) + "\t" + elem.element + ": \t[");

            for (int i = 0; i < elem.level; i++) {

                if (i == 0) {
                    if (elem.next[0] == null) {
                        System.out.print("]");
                        break;
                    }
                }

                if (i == maxLevel - 1 || i == elem.level - 1) {
                    if (elem.next[i] != null)
                        System.out.print(elem.next[i].element);
                    System.out.print("]");
                    break;
                }
                else {
                    if (elem.next[i+1] == null) {
                        System.out.print(elem.next[i].element);
                        System.out.print("]");
                        break;
                    }
                    else {
                        System.out.print(elem.next[i].element + ", ");
                    }
                }
            }
            System.out.println();
            elem = elem.next[0];
            k++;
        }
    }

    private void printListSpan() {
        System.out.println("The max level is given as " + maxLevel + ".\n");
        System.out.println("Height off list \t Current element \t Element span");
        Entry<T> elem = head;
        int k = 0;

        while (k < size+2) {
            System.out.print("h = " + elem.level+"\t" + elem.element + ": \t[");

            for (int i = 0; i < elem.level; i++) {

                if (i == 0) {
                    if (elem.next[0] == null) {
                        System.out.print("]");
                        break;
                    }
                }

                if (i == maxLevel - 1 || i == elem.level - 1) {
                    if (elem.next[i] != null)
                        System.out.print(elem.totalSpan[i]);
                    System.out.print("]");
                    break;
                }
                else {
                    if (elem.next[i+1] == null) {
                        System.out.print(elem.totalSpan[i]);
                        System.out.print("]");
                        break;
                    }
                    else {
                        System.out.print(elem.totalSpan[i] + ", ");
                    }
                }
            }

            System.out.println();
            elem = elem.next[0];
            k++;
        }
    }

    /***
     * Method: rebuild()
     * Not a standard operation in skip lists.
     * @return : E
     */
    public void rebuild() {
        maxLevel = (int) (Math.log10(size) / Math.log10(2)) + 1;

        int pos = 0;

        Entry<T>[] previous = new Entry[maxLevel];

        int[] powerOfTwoElements = new int[maxLevel];

        for (int i = 0; i < maxLevel; i++) {
            previous[i] = head;
            powerOfTwoElements[i] = (int) Math.pow(2, i);
        }

        Entry<T> elem = head.next[0];

        while (elem != null) {
            pos++;

            if (!(pos % 2 == 0)) {
                elem.level = 1;

                Entry <T>[] newNext = new Entry[1];
                int[] newSpan = new int[1];

                newNext[0] = elem.next[0];
                newSpan[0] = 1;
                elem.next = newNext;
                elem.totalSpan = newSpan;

                previous[0].next[0] = elem;
                previous[0] = elem;
            }
            else {
                int exp = maxLevel - 1;
                while (pos % powerOfTwoElements[exp] != 0) {
                    exp--;
                }
                elem.level = exp + 1;

                Entry <T>[] newNext = new Entry[elem.level];
                int[] newSpan = new int[elem.level];

                newNext[0] = elem.next[0];
                newSpan[0] = elem.totalSpan[0];
                elem.next = newNext;
                elem.totalSpan = newSpan;

                while (exp > -1) {
                    previous[exp].next[exp] = elem;
                    previous[exp].totalSpan[exp] = powerOfTwoElements[exp];
                    previous[exp] = elem;
                    exp--;
                }
            }
            elem = elem.next[0];
        }

        for (int i = 0; i < maxLevel; i++) {
            int index = maxLevel - 1 - i;

            if (tail != previous[index]) {
                previous[index].next[index] = tail;
                if (size() == Math.pow(2, maxLevel) - 1) {
                    previous[index].totalSpan[index] = powerOfTwoElements[index];
                }
                else {
                    int previousDistance = 0;
                    Entry<T> e = head;
                    while (e.next[index].element != null && e.next[index] != null && e.next[index].element.compareTo(previous[index].element) <= 0) {
                        previousDistance += e.totalSpan[index];
                        e = e.next[index];
                    }
                    previous[index].totalSpan[index] = size + 1 - previousDistance;
                }
            }
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();

        SkipList<Integer> skiplist = new SkipList <>();
        for (int i = 1; i < 240; i++) skiplist.add(i);

        //System.out.println("Old Skip list: \n");
        //skiplist.printList();
        System.out.println();
        int n = 0;
        skiplist.rebuild();

        timer.end();
        System.out.println(timer);
        System.out.println("Skip list after rebuilding: \n");
        //skiplist.printList();
    }
}