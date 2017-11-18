package com.test.signature;

public abstract class AbstractVisitor {

	private boolean _depth_first;

	public AbstractVisitor(boolean depth_first) {
		this._depth_first = depth_first;
	}

	void visit(Node node) {
		if (this._depth_first) {
			for (Node child : node.getChilds()) {
				this.visit(child);
			}
			this.onNode(node);
		} else {
			this.onNode(node);
			for (Node child : node.getChilds()) {
				this.visit(child);
			}
		}
	}

	protected abstract void onNode(Node node);
}
