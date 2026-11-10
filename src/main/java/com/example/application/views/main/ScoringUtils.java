package com.example.application.views.main;

import java.util.Map;

public class ScoringUtils {

    // Map of scores
    public static final Map<String, Integer> SCORE_VALUES = Map.ofEntries(
            Map.entry("A+++", 200),
            Map.entry("A++", 150),
            Map.entry("A+", 100),
            Map.entry("A", 50),
            Map.entry("A-", 45),

            Map.entry("B+++", 160),
            Map.entry("B++", 120),
            Map.entry("B+", 80),
            Map.entry("B", 40),
            Map.entry("B-", 35),

            Map.entry("C+++", 120),
            Map.entry("C++", 90),
            Map.entry("C+", 60),
            Map.entry("C", 30),
            Map.entry("C-", 25),

            Map.entry("D+++", 80),
            Map.entry("D++", 60),
            Map.entry("D+", 40),
            Map.entry("D", 20),
            Map.entry("D-", 15),

            Map.entry("E+++", 40),
            Map.entry("E++", 30),
            Map.entry("E+", 20),
            Map.entry("E", 10),
            Map.entry("E-", 5)
    );
}
