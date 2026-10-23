import java.io.*;
import java.util.*;

public class TestAndCompareMP2 {

    static class TestCase {
        String name;
        String input;

        TestCase(String name, String input) {
            this.name = name;
            this.input = input;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==================================================");
        System.out.println("  MP_2 Multi-Language Differential Test Suite");
        System.out.println("  Supported: java, kotlin, c, r");
        System.out.println("==================================================");

        String prog1;
        String prog2;

        if (args.length >= 2) {
            prog1 = args[0].trim().toLowerCase();
            prog2 = args[1].trim().toLowerCase();
        } else {
            System.out.print("Enter first program to compare (java, kotlin, c, r) [default: java]: ");
            String line1 = scanner.nextLine().trim().toLowerCase();
            prog1 = line1.isEmpty() ? "java" : line1;

            System.out.print("Enter second program to compare (java, kotlin, c, r) [default: kotlin]: ");
            String line2 = scanner.nextLine().trim().toLowerCase();
            prog2 = line2.isEmpty() ? "kotlin" : line2;
        }

        System.out.printf("\nSelected comparison: [%s] vs [%s]\n", prog1, prog2);

        // Verify and compile both
        compile(prog1);
        compile(prog2);

        List<TestCase> tests = getTestCases();

        int passed = 0;
        for (int t = 0; t < tests.size(); t++) {
            TestCase tc = tests.get(t);
            System.out.printf("\nRunning Test %d/%d: %s...\n", (t + 1), tests.size(), tc.name);

            List<String> out1 = runProcess(getRunCommand(prog1), tc.input);
            List<String> out2 = runProcess(getRunCommand(prog2), tc.input);

            List<String> diffs = findDifferences(out1, out2, prog1, prog2);
            if (diffs.isEmpty()) {
                System.out.printf("  [PASS] %s and %s outputs are 100%% IDENTICAL (%d lines matched).\n",
                        prog1, prog2, out1.size());
                passed++;
            } else {
                System.out.println("  [FAIL] Detected " + diffs.size() + " output mismatch(es):");
                for (String d : diffs) {
                    System.out.println("    " + d);
                }
            }
        }

        System.out.println("\n==================================================");
        System.out.printf("  Summary: %d / %d Tests Passed (%s vs %s).\n", passed, tests.size(), prog1, prog2);
        System.out.println("==================================================");

        if (passed != tests.size()) {
            System.exit(1);
        }
    }

    private static List<TestCase> getTestCases() {
        List<TestCase> tests = new ArrayList<>();

        // Test 1: Require account guard on Deposit, Withdraw, Show Interest before registration
        tests.add(new TestCase(
            "Require Account Guard (Deposit, Withdraw, Interest without Account)",
            "2\nY\n3\nY\n6\nN\n"
        ));

        // Test 2: Validation Reprompts (readChoice, isPositive, isYorN)
        tests.add(new TestCase(
            "Input Validators (readChoice, registerAccount empty check, isYorN)",
            "abc\n0\n7\n-1\n1\n\n   \nAlice\nmaybe\n123\nN\n"
        ));

        // Test 3: Registration and Deposit
        tests.add(new TestCase(
            "Register Account & Deposit (with validation)",
            "1\nJohn Doe\nY\n2\n-50\n0\n1000.50\nN\n"
        ));

        // Test 4: Withdraw (Insufficient funds and Valid withdrawal)
        tests.add(new TestCase(
            "Withdraw (Insufficient Balance & Valid Withdrawal)",
            "1\nBob\nY\n2\n500\nY\n3\n600\nY\n3\n200\nN\n"
        ));

        // Test 5: Record Exchange Rate
        tests.add(new TestCase(
            "Record Exchange Rate (PHP Fixed & Foreign Update)",
            "5\n1\nY\n5\n2\n63.50\nN\n"
        ));

        // Test 6: Currency Exchange (multi-conversion loop with Y/N)
        tests.add(new TestCase(
            "Currency Exchange (PHP to USD, USD to JPY)",
            "4\n1\n124\n2\nY\n2\n10\n3\nN\nN\n"
        ));

        // Test 7: Show Interest (Day validation & Compound calculation)
        tests.add(new TestCase(
            "Show Interest (Day validation and 5-day table)",
            "1\nCharlie\nY\n2\n10000\nY\n6\n-5\n0\n36501\n5\nN\n"
        ));

        // Test 8: Full End-to-End Banking Session
        tests.add(new TestCase(
            "Full End-to-End Workflow",
            "1\nJuan Dela Cruz\nY\n" +
            "2\n2500.00\nY\n" +
            "3\n500.00\nY\n" +
            "5\n2\n60.00\nY\n" +
            "4\n1\n600\n2\nN\nY\n" +
            "6\n3\nN\n"
        ));

        return tests;
    }

