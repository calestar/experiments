package com.test.signature;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private Node _parent;
	private List<Node> _childs;
	private Type _type;

	public Node(Type type) {
		_childs = new ArrayList<Node>();
		_type = type;
		_parent = null;
	}

	public Node(Type type, Node parent) {
		_childs = new ArrayList<Node>();
		_type = type;
		_parent = parent;
		if (parent != null) {
			parent.appendChild(this);
		}
	}

	public void appendChild(Node child) {
		_childs.add(child);
	}

	public List<Node> getChilds() {
		return _childs;
	}

	public Type getType() {
		return _type;
	}

	public boolean isRoot() {
		return _parent == null;
	}

	public Node getParent() {
		return _parent;
	}

	public String toString() {
		return "";
	}
}
