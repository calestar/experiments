package com.test.signature;

public abstract class AbstractVisitor {

	private boolean _depth_first;

	public AbstractVisitor(boolean depth_first) {
		this._depth_first = depth_first;
	}

	void visit(Node node) {
		if (this._depth_first) {
			onPushLevel();
			for (Node child : node.getChilds()) {
				this.visit(child);
			}
			onPopLevel();
			this.onNode(node);
		} else {
			this.onNode(node);
			onPushLevel();
			for (Node child : node.getChilds()) {
				this.visit(child);
			}
			onPopLevel();
		}
	}

	protected void onPushLevel() {
	}

	protected void onPopLevel() {
	}

	protected abstract void onNode(Node node);
}
