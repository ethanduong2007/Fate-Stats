package com.example.application.views.main;

// Imports
import java.io.File;
import java.util.Scanner;
import java.util.Random;

/**
 * @author Ethan Duong
 */

public class Parameter {

    // Variables
    private String name, image;
    private String str, agl, luk, end, mp, np;

    /**
     * Parameter constructor
     *
     * @param name of the servant
     * @param str of the servant
     * @param agl of the servant
     * @param luk of the servant
     * @param end of the servant
     * @param mp of the servant
     * @param np of the servant
     * @param image of the servant
     */
    public Parameter(String name, String str, String agl, String luk, String end, String mp, String np, String image) {
        this.name = name;
        this.str = str;
        this.agl = agl;
        this.luk = luk;
        this.end = end;
        this.mp = mp;
        this.np = np;
        this.image = image;
    }

    // Getter methods
    public String getName() { return name; }
    public String getSTR() {
        return str;
    }
    public String getAGL() {
        return agl;
    }
    public String getLUK() {
        return luk;
    }
    public String getEND() {
        return end;
    }
    public String getMP() {
        return mp;
    }
    public String getNP() {
        return np;
    }
    public String getImage() { return image; }

    // Setter methods
    public void setName(String name) { this.name = name; }
    public void setSTR(String str) {
        this.str = str;
    }
    public void setAGL(String agl) {
        this.agl = agl;
    }
    public void setLUK(String luk) {
        this.luk = luk;
    }
    public void setEND(String end) {
        this.end = end;
    }
    public void setMP(String mp) {
        this.mp = mp;
    }
    public void setNP(String np) {
        this.np = np;
    }
    public void setImage(String image) { this.image = image; }
}
