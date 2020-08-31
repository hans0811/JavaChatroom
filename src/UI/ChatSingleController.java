package UI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import server.ClientDemo;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class ChatSingleController implements Initializable {

    private double xOffset;
    private double yOffset;

    @FXML
    private AnchorPane chatSingle;

    @FXML
    private TextField inpText;

    @FXML
    private Button canBtn;

    @FXML
    private Label userName;

    private static String getName;

    @FXML
    private ListView lvChatWindow;
    public static String test;
    public static ObservableList<String> chatMessages_null = FXCollections.observableArrayList();
    public static TreeMap<String, ObservableList<String>> id_chatMsgPair = new TreeMap<String, ObservableList<String>>();

    @FXML
    public void sendMessageAction(ActionEvent event) {
        String receiver_loginid = String.valueOf(ClientDemo.loginid_indexPair.get(getName));
        System.out.println("snedMessage");
        //test = "Me : " + inpText.getText();
        if(id_chatMsgPair.containsKey(receiver_loginid)){
            ObservableList<String> chatMessages = id_chatMsgPair.get(receiver_loginid);
            chatMessages.add("Me : " + inpText.getText());
            lvChatWindow.setStyle("list-cell-fx-alignment: right;");
            String text = inpText.getText();
            ClientDemo.communicate(String.valueOf(ClientDemo.socket_index),text,String.valueOf(ClientDemo.loginid_indexPair.get(getName)));
            inpText.setText("");
            lvChatWindow.setItems(chatMessages);
        }else{
            id_chatMsgPair.put(receiver_loginid, chatMessages_null);
            ObservableList<String> chatMessages = id_chatMsgPair.get(receiver_loginid);
            chatMessages.add("Me : " + inpText.getText());
            lvChatWindow.setStyle("list-cell-fx-alignment: right;");
            String text = inpText.getText();
            ClientDemo.communicate(String.valueOf(ClientDemo.socket_index),text,String.valueOf(ClientDemo.loginid_indexPair.get(getName)));
            inpText.setText("");
            lvChatWindow.setItems(chatMessages);
        }

    //    chatMessages.add("Me : " + inpText.getText());
    //    lvChatWindow.setStyle("list-cell-fx-alignment: right;");
      //  String text = inpText.getText();
    ///    ClientDemo.communicate(String.valueOf(ClientDemo.socket_index),text,String.valueOf(ClientDemo.loginid_indexPair.get(getName)));
    //    inpText.setText("");
     //   lvChatWindow.setItems(chatMessages);

    }

    @FXML
    public void displayReply(String msg, String senderSocketIndex){
        int senderIndex = Integer.parseInt(senderSocketIndex);
  //      String receiver_loginid = String.valueOf(ClientDemo.loginid_indexPair.get(getName));
      //  String username = ClientDemo.index_loginidPair.get(senderIndex);
        getName = ClientDemo.index_loginidPair.get(senderIndex);
   //     String receiver_loginid = String.valueOf(ClientDemo.loginid_indexPair.get(getName));
        if(id_chatMsgPair.containsKey(getName)){
            id_chatMsgPair.get(getName).add(getName + " : " + msg);
        }else{
            id_chatMsgPair.put(getName, chatMessages_null);
            id_chatMsgPair.get(getName).add(getName + " : " + msg);
        }
 //       chatMessages.add(getName + " : " + msg);
        Platform.runLater(new Runnable(){
            @Override
            public void run() {

            }
        });
    }

    @FXML
    public void changeName(String name){
        System.out.println("changeName " + name);
        getName = name;

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
                            //friendList.getItems().clear();
                            //chatMessages.removeAll();
                        }
                    });
                    try {
                        Thread.sleep(2000);
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
 //       ObservableList<String> chatMessages1 = FXCollections.observableArrayList();
        userName.setText(getName);
       // initInputThread();
        lvChatWindow.setItems(chatMessages_null);

        // Mouse drug window
        chatSingle.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        chatSingle.setOnMouseDragged(event -> {
            chatSingle.getScene().getWindow().setX(event.getScreenX() - xOffset);
            chatSingle.getScene().getWindow().setY(event.getScreenY() - yOffset);
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
