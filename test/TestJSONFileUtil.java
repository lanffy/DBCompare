package compare.test;

import com.wk.lang.Inject;
import com.wk.sdo.ServiceData;
import com.wk.test.TestCase;
import compare.ExportDatasFromDB;
import compare.ImportDatasToDB;
import compare.JSONFileUtil;

/**
 * @description ServiceData与JSON工具类测试案例
 * @author raoliang
 * @version 2014年11月5日 上午11:24:48
 */
public class TestJSONFileUtil extends TestCase {
	@Inject ExportDatasFromDB exporter;
	@Inject ImportDatasToDB importer;
	ServiceData data;
	String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	
	@Override
	protected void setUpOnce() throws java.lang.Exception {
		data = exporter.getOneEndPoint("vmenu2cardCHL");
	}
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("******华丽丽的测试案例分割线*****");
	}
	
	public void atest_写EndPointServiceData到文件(){
		System.out.println(data);
		JSONFileUtil.storeServiceDataToJsonFile(data, filePath);
	}
	
	public void atest_从json文件读取EndPointServiceData(){
		ServiceData jsondata = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(jsondata);
		assertEquals(jsondata.getString("CHANNEL_CODE"), "vmenu2cardCHL");
		assertEquals(jsondata.getString("CHANNEL_NAME"), "vmenu2cardCHL");
		assertEquals(jsondata.getString("GET_SVC_EXP"), "String service_name=data.getString(\"sys_service_name\"); if(\"8305\".equals(service_name) || \"8306\".equals(service_name) || \"8307\".equals(service_name) || \"8308\".equals(service_name) || \"8309\".equals(service_name) || \"8322\".equals(service_name) || \"8332\".equals(service_name) || \"8323\".equals(service_name)  ||  \"8325\".equals(service_name) || \"8326\".equals(service_name)  || \"8327\".equals(service_name)){return \"cardsbadapter_passthru_service\";} else {return \"cardadapter_passthru_service\";};");
		assertEquals(jsondata.getString("PUT_SVC_EXP"), "data.putString(\"sys_service_name\", service_name);");
		assertEquals(jsondata.getString("MSG_CLASS"), "com.wk.net.ChannelBufferMsg");
		assertEquals(jsondata.getString("VERNO"), "1.00");
		assertEquals(jsondata.getString("BIZ_CHANNEL_EXP"), "");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("CATEGORY"), "0");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("CCODE"), "vmenu_tcp_adapter");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("COMM_TYPE"), "server");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("CONNECTION_MODE"), "sync_short_connection");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("PACKET_TYPE"), "length(adjust=-4,max=10485760)");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("BIND_ADDRESS"), "");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("REMOTE_ADDRESS"), "");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("MATCH_REQUEST"), "false");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("MATCH_PARTNER"), "true");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("MAX_ACTIVE"), "100");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("MIN_ACTIVE"), "0");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("MAX_CONN_LIVE_TIME"), "0");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("MAX_CONN_IDLE_TIME"), "0");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("MAX_CONN_INACTIVE_TIME"), "0");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("CONN_CHECK_INTERVAL"), "0");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("WORKER"), "1");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("TIMEOUT"), "60000");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("ROUND_ROBIN"), "true");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("MAX_PARTNER_WAIT_TIME"), "0");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("MSG_CLASS"), "com.wk.net.ChannelBufferMsg");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("IMPL_CLASS"), "");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("TRUST_HOSTS"), "");
		assertEquals(jsondata.getServiceData("COMM_ID").getString("VERNO"), "1.00");
		assertEquals(jsondata.getServiceData("REQ_PACKAGE_CONFIG").getString("SERVICE_CODE"), "vmenu2cardCHL");
		assertEquals(jsondata.getServiceData("REQ_PACKAGE_CONFIG").getString("STRUCTURE_TYPE"), "0");
		assertEquals(jsondata.getServiceData("REQ_PACKAGE_CONFIG").getString("STRUCTURE_CATEGORY"), "0");
		assertEquals(jsondata.getServiceData("REQ_PACKAGE_CONFIG").getServiceData("STRUCTURE_CONTENT").getString("SDATAS"), "{fdatas:[], is_strict:\"0\", package_mode:\"vrouterserver\", package_mode_name:\"VRouter服务器包模式\"}");
		assertEquals(jsondata.getServiceData("REQ_PACKAGE_CONFIG").getServiceData("STRUCTURE_CONTENT").getString("STYPE"), "5");
		assertEquals(jsondata.getServiceData("REQ_PACKAGE_CONFIG").getString("VERNO"), "1.00");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("MAPPING_CATEGORY"), "0");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("REF_CODE"), "vmenu2cardCHL");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("MAPPING_CODE"), "vmenu2cardCHL_resp");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("MAPPING_NAME"), "vmenu2cardCHL响应映射");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("MAPPING_ATTR"), "[]");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("MAPPING_SCRIPT"), "out=out.getServiceData(\"appdata\");");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("MAPPING_EXPR"), "[]");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("CTX_DATAS"), "[]");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("IN_DATAS"), "[]");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("OUT_DATAS"), "[]");
		assertEquals(jsondata.getServiceData("OUT_MAPPING").getString("VERNO"), "1.00");
		
	}

	public void atest_写ServerServiceData到文件(){
		ServiceData serverData = exporter.getOneServer("inbankSRV");
		System.out.println(serverData);
		JSONFileUtil.storeServiceDataToJsonFile(serverData, filePath);
	}
	
	public void atest_从json文件读取ServerServiceData(){
		ServiceData jsondata = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(jsondata);
	}
	
	public void test_写service_ServiceData到文件(){
		ServiceData service = exporter.getOneService("8808");
		System.out.println(service);
		JSONFileUtil.storeServiceDataToJsonFile(service, filePath);
	}
	
	public void test_从json文件读取ServiceData(){
		ServiceData jsondata = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(jsondata);
		assertEquals(jsondata.getServiceData("ERR_STRU").size() ,0);
	}
	
	public void test_写EndPoint关联交易到文件(){
		ServiceData tranEndPoint = exporter.getOneChannelTran("cardCHL", "0052");
		System.out.println(tranEndPoint);
		JSONFileUtil.storeServiceDataToJsonFile(tranEndPoint, filePath);
	}
	
	public void test_从文件读取EndPoint关联交易(){
		ServiceData filedata = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(filedata);
		assertEquals(filedata.getString("CHANNEL_CODE"), "cardCHL");
		assertEquals(filedata.getString("TRAN_CODE"), "0052");
		assertEquals(filedata.getServiceData("REQ_PACKAGE_CONFIG").getString("SERVICE_CODE"), "0052");
		assertEquals(filedata.getServiceData("REQ_PACKAGE_CONFIG").getServiceData("STRUCTURE_CONTENT").getString("SDATAS"), "{fdatas:[], is_strict:\"0\", package_mode:\"vrouterclient\", package_mode_name:\"VRouter客户端包模式\"}");
		assertEquals(filedata.getServiceData("IN_MAPPING").getString("MAPPING_NAME"), "0052请求映射");
		assertEquals(filedata.getServiceData("OUT_MAPPING").size(), 0);
	}
	
	public void test_写Server关联交易到文件(){
		ServiceData serverdata = exporter.getOneServerTran("inbankSRV", "0052");
		System.out.println(serverdata);
		JSONFileUtil.storeServiceDataToJsonFile(serverdata, filePath);
	}
	
	public void test_从文件读取Server关联交易(){
		ServiceData filedata = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(filedata);
		assertEquals(filedata.getString("SERVER_CODE"), "inbankSRV");
		assertEquals(filedata.getString("TRAN_CODE"), "0052");
		assertEquals(filedata.getServiceData("REQ_PACKAGE_CONFIG").getString("SERVICE_CODE"), "0052");
		assertEquals(filedata.getServiceData("REQ_PACKAGE_CONFIG").getServiceData("STRUCTURE_CONTENT").getString("SDATAS"), "{fdatas:[{field_code:\"I1TRCD\", field_name:\"I1TRCD\", field_type:\"string\", field_length:4, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:22}, {field_code:\"I1SBNO\", field_name:\"I1SBNO\", field_type:\"string\", field_length:10, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:20}, {field_code:\"I1USID\", field_name:\"I1USID\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:23}, {field_code:\"I1AUUS\", field_name:\"I1AUUS\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:15}, {field_code:\"I1AUPS\", field_name:\"I1AUPS\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:14}, {field_code:\"I1WSNO\", field_name:\"I1WSNO\", field_type:\"string\", field_length:40, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:24}, {field_code:\"I1ORTS\", field_name:\"原柜员流水号\", field_type:\"string\", field_length:10, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:19}, {field_code:\"I1ECRS\", field_name:\"错帐原因\", field_type:\"string\", field_length:3, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:17}, {field_code:\"I1NGAM\", field_name:\"交易金额\", field_type:\"decimal\", field_length:15, field_scale:2, field_category:\"2\", field_parent:\"\", field_id:18}, {field_code:\"I1YSQM\", field_name:\"预授权码\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:25}, {field_code:\"I1TRAM\", field_name:\"控制金额\", field_type:\"decimal\", field_length:15, field_scale:2, field_category:\"2\", field_parent:\"\", field_id:21}, {field_code:\"I1CZRQ\", field_name:\"交易日\", field_type:\"decimal\", field_length:8, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:16}], is_strict:\"1\", package_mode:\"outsys_mode\", package_mode_name:\"outsys_mode\"}");
		assertEquals(filedata.getServiceData("REQ_PACKAGE_CONFIG").getServiceData("STRUCTURE_CONTENT").getString("STYPE"), "5");
		assertEquals(filedata.getServiceData("IN_MAPPING").size(), 0);
		assertEquals(filedata.getServiceData("OUT_MAPPING").size(), 0);
		assertEquals(filedata.getServiceData("ERROR_MAPPING").size(), 0);
	}
	
}
