package com.example.application.views.main;

import java.util.ArrayList;

/**
 * @author Ethan Duong
 */

public class Score {

    // Variables
    private int score;
    private double multiplier;
    private static double highScore;
    private double theoreticalHighScore;
    private String[] bestAssignment;

    /**
     * Score constructor
     */
    public Score() {
        this.score = 0;
        this.multiplier = 1.0;
        this.theoreticalHighScore = 0;
        this.bestAssignment = new String[6];
    }

    // Getter methods
    public int getScore() { return score; }
    public double getMultiplier() { return multiplier; }
    public static double getHighScore() { return highScore; }
    public String[] getBestAssignment() { return bestAssignment; }

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
     * Gets the set value of the score
     *
     * @param rank is the rank of the parameter
     * @return the value of the rank of the parameter
     */
    public int getScoreValue(String rank) {
        if(rank.equals("A+++")) {
             return 200;
        } else if(rank.equals("A++")) {
             return 150;
        } else if(rank.equals("A+")) {
            return 100;
        } else if(rank.equals("A")) {
             return 50;
        } else if(rank.equals("B+++")) {
             return 160;
        } else if(rank.equals("B++") || rank.equals("C+++")) {
            return 120;
        } else if(rank.equals("B+") || rank.equals("D+++")) {
            return 80;
        } else if(rank.equals("B") || rank.equals("D+") || rank.equals("E+++")) {
            return 40;
        } else if(rank.equals("C++")) {
            return 90;
        } else if(rank.equals("C+") || rank.equals("D++")) {
            return 60;
        } else if(rank.equals("C") || rank.equals("E++")) {
            return 30;
        } else if(rank.equals("D") || rank.equals("E+")) {
            return 20;
        } else if(rank.equals("E")) {
            return 10;
        } else if(rank.equals("EX")) {
            return 0;
        } else {
            return 0;
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
     * @return the high score
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
     * Calculates the theoretical high score
     * @return the theoretical high score
     */
    public double calculateTheoreticalHighScore(ArrayList<Parameter> servants) {
        String[] parameters = {"STR", "AGL", "LUK", "END", "MP", "NP"};
        boolean[] usedParameters = new boolean[6];
        String[] currentAssignment = new String[6];

        findBestAssignment(servants, parameters, usedParameters, currentAssignment, 0);

        return theoreticalHighScore;
    }

    /**
     * Finds the best parameter assignment for the theoretical high score
     *
     * @param servants is the list of servants
     * @param parameters is the list of parameters
     * @param usedParameters tracks the parameters used
     * @param currentAssignment is the assignment of the parameters on the servant
     * @param servantIndex is the servant currently being assigned to
     */
    private void findBestAssignment(ArrayList<Parameter> servants, String[] parameters, boolean[] usedParameters, String[] currentAssignment, int servantIndex) {
        double totalScore = 0;
        double theoreticalMultiplier = 1.0;

        if(servantIndex == servants.size()) {
            for(int i = 0; i < servants.size(); i++) {
                Parameter servant = servants.get(i);
                String rank = servant.getParameter(currentAssignment[i]);

                if(rank.equals("EX")) {
                    theoreticalMultiplier += 0.5;
                }

                int rankValue = getScoreValue(rank);

                totalScore += rankValue;
            }

            totalScore *= theoreticalMultiplier;

            if(totalScore >= theoreticalHighScore) {
                theoreticalHighScore = totalScore;

                for(int i = 0; i < bestAssignment.length; i++) {
                    bestAssignment[i] = currentAssignment[i];
                }
            }

            return;
        }

        for(int i = 0; i < parameters.length; i++) {
            if (!usedParameters[i]) {
                currentAssignment[servantIndex] = parameters[i];
                usedParameters[i] = true;

                findBestAssignment(servants, parameters, usedParameters, currentAssignment, servantIndex + 1);

                usedParameters[i] = false;
                currentAssignment[servantIndex] = null;
            }
        }
    }

    /**
     * Resets the score
     */
    public void reset() {
        score = 0;
        multiplier = 1.0;
        theoreticalHighScore = 0;
    }
}