package compare;

import java.io.File;
import java.util.List;

import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.sdo.ServiceData;

/**
 * @description VRouter数据库导入类
 * @author raoliang
 * @version 2014年11月9日 下午4:38:36
 */
public class DBImporter extends DBporter{
	
	public static void main(String[] args) {
		doImport();
	}
	
	public static void doImport() {
		logger.info("***Begin开始向数据库导入数据,开始时间:{}***",getTime());
		//清空数据库
		//新的需求：不能全部导出导入，所以不能清空数据库
		/**
		 * 需要清空的表： SYS_CHANNEL SYS_COMM SYS_DICT SYS_DICT_DETAIL
		 * SYS_GROUP_SVC_CHART SYS_INSTANCE SYS_MACHINE SYS_MAPPING SYS_MODE
		 * SYS_MODE_PARAM SYS_PROCESS_INSTANCE SYS_SAVE_DATAS SYS_SERVER
		 * SYS_SERVICE SYS_STRUCTURE SYS_TRAN_CHANNEL_PACKAGE
		 * SYS_TRAN_SERVER_PACKAGE
		 */
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
		List<File> fileList = listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneEndPoint(data);
		}
		logger.info("成功插入或修改EndPoint数据{}条", count);
		// 删除EndPoint,根据channel_code删除
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneEndPoint(string);
			logger.info("成功删除EndPoint：{}", string);
		}
	}
	
	public static void insertServer(){
		String fileDir = getFileDir("db.serverDir");
		List<File> fileList = listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneServer(data);
		}
		logger.info("成功插入或修改服务系统数据{}条", count);
		//删除server,根据server_code删除
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneServer(string);
			logger.info("成功删除Server：{}", string);
		}
	}
	
	public static void insertTranEndPoint(){
		String fileDir = getFileDir("db.endPointTranDir");
		List<File> fileList = listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneTranEndPoint(data);
		}
		logger.info("成功插入或修改EndPoint关联交易数据{}条", count);
		//删除EndPoint关联交易,根据channel_code和tran_code删除
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneTranEndPoint(splitTrab(string)[0],	splitTrab(string)[1]);
			logger.info("成功删除EndPoint关联交易：{}", string);
		}
	}
	
	public static void insertTranServer() {
		String fileDir = getFileDir("db.serverTranDir");
		List<File> fileList = listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneTranServer(data);
		}
		logger.info("成功插入或修改服务系统关联交易数据{}条", count);
		// 删除服务关联交易,根据tran_code删除
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneTranServerByTranCode(splitTrab(string)[1]);
			logger.info("成功删除服务系统关联交易：{}", string);
		}
	}
	
	public static void insertService(){
		String fileDir = getFileDir("db.serviceDir");
		List<File> fileList = listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneService(data);
		}
		logger.info("成功插入或修改服务数据{}条", count);
		// 删除服务,根据service_code删除
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneService(string);
			logger.info("成功删除服务：{}", string);
		}
	}
	
	public static void insertMachine(){
		String fileDir = getFileDir("db.deployDir");
		List<File> fileList = listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneMachine(data);
		}
		logger.info("成功插入或修改部署数据{}条", count);
		/**
		 * 删除文件主键格式
		 * 001 服务器编码 
		 * 001>001 服务器编码>进程标识代码 
		 * 001>001>CHL 服务器编码>进程标识代码>EndPoint编码
		 */
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			String[] keyarray = splitTrab(string);
			if(keyarray.length == 1){
				int del = deleter.deleteOneMachine(keyarray[0]);
				logger.info("删除服务器{}数据{}条", keyarray[0], del);
			}else if(keyarray.length == 2){
				int del = deleter.deleteOneInstance(keyarray[0], keyarray[1]);
				logger.info("删除服务器{}下的进程{}数据{}条", keyarray[0], keyarray[1], del);
			}else if(keyarray.length == 3){
				int del = deleter.deleteOneProcessInstance(keyarray[0], keyarray[1], keyarray[2]);
				logger.info("删除服务器{}下的进程{}的部署的EndPoint:{}数据{}条", keyarray[0], keyarray[1], keyarray[2], del);
			}else{
				logger.error("删除部署数据文件中的主键格式不正确：{}", string);
			}
		}
	}
	
	public static void insertDict(){
		String fileDir = getFileDir("db.dictDir");
		List<File> fileList = listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOrUpdateOneDict(data);
		}
		logger.info("成功插入或修改数据字典{}条", count);
		// 删除数据字典，可以删除整个数据字典，也可以删除字典中的一个字段
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			String[] keys = splitTrab(string);
			if(keys.length == 1){
				//删除数据字典
				deleter.deleteOneDict(string);
				logger.info("成功数据字典：{}", string);
			}else if(keys.length == 2){
				//删除数据字典中的一个字段
				deleter.deleteOneDictDetail(keys[0], keys[1]);
				logger.info("成功数据字典{}中的字段：{}", keys[0], keys[1]);
			}else{
				logger.error("删除数据字典数据文件中的主键格式不正确：{}", string);
			}
		}
	}
	
	public static void insertMode(){
		String fileDir = getFileDir("db.modeDir");
		List<File> fileList = listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOrUpdateOneMode(data);
		}
		logger.info("成功插入或修改模式{}条", count);
		//TODO: 模式暂不提供删除
	}
	
	private static void commit(){
		Session session = DBSource.getDefault().getSession();
		session.commit();
		session.close();
		System.out.println("Commited!");
	}
}
