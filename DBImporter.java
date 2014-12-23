package compare;

import java.io.File;
import java.util.List;

import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.sdo.ServiceData;

/**
 * @description VRouter���ݿ⵼����
 * @author raoliang
 * @version 2014��11��9�� ����4:38:36
 */
public class DBImporter extends DBporter{
	
	public static void main(String[] args) {
		doImport();
	}
	
	public static void doImport() {
		System.out.println("begin Import...");
		logger.info("\n***Begin��ʼ�����ݿ⵼������,��ʼʱ��:{}***",getTime());
		//������ݿ�
		//�µ�����:����ȫ����������,���Բ���������ݿ�
		/**
		 * ��Ҫ��յı�: SYS_CHANNEL SYS_COMM SYS_DICT SYS_DICT_DETAIL
		 * SYS_GROUP_SVC_CHART SYS_INSTANCE SYS_MACHINE SYS_MAPPING SYS_MODE
		 * SYS_MODE_PARAM SYS_PROCESS_INSTANCE SYS_SAVE_DATAS SYS_SERVER
		 * SYS_SERVICE SYS_STRUCTURE SYS_TRAN_CHANNEL_PACKAGE
		 * SYS_TRAN_SERVER_PACKAGE
delete from SYS_CHANNEL;
delete from SYS_COMM;
delete from SYS_DICT;
delete from SYS_DICT_DETAIL;
delete from SYS_GROUP_SVC_CHART;
delete from SYS_INSTANCE;
delete from SYS_MACHINE;
delete from SYS_MAPPING;
delete from SYS_MODE;
delete from SYS_MODE_PARAM;
delete from SYS_PROCESS_INSTANCE;
delete from SYS_SAVE_DATAS;
delete from SYS_SERVER;
delete from SYS_SERVICE;
delete from SYS_STRUCTURE;
delete from SYS_TRAN_CHANNEL_PACKAGE;
delete from SYS_TRAN_SERVER_PACKAG;

		 */
		/** ����˳��:EndPoint������ϵͳ��EndPoint�������ס�����ϵͳ��������
		 * ���񡢲��������ֵ䡢ģʽ
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
		logger.info("\n***End�����ݿ⵼�����ݽ���,����ʱ��:{}***", getTime());
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
		if (count != 0)
			logger.info("�ɹ�������޸�EndPoint����{}��", count);
		// ɾ��EndPoint,����channel_codeɾ��
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			int num = deleter.deleteOneEndPoint(string);
			if(num == 0){
				logger.warn("EndPoint������:{}", string);
			}else {
				logger.info("�ɹ�ɾ��EndPoint:{}", string);
			}
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
		if (count != 0)
			logger.info("�ɹ�������޸ķ���ϵͳ����{}��", count);
		//ɾ��server,����server_codeɾ��
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			int num = deleter.deleteOneServer(string);
			if(num == 0){
				logger.warn("Server������:{}", string);
			}else {
				logger.info("�ɹ�ɾ��Server:{}", string);
			}
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
		if (count != 0)
			logger.info("�ɹ�������޸�EndPoint������������{}��", count);
		//ɾ��EndPoint��������,����channel_code��tran_codeɾ��
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			int num = deleter.deleteOneTranEndPoint(splitTrab(string)[0],	splitTrab(string)[1]);
			if(num == 0){
				logger.warn("EndPoint�������ײ�����:{}", string);
			}else {
				logger.info("�ɹ�ɾ��EndPoint��������:{}", string);
			}
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
		if (count != 0)
			logger.info("�ɹ�������޸ķ���ϵͳ������������{}��", count);
		// ɾ�������������,����tran_codeɾ��
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			int num = deleter.deleteOneTranServerByTranCode(splitTrab(string)[1]);
			if(num == 0){
				logger.warn("����ϵͳ�������ײ�����:{}", string);
			}else {
				logger.info("�ɹ�ɾ������ϵͳ��������:{}", string);
			}
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
		if (count != 0)
			logger.info("�ɹ�������޸ķ�������{}��", count);
		// ɾ������,����service_codeɾ��
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			int num = deleter.deleteOneService(string);
			if(num == 0){
				logger.warn("���񲻴���:{}", string);
			}else {
				logger.info("�ɹ�ɾ������:{}", string);
			}
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
		if (count != 0)
			logger.info("�ɹ�������޸Ĳ�������{}��", count);
		/**
		 * ɾ���ļ�������ʽ
		 * 001 ���������� 
		 * 001>001 ����������>���̱�ʶ���� 
		 * 001>001>CHL ����������>���̱�ʶ����>EndPoint����
		 */
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			String[] keyarray = splitTrab(string);
			if(keyarray.length == 1){
				int del = deleter.deleteOneMachine(keyarray[0]);
				if(del == 0){
					logger.warn("������������:{}", keyarray[0]);
				}else {
					logger.info("�ɹ�ɾ��������{},ͬʱɾ���˷������µĽ����Լ��ý����²����EndPoint", keyarray[0]);
				}
			}else if(keyarray.length == 2){
				int del = deleter.deleteOneInstance(keyarray[0], keyarray[1]);
				if(del == 0){
					logger.warn("������{}������,���߷�����{}��û�н���{}", keyarray[0], keyarray[0], keyarray[1]);
				}else {
					logger.info("�ɹ�ɾ��������{}�µĽ���{}�Լ��ý����²����EndPoint", keyarray[0], keyarray[1]);
				}
			}else if(keyarray.length == 3){
				int del = deleter.deleteOneProcessInstance(keyarray[0], keyarray[1], keyarray[2]);
				if(del == 0){
					logger.warn("������{}������,���߷�������û�н���{},���߽�����û�в���EndPoint{}", keyarray[0], keyarray[1], keyarray[2]);
				}else {
					logger.info("�ɹ�ɾ��������{}�µĽ���{}�²����EndPoint:{}", keyarray[0], keyarray[1], keyarray[2]);
				}
			}else{
				logger.warn("ɾ�����������ļ��е�������ʽ����ȷ:{}", string);
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
		if (count != 0)
			logger.info("�ɹ�������޸������ֵ�{}��", count);
		// ɾ�������ֵ�,����ɾ�����������ֵ�,Ҳ����ɾ���ֵ��е�һ���ֶ�
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			String[] keys = splitTrab(string);
			if(keys.length == 1){
				//ɾ�������ֵ�
				int num = deleter.deleteOneDict(string);
				if(num == 0){
					logger.warn("�����ֵ䲻����:{}", string);
				}else {
					logger.info("�ɹ�ɾ�������ֵ�:{}", string);
				}
			}else if(keys.length == 2){
				//ɾ�������ֵ��е�һ���ֶ�
				int num = deleter.deleteOneDictDetail(keys[0], keys[1]);
				if(num == 0){
					logger.warn("�����ֵ�{}������,���������ֵ���û���ֶ�{}", keys[0], keys[1]);
				}else {
					logger.info("�ɹ�ɾ�������ֵ�{}�µ��ֶ�{}", keys[0], keys[1]);
				}
			}else{
				logger.warn("ɾ�������ֵ������ļ��е�������ʽ����ȷ:{}", string);
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
		if (count != 0)
			logger.info("�ɹ�������޸�ģʽ{}��", count);
		//TODO: ģʽ�ݲ��ṩɾ��
	}
	
	private static void commit(){
		Session session = DBSource.getDefault().getSession();
		if(session != null){
			session.commit();
			session.close();
			System.out.println("Commited!");
		}else {
			System.out.println("Nothing Changed!");
		}
	}
}
