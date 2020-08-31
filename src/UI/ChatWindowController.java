package UI;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import server.ClientDemo;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ChatWindowController implements Initializable {

    // UI position
    double xOffset,yOffset;

    // loading images with path
    private final Image online = new Image("UI/pic/picOnline.png");
    private final Image offline = new Image("UI/pic/picOffline.png");


    // image array to show if user is online
    private Image[] imagesArray = {offline, online};

    // view the image class to display image
    private ImageView displayImage = new ImageView();

    // Friend List
    //private List<Integer> index



    @FXML
    private ListView<uiFriendList> friendList;
    private static ObservableList<uiFriendList> nameList = FXCollections.observableArrayList(uiFriendList.extractor());
    private static ObservableList<uiFriendList> nameListMatch = FXCollections.observableArrayList();

    @FXML
    private AnchorPane listAtrea;

    @FXML
    private ListView lvChatWindow;
    // create observablelist for listview
    public static ObservableList<String> chatMessages = FXCollections.observableArrayList();

    @FXML
    private VBox dialogue;
    @FXML
    private VBox converation;
    public static VBox converation1;

    @FXML
    private ScrollPane scroller;
    //scroller.setValue(1.0);

    @FXML
    private TextField inpText;

    @FXML
    private TextArea contentArea;
    public static TextArea contentArea01;

    @FXML
    private Button btnSend, friendListBtn,canBtn;

    private static TextField contentArea1;

    @FXML
    public void sendMessageAction(ActionEvent event) {
        System.out.println("snedMessage");

        if(!inpText.getText().isEmpty()){
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.BASELINE_RIGHT);
            hbox.setPadding(new Insets(5, 5, 5, 5));
            //hbox.setSpacing(50);
            //hbox.setStyle("-fx-background-color: #BDD7FF;");

            Label label = new Label(inpText.getText()+ "      ");
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-background-color: #BDD7FF; -fx-background-radius: 10px; -fx-padding: 2 2 2 20;");
            hbox.getChildren().add(label);
            converation.getChildren().add(hbox);
        }else{
            return;
        }

        chatMessages.add(inpText.getText());

        //     System.out.println(inpText.getText());
        String text = inpText.getText();
//        contentArea.appendText("Wait for new message...");

//        contentArea.appendText(text + "\n");
//        contentArea.appendText(revceMsg + "\n");
        inpText.setText("");

    }

    private ArrayList<String> friendListMatch;

    @FXML
    public void handleMouseClick(MouseEvent event) throws IOException {
        //lvChatWindow.getItems().clear();

        if(friendListMatch == null) friendListMatch = new ArrayList<String>();
        System.out.println("clicked on " + friendList.getSelectionModel().getSelectedItem());
        String friendName = friendList.getSelectionModel().getSelectedItem().getName();

        if(friendListMatch.contains(friendName)){
            System.out.println("Already open");
        }else{
            friendListMatch.add(friendName);

           ChatSingleController chatSingleController = new ChatSingleController();
           chatSingleController.changeName(friendName);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("uiChatSingle.fxml"));
            Parent rootRoom = (Parent) fxmlLoader.load();
            
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            Scene scene1 = new Scene(rootRoom);

            scene1.setFill(Color.TRANSPARENT);
            stage.setScene(scene1);
            stage.show();
            // when closing window
            stage.showingProperty().addListener((observable, oldVaule, newValue)->{
                if(oldVaule == true && newValue == false){
                    friendListMatch.remove(friendName);
                }
            });
        }
    }

    /**
     * Logout to the login page
     * @param event
     * @throws IOException
     */
    @FXML
    public void returnAction(ActionEvent event) throws IOException {
        // register successful
        Parent chatWindowParent =  FXMLLoader.load(getClass().getResource("uiLogin.fxml"));
        Scene chatWindowScene = new Scene(chatWindowParent);

        // get Stage information
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
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
    }

    /**
     * Renew online list
     */
    private void initInputThread() {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                while (true) {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            friendList.getItems().clear();
                            genNames();;
                            friendList.setItems(nameList);
                            System.out.println(nameList);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initInputThread();

        if(!friendList.equals(null)){
            System.out.println(" initilize");
            System.out.println(friendList);
            //friendList.getItems().remove(nameList.get(0));
        }

        genNames();
        System.out.println("ChatScene Initialized");
        //lvChatWindow.setItems(chatMessages);

        //friendList.setItems(nameList);
        friendList.getItems().clear();
    }// end initialze

    @FXML
    public static void displayReply(String msg, String senderSocketIndex){
        if(!msg.isEmpty()){
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.BASELINE_LEFT);
            hbox.setPadding(new Insets(5, 5, 5, 5));
            //hbox.setSpacing(50);
            //hbox.setStyle("-fx-background-color: #BDD7FF;");

            Label label = new Label(msg + "      ");
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-background-color: #BDD7FF; -fx-background-radius: 10px; -fx-padding: 2 2 2 20;");
            hbox.getChildren().add(label);
            //converation.getChildren().add(hbox);
        }else{
            return;
        }
        chatMessages.add("User 2" + msg);
    }

    /**
     * Give the online lists
     */
    public static void genNames(){
        Set<Map.Entry<String, Integer>> ILEntries = ClientDemo.loginid_indexPair.entrySet();
        ILEntries.forEach((ILEntry)->{
            nameList.add(new uiFriendList(ILEntry.getKey()));
        });

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
}
