package com.test.signature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.test.signature.Node;
import com.test.signature.Parser;
import com.test.signature.PrettyPrinter;
import com.test.signature.TypeContext;
import com.test.signature.TypeResolver;

import junit.framework.AssertionFailedError;

public class TypeResolverTests {

	@Test
	void test_nothing_to_resolve() {
		TypeContext context = new TypeContext();
		context.register("String", "java.lang");
		TypeResolver resolver = new TypeResolver(context);
		PrettyPrinter printer = new PrettyPrinter(false);

		Parser parser = new Parser();
		try {
			Node root = parser.parse("java.lang.String", false);

			printer.visit(root);
			assertEquals("java.lang.String", printer.getOutput());

			resolver.visit(root);

			printer.visit(root);
			assertEquals("java.lang.String", printer.getOutput());

		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	@Test
	void test_string_to_resolve() {
		TypeContext context = new TypeContext();
		context.register("String", "java.lang");
		TypeResolver resolver = new TypeResolver(context);
		PrettyPrinter printer = new PrettyPrinter(false);

		Parser parser = new Parser();
		try {
			Node root = parser.parse("QString", false);

			printer.visit(root);
			assertEquals("QString", printer.getOutput());
			resolver.visit(root);

			printer.visit(root);
			assertEquals("java.lang.String", printer.getOutput());

		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	@Test
	void test_generic_array_to_resolve() {
		TypeContext context = new TypeContext();
		context.register("String", "java.lang");
		context.register("ArrayList<java.lang.String>", "java.lang");
		TypeResolver resolver = new TypeResolver(context);
		PrettyPrinter printer = new PrettyPrinter(false);

		Parser parser = new Parser();
		try {
			Node root = parser.parse("QArrayList<QString;>", false);

			printer.visit(root);
			assertEquals("QArrayList<QString>", printer.getOutput());

			resolver.visit(root);

			printer.visit(root);
			assertEquals("java.lang.ArrayList<java.lang.String>", printer.getOutput());

		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	@Test
	void test_generic_array_first_resolved() {
		TypeContext context = new TypeContext();
		context.register("String", "java.lang");
		TypeResolver resolver = new TypeResolver(context);
		PrettyPrinter printer = new PrettyPrinter(false);

		Parser parser = new Parser();
		try {
			Node root = parser.parse("java.lang.ArrayList<QString;>", false);

			printer.visit(root);
			assertEquals("java.lang.ArrayList<QString>", printer.getOutput());

			resolver.visit(root);

			printer.visit(root);
			assertEquals("java.lang.ArrayList<java.lang.String>", printer.getOutput());

		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	@Test
	void test_generic_array_second_resolved() {
		TypeContext context = new TypeContext();
		context.register("ArrayList<java.lang.String>", "java.lang");
		TypeResolver resolver = new TypeResolver(context);
		PrettyPrinter printer = new PrettyPrinter(false);

		Parser parser = new Parser();
		try {
			Node root = parser.parse("QArrayList<java.lang.String;>", false);

			printer.visit(root);
			assertEquals("QArrayList<java.lang.String>", printer.getOutput());

			resolver.visit(root);

			printer.visit(root);
			assertEquals("java.lang.ArrayList<java.lang.String>", printer.getOutput());

		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	@Test
	void test_generic_map_to_resolve() {
		TypeContext context = new TypeContext();
		context.register("String", "java.lang");
		context.register("HashSet<java.lang.String,java.lang.String>", "java.lang");
		TypeResolver resolver = new TypeResolver(context);
		PrettyPrinter printer = new PrettyPrinter(false);

		Parser parser = new Parser();
		try {
			Node root = parser.parse("QHashSet<QString;QString;>", false);

			printer.visit(root);
			assertEquals("QHashSet<QString,QString>", printer.getOutput());

			resolver.visit(root);

			printer.visit(root);
			assertEquals("java.lang.HashSet<java.lang.String,java.lang.String>", printer.getOutput());

		} catch (Exception e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}
}
