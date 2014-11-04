package compare;

import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.lang.Inject;
import com.wk.sdo.ServiceData;
import com.wk.test.TestCase;


/**
 * @description
 * @author raoliang
 * @version 2014年10月31日 下午4:07:44
 */
public class TestImportDatasTotable extends TestCase {
	@Inject  ExportDatasFromDB exportService;
	@Inject  ImportDatasToDB importService;
	
	public void atest_insertEndPoint(){
		ServiceData endPoint = exportService.getEndPoint("npCHL");;
		endPoint.putString("CHANNEL_CODE", "testCHL");
		endPoint.putString("CHANNEL_NAME", "测试EndPoint");
		ServiceData commData = endPoint.getServiceData("COMM_ID");
		commData.putString("CCODE", "test_tcp");
		endPoint.putServiceData("COMM_ID", commData);
		int i = importService.insertEndPoint(endPoint);
		assertEquals(i, 1);
		ServiceData testChl = exportService.getEndPoint("testCHL");
		assertEquals(testChl.getString("CHANNEL_CODE"), "testCHL");
		System.out.println(testChl);
	}
	
	public void test_insertServer(){
		ServiceData serverData = exportService.getServer("inbankSRV");
		System.out.println("\n***修改前***\n"+serverData);
		assertEquals(serverData.getString("SERVER_CODE"), "inbankSRV");
		serverData.putString("SERVER_CODE", "testSRV");
		serverData.putString("SERVER_NAME", "test服务系统");
		ServiceData commData = serverData.getServiceData("COMM_ID");
		commData.putString("CCODE", "test_srv");
		serverData.getServiceData("REQ_PACKAGE_CONFIG").putString("SERVICE_CODE", "testSRV");
		serverData.getServiceData("RESP_PACKAGE_CONFIG").putString("SERVICE_CODE", "testSRV");
		serverData.getServiceData("ERR_PACKAGE_CONFIG").putString("SERVICE_CODE", "testSRV");
		serverData.getServiceData("IN_MAPPING").putString("REF_CODE", "testSRV");
		serverData.getServiceData("OUT_MAPPING").putString("REF_CODE", "testSRV");
		serverData.getServiceData("ERROR_MAPPING").putString("REF_CODE", "testSRV");
		serverData.getServiceData("IN_MAPPING").putString("MAPPING_CODE", "testSRV_resp");
		serverData.getServiceData("OUT_MAPPING").putString("MAPPING_CODE", "testSRV_req");
		serverData.getServiceData("ERROR_MAPPING").putString("MAPPING_CODE", "testSRV_err");
		System.out.println("\n***修改后***\n"+serverData);
		int num = importService.insertServer(serverData);
		assertEquals(num, 1);
		serverData = exportService.getServer("testSRV");
		assertEquals("testSRV", serverData.getString("SERVER_CODE"));
		assertEquals(serverData.getString("SERVER_NAME"), "test服务系统");
		commData = serverData.getServiceData("COMM_ID");
		assertEquals(commData.getString("CCODE"), "test_srv");
	}
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("********华丽丽的测试案例分割线************");
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
//		System.out.println("Commited!");
	}
}
