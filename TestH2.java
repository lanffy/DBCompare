package compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.wk.Controller;
import com.wk.lang.Inject;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;
import com.wk.sdo.ServiceData;

/**
 * @description
 * @author raoliang
 * @version 2014年10月16日 上午11:23:25
 */
public class TestH2 {
//	private static String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
//	@Inject static ExportDatasFromDB exporter;
//	@Inject static ImportDatasToDB importer;
	private static final Log logger = LogFactory.getLog("dbcompare");
	private static final Log logger2 = LogFactory.getLog();
	public static void main(String[] args) throws IOException {
		logger.info("aaaaaaaaaaa");
		logger2.info("bbbbbbbbbbb");
	}
	
	private static String readFileToString(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = "";
		StringBuilder sb = new StringBuilder();
		while((line = reader.readLine()) != null){
			sb.append(line);
		}
		reader.close();
		return sb.toString();
	}
//	
//	private void init() {
//		Controller.getInstance().getInjector().inject(this);
//	}
//
//	static {
//		new TestH2().init();
//	}
}
