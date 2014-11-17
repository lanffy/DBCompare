package compare;

import java.io.File;
import java.util.List;

import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.sdo.ServiceData;
import com.wk.util.FileUtil;

/**
 * @description VRouter数据库导入类
 * @author raoliang
 * @version 2014年11月9日 下午4:38:36
 */
public class DBImporter extends DBporter{
	private static Session session = DBSource.getDefault().openSession();
	public static void doImport() {
		logger.info("***Begin开始向数据库导入数据,开始时间:{}***",getTime());
		//清空数据库
		/**
		 * 需要清空的表： SYS_CHANNEL SYS_COMM SYS_DICT SYS_DICT_DETAIL
		 * SYS_GROUP_SVC_CHART SYS_INSTANCE SYS_MACHINE SYS_MAPPING SYS_MODE
		 * SYS_MODE_PARAM SYS_PROCESS_INSTANCE SYS_SAVE_DATAS SYS_SERVER
		 * SYS_SERVICE SYS_STRUCTURE SYS_TRAN_CHANNEL_PACKAGE
		 * SYS_TRAN_SERVER_PACKAGE
		 */
		emptyDataBase();
		/** 导入顺序：EndPoint、服务系统、EndPoint关联交易、服务系统关联交易
		 * 服务、部署、数据字典、模式
		 */
		insertEndPoint();
		insertServer();
		insertTranEndPoint();
		insertTranServer();
		insertService();
		insertMachine();
		insertDict();
		insertMode();
		commit();
		logger.info("***End向数据库导入数据结束,结束时间:{}***", getTime());
		System.out.println("Import done!");
	}
	
	public static void insertEndPoint(){
		String fileDir = getFileDir("db.endPointDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneEndPoint(data);
		}
		logger.info("成功插入EndPoint数据{}条", count);
	}
	
	public static void insertServer(){
		String fileDir = getFileDir("db.serverDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneServer(data);
		}
		logger.info("成功插入服务系统数据{}条", count);
	}
	
	public static void insertTranEndPoint(){
		String fileDir = getFileDir("db.endPointTranDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneTranEndPoint(data);
		}
		logger.info("成功插入EndPoint关联交易数据{}条", count);
	}
	
	public static void insertTranServer(){
		String fileDir = getFileDir("db.serverTranDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneTranServer(data);
		}
		logger.info("成功插入服务系统关联交易数据{}条", count);
	}
	
	public static void insertService(){
		String fileDir = getFileDir("db.serviceDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneService(data);
		}
		logger.info("成功插入服务数据{}条", count);
	}
	
	public static void insertMachine(){
		String fileDir = getFileDir("db.deployDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneMachine(data);
		}
		logger.info("成功插入部署数据{}条", count);
	}
	
	public static void insertDict(){
		String fileDir = getFileDir("db.dictDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOrUpdateOneDict(data);
		}
		logger.info("成功插入数据字典{}条", count);
	}
	
	public static void insertMode(){
		String fileDir = getFileDir("db.modeDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOrUpdateOneMode(data);
		}
		logger.info("成功插入模式{}条", count);
	}
	
	private static void emptyDataBase(){
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
//		Session session = DBSource.getDefault().openSession();
		session.execute(sql);
		logger.info("清空表数据");
	}
	
	private static void commit(){
//		Session session = DBSource.getDefault().getSession();
		session.commit();
		session.close();
		System.out.println("Commited!");
	}
}
