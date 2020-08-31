/**
 * @author Meng-Han, Tsai
 * @Date March19,2020
 */
package UI;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.ClientDemo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    // UI position
    double xOffset,yOffset;
    // Limit login times
    private int totalAttempts = 5;

    //public static ClientDemo client = new ClientDemo();

    @FXML
    private AnchorPane loginBg;

    @FXML
    private TextField inpName;

    @FXML
    private Label errorMsg;

    @FXML
    private PasswordField inpPassword;

    @FXML
    private Button btnSingIn, btnAdd, canBtn;

    /**
     * register a new account
     * @param event
     * @throws IOException
     */
    public void actionSignUp(ActionEvent event) throws IOException {

        Parent chatSignUp =  FXMLLoader.load(getClass().getResource("uiChatSingUp.fxml"));
        Scene chatWindowScene = new Scene(chatSignUp);
        System.out.println("window test");

        // get Stage information
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

        window.setScene(chatWindowScene);
        chatWindowScene.setFill(Color.TRANSPARENT);
        window.show();

        // Mouse Dragged
        chatSignUp.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        chatSignUp.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setX(event.getScreenX() - xOffset);
                window.setY(event.getScreenY() - yOffset);
            }
        });


        System.out.println(inpName.getText());
        System.out.println(inpPassword.getText());
    }


    // If user have account and correct
    public void actionSignIn(ActionEvent event) throws IOException {

        if(inpPassword.getText().isEmpty()||inpName.getText().isEmpty()){
            errorMsg.setText("");
            errorMsg.setText("Please enter username and password!");
            errorMsg.setTextFill(Color.rgb(153, 18, 180));
        }

        boolean logins = ClientDemo.login(inpName.getText(),inpPassword.getText());

        if(totalAttempts != 0) {
            if (logins == true) {
                Parent chatWindowParent = FXMLLoader.load(getClass().getResource("uiChatWindow.fxml"));
                Scene chatWindowScene = new Scene(chatWindowParent);
                System.out.println("window test");

                // get Stage information
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(chatWindowScene);
                chatWindowScene.setFill(Color.TRANSPARENT);
                window.show();

                // Mouse Dragged
                chatWindowParent.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

                chatWindowParent.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        window.setX(event.getScreenX() - xOffset);
                        window.setY(event.getScreenY() - yOffset);
                    }
                });
            } else {
                errorMsg.setText("Woops! password or username incorrect");
                errorMsg.setTextFill(Color.rgb(210, 39, 30));
                // login failed
                totalAttempts--;
            }// end login if
        } else{
            errorMsg.setText("");
            errorMsg.setText("Failed login to many times, try again later.");
            errorMsg.setTextFill(Color.rgb(153, 18, 180));
        }

        System.out.println(totalAttempts);
        System.out.println(inpName.getText());
        System.out.println(inpPassword.getText());
    }


    /**
     * Close the window
     */
    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) canBtn.getScene().getWindow();
        // do what you have to do
        stage.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginBg.setOpacity(0);
        makeFadeInTransition();
    }

    /**
     * Fadein the new Scene
     */
    private void makeFadeInTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(loginBg);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
