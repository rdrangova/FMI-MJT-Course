package bg.sofia.uni.fmi.mjt.sentiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Review {

    private String originalReview;
    private List<Word> wordsInReview;
    private double rating;

    public Review(MovieReviewSentimentAnalyzer analyzer, String review, int rate) {
        wordsInReview = new ArrayList<>();
        setOriginalReview(review);
        setReview(analyzer, review, rate);

    }

    public void setReview(MovieReviewSentimentAnalyzer analyzer, String review, int rate) {
        Scanner s = new Scanner(review);
        s.useDelimiter("[^(a-z)(0-9)]+");
        while (s.hasNext()) {
            String tempW = s.next();
            if (!analyzer.isStopWord(tempW)) {
                analyzer.addSentiment(tempW, rate);
                wordsInReview.add(new Word(tempW, analyzer.getWordSentiment(tempW)));
                setRating();
            }
        }
    }

    public void setRating() {
        double sum = 0;
        for (Word word :
                wordsInReview) {
            sum += word.averageRate();
        }
        rating = sum / (wordsInReview.size());
    }

    public double getRating() {
        return rating;
    }

    public String getOriginalReview() {
        return originalReview;
    }

    public void setOriginalReview(String originalReview) {
        this.originalReview = originalReview;
    }
}
