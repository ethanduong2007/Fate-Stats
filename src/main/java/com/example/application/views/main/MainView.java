package com.example.application.views.main;

/**
 * @author Ethan Duong
 */

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.UI;
import java.util.ArrayList;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

@PageTitle("Fate/Stats")
@Route("game")
@Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class MainView extends HorizontalLayout {
    // Initialize parameter variables
    private Parameter servant;

    private Span name;
    private Span shuffling;
    private Image servantImage;

    private Span str, agl, luk, end, mp, np;
    private Span strSummary, endStrSummary, aglSummary, endAglSummary, lukSummary, endLukSummary, endSummary, endEndSummary, mpSummary, endMpSummary, npSummary, endNpSummary;
    private Span bestStrSummary, bestAglSummary, bestEndAglSummary, bestLukSummary, bestEndSummary, bestMpSummary, bestNpSummary;

    private Image strImage, aglImage, lukImage, endImage, mpImage, npImage;
    private Image strImageSummary, aglImageSummary, lukImageSummary, endImageSummary, mpImageSummary, npImageSummary;
    private Image bestStrImageSummary, bestAglImageSummary, bestLukImageSummary, bestEndImageSummary, bestMpImageSummary, bestNpImageSummary;

    // Initialize tracking variables
    private boolean isShuffling = true;

    private boolean parameterClicked;

    private Random number;
    private ArrayList<Parameter> servants;
    private int servantCount = 0;
    private Set<String> usedServants = new HashSet<>();
    private ArrayList<Parameter> selectedServants = new ArrayList<>();
    private Set<String> usedParameters = new HashSet<>();

    // Initialize scoring variables
    private Score scoreLogic = new Score();
    private Score theoreticalScoreLogic = new Score();
    private Span scoreDisplay, multiplierDisplay;
    private Span finalScore;
    private Span theorereticalBestScore;

    // Initialize end screen
    private Dialog endScreen;

    /**
     * Load all the servants at the beginning of the program
     *
     * @param fileName is the name of the file
     */
    private void servantLoad(String fileName) {
        servants = new ArrayList<>();
        Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(fileName));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] data = line.trim().split(",");

            // Extract data
            String name = data[0].trim();
            String str = data[1].trim();
            String agl = data[2].trim();
            String luk = data[3].trim();
            String end = data[4].trim();
            String mp = data[5].trim();
            String np = data[6].trim();
            String image = data[7].trim();

            servants.add(new Parameter(name, str, agl, luk, end, mp, np, image));
        }

        scanner.close();
    }

    /** Creates a random servant
     *
     * @throws Exception
     */
    public void getRandomServant() throws Exception {
        // Display servant data
        number = new Random();
        servant = servants.get(number.nextInt(servants.size()));

        name.setText(servant.getName());
        servantImage.setSrc("images/servants/" + servant.getImage());

        str.setText("STR: ?");
        strImage.setSrc(getRankPath("?"));
        if(parameterUsed("STR")) {
            str.getStyle().set("opacity", "0.35");
            strImage.getStyle().set("opacity", "0.35");
        }

        agl.setText("AGL: ?");
        aglImage.setSrc(getRankPath("?"));
        if(parameterUsed("AGL")) {
            agl.getStyle().set("opacity", "0.35");
            aglImage.getStyle().set("opacity", "0.35");
        }

        luk.setText("LUK: ?");
        lukImage.setSrc(getRankPath("?"));
        if(parameterUsed("LUK")) {
            luk.getStyle().set("opacity", "0.35");
            lukImage.getStyle().set("opacity", "0.35");
        }

        end.setText("END: ?");
        endImage.setSrc(getRankPath("?"));
        if(parameterUsed("END")) {
            end.getStyle().set("opacity", "0.35");
            endImage.getStyle().set("opacity", "0.35");
        }

        mp.setText("MP: ?");
        mpImage.setSrc(getRankPath("?"));
        if(parameterUsed("MP")) {
            mp.getStyle().set("opacity", "0.35");
            mpImage.getStyle().set("opacity", "0.35");
        }

        np.setText("NP: ?");
        npImage.setSrc(getRankPath("?"));
        if(parameterUsed("NP")) {
            np.getStyle().set("opacity", "0.35");
            npImage.getStyle().set("opacity", "0.35");
        }
    }

    /**
     * Get the ID of the servant
     *
     * @param image is the image of the servant
     * @return the ID of the servant
     */
    private String getServantID(String image) {
        return image.substring(0, image.indexOf("."));
    }

    /**
     * Replaces the rank image for a parameter
     *
     * @param rank is the rank of the parameter
     * @return the image of the rank
     */
    private String getRankPath(String rank) {
        rank = rank.replace("+", "");

        if(rank.equals("?")) {
            rank = "Unknown";
        }

        return "images/ranks/" + rank + ".png";
    }

    /**
     * Gets the rank image for a parameter
     *
     * @param rank is the rank of the parameter
     * @return the image of the rank
     */
    private Image getRankImage(String rank) {
        return new Image(getRankPath(rank), rank);
    }

    /**
     * Creates the text for the parameter
     *
     * @param parameter is the parameter of the servant
     * @return the displayed text for the parameter
     */
    private Span createParameter(String parameter) {
        Span span = new Span(parameter + ": ?");
        span.getStyle().set("font-size", "15px").set("padding-left", "10px"); // Original size is 20px
        span.setWidth("90px");
        return span;
    }

    /**
     * Creates the text for the parameter summary
     *
     * @param parameter is the parameter of the servant
     * @return the displayed text for the parameter
     */
    private Span createParameterSummary(String parameter) {
        Span span = new Span(parameter + ": ?");
        span.getStyle().set("font-size", "15px").set("text-align", "center"); // Original size is 20px
        span.setWidth("80px");
        return span;
    }

    /**
     * Creates the image for the parameter
     *
     * @return the displayed image for the parameter
     */
    private Image createParameterImage() {
        Image image = getRankImage("?");
        image.setWidth("350px");
        image.setHeight("auto");
        return image;
    }

    /**
     * Creates the image for the parameter summary
     *
     * @return the displayed image for the parameter
     */
    private Image createParameterImageSummary() {
        Image image = getRankImage("?");
        image.setWidth("120px");
        image.setHeight("auto");
        return image;
    }

    /**
     * Creates the layout for the parameters
     *
     * @param parameter is the parameter of the servant
     * @param parameterImage is the image of the parameter
     * @return the layout for the parameter
     */
    private HorizontalLayout createParameterLayout(Span parameter, Image parameterImage) {
        HorizontalLayout horizontalLayout = new HorizontalLayout(parameter, parameterImage);
        horizontalLayout.setWidth("100%");
        horizontalLayout.setMaxWidth("500px");
        horizontalLayout.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        return horizontalLayout;
    }

    /**
     * Creates the layout for the parameter summary
     *
     * @param parameter is the parameter of the servant
     * @param parameterImage is the image of the parameter
     * @return the layout for the parameter
     */
    private VerticalLayout createParameterSummaryLayout(Span parameter, Image parameterImage) {
        VerticalLayout verticalLayout = new VerticalLayout(parameter, parameterImage);
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        verticalLayout.setSpacing(false);
        verticalLayout.getStyle().set("padding-left", "5px").set("padding-right", "5px").set("padding-top", "0px");
        return verticalLayout;
    }

    /**
     * Checks if the parameter is used
     *
     * @param parameter is the parameter of the servant
     * @return false if the parameter is used
     */
    private boolean parameterUsed(String parameter) {
        if(usedParameters.contains(parameter)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the value of parameter
     *
     * @param parameter is the parameter of the servant
     * @return the value of the parameter
     */
    private String getParameterValue(Parameter servant, String parameter) {
        if(parameter.equals("STR")) {
            return servant.getSTR();
        } else if(parameter.equals("AGL")) {
            return servant.getAGL();
        } else if(parameter.equals("LUK")) {
            return servant.getLUK();
        } else if(parameter.equals("END")) {
            return servant.getEND();
        } else if(parameter.equals("MP")) {
            return servant.getMP();
        } else {
            return servant.getNP();
        }
    }

    /**
     * Gives the function for when a parameter is clicked
     *
     * @param parameter is the parameter clicked
     */
    private void parameterClicked(String parameter) {
        if (!isShuffling && !parameterUsed(parameter) && !parameterClicked) {
            scoreLogic.addScore(getParameterValue(servant, parameter));
            scoreDisplay.setText("Score " + scoreLogic.getScore());
            multiplierDisplay.setText("Multiplier " + scoreLogic.getMultiplier() + "x");

            str.setText("STR: " + servant.getSTR());
            strImage.setSrc(getRankPath(servant.getSTR()));
            agl.setText("AGL: " + servant.getAGL());
            aglImage.setSrc(getRankPath(servant.getAGL()));
            luk.setText("LUK: " + servant.getLUK());
            lukImage.setSrc(getRankPath(servant.getLUK()));
            end.setText("END: " + servant.getEND());
            endImage.setSrc(getRankPath(servant.getEND()));
            mp.setText("MP:  " + servant.getMP());
            mpImage.setSrc(getRankPath(servant.getMP()));
            np.setText("NP:  " + servant.getNP());
            npImage.setSrc(getRankPath(servant.getNP()));

            if(parameter.equals("STR")) {
                strSummary.setText("STR: " + servant.getSTR());
                endStrSummary.setText("STR: " + servant.getSTR());
                strImageSummary.setSrc("images/servants/" + servant.getImage());
                usedParameters.add("STR");
            } else if(parameter.equals("AGL")) {
                aglSummary.setText("AGL: " + servant.getAGL());
                endAglSummary.setText("AGL: " + servant.getAGL());
                aglImageSummary.setSrc("images/servants/" + servant.getImage());
                usedParameters.add("AGL");
            } else if(parameter.equals("LUK")) {
                lukSummary.setText("LUK: " + servant.getLUK());
                endLukSummary.setText("LUK: " + servant.getLUK());
                lukImageSummary.setSrc("images/servants/" + servant.getImage());
                usedParameters.add("LUK");
            } else if(parameter.equals("END")) {
                endSummary.setText("END: " + servant.getEND());
                endEndSummary.setText("END: " + servant.getEND());
                endImageSummary.setSrc("images/servants/" + servant.getImage());
                usedParameters.add("END");
            } else if(parameter.equals("MP")) {
                mpSummary.setText("MP: " + servant.getMP());
                endMpSummary.setText("MP: " + servant.getMP());
                mpImageSummary.setSrc("images/servants/" + servant.getImage());
                usedParameters.add("MP");
            } else if(parameter.equals("NP")) {
                npSummary.setText("NP: " + servant.getNP());
                endNpSummary.setText("NP: " + servant.getNP());
                npImageSummary.setSrc("images/servants/" + servant.getImage());
                usedParameters.add("NP");
            }

            parameterClicked = true;

            endGame();
        }
    }

    /**
     * Ends the game
     */
    private void endGame() {
        if(servantCount == 6) {
            finalScore.setText("Final Score: " + (int) scoreLogic.calculateScore());
            theorereticalBestScore.setText("Best Possible Score: " + (int) theoreticalScoreLogic.calculateTheoreticalHighScore(selectedServants));

            String[] bestAssignment = theoreticalScoreLogic.getBestAssignment();

            for(int i = 0; i < bestAssignment.length; i++) {
                if(bestAssignment[i].equals("STR")) {
                    bestStrSummary.setText("STR: " + selectedServants.get(i).getParameter("STR"));
                    bestStrImageSummary.setSrc("images/servants/" + selectedServants.get(i).getImage());
                } else if(bestAssignment[i].equals("AGL")) {
                    bestAglSummary.setText("AGL: " + selectedServants.get(i).getParameter("AGL"));
                    bestAglImageSummary.setSrc("images/servants/" + selectedServants.get(i).getImage());
                } else if(bestAssignment[i].equals("LUK")) {
                    bestLukSummary.setText("LUK: " + selectedServants.get(i).getParameter("LUK"));
                    bestLukImageSummary.setSrc("images/servants/" + selectedServants.get(i).getImage());
                } else if(bestAssignment[i].equals("END")) {
                    bestEndSummary.setText("END: " + selectedServants.get(i).getParameter("END"));
                    bestEndImageSummary.setSrc("images/servants/" + selectedServants.get(i).getImage());
                } else if(bestAssignment[i].equals("MP")) {
                    bestMpSummary.setText("MP: " + selectedServants.get(i).getParameter("MP"));
                    bestMpImageSummary.setSrc("images/servants/" + selectedServants.get(i).getImage());
                } else if(bestAssignment[i].equals("NP")) {
                    bestNpSummary.setText("NP: " + selectedServants.get(i).getParameter("NP"));
                    bestNpImageSummary.setSrc("images/servants/" + selectedServants.get(i).getImage());
                }
            }

            endScreen.open();
        }
    }

    /**
     * Resets the game
     */
    private void resetGame() {
        usedServants.clear();
        selectedServants.clear();
        usedParameters.clear();
    }

    // Display
    public MainView() throws Exception {
        // Change background
        getStyle().set("background-image", "url('/images/background/background1.png')").set("background-size", "cover").set("background-position", "center-bottom").set("background-repeat", "no-repeat");

        // Load servants
        servantLoad("servants.txt");
        number = new Random();
        servant = servants.get(number.nextInt(servants.size()));

        // Shuffle servants
        UI.getCurrent().setPollInterval(50);

        UI.getCurrent().addPollListener(event -> {
            try {
                getRandomServant();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Display servant
        name = new Span(servant.getName());
        name.getStyle().set("font-size", "20px"); // Original size is 25px

        shuffling = new Span("Click to stop");
        shuffling.getStyle().set("font-size", "15px").set("opacity", "0.5");

        servantImage = new Image("images/servants/" + servant.getImage(), servant.getName());
        servantImage.setWidth("350px");
        servantImage.setHeight("auto");

        // Display parameters
        str = createParameter("STR");
        strSummary = createParameter("STR");
        endStrSummary = createParameterSummary("STR");
        strImage = createParameterImage();
        strImageSummary = createParameterImageSummary();
        bestStrSummary = createParameterSummary("STR");
        bestStrImageSummary = createParameterImageSummary();

        agl = createParameter("AGL");
        aglSummary = createParameter("AGL");
        endAglSummary = createParameterSummary("AGL");
        aglImage = createParameterImage();
        aglImageSummary = createParameterImageSummary();
        bestAglSummary = createParameterSummary("STR");
        bestAglImageSummary = createParameterImageSummary();

        luk = createParameter("LUK");
        lukSummary = createParameter("LUK");
        endLukSummary = createParameterSummary("LUK");
        lukImage = createParameterImage();
        lukImageSummary = createParameterImageSummary();
        bestLukSummary = createParameterSummary("STR");
        bestLukImageSummary = createParameterImageSummary();

        end = createParameter("END");
        endSummary = createParameter("END");
        endEndSummary = createParameterSummary("END");
        endImage = createParameterImage();
        endImageSummary = createParameterImageSummary();
        bestEndSummary = createParameterSummary("STR");
        bestEndImageSummary = createParameterImageSummary();

        mp = createParameter("MP");
        mpSummary = createParameter("MP");
        endMpSummary = createParameterSummary("MP");
        mpImage = createParameterImage();
        mpImageSummary = createParameterImageSummary();
        bestMpSummary = createParameterSummary("STR");
        bestMpImageSummary = createParameterImageSummary();

        np = createParameter("NP");
        npSummary = createParameter("NP");
        endNpSummary = createParameterSummary("NP");
        npImage = createParameterImage();
        npImageSummary = createParameterImageSummary();
        bestNpSummary = createParameterSummary("STR");
        bestNpImageSummary = createParameterImageSummary();

        // Display score
        scoreDisplay = new Span("Score " + scoreLogic.getScore());
        scoreDisplay.getStyle().set("font-size", "15px").set("text-align", "center"); // Original size is 20px

        multiplierDisplay = new Span("Multiplier " + scoreLogic.getMultiplier() + "x");
        multiplierDisplay.getStyle().set("font-size", "15px").set("text-align", "center"); // Original size is 20px

        // Display parameter summary
        HorizontalLayout parameterDisplay1 = new HorizontalLayout(strSummary, aglSummary, lukSummary);
        HorizontalLayout parameterDisplay2 = new HorizontalLayout(endSummary, mpSummary, npSummary);

        // Display parameter summary layout
        VerticalLayout parameterDisplay = new VerticalLayout(parameterDisplay1, parameterDisplay2);
        parameterDisplay.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        parameterDisplay.setWidth("100%");
        parameterDisplay.setMaxWidth("375px");
        parameterDisplay.setAlignItems(FlexComponent.Alignment.CENTER);

        // Shuffle to next servant
        parameterClicked = true;

        servantImage.addClickListener(event -> {
           try {
               if(parameterClicked) {
                   // Stop shuffling
                   if(isShuffling) {
                       UI.getCurrent().setPollInterval(-1);

                       number = new Random();

                       while(usedServants.contains(getServantID(servant.getImage()))) {
                           servant = servants.get(number.nextInt(servants.size()));
                       }

                       name.setText(servant.getName());
                       servantImage.setSrc("images/servants/" + servant.getImage());

                       shuffling.setText("Click to shuffle");
                       usedServants.add(getServantID(servant.getImage()));
                       selectedServants.add(servant);
                       servantCount++;
                       isShuffling = !isShuffling;
                       parameterClicked = !parameterClicked;
                   // Continue shuffling
                   } else {
                       UI.getCurrent().setPollInterval(50);
                       shuffling.setText("Click to stop");
                       isShuffling = !isShuffling;
                   }
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
        });

        // Modify scores
        strImage.addClickListener(event -> parameterClicked("STR"));
        aglImage.addClickListener(event -> parameterClicked("AGL"));
        lukImage.addClickListener(event -> parameterClicked("LUK"));
        endImage.addClickListener(event -> parameterClicked("END"));
        mpImage.addClickListener(event -> parameterClicked("MP"));
        npImage.addClickListener(event -> parameterClicked("NP"));

        // Create parameter layouts
        VerticalLayout info = new VerticalLayout(name, shuffling, servantImage);
        info.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        info.setAlignItems(FlexComponent.Alignment.CENTER);
        info.setWidthFull();
        info.setMaxWidth("500px");
        info.setSpacing(false);
        info.setPadding(false);

        HorizontalLayout strStat = createParameterLayout(str, strImage);
        HorizontalLayout aglStat = createParameterLayout(agl, aglImage);
        HorizontalLayout lukStat = createParameterLayout(luk, lukImage);
        HorizontalLayout endStat = createParameterLayout(end, endImage);
        HorizontalLayout mpStat = createParameterLayout(mp, mpImage);
        HorizontalLayout npStat = createParameterLayout(np, npImage);

        HorizontalLayout total = new HorizontalLayout(parameterDisplay, scoreDisplay, multiplierDisplay);
        total.setWidth("100%");
        total.setMaxWidth("500px");
        total.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout stats = new VerticalLayout(strStat, aglStat, lukStat, endStat, mpStat, npStat, total);
        stats.setAlignItems(FlexComponent.Alignment.CENTER);
        stats.setWidthFull();

        // Create page layout
        HorizontalLayout page = new HorizontalLayout(info, stats);
        page.setAlignItems(FlexComponent.Alignment.CENTER);
        page.addClassName("game-page");

        add(page);

        // Display end screen
        endScreen = new Dialog();

        endScreen.getElement().getStyle().set("--vaadin-dialog-overlay-width", "100%");
        endScreen.setWidth("900px");
        endScreen.setCloseOnEsc(false);
        endScreen.setCloseOnOutsideClick(false);
        endScreen.addClassName("game-dialog");

        // Display final score
        finalScore = new Span("Final Score: ");
        finalScore.getStyle().set("font-size", "25px");

        // Display end screen parameter summary
        VerticalLayout endStrDisplay = createParameterSummaryLayout(endStrSummary, strImageSummary);
        VerticalLayout endAglDisplay = createParameterSummaryLayout(endAglSummary, aglImageSummary);
        VerticalLayout endLukDisplay = createParameterSummaryLayout(endLukSummary, lukImageSummary);
        VerticalLayout endEndDisplay = createParameterSummaryLayout(endEndSummary, endImageSummary);
        VerticalLayout endMpDisplay = createParameterSummaryLayout(endMpSummary, mpImageSummary);
        VerticalLayout endNpDisplay = createParameterSummaryLayout(endNpSummary, npImageSummary);

        HorizontalLayout endSummaryDisplay = new HorizontalLayout(endStrDisplay, endAglDisplay, endLukDisplay, endEndDisplay, endMpDisplay, endNpDisplay);

        endSummaryDisplay.setAlignItems(FlexComponent.Alignment.CENTER);
        endSummaryDisplay.setSpacing(false);
        endSummaryDisplay.setSpacing(false);

        VerticalLayout endScoreSummary = new VerticalLayout(finalScore, endSummaryDisplay);
        endScoreSummary.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        endScoreSummary.setAlignItems(FlexComponent.Alignment.CENTER);
        endScoreSummary.setSpacing(false);
        endScoreSummary.setPadding(false);

        // Display theoretical high score
        theorereticalBestScore = new Span("Best Possible Score: ");
        theorereticalBestScore.getStyle().set("font-size", "25px");

        // Display end screen theoretical high score summary
        VerticalLayout bestStrDisplay = createParameterSummaryLayout(bestStrSummary, bestStrImageSummary);
        VerticalLayout bestAglDisplay = createParameterSummaryLayout(bestAglSummary, bestAglImageSummary);
        VerticalLayout bestLukDisplay = createParameterSummaryLayout(bestLukSummary, bestLukImageSummary);
        VerticalLayout bestEndDisplay = createParameterSummaryLayout(bestEndSummary, bestEndImageSummary);
        VerticalLayout bestMpDisplay = createParameterSummaryLayout(bestMpSummary, bestMpImageSummary);
        VerticalLayout bestNpDisplay = createParameterSummaryLayout(bestNpSummary, bestNpImageSummary);

        HorizontalLayout bestSummaryDisplay = new HorizontalLayout(bestStrDisplay, bestAglDisplay, bestLukDisplay, bestEndDisplay, bestMpDisplay, bestNpDisplay);

        bestSummaryDisplay.setAlignItems(FlexComponent.Alignment.CENTER);
        bestSummaryDisplay.setSpacing(false);
        bestSummaryDisplay.setSpacing(false);

        VerticalLayout bestScoreSummary = new VerticalLayout(theorereticalBestScore, bestSummaryDisplay);
        bestScoreSummary.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        bestScoreSummary.setAlignItems(FlexComponent.Alignment.CENTER);
        bestScoreSummary.setSpacing(false);
        bestScoreSummary.setPadding(false);

        // Display restart button
        Button restart = new Button("Restart");

        restart.addClickListener(event -> {
            UI.getCurrent().refreshCurrentRoute(false);
            resetGame();
            endScreen.close();
        });

        restart.getStyle().set("font-size", "30px").set("background-color", "#1a1a1a");
        restart.setWidth("400px");
        restart.setHeight("55px");

        // Display home button
        Button home = new Button("Home");

        home.addClickListener(event -> {
            UI.getCurrent().navigate("");
            resetGame();
            endScreen.close();
        });

        home.getStyle().set("font-size", "30px").set("background-color", "#1a1a1a");
        home.setWidth("400px");
        home.setHeight("55px");

        // Create end screen layout
        HorizontalLayout navigate = new HorizontalLayout(restart, home);
        VerticalLayout endStats = new VerticalLayout(endScoreSummary, bestScoreSummary, navigate);

        endStats.setAlignItems(FlexComponent.Alignment.CENTER);
        endStats.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        endScreen.add(endStats);

        // Set full screen
        setSizeFull();
        addClassName("main-view");
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }
}