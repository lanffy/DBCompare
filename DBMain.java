package compare;
/**
 * @description
 * @author raoliang
 * @version 2014年11月10日 下午4:10:39
 */
public class DBMain {
	public static void main(String[] args) {
		if(args.length != 1){
			help();
			System.exit(0);
		}
		if("-e".equalsIgnoreCase(args[0])){
			DBExporter.doExport();
			System.exit(0);
		}
		if("-i".equalsIgnoreCase(args[0])){
			DBImporter.doImport();
			System.exit(0);
		}
		help();
	}
	
	private static void help(){
		String str = "使用方法，参数只有一个: -e(数据导出)|-i(数据导入),忽略大小写.";
		System.out.println(str);
	}
}
