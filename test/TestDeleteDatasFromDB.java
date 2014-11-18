package compare.test;

import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.lang.Inject;
import com.wk.test.TestCase;
import compare.DeleteDatasFromDB;
import compare.ExportDatasFromDB;
import compare.ImportDatasToDB;


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
	
	public void atest_删除一个原子服务(){
		int num = deleteDatasFromDB.deleteOneService("0001");
		assertEquals(num, 1);
	}
	
	public void atest_删除一个扩展服务(){
		int num = deleteDatasFromDB.deleteOneService("0002");
		assertEquals(num, 1);
	}
	
	public void atest_删除一个组合服务(){
		int num = deleteDatasFromDB.deleteOneService("0003");
		assertEquals(num, 1);
	}
	
	public void atest_删除一个服务器(){
		int num = deleteDatasFromDB.deleteOneMachine("002");
		assertEquals(num, 1);
	}
	
	public void atest_删除一条数据字段(){
		int num = deleteDatasFromDB.deleteOneDictDetail("test", "a");
		assertEquals(num, 1);
	}
	
	public void atest_删除一个数据字典(){
		int num = deleteDatasFromDB.deleteOneDict("test");
		assertEquals(num, 1);
	}
	
	public void atest_删除一条模式参数(){
		int num = deleteDatasFromDB.deleteOneModeParam("test_mode1", "1", "byte");
		assertEquals(num, 1);
	}
	
	public void atest_删除一条模式(){
		int num = deleteDatasFromDB.deleteOneMode("1", "test_mode1");
		assertEquals(num, 1);
	}
	
	@Override
	protected void tearDown() throws java.lang.Exception {
		System.out.println("******华丽丽的测试案例分割线******");
	}
	
	@Override
	protected void tearDownOnce() {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
	}
}
