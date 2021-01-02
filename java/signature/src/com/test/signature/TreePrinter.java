/***
 * Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/

package com.test.signature;

public class TreePrinter extends AbstractVisitor {

	private int _level;

	public TreePrinter() {
		super(false);
		_level = 0;
	}

	@Override
	protected void onNode(Node node) {
		System.out.println("" + indent() + node.getType().getTypename());
	}

	protected String indent() {
		String indent = "";

		for (int ix = 0; ix < _level; ix++) {
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
