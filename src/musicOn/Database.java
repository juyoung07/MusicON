package src.musicOn;


import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import static java.lang.System.getenv;

public class Database {
    Map<String, String> env = getenv();
    static Statement state = null;
    Connection connect = null;
    String url = "jdbc:mysql://localhost:3306/musicon";
    String user = "root";
    String pw = "0000";

    Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, user, pw);
            state = connect.createStatement();
            System.out.println("DATABASE 연동 성공!");
        } catch (Exception e) {
            System.out.println("DATABASE 연동 실패 : " + e.toString());
        }
    }

    public void saveScore(String tableName, int score) {
        String sql = "INSERT INTO " + tableName + "(score) VALUES(?)";
        try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setInt(1, score);
            pstmt.executeUpdate();
            System.out.println(tableName + " 점수 저장 성공: " + score);
        } catch (SQLException e) {
            System.out.println(tableName + " 점수 저장 실패: " + e.getMessage());
        }
    }

    public ArrayList<Integer> getTop5Scores(String tableName) {
        ArrayList<Integer> topScores = new ArrayList<>();
        String sql = "SELECT score FROM " + tableName + " ORDER BY score DESC LIMIT 5";

        try (PreparedStatement pstmt = connect.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                topScores.add(rs.getInt("score"));
            }
        } catch (SQLException e) {
            System.out.println(tableName + " 랭킹 조회 실패: " + e.getMessage());
        }
        return topScores;
    }
}