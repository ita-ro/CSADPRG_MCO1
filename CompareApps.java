import java.io.*;
import java.util.*;

public class CompareApps {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first program to compare (java, kotlin, c, r): ");
        String prog1 = scanner.nextLine().trim().toLowerCase();
        
        System.out.print("Enter second program to compare (java, kotlin, c, r): ");
        String prog2 = scanner.nextLine().trim().toLowerCase();

        String input = "1\nJohn Doe\nJohn Doe\n500\nJohn Doe\n200\n[1]\n62.00\n1000\n";
        
        compile(prog1);
        compile(prog2);

        System.out.println("Running " + prog1 + " app...");
        List<String> output1 = runProcess(getRunCommand(prog1), input);
        
        System.out.println("Running " + prog2 + " app...");
        List<String> output2 = runProcess(getRunCommand(prog2), input);

        System.out.println("\n--- COMPARISON RESULTS ---");
        int diffCount = 0;
        int maxLines = Math.max(output1.size(), output2.size());
        
        for (int i = 0; i < maxLines; i++) {
            String line1 = i < output1.size() ? output1.get(i) : "<EOF>";
            String line2 = i < output2.size() ? output2.get(i) : "<EOF>";
            
            if (!line1.equals(line2)) {
                diffCount++;
                System.out.println("Difference at line " + (i + 1) + ":");
                System.out.println("  " + prog1 + "  : " + line1);
                System.out.println("  " + prog2 + "  : " + line2);
                System.out.println();
            }
        }
        
        if (diffCount == 0) {
            System.out.println("The outputs are exactly identical!");
        } else {
            System.out.println("Total lines different: " + diffCount);
        }
    }

    private static void compile(String lang) throws Exception {
        Process p = null;
        switch (lang) {
            case "java":
                System.out.println("Compiling Java app...");
                p = Runtime.getRuntime().exec("javac java/MCO1_BasicIO_2_java.java");
                break;
            case "kotlin":
                System.out.println("Compiling Kotlin app...");
                p = Runtime.getRuntime().exec("cmd.exe /c kotlinc kotlin/MCO1_BasicIO_2_kotlin.kt -include-runtime -d kotlin/MCO1_BasicIO_2_kotlin.jar");
                break;
            case "c":
                System.out.println("Compiling C app...");
                p = Runtime.getRuntime().exec("gcc c/MCO1_BasicIO_2_C.c -o c/MCO1_BasicIO_2_C.exe");
                break;
            case "r":
                System.out.println("No compilation needed for R app...");
                return;
            default:
                throw new IllegalArgumentException("Unknown language: " + lang);
        }
        if (p != null) {
            p.waitFor();
        }
    }

    private static String getRunCommand(String lang) {
        switch (lang) {
            case "java": return "java -cp java MCO1_BasicIO_2_java";
            case "kotlin": return "java -jar kotlin/MCO1_BasicIO_2_kotlin.jar";
            case "c": return "c\\MCO1_BasicIO_2_C.exe";
            case "r": return "Rscript r/MCO1_BasicIO_2_R.R";
            default: throw new IllegalArgumentException("Unknown language: " + lang);
        }
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
}
