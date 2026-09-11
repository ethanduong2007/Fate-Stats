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

    private Image strImage, aglImage, lukImage, endImage, mpImage, npImage;
    private Image strImageSummary, aglImageSummary, lukImageSummary, endImageSummary, mpImageSummary, npImageSummary;

    // Initialize tracking variables
    private boolean isShuffling = true;

    private boolean parameterClicked;

    private int servantCount = 0;
    private Set<String> usedServants = new HashSet<>();

    private Set<String> usedParameters = new HashSet<>();

    // Initialize scoring variables
    private Score scoreLogic = new Score();
    private Span scoreDisplay, multiplierDisplay;
    private Span finalScore;

    // Initialize end screen
    private Dialog endScreen;

    /**
     * Get all servant data from a file
     *
     * @param fileName is the name of the file
     * @throws Exception
     */
    private Parameter getData(String fileName) throws Exception {

        // Initialize variables
        Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(fileName));
        int rows = 0;
        int currentLine = 0;

        // Count number of lines
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            rows++;
        }

        // Reopen scanner
        scanner.close();
        scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(fileName));

        // Generate a random number
        Random number = new Random();
        int randomNumber = number.nextInt(rows);

        // Search for random servant
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Check for random servant
            if (currentLine == randomNumber) {
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

                // Create servant object
                scanner.close();
                return new Parameter(name, str, agl, luk, end, mp, np, image);
            }

            // Increase line count;
            currentLine++;
        }

        scanner.close();
        return null;
    }

    /** Creates a random servant
     *
     * @throws Exception
     */
    public void getRandomServant() throws Exception {
        // Display servant data
        servant = getData("servants.txt");

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
        span.setWidth("120px");
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
        image.setWidth("150px");
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
        verticalLayout.setWidthFull();
        verticalLayout.setSpacing(false);
        verticalLayout.getStyle().set("padding", "8px");
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
    private String getParameterValue(String parameter) {
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
            scoreLogic.addScore(getParameterValue(parameter));
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
            scoreLogic.calculateHighScore();
            finalScore.setText("Final Score: " + (int) scoreLogic.calculateScore());
            endScreen.open();
        }
    }

    /**
     * Resets the game
     */
    private void resetGame() {
        usedServants.clear();
        usedParameters.clear();
    }

    // Display
    public MainView() throws Exception {
        // Change background
        getStyle().set("background-image", "url('/images/background/background1.png')").set("background-size", "cover").set("background-position", "center-bottom").set("background-repeat", "no-repeat");

        // Extract data
        servant = getData("servants.txt");

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

        agl = createParameter("AGL");
        aglSummary = createParameter("AGL");
        endAglSummary = createParameterSummary("AGL");
        aglImage = createParameterImage();
        aglImageSummary = createParameterImageSummary();

        luk = createParameter("LUK");
        lukSummary = createParameter("LUK");
        endLukSummary = createParameterSummary("LUK");
        lukImage = createParameterImage();
        lukImageSummary = createParameterImageSummary();

        end = createParameter("END");
        endSummary = createParameter("END");
        endEndSummary = createParameterSummary("END");
        endImage = createParameterImage();
        endImageSummary = createParameterImageSummary();

        mp = createParameter("MP");
        mpSummary = createParameter("MP");
        endMpSummary = createParameterSummary("MP");
        mpImage = createParameterImage();
        mpImageSummary = createParameterImageSummary();

        np = createParameter("NP");
        npSummary = createParameter("NP");
        endNpSummary = createParameterSummary("NP");
        npImage = createParameterImage();
        npImageSummary = createParameterImageSummary();

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
                       while(usedServants.contains(getServantID(servant.getImage()))) {
                           servant = getData("servants.txt");
                       }

                       name.setText(servant.getName());
                       servantImage.setSrc("images/servants/" + servant.getImage());

                       UI.getCurrent().setPollInterval(-1);
                       shuffling.setText("Click to shuffle");
                       usedServants.add(getServantID(servant.getImage()));
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
        endScreen.setWidth("750px");
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

        HorizontalLayout endSummaryDisplay1 = new HorizontalLayout(endStrDisplay, endAglDisplay, endLukDisplay);
        HorizontalLayout endSummaryDisplay2 = new HorizontalLayout(endEndDisplay, endMpDisplay, endNpDisplay);

        endSummaryDisplay1.setAlignItems(FlexComponent.Alignment.CENTER);
        endSummaryDisplay1.setSpacing(false);
        endSummaryDisplay1.setPadding(false);

        endSummaryDisplay2.setAlignItems(FlexComponent.Alignment.CENTER);
        endSummaryDisplay2.setSpacing(false);
        endSummaryDisplay2.setPadding(false);

        VerticalLayout endSummaryDisplay = new VerticalLayout(endSummaryDisplay1, endSummaryDisplay2);
        endSummaryDisplay.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        endSummaryDisplay.setSpacing(false);
        endSummaryDisplay.setPadding(false);
        endSummaryDisplay.setWidthFull();
        endSummaryDisplay.setAlignItems(FlexComponent.Alignment.CENTER);

        // Display restart button
        Button restart = new Button("Restart");

        restart.addClickListener(event -> {
            UI.getCurrent().refreshCurrentRoute(false);
            resetGame();
            endScreen.close();
        });

        restart.getStyle().set("font-size", "30px").set("background-color", "#1a1a1a");
        restart.setWidth("300px");
        restart.setHeight("55px");

        // Display home button
        Button home = new Button("Home");

        home.addClickListener(event -> {
            UI.getCurrent().navigate("");
            resetGame();
            endScreen.close();
        });

        home.getStyle().set("font-size", "30px").set("background-color", "#1a1a1a");
        home.setWidth("300px");
        home.setHeight("55px");

        // Create end screen layout
        HorizontalLayout navigate = new HorizontalLayout(restart, home);
        VerticalLayout endStats = new VerticalLayout(finalScore, endSummaryDisplay, navigate);

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