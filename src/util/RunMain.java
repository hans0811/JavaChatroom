package util;

import com.Personalinfor;
import dao.UserDao;

public class RunMain {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        Personalinfor personalinfor = new Personalinfor();
        personalinfor.setLoginid("yos123");
        personalinfor.setPassword("654321");
        personalinfor.setRealname("Sandy Smith");
        personalinfor.setNickname("");
        personalinfor.setGender(1l);
        personalinfor.setEmail("yvonnesmith@gmail.com");
        personalinfor.setPhonenum("07579838547");
        personalinfor.setId(13l);
//        userDao.insertPersonalinfor(personalinfor);
//        userDao.updatePersonalinfor(personalinfor);
          userDao.deletePersonalinfor(13L);
  /*      List<User> list = userDao.selectAll() ;
        for(User u:list){
            System.out.println(user);
        }*/
    }

}
