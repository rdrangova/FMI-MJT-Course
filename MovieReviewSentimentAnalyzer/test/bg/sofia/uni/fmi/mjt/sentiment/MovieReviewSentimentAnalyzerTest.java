package bg.sofia.uni.fmi.mjt.sentiment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class MovieReviewSentimentAnalyzerTest {

    final static double DELTA = 0.001;
    private MovieReviewSentimentAnalyzer analyzer;

    private InputStream reviewsStream;
    private InputStream stopwordsStream;
    private OutputStream resultStream;

    @Before
    public void init() throws FileNotFoundException {
        stopwordsStream = new FileInputStream("resources/stopwords.txt");
        reviewsStream = new FileInputStream("resources/reviews.txt");
        resultStream = new FileOutputStream("resources/reviews1.txt", true);
        analyzer = new MovieReviewSentimentAnalyzer(stopwordsStream, reviewsStream, resultStream);
    }

    @Test
    public void testGetReviewSentiment() {
        double test = analyzer.getReviewSentiment("An engaging , formulaic sports" +
                " drama that carries a charge of genuine excitement .");
        final double expected = 3.25;
        Assert.assertEquals(expected, test, DELTA);
    }

    @Test
    public void testGetReviewSentimentWithNoMeaningWords() {
        double test = analyzer.getReviewSentiment("Radost");
        final double expected = -1.0;
        Assert.assertEquals(expected, test, DELTA);
    }


    @Test
    public void testGetReviewSentimentAsNameReturnsSomewhatPositive() {
        String test = analyzer
                .getReviewSentimentAsName("Great moving film");
        Assert.assertEquals("somewhat positive", test);
    }

    @Test
    public void testGetReviewSentimentAsNameReturnsPositive() {
        String test = analyzer
                .getReviewSentimentAsName("4 A moving story of determination and the human spirit .");
        Assert.assertEquals("positive", test);
    }

    @Test
    public void testGetReviewSentimentAsNameReturnsNeutral() {
        String test = analyzer.getReviewSentimentAsName("Best movie ever").toLowerCase();
        Assert.assertEquals("neutral", test);
    }

    @Test
    public void testGetReviewSentimentAsNameReturnsNegative() {
        String test = analyzer.getReviewSentimentAsName("Amazingly cinema").toLowerCase();
        Assert.assertEquals("negative", test);
    }

    @Test
    public void testGetReviewSentimentAsNameReturnsSomewhatNegative() {
        String test = analyzer
                .getReviewSentimentAsName("film amazingly problem").toLowerCase();
        Assert.assertEquals("somewhat negative", test);
    }

    @Test
    public void testGetReviewSentimentAsNameReturnsUnknown() {
        String test = analyzer
                .getReviewSentimentAsName("This is Radost");
        Assert.assertEquals("unknown", test);
    }

    @Test
    public void testGetWordSentiment() {
        double test = analyzer.getWordSentiment("best");
        final double expected = 3.0;
        Assert.assertEquals(expected, test, DELTA);
    }

    @Test
    public void testGetWordSentimentNotExistingWord() {
        // Setup
        final String word = "Radost";
        final double expectedResult = -1.0;

        final double result = analyzer.getWordSentiment(word);

        assertEquals(expectedResult, result, DELTA);
    }

    @Test
    public void testGetReview() {
        final double val = 2.75;
        String testReview = analyzer.getReview(val);
        Assert.assertEquals("told just proficiently enough " +
                "to trounce its overly comfortable trappings .", testReview);
    }

    @Test
    public void testGetReviewReturnsNull() {
        final double sentimentValue = -5.0;

        final String result = analyzer.getReview(sentimentValue);

        assertNull(result);
    }

    @Test
    public void testGetMostFrequentWords() {

        final int n = 3;
        Set<String> expectedResult = new HashSet<>();
        expectedResult.add("s");
        expectedResult.add("film");
        expectedResult.add("n");

        final Collection<String> result = analyzer.getMostFrequentWords(n);

        assertEquals(expectedResult, result);
    }

    @Test
    public void getMostPositiveWords() {
        List<String> expected = new ArrayList<>();
        final int count = 3;
        List<String> actual = (List<String>) analyzer.getMostPositiveWords(count);
        expected.add("siblings");
        expected.add("simone");
        expected.add("moving");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getMostNegativeWords() {
        List<String> expected = new ArrayList<>();
        final int count = 3;
        List<String> actual = (List<String>) analyzer.getMostNegativeWords(count);
        expected.add("half");
        expected.add("cinema");
        expected.add("amazingly");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAppendReview() {
        final String review = "This is new review";
        final int sentimentValue = 3;

        analyzer.appendReview(review, sentimentValue);
    }

    @Test
    public void testGetSentimentDictionarySize() {
        final int allMeaningWords = 532;
        Assert.assertEquals(allMeaningWords, analyzer.getSentimentDictionarySize());
    }

    @Test
    public void testIsStopWordPositive() {
        assertTrue("Stop word not counted as stop word", analyzer.isStopWord("a"));
    }

    @Test
    public void testIsStopWordNegativeNotFromDictionary() {
        String assertMessage = "A word should not be incorrectly " +
                "identified as a stopword, if it is not part of the stopwords list";
        assertFalse(assertMessage, analyzer.isStopWord("stoyo"));
    }

    @Test
    public void testIsStopWordNegativeFromDictionary() {
        String assertMessage = "A word should not be incorrectly " +
                "identified as a stopword, if it is not part of the stopwords list";
        assertFalse(assertMessage, analyzer.isStopWord("effects"));
    }

}