/** 
 * Copyright: Copyright (c)2018
 * Company: 八戒财云 
 */
package com.zbjdl.boss.admin.frame.security;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 类名称：TreeNode <br/>
 * 类描述：通用树形结构<br/>
 * 
 * @author：feng
 * @since：2012-2-17 下午01:49:39
 * @version:
 */
public class TreeNode<T> {
	private T value;
	private TreeNode<T> parent;
	private List<TreeNode<T>> children;

	public TreeNode(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	/**
	 * 增加子节点
	 * 
	 * @param node
	 */
	public void addChild(TreeNode<T> node) {
		if (children == null)
			children = new ArrayList<TreeNode<T>>();

		children.add(node);
	}

	/**
	 * 得到直接下级的节点列表
	 * 
	 * @return
	 */
	public List<TreeNode<T>> getChildren() {
		return children;
	}

	/**
	 * 得到所有的子节点列表（递归向上）
	 * 
	 * @return
	 */
	public List<TreeNode<T>> getAllChildren() {
		if (isLeaf())
			return null;

		List<TreeNode<T>> list = new ArrayList<TreeNode<T>>();
		list.addAll(children);
		for (TreeNode<T> node : children) {
			if (!node.isLeaf())
				list.addAll(node.getAllChildren());
		}
		return list;
	}

	/**
	 * 得到所有的父节点列表（递归向上）
	 * 
	 * @return
	 */
	public List<TreeNode<T>> getAllParent() {
		List<TreeNode<T>> list = new ArrayList<TreeNode<T>>();
		if (parent != null && parent != this) {
			list.add(parent);
			list.addAll(parent.getAllParent());
		}
		return list;
	}
	/**
	 * 返回树的宽度，每个节点占一个宽度单位
	 * 
	 * @return
	 */
	public int getTreeWidth() {
		if (isLeaf()) {
			return 1;
		}
		int width = 0;
		for (TreeNode<T> node : children) {
			width += node.getTreeWidth();
		}
		return width;
	}

	/**
	 * 返回树的深度
	 * 
	 * @return
	 */
	public int getTreeDepth() {
		if (isLeaf())
			return 1;
		int maxDepth = 0;
		for (TreeNode<T> node : children) {
			int depth = node.getTreeDepth();
			if (depth > maxDepth)
				maxDepth = depth;
		}
		maxDepth = maxDepth + 1;
		return maxDepth;
	}

	/**
	 * 是否是树叶，没有子节点的为树叶
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		return children == null || children.size() == 0;
	}
}