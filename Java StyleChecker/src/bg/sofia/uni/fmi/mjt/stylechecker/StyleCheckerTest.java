package bg.sofia.uni.fmi.mjt.stylechecker;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class StyleCheckerTest {

    @Test
    public void test() {
        ByteArrayInputStream input = new ByteArrayInputStream("import java.util.*;".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        StyleChecker checker = new StyleChecker();
        checker.checkStyle(input, output);
        String actual = new String(output.toByteArray());

        TestCase.assertEquals("// FIXME Wildcards are not allowed in import statements" +
                "\nimport java.util.*;", actual.trim());
    }


    @Test
    public void testLengthOfRowsExceededReturnsFix() {
        ByteArrayInputStream input = new ByteArrayInputStream(
                ("java.util.java.util.java.util.java.util.java.util." +
                        "java.util.java.util.java.util.java.util.java.util.java.util.;").getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        StyleChecker checker = new StyleChecker();
        checker.checkStyle(input, output);
        String actual = new String(output.toByteArray());

        TestCase.assertEquals("// FIXME Length of line should not exceed 100 characters\n" +
                        "java.util.java.util.java.util.java.util.java.util.java.util.java." +
                        "util.java.util.java.util.java.util.java.util.;"
                , actual.trim());
    }

    @Test
    public void testStatementsPerLineCheckReturnsFix() {
        ByteArrayInputStream input = new ByteArrayInputStream("java.util;java.util;".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        StyleChecker checker = new StyleChecker();
        checker.checkStyle(input, output);
        String actual = new String(output.toByteArray());

        TestCase.assertEquals("// FIXME Only one statement per line is allowed\n" +
                        "java.util;java.util;"
                , actual.trim());
    }

    @Test
    public void testOpeningBracketCheckReturnsFix() {
        ByteArrayInputStream input = new ByteArrayInputStream("{".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        StyleChecker checker = new StyleChecker();
        checker.checkStyle(input, output);
        String actual = new String(output.toByteArray());

        TestCase.assertEquals(
                "// FIXME Opening brackets should be placed on " +
                        "the same line as the declaration\n{", actual.trim());
    }

    @Test
    public void testMultipleErrorsReturnsFix() {
        ByteArrayInputStream input =
                new ByteArrayInputStream(
                        ("void thisIsJustAnotherTest(int argument1....., argument1...., " +
                                "argument2..., argument3.....argument4...)" +
                                "\n{func1;func2;func3;" + "\ncorrect line}").getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        StyleChecker checker = new StyleChecker();
        checker.checkStyle(input, output);
        String actual = new String(output.toByteArray());

        TestCase.assertEquals("// FIXME Length of line should not exceed 100 characters\n" +
                "void thisIsJustAnotherTest(int argument1....., " +
                "argument1...., argument2..., argument3.....argument4...)\n" +
                "// FIXME Only one statement per line is allowed\n" +
                "// FIXME Opening brackets should be placed on the same line as the declaration\n" +
                "{func1;func2;func3;\n" +
                "correct line}", actual.trim());
    }

    @Test
    public void testMultipleErrorsReturnsFixWithConstructorWithParameters() {
        ByteArrayInputStream input =
                new ByteArrayInputStream(
                        ("void thisIsJustAnotherTest(int argument1....., " +
                                "argument1...., argument2..., argument3.....argument4...)" +
                                "\n{func1;func2;func3;" + "\ncorrect line}").getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        ByteArrayInputStream info =
                new ByteArrayInputStream(
                        ("statements.per.line.check.active=true\n"
                                + "opening.bracket.check.active=false\n" + "length.of.line.check.active=true\n"
                                + "line.length.limit=150\n" + "wild.card.import.check.active=true\n").getBytes());

        StyleChecker checker = new StyleChecker(info);
        checker.checkStyle(input, output);
        String actual = new String(output.toByteArray());

        TestCase.assertEquals(
                "void thisIsJustAnotherTest(int argument1....., argument1...., " +
                        "argument2..., argument3.....argument4...)\n" +
                        "// FIXME Only one statement per line is allowed\n" +
                        "{func1;func2;func3;\n" +
                        "correct line}", actual.trim());
    }


}


