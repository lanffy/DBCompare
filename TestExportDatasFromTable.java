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
	
	@Override
	protected void setUpOnce() throws java.lang.Exception {
		data = service.getEndPoint("vmenu2cardCHL");
		tran_data = service.getChannelTran("cardCHL", "0052");
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
}
