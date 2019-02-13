package bg.sofia.uni.fmi.mjt.stylechecker;

import java.io.*;
import java.util.Properties;

public class StyleChecker {
    public static final int MAX_DEFAULT_LINES = 100;


    Properties properties = new Properties();
    private boolean wildCardImportCheckActive;
    private boolean statementsPerLineCheckActive;
    private boolean openingBracketCheckActive;
    private boolean lengthOfLineCheckActive;
    private int lineLengthLimit;


    public StyleChecker() {
        wildCardImportCheckActive = true;
        statementsPerLineCheckActive = true;
        openingBracketCheckActive = true;
        lengthOfLineCheckActive = true;
        lineLengthLimit = MAX_DEFAULT_LINES;
    }

    public StyleChecker(InputStream inputStream) {

        Properties properties = new Properties();
        properties.setProperty("statements.per.line.check.active", "true");
        properties.setProperty("opening.bracket.check.active", "true");
        properties.setProperty("length.of.line.check.active", "true");
        properties.setProperty("line.length.limit", "100");
        properties.setProperty("wild.card.import.check.active", "true");

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        statementsPerLineCheckActive = Boolean.parseBoolean(properties.getProperty("statements.per.line.check.active"));
        wildCardImportCheckActive = Boolean.parseBoolean(properties.getProperty("wild.card.import.check.active"));
        openingBracketCheckActive = Boolean.parseBoolean(properties.getProperty("opening.bracket.check.active"));
        lengthOfLineCheckActive = Boolean.parseBoolean(properties.getProperty("length.of.line.check.active"));
        lineLengthLimit = Integer.parseInt(properties.getProperty("line.length.limit"));

    }

    private boolean statementPerLineCheck(String line) {
        int firstStatementEnd = -1;
        for (int i = 0; i < line.length() - 1; i++) {
            if (line.charAt(i) == ';') {
                firstStatementEnd = i;
                break;
            }
        }
        if (firstStatementEnd == -1) {
            return true;
        }
        for (int i = firstStatementEnd + 1; i < line.length(); i++) {
            if (line.charAt(i) != ';' || line.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    private boolean wildCardImportCheck(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '*' && line.startsWith("import")) {
                return false;
            }
        }
        return true;
    }

    private boolean openingBracketCheck(String line) {
        line.trim();
        if (line.startsWith("{")) {
            return false;
        }
        return true;
    }

    private boolean lengthOfLineCheck(String line) {
        line.trim();
        if (line.length() > lineLengthLimit) {
            return false;
        }
        return true;
    }

    public void checkStyle(InputStream source, OutputStream output) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(source))) {

            String line;
            while ((line = in.readLine()) != null) {

                if (statementsPerLineCheckActive && !statementPerLineCheck(line)) {
                    output.write(("// FIXME Only one statement per line is allowed\n").getBytes());
                }

                if (wildCardImportCheckActive && !wildCardImportCheck(line)) {
                    output.write("// FIXME Wildcards are not allowed in import statements\n".getBytes());
                }

                if (lengthOfLineCheckActive && !lengthOfLineCheck(line) && !line.startsWith("import")) {
                    output.write(
                            ("// FIXME Length of line should not exceed "
                                    + lineLengthLimit + " characters\n").getBytes());
                }

                if (openingBracketCheckActive && !openingBracketCheck(line)) {
                    output.write(
                            ("// FIXME Opening brackets should be placed" +
                                    " on the same line as the declaration\n").getBytes());
                }

                output.write((line + "\n").getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}