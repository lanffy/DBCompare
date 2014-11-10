package compare.test;

import com.wk.lang.Inject;
import com.wk.test.TestCase;
import compare.DeleteDatasFromDB;
import compare.ExportDatasFromDB;
import compare.ImportDatasToDB;


/**
 * @description
 * @author raoliang
 * @version 2014��11��4�� ����3:47:37
 */
public class TestDeleteDatasFromDB extends TestCase {
	@Inject DeleteDatasFromDB deleteDatasFromDB;
	@Inject  ImportDatasToDB importService;
	@Inject  ExportDatasFromDB exportService;
	
	public void atest_ɾ��һ��EndPoint�Ĺ�������(){
		int delnum = deleteDatasFromDB.deleteOneTranEndPoint("testCHL", "8809");
		assertEquals(delnum, 1);
	}
	
	public void atest_ɾ��һ��Server�Ĺ�������(){
		int delnum = deleteDatasFromDB.deleteOneTranServerByTranCode("3002");
		assertEquals(delnum, 1);
	}
	
	public void atest_ɾ��һ��EndPoint(){
		int num = deleteDatasFromDB.deleteOneEndPoint("testCHL");
		assertEquals(num, 1);
	}
	
	public void atestɾ��һ��Server(){
		int num = deleteDatasFromDB.deleteOneServer("testSRV");
		assertEquals(num, 1);
	}
	
	@Override
	protected void tearDown() throws java.lang.Exception {
		System.out.println("******�������Ĳ��԰����ָ���******");
	}
	
	@Override
	protected void tearDownOnce() throws java.lang.Exception {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
	}
}
