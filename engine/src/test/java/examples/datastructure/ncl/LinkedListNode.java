package examples.datastructure.ncl;



/****************************************************************************
Author: Juan Pablo Galeotti and Marcelo Frias, Relational Formal Methods 
Group, University of Buenos Aires and Buenos Aires Institute of Technology,
Buenos Aires, Argentina.
Companion LinkedListNode implementation for a ROOPS class implementing the 
apache.commons.collections class NodeCachingLinkedList.

The class has annotations in JFSL [1] given as ROOPS comments.

[1] http://sdg.csail.mit.edu/forge/plugin.html
****************************************************************************/



//package roops.core.objects;

public class LinkedListNode  {



    /** A pointer to the node before this node */
    private LinkedListNode previous;
    /** A pointer to the node after this node */
    private LinkedListNode next;
    /** The object contained within this node */
    private Integer value;
    
	public LinkedListNode() {}
	
	public LinkedListNode getPrevious() {
		return previous;
	}
	
	public void setPrevious(LinkedListNode previous) {
		this.previous = previous;
	}
	
	public LinkedListNode getNext() {
		return next;
	}
	
	public void setNext(LinkedListNode next) {
		this.next = next;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	
	
	
}
