package bg.sofia.uni.fmi.mjt.grep;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GrepExecutor {
    private static final int IDX60 = 60;
    private File src;
    private String word;
    private boolean caseSense;
    private boolean wholeWordsOnly;
    private String output;
    private ExecutorService executor;


    public GrepExecutor(boolean caseSense, boolean wholeWordsOnly,
                        String word, String srcPath, int threads, String path) {
        this.caseSense = caseSense;
        this.wholeWordsOnly = wholeWordsOnly;
        this.word = word;
        this.src = new File(srcPath);
        this.output = path;

        executor = Executors.newFixedThreadPool(threads);
    }

    public void finderInDirectory(File src) {
        //File[] files = src.listFiles();
        Queue<File> travers = new LinkedList<>();
        travers.add(src);
        while (!travers.isEmpty()) {
            File current = travers.poll();
            File[] fileDirList = current.listFiles();

            if (fileDirList != null) {
                for (File fd : fileDirList) {
                    if (fd.isDirectory()) {
                        travers.add(fd);
                    } else {
                        executor.execute(() -> finderInFile(fd.getPath()));
                    }
                }
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(IDX60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void finderInFile(String fileName) {
        String line = null;
        int lineNumber = 0;
        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                String originalLine = line;
                if (!caseSense) {
                    line = line.toLowerCase();
                }

                String toFind = this.word;
                if (wholeWordsOnly) {
                    String[] words = line.split(" ");
                    for (String word :
                            words) {
                        if (word.equals(toFind)) {
                            print(fileName, lineNumber, originalLine);
                            break;
                        }
                    }
                } else {
                    if (line.contains(toFind)) {
                        print(fileName, lineNumber, originalLine);
                    }
                }
                lineNumber++;
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
    }

    public void print(String fileName, int lineNumber, String line) {
        BufferedWriter writer;
        int lineIndex = lineNumber + 1;
        if (this.output == null) {
            System.out.println(fileName.substring(this.src.getPath().length() + 1) + ":" + lineIndex + ":" + line);
        } else {
            try {
                writer = new BufferedWriter(new FileWriter(this.output, true));
                writer.write(fileName.substring(this.src.getPath().length() + 1) + ":" + lineIndex + ":" + line + "\n");
                writer.close();
            } catch (IOException e) {
                System.out.println(
                        "Error writing to file '"
                                + this.output + "'");
            }

        }

    }
}
