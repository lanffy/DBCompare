package compare;

import com.wk.lang.Inject;
import com.wk.sdo.ServiceData;
import com.wk.test.TestCase;


/**
 * @description
 * @author raoliang
 * @version 2014年11月4日 下午3:47:37
 */
public class TestDeleteDatasFromDB extends TestCase {
	@Inject DeleteDatasFromDB deleteDatasFromDB;
	@Inject  ImportDatasToDB importService;
	@Inject  ExportDatasFromDB exportService;
	
	public void test_删除一个EndPoint的关联交易(){
		ServiceData tranEndPoint = exportService.getChannelTran("npCHL", "8809");
		tranEndPoint.putString("CHANNEL_CODE", "testCHL");
		tranEndPoint.putString("TRAN_CODE", "8809");
		tranEndPoint.putString("TRAN_NAME", "8809测试关联交易");
		int addnum = importService.insertTranEndPoint(tranEndPoint);
		assertEquals(addnum, 1);
		tranEndPoint = exportService.getChannelTran("testCHL", "8809");
		assertEquals(tranEndPoint.getString("TRAN_NAME"), "8809测试关联交易");
		int delnum = deleteDatasFromDB.deleteOneTranEndPoint("testCHL", "8809");
		assertEquals(delnum, 1);
	}
	
	@Override
	protected void tearDown() throws java.lang.Exception {
		System.out.println("******华丽丽的测试案例分割线******");
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
		Session session = DBSource.getDefault().getSession();
		session.commit();
		session.close();
	}
}
