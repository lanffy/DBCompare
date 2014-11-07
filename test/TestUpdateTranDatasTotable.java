package compare.test;

import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.lang.Inject;
import com.wk.sdo.ServiceData;
import com.wk.test.TestCase;
import compare.ExportDatasFromDB;
import compare.ImportDatasToDB;


/**
 * @description
 * @author raoliang
 * @version 2014年10月31日 下午4:07:44
 */
public class TestUpdateTranDatasTotable extends TestCase {
	@Inject  ExportDatasFromDB exportService;
	@Inject  ImportDatasToDB importService;
	
	public void atest_修改EndPoint关联交易基本参数(){
		ServiceData tranData = exportService.getChannelTran("testCHL", "8802");;
		tranData.putString("TRAN_NAME", "1114修改8802名称");
		System.out.println(tranData);
		int num = importService.updateTranEndPoint(tranData);
		assertEquals(num, 1);
		ServiceData confirmTranData = exportService.getChannelTran("testCHL", "8802");
		assertEquals(confirmTranData.getString("TRAN_NAME"), "1114修改8802名称");
	}
	
	public void atest_修改EndPoint关联交易接口配置(){
		ServiceData tranData = exportService.getChannelTran("testCHL", "8802");;
		ServiceData configTranData = tranData.getServiceData("REQ_PACKAGE_CONFIG");
		ServiceData configContentData = configTranData.getServiceData("STRUCTURE_CONTENT");
		configContentData.putString("SDATAS", "{fdatas:[{field_code:\"O1MGID\", field_name:\"O1MGID\", field_type:\"string\", field_length:7, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:10}], is_strict:\"1\", package_mode:\"outsys_mode\", package_mode_name:\"outsys_mode\"}");
		System.out.println(tranData);
		int num = importService.updateTranEndPoint(tranData);
		assertEquals(num, 1);
		ServiceData confirmTranData = exportService.getChannelTran("testCHL", "8802");
		ServiceData confirmConfigTranData = confirmTranData.getServiceData("REQ_PACKAGE_CONFIG");
		ServiceData confirmConfigContentData = confirmConfigTranData.getServiceData("STRUCTURE_CONTENT");
		assertEquals(confirmConfigContentData.getString("SDATAS"), "{fdatas:[{field_code:\"O1MGID\", field_name:\"O1MGID\", field_type:\"string\", field_length:7, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:10}], is_strict:\"1\", package_mode:\"outsys_mode\", package_mode_name:\"outsys_mode\"}");
	}
	
	public void atest_修改EndPoint关联交易映射(){
		ServiceData tranData = exportService.getChannelTran("testCHL", "8802");;
		ServiceData mapTranData = tranData.getServiceData("IN_MAPPING");
		mapTranData.putString("MAPPING_NAME", "1410修改8802请求映射名称");
		System.out.println(tranData);
		int num = importService.updateTranEndPoint(tranData);
		assertEquals(num, 1);
		ServiceData confirmTranData = exportService.getChannelTran("testCHL", "8802");
		ServiceData confirmMapTranData = confirmTranData.getServiceData("IN_MAPPING");
		assertEquals(confirmMapTranData.getString("MAPPING_NAME"), "1410修改8802请求映射名称");
	}
	
	public void atest_新增EndPoint关联交易映射(){
		ServiceData tranData = exportService.getChannelTran("testCHL", "8802");;
		ServiceData mapTranData = tranData.getServiceData("IN_MAPPING");
		tranData.putServiceData("OUT_MAPPING", mapTranData);
		System.out.println(tranData);
		int num = importService.updateTranEndPoint(tranData);
		assertEquals(num, 1);
		ServiceData confirmTranData = exportService.getChannelTran("testCHL", "8802");
		ServiceData confirmMapTranData = confirmTranData.getServiceData("OUT_MAPPING");
		assertEquals(confirmMapTranData.getString("MAPPING_NAME"), "1410修改8802请求映射名称");
	}
	
	public void atest_修改Server关联交易基本参数(){
		ServiceData tranData = exportService.getServerTran("testSRV", "3002");
		System.out.println("\n***修改前***\n"+tranData);
		tranData.putString("TRAN_NAME", "1421修改核心应用账务前移服务");
		System.out.println("\n***修改后***\n"+tranData);
		int num = importService.updateTranServer(tranData);
		assertEquals(num, 1);
		ServiceData confirmData =  exportService.getServerTran("testSRV", "3002");
		assertEquals(confirmData.getString("SERVER_CODE"), "testSRV");
		assertEquals(confirmData.getString("TRAN_CODE"), "3002");
		assertEquals(confirmData.getString("TRAN_NAME"), "1421修改核心应用账务前移服务");
	}
	
	public void atest_修改Server关联交易接口配置(){
		ServiceData tranData = exportService.getServerTran("testSRV", "3002");
		System.out.println("\n***修改前***\n"+tranData);
		assertEquals(tranData.getString("SERVER_CODE"), "testSRV");
		ServiceData req_config_data = tranData.getServiceData("REQ_PACKAGE_CONFIG");
		req_config_data.getServiceData("STRUCTURE_CONTENT").putString("SDATAS", "{fdatas:[], is_strict:\"0\", package_mode:\"vrouterserver\", package_mode_name:\"VRouter服务器包模式\"}");
		System.out.println("\n***修改后***\n"+tranData);
		int num = importService.updateTranServer(tranData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getServerTran("testSRV", "3002");
		ServiceData req_config_confirm_data = confirmData.getServiceData("REQ_PACKAGE_CONFIG");
		assertEquals(req_config_confirm_data.getServiceData("STRUCTURE_CONTENT").getString("SDATAS"), "{fdatas:[], is_strict:\"0\", package_mode:\"vrouterserver\", package_mode_name:\"VRouter服务器包模式\"}");
	}
	
	public void atest_修改Server关联交易映射(){
		ServiceData tranData = exportService.getServerTran("testSRV", "3002");
		System.out.println("\n***修改前***\n"+tranData);
		assertEquals(tranData.getString("SERVER_CODE"), "testSRV");
		ServiceData in_map_data = tranData.getServiceData("IN_MAPPING");
		in_map_data.putString("MAPPING_NAME", "1435修改请求映射名称");
		System.out.println("\n***修改后***\n"+tranData);
		assertEquals(importService.updateTranServer(tranData), 1);
		ServiceData confirmData = exportService.getServerTran("testSRV", "3002");
		ServiceData in_map_confirm_data = confirmData.getServiceData("IN_MAPPING");
		assertEquals(in_map_confirm_data.getString("MAPPING_NAME"), "1435修改请求映射名称");
	}
	
	public void atest_新增Server关联交易映射(){
		ServiceData tranData = exportService.getServerTran("testSRV", "3002");
		System.out.println("\n***修改前***\n"+tranData);
		assertEquals(tranData.getString("SERVER_CODE"), "testSRV");
		tranData.putServiceData("OUT_MAPPING", tranData.getServiceData("IN_MAPPING"));
		System.out.println("\n***修改后***\n"+tranData);
		int num = importService.updateTranServer(tranData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getServerTran("testSRV", "3002");
		ServiceData out_map_confirm_data = confirmData.getServiceData("OUT_MAPPING");
		assertEquals(out_map_confirm_data.getString("MAPPING_NAME"), "1435修改请求映射名称");
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
//		System.out.println("Commited!");
	}
}
