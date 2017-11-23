package com.test.signature;

public class PrettyPrinter extends AbstractVisitor {

	private String _output;

	public PrettyPrinter() {
		super(false);
	}

	@Override
	protected void onNode(Node node) {
		_output += node.getType().getTypename();
	}

	@Override
	protected void beforeNode(Node node) {
		Node parent = node.getParent();
		if (parent != null) {
			if (node.isParentFirstChild()) {
				_output += "<";
			} else {
				_output += ",";
			}
		}
	}

	@Override
	protected void afterNode(Node node) {
		Node parent = node.getParent();
		if (parent != null && node.isParentLastChild()) {
			_output += ">";
		}
	}

	public String getOutput() {
		return _output;
	}

	@Override
	protected void onVisitStart(Node node) {
		_output = "";

		if (!node.isRoot()) {
			throw new RuntimeException("Can't start visit in PrettyPrinter with non-root node ");
		}
	}
}
