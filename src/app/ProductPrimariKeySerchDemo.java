package app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import app.dao.ProductDAO;
import app.entity.Product;

/**
 * JDBCを介したデータ永続化のサンプル
 */
public class ProductPrimariKeySerchDemo {

	/**
	 * productsテーブルにアクセスするプログラム
	 * @param args
	 */
	public static void main(String[] args) {
		// 1. すべての商品を表示
		ProductListDemo.main(args);
		
		try (Scanner scanner = new Scanner(System.in);) {
			// 2. 検索する商品IDの取得
			System.out.print("商品ID：");
			int id = scanner.nextInt();
			// 3. ProductDAOをインスタンス化
			ProductDAO dao = new ProductDAO();
			// 4. 商品の全件検索の実行
			Product product = dao.findById(id);
			
			// 5. 商品リストの要素を表示
			System.out.println();
			System.out.printf("%-6s\t%-10s\t%-6s\t%-3s\n", "商品ID", "商品名", "価格", "在庫数");
			System.out.printf("%-6s\t%-10s\t%-6s\t%-3s\n", 
					product.getId(),
					product.getName(),
					product.getPrice(),
					product.getStock());
			
		} catch (InputMismatchException | SQLException e) {
			e.printStackTrace();
		}
	}
}
