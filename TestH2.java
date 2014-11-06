package compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.wk.Controller;
import com.wk.lang.Inject;
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
	
	public static void main(String[] args) throws IOException {
		ServiceData dataall = new ServiceData();
		ServiceData datas = new ServiceData();
		ServiceData dataa = new ServiceData();
		ServiceData datab = new ServiceData();
		dataa.putString("a1", "av1");
		dataa.putString("a2", "av2");
		datab.putString("b1", "bv1");
		datab.putString("b2", "bv2");
		datas.putServiceData("dataa", dataa);
		datas.putServiceData("datab", datab);
		dataall.putServiceData("dataall", datas);
		System.out.println(dataall+"\n******");
		String[] keys = dataall.getServiceData("dataall").getKeys();
		for (String string : keys) {
			System.out.println(string);
		}
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
