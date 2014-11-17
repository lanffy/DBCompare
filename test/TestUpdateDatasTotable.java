package compare.test;

import com.wk.db.DBSource;
import com.wk.db.Session;
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
 * @version 2014年10月31日 下午4:07:44
 */
public class TestUpdateDatasTotable extends TestCase {
	@Inject  ExportDatasFromDB exportService;
	@Inject  ImportDatasToDB importService;
	private final Log logger = LogFactory.getLog("dbcompare");
	String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("********华丽丽的测试案例分割线************");
	}
	
	public void atest_修改EndPoint基本参数(){
		ServiceData endPoint = exportService.getOneEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		endPoint.putString("CHANNEL_NAME", "1338修改了EndPoint");
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneEndPoint("testCHL");
		assertEquals(confirmData.getString("CHANNEL_NAME"), "1338修改了EndPoint");
	}
	
	public void atest_EndPoint修改通讯参数(){
		ServiceData endPoint = exportService.getOneEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		ServiceData commData = endPoint.getServiceData("COMM_ID");
		commData.putString("CCODE", "test_tcp_updated1");
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneEndPoint("testCHL");
		ServiceData confirmCommData = confirmData.getServiceData("COMM_ID");
		assertEquals(confirmCommData.getString("CCODE"), "test_tcp_updated1");
	}
	
	public void atest_修改EndPoint接口配置(){
		ServiceData endPoint = exportService.getOneEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		ServiceData reqConfigData = endPoint.getServiceData("REQ_PACKAGE_CONFIG");
		ServiceData reqConfigContentData = reqConfigData.getServiceData("STRUCTURE_CONTENT"); 
		assertEquals(reqConfigContentData.getString("SDATAS"), "{fdatas:[], is_strict:\"0\", package_mode:\"vrouterserver\", package_mode_name:\"VRouter服务器包模式\"}");
		reqConfigContentData.putString("SDATAS", "{fdatas:[], is_strict:\"1\", package_mode:\"vrouterserver\", package_mode_name:\"VRouter服务器包模式\"}");
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
	}
	
	public void atest_修改EndPoint映射(){
		ServiceData endPoint = exportService.getOneEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		ServiceData errMapData = endPoint.getServiceData("IN_MAPPING");
		assertEquals(errMapData.getString("MAPPING_NAME"), "test增加输入映射");
		errMapData.putString("MAPPING_NAME", "0929test增加输入映射");
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneEndPoint("testCHL");
		System.out.println(confirmData);
		ServiceData confirmErrMapData = confirmData.getServiceData("IN_MAPPING");
		assertEquals(confirmErrMapData.getString("MAPPING_NAME"), "0929test增加输入映射");
	}
	
	public void atest_增加EndPoint映射(){
		ServiceData endPoint = exportService.getOneEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		ServiceData errMapData = endPoint.getServiceData("ERROR_MAPPING");
		assertEquals(errMapData.getString("MAPPING_NAME"), "test错误映射");
		errMapData.putString("MAPPING_NAME", "test增加输入映射");
		endPoint.putServiceData("IN_MAPPING", errMapData);
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneEndPoint("testCHL");
		System.out.println(confirmData);
		ServiceData confirmErrMapData = confirmData.getServiceData("IN_MAPPING");
		assertEquals(confirmErrMapData.getString("MAPPING_NAME"), "test增加输入映射");
	}
	
	public void atest_修改Server基本参数(){
		ServiceData server = exportService.getOneServer("testSRV");
		System.out.println(server+"\n*************************************\n");
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		server.putString("SERVER_NAME", "1703修改了Server名称");
		System.out.println(server+"\n*************************************\n");
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneServer("testSRV");
		assertEquals(confirmData.getString("SERVER_NAME"), "1703修改了Server名称");
	}
	
	public void atest_修改Server的通讯参数(){
		ServiceData server = exportService.getOneServer("testSRV");
		System.out.println(server+"\n*************************************\n");
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		ServiceData commData = server.getServiceData("COMM_ID");
		commData.putString("CCODE", "test_srv_updated");
		System.out.println(server+"\n*************************************\n");
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneServer("testSRV");
		assertEquals(confirmData.getServiceData("COMM_ID").getString("CCODE"), "test_srv_updated");
	}
	
	public void atest_修改Server配置接口(){
		ServiceData server = exportService.getOneServer("testSRV");
		System.out.println(server+"\n*************************************\n");
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		ServiceData configData = server.getServiceData("REQ_PACKAGE_CONFIG");
		ServiceData contentData = configData.getServiceData("STRUCTURE_CONTENT");
		contentData.putString("SDATAS", "{fdatas:[{field_code:\"O1MGID\", field_name:\"O1MGID\", field_type:\"string\", field_length:7, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:10}], is_strict:\"1\", package_mode:\"outsys_mode\", package_mode_name:\"outsys_mode\"}");
		System.out.println(server+"\n*************************************\n");
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneServer("testSRV");
		ServiceData configConfirmData = confirmData.getServiceData("REQ_PACKAGE_CONFIG");
		assertEquals(configConfirmData.getServiceData("STRUCTURE_CONTENT").getString("SDATAS"), "{fdatas:[{field_code:\"O1MGID\", field_name:\"O1MGID\", field_type:\"string\", field_length:7, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:10}], is_strict:\"1\", package_mode:\"outsys_mode\", package_mode_name:\"outsys_mode\"}");
	}
	
	public void atest_修改Server映射(){
		ServiceData server = exportService.getOneServer("testSRV");
		System.out.println("\n************修改前****************\n"+server);
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		ServiceData mapData = server.getServiceData("IN_MAPPING");
		assertEquals(mapData.getString("MAPPING_NAME"), "核心服务系统响应映射");
		mapData.putString("MAPPING_NAME", "0952修改核心服务系统响应映射");
		System.out.println("\n************修改后*******************\n"+server);
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneServer("testSRV");
		System.out.println("\n***********updateServer后***********\n"+confirmData);
		ServiceData mapConfirmData = confirmData.getServiceData("IN_MAPPING");
		assertEquals(mapConfirmData.getString("MAPPING_NAME"), "0952修改核心服务系统响应映射");
	}
	
	public void atest_增加Server映射(){
		ServiceData server = exportService.getOneServer("testSRV");
		System.out.println("\n************修改前****************\n"+server);
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		ServiceData mapData = server.getServiceData("ERROR_MAPPING");
		server.putServiceData("OUT_MAPPING", mapData);
		System.out.println("\n************修改后*******************\n"+server);
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneServer("testSRV");
		ServiceData mapConfirmData = confirmData.getServiceData("OUT_MAPPING");
		assertEquals(mapConfirmData.getString("MAPPING_NAME"), "核心服务系统错误映射");
	}
	
	public void atest_修改组合服务的服务名称(){
		ServiceData service = exportService.getOneService("0033");
		System.out.println("\n************修改前****************\n"+service);
		assertEquals(service.getString("SERVICE_CODE"), "0033");
		assertEquals(service.getString("SERVICE_NAME"), "0033");
		JSONFileUtil.storeServiceDataToJsonFile(service, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("SERVICE_NAME", "0033updated");
		int num = importService.updateOneService(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneService("0033");
		System.out.println("\n************修改后****************\n"+confirmData);
		assertEquals(confirmData.getString("SERVICE_NAME"), "0033updated");
	}
	
	public void atest_修改组合服务的接口(){
		ServiceData service = exportService.getOneService("0033");
		System.out.println("\n************修改前****************\n"+service);
		assertEquals(service.getString("SERVICE_CODE"), "0033");
		assertEquals(service.getString("SERVICE_NAME"), "0033updated");
		JSONFileUtil.storeServiceDataToJsonFile(service, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		ServiceData errData = fileData.getServiceData("ERR_STRU");
		errData.putServiceData("STRUCTURE_CONTENT", fileData.getServiceData("RESP_STRU").getServiceData("STRUCTURE_CONTENT"));
		int num = importService.updateOneService(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneService("0033");
		System.out.println("\n************修改后****************\n"+confirmData);
	}
	
	public void atest_删除组合服务的接口(){
		ServiceData service = exportService.getOneService("0033");
		System.out.println("\n************修改前****************\n"+service);
		assertEquals(service.getString("SERVICE_CODE"), "0033");
		assertEquals(service.getString("SERVICE_NAME"), "0033updated");
		JSONFileUtil.storeServiceDataToJsonFile(service, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putServiceData("ERR_STRU", null);
		int num = importService.updateOneService(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneService("0033");
		System.out.println("\n************修改后****************\n"+confirmData);
	}
	
	public void atest_修改组合服务的流程图(){
		ServiceData service = exportService.getOneService("0033");
		ServiceData service8310 = exportService.getOneService("8310");
		System.out.println("\n************修改前****************\n"+service);
		assertEquals(service.getString("SERVICE_CODE"), "0033");
		assertEquals(service.getString("SERVICE_NAME"), "0033updated");
		JSONFileUtil.storeServiceDataToJsonFile(service, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("CONTENT", service8310.getString("CONTENT"));
		int num = importService.updateOneService(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneService("0033");
		System.out.println("\n************修改后****************\n"+confirmData);
	}
	
	public void atest_删除组合服务的流程图(){
		ServiceData service = exportService.getOneService("0033");
		System.out.println("\n************修改前****************\n"+service);
		assertEquals(service.getString("SERVICE_CODE"), "0033");
		assertEquals(service.getString("SERVICE_NAME"), "0033updated");
		JSONFileUtil.storeServiceDataToJsonFile(service, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("CONTENT", "");
		int num = importService.updateOneService(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneService("0033");
		System.out.println("\n************修改后****************\n"+confirmData);
	}
	
	public void atest_修改原子服务名称(){
		ServiceData service = exportService.getOneService("0001");
		System.out.println("\n************修改前****************\n"+service);
		assertEquals(service.getString("SERVICE_CODE"), "0001");
		assertEquals(service.getString("SERVICE_NAME"), "0001");
		JSONFileUtil.storeServiceDataToJsonFile(service, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("SERVICE_NAME", "0001updated");
		int num = importService.updateOneService(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneService("0001");
		System.out.println("\n************修改后****************\n"+confirmData);
		assertEquals(confirmData.getString("SERVICE_NAME"), "0001updated");
	}
	
	public void atest_修改扩展服务名称(){
		ServiceData service = exportService.getOneService("0002");
		System.out.println("\n************修改前****************\n"+service);
		assertEquals(service.getString("SERVICE_CODE"), "0002");
		assertEquals(service.getString("SERVICE_NAME"), "0002");
		JSONFileUtil.storeServiceDataToJsonFile(service, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("SERVICE_NAME", "0002updated");
		int num = importService.updateOneService(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneService("0002");
		System.out.println("\n************修改后****************\n"+confirmData);
		assertEquals(confirmData.getString("SERVICE_NAME"), "0002updated");
	}
	
	public void atest_修改扩展服务的扩展服务名称(){
		ServiceData service = exportService.getOneService("0002");
		System.out.println("\n************修改前****************\n"+service);
		assertEquals(service.getString("SERVICE_CODE"), "0002");
		assertEquals(service.getString("EXTEND_SERVICE_NAME"), "name");
		JSONFileUtil.storeServiceDataToJsonFile(service, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("EXTEND_SERVICE_NAME", "nameUpdated");
		int num = importService.updateOneService(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneService("0002");
		System.out.println("\n************修改后****************\n"+confirmData);
		assertEquals(confirmData.getString("EXTEND_SERVICE_NAME"), "nameUpdated");
	}
	
	public void atest_修改服务器名称(){
		ServiceData expandData = exportService.getOneMachine("001");
		System.out.println("\n************修改前****************\n"+expandData);
		assertEquals(expandData.getString("MACHINE_CODE"), "001");
		assertEquals(expandData.getServiceData("INSTANCE").getServiceData("001").getString("MACHINE_CODE"), "001");
		assertEquals(expandData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").getString("CHANNEL_CODE"), "tbCHL");
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("MACHINE_NAME", "001updated");
		int num = importService.updateOneMachine(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneMachine("001");
		assertEquals(confirmData.getString("MACHINE_NAME"), "001updated");
		System.out.println("\n************修改后****************\n"+confirmData);
	}
	
	public void atest_修改服务器ip(){
		ServiceData expandData = exportService.getOneMachine("001");
		System.out.println("\n************修改前****************\n"+expandData);
		assertEquals(expandData.getString("MACHINE_CODE"), "001");
		assertEquals(expandData.getServiceData("INSTANCE").getServiceData("001").getString("MACHINE_CODE"), "001");
		assertEquals(expandData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").getString("CHANNEL_CODE"), "tbCHL");
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.putString("MACHINE_IP", "localhost");
		int num = importService.updateOneMachine(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneMachine("001");
		assertEquals(confirmData.getString("MACHINE_IP"), "localhost");
		System.out.println("\n************修改后****************\n"+confirmData);
	}
	
	public void atest_修改服务器进程名称(){
		ServiceData expandData = exportService.getOneMachine("001");
		System.out.println("\n************修改前****************\n"+expandData);
		assertEquals(expandData.getString("MACHINE_CODE"), "001");
		assertEquals(expandData.getServiceData("INSTANCE").getServiceData("001").getString("MACHINE_CODE"), "001");
		assertEquals(expandData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").getString("CHANNEL_CODE"), "tbCHL");
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.getServiceData("INSTANCE").getServiceData("001").putString("SKEYD", "001updated2");
		int num = importService.updateOneMachine(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneMachine("001");
		assertEquals(confirmData.getServiceData("INSTANCE").getServiceData("001").getString("SKEYD"), "001updated2");
		System.out.println("\n************修改后****************\n"+confirmData);
	}
	
	public void atest_修改服务器下部署EndPoint的端口(){
		ServiceData expandData = exportService.getOneMachine("001");
		System.out.println("\n************修改前****************\n"+expandData);
		assertEquals(expandData.getString("MACHINE_CODE"), "001");
		assertEquals(expandData.getServiceData("INSTANCE").getServiceData("001").getString("MACHINE_CODE"), "001");
		assertEquals(expandData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").getString("CHANNEL_CODE"), "tbCHL");
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		fileData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").putString("BIND_ADDRESS", "9555");
		int num = importService.updateOneMachine(fileData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getOneMachine("001");
		assertEquals(confirmData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").getString("BIND_ADDRESS"), "9555");
		System.out.println("\n************修改后****************\n"+confirmData);	
	}
	
	public void atest_修改数据字典字段(){
		ServiceData dictData = exportService.getOneDict("global");
		logger.info("导出数据:\n{}", dictData);
		JSONFileUtil.storeServiceDataToJsonFile(dictData, filePath);
		ServiceData updateData = new ServiceData();
		updateData.putString("DICT_CODE", "global");
		updateData.putString("DICT_NAME", "全局数据字典");
		updateData.putString("IS_GLOBAL", "1");
		ServiceData detailupdateData = new ServiceData();
		ServiceData detaildictData = new ServiceData();
		detaildictData.putString("DICT_CODE", "global");
		detaildictData.putString("FIELD_CODE", "AAA");
		detaildictData.putString("FIELD_NAME", "aaaname");
		detaildictData.putString("FIELD_TYPE", "double");
		detaildictData.putString("FIELD_LENGTH", "10");
		detaildictData.putString("FIELD_SCALE", "2");
		detailupdateData.putServiceData("AAA", detaildictData);
		updateData.putServiceData("DICT_DETAIL", detailupdateData);
		logger.info("导入数据:\n{}", updateData);
		int num = importService.insertOrUpdateOneDict(updateData);
		logger.info("成功插入数据字典{}个", num);
		assertEquals(num, 0);
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
//		System.out.println("Commited!");
	}
}
