package treePerformance;

/**
 * I took Weiss's main and changed it around so it also uses a BST
 * @author Gittie Klein
 *
 */
public class TestTree
{
	public static void main(String[] args)
	{
		AvlTree<Integer> avl = new AvlTree<Integer>();
		BSTree<Integer> bst = new BSTree<Integer>(); 
		
        int nums = 500000;  // must be even
        int gap = 43;

        long timeStart;
        long timeEnd;
        
        timeStart = System.currentTimeMillis();
        for (int i = gap; i != 0; i = (i + gap) % nums)
        {
            bst.insert(i);
        }
        timeEnd = System.currentTimeMillis();
        System.out.printf("Inserting into BST: %f seconds, %,d comparisons%n", (timeEnd - timeStart)/1000.0, bst.getCount());
        
        timeStart = System.currentTimeMillis();
        for (int i = gap; i != 0; i = (i + gap) % nums)
        {
            avl.insert(i);
        }
        timeEnd = System.currentTimeMillis();
        System.out.printf("Inserting into AVL: %f seconds, %,d comparisons%n%n", (timeEnd - timeStart)/1000.0, avl.getCount());

        bst.resetCount();
        avl.resetCount();
        
        //change gap to search
        gap = 59;
        timeStart = System.currentTimeMillis();
        for (int i = gap; i != 0; i = (i + gap) % nums)
        {
            bst.search(i);
        }
        timeEnd = System.currentTimeMillis();
        System.out.printf("Searching a BST: %f seconds, %,d comparisons%n", (timeEnd - timeStart)/1000.0, bst.getCount());
        
        timeStart = System.currentTimeMillis();
        for (int i = gap; i != 0; i = (i + gap) % nums)
        {
            avl.contains(i);
        }
        timeEnd = System.currentTimeMillis();
        System.out.printf("Searching an AVL: %f seconds, %,d comparisons%n%n", (timeEnd - timeStart)/1000.0, avl.getCount());
        
        bst.resetCount();
        avl.resetCount();
        
        //change gap to remove
        gap = 109;
        timeStart = System.currentTimeMillis();
        for (int i = gap; i != 0; i = (i + gap) % nums)
        {
            bst.remove(i);
        }
        timeEnd = System.currentTimeMillis();
        System.out.printf("Removing from BST: %f seconds, %,d comparisons%n", (timeEnd - timeStart)/1000.0, bst.getCount());
        
        timeStart = System.currentTimeMillis();
        for (int i = gap; i != 0; i = (i + gap) % nums)
        {
            avl.remove(i);
        }
        timeEnd = System.currentTimeMillis();
        System.out.printf("Removing from AVL: %f seconds, %,d comparisons%n%n", (timeEnd - timeStart)/1000.0, avl.getCount());
        
    }
}
