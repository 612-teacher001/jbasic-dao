package app;

import java.sql.SQLException;
import java.util.List;

import app.dao.ProductDAO;
import app.entity.Product;

/**
 * JDBCを介したデータ永続化のサンプル
 */
public class ProductListDemo {

	/**
	 * productsテーブルにアクセスするプログラム
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			// 1. ProductDAOをインスタンス化
			ProductDAO dao = new ProductDAO();
			// 2. 商品の全件検索の実行
			List<Product> list = dao.findAll();
			// 3. 商品リストの要素を表示
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
		}
		
	}

}
