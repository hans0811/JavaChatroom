package UI;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class updateFriendTask extends Task<Void> {
    public ObjectProperty<ObservableList<String>> friendProperty = new SimpleObjectProperty<>();
    public ObservableList<String> friendList;

    public updateFriendTask (ObservableList<String> friendList) {

        this.friendList = friendList;
        friendProperty.setValue(friendList);

    }

    @Override
    protected Void call() throws Exception {
        return null;
    }
}
