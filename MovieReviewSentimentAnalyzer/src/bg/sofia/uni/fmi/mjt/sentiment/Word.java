package bg.sofia.uni.fmi.mjt.sentiment;

public class Word {
    private String word;
    private double ratingsSum;
    private int occurrences;

    public Word(String word, double rate) {
        this.word = word;
        ratingsSum = rate;
        occurrences = 1;
    }

    public void appendSameWord(double rate) {
        ratingsSum += rate;
        this.occurrences++;
    }

    public double averageRate() {
        if (occurrences != 0) {
            return ratingsSum / occurrences;
        }
        return 0;
    }

    public String getWord() {
        return word;
    }

    public int getOccurrences() {
        return occurrences;
    }
}
