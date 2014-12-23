package compare;

import java.io.File;
import java.util.List;

import com.wk.sdo.ServiceData;

/**
 * @description VRouter数据库导出类
 * @author raoliang
 * @version 2014年11月9日 下午1:29:03
 */
public class DBExporter extends DBporter{
	
	public static void main(String[] args) {
		doExport();
	}
	
	public static void doExport() {
		System.out.println("begin Export...");
		logger.info("\n***Begin开始导出数据,开始时间:{}***",getTime());
		/** 导出顺序：EndPoint、服务系统、EndPoint关联交易、服务系统关联交易
		 * 服务、部署、数据字典、模式
		 */
		deleteFloder(prop.getProperty("db.exportDirStr"));
		
		exportEndPoint();
		exprotServer();
		exportTranEndPoint();
		exportTranServer();
		exportService();
		exportMachine();
		exportDict();
		exportMode();
		logger.info("\n***End导出数据结束,结束时间:{}***", getTime());
		System.out.println("Export done!");
	}
	
	public static void exportEndPoint(){
		List<String> list = exporter.getAllChannelCode();
		String fileDir = getFileDir("db.endPointDir");
		for (String channel_code : list) {
			File file = createFile(fileDir+replace(channel_code));
			ServiceData data = exporter.getOneEndPoint(channel_code);
			JSONFileUtil.storeServiceDataToJsonFile(data, file);
		}
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		createDeleteFile(delFileDir);
		logger.info("导出EndPoint数据{}条,存储路径{}", list.size(), fileDir);
	}
	
	public static void exprotServer(){
		List<String> list = exporter.getAllServerCode();
		String fileDir = getFileDir("db.serverDir");
		for (String server_code : list) {
			File file = createFile(fileDir+replace(server_code));
			ServiceData data = exporter.getOneServer(server_code);
			JSONFileUtil.storeServiceDataToJsonFile(data, file);
		}
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		createDeleteFile(delFileDir);
		logger.info("导出服务系统数据{}条,存储路径{}", list.size(), fileDir);
	}
	
	public static void exportTranEndPoint(){
		List<String> list = exporter.getAllChannelTranChannelCodeAndTranCode();
		String fileDir = getFileDir("db.endPointTranDir");
		for (String code : list) {
			File file = createFile(fileDir+replace(code));
			ServiceData data = exporter.getOneChannelTran(splitTrab(code)[0], splitTrab(code)[1]);
			JSONFileUtil.storeServiceDataToJsonFile(data, file);
		}
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		createDeleteFile(delFileDir);
		logger.info("导出EndPoint关联交易数据{}条,存储路径{}", list.size(), fileDir);
	}
	
	public static void exportTranServer(){
		List<String> list = exporter.getAllServerTranServerCodeAndTranCode();
		String fileDir = getFileDir("db.serverTranDir");
		for (String code : list) {
			File file = createFile(fileDir+replace(code));
			ServiceData data = exporter.getOneServerTran(splitTrab(code)[0], splitTrab(code)[1]);
			JSONFileUtil.storeServiceDataToJsonFile(data, file);
		}
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		createDeleteFile(delFileDir);
		logger.info("导出服务系统关联交易数据{}条,存储路径{}", list.size(), fileDir);
	}
	
	public static void exportService(){
		List<String> list = exporter.getAllServiceCode();
		String fileDir = getFileDir("db.serviceDir");
		for (String service_code : list) {
			File file = createFile(fileDir+replace(service_code));
			ServiceData data = exporter.getOneService(service_code);
			JSONFileUtil.storeServiceDataToJsonFile(data, file);
		}
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		createDeleteFile(delFileDir);
		logger.info("导出服务数据{}条,存储路径{}", list.size(), fileDir);
	}
	
	public static void exportMachine(){
		List<String> list = exporter.getAllMachineCode();
		String fileDir = getFileDir("db.deployDir");
		for (String machine_code : list) {
			ServiceData data = exporter.getOneMachine(machine_code);
			if(data.size() >= 5){
				ServiceData INSTANCE = data.getServiceData("INSTANCE");
				ServiceData PROCESSINSTANCE = data.getServiceData("PROCESSINSTANCE");
				if(INSTANCE.size() > 0){
					//服务器下的进程
					for (String keyInstance : INSTANCE.getKeys()) {
						ServiceData SKEYC = INSTANCE.getServiceData(keyInstance);
						ServiceData provessInstanceAll = PROCESSINSTANCE.getServiceData(keyInstance);
						if(provessInstanceAll.size() > 0){
							//进程下部署的EndPoint
							for (String pkey : provessInstanceAll.getKeys()) {
								ServiceData processData = provessInstanceAll.getServiceData(pkey);
								ServiceData singlInstance = new ServiceData();
								singlInstance.putString("MACHINE_CODE", data.getString("MACHINE_CODE"));
								singlInstance.putString("MACHINE_IP", data.getString("MACHINE_IP"));
								singlInstance.putString("MACHINE_NAME", data.getString("MACHINE_NAME"));
								
								ServiceData instance = new ServiceData();
								instance.putServiceData(keyInstance, SKEYC);
								singlInstance.putServiceData("INSTANCE", instance);
								
								ServiceData pInstance = new ServiceData();
								pInstance.putServiceData(pkey, processData);
								singlInstance.putServiceData("PROCESSINSTANCE", pInstance);
								File file = createFile(fileDir+replace(machine_code+"_"+keyInstance+"_"+pkey));
								JSONFileUtil.storeServiceDataToJsonFile(singlInstance, file);
							}
						}
					}
				}
			} else {
				File file = createFile(fileDir+replace(machine_code));
				JSONFileUtil.storeServiceDataToJsonFile(data, file);
			}
		}
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		createDeleteFile(delFileDir);
		logger.info("导出部署数据{}条,存储路径{}", list.size(), fileDir);
	}
	
	public static void exportDict(){
		List<String> list = exporter.getAllDictCode();
		String fileDir = getFileDir("db.dictDir");
		for (String dict_code : list) {
			File file = createFile(fileDir+replace(dict_code));
			ServiceData data = exporter.getOneDict(dict_code);
			JSONFileUtil.storeServiceDataToJsonFile(data, file);
		}
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		createDeleteFile(delFileDir);
		logger.info("导出数据字典数据{}条,存储路径{}", list.size(), fileDir);
	}
	
	public static void exportMode(){
		List<String> list = exporter.getAllModeCode();
		String fileDir = getFileDir("db.modeDir");
		for (String mode_code : list) {
			File file = createFile(fileDir+replace(mode_code));
			ServiceData data = exporter.getOneMode(mode_code);
			JSONFileUtil.storeServiceDataToJsonFile(data, file);
		}
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		createDeleteFile(delFileDir);
		logger.info("导出模式数据{}条,存储路径{}", list.size(), fileDir);
	}
	
	private static void deleteFloder(String fileDirPath) {
		File file = new File(fileDirPath);
		if (!file.exists()) {
			return;
		}
		File[] fileList = file.listFiles();
		for (File file2 : fileList) {
			if (file.exists() && file2.isFile()) {
				file.delete();
			} else if (file2.isDirectory()) {
				deleteFloder(file2.getAbsolutePath());
			}
			file2.delete();
		}
	}
	
}
