package app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import app.dao.ProductDAO;
import app.entity.Product;

/**
 * JDBCを介したデータ永続化のサンプル
 */
public class ProductKeywordSerchDemo {

	/**
	 * productsテーブルにアクセスするプログラム
	 * @param args
	 */
	public static void main(String[] args) {
		// 1. すべての商品を表示
		ProductListDemo.main(args);
		
		try (Scanner scanner = new Scanner(System.in);) {
			// 2. 検索するキーワードの取得
			System.out.print("商品キーワード：");
			String keyword = scanner.next();
			// 3. ProductDAOをインスタンス化
			ProductDAO dao = new ProductDAO();
			// 4. 商品の全件検索の実行
			List<Product> list = dao.findByName(keyword);
			
			// 5. 商品リストの要素を表示
			System.out.println();
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
