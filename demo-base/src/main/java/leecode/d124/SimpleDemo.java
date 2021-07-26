package leecode.d124;

import leecode.model.TreeNode;

/**
 * 
 * 
 * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
	路径和 是路径中各节点值的总和。
	给你一个二叉树的根节点 root ，返回其 最大路径和 。
 * 
 * 
 * @author yuezh2
 * @date 2021/07/21 17:40
 *
 */
public class SimpleDemo {

	int ans = 0;
	
	
	
	public int oneSideMax(TreeNode root) {
		if(root == null) {
			return 0;
		}
		
		int left = Integer.max(0, oneSideMax(root.getLeft()));
		int right = Integer.max(0, oneSideMax(root.getRight()));
		ans = Integer.max(ans , left+right+root.getVal());
		return Integer.max(left, right)+root.getVal();
	}
	
	
	
	
	public static void main(String[] args) {
		TreeNode root = new TreeNode(-10);
		
		root.setLeft(new TreeNode(9));
		
		
		
	}
	
}
