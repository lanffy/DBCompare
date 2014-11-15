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
		logger.info("***Begin��ʼ��������,��ʼʱ��:{}***",getTime());
		/** ����˳��EndPoint������ϵͳ��EndPoint�������ס�����ϵͳ��������
		 * ���񡢲��������ֵ䡢ģʽ
		 */
		exportEndPoint();
		exprotServer();
		exportTranEndPoint();
		exportTranServer();
		exportService();
		exportMachine();
		exportDict();
		exportMode();
		logger.info("***End�������ݽ���,����ʱ��:{}***", getTime());
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
		logger.info("������������{}��,�洢·��{}", list.size(), fileDir);
	}
	
	public static void exportMachine(){
		List<String> list = exporter.getAllMachineCode();
		String fileDir = getFileDir("db.deployDir");
		for (String machine_code : list) {
			File file = createFile(fileDir+replace(machine_code));
			ServiceData data = exporter.getOneMachine(machine_code);
			JSONFileUtil.storeServiceDataToJsonFile(data, file);
		}
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
		logger.info("����ģʽ����{}��,�洢·��{}", list.size(), fileDir);
	}
	
}