    private static void compile(String lang) throws Exception {
        Process p = null;
        switch (lang) {
            case "java": {
                File src = new File("java/MP_2_java.java");
                if (!src.exists()) {
                    throw new FileNotFoundException("Java source file not found: " + src.getPath());
                }
                System.out.println("Compiling Java app (MP_2_java.java)...");
                p = Runtime.getRuntime().exec("javac java/MP_2_java.java");
                break;
            }
            case "kotlin": {
                File src = new File("kotlin/MP_2_kotlin.kt");
                if (!src.exists()) {
                    throw new FileNotFoundException("Kotlin source file not found: " + src.getPath());
                }
                System.out.println("Compiling Kotlin app (MP_2_kotlin.kt)...");
                p = Runtime.getRuntime().exec("cmd.exe /c kotlinc kotlin/MP_2_kotlin.kt -include-runtime -d kotlin/MP_2_kotlin.jar");
                break;
            }
            case "c": {
                String cPath = getExistingPath("c/MP_2_C.c", "c/MP_2_c.c");
                if (cPath == null) {
                    throw new FileNotFoundException("C source file not found (checked c/MP_2_C.c and c/MP_2_c.c).");
                }
                System.out.println("Compiling C app (" + cPath + ")...");
                p = Runtime.getRuntime().exec("gcc " + cPath + " -o c/MP_2_C.exe");
                break;
            }
            case "r": {
                String rPath = getExistingPath("r/MP_2_R.R", "r/MP_2_r.R", "r/MP_2_R.r");
                if (rPath == null) {
                    throw new FileNotFoundException("R script not found (checked r/MP_2_R.R, r/MP_2_r.R).");
                }
                System.out.println("No compilation needed for R app (" + rPath + ")...");
                return;
            }
            default:
                throw new IllegalArgumentException("Unknown language: " + lang + ". Supported: java, kotlin, c, r");
        }

        if (p != null) {
            int exitCode = p.waitFor();
            if (exitCode != 0) {
                BufferedReader errReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                String l;
                while ((l = errReader.readLine()) != null) sb.append(l).append("\n");
                throw new RuntimeException("Compilation failed for " + lang + " (exit code " + exitCode + "):\n" + sb);
            }
        }
    }

    private static String getRunCommand(String lang) {
        switch (lang) {
            case "java":
                return "java -cp java MP_2_java";
            case "kotlin":
                return "java -jar kotlin/MP_2_kotlin.jar";
            case "c":
                return "c\\MP_2_C.exe";
            case "r": {
                String rPath = getExistingPath("r/MP_2_R.R", "r/MP_2_r.R", "r/MP_2_R.r");
                return "Rscript " + (rPath != null ? rPath : "r/MP_2_R.R");
            }
            default:
                throw new IllegalArgumentException("Unknown language: " + lang);
        }
    }

    private static String getExistingPath(String... paths) {
        for (String p : paths) {
            if (new File(p).exists()) {
                return p;
            }
        }
        return null;
    }

    private static List<String> runProcess(String command, String input) throws Exception {
        Process p = Runtime.getRuntime().exec(command);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        writer.write(input);
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        p.waitFor();
        return lines;
    }

    private static List<String> findDifferences(List<String> out1, List<String> out2, String prog1, String prog2) {
        List<String> diffs = new ArrayList<>();
        int max = Math.max(out1.size(), out2.size());
        for (int i = 0; i < max; i++) {
            String l1 = i < out1.size() ? out1.get(i) : "<EOF>";
            String l2 = i < out2.size() ? out2.get(i) : "<EOF>";
            if (!l1.equals(l2)) {
                diffs.add(String.format("Line %d -> %s: '%s' | %s: '%s'", (i + 1), prog1, l1, prog2, l2));
                if (diffs.size() >= 5) {
                    diffs.add("... (truncated further diffs)");
                    break;
                }
            }
        }
        return diffs;
    }
}
