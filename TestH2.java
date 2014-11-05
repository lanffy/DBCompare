package compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.wk.Controller;
import com.wk.lang.Inject;
import com.wk.sdo.ServiceData;

/**
 * @description
 * @author raoliang
 * @version 2014年10月16日 上午11:23:25
 */
public class TestH2 {
	private static String filePath = "C:\\Users\\Administrator\\Desktop\\serviceData.json";
	@Inject static ExportDatasFromDB exporter;
	@Inject static ImportDatasToDB importer;
	
	public static void main(String[] args) throws IOException {
		ServiceData data = exporter.getServerTran("inbankSRV", "0052");
		ServiceData inmap = data.getServiceData("IN_MAPPING");
		System.out.println(data+"\n***************8");
		System.out.println(inmap == null);
		JSONFileUtil.storeServiceDataToJsonFile(data, filePath);
		ServiceData fileData = JSONFileUtil.loadJsonFileToServiceData(filePath);
		System.out.println(fileData);
		inmap = fileData.getServiceData("IN_MAPPING");
		System.out.println(inmap == null);
		System.out.println(inmap.size());
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
