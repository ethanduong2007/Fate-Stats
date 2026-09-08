package com.example.application.views.main;

/**
 * @author Ethan Duong
 */

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.button.Button;

@PageTitle("Fate/Stats")
@Route("")
public class HomeView extends VerticalLayout {
    // Initialize variables text
    private Span title;
    private Dialog howToPlayScreen;
    private Span instructions;
    private Span score, scoringA, scoringB, scoringC, scoringD, scoringE, scoringEX;
    private Dialog creditsScreen;
    private Span creator;
    private Span highScore;

    private Span createScoringSpan(String score) {
        Span span = new Span(score + "+++: +200\n" + score + "++: +150\n" + score + "+: +100\n" + score + ": +50");
        span.getStyle().set("font-size", "20px").set("text-align", "center").set("white-space", "pre-line");
        span.setWidth("175px");
        return span;
    }

    public HomeView() {
        // Change background
        getStyle().set("background-image", "url('/images/background/background1.png')").set("background-size", "cover").set("background-position", "center-bottom").set("background-repeat", "no-repeat");

        // Display title page
        title = new Span("Fatetle");
        title.getStyle().set("font-size", "100px").set("border", "1px solid white").set("border-radius", "10px").set("padding", "10px");

        // Display play button
        Button play = new Button("Play");

        play.addClickListener(event -> {
            UI.getCurrent().navigate("game");
        });

        play.getStyle().set("font-size", "50px").set("background-color", "#1a1a1a");
        play.setWidth("300px");
        play.setHeight("90px");

        // Display how to play button
        Button howToPlay = new Button("How to Play");

        howToPlay.addClickListener(event -> {
            howToPlayScreen.open();
        });

        howToPlay.getStyle().set("font-size", "50px").set("background-color", "#1a1a1a");
        howToPlay.setWidth("300px");
        howToPlay.setHeight("90px");


        // Display how to play screen
        howToPlayScreen = new Dialog();

        howToPlayScreen.getElement().getStyle().set("--vaadin-dialog-overlay-width", "100%");
        howToPlayScreen.setWidth("750px");
        howToPlayScreen.setCloseOnEsc(false);
        howToPlayScreen.setCloseOnOutsideClick(false);
        howToPlayScreen.addClassName("game-dialog");

        instructions = new Span("6 servants, 6 parameters. Choose the highest parameter on a servant, and aim for a high score! You can only choose each parameter once so think strategically.");
        instructions.getStyle().set("font-size", "20px").set("text-align", "center");

        score = new Span("Scoring");
        score.getStyle().set("font-size", "20px").set("text-align", "center");

        scoringA = createScoringSpan("A");
        scoringB = createScoringSpan("B");
        scoringC = createScoringSpan("C");
        scoringD = createScoringSpan("D");
        scoringE = createScoringSpan("E");

        scoringEX = new Span("EX: +0.5x multiplier\n" + "?: 0");
        scoringEX.getStyle().set("font-size", "20px").set("text-align", "center").set("white-space", "pre-line").set("display", "flex").set("align-items", "center");

        // Display close button for how to play screen
        Button close1 = new Button("Close");

        close1.addClickListener(event -> {
            howToPlayScreen.close();
        });

        close1.getStyle().set("font-size", "30px").set("background-color", "#1a1a1a");
        close1.setWidth("300px");
        close1.setHeight("55px");

        // Create how to play screen layout
        HorizontalLayout scoring1 = new HorizontalLayout(scoringA, scoringB, scoringC);
        scoring1.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);

        HorizontalLayout scoring2 = new HorizontalLayout(scoringD, scoringE, scoringEX);
        scoring2.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);

        VerticalLayout explanation = new VerticalLayout(instructions, score, scoring1, scoring2);
        explanation.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        explanation.setAlignItems(FlexComponent.Alignment.CENTER);
        explanation.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        VerticalLayout howToPlayFinal = new VerticalLayout(explanation, close1);

        howToPlayFinal.setAlignItems(FlexComponent.Alignment.CENTER);
        howToPlayFinal.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        howToPlayScreen.add(howToPlayFinal);

        // Display credits button
        Button credits = new Button("Credits");

        credits.addClickListener(event -> {
            creditsScreen.open();
        });

        credits.getStyle().set("font-size", "50px").set("background-color", "#1a1a1a");
        credits.setWidth("300px");
        credits.setHeight("90px");

        // Display credits screen
        creditsScreen = new Dialog();

        creditsScreen.getElement().getStyle().set("--vaadin-dialog-overlay-width", "100%");
        creditsScreen.setWidth("750px");
        creditsScreen.setCloseOnEsc(false);
        creditsScreen.setCloseOnOutsideClick(false);
        creditsScreen.addClassName("game-dialog");

        instructions = new Span("6 servants, 6 parameters. Choose the highest parameter on a servant, and aim for a high score! You can only choose each parameter once so think strategically.");

        // Display atlas button
        Button atlas = new Button("Atlas Academy DB");

        atlas.addClickListener(event -> {
            UI.getCurrent().getPage().open("https://apps.atlasacademy.io/db/", "_blank");
        });

        atlas.getStyle().set("font-size", "30px").set("background-color", "#1a1a1a");
        atlas.setWidth("500px");
        atlas.setHeight("70px");

        // Display wiki button
        Button wiki = new Button("Fate/Grand Order Wiki");

        wiki.addClickListener(event -> {
            UI.getCurrent().getPage().open("https://fategrandorder.fandom.com/wiki/Fate/Grand_Order_Wikia", "_blank");
        });

        wiki.getStyle().set("font-size", "30px").set("background-color", "#1a1a1a");
        wiki.setWidth("500px");
        wiki.setHeight("70px");

        // Display creator
        creator = new Span("Creator: Wish");
        creator.getStyle().set("font-size", "25px").set("text-align", "center");

        // Display close button for credits screen
        Button close2 = new Button("Close");

        close2.addClickListener(event -> {
            creditsScreen.close();
        });

        close2.getStyle().set("font-size", "30px").set("background-color", "#1a1a1a");
        close2.setWidth("300px");
        close2.setHeight("55px");

        // Create credits layout
        VerticalLayout information = new VerticalLayout(atlas, wiki, creator);
        information.getStyle().set("border", "1px solid white").set("border-radius", "10px").set("padding", "5px");
        information.setAlignItems(FlexComponent.Alignment.CENTER);
        information.setJustifyContentMode(FlexComponent.JustifyContentMode.EVENLY);

        VerticalLayout informationFinal = new VerticalLayout(information, close2);
        informationFinal.setAlignItems(FlexComponent.Alignment.CENTER);
        informationFinal.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        creditsScreen.add(informationFinal);

        // Display high score
        highScore = new Span("High Score: " + (int) Score.getHighScore());
        highScore.getStyle().set("font-size", "30px").set("text-align", "center").set("white-space", "normal").set("border", "1px solid white").set("border-radius", "10px").set("padding", "10px");

        // Create layout
        VerticalLayout titleScreen = new VerticalLayout(title, play, howToPlay, credits, highScore);
        titleScreen.setAlignItems(FlexComponent.Alignment.CENTER);

        add(titleScreen);

        // Set full screen
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
    }
}
