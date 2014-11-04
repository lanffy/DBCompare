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
	@Inject  ExportDatasFromDB service;
	ServiceData data;
	ServiceData tran_data;
	ServiceData service_data;
	
	@Override
	protected void setUpOnce() throws java.lang.Exception {
		data = service.getEndPoint("vmenu2cardCHL");
		tran_data = service.getChannelTran("cardCHL", "0052");
		service_data = service.getOneService("8808");
		System.out.println("\n******service_data******\n"+service_data);
	}
	public void test_endPoint基本属性(){
		assertEquals(data.getString("CHANNEL_NAME"), "vmenu2cardCHL");
	}
	
	public void test_不存在的EndPoint(){
		try {
			service.getEndPoint("vmenu2cardCHLaaa");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	public void test_endpoint_comm(){
		ServiceData commData = data.getServiceData("COMM_ID");
		assertEquals(commData.getString("CCODE"), "vmenu_tcp_adapter");
	}
	
	public void test_structure(){
		ServiceData structureData = data.getServiceData("REQ_PACKAGE_CONFIG");
		assertEquals(structureData.getString("STRUCTURE_CATEGORY"), "0");
	}
	
	public void test_structure_config(){
		ServiceData structureData = data.getServiceData("REQ_PACKAGE_CONFIG");
		ServiceData structureContent = structureData.getServiceData("STRUCTURE_CONTENT");
		assertEquals(structureContent.getString("STYPE"), "5");
	}
	
	public void test_mapping(){
		ServiceData mapData = data.getServiceData("OUT_MAPPING");
		assertEquals(mapData.getString("MAPPING_SCRIPT"), "out=out.getServiceData(\"appdata\");");
	}
	
	public void test_tran(){
		assertEquals(tran_data.getString("CHANNEL_CODE"), "cardCHL");
		assertEquals(tran_data.getString("TRAN_CODE"), "0052");
	}
	
	public void test_tran_config(){
		ServiceData tran_config_data = tran_data.getServiceData("REQ_PACKAGE_CONFIG");
		assertEquals(tran_config_data.getString("STRUCTURE_CATEGORY"), "3");
		
		ServiceData tran_config_content_data = tran_config_data.getServiceData("STRUCTURE_CONTENT");
		assertEquals(tran_config_content_data.getString("STYPE"), "5");
	}
	
	public void test_tran_map(){
		ServiceData tran_map_data = tran_data.getServiceData("IN_MAPPING");
		assertEquals(tran_map_data.getString("MAPPING_NAME"), "0052请求映射");
	}
	
	public void test_服务基本参数(){
		assertEquals(service_data.getString("SERVICE_CODE"), "8808");
		assertEquals(service_data.getString("SERVER_CODE"), "inbankSRV");
		assertEquals(service_data.getString("SERVICE_NAME"), "8808");
		assertEquals(service_data.getString("SERVICE_TYPE"), "0");
		assertEquals(service_data.getString("CATEGORY_CODE"), "");
		assertNull(service_data.getString("EXTEND_SERVICE_NAME"));
		assertNull(service_data.getString("IS_PUBLISHED"));
	}
	
	public void test_服务接口参数(){
		ServiceData req_service_config_data = service_data.getServiceData("REQ_STRU");
		assertEquals(req_service_config_data.getString("SERVICE_CODE"), "8808");
		assertEquals(req_service_config_data.getString("STRUCTURE_TYPE"), "0");
		assertEquals(req_service_config_data.getString("STRUCTURE_CATEGORY"), "2");
		ServiceData req_service_config_content_data = req_service_config_data.getServiceData("STRUCTURE_CONTENT");
		assertEquals(req_service_config_content_data.getString("SDATAS"), "[{field_code:\"I1TRCD\", field_name:\"I1TRCD\", field_type:\"string\", field_length:4, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:5}, {field_code:\"I1SBNO\", field_name:\"I1SBNO\", field_type:\"string\", field_length:10, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:4}, {field_code:\"I1USID\", field_name:\"I1USID\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:6}, {field_code:\"I1AUUS\", field_name:\"I1AUUS\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:2}, {field_code:\"I1AUPS\", field_name:\"I1AUPS\", field_type:\"string\", field_length:6, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:1}, {field_code:\"I1WSNO\", field_name:\"I1WSNO\", field_type:\"string\", field_length:40, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:7}, {field_code:\"I1PYNO\", field_name:\"I1PYNO\", field_type:\"string\", field_length:4, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:3}]");
		assertEquals(req_service_config_content_data.getString("STYPE"), "5");
	}
}
