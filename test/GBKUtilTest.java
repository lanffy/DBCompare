package compare.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.wk.util.GBKProperties;

/**
 * @description
 * @author raoliang
 * @version 2014年12月24日 下午4:38:32
 */
public class GBKUtilTest {
	private static final String file = "/gbktest.properties";
	public static void main(String[] args) throws FileNotFoundException, IOException {
		GBKProperties prop = new GBKProperties();
		InputStream stream = GBKUtilTest.class.getResourceAsStream(file);
		prop.load(new java.io.InputStreamReader(stream));
		stream.close();
		String name = prop.getString("name", "noName");
		int age = prop.getInt("age", 0);
		System.out.println(name);
		System.out.println(age);
		prop.setProperty("name", "changed");
		prop.put("add", "addvalue");
		name = prop.getString("name", "noName");
		System.out.println(name);
		String add = prop.getString("add");
		System.out.println(add);
	}
}
