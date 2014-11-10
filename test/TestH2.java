package compare.test;

import java.io.IOException;

import com.wk.Controller;
import com.wk.db.DBSource;
import com.wk.db.DBTransaction;
import com.wk.db.Session;
import com.wk.db.SessionHandle;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;
import com.wk.sdo.ServiceData;

/**
 * @description
 * @author raoliang
 * @version 2014年10月16日 上午11:23:25
 */
public class TestH2 {
//	@Inject static ExportDatasFromDB exportService;
//	@Inject static ImportDatasToDB importService;
	private static final Log logger = LogFactory.getLog("dbcompare");
//	private static String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	
	public static void main(String[] args) throws IOException {
		String sql = "delete from SYS_CHANNEL;"
				+ "delete from SYS_COMM;"
				+ "delete from SYS_DICT;"
				+ "delete from SYS_DICT_DETAIL;"
				+ "delete from SYS_GROUP_SVC_CHART;"
				+ "delete from SYS_INSTANCE;"
				+ "delete from SYS_MACHINE;"
				+ "delete from SYS_MAPPING;"
				+ "delete from SYS_MODE;"
				+ "delete from SYS_MODE_PARAM;"
				+ "delete from SYS_PROCESS_INSTANCE;"
				+ "delete from SYS_SAVE_DATAS;"
				+ "delete from SYS_SERVER;"
				+ "delete from SYS_SERVICE;"
				+ "delete from SYS_STRUCTURE;"
				+ "delete from SYS_TRAN_CHANNEL_PACKAGE;"
				+ "delete from SYS_TRAN_SERVER_PACKAGE;";
		Session session = DBSource.getDefault().openSession();
		int num = session.execute(sql);
		System.out.println(num);
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
	
	private void init() {
		Controller.getInstance().getInjector().inject(this);
	}

	static {
		new TestH2().init();
	}
}
