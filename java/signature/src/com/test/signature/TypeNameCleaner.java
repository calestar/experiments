/***
 * Copyright (c) 2020, 2021 Jean-Sebastien Gelinas, see LICENSE at the root of the repository
 ***/

package com.test.signature;

public class TypeNameCleaner extends AbstractVisitor {

	public TypeNameCleaner() {
		super(true);
	}

	@Override
	protected void onNode(Node node) {
		// Check node name, if it starts with Q, rename it
		String typename = node.getType().getTypename();
		if (!typename.equals("") && typename.charAt(0) == 'Q') {
			String new_typename = typename.substring(1);
			node.getType().updateTypeName(new_typename);
		}
	}
}
