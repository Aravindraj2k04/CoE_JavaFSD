import java.io.*;
import java.util.*;

public class LogFileAnalyzer {
    private static final List<String> keywords = Arrays.asList("ERROR", "WARNING");
    
    public static void analyzeLogFile(String inputFile, String outputFile) {
        Map<String, Integer> keywordCounts = new HashMap<>();
        
        for (String keyword : keywords) {
            keywordCounts.put(keyword, 0);
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                for (String keyword : keywords) {
                    if (line.contains(keyword)) {
                        keywordCounts.put(keyword, keywordCounts.get(keyword) + 1);
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
            
            writer.write("\nSummary:\n");
            for (Map.Entry<String, Integer> entry : keywordCounts.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue() + " occurrences\n");
            }
            System.out.println("Log analysis complete. Results saved to " + outputFile);
            
        } catch (IOException e) {
            System.out.println("Error processing log file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        String inputFile = "log.txt";
        String outputFile = "log_analysis.txt";
        analyzeLogFile(inputFile, outputFile);
    }
}
