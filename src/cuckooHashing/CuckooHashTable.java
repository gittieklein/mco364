package cuckooHashing;

public class CuckooHashTable<T>
{
	private final double MAX_LOAD = .5; // rehash after this load is reached
    private final int MAX_NR_REHASHES = 10; // do not allow any more inserts 
    private final int MAX_NR_EVICTIONS = 4;
    private final int INIT_TABLE_SIZE;
    private T[] array1; // The first array of elements
    private T[] array2; // The second array of elements
    private int nrOccupied;	// The number of occupied cells in both arrays combined
    private int nrRehashes;	//how many times the table was hashed
    private int capacity;   // current capacity of each array
    
    /**
     * Constructor
     * If the user doesn't specify a size for the initial table size, then pass in 11
     */
    public CuckooHashTable()
    {
    	this(11);
    }
    
    /**
     * Constructor
     * @param size initial array size
     */
    public CuckooHashTable(int size)
    {
    	INIT_TABLE_SIZE = (size % 2 == 0 ? size++ : size);
    	array1 = (T[]) (new Object[INIT_TABLE_SIZE]);
    	array2 = (T[]) (new Object[INIT_TABLE_SIZE]);
    	nrOccupied = 0;
    	capacity = INIT_TABLE_SIZE;
    	nrRehashes = 0;
    }
    
    /**
     * hash a value to find place in the first array
     * @param value 
     * @return array location
     */
    private int hash1(T value)
    {
    	return value.hashCode() % capacity;
    }
    
    /**
     * hash a value to find place place in the second array
     * @param value
     * @return array location
     */
    private int hash2(T value)
    {
    	return (value.hashCode()/capacity) % capacity;
    }
     
    /**
     * insert a value into the array
     * @param x the value to insert
     * @return true or false if inserted
     */
    public boolean insert(T x)
    {	
    	if(contains(x)) { return false; }
    	
    	if(nrOccupied >= MAX_LOAD * (capacity * 2))
    	{
    		//if the table was already rehashed too many times, return false, it's not inserted
    		if(nrRehashes < MAX_NR_REHASHES) rehash(null);
    		else return false;
    	}
    	
    	int pos;
    	T tmp;
    	T insertValue = x;	//create a variable to hold onto the value incase you have to remove it
    	for(int i = 0; i < MAX_NR_EVICTIONS; i++)
    	{
    		pos = hash1(x);
    	
	    	if(array1[pos] == null)
	    	{
	    		array1[pos] = x;
	    		nrOccupied++;
	    		return true;
	    	}
	    	
    		tmp = array1[pos];
    		array1[pos] = x;
    		
    		pos = hash2(tmp);
    		if(array2[pos] == null)
    		{
    			array2[pos] = tmp;
    			nrOccupied++;
    			return true;
    		}
    		
    		x = array2[pos];
    		array2[pos] = tmp;
	   	}
    	//if it came out of the loop (it wasn't returned) then the array is too full and needs to be rehashed
    	if(nrRehashes < MAX_NR_REHASHES) 
    	{
    		rehash(x);
        	return true;	//after it was rehashed, insert is true because it added that value
    	}
    	//the number that the user tried to insert should be removed because there is no room for it and the number 
    	//that was evicted should be reinserted
		else
		{
			remove(insertValue);
			insert(x);
			return false;
		}    	
    }
    
    /**
     * Remove an element from the hash table
     * @param x the element to remove
     * @return true if removed, false if not in the table
     */
    public boolean remove(T x)
    {
    	if(array1[hash1(x)] != null && array1[hash1(x)].equals(x)) 
    	{
    		array1[hash1(x)] = null;
    		nrOccupied--;
    		return true;
    	}
    	if(array2[hash2(x)] != null && array2[hash2(x)].equals(x))
    	{
    		array2[hash2(x)] = null;
    		nrOccupied--;
    		return true;
    	}
    	return false;
    }
    
    /**
     * check if the value is in the hash table
     * @param x 
     * @return true or false
     */
    public boolean contains(T x)
    {
    	if(contains1(x) != -1) return true;
    	if(contains2(x) != -1) return true;
    	return false;
    }
    
    /**
     * check if the value is in the first hash table
     * this will be used internally by a bunch of the methods
     * @param x 
     * @return the position in the array or -1 if it is not there
     */
    private int contains1(T x)
    {
    	int pos = hash1(x);
    	if(array1[pos] != null && array1[pos].equals(x)) return pos;
    	return -1;    	
    }
    
    /**
     * check if the value is in the second hash table
     * this will be used internally by a bunch of the methods
     * @param x 
     * @return the position in the array or -1 if it is not there
     */
    private int contains2(T x)
    {
    	int pos = hash2(x);
    	if(array2[pos] != null && array2[pos].equals(x)) return pos;
    	return -1;    	
    }
    
    /**
     * rehash the table if it is too full
     * the rehash function could receive a value, it will be null if there is no extra value
     * the reason why you would need to insert an extra value is if you were in middle of inserting
     * and bounced back too many times - now that value is not in either array at this point but you
     * want it to be part of the array
     * @param x null or the value to insert
     */
    private void rehash(T x)
    {
    	capacity = (capacity * 2) + 1;	//add 1 so that the capacity is an odd number
    	nrOccupied = 0;	//reset occupied to 0
    	T[] temp1 = array1;
    	T[] temp2 = array2;
    	array1 = (T[]) new Object[capacity];
    	array2 = (T[]) new Object[capacity];
    	
    	if(x != null)
    	{
    		insert(x);
    	}
    	for(int i = 0; i < temp1.length; i++)
    	{
    		if(temp1[i] != null) insert(temp1[i]);	
    		if(temp2[i] != null) insert(temp2[i]);	
    	}
    	nrRehashes++;	//increase number of times the table was rehashed
    }
    
    /**
     * getters for testing
     */
    public int getOccupied()
	{
		return nrOccupied;
	}

	public int getRehashes()
	{
		return nrRehashes;
	}
	
	public int getCapacity()
	{
		return capacity;
	}

	/**
     * get position of element in array 
     * the position is returned +1 so that the user sees the position starting from 1 not 0
     * @param x
     * @return a string with the table and position
     */
    public String getPosition(T x)
    {
    	int pos = contains1(x);
    	if(pos != -1) return x + " is in table 1, position " + (pos + 1);	//add one when return position so starts at 1
    	pos = contains2(x);
    	if(pos != -1) return x + " is in table 2, position " + (pos +1);
    	
    	return x + " is not in the hash table";
    }
    
    /**
     * to string to display the tree
     */
    public String toString()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append(String.format("    |  %-10s  |  %-10s%n", "Table 1", "Table 2"));
    	sb.append("------------------------------------\n");
    	for(int i = 0; i < array1.length; i++)
    		sb.append(String.format("%2d  |  %10s  |  %10s%n", i, array1[i] == null ? " - " : array1[i], array2[i] == null ? " - " : array2[i]));
    	return sb.toString();
    }   


    
}
