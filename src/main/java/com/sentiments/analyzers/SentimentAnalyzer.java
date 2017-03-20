package com.sentiments.analyzers;

import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalyzer {

    public String findSentiment(String line) {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        int mainSentiment = 0;
        if (line != null && line.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(line);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }
        if (mainSentiment == 2 || mainSentiment > 4 || mainSentiment < 0) {
            return null;
        }
        System.out.println("mainSentiment is "+mainSentiment);
        return toSting(mainSentiment);

    }

    private String toSting(int sentiment) {
        switch (sentiment) {
        case 0:
            System.out.println("---------------- NEGATIVE-----------");
            return "NEGATIVE";

        case 1:
            System.out.println("---------------- NEGATIVE-----------");
            return "NEGATIVE";
        case 2:
            System.out.println("---------------- NEUTRAL-----------");
            return "NEUTRAL";
        case 3:
            System.out.println("---------------- POSITIVE-----------");
            return "POSITIVE";
        case 4:
            System.out.println("---------------- POSITIVE-----------");

            return "POSITIVE";
        default:
            return "";
        }
    }

    public static void main(String[] args) {
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        String result=sentimentAnalyzer
                .findSentiment("There are slow and repetitive parts, but it has just enough spice to keep it interesting.");
//                .findSentiment("@!@1212121@@~ Great optimism in America – and the results will be even better!");
                //.findSentiment("click here for your Sachin Tendulkar personalized digital autograph.");
        System.out.println(result);
    }
}