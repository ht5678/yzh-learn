package algorithm;


/**
 * 
 * @author yuezh2
 *
 * @date 2020年11月9日 下午4:56:16  
 *
 */
public class ReverseLinkedList {

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
	
	
	
	
	public static Node reverse(Node head) {
		if(head ==null || head.next ==null) {
			return head;
		}
		
		Node newHead = reverse(head.next);
		head.getNext().setNext(head);
		head.setNext(null);
		return newHead;
		
	}
	
	
	
	
	public static void main(String[] args) throws Exception{
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
			System.out.print(head.getData()+"     ");
			head = head.next;
		}
		
	}
	
	
	
}
