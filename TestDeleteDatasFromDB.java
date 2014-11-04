package compare;

import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.lang.Inject;
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
	
	public void atest_删除一个EndPoint的关联交易(){
		int delnum = deleteDatasFromDB.deleteOneTranEndPoint("testCHL", "8809");
		assertEquals(delnum, 1);
	}
	
	public void atest_删除一个Server的关联交易(){
		int delnum = deleteDatasFromDB.deleteOneTranServerByTranCode("3002");
		assertEquals(delnum, 1);
	}
	
	public void atest_删除一个EndPoint(){
		int num = deleteDatasFromDB.deleteOneEndPoint("testCHL");
		assertEquals(num, 1);
	}
	
	public void atest删除一个Server(){
		int num = deleteDatasFromDB.deleteOneServer("testSRV");
		assertEquals(num, 1);
	}
	
	@Override
	protected void tearDown() throws java.lang.Exception {
		System.out.println("******华丽丽的测试案例分割线******");
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
	}
}
