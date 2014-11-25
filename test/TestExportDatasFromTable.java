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
 * @version 2014��10��31�� ����1:30:14
 */
public class TestExportDatasFromTable extends TestCase {
	@Inject static ExportDatasFromDB exporter;
	private final Log logger = LogFactory.getLog("dbcompare");
	String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("***�������Ĳ��԰����ָ���***");
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
	
	public void atest_�����ڵ�EndPoint(){
		ServiceData data = exporter.getOneEndPoint("vmenu2cardCHLaaa");
		assertNull(data);
	}
	
	public void atest_���ڵ�EndPoint(){
		ServiceData data = exporter.getOneEndPoint("ATMCHL");
		assertNotNull(data);
	}
	
	public void atest_EndPointͨѶ����������(){
		ServiceData data = exporter.getOneEndPoint("ATMCHL");
		assertNull(data.getServiceData("COMM_ID"));
	}
	
	public void atest_EndPoint��������Ϊ��(){
		ServiceData data = exporter.getOneEndPoint("ATMCHL");
		assertNull(data.getServiceData("REQ_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("RESP_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("ERR_PACKAGE_CONFIG"));
	}
	
	public void atest_EndPointϵͳӳ��Ϊ��(){
		ServiceData data = exporter.getOneEndPoint("ATMCHL");
		assertNull(data.getServiceData("IN_MAPPING"));
		assertNull(data.getServiceData("OUT_MAPPING"));
		assertNull(data.getServiceData("ERROR_MAPPING"));
	}
	
	
	public void atest_�����ڵķ���ϵͳ(){
		ServiceData data = exporter.getOneServer("aaaa");
		assertNull(data);
	}
	
	public void atest_���ڵķ���ϵͳ(){
		ServiceData data = exporter.getOneServer("appblocksSRV");
		assertNotNull(data);
	}
	
	public void atest����ϵͳͨѶ����������(){
		ServiceData data = exporter.getOneServer("appblocksSRV");
		assertNull(data.getServiceData("COMM_ID"));
	}
	
	public void atest����ϵͳ��������Ϊ��(){
		ServiceData data = exporter.getOneServer("appblocksSRV");
		assertNull(data.getServiceData("REQ_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("RESP_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("ERR_PACKAGE_CONFIG"));
	}
	
	public void atest����ϵͳϵͳӳ��Ϊ��(){
		ServiceData data = exporter.getOneServer("appblocksSRV");
		assertNull(data.getServiceData("IN_MAPPING"));
		assertNull(data.getServiceData("OUT_MAPPING"));
		assertNull(data.getServiceData("ERROR_MAPPING"));
	}
	
	public void atest_�����ڵ�EndPoint��������(){
		ServiceData data = exporter.getOneChannelTran("cardCHL", "0000");
		assertNull(data);
		ServiceData data2 = exporter.getOneChannelTran("aCHL", "0052");
		assertNull(data2);
	}
	
	public void atest_���ڵ�EndPoint��������(){
		ServiceData data = exporter.getOneChannelTran("cardCHL", "0061");
		assertNotNull(data);
	}
	
	public void atest_EndPoint�������ױ������ô���(){
		ServiceData data = exporter.getOneChannelTran("cardCHL", "0052");
		assertNull(data.getServiceData("REQ_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("RESP_PACKAGE_CONFIG"));
	}
	
	public void atest_EndPoint��������ӳ�����ô���(){
		ServiceData data = exporter.getOneChannelTran("cardCHL", "0052");
		assertNull(data.getServiceData("IN_MAPPING"));
		assertNull(data.getServiceData("OUT_MAPPING"));
		assertNull(data.getServiceData("ERROR_MAPPING"));
	}
	
	public void atest_�����ڵķ���ϵͳ��������(){
		ServiceData data = exporter.getOneServerTran("appblocksSRV", "0000");
		assertNull(data);
		ServiceData data2 = exporter.getOneServerTran("aCHL", "00aa");
		assertNull(data2);
	}
	
	public void atest_���ڵķ���ϵͳ��������(){
		ServiceData data = exporter.getOneServerTran("appblocksSRV", "3001");
		assertNotNull(data);
	}
	
	public void atest_����ϵͳ�������ױ������ô���(){
		ServiceData data = exporter.getOneServerTran("appblocksSRV", "3000");
		assertNull(data.getServiceData("REQ_PACKAGE_CONFIG"));
		assertNull(data.getServiceData("RESP_PACKAGE_CONFIG"));
	}
	
	public void atest_����ϵͳ��������ӳ�����ô���(){
		ServiceData data = exporter.getOneServerTran("appblocksSRV", "3000");
		assertNull(data.getServiceData("IN_MAPPING"));
		assertNull(data.getServiceData("OUT_MAPPING"));
		assertNull(data.getServiceData("ERROR_MAPPING"));
	}
	
	public void atest_��������������(){
		ServiceData endPoint = exporter.getOneEndPoint("chCHL");
		assertNotNull(endPoint);
		ServiceData server = exporter.getOneServer("locoreSRV");
		assertNotNull(server);
		ServiceData endPointTran = exporter.getOneChannelTran("cardCHL", "0611");
		assertNotNull(endPointTran);
		ServiceData serverTran = exporter.getOneServerTran("inbankSRV", "0052");
		assertNotNull(serverTran);
	}
	
	public void test_�����ڵķ���(){
		ServiceData date = exporter.getOneService("0000");
		assertNull(date);
	}
	
	public void test_���ڵķ���(){
		ServiceData date = exporter.getOneService("3001");
		assertNotNull(date);
		ServiceData date2 = exporter.getOneService("0652");
		assertNotNull(date2);
	}
	
	public void test_���������ô���(){
		ServiceData date = exporter.getOneService("3000");
		assertNotNull(date);
		ServiceData date2 = exporter.getOneService("8310");
		assertNotNull(date2);
	}
	
	//TODO:
	public void atest_��������ϵͳ��������(){
		ServiceData data = exporter.getOneServerTran("", "9219");
		System.out.println(data);
	}
	
	public void atest_����ԭ�ӷ����ļ�(){
		ServiceData atomData = exporter.getOneService("0001");
		System.out.println(atomData);
		JSONFileUtil.storeServiceDataToJsonFile(atomData, filePath);
	}
	
	public void atest_���ļ���ȡԭ�ӷ���(){
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(fileData);
		assertEquals(fileData.getString("SERVICE_CODE"), "0001");
		assertEquals(fileData.getString("SERVICE_TYPE"), "0");
		assertEquals(fileData.getServiceData("RESP_STRU").getString("SERVICE_CODE"), "0001");
		assertEquals(fileData.getServiceData("ERR_STRU").size(), 0);
	}
	
	public void atest_������չ�����ļ�(){
		ServiceData expandData = exporter.getOneService("0002");
		System.out.println(expandData);
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
	}
	
	public void atest_���ļ���ȡ��չ����(){ 
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(fileData);
		assertEquals(fileData.getString("SERVICE_CODE"), "0002");
		assertEquals(fileData.getString("SERVICE_TYPE"), "1");
		assertEquals(fileData.getServiceData("RESP_STRU").size(), 0);
		assertEquals(fileData.getServiceData("REQ_STRU").getString("SERVICE_CODE"), "0002");
	}
	
	public void atest_������Ϸ����ļ�(){
		ServiceData expandData = exporter.getOneService("0003");
		System.out.println(expandData);
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
	}
	
	public void atest_���ļ���ȡ��Ϸ���(){
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(fileData);
		assertEquals(fileData.getString("SERVICE_CODE"), "0003");
		assertEquals(fileData.getString("SERVICE_TYPE"), "2");
		assertEquals(fileData.getServiceData("REQ_STRU").size(), 0);
		assertEquals(fileData.getServiceData("RESP_STRU").getString("SERVICE_CODE"), "0003");
		assertEquals(fileData.getString("CONTENT"), "{nodes:[{id:\"node_0\", name:\"��ʼ\", code:\"cstart\", node_type:\"img\", left:-2, top:0, width:64, height:64, img_url:\"cstart.png\", point_type:2, in_nums:0, out_nums:1, can_resize:true, bindable:false, properties:{}}, {id:\"node_2\", name:\"ӳ��(int a = 0;)(R S E)\", code:\"cmap\", node_type:\"img\", left:133, top:29, width:64, height:64, img_url:\"cmapping.png\", point_type:2, in_nums:1, out_nums:1, can_resize:true, bindable:false, properties:{mapping_code:\"int a = 0;\"}}, {id:\"node_3\", name:\"����(0052)\", code:\"cservice\", node_type:\"img\", left:40, top:128, width:64, height:64, img_url:\"cservice.png\", point_type:2, in_nums:10, out_nums:0, can_resize:true, bindable:false, properties:{select_mode:\"1\", service_code:\"0052\"}}], lines:[{from:\"node_0\", to:\"node_2\", type:\"line\", properties:{}}, {from:\"node_2\", to:\"node_3\", type:\"line\", properties:{}}]}");
	}
	
	public void atest_�����������ݵ��ļ�(){
		ServiceData expandData = exporter.getOneMachine("001");
		System.out.println(expandData);
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
	}
	
	public void atest_���ļ���ȡ��������(){
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
	
	public void atest_���������ֵ䵽�ļ�(){
		ServiceData dictData = exporter.getOneDict("global");
		logger.info("test_���������ֵ䵽�ļ�:\n{}", dictData);
		JSONFileUtil.storeServiceDataToJsonFile(dictData, filePath);
	}
	
	public void atest_���ļ���ȡ�����ֵ�(){
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		logger.info("test_���ļ���ȡ�����ֵ�:\n{}", fileData);
	}
	
	public void atest_����ģʽ���ݵ��ļ�(){
		ServiceData dictData = exporter.getOneMode("appblocks_mode");
		logger.info("test_����ģʽ���ݵ��ļ�:\n{}", dictData);
		JSONFileUtil.storeServiceDataToJsonFile(dictData, filePath);
	}
	
	public void atest_���ļ��ж�ȡģʽ����(){
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		logger.info("test_���ļ��ж�ȡģʽ����:\n{}", fileData);
	}
}
