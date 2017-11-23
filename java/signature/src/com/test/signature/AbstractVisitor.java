package com.test.signature;

public abstract class AbstractVisitor {

	private boolean _depth_first;

	public AbstractVisitor(boolean depth_first) {
		this._depth_first = depth_first;
	}

	public void visit(Node node) {
		onVisitStart(node);
		_visit(node);
		onVisitEnd();
	}

	private void _visit(Node node) {
		beforeNode(node);
		if (this._depth_first) {
			onPushLevel();
			for (Node child : node.getChilds()) {
				this._visit(child);
			}
			onPopLevel();
			this.onNode(node);
		} else {
			this.onNode(node);
			onPushLevel();
			for (Node child : node.getChilds()) {
				this._visit(child);
			}
			onPopLevel();
		}
		afterNode(node);
	}

	protected void beforeNode(Node node) {
	}

	protected void afterNode(Node node) {
	}

	protected void onPushLevel() {
	}

	protected void onPopLevel() {
	}

	protected void onVisitStart(Node node) {
	}

	protected void onVisitEnd() {
	}

	protected abstract void onNode(Node node);
}
