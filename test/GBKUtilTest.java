package compare.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.wk.test.TestCase;
import com.wk.util.GBKProperties;

/**
 * @description
 * @author raoliang
 * @version 2014��12��24�� ����4:38:32
 */
public class GBKUtilTest extends TestCase{
	private static final String file = "/gbktest.properties";
	private static GBKProperties prop = new GBKProperties();
	private static InputStream in = null;
	private static URL url = GBKUtilTest.class.getResource(file);
	private static File propFile = new File(url.getFile());
	
	@Override
	protected void setUpOnce() throws java.lang.Exception {
		in = new FileInputStream(propFile);
		prop.load(in);
	}
	
	@Override
	protected void setUp() throws java.lang.Exception {
		System.out.println("***���԰����ָ���***");
	}
	
	public void test_��һ�����ڵĲ���(){
		String name = prop.getString("name", "noName");
		System.out.println(name);
	}
	
	public void test_��һ�������ڵĲ���(){
		int age = prop.getInt("age", 0);
		System.out.println(age);
	}
	
	public void test_��������(){
		OutputStream out = null;
		try {
			out = new FileOutputStream(propFile);
			prop.put("addArg", "addValue");
			prop.store(out, "insert");
		} catch (IOException e) {
			assertFalse(true);
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					assertFalse(true);
				}
			}
		}
	}
	
	public void test_�޸Ĳ���ֵ(){
		OutputStream out = null;
		try {
			out = new FileOutputStream(propFile);
			prop.setProperty("name", "changedNameValue");
			prop.store(out, "update");
		} catch (IOException e) {
			assertFalse(true);
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					assertFalse(true);
				}
			}
		}
	}
	
}
