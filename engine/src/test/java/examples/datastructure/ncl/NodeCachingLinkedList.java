package examples.datastructure.ncl;


/****************************************************************************
Author: Juan Pablo Galeotti and Marcelo Frias, Relational Formal Methods 
Group, University of Buenos Aires and Buenos Aires Institute of Technology,
Buenos Aires, Argentina.

ROOPS class implementing the apache.commons.collections class NodeCachingLinkedList.
It uses an auxiliary class LinkedListNode. 

The class has annotations in JFSL [1] given as ROOPS comments. In particular, a class
invariant is provided.

[1] http://sdg.csail.mit.edu/forge/plugin.html
****************************************************************************/




public class NodeCachingLinkedList{


		
	private  LinkedListNode header;
	private int size;

	private  LinkedListNode firstCachedNode;

	/**
	 * The size of the cache.
	 */
	private  int cacheSize;

	/**
	 * The maximum size of the cache.
	 */
	private  int maximumCacheSize;

	public static final int DEFAULT_MAXIMUM_CACHE_SIZE = 20;

	public NodeCachingLinkedList(){
		header = new LinkedListNode();
		header.setValue(null);
		header.setNext(header);
		header.setPrevious(header);
		this.maximumCacheSize = DEFAULT_MAXIMUM_CACHE_SIZE;
	}

	public NodeCachingLinkedList(int maximumCacheSize){
		header = new LinkedListNode();
		header.setValue(null);
		header.setNext(header);
		header.setPrevious(header);
		this.maximumCacheSize = maximumCacheSize;
	}

	public int size(){
		return size;
	}

	public int cacheSize(){return cacheSize;}

	public boolean cacheIsFull(){return isCacheFull();}
	
	/**Gets the element from the list in index position 
	 * @param index of the searched element
	 * @throws IllegalArgumentException if index <0 or index <= size
	 * @return the element in the index position 
	 **/
	public Integer get(int index) {
		if(index >= this.size || index < 0)
			throw new IllegalArgumentException("invalid index");
        LinkedListNode node = getNode(index);
        return node.getValue();
    }

    
    /*Adds an element to the list**/
    public void addFirst(Integer o) {
        LinkedListNode newNode = createNode(o);
        addNode(newNode, header.getNext());

    }
	
	
	 /**
     * Gets a node from the cache. If a node is returned, then the value of
     * cacheSize is decreased accordingly. The node that is returned
     * will have null values for next, previous and element.
     *
     * @return a node, or null if there are no nodes in the cache.
     */
    private LinkedListNode getNodeFromCache() {
        if (cacheSize == 0) {
            return null;
        }
        final LinkedListNode cachedNode = firstCachedNode;
        firstCachedNode = cachedNode.getNext();
        cachedNode.setNext(null); 
        cacheSize--;
        return cachedNode;
    }
    
    /**
     * Inserts a new node into the list.
     *
     * @param nodeToInsert  new node to insert
     * @param insertAfterNode  node to insert before
     * @throws NullPointerException if either node is null
     */
    private void addNode(LinkedListNode nodeToInsert, LinkedListNode insertAfterNode) {
    	nodeToInsert.setNext(insertAfterNode);
        nodeToInsert.setPrevious(insertAfterNode.getPrevious());
        insertAfterNode.getPrevious().setNext(nodeToInsert);
        insertAfterNode.setPrevious(nodeToInsert);
        size++;
    }

    
    
    //-----------------------------------------------------------------------
    /**
     * Creates a new node, either by reusing one from the cache or creating
     * a new one.
     *
     * @param value  value of the new node
     * @return the newly created node
     */
    
    private LinkedListNode createNode(Integer value) {
        final LinkedListNode cachedNode = getNodeFromCache();
        if (cachedNode == null) {
        	LinkedListNode n = new LinkedListNode();
        	n.setValue(value);
            return n;
        }
        cachedNode.setValue(value);
        return cachedNode;
    }
    
    
    /**
	 * Checks whether the cache is full.
	 * 
	 * @return true if the cache is full
	 */
	private boolean isCacheFull() {
		return cacheSize >= maximumCacheSize; 
	}

	

