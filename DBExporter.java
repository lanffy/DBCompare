package compare;

import java.io.File;
import java.util.List;

import com.wk.sdo.ServiceData;

/**
 * @description VRouter���ݿ⵼����
 * @author raoliang
 * @version 2014��11��9�� ����1:29:03
 */
public class DBExporter extends DBporter{
	
	public static void main(String[] args) {
		doExport();
	}
	
	public static void doExport() {
		System.out.println("begin Export...");
		logger.info("\n***Begin��ʼ��������,��ʼʱ��:{}***",getTime());
		/** ����˳��EndPoint������ϵͳ��EndPoint�������ס�����ϵͳ��������
		 * ���񡢲��������ֵ䡢ģʽ
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
		logger.info("\n***End�������ݽ���,����ʱ��:{}***", getTime());
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
		logger.info("����EndPoint����{}��,�洢·��{}", list.size(), fileDir);
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
		logger.info("��������ϵͳ����{}��,�洢·��{}", list.size(), fileDir);
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
		logger.info("����EndPoint������������{}��,�洢·��{}", list.size(), fileDir);
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
		logger.info("��������ϵͳ������������{}��,�洢·��{}", list.size(), fileDir);
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
		logger.info("������������{}��,�洢·��{}", list.size(), fileDir);
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
					//�������µĽ���
					for (String keyInstance : INSTANCE.getKeys()) {
						ServiceData SKEYC = INSTANCE.getServiceData(keyInstance);
						ServiceData provessInstanceAll = PROCESSINSTANCE.getServiceData(keyInstance);
						if(provessInstanceAll.size() > 0){
							//�����²����EndPoint
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
		logger.info("������������{}��,�洢·��{}", list.size(), fileDir);
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
		logger.info("���������ֵ�����{}��,�洢·��{}", list.size(), fileDir);
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
		logger.info("����ģʽ����{}��,�洢·��{}", list.size(), fileDir);
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
