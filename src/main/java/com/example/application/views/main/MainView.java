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

import java.util.Random;
import java.util.Scanner;

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
    private Span bestStrSummary, bestAglSummary, bestLukSummary, bestEndSummary, bestMpSummary, bestNpSummary;

    private Image strImage, aglImage, lukImage, endImage, mpImage, npImage;
    private Image strImageSummary, aglImageSummary, lukImageSummary, endImageSummary, mpImageSummary, npImageSummary;
    private Image bestStrImageSummary, bestAglImageSummary, bestLukImageSummary, bestEndImageSummary, bestMpImageSummary, bestNpImageSummary;

    // Initialize tracking variables
    private boolean isShuffling = true;

    private boolean parameterClicked;

    private Random number;
    private ArrayList<Parameter> servants;

    private GameState gameState = new GameState();

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
        rank = rank.replace("+", "").replace("-", "");

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
        span.getStyle().set("font-size", "18px").set("padding-left", "15px");
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
        span.getStyle().set("font-size", "18px").set("text-align", "center");
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
        verticalLayout.getStyle().set("padding-left", "3px").set("padding-right", "3px").set("padding-top", "0px");
        return verticalLayout;
    }

    /**
     * Add border highlights when parameter is clickable
     * @param parameter is the parameter of a servant
     * @param info is the name and image of a servant
     * @param paramaeterRows is the list of parameters
     */
    private void clickableParameters(String parameter, VerticalLayout info, HorizontalLayout[] paramaeterRows) {
        if(!isShuffling && !parameterClicked && !parameterUsed(parameter)) {
            parameterClicked(parameter);

            shuffling.setText("Click to shuffle");

            for (int i = 0; i < paramaeterRows.length; i++) {
                paramaeterRows[i].removeClassName("highlight");
            }

            info.addClassName("highlight");
        }
    }

    /**
     * Checks if the parameter is used
     *
     * @param parameter is the parameter of the servant
     * @return false if the parameter is used
     */
    private boolean parameterUsed(String parameter) {
        return gameState.hasUsedParameter(parameter);
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
                gameState.useParameter("STR");
            } else if(parameter.equals("AGL")) {
                aglSummary.setText("AGL: " + servant.getAGL());
                endAglSummary.setText("AGL: " + servant.getAGL());
                aglImageSummary.setSrc("images/servants/" + servant.getImage());
                gameState.useParameter("AGL");
            } else if(parameter.equals("LUK")) {
                lukSummary.setText("LUK: " + servant.getLUK());
                endLukSummary.setText("LUK: " + servant.getLUK());
                lukImageSummary.setSrc("images/servants/" + servant.getImage());
                gameState.useParameter("LUK");
            } else if(parameter.equals("END")) {
                endSummary.setText("END: " + servant.getEND());
                endEndSummary.setText("END: " + servant.getEND());
                endImageSummary.setSrc("images/servants/" + servant.getImage());
                gameState.useParameter("END");
            } else if(parameter.equals("MP")) {
                mpSummary.setText("MP: " + servant.getMP());
                endMpSummary.setText("MP: " + servant.getMP());
                mpImageSummary.setSrc("images/servants/" + servant.getImage());
                gameState.useParameter("MP");
            } else if(parameter.equals("NP")) {
                npSummary.setText("NP: " + servant.getNP());
                endNpSummary.setText("NP: " + servant.getNP());
                npImageSummary.setSrc("images/servants/" + servant.getImage());
                gameState.useParameter("NP");
            }

            parameterClicked = true;

            endGame();
        }
    }

    /**
     * Starts the shuffling animation
     */
    private void startShuffling() {
        String[] images = servants.stream().filter(s -> !gameState.hasUsedServant(getServantID(s.getImage())))
                        .map(s -> "/images/servants/" + s.getImage())
                        .toArray(String[]::new);

        String[] names = servants.stream().filter(s -> !gameState.hasUsedServant(getServantID(s.getImage())))
                .map(Parameter::getName)
                .toArray(String[]::new);

        servantImage.getElement().executeJs("""
                const images = $0;
                const names = $1;
                const nameElement = $2;
                
                const entries = images.map((image, index) => ({
                    image,
                    name: names[index]
                }));
                
                for(let i = images.length - 1; i > 0; i--) {
                    const j = Math.floor(Math.random() * (i + 1));
                    [entries[i], entries[j]] = [entries[j], entries[i]];
                }
                
                let index = 0;
                clearInterval(this.shuffleTimer);
                
                this.shuffleTimer = setInterval(() => {
                    this.src = entries[index].image;
                    nameElement.textContent = entries[index].name;
                    index = (index + 1) % entries.length;
                }, 50);
                """, (Object) images, (Object) names, name.getElement());
    }

    /**
     * Gets the stopped images data
     * @param imageUrl is the url of the image
     */
    private void useStoppedimage(String imageUrl) {
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        servant = servants.stream().filter(s -> s.getImage().equals(imageName)).findFirst().orElseThrow();
    }

    /**
     * Ends the game
     */
    private void endGame() {
        if(gameState.isFinished()) {
            finalScore.setText("Final Score: " + (int) scoreLogic.calculateScore());
            theorereticalBestScore.setText("Best Possible Score: " + (int) theoreticalScoreLogic.calculateTheoreticalHighScore(gameState.getSelectedServants()));

            String[] bestAssignment = theoreticalScoreLogic.getBestAssignment();

            for(int i = 0; i < bestAssignment.length; i++) {
                if(bestAssignment[i].equals("STR")) {
                    bestStrSummary.setText("STR: " + gameState.getSelectedServants().get(i).getParameter("STR"));
                    bestStrImageSummary.setSrc("images/servants/" + gameState.getSelectedServants().get(i).getImage());
                } else if(bestAssignment[i].equals("AGL")) {
                    bestAglSummary.setText("AGL: " + gameState.getSelectedServants().get(i).getParameter("AGL"));
                    bestAglImageSummary.setSrc("images/servants/" + gameState.getSelectedServants().get(i).getImage());
                } else if(bestAssignment[i].equals("LUK")) {
                    bestLukSummary.setText("LUK: " + gameState.getSelectedServants().get(i).getParameter("LUK"));
                    bestLukImageSummary.setSrc("images/servants/" + gameState.getSelectedServants().get(i).getImage());
                } else if(bestAssignment[i].equals("END")) {
                    bestEndSummary.setText("END: " + gameState.getSelectedServants().get(i).getParameter("END"));
                    bestEndImageSummary.setSrc("images/servants/" + gameState.getSelectedServants().get(i).getImage());
                } else if(bestAssignment[i].equals("MP")) {
                    bestMpSummary.setText("MP: " + gameState.getSelectedServants().get(i).getParameter("MP"));
                    bestMpImageSummary.setSrc("images/servants/" + gameState.getSelectedServants().get(i).getImage());
                } else if(bestAssignment[i].equals("NP")) {
                    bestNpSummary.setText("NP: " + gameState.getSelectedServants().get(i).getParameter("NP"));
                    bestNpImageSummary.setSrc("images/servants/" + gameState.getSelectedServants().get(i).getImage());
                }
            }

            scoreLogic.calculateHighScore();

            endScreen.open();
        }
    }

    // Display
    public MainView() throws Exception {
        // Change background
        getStyle().set("background-image", "url('/images/background/background1.png')").set("background-size", "cover").set("background-position", "center-bottom").set("background-repeat", "no-repeat");

        // Load servants
        servantLoad("servants.txt");
        number = new Random();
        servant = servants.get(number.nextInt(servants.size()));

        // Display servant
        name = new Span(servant.getName());
        name.getStyle().set("font-size", "23px");

        shuffling = new Span("Click to stop");
        shuffling.getStyle().set("font-size", "16px").set("opacity", "0.8").set("margin-top", "-5px");

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
        bestAglSummary = createParameterSummary("AGL");
        bestAglImageSummary = createParameterImageSummary();

        luk = createParameter("LUK");
        lukSummary = createParameter("LUK");
        endLukSummary = createParameterSummary("LUK");
        lukImage = createParameterImage();
        lukImageSummary = createParameterImageSummary();
        bestLukSummary = createParameterSummary("LUK");
        bestLukImageSummary = createParameterImageSummary();

        end = createParameter("END");
        endSummary = createParameter("END");
        endEndSummary = createParameterSummary("END");
        endImage = createParameterImage();
        endImageSummary = createParameterImageSummary();
        bestEndSummary = createParameterSummary("END");
        bestEndImageSummary = createParameterImageSummary();

        mp = createParameter("MP");
        mpSummary = createParameter("MP");
        endMpSummary = createParameterSummary("MP");
        mpImage = createParameterImage();
        mpImageSummary = createParameterImageSummary();
        bestMpSummary = createParameterSummary("MP");
        bestMpImageSummary = createParameterImageSummary();

        np = createParameter("NP");
        npSummary = createParameter("NP");
        endNpSummary = createParameterSummary("NP");
        npImage = createParameterImage();
        npImageSummary = createParameterImageSummary();
        bestNpSummary = createParameterSummary("NP");
        bestNpImageSummary = createParameterImageSummary();

        // Display score
        scoreDisplay = new Span("Score " + scoreLogic.getScore());
        scoreDisplay.getStyle().set("font-size", "18px").set("text-align", "center");

        multiplierDisplay = new Span("Multiplier " + scoreLogic.getMultiplier() + "x");
        multiplierDisplay.getStyle().set("font-size", "18px").set("text-align", "center");

        // Display parameter summary
        HorizontalLayout parameterDisplay1 = new HorizontalLayout(strSummary, aglSummary, lukSummary);
        HorizontalLayout parameterDisplay2 = new HorizontalLayout(endSummary, mpSummary, npSummary);

        parameterDisplay1.setSpacing(false);
        parameterDisplay2.setSpacing(false);

        // Display parameter summary layout
        VerticalLayout parameterDisplay = new VerticalLayout(parameterDisplay1, parameterDisplay2);
        parameterDisplay.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        parameterDisplay.setMaxWidth("325px");
        parameterDisplay.setAlignItems(FlexComponent.Alignment.CENTER);

        // Create servant layout
        VerticalLayout info = new VerticalLayout(name, shuffling, servantImage);
        info.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "3px");
        info.setAlignItems(FlexComponent.Alignment.CENTER);
        info.setMaxWidth("500px");
        info.setSpacing(false);
        info.setPadding(false);

        // Create parameter layouts
        HorizontalLayout strStat = createParameterLayout(str, strImage);
        HorizontalLayout aglStat = createParameterLayout(agl, aglImage);
        HorizontalLayout lukStat = createParameterLayout(luk, lukImage);
        HorizontalLayout endStat = createParameterLayout(end, endImage);
        HorizontalLayout mpStat = createParameterLayout(mp, mpImage);
        HorizontalLayout npStat = createParameterLayout(np, npImage);

        HorizontalLayout[] parameterRows = {strStat, aglStat, lukStat, endStat, mpStat, npStat};

        // Shuffle to next servant
        parameterClicked = true;
        info.addClassName("highlight");

        info.addClickListener(event -> {
            try {
                if(parameterClicked) {
                    // Stop shuffling
                    if(isShuffling) {
                        servantImage.getElement().executeJs("""
                                clearInterval(this.shuffleTimer);
                                return this.getAttribute("src");
                                """)
                                .then(String.class, imageUrl -> {
                                    useStoppedimage(imageUrl);

                                    info.removeClassName("highlight");
                                    if(!parameterUsed("STR")) { strStat.addClassName("highlight"); }
                                    if(!parameterUsed("AGL")) { aglStat.addClassName("highlight"); }
                                    if(!parameterUsed("LUK")) { lukStat.addClassName("highlight"); }
                                    if(!parameterUsed("END")) { endStat.addClassName("highlight"); }
                                    if(!parameterUsed("MP")) { mpStat.addClassName("highlight"); }
                                    if(!parameterUsed("NP")) { npStat.addClassName("highlight"); }

                                    number = new Random();

                                    while(gameState.hasUsedServant(getServantID(servant.getImage()))) {
                                        servant = servants.get(number.nextInt(servants.size()));
                                    }

                                    name.setText(servant.getName());
                                    shuffling.setText("Click a parameter");
                                    servantImage.setSrc("images/servants/" + servant.getImage());

                                    gameState.recordServant(servant, getServantID(servant.getImage()));

                                    isShuffling = !isShuffling;
                                    parameterClicked = !parameterClicked;
                                });
                        // Continue shuffling
                    } else {
                        getRandomServant();
                        startShuffling();

                        info.addClassName("highlight");
                        strStat.removeClassName("highlight");
                        aglStat.removeClassName("highlight");
                        lukStat.removeClassName("highlight");
                        endStat.removeClassName("highlight");
                        mpStat.removeClassName("highlight");
                        npStat.removeClassName("highlight");

                        shuffling.setText("Click to stop");
                        isShuffling = !isShuffling;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Add parameter click listeners
        strStat.addClickListener(event -> {clickableParameters("STR", info, parameterRows);});

        aglStat.addClickListener(event -> {clickableParameters("AGL", info, parameterRows);});

        lukStat.addClickListener(event -> {clickableParameters("LUK", info, parameterRows);});

        endStat.addClickListener(event -> {clickableParameters("END", info, parameterRows);});

        mpStat.addClickListener(event -> {clickableParameters("MP", info, parameterRows);});

        npStat.addClickListener(event -> {clickableParameters("NP", info, parameterRows);});

        HorizontalLayout total = new HorizontalLayout(parameterDisplay, scoreDisplay, multiplierDisplay);
        total.setMaxWidth("500px");
        total.setWidthFull();
        total.setAlignItems(FlexComponent.Alignment.CENTER);
        total.getStyle().set("column-gap", "5px");

        VerticalLayout stats = new VerticalLayout(strStat, aglStat, lukStat, endStat, mpStat, npStat, total);
        stats.setAlignItems(FlexComponent.Alignment.CENTER);
        stats.setSpacing(false);
        stats.setWidth("500px");
        stats.getStyle().set("row-gap", "18px");

        for (int i = 0; i < parameterRows.length; i++) {
            parameterRows[i].setWidthFull();
        }

        // Create page layout
        HorizontalLayout page = new HorizontalLayout(info, stats);
        page.setAlignItems(FlexComponent.Alignment.CENTER);
        page.addClassName("game-page");
        page.setSpacing(false);
        page.getStyle().set("column-gap", "4px");

        add(page);

        // Start shuffling
        startShuffling();

        // Display end screen
        endScreen = new Dialog();

        endScreen.getElement().getStyle().set("--vaadin-dialog-overlay-width", "100%");
        endScreen.setWidth("865px");
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

        VerticalLayout bestScoreSummary = new VerticalLayout(theorereticalBestScore, bestSummaryDisplay);
        bestScoreSummary.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        bestScoreSummary.setAlignItems(FlexComponent.Alignment.CENTER);
        bestScoreSummary.setSpacing(false);
        bestScoreSummary.setPadding(false);

        // Display restart button
        Button restart = new Button("Restart");

        restart.addClickListener(event -> {
            UI.getCurrent().refreshCurrentRoute(false);
            gameState.reset();
            endScreen.close();
        });

        restart.getStyle().set("font-size", "30px").set("background-color", "#141414").set("border", "1px solid white");
        restart.setWidth("380px");
        restart.setHeight("55px");
        restart.addClassName("highlight");

        // Display home button
        Button home = new Button("Home");

        home.addClickListener(event -> {
            UI.getCurrent().navigate("");
            gameState.reset();
            endScreen.close();
        });

        home.getStyle().set("font-size", "30px").set("background-color", "#141414").set("border", "1px solid white");
        home.setWidth("380px");
        home.setHeight("55px");
        home.addClassName("highlight");

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
