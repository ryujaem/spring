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

    @RequestMapping(value = "/checkedid", method = RequestMethod.POST)
    @ResponseBody
    public CheckVO CheckedId(@RequestBody CheckVO checkedId_vo) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(
                    "jdbc:mariadb://127.0.0.1:3306/allokal",
                    "root",
                    "root");

            if (con != null) {
                System.out.println("DB 접속 성공");
            }

            String get_id = checkedId_vo.id;
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
            checkedId_vo.setId("");
        }
        return checkedId_vo;

    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public SignupVO Signup(@RequestBody SignupVO signupvo) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(
                    "jdbc:mariadb://127.0.0.1:3306/allokal",
                    "root",
                    "root");

            if (con != null) {
                System.out.println("DB 접속 성공");
            }

            stmt = con.createStatement();
            String sql = "INSERT INTO user (id,pw,country,name,email,auth,signup_date) " +
                    "VALUES('"+signupvo.id+"','"+ signupvo.pw+"','"+signupvo.country+"','"+signupvo.name+"','" +
                    ""+signupvo.email+"',1,now());";
            System.out.println("INSERT문 : "+sql);
            stmt.execute(sql);

            System.out.println("insert 완료");
            sql = "SELECT user_num FROM user WHERE id = '" + signupvo.id + "' AND pw = '" + signupvo.pw + "';";
            System.out.println("SELECT문 : "+sql);
            rs = stmt.executeQuery(sql);
            rs.next();
            String user_num = rs.getString("user_num");
            System.out.println(user_num);
            signupvo.setUser_num(user_num);
            System.out.println(signupvo.user_num);
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
        } catch (SQLException e) {
            System.out.println("데이터 조회 실패");
            signupvo.setId("");
        } finally {
            System.out.println("데이터 입출력 완료");
        }
        return signupvo;

    }
}
