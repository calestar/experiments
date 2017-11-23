package com.test.signature;

public final class Main {

	public static void main(String[] args) {
		Parser parser = new Parser();

		try {
			TypeNameCleaner cleaner = new TypeNameCleaner();
			TreePrinter printer = new TreePrinter();

			Node root = parser.parse("QFutureTask<[QFont;>");
			root = parser.parse("QHashMap<QString;[QString;>");

			root = parser.parse("QHashMap<QString;QSet<QEnum;>;>");
			printer.visit(root);

			root = parser.parse("QArrayList<QArrayList<QPoint2D.Double;>;>");
			printer.visit(root);
			cleaner.visit(root);
			printer.visit(root);

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace(System.out);
		}
	}
}
