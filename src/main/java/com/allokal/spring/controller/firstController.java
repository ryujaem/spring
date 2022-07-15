package com.allokal.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
@RequiredArgsConstructor
public class firstController {

    String driver = "org.mariadb.jdbc.Driver";
    Connection con = null;
    PreparedStatement pstmt;
    Statement stmt = null;
    ResultSet rs = null;
    int r;
    String user_num = null;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginVO Login(@RequestBody LoginVO loginvo) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(
                    "jdbc:mariadb://127.0.0.1:3306/allokal",
                    "root",
                    "root");

            if (con != null) {
                System.out.println("DB 접속 성공");
            }
            String id = loginvo.id;
            String pwd = loginvo.pwd;
            stmt = con.createStatement();
            String sql = "SELECT user_num FROM user WHERE id = '" + id + "' AND pw = '" + pwd + "';";
            System.out.println(sql+1);
            rs = stmt.executeQuery(sql);
            System.out.println(sql+2);
            rs.next();
            System.out.println(sql+3);
            user_num = rs.getString("user_num");
            System.out.println(user_num);
            loginvo.setMsg(user_num);
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
        }

        return loginvo;

    }
}
