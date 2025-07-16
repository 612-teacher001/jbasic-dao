package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.entity.Product;

/**
 * productsテーブルにアクセスするDAOクラス
 */
public class ProductDAO {

	/**
	 * クラス定数
	 */
	// データベース接続情報文字列群
	private static final String JDBC_DRIVER  = "org.postregsql.Driver"; // Java8より自動で読み込まれるようになったため不要
	private static final String DB_URL       = "jdbc:postgresql://localhost:5432/jbasicdaodb";
	private static final String DB_USER      = "student";
	private static final String DB_PASSWORD  = "himitu";
	
	// SQL文字列定数
	private static final String SQL_FIND_ALL = "SELECT * FROM products ORDER BY id";
	
	/**
	 * productsテーブルのすべてのレコードを取得する
	 * @return 商品リスト
	 * @throws SQLException 
	 */
	public List<Product> findAll() throws SQLException {
		
		try ( // 2.1 データベース接続オブジェクトの取得
			  Connection  conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			  // 2.2 SQL実行オブジェクトの取得
			  PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_ALL);
			  // 2.3 SQLの実行と結果セットの取得
			  ResultSet rs = pstmt.executeQuery();
			) {
			
			// 3. 結果セットを商品リストに変換
			List<Product> list = convertToEntityList(rs);
			
			// 4. 商品リストの返却
			return list;
			
		}
	}

	/**
	 * 結果セットからエンティティリストに変換する
	 * @param rs 結果セット
	 * @return エンティティリスト
	 * @throws SQLException
	 */
	private List<Product> convertToEntityList(ResultSet rs) throws SQLException {
		List<Product> list = new ArrayList<Product>();
		while(rs.next()) {
			Product entity  = convertToEntity(rs);
			list.add(entity);
		}
		return list;
	}

	/**
	 * 結果セットのレコードをエンティティクラスに変換する
	 * @param rs 結果セット
	 * @return エンティティのインスタンス
	 * @throws SQLException
	 */
	private Product convertToEntity(ResultSet rs) throws SQLException {
		Product entity = new Product();
		entity.setId(rs.getInt("id"));
		entity.setName(rs.getString("name"));
		entity.setPrice(rs.getInt("price"));
		entity.setStock(rs.getInt("stock"));
		return entity;
	}	
	
}
