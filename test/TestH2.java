package compare.test;

import java.io.IOException;
import java.util.List;

import com.wk.Controller;
import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.eai.webide.dao.ChannelDaoService;
import com.wk.eai.webide.info.ModeInfo;
import com.wk.lang.Inject;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;
import com.wk.sdo.ServiceData;

import compare.ExportDatasFromDB;
import compare.ImportDatasToDB;
import compare.JSONFileUtil;

/**
 * @description
 * @author raoliang
 * @version 2014年10月16日 上午11:23:25
 */
public class TestH2 {
	@Inject static ExportDatasFromDB exportService;
	@Inject static ImportDatasToDB importService;
	private static final Log logger = LogFactory.getLog("dbcompare");
	private static String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	
	public static void main(String[] args) throws IOException {
		ServiceData modeData = exportService.getOneMode("vrouterclient_lu");
		logger.info("导出数据:\n{}", modeData);
		JSONFileUtil.storeServiceDataToJsonFile(modeData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
//		String str = JSON.fromServiceData(fileData, JSONCaseType.DEFAULT);
//		fileData = JSON.toServiceDataByType(str.replaceAll("，", ","), JSONCaseType.DEFAULT);
		fileData.putString("MODE_CODE", "test_mode1");
		System.out.println(fileData.getString("MODE_NAME"));
//		fileData.putString("MODE_NAME", "VRouter客户端包模式,请求小写,响应大大");
		logger.info("导入数据:\n{}", fileData);
		
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
//		int num = importService.insertOneMode(fileData);
		logger.info("成功插入模式{}个", num);
		System.out.println(num);
	}
	
	private static String replace(String fileName){
		return fileName.replaceAll("\\\\|/|:|\\*|\\?|\"|<|>|\\|", "_");
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
