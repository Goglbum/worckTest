package ru.netology.web.SQL;

import lombok.val;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLQuery {
    public static String getCode(String userLogin) throws SQLException {
        val userId = "SELECT id FROM users WHERE login = ?;";
        val code = "SELECT * FROM `auth_codes` WHERE `user_id` = ? ORDER BY created DESC";
        String resultUserId = null;

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                val userIdStmt = conn.prepareStatement(userId);
                val codeStmt = conn.prepareStatement(code);
        ) {
            userIdStmt.setString(1, userLogin);
            try (val rs = userIdStmt.executeQuery()) {
                if (rs.next()) {
                    resultUserId = rs.getString("id");
                }
            }
            codeStmt.setString(1, resultUserId);
            try (val rs = codeStmt.executeQuery()) {
                if (rs.next()) {
                    val resultCode = rs.getString("code");
                    return resultCode;
                }
            }
        }
        return null;
    }
}
