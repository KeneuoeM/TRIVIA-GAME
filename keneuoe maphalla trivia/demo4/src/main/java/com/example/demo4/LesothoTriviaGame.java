package com.example.demo4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LesothoTriviaGame extends Application {

    private int score = 0;
    private int questionIndex = 0;

    private final String[] questions = {
            "What is the clan of the founder of Basotho nation?",
            "What is the highest waterfall in Lesotho?",
            "Which dam is the biggest in Lesotho?",
            "Who is the founder of Basotho nation?",
            "What is the traditional Basotho clothing?"
    };

    private final String[] correctAnswers = {
            "Mokoena",
            "Maletsunyane Falls",
            "Katse Dam",
            "King Moshoeshoe I",
            "Mokorotlo oa Basotho"
    };

    private final String[][] options = {
            {"Mohlakoana", "Mosia", "Mokoena", "Lekholokoe"},
            {"Maletsunyane Falls", "Qhoasing Falls", "Ketane Falls", "Ribaneng Falls"},
            {"Mohale Dam", "Katse Dam", "Polihali Dam", "Metolong Dam"},
            {"Queen Elizabeth", "King Mswati", "Shaka Zulu", "King Moshoeshoe I"},
            {"Jeans", "Mokorotlo oa Basotho", "African print", "Delela"}
    };

    // File paths for images and videos
    private final String[] mediaFiles = {
            "images/Crocodile.jpg",
            "images/Falls.jpg",
            "images/Katse.jpg",
            "images/King Moshoeshoe1.jpg",
            "images/Mokorotlo oa Basotho.jpg"
    };

    private final String[] videoFiles = {
            "videos/1.mp4",
            "videos/2.mp4",
            "videos/3.mp4",
            "videos/4.mp4",
            "videos/5.mp4"
    };

    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lesotho Trivia Game");

        MediaView mediaView = new MediaView();

        // Set the MediaView size to fill the scene
        mediaView.setFitWidth(500);
        mediaView.setFitHeight(500);

        Label questionLabel = new Label();
        ImageView imageView = new ImageView();

        Label videoInfoLabel = new Label(); // Added label for video information
        Label feedbackLabel = new Label();

        Button[] optionButtons = new Button[4];
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new Button();
            optionButtons[i].setStyle("-fx-font-size: 16px; -fx-padding: 8px 16px; -fx-background-color: #154360; -fx-text-fill: white;");
            int finalI1 = i;
            optionButtons[i].setOnMouseEntered(e -> optionButtons[finalI1].setStyle("-fx-background-color: #154360; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));
            optionButtons[i].setOnMouseExited(e -> optionButtons[finalI1].setStyle("-fx-background-color: #154360; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));
        }

        Button nextButton = new Button("Next Question");
        nextButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-color: #154360; -fx-text-fill: white;");
        nextButton.setOnMouseEntered(e -> nextButton.setStyle("-fx-background-color: #154360; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));
        nextButton.setOnMouseExited(e -> nextButton.setStyle("-fx-background-color: #154360; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-text-fill: white;"));

        nextButton.setOnAction(e -> {
            if (questionIndex < questions.length - 1) {
                questionIndex++;
                showQuestion(questionLabel, imageView, optionButtons, feedbackLabel, mediaView, videoInfoLabel, nextButton);
            } else {
                showScore(primaryStage);
            }
        });

        VBox questionBox = new VBox(10);
        questionBox.setAlignment(Pos.CENTER);
        questionBox.getChildren().addAll(questionLabel, imageView, mediaView, videoInfoLabel, feedbackLabel); // Added videoInfoLabel

        HBox optionsBox = new HBox(10);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.getChildren().addAll(optionButtons);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(nextButton);

        BorderPane layout = new BorderPane();
        layout.setTop(questionBox);
        layout.setCenter(optionsBox);
        layout.setBottom(buttonBox);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color:#99A3A4;");

        Scene scene = new Scene(layout, 600, 600);

        Button startButton = new Button("Start");
        startButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-color: #3498DB; -fx-text-fill: white;");
        startButton.setOnMouseEntered(e -> startButton.setStyle("-fx-background-color: #3498DB; -fx-font-size: 40px; -fx-padding: 10px 20px; -fx-text-fill: white;"));
        startButton.setOnMouseExited(e -> startButton.setStyle("-fx-background-color: #3498DB; -fx-font-size: 40px; -fx-padding: 10px 20px; -fx-text-fill: white;"));
        startButton.setOnAction(e -> {
            questionIndex = 0;
            score = 0;
            showQuestion(questionLabel, imageView, optionButtons, feedbackLabel, mediaView, videoInfoLabel, nextButton);
            primaryStage.setScene(scene);
        });

        VBox welcomeBox = new VBox(10);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.getChildren().addAll(new Label("Welcome to Lesotho Trivia Game"), startButton);

        Scene welcomeScene = new Scene(welcomeBox, 600, 600);

        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    private void showQuestion(Label questionLabel, ImageView imageView, Button[] optionButtons, Label feedbackLabel, MediaView mediaView, Label videoInfoLabel, Button nextButton) {
        questionLabel.setText(questions[questionIndex]);

        // Load and resize the image
        Image image = new Image(getClass().getResourceAsStream(mediaFiles[questionIndex]));
        imageView.setImage(image);
        imageView.setFitWidth(100); // Set desired width
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true); // Preserve aspect ratio

        // Load the corresponding video
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop any existing media player
        }

        Media media = new Media(getClass().getResource(videoFiles[questionIndex]).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        // Stop the video after 10 seconds
        mediaPlayer.setStopTime(Duration.seconds(10));

        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setOnReady(() -> {
            // Get the video's width and height from metadata
            double videoWidth = mediaPlayer.getMedia().getWidth();
            double videoHeight = mediaPlayer.getMedia().getHeight();

            videoInfoLabel.setText("Video Size: " + 150 + "x" + 100); // Display width and height
        });

        // Set options
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[questionIndex][i]);
            optionButtons[i].setDisable(false); // Re-enable the button
            int index = i; // Capture the value of i
            optionButtons[i].setOnAction(e -> checkAnswer(optionButtons[index].getText(), optionButtons, feedbackLabel, nextButton));
        }

        // Initially disable "Next Question" until an answer is selected
        nextButton.setDisable(true);
    }

    private void checkAnswer(String selectedAnswer, Button[] optionButtons, Label feedbackLabel, Button nextButton) {
        String correctAnswer = correctAnswers[questionIndex];
        boolean isCorrect = selectedAnswer.equals(correctAnswer);

        feedbackLabel.setText(isCorrect ? "Correct" : "Wrong Answer");
        feedbackLabel.setTextFill(isCorrect ? Color.SKYBLUE : javafx.scene.paint.Color.RED);

        // Disable all option buttons
        for (Button button : optionButtons) {
            button.setDisable(true);
        }

        // Enable the "Next Question" button once an answer is selected
        nextButton.setDisable(false);

        // If correct, update the score
        if (isCorrect) {
            score++;
        }
    }

    private void showScore(Stage primaryStage) {
        Label scoreLabel = new Label("Your score: " + score + "/" + questions.length);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> primaryStage.close());

        VBox scoreBox = new VBox(10);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.getChildren().addAll(scoreLabel, closeButton);

        Scene scoreScene = new Scene(scoreBox, 300, 200);
        primaryStage.setScene(scoreScene);
    }

    public static void main(String[] args) {
        launch(args);
    }

}