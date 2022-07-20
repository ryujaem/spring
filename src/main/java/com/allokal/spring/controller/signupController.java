package com.allokal.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
@RequiredArgsConstructor
public class signupController {

    String driver = "org.mariadb.jdbc.Driver";
    Connection con = null;
    PreparedStatement pstmt;
    Statement stmt = null;
    ResultSet rs = null;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
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

            String get_id = loginvo.id;
            stmt = con.createStatement();
            String sql = "SELECT id FROM user WHERE id = '"+get_id+"';";
            System.out.println(sql+1);
            rs = stmt.executeQuery(sql);
            System.out.println(sql+2);
            rs.next();
            System.out.println(sql+3);
            String id = rs.getString("id");
            System.out.println("id 조회 결과 : "+id);

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
        } catch (SQLException e) {
            System.out.println("데이터 조회 실패");
            loginvo.setId("");
        }

        return loginvo;

    }
}
