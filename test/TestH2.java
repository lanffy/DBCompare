package compare.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.wk.Controller;
import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.eai.webide.dao.ModeDaoService;
import com.wk.eai.webide.dao.ModeParamDaoService;
import com.wk.eai.webide.info.ModeParamInfo;
import com.wk.lang.Inject;

/**
 * @description
 * @author raoliang
 * @version 2014年10月16日 上午11:23:25
 */
public class TestH2 {
//	private static String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
//	@Inject static ExportDatasFromDB exporter;
//	@Inject static ImportDatasToDB importer;
	@Inject static ModeDaoService modeDaoService;
	@Inject static ModeParamDaoService modeParamDaoService;
	
	public static void main(String[] args) throws IOException {
		ModeParamInfo info = new ModeParamInfo();
		info.setMode_code("aaaaaaaaa");
		info.setMode_type("1");
		info.setParam_code("ab");
		info.setParam_class("string");
		info.setParam_value("zone");
		System.out.println(modeParamDaoService.addOneModeParam(info));
		Session session = DBSource.getDefault().getSession();
		session.commit();
		session.close();
		System.out.println("Commited!");
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
	
	private void init() {
		Controller.getInstance().getInjector().inject(this);
	}

	static {
		new TestH2().init();
	}
}
