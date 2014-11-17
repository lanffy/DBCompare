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
	private static Session session = DBSource.getDefault().openSession();
	public static void doImport() {
		logger.info("***Begin��ʼ�����ݿ⵼������,��ʼʱ��:{}***",getTime());
		//������ݿ�
		/**
		 * ��Ҫ��յı� SYS_CHANNEL SYS_COMM SYS_DICT SYS_DICT_DETAIL
		 * SYS_GROUP_SVC_CHART SYS_INSTANCE SYS_MACHINE SYS_MAPPING SYS_MODE
		 * SYS_MODE_PARAM SYS_PROCESS_INSTANCE SYS_SAVE_DATAS SYS_SERVER
		 * SYS_SERVICE SYS_STRUCTURE SYS_TRAN_CHANNEL_PACKAGE
		 * SYS_TRAN_SERVER_PACKAGE
		 */
		emptyDataBase();
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
	}
	
	public static void insertTranServer(){
		String fileDir = getFileDir("db.serverTranDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneTranServer(data);
		}
		logger.info("�ɹ��������ϵͳ������������{}��", count);
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
	}
	
	public static void insertMachine(){
		String fileDir = getFileDir("db.deployDir");
		List<File> fileList = FileUtil.listAllFiles(new File(fileDir));
		int count = 0;
		for (File file : fileList) {
			ServiceData data = JSONFileUtil.loadJsonFileToServiceData(file);
			count += impoter.insertOneMachine(data);
		}
		logger.info("�ɹ����벿������{}��", count);
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
		logger.info("��ձ�����");
	}
	
	private static void commit(){
//		Session session = DBSource.getDefault().getSession();
		session.commit();
		session.close();
		System.out.println("Commited!");
	}
}
