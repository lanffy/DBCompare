package compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.wk.lang.SystemException;
import com.wk.sdo.ServiceData;
import com.wk.util.JSON;
import com.wk.util.JSONCaseType;

/**
 * @description 
 * <ol>此工具类有以下几个功能
 * <li>读取json格式文件，将其内容转换成ServiceData格式</li>
 * <li>将ServiceData格式内容以json的格式存入文件中</li>
 * </ol>
 * @author raoliang
 * @version 2014年11月5日 上午11:18:30
 */
public class JSONFileUtil {
	
	/**
	* @description ServiceData格式数据转换成json字符串
	* @param data 数据源
	* @return json字符串
	* @author raoliang
	* @version 2014年11月5日 上午10:27:58
	*/
	public static String convertServiceDataToJson(ServiceData data){
		return JSON.fromServiceData(data, JSONCaseType.DEFAULT);
	}
	
	/**
	* @description 将ServiceData以json格式写入文件中
	* @param data 数据源
	* @param filePath 文件路径
	* @author raoliang
	* @version 2014年11月5日 上午11:17:13
	*/
	public static void storeServiceDataToJsonFile(ServiceData data, String filePath){
		creatNewFile(filePath);
		storeServiceDataToJsonFile(data, new File(filePath));
	}
	
	/**
	* @description 将ServiceData以json格式写入文件中
	* @param data 数据源
	* @param file 文件
	* @author raoliang
	* @version 2014年11月5日 上午11:15:58
	*/
	private static void storeServiceDataToJsonFile(ServiceData data, File file) {
		String json = convertServiceDataToJson(data);
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(json);
		} catch (IOException e) {
			throw new SystemException("SYS_DB_COMPARE_GET_FILE_WRITER_ERROR")
					.addScene("filePath", file.getAbsolutePath());
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new SystemException(
							"SYS_DB_COMPARE_CLOSE_FILE_WRITER_ERROR").addScene(
							"filePath", file.getAbsolutePath());
				}
			}
		}
	}
	
	/**
	* @description 读取json格式文件内容，转换成ServiceData格式
	* @param fileName json格式文件路径名称
	* @return ServiceData格式内容
	* @author raoliang
	* @version 2014年11月5日 上午10:28:00
	*/
	public static ServiceData loadJsonFileToServiceData(String fileName) {
		isFileExist(fileName);
		return loadJsonFileToServiceData(new File(fileName));
	}
	
	/**
	* @description 读取json格式文件内容，转换成ServiceData格式
	* @param file json格式文件
	* @return ServiceData格式内容
	* @author raoliang
	* @version 2014年11月5日 上午10:28:02
	*/
	public static ServiceData loadJsonFileToServiceData(File file) {
		isFileExist(file);
        String json = "";
		try {
			json = readFileToString(file);
		} catch (IOException e) {
			throw new SystemException("SYS_DB_COMPARE_READ_FILE_TO_SERVICEDATA_ERROR")
				.addScene("filePath", file.getAbsolutePath());
		}
		return JSON.toServiceDataByType(json, JSONCaseType.DEFAULT);
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
	
	private static void isFileExist(String filePath){
		File file = new File(filePath);
		isFileExist(file);
	}
	
	private static void isFileExist(File file){
		if (!file.exists()) {
			throw new SystemException("SYS_DB_COMPARE_FILE_IS_NOT_EXIST")
					.addScene("filePath", file.getAbsolutePath());
		}
	}
	
	private static void creatNewFile(String filePath){
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new SystemException("SYS_DB_COMPARE_CREATE_FILE_ERROR")
			.addScene("filePath", filePath);
		}
	}
}
