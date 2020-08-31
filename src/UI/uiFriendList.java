package UI;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class uiFriendList {
    private StringProperty name;
    private String loginid;

    public uiFriendList(String Name, String name){
        setFirstName(name);
        this.loginid = Name;
    }

    public uiFriendList(String key) {
        setFirstName(key);
    }

    public final StringProperty nameProperty() {
        if (name == null) {
            name = new SimpleStringProperty();
        }
        return name;
    }

    public final String getName() {
        return nameProperty().get();
    }

    private void setFirstName(final java.lang.String name) {
        nameProperty().set(name);
    }




    public static Callback<uiFriendList, Observable[]> extractor(){
        return new Callback<uiFriendList, Observable[]>() {
            @Override
            public Observable[] call(uiFriendList param) {
                return new Observable[]{param.name};
            }
        };
    }

//    public String getName(){
//        return loginid;
//    }

    public void setName(String name){
        this.loginid = name;
    }

    @Override
    public String toString() {
        return name.get();
    }
//    @Override
//    public String toString(){
//        return loginid;
//    }

}
