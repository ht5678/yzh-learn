package algorithm;

/**
 * 
 * 
 * 
 * 
 * 准备两个空结点 pre用来保存先前结点、next用来做临时变量
	在头结点node遍历的时候此时为1结点
	next = 1结点.next(2结点)
	1结点.next=pre(null)
	pre = 1结点
	node = 2结点
	进行下一次循环node=2结点
	next = 2结点.next(3结点)
	2结点.next=pre(1结点)=>即完成2->1
	pre = 2结点
	node = 3结点
	进行循环...


 * 
 * @author yuezh2
 *
 * @date 2020年11月19日 下午5:59:14  
 *
 */
public class ReverseList2 {

	
	public static Node reverse(Node node) {
		if(node ==null || node.next==null) {
			return node;
		}
		
		Node pre = null;
		Node next = null;
		
		while(node != null) {
			next = node.next;
			node.next = pre;
			pre = node;
			node = next;
		}
		return pre;
	}
	
	
	public static void main(String[] args) {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		
		node1.setNext(node2);
		node2.setNext(node3);
		node3.setNext(node4);
		node4.setNext(node5);
		
		Node head = reverse(node1);
		while(head!=null) {
			System.out.print(head.getData()+"   ");
			head = head.next;
		}
	}
	
	
	
	static class Node{
		
		private int data;
		
		private Node next;
		
		
		
		public Node(int data) {
			this.data = data;
		}
		
		

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}
		
		
	}
}
