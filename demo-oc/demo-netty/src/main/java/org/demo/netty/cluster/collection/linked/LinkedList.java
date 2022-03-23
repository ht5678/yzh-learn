package org.demo.netty.cluster.collection.linked;
public class LinkedList<E> {

	private LinkedListNode<E> head;
	
	public LinkedList() {
		head = new LinkedListNode<>();
	}
	
	public LinkedListNode<E> getFirst() {
		LinkedListNode<E> node = head.next;
		if (null == head) {
			return null;
		}
		return node;
	}
	
	public LinkedListNode<E> getLast() {
		LinkedListNode<E> node = head.previous;
		if (null == node) {
			return null;
		}
		return node;
	}
	
	public LinkedListNode<E> addFirst(LinkedListNode<E> node) {
		return node.insert(head.next, head);
	}
	
	public LinkedListNode<E> addFirst(E object) {
		return new LinkedListNode<>(object, head.next, head);
	}
	
	public LinkedListNode<E> addLast(LinkedListNode<E> node) {
		return node.insert(head, head.previous);
	}
	
	public LinkedListNode<E> addLast(E object) {
		return new LinkedListNode<E>(object, head, head.previous);
	}
	
	public void clear() {
		LinkedListNode<E> node = getLast();
		while(null != node) {
			node.remove();
			node = getLast();
		}
		head.next = head.previous = head;
	}
	
	@Override
	public String toString( ) {
		LinkedListNode<E> node = head.next;
		StringBuffer sb = new StringBuffer();
		while(null != node) {
			sb.append(node.toString()).append(", ");
			node = node.next;
		}
		return sb.toString();
	}
}
