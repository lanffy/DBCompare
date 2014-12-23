package compare;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.wk.Controller;
import com.wk.lang.SystemException;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;
import com.wk.util.JaDateTime;

/**
 * @description
 * @author raoliang
 * @version 2014年11月9日 下午4:47:27
 */
public class DBporter {
//	@Inject static ExportDatasFromDB exporter;
//	@Inject static ImportDatasToDB impoter;
	static ExportDatasFromDB exporter = Controller.getInstance().getInjector().getBean(ExportDatasFromDB.class);
	static ImportDatasToDB impoter = Controller.getInstance().getInjector().getBean(ImportDatasToDB.class);
	static DeleteDatasFromDB deleter = Controller.getInstance().getInjector().getBean(DeleteDatasFromDB.class);
	protected static final Log logger = LogFactory.getLog("dbcompare");
	protected static final Properties prop = new Properties();
	protected static final String dbfile = "/dbcompare.properties";
	static{
		URL url = DBExporter.class.getResource(dbfile);
		if(url == null){
			throw new SystemException("SYS_DB_FILE_NOT_EXIST").addScene("dbfile", dbfile);
		}
		File dbfile = new File(url.getFile());
		try {
			InputStream in = new FileInputStream(dbfile);
			prop.load(in);
		} catch (IOException e) {
			throw new RuntimeException("LOAD_DBPROPERTIES_FILE_ERROR");
		}
	}
	protected static final String DBDataDir = prop.getProperty("db.exportDirStr")+File.separator;
	
	protected static String getFileDir(String fileNameProp){
		return DBDataDir+prop.getProperty(fileNameProp)+File.separator;
	}
	
	protected static String getDeletedFileDir(String fileNameProp){
		return prop.getProperty(fileNameProp);
	}
	
	protected static File createFile(String dir){
		return DBCompare.createFile(dir);
	}
	
	protected static void createDeleteFile(String dir){
		boolean isCreateDeleteFile = false;
		String isCreateDeleteFileStr = prop.getProperty("db.isCreateDeleteFile");
		isCreateDeleteFile = isCreateDeleteFileStr == null ? false : Boolean.valueOf(isCreateDeleteFileStr);
		if(isCreateDeleteFile){
			DBCompare.createFile(dir);
		}
	}
	
	public static List<File> listAllFiles(File dir) {
		final List<File> file_list = new ArrayList<File>();
		if(dir.exists()){
			listAllFiles(dir, file_list);
		}
		return file_list;
	}

	public static void listAllFiles(File dir, List<File> file_list) {
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				file_list.add(file);
			}
		}
	}
	
	protected static String[] splitTrab(String tran){
		return tran.split(">");
	}
	
	protected static String getTime(){
		return JaDateTime.now().toDateString()+" "+JaDateTime.now().toTimeString();
	}
	
	protected static String replace(String fileName){
		return fileName.replaceAll("\\\\|/|:|\\*|\\?|\"|<|>|\\|", "_");
	}
	
//	protected void init() {
//		Controller.getInstance().getInjector().inject(this);
//	}
//
//	static {
//		new DBporter().init();
//	}
}
