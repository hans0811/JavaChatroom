package util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author Yirong Wang
 */
public class DBUtil {
    private static String URL;
    private static String  USERNAME;
    private static String PASSWORD;
    //读取属性文件
    static {
        Properties prop = new Properties();
        try {
            //加载
            prop.load(new FileReader("/Users/hans/Desktop/Bham/groupProject/login/information.properties"));
            //给属性赋值
            URL = prop.getProperty("URL");
            USERNAME = prop.getProperty("USERNAME");
            PASSWORD =prop.getProperty("PASSWORD");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   //静态代码块加载驱动
    static{
       try {
           Class.forName("org.postgresql.Driver");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
   }
    //获取连接
    public static Connection getConn(){
    Connection conn=null;
    try{
        conn= DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }catch(SQLException e){
        e.printStackTrace();
    }
    return conn;
    }
    //关闭
    public static void  close(PreparedStatement ps, Connection conn){
    close(null,ps,conn);
    }
    public static void  close(ResultSet rs, PreparedStatement ps, Connection conn){
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(ps!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if(conn!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
