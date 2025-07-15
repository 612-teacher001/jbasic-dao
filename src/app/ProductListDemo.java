package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.entity.Product;

/**
 * JDBCを介したデータ永続化のサンプル
 */
public class ProductListDemo {

	/**
	 * クラス定数：データベース接続情報文字列群
	 */
	private static final String JDBC_DRIVER  = "org.postregsql.Driver"; // Java8より自動で読み込まれるようになったため不要
	private static final String DB_URL       = "jdbc:postgresql://localhost:5432/jbasicdaodb";
	private static final String DB_USER      = "student";
	private static final String DB_PASSWORD  = "himitu";
	
	/**
	 * productsテーブルにアクセスするプログラム
	 * @param args
	 */
	public static void main(String[] args) {
		// 1. データベース接続関連オブジェクトの初期化
		Connection conn = null;         // データベース接続オブジェクト
		PreparedStatement pstmt = null; // SQL実行オブジェクト
		ResultSet rs = null;            // SQL実行結果セット
		
		// 2. 実行するSQLの設定
		String sql = "SELECT * FROM products ORDER BY id";
		
		try {
			// 3. データベースに接続：データベース接続オブジェクトの取得
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			
			// 4. SQL実行オブジェクトの取得
			pstmt = conn.prepareStatement(sql);
			
			// 5. SQLの実行と結果セットの取得
			rs = pstmt.executeQuery();
			
			// 6. 結果セットを商品リストに変換
			List<Product> list= new ArrayList<>();
			while(rs.next()) {
				Product entity = new Product();
				entity.setId(rs.getInt("id"));
				entity.setName(rs.getString("name"));
				entity.setPrice(rs.getInt("price"));
				entity.setStock(rs.getInt("stock"));
				list.add(entity);
			}
			
			rs.close(); // 不要になったので解放
			
			// 7. 商品リストの要素を表示
			System.out.printf("%-6s\t%-10s\t%-6s\t%-3s\n", "商品ID", "商品名", "価格", "在庫数");
			for (Product product : list) {
				System.out.printf("%-6s\t%-10s\t%-6s\t%-3s\n", 
						product.getId(),
						product.getName(),
						product.getPrice(),
						product.getStock());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 8. データベース接続関連オブジェクトの解放
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
