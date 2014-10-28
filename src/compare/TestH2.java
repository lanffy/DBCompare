package compare;

import java.sql.SQLException;

/**
 * @description
 * @author raoliang
 * @version 2014年10月16日 上午11:23:25
 */
public class TestH2 {
	public static void main(String[] args) throws SQLException, Exception {
		String a = "00000.00";
		double b = Double.parseDouble(a);
		System.out.println(b);
	}
}
