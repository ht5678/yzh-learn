package leecode.model;


/**
 * 
 * @author yuezh2
 * @date 2021/07/21 17:42
 *
 */
public class TreeNode {

	private TreeNode left;
	
	private TreeNode right;
	
	private int val;
	
	
	

	public TreeNode(int val) {
		this.val = val;
	}



	public TreeNode(TreeNode left, TreeNode right, int val) {
		this.left = left;
		this.right = right;
		this.val = val;
	}
	
	

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}
	
	
	
	public static TreeNode arrayToTree(int[] array) {
		if(array == null || array.length==0) {
			return null;
		}
		
		TreeNode root = null;
		for(int item : array) {
			if(root == null) {
				root = new TreeNode(item);
				continue;
			}
			
			root.setLeft(new TreeNode(9));
			
			
		}
		
		
		return root;
	}
	
	
}
