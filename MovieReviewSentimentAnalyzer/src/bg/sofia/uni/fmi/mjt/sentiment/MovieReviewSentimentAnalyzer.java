package bg.sofia.uni.fmi.mjt.sentiment;

import bg.sofia.uni.fmi.mjt.sentiment.interfaces.SentimentAnalyzer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {
    private static final double DELTA = 0.001;
    private Set<String> allStopwords;
    private Map<String, Integer> startReviewSentimentMap;
    private Set<Review> scoredReviews;
    private Map<String, Word> sentiments;
    private PrintWriter writer;

    public MovieReviewSentimentAnalyzer(InputStream stopwordsInput, InputStream reviewsInput,
                                        OutputStream reviewsOutput) {
        List<String> allReviews;
        startReviewSentimentMap = new HashMap<>();
        sentiments = new HashMap<>();
        scoredReviews = new HashSet<>();

        try (BufferedReader stopwordsReader = new BufferedReader(new InputStreamReader(stopwordsInput));
             BufferedReader reviewsReader = new BufferedReader(new InputStreamReader(reviewsInput))) {


            allStopwords = stopwordsReader.lines().collect(Collectors.toSet());
            allReviews = reviewsReader.lines().collect(Collectors.toList());
            this.writer = new PrintWriter(reviewsOutput);
        } catch (IOException ioExc) {
            throw new RuntimeException("Error occurred while reading or writing reviews", ioExc);
        }

        for (String line : allReviews) {
            String[] temp = line.split(" ", 2);

            startReviewSentimentMap.put(temp[1].trim().toLowerCase(), Integer.parseInt(temp[0]));
        }

        for (String review : startReviewSentimentMap.keySet()) {
            scoredReviews.add(new Review(this, review, startReviewSentimentMap.get(review)));
        }
    }

    public void addSentiment(String word, double rate) {

        if (sentiments.containsKey(word)) {
            sentiments.get(word).appendSameWord(rate);
        } else {
            sentiments.put(word, new Word(word, rate));
        }
    }

    @Override
    public double getReviewSentiment(String review) {

        if (review == null) {
            return -1.0;
        }
        review = review.toLowerCase();
        double sum = 0;
        int sentimentsCount = 0;
        Scanner s = new Scanner(review);
        s.useDelimiter("[^(a-z)(0-9)]+");
        while (s.hasNext()) {
            String temp = s.next();
            if (isStopWord(temp)) {
                continue;
            } else if (sentiments.containsKey(temp)) {
                sum += sentiments.get(temp).averageRate();
                sentimentsCount++;
            }
        }
        if (sentimentsCount > 0) {
            return sum / sentimentsCount;
        }
        return -1;
    }

    @Override
    public String getReviewSentimentAsName(String review) {
        final int four = 4;
        final int three = 3;
        final int two = 2;
        final int one = 1;
        final int zero = 0;
        int rate = (int) Math.round(getReviewSentiment(review));
        switch (rate) {
            case zero:
                return "negative";
            case one:
                return "somewhat negative";
            case two:
                return "neutral";
            case three:
                return "somewhat positive";
            case four:
                return "positive";
            default:
                return "unknown";
        }
    }

    @Override
    public double getWordSentiment(String word) {
        word = word.toLowerCase();
        double sentiment;
        try {
            sentiment = sentiments.get(word).averageRate();
        } catch (NullPointerException e) {
            return -1.0;
        }
        return sentiment;
    }

    @Override
    public String getReview(double sentimentValue) {
        String review;
        try {
            review = scoredReviews.stream().filter(r -> Math.abs(r.getRating() - sentimentValue) < DELTA)
                    .findFirst().get().getOriginalReview();
        } catch (NoSuchElementException e) {
            return null;
        }

        return review;
    }

    @Override
    public Collection<String> getMostFrequentWords(int n) {
        return sentiments.values().stream().sorted((w1, w2) -> (w2.getOccurrences() - w1.getOccurrences())).limit(n)
                .map(Word::getWord).collect(Collectors.toSet());
    }

    @Override
    public Collection<String> getMostPositiveWords(int n) {
        return sentiments.values().stream().sorted(Comparator.comparing(Word::averageRate).reversed()).limit(n)
                .map(Word::getWord).collect(Collectors.toList());
    }

    @Override
    public Collection<String> getMostNegativeWords(int n) {
        return sentiments.values().stream().sorted(Comparator.comparing(Word::averageRate)).limit(n)
                .map(Word::getWord).collect(Collectors.toList());
    }

    @Override
    public void appendReview(String review, int sentimentValue) {
        scoredReviews.add(new Review(this, review, sentimentValue));

        writer.write(sentimentValue + " " + review + System.lineSeparator());
        writer.flush();
    }

    @Override
    public int getSentimentDictionarySize() {
        return sentiments.size();
    }

    @Override
    public boolean isStopWord(String word) {
        word = word.toLowerCase();
        return allStopwords.contains(word);
    }

}