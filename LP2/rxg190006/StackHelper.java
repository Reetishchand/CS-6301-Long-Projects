package LP2.rxg190006;

public class StackHelper<E> {
	int length;
    int peek;
    E[] stack;
    int peakCapacity;
    
    public StackHelper(int peakCapacity){
    	this.length = 0;
        this.peakCapacity = peakCapacity;
        this.peek = -1;
        this.stack = (E[]) new Object[peakCapacity];   
    }

    public E pop(){
        if(this.length>0){  
        this.length--;
        return this.stack[peek--];
        }
        return null;
    }

    public E peek(){
    	return this.length>0?this.stack[peek]:null;
    }

    public boolean push(E e){
       if(isStackFull()) {
        	return false;
        }
        this.length++;
        this.stack[++peek] = e;
        return true;
    }

    public boolean isStackFull(){
    	
    	return (this.length == this.peakCapacity);
    }

    public boolean isStackEmpty(){
    	return this.length==0;
      
    }
}