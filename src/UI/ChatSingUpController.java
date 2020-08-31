package UI;

import com.Personalinfor;
import dao.UserDao;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import server.ClientDemo;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChatSingUpController implements Initializable{
    // UI position
    double xOffset,yOffset;

    @FXML
    private AnchorPane singupBg;

    @FXML
    private TextField inpName, inpRelName, inpEmail, inpPhoneNum;

    @FXML
    private PasswordField inpPassword, inpPasswordMatch;

    @FXML
    private Button btnSingUp, canBtn;

    @FXML
    private RadioButton maleBtn, femaleBtn;


    /**
     * Let user fill the information and match it.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    public void register(ActionEvent event) throws SQLException, IOException {
        Window owner = ((Node)event.getTarget()).getScene().getWindow();

        String u_username = inpName.getText();
        String matchName;
        String u_password = inpPassword.getText();
        String u_passwordMatch = inpPasswordMatch.getText();
        String u_email = inpEmail.getText();
        String u_realname = inpRelName.getText();
        String u_loginid = null;
        int    u_gender  = 0;
        String u_phonenum = inpPhoneNum.getText();

        // Username if account already existed?
        if (inpName.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner,"Form Error!",
                    "Please enter your username");
            return;
        }

        if (inpRelName.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner,"Form Error!",
                    "Please enter your real name");
            return;
        }

        if (inpPassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner,"Form Error!",
                    "Please enter your password");
            return;
        }

        if (inpPasswordMatch.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner,"Form Error!",
                    "Please enter password to match");
            return;
        }

        if (inpEmail.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner,"Form Error!",
                    "Please enter your email");
            return;
        }

        if (inpPhoneNum.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner,"Form Error!",
                    "Please enter your phone number");
            return;
        }

        if(!maleBtn.isSelected() && !femaleBtn.isSelected()){
            showAlert(Alert.AlertType.ERROR, owner,"Form Error!",
                    "Please choose your gender");
            return;
        }

        if(maleBtn.isSelected()){
            u_gender = 0;
        }else{
            u_gender = 1;
        }

        if(!inpPassword.getText().equals(inpPasswordMatch.getText()) ){
            showAlert(Alert.AlertType.ERROR, owner,"Form Error!",
                    "Password doesn't match!");
            return;
        }else{
            System.out.println(u_gender);

            // Use ClientDemo
            ClientDemo.register(u_realname,u_username,u_password, u_gender, u_email,  u_phonenum);

            // register successful
            Parent chatWindowParent =  FXMLLoader.load(getClass().getResource("uiLogin.fxml"));
            Scene chatWindowScene = new Scene(chatWindowParent);
            chatWindowScene.setFill(Color.TRANSPARENT);

            // get Stage information
            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(chatWindowScene);
            window.show();
        }



        // Contact JDBC
        UserDao userDao = new UserDao();
        //insert into u_personalinfor(u_loginid,u_nickname,u_realname,u_password,u_gender,u_email,u_phonenum)values(?,?,?,?,?,?,?)
        Personalinfor personalinfor = new Personalinfor();
        //userDao.insertPersonalinfor(u_loginid,u_nickname,u_realname,u_password,u_gender,u_email,u_phonenum);
        //if(usernameCounter.equals(username)).

    }

    public void test(ActionEvent event) throws IOException {

        String password = "1234";
        Parent chatWindowParent =  FXMLLoader.load(getClass().getClassLoader().getResource("uiLogin.fxml"));
        Scene chatWindowScene = new Scene(chatWindowParent);
        System.out.println("window test");

        // get Stage information
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(chatWindowScene);
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

        System.out.println(inpName.getText());
        System.out.println(inpPassword.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup gender = new ToggleGroup();
        maleBtn.setToggleGroup(gender);
        femaleBtn.setToggleGroup(gender);

        singupBg.setOpacity(0);
        makeFadeInTransition();
    }

    /**
     * Fadein the new Scene
     */
    private void makeFadeInTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(singupBg);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * Show Alter
     * @param alertType
     * @param owner
     * @param title
     * @param message
     */
    private static void showAlert(Alert.AlertType alertType, Window owner,String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
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
        System.exit(0);
    }


}
