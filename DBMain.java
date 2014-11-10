package compare;
/**
 * @description
 * @author raoliang
 * @version 2014��11��10�� ����4:10:39
 */
public class DBMain {
	public static void main(String[] args) {
		if(args.length != 1){
			help();
			System.exit(0);
		}
		if("-e".equalsIgnoreCase(args[0])){
			DBExporter.doExport();
		}
		if("-m".equalsIgnoreCase(args[0])){
			DBImporter.doImport();
		}
	}
	
	private static void help(){
		String str = "ʹ�÷���������ֻ��һ��: -e(���ݵ���)|-i(���ݵ���),���Դ�Сд.";
		System.out.println(str);
	}
}