	private void addNodeToCache(LinkedListNode node) {
		if (isCacheFull()) {
			// don't cache the node.
			return;
		}       
		// clear the node's contents and add it to the cache.
		LinkedListNode nextCachedNode = firstCachedNode;
		node.setPrevious(null);
		node.setNext(nextCachedNode);
		node.setValue(null);
		firstCachedNode = node;
		cacheSize=cacheSize +1;
	}

	
	/**
	 * Removes the specified node from the list.
	 *
	 * @param node  the node to remove
	 * @throws NullPointerException if node is null
	 */
	private void super_removeNode(LinkedListNode node) {
		node.getPrevious().setNext(node.getNext());
		node.getNext().setPrevious(node.getPrevious());
		size=size-1;
	}

	

	private void removeNode(LinkedListNode node) {
		super_removeNode(node);
		addNodeToCache(node);
	}

	//-----------------------------------------------------------------------

	public Integer removeIndex(int index) {
		
		Integer oldValue= null;
		LinkedListNode node = getNode(index);
		if(node!=null){
			oldValue = node.getValue();
			removeNode(node);
		}	
		return oldValue;
	}

	//-----------------------------------------------------------------------
	private LinkedListNode getNode(int index) {
		// Check the index is within the bounds
		if (index < 0) {
			return null;
		} 
		if (index >= size) {
			return null;
		} 
		// Search the list and get the node
		LinkedListNode node;
		int size_div_2 = size >> 1;
		
		if (index < size_div_2) {
			
			// Search forwards
			node = header.getNext();
			for (int currentIndex = 0; currentIndex < index; currentIndex++) {
				node = node.getNext();
			}
			
		} else {
			
			// Search backwards
			node = header;
			for (int currentIndex = size; currentIndex > index; currentIndex--) {
				node = node.getPrevious();
			}
			
		}
		return node;
	}
	
	
	//-----------------------------------------------------------------------
	
	public String toString(){
		String res="[";
		for(int i = 0; i < this.size; i++){
			if(i == this.size-1)
				res= res + this.get(i);
			else
				res= res + this.get(i) + ",";
		}
		res= res + "]";
	    return res;
	}
	
	
		  
	/**
	 * @Invariant 
	 *		( this.header!=null ) && -
	 *		( this.header.next!=null ) && -
	 *		( this.header.previous!=null ) && -
	 *		( this.size>=0 ) && -
	 *		( this.cacheSize <= this.maximumCacheSize ) && -
	 *		( this.DEFAULT_MAXIMUM_CACHE_SIZE == 20 ) && -
	 *		( cacheSize == number of nodes in the cachelist) && -
	 *
	 *		( this.size==#(this.header.*next @- null)-1 ) && -
	 *		(for all node m in the cachelist, m.previous==null && m.value==null) &&-
	 *		(cachelist is acyclic) &&
	 *		(for all node n in the list, n!=null &&  n.previous!=null && n.previous.next==n &&
	 *				  n.next!=null &&  n.next.previous==n )&& -
	 *		(size == number of nodes in the list-1 -
	 */
	    public boolean repOK() {
		    if(this.header == null) return false;
			if(this.header.getNext() == null) return false;
			if(this.header.getPrevious() == null) return false;
			if(this.size < 0 ) return false;
			if(this.cacheSize > this.maximumCacheSize) return false;
			if(DEFAULT_MAXIMUM_CACHE_SIZE != 20) return false;
			if(!checkCache()) return false;
			if(!checkList()) return false;
			return true;
	    }

		private boolean checkCache(){
			if(firstCachedNode == null) return true;
			LinkedListNode node = firstCachedNode.getNext();
			int s = 0;
			do {
				if (node.getPrevious() != null || node.getValue() != null) return false;
				node = node.getNext();
				s++;
			}while(node != null);
			return s == this.cacheSize-1;
		}
		private boolean checkList(){
			if(firstCachedNode == null) return true;
			int s = 0;
			LinkedListNode h = this.header.getNext();
			do{
				if(h == null || h.getPrevious() == null || !h.getPrevious().getNext().equals(h)
						|| h.getNext() == null || !h.getNext().getPrevious().equals(h))
					return false;
				h = h.getNext();
				s++;
			}while(!h.equals(header));

			return s == this.size;
		}

	@Override
	public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			NodeCachingLinkedList that = (NodeCachingLinkedList) o;
			if (size != that.size) return false;
			if (maximumCacheSize != that.maximumCacheSize) return false;
			for (int i = 0; i < size; i++) {
				if(!get(i).equals(that.get(i))) return false;
			}
			return true;
	}

}