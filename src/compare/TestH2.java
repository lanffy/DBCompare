package compare;

import java.io.File;
import java.sql.SQLException;

/**
 * @description
 * @author raoliang
 * @version 2014年10月16日 上午11:23:25
 */
public class TestH2 {
	public static void main(String[] args) throws SQLException, Exception {
		String a = "D:\\appblocks\\DBCompare\\dbcompare\\result\\frame\\compare_report.html";
		File file = new File(a);
		System.out.println(file.getParent());
	}
}
