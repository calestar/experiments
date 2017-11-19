package com.test.signature;

public class PrettyPrinter extends AbstractVisitor {

	private int _level;

	public PrettyPrinter() {
		super(false);
		_level = 0;
	}

	@Override
	protected void onNode(Node node) {
		System.out.println("" + indent() + node.getType().getTypename());
	}
	
	protected String indent() {
		String indent = "";
		
		for (int ix = 0; ix < _level; ix++ ) {
			indent += "  ";
		}
		
		return indent;
	}
	
	@Override
	protected void onPushLevel() {
		_level += 1;
	}

	@Override
	protected void onPopLevel() {
		_level -= 1;
	}
}
