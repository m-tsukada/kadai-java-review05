import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement spstmt = null; //更新前、更新後の検索用プリペアードステートメントオブジェクト
        PreparedStatement ipstmt = null; //更新処理用プリペアードステートメントオブジェクト
        ResultSet rs = null;
        
        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Newyork03#$"
            );

            // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            // 検索用SQLおよび検索用PreparedStatementオブジェクトを取得
            String selectSql = "SELECT * From persons where id = ?";
            spstmt = con.prepareStatement(selectSql);
            
            
            // 5, 6. Select文の実行と結果を格納／代入
            System.out.print("idを入力してください > ");
            int num1 = keyInNum();
            
            // PreparedStatementオブジェクトの?に値をセット
            spstmt.setInt(1, num1);
            
            rs = spstmt.executeQuery();
                    
            // 7. 結果を表示する
            while( rs.next() ) {
                // name 列の値を取得
                String name = rs.getString("name");
                // 取得した値を表示
                System.out.println(name);
                // age 列の値を取得
                int age = rs.getInt("age");
                // 取得した値を表示
                System.out.println(age);
            }

            
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました");
        } finally {
            // 8. 接続を閉じる
            if( spstmt != null) {
                try {
                    spstmt.close();
                } catch (SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました");
                    e.printStackTrace();
                }
            }
            if( con != null ) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました");
                    e.printStackTrace();
                }
            }
        }
    }
    /*
     * キーボードから入力された値をStringで返す 引数:なし 戻り値:入力された文字列
     */
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {
            
        }
        return line;
    }
    
    /*
     * キーボードから入力された値をintで返す 引数:なし 戻り値:int
     */
    private static int keyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        } catch (NumberFormatException e) {
            
        }
        return result;
    }

}
