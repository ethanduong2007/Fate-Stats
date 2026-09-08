package com.example.application.views.main;

/**
 * @author Ethan Duong
 */

public class Score {

    // Variables
    private int score;
    private double multiplier;
    private static double highScore;

    /**
     * Score constructor
     */
    public Score() {
        this.score = 0;
        this.multiplier = 1.0;
    }

    // Getter methods
    public int getScore() { return score; }
    public double getMultiplier() { return multiplier; }
    public static double getHighScore() { return highScore; }

    /**
     * Add a set amount to the score
     *
     * @param rank is the rank of the parameter
     */
    public void addScore(String rank) {
        if(rank.equals("A+++")) {
            score += 200;
        } else if(rank.equals("A++")) {
            score += 150;
        } else if(rank.equals("A+")) {
            score += 100;
        } else if(rank.equals("A")) {
            score += 50;
        } else if(rank.equals("B+++")) {
            score += 160;
        } else if(rank.equals("B++") || rank.equals("C+++")) {
            score += 120;
        } else if(rank.equals("B+") || rank.equals("D+++")) {
            score += 80;
        } else if(rank.equals("B") || rank.equals("D+") || rank.equals("E+++")) {
            score += 40;
        } else if(rank.equals("C++")) {
            score += 90;
        } else if(rank.equals("C+") || rank.equals("D++")) {
            score += 60;
        } else if(rank.equals("C") || rank.equals("E++")) {
            score += 30;
        } else if(rank.equals("D") || rank.equals("E+")) {
            score += 20;
        } else if(rank.equals("E")) {
            score += 10;
        } else if(rank.equals("EX")) {
            multiplier += 0.5;
        } else {
            score += 0;
        }
    }

    /**
     * Calculates the final score
     */
    public double calculateScore() {
        return score * multiplier;
    }

    /**
     * Calculates the high score
     */
    public double calculateHighScore() {
        if(calculateScore() > highScore) {
            highScore = calculateScore();
            return highScore;
        } else {
            return highScore;
        }
    }

    /**
     * Resets the score
     */
    public void reset() {
        score = 0;
        multiplier = 1.0;
    }
}
