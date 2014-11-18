package compare.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;
import com.wk.sdo.ServiceData;
import compare.JSONFileUtil;

/**
 * @description
 * @author raoliang
 * @version 2014年10月16日 上午11:23:25
 */
public class TestH2 {
//	@Inject static ExportDatasFromDB exportService;
//	@Inject static ImportDatasToDB importService;
	private static final Log logger = LogFactory.getLog("dbcompare");
	private static String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	
	public static void main(String[] args) throws IOException {
		List<String> list = JSONFileUtil.readFileToStringArray(new File(filePath));
		for (String string : list) {
			System.out.println(string);
		}
		System.out.println("done!");
	}
	
	private static String replace(String fileName){
		return fileName.replaceAll("\\\\|/|:|\\*|\\?|\"|<|>|\\|", "_");
	}
	
	private static int executeSql(ServiceData fileData){
		String sql = "insert into sys_mode values('"+fileData.getString("MODE_CODE")+
				"','"+fileData.getString("MODE_NAME")+
				"','"+fileData.getString("MODE_TYPE")+
				"','"+fileData.getString("MODE_CLASS")+
				"','"+fileData.getString("IS_SYS_MODE")+
				"','"+fileData.getString("VERNO")+"')";
		Session session = DBSource.getDefault().getSession();
		int num = session.execute(sql);
		session.commit();
		session.close();
		System.out.println("Commited!");
		return num;
	}
	
	private static void commit(){
		Session session = DBSource.getDefault().getSession();
		session.commit();
		session.close();
		System.out.println("Commited!");
	}
	
//	private void init() {
//		Controller.getInstance().getInjector().inject(this);
//	}
//
//	static {
//		new TestH2().init();
//	}
}
