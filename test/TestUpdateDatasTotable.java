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
public class TestUpdateDatasTotable extends TestCase {
	@Inject  ExportDatasFromDB exportService;
	@Inject  ImportDatasToDB importService;
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("********�������Ĳ��԰����ָ���************");
	}
	
	public void atest_�޸�EndPoint��������(){
		ServiceData endPoint = exportService.getEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		endPoint.putString("CHANNEL_NAME", "1338�޸���EndPoint");
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getEndPoint("testCHL");
		assertEquals(confirmData.getString("CHANNEL_NAME"), "1338�޸���EndPoint");
	}
	
	public void atest_EndPoint�޸�ͨѶ����(){
		ServiceData endPoint = exportService.getEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		ServiceData commData = endPoint.getServiceData("COMM_ID");
		commData.putString("CCODE", "test_tcp_updated1");
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getEndPoint("testCHL");
		ServiceData confirmCommData = confirmData.getServiceData("COMM_ID");
		assertEquals(confirmCommData.getString("CCODE"), "test_tcp_updated1");
	}
	
	public void atest_�޸�EndPoint�ӿ�����(){
		ServiceData endPoint = exportService.getEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		ServiceData reqConfigData = endPoint.getServiceData("REQ_PACKAGE_CONFIG");
		ServiceData reqConfigContentData = reqConfigData.getServiceData("STRUCTURE_CONTENT"); 
		assertEquals(reqConfigContentData.getString("SDATAS"), "{fdatas:[], is_strict:\"0\", package_mode:\"vrouterserver\", package_mode_name:\"VRouter��������ģʽ\"}");
		reqConfigContentData.putString("SDATAS", "{fdatas:[], is_strict:\"1\", package_mode:\"vrouterserver\", package_mode_name:\"VRouter��������ģʽ\"}");
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
	}
	
	public void atest_�޸�EndPointӳ��(){
		ServiceData endPoint = exportService.getEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		ServiceData errMapData = endPoint.getServiceData("IN_MAPPING");
		assertEquals(errMapData.getString("MAPPING_NAME"), "test��������ӳ��");
		errMapData.putString("MAPPING_NAME", "0929test��������ӳ��");
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getEndPoint("testCHL");
		System.out.println(confirmData);
		ServiceData confirmErrMapData = confirmData.getServiceData("IN_MAPPING");
		assertEquals(confirmErrMapData.getString("MAPPING_NAME"), "0929test��������ӳ��");
	}
	
	public void atest_����EndPointӳ��(){
		ServiceData endPoint = exportService.getEndPoint("testCHL");
		System.out.println("\n*************************************\n"+endPoint);
		assertEquals(endPoint.getString("CHANNEL_CODE"), "testCHL");
		ServiceData errMapData = endPoint.getServiceData("ERROR_MAPPING");
		assertEquals(errMapData.getString("MAPPING_NAME"), "test����ӳ��");
		errMapData.putString("MAPPING_NAME", "test��������ӳ��");
		endPoint.putServiceData("IN_MAPPING", errMapData);
		System.out.println("\n*************************************\n"+endPoint);
		int num = importService.updateEndPoint(endPoint);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getEndPoint("testCHL");
		System.out.println(confirmData);
		ServiceData confirmErrMapData = confirmData.getServiceData("IN_MAPPING");
		assertEquals(confirmErrMapData.getString("MAPPING_NAME"), "test��������ӳ��");
	}
	
	public void atest_�޸�Server��������(){
		ServiceData server = exportService.getServer("testSRV");
		System.out.println(server+"\n*************************************\n");
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		server.putString("SERVER_NAME", "1703�޸���Server����");
		System.out.println(server+"\n*************************************\n");
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getServer("testSRV");
		assertEquals(confirmData.getString("SERVER_NAME"), "1703�޸���Server����");
	}
	
	public void atest_�޸�Server��ͨѶ����(){
		ServiceData server = exportService.getServer("testSRV");
		System.out.println(server+"\n*************************************\n");
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		ServiceData commData = server.getServiceData("COMM_ID");
		commData.putString("CCODE", "test_srv_updated");
		System.out.println(server+"\n*************************************\n");
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getServer("testSRV");
		assertEquals(confirmData.getServiceData("COMM_ID").getString("CCODE"), "test_srv_updated");
	}
	
	public void atest_�޸�Server���ýӿ�(){
		ServiceData server = exportService.getServer("testSRV");
		System.out.println(server+"\n*************************************\n");
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		ServiceData configData = server.getServiceData("REQ_PACKAGE_CONFIG");
		ServiceData contentData = configData.getServiceData("STRUCTURE_CONTENT");
		contentData.putString("SDATAS", "{fdatas:[{field_code:\"O1MGID\", field_name:\"O1MGID\", field_type:\"string\", field_length:7, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:10}], is_strict:\"1\", package_mode:\"outsys_mode\", package_mode_name:\"outsys_mode\"}");
		System.out.println(server+"\n*************************************\n");
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getServer("testSRV");
		ServiceData configConfirmData = confirmData.getServiceData("REQ_PACKAGE_CONFIG");
		assertEquals(configConfirmData.getServiceData("STRUCTURE_CONTENT").getString("SDATAS"), "{fdatas:[{field_code:\"O1MGID\", field_name:\"O1MGID\", field_type:\"string\", field_length:7, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:10}], is_strict:\"1\", package_mode:\"outsys_mode\", package_mode_name:\"outsys_mode\"}");
	}
	
	public void atest_�޸�Serverӳ��(){
		ServiceData server = exportService.getServer("testSRV");
		System.out.println("\n************�޸�ǰ****************\n"+server);
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		ServiceData mapData = server.getServiceData("IN_MAPPING");
		assertEquals(mapData.getString("MAPPING_NAME"), "���ķ���ϵͳ��Ӧӳ��");
		mapData.putString("MAPPING_NAME", "0952�޸ĺ��ķ���ϵͳ��Ӧӳ��");
		System.out.println("\n************�޸ĺ�*******************\n"+server);
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getServer("testSRV");
		System.out.println("\n***********updateServer��***********\n"+confirmData);
		ServiceData mapConfirmData = confirmData.getServiceData("IN_MAPPING");
		assertEquals(mapConfirmData.getString("MAPPING_NAME"), "0952�޸ĺ��ķ���ϵͳ��Ӧӳ��");
	}
	
	public void atest_����Serverӳ��(){
		ServiceData server = exportService.getServer("testSRV");
		System.out.println("\n************�޸�ǰ****************\n"+server);
		assertEquals(server.getString("SERVER_CODE"), "testSRV");
		ServiceData mapData = server.getServiceData("ERROR_MAPPING");
		server.putServiceData("OUT_MAPPING", mapData);
		System.out.println("\n************�޸ĺ�*******************\n"+server);
		int num = importService.updateServer(server);
		assertEquals(num, 1);
		ServiceData confirmData = exportService.getServer("testSRV");
		ServiceData mapConfirmData = confirmData.getServiceData("OUT_MAPPING");
		assertEquals(mapConfirmData.getString("MAPPING_NAME"), "���ķ���ϵͳ����ӳ��");
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
//		System.out.println("Commited!");
	}
}
