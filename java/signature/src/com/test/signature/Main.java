package com.test.signature;

public final class Main {

	public static void main(String[] args) {
		Parser parser = new Parser();

		try {
			Node root = parser.parse("QFutureTask<[QFont;>");
			root = parser.parse("QHashMap<QString;[QString;>");
			root = parser.parse("QHashMap<QString;QSet<QEnum;>;>");
			root = parser.parse("QArrayList<QArrayList<QPoint2D.Double;>;>");

			TypeNameCleaner cleaner = new TypeNameCleaner();
			cleaner.visit(root);

			System.out.print(root);

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace(System.out);
		}
	}
}
