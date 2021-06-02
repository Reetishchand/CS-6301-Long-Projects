/** Interface for a RMQ Structure.
 *  @author SA
 */

package dxp190051;

public interface RMQStructure {
	
    public void preProcess(int[] arr);
	
	public int query(int[] arr, int i, int j);

}

