package compare.test;

import java.util.List;

import com.wk.lang.Inject;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;
import com.wk.sdo.ServiceData;
import com.wk.test.TestCase;
import compare.ExportDatasFromDB;
import compare.JSONFileUtil;


/**
 * @description
 * @author raoliang
 * @version 2014年10月31日 下午1:30:14
 */
public class TestExportDatasFromTable extends TestCase {
	@Inject static ExportDatasFromDB exporter;
	private final Log logger = LogFactory.getLog("dbcompare");
	String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("***华丽丽的测试案例分割线***");
	}
	
	public void atest_getAllChannel_code(){
		List<String> listcode = exporter.getAllChannelCode();
		System.out.println("get channel_code:"+listcode.size());
		for (String string : listcode) {
			System.out.println(string);
		}
	}
	
	public void atest_getAllServer_code(){
		List<String> listcode = exporter.getAllServerCode();
		System.out.println("get server_code:"+listcode.size());
		for (String string : listcode) {
			System.out.println(string);
		}
	}
	
	public void atest_getAllTranChannel_code(){
		List<String> listcode = exporter.getAllChannelTranChannelCodeAndTranCode();
		System.out.println("get tran channel_code:"+listcode.size());
		for (String string : listcode) {
			System.out.println(string);
		}
	}
	
	public void atest_getAllTranServer_code(){
		List<String> listcode = exporter.getAllServerTranServerCodeAndTranCode();
		System.out.println("get tran server_code:"+listcode.size());
		for (String string : listcode) {
			System.out.println(string);
		}
	}
	
	public void atest_getAllServiceCode(){
		List<String> listcode = exporter.getAllServiceCode();
		System.out.println("get service_code:"+listcode.size());
		for (String string : listcode) {
			System.out.println(string);
		}
	}
	
	public void atest_getAllMachineCode(){
		List<String> listcode = exporter.getAllMachineCode();
		System.out.println("get machine_code:"+listcode.size());
		for (String string : listcode) {
			System.out.println(string);
		}
	}
	
	public void atest_getAllDictCode(){
		List<String> listcode = exporter.getAllDictCode();
		System.out.println("get dict_code:"+listcode.size());
		for (String string : listcode) {
			System.out.println(string);
		}
	}
	
	public void atest_getAllModeCode(){
		List<String> listcode = exporter.getAllModeCode();
		System.out.println("get mode_code:"+listcode.size());
		for (String string : listcode) {
			System.out.println(string);
		}
	}
	
	public void atest_不存在的EndPoint(){
		ServiceData data = exporter.getOneEndPoint("vmenu2cardCHLaaa");
		assertNull(data);
	}
	
	public void atest_存在的EndPoint(){
		ServiceData data = exporter.getOneEndPoint("ATMCHL");
		assertNotNull(data);
	}
	
	public void atest_EndPoint通讯参数不存在(){
		ServiceData data = exporter.getOneEndPoint("ATMCHL");
		assertNull(data.getServiceData("COMM_ID"));
	}
	
	public void atest_EndPoint报文配置为空(){
		ServiceData data = exporter.getOneEndPoint("ATMCHL");
		assertNull(data.getServiceData("REQ_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("RESP_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("ERR_PACKAGE_CONFIG"));
	}
	
	public void atest_EndPoint系统映射为空(){
		ServiceData data = exporter.getOneEndPoint("ATMCHL");
		assertNull(data.getServiceData("IN_MAPPING"));
		assertNull(data.getServiceData("OUT_MAPPING"));
		assertNull(data.getServiceData("ERROR_MAPPING"));
	}
	
	
	public void atest_不存在的服务系统(){
		ServiceData data = exporter.getOneServer("aaaa");
		assertNull(data);
	}
	
	public void atest_存在的服务系统(){
		ServiceData data = exporter.getOneServer("appblocksSRV");
		assertNotNull(data);
	}
	
	public void atest服务系统通讯参数不存在(){
		ServiceData data = exporter.getOneServer("appblocksSRV");
		assertNull(data.getServiceData("COMM_ID"));
	}
	
	public void atest服务系统报文配置为空(){
		ServiceData data = exporter.getOneServer("appblocksSRV");
		assertNull(data.getServiceData("REQ_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("RESP_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("ERR_PACKAGE_CONFIG"));
	}
	
	public void atest服务系统系统映射为空(){
		ServiceData data = exporter.getOneServer("appblocksSRV");
		assertNull(data.getServiceData("IN_MAPPING"));
		assertNull(data.getServiceData("OUT_MAPPING"));
		assertNull(data.getServiceData("ERROR_MAPPING"));
	}
	
	public void atest_不存在的EndPoint关联交易(){
		ServiceData data = exporter.getOneChannelTran("cardCHL", "0000");
		assertNull(data);
		ServiceData data2 = exporter.getOneChannelTran("aCHL", "0052");
		assertNull(data2);
	}
	
	public void atest_存在的EndPoint关联交易(){
		ServiceData data = exporter.getOneChannelTran("cardCHL", "0061");
		assertNotNull(data);
	}
	
	public void atest_EndPoint关联交易报文配置错误(){
		ServiceData data = exporter.getOneChannelTran("cardCHL", "0052");
		assertNull(data.getServiceData("REQ_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("RESP_PACKAGE_CONFIG"));
	}
	
	public void atest_EndPoint关联交易映射配置错误(){
		ServiceData data = exporter.getOneChannelTran("cardCHL", "0052");
		assertNull(data.getServiceData("IN_MAPPING"));
		assertNull(data.getServiceData("OUT_MAPPING"));
		assertNull(data.getServiceData("ERROR_MAPPING"));
	}
	
	public void atest_不存在的服务系统关联交易(){
		ServiceData data = exporter.getOneServerTran("appblocksSRV", "0000");
		assertNull(data);
		ServiceData data2 = exporter.getOneServerTran("aCHL", "00aa");
		assertNull(data2);
	}
	
	public void atest_存在的服务系统关联交易(){
		ServiceData data = exporter.getOneServerTran("appblocksSRV", "3001");
		assertNotNull(data);
	}
	
	public void atest_服务系统关联交易报文配置错误(){
		ServiceData data = exporter.getOneServerTran("appblocksSRV", "3000");
		assertNull(data.getServiceData("REQ_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("RESP_PACKAGE_CONFIG"));
	}
	
	public void atest_服务系统关联交易映射配置错误(){
		ServiceData data = exporter.getOneServerTran("appblocksSRV", "3000");
		assertNull(data.getServiceData("IN_MAPPING"));
		assertNull(data.getServiceData("OUT_MAPPING"));
		assertNull(data.getServiceData("ERROR_MAPPING"));
	}
	
	public void atest_导出正常的渠道(){
		ServiceData endPoint = exporter.getOneEndPoint("chCHL");
		assertNotNull(endPoint);
		ServiceData server = exporter.getOneServer("locoreSRV");
		assertNotNull(server);
		ServiceData endPointTran = exporter.getOneChannelTran("cardCHL", "0611");
		assertNotNull(endPointTran);
		ServiceData serverTran = exporter.getOneServerTran("inbankSRV", "0052");
		assertNotNull(serverTran);
	}
	
	public void test_不存在的服务(){
		ServiceData date = exporter.getOneService("0000");
		assertNull(date);
	}
	
	public void test_存在的服务(){
		ServiceData date = exporter.getOneService("3001");
		assertNotNull(date);
		ServiceData date2 = exporter.getOneService("0652");
		assertNotNull(date2);
	}
	
	public void test_服务报文配置错误(){
		ServiceData date = exporter.getOneService("3000");
		assertNotNull(date);
		ServiceData date2 = exporter.getOneService("8310");
		assertNotNull(date2);
	}
	
	//TODO:
	public void atest_导出服务系统关联交易(){
		ServiceData data = exporter.getOneServerTran("", "9219");
		System.out.println(data);
	}
	
	public void atest_导出原子服务到文件(){
		ServiceData atomData = exporter.getOneService("0001");
		System.out.println(atomData);
		JSONFileUtil.storeServiceDataToJsonFile(atomData, filePath);
	}
	
	public void atest_从文件读取原子服务(){
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(fileData);
		assertEquals(fileData.getString("SERVICE_CODE"), "0001");
		assertEquals(fileData.getString("SERVICE_TYPE"), "0");
		assertEquals(fileData.getServiceData("RESP_STRU").getString("SERVICE_CODE"), "0001");
		assertEquals(fileData.getServiceData("ERR_STRU").size(), 0);
	}
	
	public void atest_导出扩展服务到文件(){
		ServiceData expandData = exporter.getOneService("0002");
		System.out.println(expandData);
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
	}
	
	public void atest_从文件读取扩展服务(){ 
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(fileData);
		assertEquals(fileData.getString("SERVICE_CODE"), "0002");
		assertEquals(fileData.getString("SERVICE_TYPE"), "1");
		assertEquals(fileData.getServiceData("RESP_STRU").size(), 0);
		assertEquals(fileData.getServiceData("REQ_STRU").getString("SERVICE_CODE"), "0002");
	}
	
	public void atest_导出组合服务到文件(){
		ServiceData expandData = exporter.getOneService("0003");
		System.out.println(expandData);
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
	}
	
	public void atest_从文件读取组合服务(){
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(fileData);
		assertEquals(fileData.getString("SERVICE_CODE"), "0003");
		assertEquals(fileData.getString("SERVICE_TYPE"), "2");
		assertEquals(fileData.getServiceData("REQ_STRU").size(), 0);
		assertEquals(fileData.getServiceData("RESP_STRU").getString("SERVICE_CODE"), "0003");
		assertEquals(fileData.getString("CONTENT"), "{nodes:[{id:\"node_0\", name:\"开始\", code:\"cstart\", node_type:\"img\", left:-2, top:0, width:64, height:64, img_url:\"cstart.png\", point_type:2, in_nums:0, out_nums:1, can_resize:true, bindable:false, properties:{}}, {id:\"node_2\", name:\"映射(int a = 0;)(R S E)\", code:\"cmap\", node_type:\"img\", left:133, top:29, width:64, height:64, img_url:\"cmapping.png\", point_type:2, in_nums:1, out_nums:1, can_resize:true, bindable:false, properties:{mapping_code:\"int a = 0;\"}}, {id:\"node_3\", name:\"服务(0052)\", code:\"cservice\", node_type:\"img\", left:40, top:128, width:64, height:64, img_url:\"cservice.png\", point_type:2, in_nums:10, out_nums:0, can_resize:true, bindable:false, properties:{select_mode:\"1\", service_code:\"0052\"}}], lines:[{from:\"node_0\", to:\"node_2\", type:\"line\", properties:{}}, {from:\"node_2\", to:\"node_3\", type:\"line\", properties:{}}]}");
	}
	
	public void atest_导出部署数据到文件(){
		ServiceData expandData = exporter.getOneMachine("001");
		System.out.println(expandData);
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
	}
	
	public void atest_从文件读取部署数据(){
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(fileData);
		assertEquals(fileData.getString("MACHINE_CODE"), "001");
		assertEquals(fileData.getString("MACHINE_IP"), "10.0.137.13");
		assertEquals(fileData.getString("MACHINE_NAME"), "001");
		assertEquals(fileData.getServiceData("INSTANCE").getServiceData("001").getString("SKEYC"), "001");
		assertEquals(fileData.getServiceData("INSTANCE").getServiceData("001").getString("SKEYD"), "001");
		assertEquals(fileData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").getString("SKEYC"), "001");
		assertEquals(fileData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").getString("CHANNEL_CODE"), "tbCHL");
		assertEquals(fileData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").getString("BIND_ADDRESS"), "9447");
		assertEquals(fileData.getServiceData("PROCESSINSTANCE").getServiceData("tbCHL").getString("REMOTE_ADDRESS"), "");
	}
	
	public void atest_导出数据字典到文件(){
		ServiceData dictData = exporter.getOneDict("global");
		logger.info("test_导出数据字典到文件:\n{}", dictData);
		JSONFileUtil.storeServiceDataToJsonFile(dictData, filePath);
	}
	
	public void atest_从文件读取数据字典(){
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		logger.info("test_从文件读取数据字典:\n{}", fileData);
	}
	
	public void atest_导出模式数据到文件(){
		ServiceData dictData = exporter.getOneMode("appblocks_mode");
		logger.info("test_导出模式数据到文件:\n{}", dictData);
		JSONFileUtil.storeServiceDataToJsonFile(dictData, filePath);
	}
	
	public void atest_从文件中读取模式数据(){
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		logger.info("test_从文件中读取模式数据:\n{}", fileData);
	}
}
