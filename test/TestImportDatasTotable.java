package compare.test;

import com.wk.lang.Inject;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;
import com.wk.sdo.ServiceData;
import com.wk.test.TestCase;
import com.wk.util.JSON;
import com.wk.util.JSONCaseType;
import compare.ExportDatasFromDB;
import compare.ImportDatasToDB;
import compare.JSONFileUtil;


/**
 * @description
 * @author raoliang
 * @version 2014��10��31�� ����4:07:44
 */
public class TestImportDatasTotable extends TestCase {
	@Inject  ExportDatasFromDB exportService;
	@Inject  ImportDatasToDB importService;
	private final Log logger = LogFactory.getLog("dbcompare");
	String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	
	public void atest_insertEndPoint(){
		ServiceData endPoint = exportService.getOneEndPoint("npCHL");;
		endPoint.putString("CHANNEL_CODE", "testCHL");
		endPoint.putString("CHANNEL_NAME", "����EndPoint");
		ServiceData commData = endPoint.getServiceData("COMM_ID");
		commData.putString("CCODE", "test_tcp");
		endPoint.putServiceData("COMM_ID", commData);
		int i = importService.insertOneEndPoint(endPoint);
		assertEquals(i, 1);
		ServiceData testChl = exportService.getOneEndPoint("testCHL");
		assertEquals(testChl.getString("CHANNEL_CODE"), "testCHL");
		System.out.println(testChl);
	}
	
	public void atest_insertServer(){
		ServiceData serverData = exportService.getOneServer("inbankSRV");
		System.out.println("\n***�޸�ǰ***\n"+serverData);
		assertEquals(serverData.getString("SERVER_CODE"), "inbankSRV");
		serverData.putString("SERVER_CODE", "testSRV");
		serverData.putString("SERVER_NAME", "test����ϵͳ");
		ServiceData commData = serverData.getServiceData("COMM_ID");
		commData.putString("CCODE", "test_srv");
		serverData.getServiceData("REQ_PACKAGE_CONFIG").putString("SERVICE_CODE", "testSRV");
		serverData.getServiceData("RESP_PACKAGE_CONFIG").putString("SERVICE_CODE", "testSRV");
		serverData.getServiceData("ERR_PACKAGE_CONFIG").putString("SERVICE_CODE", "testSRV");
		serverData.getServiceData("IN_MAPPING").putString("REF_CODE", "testSRV");
		serverData.getServiceData("OUT_MAPPING").putString("REF_CODE", "testSRV");
		serverData.getServiceData("ERROR_MAPPING").putString("REF_CODE", "testSRV");
		serverData.getServiceData("IN_MAPPING").putString("MAPPING_CODE", "testSRV_resp");
		serverData.getServiceData("OUT_MAPPING").putString("MAPPING_CODE", "testSRV_req");
		serverData.getServiceData("ERROR_MAPPING").putString("MAPPING_CODE", "testSRV_err");
		System.out.println("\n***�޸ĺ�***\n"+serverData);
		int num = importService.insertOneServer(serverData);
		assertEquals(num, 1);
		serverData = exportService.getOneServer("testSRV");
		assertEquals("testSRV", serverData.getString("SERVER_CODE"));
		assertEquals(serverData.getString("SERVER_NAME"), "test����ϵͳ");
		commData = serverData.getServiceData("COMM_ID");
		assertEquals(commData.getString("CCODE"), "test_srv");
	}
	
