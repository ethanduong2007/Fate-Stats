package com.example.application.views.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameState {
    private int servantCount = 0;
    private Set<String> usedServants = new HashSet<>();
    private ArrayList<Parameter> selectedServants = new ArrayList<>();
    private Set<String> usedParameters = new HashSet<>();

    /**
     * Checks if the servant was already used
     * @param id is the id of the servant
     * @return if the servant was already used
     */
    public boolean hasUsedServant(String id) {
        return usedServants.contains(id);
    }

    /**
     * Checks if the parameter was already used
     * @param parameter is the parameter
     * @return if the parameter was already used
     */
    public boolean hasUsedParameter(String parameter) {
        return usedParameters.contains(parameter);
    }

    /**
     * Records a servant used
     * @param servant is the servant
     * @param id is the id of the servant
     */
    public void recordServant(Parameter servant, String id) {
        usedServants.add(id);
        selectedServants.add(servant);
        servantCount++;
    }

    /**
     * Records a parameter used
     * @param parameter is the parameter
     */
    public void useParameter(String parameter) {
        usedParameters.add(parameter);
    }

    /**
     * Gets the servant count
     * @return the servant count
     */
    public int getServantCount() {
        return servantCount;
    }

    /**
     * Gets the selected servants
     * @return the selected servants
     */
    public ArrayList<Parameter> getSelectedServants() {
        return selectedServants;
    }

    /**
     * Resets the round
     */
    public void reset() {
        servantCount = 0;
        usedServants.clear();
        selectedServants.clear();
        usedParameters.clear();
    }

    /**
     * Checks is the round is finished
     * @return if the round goes through 6 or more servants
     */
    public boolean isFinished() {
        return servantCount >= 6;
    }
}
