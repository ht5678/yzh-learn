package leecode.d105;

import java.util.Map;

import leecode.model.TreeNode;

/**
 * 
 * 给定一棵树的前序遍历 preorder 与中序遍历  inorder。请构造二叉树并返回其根节点。
 * 
 * @author yuezh2
 * @date 2021/07/21 18:10
 *
 */
public class SimpleDemo {

	
	public TreeNode buildTree(int[] preorder , int preStart, int preEnd , int[] inorder , int inStart , int inEnd , Map<Integer, Integer> inMap) {
		if(preStart>preEnd || inStart > inEnd) {
			return null;
		}
		
		TreeNode root = new TreeNode(preorder[preStart]);
		int inRoot = inMap.get(root.getVal());
		int numsLeft = inRoot - inStart;
		
		root.setLeft(buildTree(preorder, preStart+1, preStart+numsLeft, inorder, inStart, inRoot-1, inMap));
		
		
		return root;
	}
	
}
