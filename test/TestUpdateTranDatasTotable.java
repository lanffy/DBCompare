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
 * @version 2014��10��31�� ����4:07:44
 */
public class TestUpdateTranDatasTotable extends TestCase {
	@Inject  ExportDatasFromDB exportService;
	@Inject  ImportDatasToDB importService;
	
	public void atest_�޸�EndPoint�������׻�������(){
		ServiceData tranData = exportService.getChannelTran("testCHL", "8802");;
		tranData.putString("TRAN_NAME", "1114�޸�8802����");
		System.out.println(tranData);
		int num = importService.updateTranEndPoint(tranData);
		assertEquals(num, 1);
		ServiceData confirmTranData = exportService.getChannelTran("testCHL", "8802");
		assertEquals(confirmTranData.getString("TRAN_NAME"), "1114�޸�8802����");
	}
	
	public void atest_�޸�EndPoint�������׽ӿ�����(){
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
	
	public void atest_�޸�EndPoint��������ӳ��(){
		ServiceData tranData = exportService.getChannelTran("testCHL", "8802");;
		ServiceData mapTranData = tranData.getServiceData("IN_MAPPING");
		mapTranData.putString("MAPPING_NAME", "1410�޸�8802����ӳ������");
		System.out.println(tranData);
		int num = importService.updateTranEndPoint(tranData);
		assertEquals(num, 1);
		ServiceData confirmTranData = exportService.getChannelTran("testCHL", "8802");
		ServiceData confirmMapTranData = confirmTranData.getServiceData("IN_MAPPING");
		assertEquals(confirmMapTranData.getString("MAPPING_NAME"), "1410�޸�8802����ӳ������");
	}
	
	public void atest_����EndPoint��������ӳ��(){
		ServiceData tranData = exportService.getChannelTran("testCHL", "8802");;
		ServiceData mapTranData = tranData.getServiceData("IN_MAPPING");
		tranData.putServiceData("OUT_MAPPING", mapTranData);
		System.out.println(tranData);
		int num = importService.updateTranEndPoint(tranData);
		assertEquals(num, 1);
		ServiceData confirmTranData = exportService.getChannelTran("testCHL", "8802");
		ServiceData confirmMapTranData = confirmTranData.getServiceData("OUT_MAPPING");
		assertEquals(confirmMapTranData.getString("MAPPING_NAME"), "1410�޸�8802����ӳ������");
	}
	
	public void atest_�޸�Server�������׻�������(){
		ServiceData tranData = exportService.getServerTran("testSRV", "3002");
		System.out.println("\n***�޸�ǰ***\n"+tranData);
		tranData.putString("TRAN_NAME", "1421�޸ĺ���Ӧ������ǰ�Ʒ���");
		System.out.println("\n***�޸ĺ�***\n"+tranData);
		int num = importService.updateTranServer(tranData);
		assertEquals(num, 1);
		ServiceData confirmData =  exportService.getServerTran("testSRV", "3002");
		assertEquals(confirmData.getString("SERVER_CODE"), "testSRV");
		assertEquals(confirmData.getString("TRAN_CODE"), "3002");
		assertEquals(confirmData.getString("TRAN_NAME"), "1421�޸ĺ���Ӧ������ǰ�Ʒ���");
	}
	
	public void atest_�޸�Server�������׽ӿ�����(){
		ServiceData tranData = exportService.getServerTran("testSRV", "3002");
		System.out.println("\n***�޸�ǰ***\n"+tranData);
		assertEquals(tranData.getString("SERVER_CODE"), "testSRV");
		ServiceData req_config_data = tranData.getServiceData("REQ_PACKAGE_CONFIG");
		req_config_data.getServiceData("STRUCTURE_CONTENT").putString("SDATAS", "{fdatas:[], is_strict:\"0\", package_mode:\"vrouterserver\", package_mode_name:\"VRouter��������ģʽ\"}");
		System.out.println("\n***�޸ĺ�***\n"+tranData);
		int num = importService.updateTranServer(tranData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getServerTran("testSRV", "3002");
		ServiceData req_config_confirm_data = confirmData.getServiceData("REQ_PACKAGE_CONFIG");
		assertEquals(req_config_confirm_data.getServiceData("STRUCTURE_CONTENT").getString("SDATAS"), "{fdatas:[], is_strict:\"0\", package_mode:\"vrouterserver\", package_mode_name:\"VRouter��������ģʽ\"}");
	}
	
	public void atest_�޸�Server��������ӳ��(){
		ServiceData tranData = exportService.getServerTran("testSRV", "3002");
		System.out.println("\n***�޸�ǰ***\n"+tranData);
		assertEquals(tranData.getString("SERVER_CODE"), "testSRV");
		ServiceData in_map_data = tranData.getServiceData("IN_MAPPING");
		in_map_data.putString("MAPPING_NAME", "1435�޸�����ӳ������");
		System.out.println("\n***�޸ĺ�***\n"+tranData);
		assertEquals(importService.updateTranServer(tranData), 1);
		ServiceData confirmData = exportService.getServerTran("testSRV", "3002");
		ServiceData in_map_confirm_data = confirmData.getServiceData("IN_MAPPING");
		assertEquals(in_map_confirm_data.getString("MAPPING_NAME"), "1435�޸�����ӳ������");
	}
	
	public void atest_����Server��������ӳ��(){
		ServiceData tranData = exportService.getServerTran("testSRV", "3002");
		System.out.println("\n***�޸�ǰ***\n"+tranData);
		assertEquals(tranData.getString("SERVER_CODE"), "testSRV");
		tranData.putServiceData("OUT_MAPPING", tranData.getServiceData("IN_MAPPING"));
		System.out.println("\n***�޸ĺ�***\n"+tranData);
		int num = importService.updateTranServer(tranData);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getServerTran("testSRV", "3002");
		ServiceData out_map_confirm_data = confirmData.getServiceData("OUT_MAPPING");
		assertEquals(out_map_confirm_data.getString("MAPPING_NAME"), "1435�޸�����ӳ������");
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
//		System.out.println("Commited!");
	}
}
