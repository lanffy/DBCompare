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
	
	public void atest_ɾ��һ��ԭ�ӷ���(){
		int num = deleteDatasFromDB.deleteOneService("0001");
		assertEquals(num, 1);
	}
	
	public void atest_ɾ��һ����չ����(){
		int num = deleteDatasFromDB.deleteOneService("0002");
		assertEquals(num, 1);
	}
	
	public void atest_ɾ��һ����Ϸ���(){
		int num = deleteDatasFromDB.deleteOneService("0003");
		assertEquals(num, 1);
	}
	
	public void atest_ɾ��һ��������(){
		int num = deleteDatasFromDB.deleteOneMachine("002");
		assertEquals(num, 1);
	}
	
	public void atest_ɾ��һ�������ֶ�(){
		int num = deleteDatasFromDB.deleteOneDictDetail("test", "a");
		assertEquals(num, 1);
	}
	
	public void atest_ɾ��һ�������ֵ�(){
		int num = deleteDatasFromDB.deleteOneDict("test");
		assertEquals(num, 1);
	}
	
	public void atest_ɾ��һ��ģʽ����(){
		int num = deleteDatasFromDB.deleteOneModeParam("test_mode1", "1", "byte");
		assertEquals(num, 1);
	}
	
	public void atest_ɾ��һ��ģʽ(){
		int num = deleteDatasFromDB.deleteOneMode("1", "test_mode1");
		assertEquals(num, 1);
	}
	
	@Override
	protected void tearDown() throws java.lang.Exception {
		System.out.println("******�������Ĳ��԰����ָ���******");
	}
	
	@Override
	protected void tearDownOnce() {
//		Session session = DBSource.getDefault().getSession();
//		session.commit();
//		session.close();
	}
}
