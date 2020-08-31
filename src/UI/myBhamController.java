package UI;

import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ProgressBar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class myBhamController implements Initializable {

    // UI position
    double xOffset,yOffset;

    double progressNum = 10;
    // static DoubleProperty barUpdater = new SimpleDoubleProperty(.0);

    private int start = 10;

    private IntegerProperty timeSeconds = new SimpleIntegerProperty(start* 100);

    @FXML
    private AnchorPane background;

    @FXML
    private Button handlebtn;

    @FXML
    private ProgressBar loadingBar;

    public static ProgressBar stateProgressBar;

    @FXML
    public void handlebtnClick(javafx.event.ActionEvent actionEvent) {
        progressNum += 1;
        loadingBar.setProgress(progressNum);
        makeFadeOut();
    }

    /**
     * Fadeout AnchorPane
     */
    private void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(background);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    loadNextScene();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        fadeTransition.play();
    }

    /**
     * Fadeout AnchorPane
     */
    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(background);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * Load new scene
     * @throws IOException
     */
    private void loadNextScene() throws IOException {
        makeFadeIn();
        Parent secondView;
        secondView = (AnchorPane)FXMLLoader.load(getClass().getResource("uiLogin.fxml"));

        Scene loginScene = new Scene(secondView);
        Stage window = (Stage) background.getScene().getWindow();
        loginScene.setFill(Color.TRANSPARENT);
        window.setScene(loginScene);

        // Mouse Dragged
        secondView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        secondView.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setX(event.getScreenX() - xOffset);
                window.setY(event.getScreenY() - yOffset);
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //progressNum += 0.3;

        loadingBar.progressProperty().bind(timeSeconds.divide(progressNum* 100.0).subtract(1).multiply(-1));
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(2500));
        fadeTransition.setNode(background);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Thread.sleep(1000);
                    loadNextScene();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        fadeTransition.play();
    }// end initialize


}
