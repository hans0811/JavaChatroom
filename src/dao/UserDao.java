package dao;

import com.Personalinfor;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    /**
     * connect to SSH
     */
    public static void go() {
        String url = "mod-msc-sw1.cs.bham.ac.uk"; //远程PostgreSQL服务器
        String sshurl = "tinky-winky.cs.bham.ac.uk"; //SSH服务器
        String sshuser = "mxt920"; //SSH连接用户名
        String sshpassword = "For3v3r0811"; //SSH连接密码
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sshuser, sshurl, 22);
            session.setPassword(sshpassword);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息

            int assinged_port = session.setPortForwardingL(5433, url, 5432);//端口映射 转发  数据库服务器地址url

            System.out.println("localhost:" + assinged_port);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertPersonalinfor(Personalinfor personalinfor) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            go();
            conn = DBUtil.getConn();
            String sql = "insert into u_personalinfor(u_loginid,u_nickname,u_realname,u_password,u_gender,u_email,u_phonenum)values(?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, personalinfor.getLoginid());
            ps.setString(2, personalinfor.getNickname());
            ps.setString(3, personalinfor.getRealname());
            ps.setString(4, personalinfor.getPassword());
            ps.setLong(5, personalinfor.getGender());
            ps.setString(6, personalinfor.getEmail());
            ps.setString(7, personalinfor.getPhonenum());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    public void updatePersonalinfor(Personalinfor personalinfor) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            go();
            conn = DBUtil.getConn();
            String sql = "update u_personalinfor set u_loginid = ?,u_nickname =?, u_realname = ? ,u_password= ? ,u_gender=?,u_email=?,u_phonenum=? where u_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, personalinfor.getLoginid());
            ps.setString(2, personalinfor.getNickname());
            ps.setString(3, personalinfor.getRealname());
            ps.setString(4, personalinfor.getPassword());
            ps.setLong(5, personalinfor.getGender());
            ps.setString(6, personalinfor.getEmail());
            ps.setString(7, personalinfor.getPhonenum());
            ps.setLong(8, personalinfor.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
    }

    public void deletePersonalinfor(Long   Id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            go();
            connection = DBUtil.getConn();
            String sql = "delete from u_personalinfor where u_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, Id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(preparedStatement, connection);
        }
    }
/**
        public User selectUserById ( long id){
            User user = null;
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String sql = "select * from t_user where id = ?";
            try {
                go();
                conn = DBUtil.getConn();
                ps = conn.prepareStatement(sql);
                ps.setLong(1, id);// 执行数据库的查询语句，并返回查询结果
                rs = ps.executeQuery();// 光标向下移动一次并判断下一个元素是否有值。
                while (rs.next()) {
                    user = new User();//将结果集中当前元素的显示列名为id的数据获取出来并设置道user的id上
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setUsername(rs.getString("username"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtil.close(rs, ps, conn);
            }
            return user;
        }

        public List<User> selectAll () {
            List<User> list = new ArrayList<>();
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String sql = "";
            try {
                go();
                conn = DBUtil.getConn();
                sql = "SELECT * FROM t_user";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setId(rs.getLong("id"));
                    list.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtil.close(rs, ps, conn);
            }
            return list;
        }
 */

}