	public void atest_insertԭ��Service(){
		ServiceData serviceData = exportService.getOneService("0001");
		System.out.println("\n****��������****\n"+serviceData);
		JSONFileUtil.storeServiceDataToJsonFile(serviceData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("SERVICE_CODE", "0011");
		fileData.putString("SERVICE_NAME", "0011");
		fileData.getServiceData("REQ_STRU").putString("SERVICE_CODE", "0011");
		fileData.getServiceData("RESP_STRU").putString("SERVICE_CODE", "0011");
		System.out.println("\n****��������****\n"+fileData);
		int num = importService.insertOneService(fileData);
		assertEquals(num, 1);
	}
	
	public void atest_insert��չService(){
		ServiceData serviceData = exportService.getOneService("0002");
		System.out.println("\n****��������****\n"+serviceData);
		JSONFileUtil.storeServiceDataToJsonFile(serviceData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("SERVICE_CODE", "0022");
		fileData.putString("SERVICE_NAME", "0022");
		ServiceData req = fileData.getServiceData("REQ_STRU");
		if(req.size()>0){
			req.putString("SERVICE_CODE", "0022");
		}
		ServiceData resp = fileData.getServiceData("RESP_STRU");
		if(resp.size()>0){
			resp.putString("SERVICE_CODE", "0022");
		}
		ServiceData err = fileData.getServiceData("ERR_STRU");
		if(err.size()>0){
			err.putString("SERVICE_CODE", "0022");
		}
		System.out.println("\n****��������****\n"+fileData);
		int num = importService.insertOneService(fileData);
		assertEquals(num, 1);
	}
	
	public void atest_insert���Service(){
		ServiceData serviceData = exportService.getOneService("0003");
		System.out.println("\n****��������****\n"+serviceData);
		JSONFileUtil.storeServiceDataToJsonFile(serviceData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("SERVICE_CODE", "0033");
		fileData.putString("SERVICE_NAME", "0033");
		ServiceData req = fileData.getServiceData("REQ_STRU");
		if(req.size()>0){
			req.putString("SERVICE_CODE", "0033");
		}
		ServiceData resp = fileData.getServiceData("RESP_STRU");
		if(resp.size()>0){
			resp.putString("SERVICE_CODE", "0033");
		}
		ServiceData err = fileData.getServiceData("ERR_STRU");
		if(err.size()>0){
			err.putString("SERVICE_CODE", "0033");
		}
		System.out.println("\n****��������****\n"+fileData);
		int num = importService.insertOneService(fileData);
		assertEquals(num, 1);
	}
	
	public void atest_insert����(){
		ServiceData expandData = exportService.getOneMachine("001");
		System.out.println("***�޸�ǰ***\n"+expandData);
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("MACHINE_CODE", "002");
		fileData.putString("MACHINE_IP", "127.0.0.1");
		fileData.putString("MACHINE_NAME", "002");
		
		ServiceData instance = fileData.getServiceData("INSTANCE");
		String instanceStr = JSON.fromServiceData(instance, JSONCaseType.DEFAULT).replace("001", "002");
		instance = JSON.toServiceDataByType(instanceStr, JSONCaseType.DEFAULT);
		fileData.putServiceData("INSTANCE", instance);
		
		ServiceData processInstance = fileData.getServiceData("PROCESSINSTANCE");
		String processinstanceStr = JSON.fromServiceData(processInstance, JSONCaseType.DEFAULT).replace("001", "002");
		processInstance = JSON.toServiceDataByType(processinstanceStr, JSONCaseType.DEFAULT);
		fileData.putServiceData("PROCESSINSTANCE", processInstance);
		
		System.out.println("***�޸ĺ�***\n"+fileData);
		int num = importService.insertOneMachine(fileData);
		assertEquals(num, 1);
	}
	
	public void atest_insertDict(){
		ServiceData dictData = exportService.getOneDict("global");
		logger.info("��������:\n{}", dictData);
		JSONFileUtil.storeServiceDataToJsonFile(dictData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("DICT_CODE", "test");
		fileData.putString("DICT_NAME", "testname");
		fileData.putString("IS_GLOBAL", "0");
		ServiceData detailData = fileData.getServiceData("DICT_DETAIL");
		String detailStr = JSON.fromServiceData(detailData, JSONCaseType.DEFAULT).replace("global", "test");
		detailData = JSON.toServiceDataByType(detailStr, JSONCaseType.DEFAULT);
		fileData.putServiceData("DICT_DETAIL", detailData);
		logger.info("��������:\n{}", fileData);
		int num = importService.insertOneDict(fileData);
		logger.info("�ɹ����������ֵ�{}��", num);
		assertEquals(num, 1);
	}
	
	public void test_insertMode() {
		ServiceData modeData = exportService.getOneMode("vrouterclient_lu");
		logger.info("��������:\n{}", modeData);
		JSONFileUtil.storeServiceDataToJsonFile(modeData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("MODE_CODE", "test_mode1");
		fileData.putString("MODE_NAME", "VRouter�ͻ��˰�ģʽ,����Сд,��Ӧ�����Ӧ�����Ӧ���");
		logger.info("��������:\n{}", fileData);
		int num = importService.insertOneMode(fileData);
		logger.info("�ɹ�����ģʽ{}��", num);
		assertEquals(num, 1);
	}
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("********�������Ĳ��԰����ָ���************");
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
//		System.out.println("Commited!");
	}
}
