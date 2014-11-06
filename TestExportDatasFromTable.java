package compare;

import com.wk.lang.Inject;
import com.wk.sdo.ServiceData;
import com.wk.test.TestCase;


/**
 * @description
 * @author raoliang
 * @version 2014年10月31日 下午1:30:14
 */
public class TestExportDatasFromTable extends TestCase {
	@Inject static ExportDatasFromDB exporter;
	ServiceData data;
	ServiceData tran_data;
	ServiceData service_data;
	String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	@Override
	protected void setUpOnce() throws java.lang.Exception {
		data = exporter.getEndPoint("vmenu2cardCHL");
//		System.out.println("\n******data******\n"+data);
		tran_data = exporter.getChannelTran("cardCHL", "0052");
		service_data = exporter.getOneService("8808");
//		System.out.println("\n******service_data******\n"+service_data);
	}
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("***华丽丽的测试案例分割线***");
	}
	
	public void atest_endPoint基本属性(){
		assertEquals(data.getString("CHANNEL_NAME"), "vmenu2cardCHL");
	}
	
	public void atest_不存在的EndPoint(){
		try {
			exporter.getEndPoint("vmenu2cardCHLaaa");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	public void atest_endpoint_comm(){
		ServiceData commData = data.getServiceData("COMM_ID");
		assertEquals(commData.getString("CCODE"), "vmenu_tcp_adapter");
	}
	
	public void atest_structure(){
		ServiceData structureData = data.getServiceData("REQ_PACKAGE_CONFIG");
		assertEquals(structureData.getString("STRUCTURE_CATEGORY"), "0");
	}
	
	public void atest_structure_config(){
		ServiceData structureData = data.getServiceData("REQ_PACKAGE_CONFIG");
		ServiceData structureContent = structureData.getServiceData("STRUCTURE_CONTENT");
		assertEquals(structureContent.getString("STYPE"), "5");
	}
	
	public void atest_mapping(){
		ServiceData mapData = data.getServiceData("OUT_MAPPING");
		assertEquals(mapData.getString("MAPPING_SCRIPT"), "out=out.getServiceData(\"appdata\");");
	}
	
	public void atest_tran(){
		assertEquals(tran_data.getString("CHANNEL_CODE"), "cardCHL");
		assertEquals(tran_data.getString("TRAN_CODE"), "0052");
	}
	
	public void atest_tran_config(){
		ServiceData tran_config_data = tran_data.getServiceData("REQ_PACKAGE_CONFIG");
		assertEquals(tran_config_data.getString("STRUCTURE_CATEGORY"), "3");
		
		ServiceData tran_config_content_data = tran_config_data.getServiceData("STRUCTURE_CONTENT");
		assertEquals(tran_config_content_data.getString("STYPE"), "5");
	}
	
	public void atest_tran_map(){
		ServiceData tran_map_data = tran_data.getServiceData("IN_MAPPING");
		assertEquals(tran_map_data.getString("MAPPING_NAME"), "0052请求映射");
	}
	
	public void atest_原子服务服务基本参数(){
		assertEquals(service_data.getString("SERVICE_CODE"), "8808");
		assertEquals(service_data.getString("SERVER_CODE"), "inbankSRV");
		assertEquals(service_data.getString("SERVICE_NAME"), "8808");
		assertEquals(service_data.getString("SERVICE_TYPE"), "0");
		assertEquals(service_data.getString("CATEGORY_CODE"), "");
		assertNull(service_data.getString("EXTEND_SERVICE_NAME"));
		assertNull(service_data.getString("IS_PUBLISHED"));
	}
	
	public void atest_原子服务服务接口参数(){
		ServiceData req_service_config_data = service_data.getServiceData("REQ_STRU");
		assertEquals(req_service_config_data.getString("SERVICE_CODE"), "8808");
		assertEquals(req_service_config_data.getString("STRUCTURE_TYPE"), "0");
		assertEquals(req_service_config_data.getString("STRUCTURE_CATEGORY"), "2");
		ServiceData req_service_config_content_data = req_service_config_data.getServiceData("STRUCTURE_CONTENT");
		assertEquals(req_service_config_content_data.getString("SDATAS"), "[{field_code:\"I1TRCD\", field_name:\"I1TRCD\", field_type:\"string\", field_length:4, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:5}, {field_code:\"I1SBNO\", field_name:\"I1SBNO\", field_type:\"string\", field_length:10, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:4}, {field_code:\"I1USID\", field_name:\"I1USID\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:6}, {field_code:\"I1AUUS\", field_name:\"I1AUUS\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:2}, {field_code:\"I1AUPS\", field_name:\"I1AUPS\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:1}, {field_code:\"I1WSNO\", field_name:\"I1WSNO\", field_type:\"string\", field_length:40, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:7}, {field_code:\"I1PYNO\", field_name:\"I1PYNO\", field_type:\"string\", field_length:4, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:3}]");
		assertEquals(req_service_config_content_data.getString("STYPE"), "5");
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
	
	public void test_导出部署数据到文件(){
		ServiceData expandData = exporter.getMachine("001");
		System.out.println(expandData);
		JSONFileUtil.storeServiceDataToJsonFile(expandData, filePath);
	}
	
	public void test_从文件读取部署数据(){
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
}
