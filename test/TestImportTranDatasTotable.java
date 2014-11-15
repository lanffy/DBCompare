package compare.test;

import com.wk.eai.webide.dao.TranChannelPackageDaoService;
import com.wk.lang.Inject;
import com.wk.sdo.ServiceData;
import com.wk.test.TestCase;
import compare.ExportDatasFromDB;
import compare.ImportDatasToDB;


/**
* @author raoliang
* @version 2014��10��31�� ����5:30:41
*/
public class TestImportTranDatasTotable extends TestCase {
	@Inject  ExportDatasFromDB exportService;
	@Inject  ImportDatasToDB importService;
	@Inject  TranChannelPackageDaoService tranChannelPackageDaoService;
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("********�������Ĳ��԰����ָ���************");
	}
	
	public void atest_insertOneTranEndPoint(){
		ServiceData tranEndPoint = exportService.getOneChannelTran("npCHL", "8809");
		tranEndPoint.putString("CHANNEL_CODE", "testCHL");
		tranEndPoint.putString("TRAN_CODE", "8809");
		tranEndPoint.putString("TRAN_NAME", "8809���Թ�������");
		System.out.println(tranEndPoint);
		int num = importService.insertOneTranEndPoint(tranEndPoint);
		assertEquals(num, 1);
		tranEndPoint = exportService.getOneChannelTran("testCHL", "8809");
		assertEquals(tranEndPoint.getString("TRAN_NAME"), "8809���Թ�������");
	}
	
	public void atest_insertOneTranServer(){
		ServiceData tranData = exportService.getOneServerTran("bzfwptSRV", "3001");
		System.out.println("\n***�޸�ǰ***\n"+tranData);
		tranData.putString("SERVER_CODE", "testSRV");
		tranData.putString("TRAN_CODE", "3002");
		System.out.println("\n***�޸ĺ�***\n"+tranData);
		int num = importService.insertOneTranServer(tranData);
		assertEquals(num, 1);
		ServiceData confirmData =  exportService.getOneServerTran("testSRV", "3002");
		assertEquals(confirmData.getString("SERVER_CODE"), "testSRV");
		assertEquals(confirmData.getString("TRAN_CODE"), "3002");
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
//		System.out.println("Commited!");
	}
}