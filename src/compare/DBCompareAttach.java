package compare;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description
 * @author raoliang
 * @version 2014��9��21�� ����2:09:46
 */
public class DBCompareAttach {
	private static final String rowBegin = "<tr><td>";
	private static final String rowEnd = "</td></tr>";
	private static AtomicInteger counter = new AtomicInteger();
	public static void main(String[] args) throws Exception {
		String str = "{fdatas:[{field_code:\"test\", field_name:\"test\", field_type:\"String\", field_length:32, field_scale:0, field_category:\"2\", field_parent:\"\", field_id:1, type_fmodes:{}, type:\"modify\", childcount:\"0\", fexp:\"\", freq:\"0\"}], is_strict:\"1\", package_mode:\"outsys_mode\", package_mode_name:\"outsys_mode\"}";
		Pattern regex = Pattern.compile("\\[(.*)\\],(.*?)}");
		Matcher matche = regex.matcher(str);
		String fields = "";
		String modes = "";
		if(matche.find()){
			fields = matche.group(1).trim();
			modes = matche.group(2).trim();
		}
		System.out.println(modes(modes));
		System.out.println(fields(fields));
	}
	
	public static String putConfigInTable(String config){
		if(!(config.length()>0)){
			return "";
		}
		counter.set(1);
		StringBuilder table = new StringBuilder();
		table.append("<table id=\"tables\">");
		Pattern regex = Pattern.compile("\\[(.*)\\],(.*?)}");
		Matcher matche = regex.matcher(config);
		String fields = "";
		String modes = "";
		if(matche.find()){
			fields = matche.group(1).trim();
			modes = matche.group(2).trim();
			if(modes.length()>0){
				table.append(modes(modes).replaceAll("\\\\", ""));
			}
			if(fields.length()>0){
				table.append(fields(fields).replaceAll("\\\\", ""));
			}
			table.append("</table>");
			return table.toString();
		}else{
			fields = config.replaceAll("\\[|\\]", "").trim();
			table.append(fields(fields).replaceAll("\\\\", ""));
			table.append("</table>");
			return table.toString();
		}
	}
	
	private static String fields(String fields){
		StringBuilder fieldsStr = new StringBuilder();
		fields = fields.substring(1, fields.length()-1);
		String[] filedArray = fields.split("\\}, \\{");
		for (String string : filedArray) {
			Map<String, String> fieldMap = fill(string);
			fieldsStr.append(splitFieldMap(fieldMap));
		}
		return fieldsStr.toString();
	}
	
	private static String modes(String modes){
		String[] modeArray = modes.split(", ");
		StringBuilder mode = new StringBuilder(rowBegin);
		counter(mode);
		mode.append("�Ƿ��ϸ����ò���:");
		String is_strict = modeArray[0].split(":")[1].replaceAll("\"", "");
		if("0".equals(is_strict)){
			mode.append("��");
		}else if("1".equals(is_strict)){
			mode.append("��");
		}
		mode.append(";��ģʽ:");
		mode.append(modeArray[2].split(":")[1]);
		mode.append(rowEnd);
		return mode.toString();
	}
	
	private static void counter(StringBuilder sb){
		sb.append(counter.getAndIncrement()+"</td><td>");
	}
	
	private static Map<String, String> fill(String field){
		Map<String, String> fieldMap = new HashMap<String, String>();
		if(field.contains("type_fmodes")){
			int type_fmodesStart = field.indexOf("type_fmodes");
			int type_fmodesEnd = field.lastIndexOf("}");
			String before = field.substring(0, type_fmodesStart);
			String end = field.substring(type_fmodesEnd+3);
			fillMap(fieldMap, before);
			fillMap(fieldMap, end);
		}else{
			fillMap(fieldMap, field);
		}
		return fieldMap;
	}
	
	private static void fillMap(Map<String, String> map, String string){
		String[] array = string.split(", ");
		String key = "", value = "";
		for (String string2 : array) {
			key = string2.split(":")[0];
			value = string2.split(":")[1];
			map.put(key, value);
		}
	}
	
	private static String splitFieldMap(Map<String, String> map){
		StringBuilder field = new StringBuilder(rowBegin);
		counter(field);
		Map<String, String> field_category_Map = new HashMap<String, String>();
		field_category_Map.put("0", "�ṹ");
		field_category_Map.put("1", "����");
		field_category_Map.put("2", "��");
		
		String field_code = map.get("field_code");
		String field_name = map.get("field_name");
		String field_type = map.get("field_type") != null ? map.get("field_type") : "\"\"";
		String field_length = map.get("field_length") != null ? map.get("field_length") : "\"\"";
		String field_scale = map.get("field_scale") != null ? map.get("field_scale") : "\"\"";
		String field_category = map.get("field_category").replaceAll("\"", "");
		String field_parent = map.get("field_parent");
		String field_id = map.get("field_id");

		field.append("�����:"+field_code+";������:"+field_name+";������:"+field_category_Map.get(field_category)
				+";�ֶ�����:"+field_type+";�ֶγ���:"+field_length+";С��λ:"+field_scale);
		
		if(map.containsKey("fexp")){
			String fexp = map.get("fexp");
			field.append(";���������ʽ:"+fexp);
		}
		if(map.containsKey("freq")){
			String freq = map.get("freq");
			if("1".equals(freq)){
				field.append(";�Ƿ����:��");
			}
		}
		if(map.containsKey("anonymous")){
			String anonymous = map.get("anonymous");
			if("".equals(anonymous)){
				field.append(";�Ƿ������ṹ:");
			}else if("0".equals(anonymous)){
				field.append(";�Ƿ������ṹ:��");
			}else if("1".equals(anonymous)){
				field.append(";�Ƿ������ṹ:��");
			}
		}
		if(map.containsKey("faexp")){
			String faexp = map.get("faexp");
			field.append(";����ѭ�����ʽ:"+faexp);
		}
		if(map.containsKey("fasize")){
			String fasize = map.get("fasize");
			field.append(";�����С���ʽ:"+fasize);
		}
		if(map.containsKey("npmode")){
			String npmode = map.get("npmode");
			field.append(";���İ�ģʽ:"+npmode);
		}
		String nfmode = "";
		if(map.containsKey("nfmode")){
			nfmode = map.get("nfmode");
			field.append(";������ģʽ:"+nfmode);
		}
		if(map.containsKey("nfpmode")){
			String nfpmode = map.get("nfpmode");
			field.append(";����ģʽ:"+nfpmode);
		}
		if(map.containsKey("nfemode")){
			String nfemode = map.get("nfemode");
			field.append(";���������ģʽ:"+nfemode);
		}
		if(!"".equals(field_parent)){
			field.append(";field_parent="+field_parent);
		}
		field.append(";field_id="+field_id);
		field.append(rowEnd);
		return field.toString();
	}
	
}
