package compare;

import java.io.File;
import java.util.List;

import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.sdo.ServiceData;
import com.wk.util.FileUtil;

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
		logger.info("***Begin��ʼ�����ݿ⵼������,��ʼʱ��:{}***",getTime());
		//������ݿ�
		//�µ����󣺲���ȫ���������룬���Բ���������ݿ�
		/**
		 * ��Ҫ��յı� SYS_CHANNEL SYS_COMM SYS_DICT SYS_DICT_DETAIL
		 * SYS_GROUP_SVC_CHART SYS_INSTANCE SYS_MACHINE SYS_MAPPING SYS_MODE
		 * SYS_MODE_PARAM SYS_PROCESS_INSTANCE SYS_SAVE_DATAS SYS_SERVER
		 * SYS_SERVICE SYS_STRUCTURE SYS_TRAN_CHANNEL_PACKAGE
		 * SYS_TRAN_SERVER_PACKAGE
		 */
		/** ����˳��EndPoint������ϵͳ��EndPoint�������ס�����ϵͳ��������
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
		logger.info("***End�����ݿ⵼�����ݽ���,����ʱ��:{}***", getTime());
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
		logger.info("�ɹ�����EndPoint����{}��", count);
		// ɾ��EndPoint,����channel_codeɾ��
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneEndPoint(string);
			logger.info("�ɹ�ɾ��EndPoint��{}", string);
		}
	}
	
	public static void insertServer(){
		String fileDir = getFileDir("db.serverDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneServer(data);
		}
		logger.info("�ɹ��������ϵͳ����{}��", count);
		//ɾ��server,����server_codeɾ��
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneServer(string);
			logger.info("�ɹ�ɾ��Server��{}", string);
		}
	}
	
	public static void insertTranEndPoint(){
		String fileDir = getFileDir("db.endPointTranDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneTranEndPoint(data);
		}
		logger.info("�ɹ�����EndPoint������������{}��", count);
		//ɾ��EndPoint��������,����channel_code��tran_codeɾ��
		String delFileDir = fileDir+getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneTranEndPoint(splitTrab(string)[0],	splitTrab(string)[1]);
			logger.info("�ɹ�ɾ��EndPoint�������ף�{}", string);
		}
	}
	
	public static void insertTranServer() {
		String fileDir = getFileDir("db.serverTranDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneTranServer(data);
		}
		logger.info("�ɹ��������ϵͳ������������{}��", count);
		// ɾ�������������,����tran_codeɾ��
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneTranServerByTranCode(splitTrab(string)[1]);
			logger.info("�ɹ�ɾ������ϵͳ�������ף�{}", string);
		}
	}
	
	public static void insertService(){
		String fileDir = getFileDir("db.serviceDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneService(data);
		}
		logger.info("�ɹ������������{}��", count);
		// ɾ������,����service_codeɾ��
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			deleter.deleteOneService(string);
			logger.info("�ɹ�ɾ������{}", string);
		}
	}
	
	public static void insertMachine(){
		String fileDir = getFileDir("db.deployDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			//����ǰ��ɾ���Ѿ����ڵķ�����
			deleter.deleteOneMachine(data.getString("MACHINE_CODE"));
			count += impoter.insertOneMachine(data);
		}
		logger.info("�ɹ����벿������{}��", count);
		//TODO: ������̲��ṩɾ������
	}
	
	public static void insertDict(){
		String fileDir = getFileDir("db.dictDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOrUpdateOneDict(data);
		}
		logger.info("�ɹ����������ֵ�{}��", count);
		// ɾ�������ֵ䣬����ɾ�����������ֵ䣬Ҳ����ɾ���ֵ��е�һ���ֶ�
		String delFileDir = fileDir + getDeletedFileDir("db.deletedFile");
		List<String> pkey = JSONFileUtil.readFileToStringArray(new File(delFileDir));
		for (String string : pkey) {
			String[] keys = splitTrab(string);
			if(keys.length == 1){
				//ɾ�������ֵ�
				deleter.deleteOneDict(string);
				logger.info("�ɹ������ֵ䣺{}", string);
			}else if(keys.length == 2){
				//ɾ�������ֵ��е�һ���ֶ�
				deleter.deleteOneDictDetail(keys[0], keys[1]);
				logger.info("�ɹ������ֵ�{}�е��ֶΣ�{}", keys[0], keys[1]);
			}
		}
	}
	
	public static void insertMode(){
		String fileDir = getFileDir("db.modeDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOrUpdateOneMode(data);
		}
		logger.info("�ɹ�����ģʽ{}��", count);
		//TODO: ģʽ�ݲ��ṩɾ��
	}
	
	private static void commit(){
		Session session = DBSource.getDefault().getSession();
		session.commit();
		session.close();
		System.out.println("Commited!");
	}
}
