package bg.sofia.uni.fmi.mjt.grep;

import java.io.File;
import java.util.Scanner;

public class Main {
    private final static int IDX2 = 2;
    private final static int IDX3 = 3;
    private final static int IDX4 = 4;
    private final static int IDX5 = 5;

    public static void main(String[] args) {
        boolean wholeWordsFlag = false;
        boolean caseSense = true;
        String searchedWord = null;
        String srcDirectoryPath = null;
        int numOfThreads = 0;
        String dstPath = null;
        Scanner reader = new Scanner(System.in);
        String command = reader.nextLine();

        String[] instructions = command.split(" ");

        switch (instructions[1]) {
            case "-w":
                wholeWordsFlag = true;
                break;
            case "-i":
                caseSense = false;
                break;
            case "-wi":
                wholeWordsFlag = true;
                caseSense = false;
                break;
            default:
                searchedWord = instructions[1];
                break;
        }

        if (searchedWord == null) {
            searchedWord = instructions[IDX2];
        } else {
            srcDirectoryPath = instructions[IDX2];
        }

        if (srcDirectoryPath == null) {
            srcDirectoryPath = instructions[IDX3];
        } else {
            numOfThreads = Integer.parseInt(instructions[IDX3]);
        }

        try {
            if (instructions.length > IDX4) {
                if (numOfThreads == 0) {
                    numOfThreads = Integer.parseInt(instructions[IDX4]);
                } else {
                    dstPath = instructions[IDX4];
                }
            }
        } catch (NumberFormatException e) {
            wholeWordsFlag = false;
            caseSense = true;
            searchedWord = instructions[1];
            srcDirectoryPath = instructions[2];
            numOfThreads = Integer.parseInt(instructions[IDX3]);
            dstPath = instructions[IDX4];
        }

        if (instructions.length > IDX5) {
            dstPath = instructions[IDX5];
        }

        if (instructions.length < IDX5) {
            wholeWordsFlag = false;
            caseSense = true;
            searchedWord = instructions[1];
            srcDirectoryPath = instructions[2];
            numOfThreads = Integer.parseInt(instructions[IDX3]);
        }

        GrepExecutor greper = new GrepExecutor(caseSense, wholeWordsFlag,
                searchedWord, srcDirectoryPath, numOfThreads, dstPath);
        greper.finderInDirectory(new File(srcDirectoryPath));
    }

}
