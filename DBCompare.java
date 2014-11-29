package compare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.h2.tools.Server;

import com.wk.lang.SystemException;
import com.wk.util.StringUtil;

/**
* 
* @author raoliang
* @version 2014��9��19�� ����4:33:57
*/
public class DBCompare {
	private static final Properties prop = new Properties();
	private static final String dbfile = "/dbcompare.properties";
	private static String reportDirStr = "";
	private static File reportfile = null;
	private static File menufile = null;
	private static FileWriter menuwriter = null;
	private static FileWriter writer = null;
	private static Statement oldst = null;
	private static Statement newst = null;
	private static final String REPORT_PAGE_HEAD = "<html>\r\n<head>\r\n<style type=\"text/css\">\n"
			+ "#tables\n"
			+ "  {\n"
			+ "  font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;\n"
			+ "  width:100%;\n"
			+ "  border-collapse:collapse;\n"
			+ "  }\n"
			+ "\n"
			+ "#tables td, #tables th \n"
			+ "  {\n"
			+ "  font-size:0.95em;\n"
			+ "  border:1px solid #999999;\n"
			+ "  padding:3px 7px 2px 7px;\n"
			+ "  }\n"
			+ "\n"
			+ "#tables th \n"
			+ "  {\n"
			+ "  font-size:1em;\n"
			+ "  text-align:left;\n"
			+ "  padding-top:5px;\n"
			+ "  padding-bottom:4px;\n"
			+ "  background-color:#B0B0B0;\n"
			+ "  color:#000000;\n"
			+ "  }\n"
			+ "\n"
			+ "#tables tr.modify td \n"
			+ "  {\n"
			+ "  color:#000000;\n"
			+ "  background-color:#EDEDED;\n"
			+ "  }\n"
			+ "#tables tr.delete td \n"
			+ "  {\n"
			+ "  color:red;\n"
			+ "  }\n"
			+ "</style>\r\n</head>\r\n<body>\r\n";
	private static final String RESULT_PAGE_HEAD ="<html>\r\n<head>\r\n"+
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"><style type=\"text/css\">"+
			"#bodys\r\n{\r\nfont-size:0.95em;\r\n}\r\n</style>\r\n</head>\r\n<body id=\"bodys\"><ol>";
	private static final String modifytableBegin = "<table id=\"tables\"><tr class=\"modify\" align=\"center\"><td>�޸���</td><td>�޸�ǰ</td><td>�޸ĺ�</td></tr>";
	private static final String noChange = "<tr><td colspan=\"3\">��</td></tr>";
	private static final String tableEnd = "</td></tr></table></td></tr>";
	private static final String pageEnd = "</body></html>";
	private static boolean isComparing = true;
	static{
		URL url = DBCompare.class.getResource(dbfile);
		if(url == null){
			throw new SystemException("SYS_DB_FILE_NOT_EXIST").addScene("dbfile", dbfile);
		}
		File dbfile = new File(url.getFile());
		try {
			InputStream in = new FileInputStream(dbfile);
			prop.load(in);
			reportDirStr = prop.getProperty("db.reportDirStr");
			
			reportfile = createFile(reportDirStr);
			menufile = createFile(reportfile.getParent()+File.separator+"menu.html");
			writer = new FileWriter(reportfile, true);
			menuwriter = new FileWriter(menufile, true);
			writer.write(REPORT_PAGE_HEAD);
			menuwriter.write(RESULT_PAGE_HEAD);
			writer.flush();
			menuwriter.flush();
		} catch (IOException e) {
			throw new RuntimeException("LOAD_DBPROPERTIES_FILE_ERROR");
		}
	}
	private static final String oldTcpPort = prop.getProperty("oldDb.jdbc.tcpPort");
	private static final String newTcpPort = prop.getProperty("newDb.jdbc.tcpPort");
	//���޸����Ӧ�������б�
	private static List<String> samelist = new ArrayList<String>();
	
	public static Connection getDbConnetion(String dbname) {
		dbname += ".jdbc.";
		try {
			String dirver = prop.getProperty(dbname + "driver");
			String url = "jdbc:h2:tcp://localhost:"+prop.getProperty(dbname+"tcpPort")+"/core";
			String username = prop.getProperty(dbname + "user");
			String password = prop.getProperty(dbname + "password");
			isArgumentEmpty(dirver);
			isArgumentEmpty(url);
			Class.forName(dirver);
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("DRIVER_NOT_FOUND");
		} catch (SQLException e) {
			throw new RuntimeException("GET_DB_CONNECTION_ERROR");
		}
	}

	/**
	 * ���ݿ�����ת��ΪCSV�ļ�
	 * @param csvDir CSV�ļ�����Ŀ¼
	 * @param DBName ���ݿ�����
	 * @param tableNames Ҫ�����ı���������
	 * @return ת�������ݱ���Ŀ
	 */
/**
	public static int dumpDataToCSV(File csvDir, String DBName) {
		Connection conn = getDbConnetion(DBName);
		int table_count = 0;
		try {
			for (String tableName : VerManageConfig.tableNames) {
				final Writer wr = new FileWriter(new File(csvDir, tableName	+ ".csv"));
				String sql = "select * from " + tableName;
				ResultSet rs = conn.createStatement().executeQuery(sql);
				Csv.getInstance().write(wr, rs);
				table_count++;
			}
		} catch (Exception e) {
			throw new RuntimeException("DUMP_DATE_ERROR");
		} finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException("CLOSE_CONNECTION_ERROR");
			}
		}
		return table_count;
	}
*/
	
	/**
	 * ���ݿ����ݱȽ�,ֱ���������ݿ���жԱ�
	 * @throws Exception 
	 */
	public static void compareData() throws Exception{
		startH2DB();
		printMark();
		//������ݿ�����
		Connection olddb = getDbConnetion("oldDb");
		Connection newdb = getDbConnetion("newDb");
		oldst = olddb.createStatement();
		newst = newdb.createStatement();
		//�Ƚ�EndPoint(SYS_CHANNEL)
		compareEndpoint();
		//�ȽϷ���ϵͳ(SYS_SERVER)
		compareServer();
		//�ȽϷ���(SYS_SERVICE)
		compareService();
		//�Ƚϲ���
		compareDploy();
		//�Ƚ���չ����
		compareExpand();
		menuwriter.write("</ol>"+pageEnd);
		writer.write(pageEnd);
		menuwriter.close();
		writer.close();
		oldst.close();
		newst.close();
		isComparing = false;
		stopH2DB();
	}
	
	private static void compareEndpoint(){
		compare("EndPoint");
	}
	
	private static void compareServer(){
		compare("Server");
	}
	
	private static void compare(String channelName){
		samelist.clear();
		String[] endpointColumnNames = {"CHANNEL_NAME", "GET_SVC_EXP", "PUT_SVC_EXP", "MSG_CLASS", "BIZ_CHANNEL_EXP"};
		String[] endpointColumnNamesCh = {"EndPoint����", "����ʶ����ʽ", "��������ӱ��ʽ", 	"��Ϣ����", "����ʶ����ʽ"};
		String[] serverColumnNames = {"SERVER_NAME", "SUCCESS_EXP", "PUT_SVC_EXP", "MSG_CLASS", "SERVER_ACTOR_CLASS"};
		String[] serverColumnNamesCh = {"����ϵͳ����", "�ɹ�ʶ����ʽ", "��������ӱ��ʽ", 	"��Ϣ����", "����ϵͳActorʵ����"};
		String[] columnNames = null;
		String[] columnNamesCh = null;
		boolean isChanged = false;
		String tableName = "";
		String code = "";
		String channelNameCh = "";
		ResultSet oldrs = null;
		ResultSet newrs = null;
		String sql = "";
		try {
			if("endpoint".equalsIgnoreCase(channelName)){
				columnNames = endpointColumnNames;
				columnNamesCh = endpointColumnNamesCh;
				tableName = "SYS_CHANNEL";
				code = "channel_code";
				channelNameCh = "EndPoint";
				menuwriter.write(liHref(channelNameCh));
			}else if("server".equalsIgnoreCase(channelName)){
				columnNames = serverColumnNames;
				columnNamesCh = serverColumnNamesCh;
				tableName = "SYS_SERVER";
				code = "server_code";
				channelNameCh = "����ϵͳ";
				menuwriter.write(liHref(channelNameCh));
			}else {
				throw new SystemException("SYS_CHANNEL_NAME_NOT_EXIST").addScene("ChannelName", channelName);
			}
			writer.write(new String(tableBegin(channelNameCh)));
			//��ý����
			sql = "SELECT "+code+" FROM "+tableName;
			oldrs = oldst.executeQuery(sql);
			newrs = newst.executeQuery(sql);
			//����������array��
			List<String> oldlist = new ArrayList<String>();
			List<String> newlist = new ArrayList<String>();
			
			while(oldrs.next()){
				oldlist.add(oldrs.getString(code));
			}
			while(newrs.next()){
				newlist.add(newrs.getString(code));
			}
			//�Ƿ�������,ɾ��
			StringBuilder add = new StringBuilder();
			AtomicInteger num = new AtomicInteger(0);
			for (String string : newlist) {
				if(!oldlist.contains(string)){
					num.incrementAndGet();
					add.append("["+string+"] ");
				}else{
					samelist.add(string);
				}
			}
			if(add.length()>0){
				menuwriter.write("&nbsp;����"+gethrefStr(channelName, num.get(), "add"));
				add.insert(0, "<tr id=\""+channelName+"_add\"><td>����"+channelName+"</td><td colspan=\"2\">");
				add.append("</td></tr>");
				writer.write(add.toString());
				isChanged = true;
			}
			StringBuilder delete = new StringBuilder();
			num.set(0);
			for (String string : oldlist) {
				if(!newlist.contains(string)){
					num.incrementAndGet();
					delete.append("["+string+"] ");
				}
			}
			if(delete.length()>0){
				menuwriter.write("&nbsp;ɾ��"+gethrefStr(channelName, num.get(), "del"));
				delete.insert(0, "<tr class=\"delete\" id="+channelName+"_del\"><td>ɾ��"+channelName+"</td><td colspan=\"2\">");
				delete.append("</td></tr>");
				writer.write(delete.toString());
				isChanged = true;
			}
			//�Ƚ���������(channel_name)������ʶ����ʽ(GET_SVC_EXP)����������ӱ��ʽ(PUT_SVC_EXP)
			//��Ϣ����(MSG_CLASS)������ʶ����ʽ(BIZ_CHANNEL_EXP)
			String oldcolumn = "";
			String newcolumn = "";
			StringBuilder modifyed = new StringBuilder();
			modifyed.append(modifytableBegin);
			for (String string : samelist) {
				sql = "select * from "+tableName+" where "+code+" ='"+string+"';";
				oldrs = oldst.executeQuery(sql);
				newrs = newst.executeQuery(sql);
				oldrs.next();newrs.next();
				for (int i = 0; i < columnNames.length; i++) {
					oldcolumn = oldrs.getString(columnNames[i]);
					newcolumn = newrs.getString(columnNames[i]);
					if(oldcolumn!=null&&!oldcolumn.equals(newcolumn)){
						num.incrementAndGet();
						modifyed.append("<tr class=\"modify\"><td>["+string+"]>["+columnNamesCh[i]+"]</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>");
					}
				}
			}
			//�Ƚ�ͨѶ 
			String comm = compareComm(channelName, num);
			if(comm.length()>0){
				modifyed.append(comm);
			}
			//����ͷ����
			String config = compareConfig(channelName, num);
			if(config.length()>0){
				modifyed.append(config);
			}
			//����ͷӳ��
			String map = compareMapping(channelName, num);
			if(map.length()>0){
				modifyed.append(map);
			}
			if(modifyed.length()>modifytableBegin.length()){
				modifyed.insert(0, "<tr id=\""+channelName+"_mod\"><td>�޸�"+channelName+"</td><td colspan=\"2\">");
				modifyed.append(tableEnd);
				menuwriter.write("&nbsp;�޸�"+gethrefStr(channelName, num.get(), "mod"));
				writer.write(modifyed.toString());
				isChanged = true;
			}
			//��������
			menuwriter.write("&nbsp;��������<br/>");
			if(!(compareTranPackage(channelName) || isChanged)){
				writer.write(noChange);
			}
			writer.write("</table></br>");
			writer.flush();
			menuwriter.flush();
		} catch (SQLException e) {
			throw new SystemException("SYS_EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} catch (IOException e) {
			throw new SystemException("SYS_WRITE_FILE_ERROR", e);
		} finally {
			closeResultSet(oldrs);
			closeResultSet(newrs);
		}
	}
	
	private static String compareComm(String flagStr, AtomicInteger modNum){
		StringBuilder modifyedTotal = new StringBuilder();
		for (String string : samelist) {
			modifyedTotal.append(compareComm(flagStr, string, modNum));
		}
		return modifyedTotal.toString();
	}
	
	/**
	 * �Ƚ�ͨѶ
	 * @param flagStr ��������
	 */
	private static String compareComm(String flagStr,String code, AtomicInteger modNum){
		String tableName = "";
		String channelName = "";
		if("endpoint".equalsIgnoreCase(flagStr)){
			tableName = "SYS_CHANNEL";
			channelName = "CHANNEL_CODE";
		}else if("server".equalsIgnoreCase(flagStr)){
			tableName = "SYS_SERVER";
			channelName = "SERVER_CODE";
		}else{
			throw new SystemException("SYS_CHANNEL_NAME_NOT_EXIST");
		}
		String[] columnNames = {"CCODE", "COMM_TYPE", "CONNECTION_MODE",
				"MAX_CONN_LIVE_TIME", "TIMEOUT", "WORKER", "PACKET_TYPE",
				"MSG_CLASS", "IMPL_CLASS", "TRUST_HOSTS", "REMOTE_ADDRESS",
				"MAX_ACTIVE", "MIN_ACTIVE", "MAX_PARTNER_WAIT_TIME",
				"MAX_CONN_IDLE_TIME", "MAX_CONN_INACTIVE_TIME",
				"CONN_CHECK_INTERVAL", "MATCH_REQUEST", "MATCH_PARTNER",
				"ROUND_ROBIN", "BIND_ADDRESS" };
		String[] columnNamesCh = {"ͨѶ����", "ͨѶ����", "����ģʽ", "����������ʱ��",
				"��ʱʱ��", "ioͨѶ�Ĵ�������", "ͨѶ��������", "��Ϣʵ����", "ͨѶʵ����", "����ip��ַ�б�",
				"����Զ�̵�ַ", "���������", "��С�������", "partnerʧЧ���´����Ե������ʱ��",
				"δ��ʹ�õĻ����������ʱ��", "��ռ��ʱ����ǰ���ӵ���Чʱ��", "����״̬���ļ��ʱ��", "�Ƿ�ƥ������",
				"�Ƿ�ƥ��partner", "�Ƿ���ѯpartern", "�����������˿�" };
		String sql = "";
		ResultSet new_comm_id_rs = null;
		ResultSet old_comm_id_rs = null;
		ResultSet old_comm_rs = null;
		ResultSet new_comm_rs = null;
		try {
			//���ͨѶid(comm_id)�Ľ����,��ͨѶid���뼯����
			sql = "SELECT comm_id FROM "+tableName+" where "+channelName+" ='"+code+"'";
			new_comm_id_rs = newst.executeQuery(sql);
			old_comm_id_rs = oldst.executeQuery(sql);
			new_comm_id_rs.next();old_comm_id_rs.next();
			String new_comm_id_str = new_comm_id_rs.getString("comm_id");
			String old_comm_id_str = old_comm_id_rs.getString("comm_id");
			//���ͨѶ�����,���бȽ�
			new_comm_rs = newst.executeQuery("select * from sys_comm where comm_id ='"+new_comm_id_str+"';");
			old_comm_rs = oldst.executeQuery("select * from sys_comm where comm_id ='"+old_comm_id_str+"';");
			old_comm_rs.next();new_comm_rs.next();
			String oldcolumn = "";
			String newcolumn = "";
			StringBuilder modifyed = new StringBuilder();
			for (int i = 0; i < columnNames.length; i++) {
				oldcolumn = old_comm_rs.getString(columnNames[i]);
				newcolumn = new_comm_rs.getString(columnNames[i]);
				if(!oldcolumn.equals(newcolumn)){
					modNum.incrementAndGet();
					modifyed.append("<tr class=\"modify\"><td>["+ code +"]>["+columnNamesCh[i]+"]</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>");
				}
			}
			return modifyed.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} finally {
			closeResultSet(new_comm_id_rs);
			closeResultSet(old_comm_id_rs);
			closeResultSet(old_comm_rs);
			closeResultSet(new_comm_rs);
		}
	}
	
	private static String compareConfig(String channelName, AtomicInteger modNum){
		StringBuilder configTotal = new StringBuilder();
		for (String string : samelist) {
			configTotal.append(compareConfig(channelName, string, modNum));
		}
		return configTotal.toString();
	}
	
	private static String compareConfig(String channelName, String code, AtomicInteger modNum){
		StringBuilder configOne = new StringBuilder();
		String req = "";
		String resp = "";
		String err = "";
		if("service".equalsIgnoreCase(channelName)){
			//��������ӿ�����
			req = compareConfig(channelName, "REQ_STRU", code);
			if(req.length()>0){
				modNum.incrementAndGet();
				configOne.append(req);
			}
			//������Ӧ�ӿ�����
			resp = compareConfig(channelName, "RESP_STRU", code);
			if(resp.length()>0){
				modNum.incrementAndGet();
				configOne.append(resp);
			}
			//�������ӿ�����
			err = compareConfig(channelName, "ERR_STRU", code);
			if(err.length()>0){
				modNum.incrementAndGet();
				configOne.append(err);
			}
		}else{
			//����ӿ�����
			req = compareConfig(channelName, "REQ_PACKAGE_CONFIG", code);
			if(req.length()>0){
				modNum.incrementAndGet();
				configOne.append(req);
			}
			//��Ӧ�ӿ�����
			resp = compareConfig(channelName, "RESP_PACKAGE_CONFIG", code);
			if(resp.length()>0){
				modNum.incrementAndGet();
				configOne.append(resp);
			}
			//����ӿ�����
			err = compareConfig(channelName, "ERR_PACKAGE_CONFIG", code);
			if(err.length()>0){
				modNum.incrementAndGet();
				configOne.append(err);
			}
		}
		return configOne.toString();
	}
	
	/**
	 * �Ƚϱ��Ľӿ�����
	 * @param oldst
	 * @param newst
	 * @param configColumnName
	 */
	private static String compareConfig(String flagStr, String configColumnName, String code){
		StringBuilder config = new StringBuilder();
		String tableName = "";
		String channelName = "";
		if("endpoint".equalsIgnoreCase(flagStr)){
			tableName = "SYS_CHANNEL";
			channelName = "CHANNEL_CODE";
		}else if("server".equalsIgnoreCase(flagStr)){
			tableName = "SYS_SERVER";
			channelName = "SERVER_CODE";
		}else if("service".equalsIgnoreCase(flagStr)){
			tableName = "SYS_SERVICE";
			channelName = "SERVICE_CODE";
		}else{
			throw new SystemException("SYS_CHANNEL_NAME_NOT_EXIST").addScene("ChannelName", flagStr);
		}
		Map<String, String> configNameMap = new HashMap<String, String>();
		configNameMap.put("REQ_PACKAGE_CONFIG", "����������");
		configNameMap.put("RESP_PACKAGE_CONFIG", "��Ӧ��������");
		configNameMap.put("ERR_PACKAGE_CONFIG", "����������");
		configNameMap.put("REQ_STRU", "�����������");
		configNameMap.put("RESP_STRU", "��Ӧ��������");
		configNameMap.put("ERR_STRU", "�����������");
		  	  	  
		String sql="";
		ResultSet new_config_rs = null;
		ResultSet old_config_rs = null;
		try {
			//���config ����,����list��
			sql = "SELECT "+configColumnName+" FROM "+tableName+" where "+channelName+" ='"+code+"'";
			new_config_rs = newst.executeQuery(sql);
			old_config_rs = oldst.executeQuery(sql);
			new_config_rs.next();old_config_rs.next();
			
			String new_config_id = new_config_rs.getString(configColumnName);
			String old_config_id = old_config_rs.getString(configColumnName);
			if(!StringUtil.isEmpty(new_config_id)&&StringUtil.isEmpty(old_config_id)){
				config.append("<tr><td colspan=\"3\">����["+code+"]>["+configNameMap.get(configColumnName)+"]</td></tr>");
			}else if(StringUtil.isEmpty(new_config_id)&&!StringUtil.isEmpty(old_config_id)){
				config.append("<tr class=\"delete\"><td colspan=\"3\">ɾ�� ["+code+"]>["+configNameMap.get(configColumnName)+"]</td></tr>");
			}else if(StringUtil.isEmpty(new_config_id)&&StringUtil.isEmpty(old_config_id)){
				return "";
			}else{
				String result = _compareConfig(old_config_id, new_config_id);
				if(result.length()>0){
					config.append("<tr class=\"modify\"><td>["+code+"]>["+configNameMap.get(configColumnName)+"]</td>"+result+"</tr>");
				}
			}
			return config.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} finally {
			closeResultSet(new_config_rs);
			closeResultSet(old_config_rs);
		}
	}
	
	private static String _compareConfig(String oldConfigId, String newConfigId){
		String _sql= "select * from sys_save_datas where sid =(select STRUCTURE_CONTENT from sys_structure where structure_id ='";
		String sql = "";
		String result = "";
		ResultSet new_config_rs = null;
		ResultSet old_config_rs = null;
		try {
			String oldcolumn = "";
			String newcolumn = "";
			
			sql = _sql+oldConfigId+"');";
			old_config_rs = oldst.executeQuery(sql);
			sql = _sql+newConfigId+"');";
			new_config_rs = newst.executeQuery(sql);
			StringBuffer old_config_datas = new StringBuffer();
			StringBuffer new_config_datas = new StringBuffer();
			while(old_config_rs.next()){
				old_config_datas.append(old_config_rs.getString("SDATAS"));
			}
			oldcolumn = old_config_datas.toString();
			while(new_config_rs.next()){
				new_config_datas.append(new_config_rs.getString("SDATAS"));
			}
			newcolumn = new_config_datas.toString();
			if(!oldcolumn.equals(newcolumn)){
				result = "<td>"+DBCompareAttach.putConfigInTable(oldcolumn)+"</td><td>"+DBCompareAttach.putConfigInTable(newcolumn)+"</td>";
			}
			return result;
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} finally {
			closeResultSet(new_config_rs);
			closeResultSet(old_config_rs);
		}
	}
	
	private static String compareMapping(String channelName, AtomicInteger modNum){
		StringBuilder mapTotal = new StringBuilder();
		for (String string : samelist) {
			mapTotal.append(compareMapping(channelName, string, modNum));
		}
		return mapTotal.toString();
	}
	
	private static String compareMapping(String channelName, String code, AtomicInteger modNum){
		StringBuilder mapOne = new StringBuilder();
		//����ӳ��
		mapOne.append(compareMapping(channelName, "IN_MAPPING", code, modNum));
		//��Ӧӳ��
		mapOne.append(compareMapping(channelName, "OUT_MAPPING", code, modNum));
		//����ӳ��
		mapOne.append(compareMapping(channelName, "ERROR_MAPPING", code, modNum));
		return mapOne.toString();
	}
	
	/**
	 * �Ƚϱ���ӳ��
	 * @param flagStr
	 * @param mappingName
	 */
	private static String compareMapping(String flagStr, String mappingName, String code, AtomicInteger modNum){
		StringBuilder map = new StringBuilder();
		String tableName = "";
		String channel_Name = "";
		String tran_name = "ϵͳ";
		Map<String, String> configNameMap = new HashMap<String, String>();
		if("endpoint".equalsIgnoreCase(flagStr)){
			tableName = "SYS_CHANNEL";
			channel_Name = "CHANNEL_CODE";
		}else if("server".equalsIgnoreCase(flagStr)){
			tableName = "SYS_SERVER";
			channel_Name = "SERVER_CODE";
		}else if("tranEndpoint".equalsIgnoreCase(flagStr)){
			tableName = "SYS_TRAN_CHANNEL_PACKAGE ";
			channel_Name = "CHANNEL_CODE";
			tran_name = "����";
		}else if("tranServer".equalsIgnoreCase(flagStr)){
			tableName = "sys_tran_server_package ";
			channel_Name = "SERVER_CODE";
			tran_name = "����";
		}else {
			throw new SystemException("SYS_CHANNEL_NAME_NOT_EXIST").addScene("tableName", tableName);
		}
		String[] columnNames = {"MAPPING_NAME","MAPPING_ATTR","MAPPING_SCRIPT","MAPPING_EXPR", "CTX_DATAS","IN_DATAS","OUT_DATAS"};
		String[] columnNamesCh = {"ӳ������","ӳ������","ӳ�����","ӳ����ʽ","�����ı���","����ṹ","����ṹ"};
		
		configNameMap.put("IN_MAPPING", tran_name + "����ӳ��");
		configNameMap.put("OUT_MAPPING", tran_name + "��Ӧӳ��");
		configNameMap.put("ERROR_MAPPING", tran_name + "����ӳ��");
		String sql="";
		ResultSet new_map_rs = null;
		ResultSet old_map_rs = null;
		try {
			//���map id ����,����list��
			List<String> new_map_id_list = new ArrayList<String>();
			List<String> old_map_id_list = new ArrayList<String>();
			sql = "SELECT "+mappingName+" FROM "+tableName+" where "+channel_Name+" ='"+splitTrab(code)[0]+"'";
			if("tranServer".equalsIgnoreCase(flagStr)||"tranEndpoint".equalsIgnoreCase(flagStr)){
				sql += " and tran_code='"+splitTrab(code)[1]+"'";
			}
			new_map_rs = newst.executeQuery(sql);
			old_map_rs = oldst.executeQuery(sql);
			new_map_rs.next();old_map_rs.next();
			String new_map_id = new_map_rs.getString(mappingName);
			String old_map_id = old_map_rs.getString(mappingName);
			if(!StringUtil.isEmpty(new_map_id)&&StringUtil.isEmpty(old_map_id)){
				modNum.incrementAndGet();
				map.append("<tr><td colspan=\"3\">����["+code+"]>["+configNameMap.get(mappingName)+"]</td></tr>");
			}else if(StringUtil.isEmpty(new_map_id)&&!StringUtil.isEmpty(old_map_id)){
				modNum.incrementAndGet();
				map.append("<tr class=\"delete\"><td colspan=\"3\">ɾ�� ["+code+"]>["+configNameMap.get(mappingName)+"]</td></tr>");
			}else if(StringUtil.isEmpty(new_map_id)&&StringUtil.isEmpty(old_map_id)){
				return "";
			}else{
				new_map_id_list.add(new_map_id);
				old_map_id_list.add(old_map_id);
				
				new_map_rs = newst.executeQuery("SELECT * FROM sys_mapping where ID ='"+new_map_id+"'");
				old_map_rs = oldst.executeQuery("SELECT * FROM sys_mapping where ID ='"+old_map_id+"'");
				old_map_rs.next();new_map_rs.next();
				if(old_map_rs.getRow()==0||new_map_rs.getRow()==0){
					//TODO:
					return "";
				}
				String oldcolumn = "";
				String newcolumn = "";
				StringBuilder mapModifyed = new StringBuilder();
				for (int i = 0; i < columnNames.length; i++) {
					oldcolumn = old_map_rs.getString(columnNames[i]);
					newcolumn = new_map_rs.getString(columnNames[i]);
					String changedStr = "<tr class=\"modify\"><td>["+code+"]>["+configNameMap.get(mappingName)+"]>["+columnNamesCh[i]+"]</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>";
					if(i < 3){
						if(!oldcolumn.equals(newcolumn)){
							modNum.incrementAndGet();
							mapModifyed.append(changedStr);
						}
					}else {
						//�Ա�ӳ�������
						String old_map_parameter = getSaveDatasById(oldcolumn, "old");
						String new_map_parameter = getSaveDatasById(newcolumn, "new");
						if(!old_map_parameter.equals(new_map_parameter)){
							modNum.incrementAndGet();
							mapModifyed.append(changedStr);
						}
					}
				}
				if(mapModifyed.length()>0){
					map.append(mapModifyed);
				}
			}
			return map.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} finally {
			closeResultSet(old_map_rs);
			closeResultSet(new_map_rs);
		}
	}
	
	private static String getSaveDatasById(String id, String stFlag) {
		String sql = "select sdatas from sys_save_datas where sid ='" + id + "'";
		ResultSet rs = null;
		StringBuilder dataSb = new StringBuilder();
		try {
			if ("old".equalsIgnoreCase(stFlag)) {
				rs = getDbConnetion("oldDb").createStatement().executeQuery(sql);
			}else if("new".equalsIgnoreCase(stFlag)){
				rs = getDbConnetion("newDb").createStatement().executeQuery(sql);
			}
			while(rs.next()){
				dataSb.append(rs.getString("SDATAS"));
			}
			return dataSb.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		}finally {
			closeResultSet(rs);
		}
	}
	
	private static boolean compareTranPackage(String flagStr){
		samelist.clear();
		String tableName = "";
		String channelName = "";
		if("endpoint".equalsIgnoreCase(flagStr)){
			tableName = "SYS_TRAN_CHANNEL_PACKAGE";
			channelName = "CHANNEL_CODE";
		}else if("server".equalsIgnoreCase(flagStr)){
			tableName = "sys_tran_server_package";
			channelName = "SERVER_CODE";
		}else{
			throw new SystemException("SYS_CHANNEL_NAME_NOT_EXIST").addScene("ChannelName", flagStr);
		}
		boolean isChanged = false;
		String sql="";
		ResultSet new_tran_rs = null;
		ResultSet old_tran_rs = null;
		try {
			//��ù��������б�,����list��
			List<String> new_tran_list = new ArrayList<String>();
			List<String> old_tran_list = new ArrayList<String>();
			sql = "select "+channelName+"||'>'||tran_code as tran from "+tableName+"";
			new_tran_rs = newst.executeQuery(sql);
			old_tran_rs = oldst.executeQuery(sql);
			while(new_tran_rs.next()){
				new_tran_list.add(new_tran_rs.getString("TRAN"));
			}
			while(old_tran_rs.next()){
				old_tran_list.add(old_tran_rs.getString("TRAN"));
			}
			//�Ƿ�������,ɾ����������
			AtomicInteger Num = new AtomicInteger(0);
			StringBuilder tranAdd = new StringBuilder();
			for (String string : new_tran_list) {
				if(!old_tran_list.contains(string)){
					Num.incrementAndGet();
					tranAdd.append("["+string+"] ");
				}else{
					samelist.add(string);
				}
			}
			if(tranAdd.length()>0){
				menuwriter.write("&nbsp;&nbsp;����"+gethrefStr(flagStr, Num.get(), "tran_add"));
				tranAdd.insert(0, "<tr id=\""+flagStr+"_tran_add\"><td>������������([������>������])</td><td colspan=\"2\">");
				tranAdd.append("</td></tr>");
				writer.write(tranAdd.toString());
				isChanged = true;
			}

			StringBuilder tranDel = new StringBuilder();
			Num.set(0);
			for (String string : old_tran_list) {
				if(!new_tran_list.contains(string)){
					Num.incrementAndGet();
					tranDel.append("["+string+"] ");
				}
			}
			if(tranDel.length()>0){
				menuwriter.write("&nbsp;&nbsp;ɾ��"+gethrefStr(flagStr, Num.get(), "tran_del"));
				tranDel.insert(0, "<tr class=\"delete\" id=\""+flagStr+"_tran_del\"><td>ɾ����������([������>������])</td><td colspan=\"2\">");
				tranDel.append("</td></tr>");
				writer.write(tranDel.toString());
				isChanged = true;
			}
			String oldcolumn = "";
			String newcolumn = "";
			StringBuilder tranModifyed = new StringBuilder();
			Num.set(0);
			tranModifyed.append(modifytableBegin);
			for (String string : samelist) {
				sql = "select tran_name from "+tableName+" where "+channelName+" ='"+splitTrab(string)[0]+"' and tran_code='"+splitTrab(string)[1]+"'";
				old_tran_rs = oldst.executeQuery(sql);
				new_tran_rs = newst.executeQuery(sql);
				old_tran_rs.next();new_tran_rs.next();
				oldcolumn = old_tran_rs.getString("tran_name");
				newcolumn = new_tran_rs.getString("tran_name");
				if(!oldcolumn.equals(newcolumn)){
					Num.incrementAndGet();
					tranModifyed.append("<tr class=\"modify\"><td>["+splitTrab(string)[0]+"]>["+splitTrab(string)[1]+"]������</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>");
				}
			}
			//�Աȹ������׵�ӳ��
			String tranMap = compareMapping("tran"+flagStr, Num);
			if(tranMap.length()>0){
				tranModifyed.append(tranMap);
			}
			//�Աȹ������׵ı�������
			String tranConfig = compareTranConfig(flagStr, Num);
			if(tranConfig.length()>0){
				tranModifyed.append(tranConfig);
			}
			if(tranModifyed.length()>modifytableBegin.length()){
				tranModifyed.insert(0, "<tr id=\""+flagStr+"_tran_mod\"><td>�޸Ĺ�������</td><td clospan=\"2\">");
				tranModifyed.append(tableEnd);
				menuwriter.write("&nbsp;&nbsp;�޸�"+gethrefStr(flagStr, Num.get(), "tran_mod"));
				writer.write(tranModifyed.toString());
				isChanged = true;
			}
			writer.flush();
			menuwriter.flush();
			return isChanged;
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} catch (IOException e) {
			throw new SystemException("WRITE_FILE_ERROR", e);
		} finally {
			closeResultSet(old_tran_rs);
			closeResultSet(new_tran_rs);
		}
	}
	
	private static String compareTranConfig(String channelName, AtomicInteger Num){
		StringBuilder tranConfigTotal = new StringBuilder();
		for (String string : samelist) {
			tranConfigTotal.append(compareTranConfig(channelName, string, Num));
		}
		return tranConfigTotal.toString();
	}
	
	private static String compareTranConfig(String channelName, String code, AtomicInteger Num){
		StringBuilder tranConfigOne = new StringBuilder(); 
		String req = compareTranConfig(channelName, "REQ_PACKAGE_CONFIG", code);
		String resp = compareTranConfig(channelName, "RESP_PACKAGE_CONFIG", code);
		//������������������ 
		if(req.length()>0){
			Num.incrementAndGet();
			tranConfigOne.append(req);
		}
		//����������Ӧ��������
		if(resp.length()>0){
			Num.incrementAndGet();
			tranConfigOne.append(resp);
		}
		return tranConfigOne.toString();
	}
	
	private static String compareTranConfig(String flagStr, String configColumnName, String code){
		String tableName = "";
		String channelName = "";
		StringBuilder tranConfig = new StringBuilder();
		if("endpoint".equalsIgnoreCase(flagStr)){
			tableName = "SYS_TRAN_CHANNEL_PACKAGE";
			channelName = "CHANNEL_CODE";
		}else if("server".equalsIgnoreCase(flagStr)){
			tableName = "SYS_TRAN_SERVER_PACKAGE";
			channelName = "SERVER_CODE";
		}else{
			throw new SystemException("SYS_CHANNEL_NAME_NOT_EXIST").addScene("ChannelName", flagStr);
		}
		Map<String, String> configNameMap = new HashMap<String, String>();
		configNameMap.put("REQ_PACKAGE_CONFIG", "����������");
		configNameMap.put("RESP_PACKAGE_CONFIG", "��Ӧ��������");
		String sql="";
		ResultSet new_tran_config_rs = null;
		ResultSet old_tran_config_rs = null;
		try {
			//���config ����,����list��
			sql = "SELECT "+configColumnName+" FROM "+tableName+" where "+channelName+" ='"+splitTrab(code)[0]+"' and tran_code='"+splitTrab(code)[1]+"'";
			new_tran_config_rs = newst.executeQuery(sql);
			old_tran_config_rs = oldst.executeQuery(sql);
			new_tran_config_rs.next();old_tran_config_rs.next();
			String new_tran_config_id = new_tran_config_rs.getString(configColumnName);
			String old_tran_config_id = old_tran_config_rs.getString(configColumnName);
			if(!StringUtil.isEmpty(new_tran_config_id)&&StringUtil.isEmpty(old_tran_config_id)){
				tranConfig.append("<tr><td colspan=\"3\">["+code+"]>["+configNameMap.get(configColumnName)+"]</td></tr>");
			}else if(StringUtil.isEmpty(new_tran_config_id)&&!StringUtil.isEmpty(old_tran_config_id)){
				tranConfig.append("<tr class=\"delete\"><td colspan=\"3\">ɾ��["+code+"]>["+configNameMap.get(configColumnName)+"]</td></tr>");
			}else if(StringUtil.isEmpty(new_tran_config_id)&&StringUtil.isEmpty(old_tran_config_id)){
				return "";
			}else{
				String result = _compareConfig(old_tran_config_id, new_tran_config_id);
				if(result.length()>0){
					tranConfig.append("<tr class=\"modify\" ><td>["+code+"]>["+configNameMap.get(configColumnName)+"]</td>"+result+"</br>");
				}
			}
			return tranConfig.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} finally {
			closeResultSet(old_tran_config_rs);
			closeResultSet(new_tran_config_rs);
		}
	}
	
	//�ȽϷ���
	private static void compareService(){
		samelist.clear();
		String[] columnNames = {"SERVICE_NAME", "SERVICE_TYPE", "IS_PUBLISHED", "SERVER_CODE", "EXTEND_SERVICE_NAME"};
		String[] columnNamesCh = {"��������", "��������", "����״̬", "����ϵͳ", "��չ����ķ�����"};
		Map<String, String> serviceNameMap = new HashMap<String, String>();
		serviceNameMap.put("0", "ԭ�ӷ���");
		serviceNameMap.put("1", "��չ����");
		serviceNameMap.put("2", "��Ϸ���");
		String sql="select SERVICE_CODE from sys_service";
		ResultSet old_service_rs = null;
		ResultSet new_service_rs = null;
		boolean isChanged = false;
		try {
			String channelName = "��&nbsp;&nbsp;&nbsp;&nbsp;��";
			menuwriter.write(liHref(channelName));
			writer.write(tableBegin(channelName));
			//��ý����
			old_service_rs = oldst.executeQuery(sql);
			new_service_rs = newst.executeQuery(sql);
			//����������array��
			List<String> old_service_list = new ArrayList<String>();
			List<String> new_service_list = new ArrayList<String>();
			
			while(old_service_rs.next()){
				old_service_list.add(old_service_rs.getString("SERVICE_CODE"));
			}
			while(new_service_rs.next()){
				new_service_list.add(new_service_rs.getString("SERVICE_CODE"));
			}
			//�Ƿ�������,ɾ��
			StringBuilder addServer = new StringBuilder();
			AtomicInteger num = new AtomicInteger(0);
			for (String string : new_service_list) {
				if(!old_service_list.contains(string)){
					num.incrementAndGet();
					addServer.append("["+string+"] ");
				}else{
					samelist.add(string);
				}
			}
			if(addServer.length()>0){
				addServer.insert(0, "<tr id=\""+channelName+"_add\"><td>��������</td><td colspan=\"2\">");
				addServer.append("</td></tr>");
				menuwriter.write("&nbsp;����"+gethrefStr(channelName, num.get(), "add"));
				writer.write(addServer.toString());
				isChanged = true;
			}
			StringBuilder delServer = new StringBuilder();
			num.set(0);
			for (String string : old_service_list) {
				if(!new_service_list.contains(string)){
					num.incrementAndGet();
					delServer.append("["+string+"] ");
				}
			}
			if(delServer.length()>0){
				delServer.insert(0, "<tr class=\"delete\" id=\""+channelName+"_del\"><td>ɾ������</td><td colspan=\"2\">");
				delServer.append("</td></tr>");
				menuwriter.write("&nbsp;ɾ��"+gethrefStr(channelName, num.get(), "del"));
				writer.write(delServer.toString());
				isChanged = true;
			}
			
			StringBuilder modifyServer = new StringBuilder();
			num.set(0);
			modifyServer.append(modifytableBegin);
			//��ñ������ý����,���бȽ�
			String oldcolumn = "";
			String newcolumn = "";
			for (String string : samelist) {
				sql = "select * from sys_service where SERVICE_CODE ='"+string+"'";
				old_service_rs = oldst.executeQuery(sql);
				new_service_rs = newst.executeQuery(sql);
				old_service_rs.next();
				new_service_rs.next();
				for (int i = 0; i < columnNames.length; i++) {
					newcolumn = new_service_rs.getString(columnNames[i]);
					oldcolumn = old_service_rs.getString(columnNames[i]);
					if(columnNames[i].equalsIgnoreCase("IS_PUBLISHED")){
						//ֹͣ-����
						if((oldcolumn!=null&&!"1".equals(oldcolumn))&&(newcolumn==null||"1".equals(newcolumn))){
							num.incrementAndGet();
							modifyServer.append("<tr class=\"modify\"><td>["+string+"]>["+columnNamesCh[i]+"]</td><td>ֹͣ</td><td>����</td></tr>");
						//����-ֹͣ
						}else if((oldcolumn==null||"1".equals(oldcolumn))&&(newcolumn!=null&&!"1".equals(newcolumn))){
							num.incrementAndGet();
							modifyServer.append("<tr class=\"modify\"><td>["+string+"]>["+columnNamesCh[i]+"]</td><td>����</td><td>ֹͣ</td></tr>");
						}
					}else {
						if(StringUtil.isEmpty(oldcolumn) && StringUtil.isEmpty(newcolumn)){
							continue;
						}else if(!StringUtil.isEmpty(oldcolumn) && StringUtil.isEmpty(newcolumn)){
							num.incrementAndGet();
							modifyServer.append("<tr class=\"delete\"><td colspan=\"3\">ɾ��["+string+"]>["+columnNamesCh[i]+"]</td></tr>");
						}else if(StringUtil.isEmpty(oldcolumn) && !StringUtil.isEmpty(newcolumn)){
							num.incrementAndGet();
							modifyServer.append("<tr><td colspan=\"3\">����["+string+"]>["+columnNamesCh[i]+"]:["+newcolumn+"]</td></tr>");
						}else if(!oldcolumn.equals(newcolumn)){
							num.incrementAndGet();
							if(columnNames[i].equalsIgnoreCase("SERVICE_TYPE")){
								modifyServer.append("<tr class=\"modify\"><td>["+string+"]>["+columnNamesCh[i]+"]</td><td>"+serviceNameMap.get(oldcolumn)+"</td><td>"+serviceNameMap.get(newcolumn)+"</td></tr>");
							}else{
								modifyServer.append("<tr class=\"modify\"><td>["+string+"]>["+columnNamesCh[i]+"]</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>");
							}
						}
						if (columnNames[i].equalsIgnoreCase("SERVICE_TYPE")) {
							String result = compareGroupSvc(string);
							if(result.length()>0){
								num.incrementAndGet();
								modifyServer.append(result);
							}
						}
					}
				}
			}
			//�ȽϷ���Ľӿ�����
			String config = compareConfig("service", num);
			if(config.length()>0){
				modifyServer.append(config);
			}
			if(modifyServer.length()>modifytableBegin.length()){
				modifyServer.insert(0, "<tr id=\""+channelName+"_mod\"><td>�޸ķ���</td><td colspan=\"2\">");
				modifyServer.append(tableEnd);
				menuwriter.write("&nbsp;�޸�"+gethrefStr(channelName, num.get(), "mod"));
				writer.write(modifyServer.toString());	
				isChanged = true;
			}
			if(!isChanged){
				writer.write(noChange);
			}
			writer.write("</table></br>");
			writer.flush();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} catch (IOException e) {
			throw new SystemException("WRITE_FILE_ERROR", e);
		} finally {
			closeResultSet(new_service_rs);
			closeResultSet(old_service_rs);
		}
	}
	
	/**
	 * �Ƚ���Ϸ�������ͼ
	 * @param service_code
	 * @return
	 */
	private static String compareGroupSvc(String service_code){
		String sql = "select SDATAS from sys_save_datas where sid = (select content from sys_group_svc_chart where service_code ='"+service_code+"')";
		ResultSet new_group_rs = null;
		ResultSet old_group_rs = null;
		try {
			new_group_rs = getDbConnetion("newDb").createStatement().executeQuery(sql);
			old_group_rs = getDbConnetion("oldDb").createStatement().executeQuery(sql);
			old_group_rs.next();new_group_rs.next();
			if(old_group_rs.getRow() == 0 && new_group_rs.getRow() == 0){
				return "";
			}else if(old_group_rs.getRow() != 0 && new_group_rs.getRow() == 0){
				return "<tr class=\"delete\"><td colspan=\"3\">ɾ����Ϸ��� ["+service_code+"]����Ϸ�������ͼ</td></tr>";
			}else if(old_group_rs.getRow() == 0 && new_group_rs.getRow() != 0){
				return "<tr><td colspan=\"3\">������Ϸ���["+service_code+"]����Ϸ�������ͼ</td></tr>";
			}else if(old_group_rs.getRow() != 0 && new_group_rs.getRow() != 0){
				String oldStr = old_group_rs.getString("SDATAS");
				while (old_group_rs.next()) {
					oldStr += old_group_rs.getString("SDATAS");
				}
				String newStr = new_group_rs.getString("SDATAS");
				while (new_group_rs.next()) {
					newStr += new_group_rs.getString("SDATAS");
				}
				if(!oldStr.equals(newStr)){
					return "<tr class=\"modify\"><td>�޸���Ϸ��� ["+service_code+"]��Ϸ�������ͼ</td><td>"+oldStr+"</td><td>"+newStr+"</td></tr>";
				}else{
					return "";
				}
			}
			return "";
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} finally {
			closeResultSet(old_group_rs);
			closeResultSet(new_group_rs);
		}
	}
	
	/**
	 * �Ƚϲ���
	 */
	private static void compareDploy(){
		try {
			menuwriter.write(liNoHref("��&nbsp;&nbsp;&nbsp;&nbsp;��"));
		} catch (IOException e) {
			throw new SystemException("SYS_DBCOMPARE_WRITE_MENU_FILE_EXCEPTION");
		}
		String machine = "�������б�";
		String process = "�����б�";
		writeToFile(compareMachine(machine), machine);
		writeToFile(compareProcess(process), process);
	}
	
	/**
	 * �ȽϷ������б�
	 */
	private static String compareMachine(String machineName){
		samelist.clear();
		StringBuilder machine = new StringBuilder();
		String[] columnNames = {"MACHINE_CODE", "MACHINE_IP", "MACHINE_NAME"};
		String[] columnNamesCh = {"����������", "������IP", "����������"};
		String sql="SELECT MACHINE_CODE FROM SYS_MACHINE";
		ResultSet old_machine_rs = null;
		ResultSet new_machine_rs = null;
		try {
			menuwriter.write("&nbsp;<a href=\""+reportfile.getName()+"#"+machineName+"\" target=\"show\">"+machineName+"</a></br>");
			//��ý����
			old_machine_rs = oldst.executeQuery(sql);
			new_machine_rs = newst.executeQuery(sql);
			//����������array��
			List<String> old_machine_list = new ArrayList<String>();
			List<String> new_machine_list = new ArrayList<String>();
			while(old_machine_rs.next()){
				old_machine_list.add(old_machine_rs.getString("MACHINE_CODE"));
			}
			while(new_machine_rs.next()){
				new_machine_list.add(new_machine_rs.getString("MACHINE_CODE"));
			}
			//�Ƿ�������,ɾ��
			StringBuilder addMachine = new StringBuilder();
			AtomicInteger num = new AtomicInteger(0);
			for (String string : new_machine_list) {
				if(!old_machine_list.contains(string)){
					num.incrementAndGet();
					addMachine.append("["+string+"] ");
				}else{
					samelist.add(string);
				}
			}
			if(addMachine.length()>0){
				addMachine.insert(0, "<tr id=\""+machineName+"_add\"><td>����������</td><td colspan=\"2\">");
				addMachine.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;����"+gethrefStr(machineName, num.get(), "add"));
				machine.append(addMachine);
			}
			StringBuffer delMachine = new StringBuffer();
			num.set(0);
			for (String string : old_machine_list) {
				if(!new_machine_list.contains(string)){
					num.incrementAndGet();
					delMachine.append("["+string+"] ");
				}
			}
			if(delMachine.length()>0){
				delMachine.insert(0, "<tr class=\"delete\" id=\""+machineName+"_del\"><td>ɾ��������</td><td colspan=\"2\">");
				delMachine.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;ɾ��"+gethrefStr(machineName, num.get(), "del"));
				machine.append(delMachine);
			}
			
			StringBuilder modifyMachine = new StringBuilder();
			num.set(0);
			modifyMachine.append(modifytableBegin);
			//��ñ������ý����,���бȽ�
			String oldcolumn = "";
			String newcolumn = "";
			for (String string : samelist) {
				sql = "SELECT * FROM SYS_MACHINE where MACHINE_CODE ='"+string+"'";
				old_machine_rs = oldst.executeQuery(sql);
				new_machine_rs = newst.executeQuery(sql);
				old_machine_rs.next();new_machine_rs.next();
				for (int i = 0; i < columnNames.length; i++) {
					oldcolumn = old_machine_rs.getString(columnNames[i]);
					newcolumn = new_machine_rs.getString(columnNames[i]);
					if(!oldcolumn.equals(newcolumn)){
						num.incrementAndGet();
						modifyMachine.append("<tr class=\"modify\"><td>["+string+"]�Ļ�������["+columnNamesCh[i]+"]</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>");
					}
				}
			}
			if(modifyMachine.length()>modifytableBegin.length()){
				modifyMachine.insert(0, "<tr id=\""+machineName+"_mod\"><td>�޸ķ�����</td><td colspan=\"2\">");
				modifyMachine.append(tableEnd);
				menuwriter.write("&nbsp;&nbsp;�޸�"+gethrefStr(machineName, num.get(), "mod"));
				machine.append(modifyMachine);
			}
			return machine.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} catch (IOException e) {
			throw new SystemException("SYS_DBCOMPARE_WRITE_MENU_FILE_EXCEPTION");
		} finally {
			closeResultSet(new_machine_rs);
			closeResultSet(old_machine_rs);
		}
	}
	
	/**
	 * �ȽϽ����б�
	 */
	private static String compareProcess(String processName){
		samelist.clear();
		StringBuilder process = new StringBuilder();
		String[] columnNames = {"SKEYC", "CHANNEL_CODE", "BIND_ADDRESS", "REMOTE_ADDRESS"};
		String[] columnNamesCh = {"���̱�ʶ����", "Endpoint����", "�����������˿�", "Զ�̷��ʵ�ַ"};
		String sql="SELECT CHANNEL_CODE,BIND_ADDRESS FROM SYS_PROCESS_INSTANCE";
		ResultSet old_process_rs = null;
		ResultSet new_process_rs = null;
		try {
			menuwriter.write("&nbsp;<a href=\""+reportfile.getName()+"#"+processName+"\" target=\"show\">"+processName+"</a></br>");
			//��ý����
			old_process_rs = oldst.executeQuery(sql);
			new_process_rs = newst.executeQuery(sql);
			//����������array��
			List<String> old_process_list = new ArrayList<String>();
			List<String> new_process_list = new ArrayList<String>();
			while(old_process_rs.next()){
				old_process_list.add(old_process_rs.getString("CHANNEL_CODE")+">"+old_process_rs.getString("BIND_ADDRESS"));
			}
			while(new_process_rs.next()){
				new_process_list.add(new_process_rs.getString("CHANNEL_CODE")+">"+new_process_rs.getString("BIND_ADDRESS"));
			}
			//�Ƿ�������,ɾ��
			AtomicInteger num = new AtomicInteger(0);
			StringBuilder addProcess = new StringBuilder();
			for (String string : new_process_list) {
				if(!old_process_list.contains(string)){
					num.incrementAndGet();
					addProcess.append("["+string+"] ");
				}else{
					samelist.add(splitTrab(string)[0]);
				}
			}
			if(addProcess.length()>0){
				addProcess.insert(0, "<tr id=\""+processName+"_add\"><td>�����������</td><td colspan=\"2\">");
				addProcess.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;����"+gethrefStr(processName, num.get(), "add"));
				process.append(addProcess);
			}
			StringBuilder delProcess = new StringBuilder();
			num.set(0);
			for (String string : old_process_list) {
				if(!new_process_list.contains(string)){
					num.incrementAndGet();
					delProcess.append("["+string+"] ");
				}
			}
			if(delProcess.length()>0){
				delProcess.insert(0, "<tr class=\"delete\" id=\""+processName+"_del\"><td>ɾ���������</td><td colspan=\"2\">");
				delProcess.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;ɾ��"+gethrefStr(processName, num.get(), "del"));
				process.append(delProcess);
			}
			StringBuilder modifyProcess = new StringBuilder();
			num.set(0);
			modifyProcess.append(modifytableBegin);
			//��ñ������ý����,���бȽ�
			String oldcolumn = "";
			String newcolumn = "";
			for (String string : samelist) {
				sql = "SELECT * FROM SYS_PROCESS_INSTANCE where CHANNEL_CODE ='"+string+"'";
				old_process_rs = oldst.executeQuery(sql);
				new_process_rs = newst.executeQuery(sql);
				old_process_rs.next();new_process_rs.next();
				for (int i = 0; i < columnNames.length; i++) {
					oldcolumn = old_process_rs.getString(columnNames[i]);
					newcolumn = new_process_rs.getString(columnNames[i]);
					if(!oldcolumn.equals(newcolumn)){
						num.incrementAndGet();
						modifyProcess.append("<tr class=\"modify\"><td>["+string+"]�Ĳ������["+columnNamesCh[i]+"]</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>");
					}
				}
			}
			if(modifyProcess.length()>modifytableBegin.length()){
				modifyProcess.insert(0, "<tr id=\""+processName+"_mod\"><td>�޸Ľ����б�</td><td colspan=\"2\">");
				modifyProcess.append(tableEnd);
				menuwriter.write("&nbsp;&nbsp;�޸�"+gethrefStr(processName, num.get(), "mod"));
				process.append(modifyProcess);
			}
			return process.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} catch (IOException e) {
			throw new SystemException("SYS_DBCOMPARE_WRITE_MENU_FILE_EXCEPTION");
		} finally {
			closeResultSet(new_process_rs);
			closeResultSet(old_process_rs);
		}
	}
	
	/**
	 * �Ƚ���չ����
	 */
	private static void compareExpand(){
		try {
			menuwriter.write(liNoHref("��չ����"));
		} catch (IOException e) {
			throw new SystemException("SYS_DBCOMPARE_WRITE_MENU_FILE_EXCEPTION");
		}
		String mode = "ģʽ";
		String modeParameters = "ģʽ����";
		String dict = "�����ֵ�";
		String dictParameters = "�����ֶ�";
		
		writeToFile(compareMode(mode), mode);
		writeToFile(compareModeParam(modeParameters), modeParameters);
		writeToFile(compareDict(dict), dict);
		writeToFile(compareDictDetail(dictParameters), dictParameters);
	}
	
	private static void writeToFile(String string, String tableName) {
		try {
			writer.write(tableBegin(tableName));
			if (string.length() > 0) {
				writer.write(string);
			}else {
				writer.write(noChange);
			}
			writer.write("</table></br>");
			writer.flush();
		} catch (IOException e) {
			throw new SystemException("SYS_WRITE_FILE_ERROR", e);
		}
	}
	
	/**
	 * �Ƚϰ�ģʽ
	 */
	private static String compareMode(String modeCh){
		samelist.clear();
		StringBuilder mode = new StringBuilder();
		String[] columnNames = {"MODE_CODE", "MODE_NAME", "MODE_TYPE", "MODE_CLASS", "IS_SYS_MODE"};
		String[] columnNamesCh = {"ģʽ����", "ģʽ����", "ģʽ����", "ģʽ��", "�Ƿ�ϵͳ����ģʽ"};
		Map<String, String> modeNameMap = new HashMap<String, String>();
		modeNameMap.put("0", "��ģʽ");
		modeNameMap.put("1", "��ģʽ");
		modeNameMap.put("2", "����ģʽ");
		modeNameMap.put("3", "���������ģʽ");
		
		String sql="SELECT * FROM SYS_MODE order by MODE_TYPE";
		ResultSet old_mode_rs = null;
		ResultSet new_mode_rs = null;
		try {
			menuwriter.write("&nbsp;<a href=\""+reportfile.getName()+"#"+modeCh+"\" target=\"show\">"+modeCh+"</a></br>");
			//��ý����
			old_mode_rs = oldst.executeQuery(sql);
			new_mode_rs = newst.executeQuery(sql);
			//����������array��
			List<String> old_mode_list = new ArrayList<String>();
			List<String> new_mode_list = new ArrayList<String>();
			
			
			Map<String, String> old_mode_type_map = new HashMap<String, String>();
			Map<String, String> new_mode_type_map = new HashMap<String, String>();
			Map<String, String> old_mode_name_map = new HashMap<String, String>();
			Map<String, String> new_mode_name_map = new HashMap<String, String>();
			String mode_code = "";
			while(old_mode_rs.next()){
				mode_code = old_mode_rs.getString("MODE_CODE");
				old_mode_list.add(mode_code);
				old_mode_type_map.put(mode_code, old_mode_rs.getString("MODE_TYPE"));
				old_mode_name_map.put(mode_code, old_mode_rs.getString("MODE_NAME"));
			}
			while(new_mode_rs.next()){
				mode_code = new_mode_rs.getString("MODE_CODE");
				new_mode_list.add(mode_code);
				new_mode_type_map.put(mode_code, new_mode_rs.getString("MODE_TYPE"));
				new_mode_name_map.put(mode_code, new_mode_rs.getString("MODE_NAME"));
			}
			//�Ƿ�������,ɾ��
			AtomicInteger num = new AtomicInteger(0);
			StringBuilder addMode = new StringBuilder();
			for (String string : new_mode_list) {
				if(!old_mode_list.contains(string)){
					num.incrementAndGet();
					addMode.append("["+modeNameMap.get(new_mode_type_map.get(string))+"]>["+string+"]>["+new_mode_name_map.get(string)+"]</br>");
				}else{
					samelist.add(string);
				}
			}
			if(addMode.length()>0){
				addMode.insert(0, "<tr id=\""+modeCh+"_add\"><td>����ģʽ</td><td colspan=\"2\">");
				addMode.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;����"+gethrefStr(modeCh, num.get(), "add"));
				mode.append(addMode);
			}
			
			StringBuilder delMode = new StringBuilder();
			num.set(0);
			for (String string : old_mode_list) {
				if(!new_mode_list.contains(string)){
					num.incrementAndGet();
					delMode.append("["+modeNameMap.get(old_mode_type_map.get(string))+"]>["+string+"]>["+old_mode_name_map.get(string)+"]</br>");
				}
			}
			if(delMode.length()>0){
				delMode.insert(0, "<tr class=\"delete\" id=\""+modeCh+"_del\"><td>ɾ��ģʽ</td><td colspan=\"2\">");
				delMode.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;ɾ��"+gethrefStr(modeCh, num.get(), "del"));
				mode.append(delMode);
			}
			
			StringBuilder modifyMode = new StringBuilder();
			num.set(0);
			modifyMode.append(modifytableBegin);
			//��ñ������ý����,���бȽ�
			String oldcolumn = "";
			String newcolumn = "";
			String mode_type = "";
			for (String string : samelist) {
				sql = "SELECT * FROM SYS_MODE where MODE_CODE ='"+string+"'";
				old_mode_rs = oldst.executeQuery(sql);
				new_mode_rs = newst.executeQuery(sql);
				old_mode_rs.next();new_mode_rs.next();
				for (int i = 0; i < columnNames.length; i++) {
					oldcolumn = old_mode_rs.getString(columnNames[i]);
					newcolumn = new_mode_rs.getString(columnNames[i]);
					if(!oldcolumn.equals(newcolumn)){
						num.incrementAndGet();
						mode_type = new_mode_rs.getString("MODE_TYPE");
						modifyMode.append("<tr class=\"modify\"><td>["+modeNameMap.get(mode_type)+"]�µ�["+string+"]��ģʽ����["+columnNamesCh[i]+"]</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>");
					}
				}
			}
			if(modifyMode.length()>modifytableBegin.length()){
				modifyMode.insert(0, "<tr id=\""+modeCh+"_mod\"><td>�޸�ģʽ</td><td colspan=\"2\">");
				modifyMode.append(tableEnd);
				menuwriter.write("&nbsp;&nbsp;�޸�"+gethrefStr(modeCh, num.get(), "mod"));
				mode.append(modifyMode);
			}
			return mode.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} catch (IOException e) {
			throw new SystemException("SYS_DBCOMPARE_WRITE_MENU_FILE_EXCEPTION");
		} finally {
			closeResultSet(new_mode_rs);
			closeResultSet(old_mode_rs);
		}
	}
	
	/**
	 * �Ƚ�ģʽ�Ĳ���
	 */
	private static String compareModeParam(String modeParameters){
		samelist.clear();
		StringBuilder modeParam = new StringBuilder();
		String[] columnNames = {"MODE_CODE", "MODE_TYPE", "PARAM_CODE", "PARAM_CLASS", "PARAM_VALUE"};
//		String[] columnNamesCh = {"ģʽ����", "ģʽ����", "ģʽ��������", "ģʽ������", "ģʽ����ֵ"};
		Map<String, String> modeNameMap = new HashMap<String, String>();
		modeNameMap.put("0", "��ģʽ");
		modeNameMap.put("1", "��ģʽ");
		modeNameMap.put("2", "����ģʽ");
		modeNameMap.put("3", "���������ģʽ");
		
		String sql="SELECT mode_code||'>'||mode_type||'>'||param_code as code,PARAM_VALUE FROM SYS_MODE_PARAM order by MODE_TYPE";
		ResultSet old_mode_rs = null;
		ResultSet new_mode_rs = null;
		try {
			menuwriter.write("&nbsp;<a href=\""+reportfile.getName()+"#"+modeParameters+"\" target=\"show\">"+modeParameters+"</a></br>");
			//��ý����
			old_mode_rs = oldst.executeQuery(sql);
			new_mode_rs = newst.executeQuery(sql);
			//����������array��
			List<String> old_mode_list = new ArrayList<String>();
			List<String> new_mode_list = new ArrayList<String>();
			
			
			Map<String, String> old_mode_type_map = new HashMap<String, String>();
			Map<String, String> new_mode_type_map = new HashMap<String, String>();
			Map<String, String> old_mode_code_map = new HashMap<String, String>();
			Map<String, String> new_mode_code_map = new HashMap<String, String>();
			Map<String, String> old_mode_value_map = new HashMap<String, String>();
			Map<String, String> new_mode_value_map = new HashMap<String, String>();
			String mode_code = "";
			while(old_mode_rs.next()){
				mode_code = old_mode_rs.getString("CODE");
				old_mode_list.add(mode_code);
				old_mode_type_map.put(mode_code, splitTrab(mode_code)[1]);
				old_mode_code_map.put(mode_code, splitTrab(mode_code)[0]);
				old_mode_value_map.put(mode_code, old_mode_rs.getString("PARAM_VALUE"));
			}
			while(new_mode_rs.next()){
				mode_code = new_mode_rs.getString("CODE");
				new_mode_list.add(mode_code);
				new_mode_type_map.put(mode_code, splitTrab(mode_code)[1]);
				new_mode_code_map.put(mode_code, splitTrab(mode_code)[0]);
				new_mode_value_map.put(mode_code, new_mode_rs.getString("PARAM_VALUE"));
			}
			//�Ƿ�������,ɾ��
			StringBuilder addModePar = new StringBuilder();
			AtomicInteger num = new AtomicInteger(0);
			for (String string : new_mode_list) {
				if(!old_mode_list.contains(string)){
					num.incrementAndGet();
					addModePar.append("["+modeNameMap.get(new_mode_type_map.get(string))+"]>["+new_mode_code_map.get(string)+"],ģʽ����:["+splitTrab(string)[2]+"]>["+new_mode_value_map.get(string)+"]</br>");
				}else{
					samelist.add(string);
				}
			}
			if(addModePar.length()>0){
				addModePar.insert(0, "<tr id=\""+modeParameters+"_add\"><td>����ģʽ����</td><td colspan=\"2\">");
				addModePar.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;����"+gethrefStr(modeParameters, num.get(), "add"));
				modeParam.append(addModePar);
			}
			StringBuilder delModePar = new StringBuilder();
			num.set(0);
			for (String string : old_mode_list) {
				if(!new_mode_list.contains(string)){
					num.incrementAndGet();
					delModePar.append("["+modeNameMap.get(old_mode_type_map.get(string))+"]>["+old_mode_code_map.get(string)+"],ģʽ����:["+splitTrab(string)[2]+"]>["+old_mode_value_map.get(string)+"]</br>");
				}
			}
			if(delModePar.length()>0){
				delModePar.insert(0, "<tr class=\"delete\" id=\""+modeParameters+"_del\"><td>ɾ��ģʽ����</td><td colspan=\"2\">");
				delModePar.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;ɾ��"+gethrefStr(modeParameters, num.get(), "del"));
				modeParam.append(delModePar);
			}
			StringBuilder modifyModePar = new StringBuilder();
			num.set(0);
			modifyModePar.append(modifytableBegin);
			//��ñ������ý����,���бȽ�
			String oldcolumn = "";
			String newcolumn = "";
			for (String string : samelist) {
				sql = "SELECT * FROM SYS_MODE_PARAM where MODE_CODE ='"+splitTrab(string)[0]+"' and MODE_TYPE='"+splitTrab(string)[1]+"' and PARAM_CODE='"+splitTrab(string)[2]+"'";
				old_mode_rs = oldst.executeQuery(sql);
				new_mode_rs = newst.executeQuery(sql);
				old_mode_rs.next();new_mode_rs.next();
				for (int i = 0; i < columnNames.length; i++) {
					oldcolumn = old_mode_rs.getString(columnNames[i]);
					newcolumn = new_mode_rs.getString(columnNames[i]);
					if(!oldcolumn.equals(newcolumn)){
						num.incrementAndGet();
						modifyModePar.append("<tr class=\"modify\"><td>["+modeNameMap.get(splitTrab(string)[1])+"]>["+splitTrab(string)[0]+"]>["+splitTrab(string)[2]+"]</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>");
					}
				}
			}
			if(modifyModePar.length()>modifytableBegin.length()){
				modifyModePar.insert(0, "<tr id=\""+modeParameters+"_mod\"><td>�޸�ģʽ����</td><td colspan=\"2\">");
				modifyModePar.append(tableEnd);
				menuwriter.write("&nbsp;&nbsp;�޸�"+gethrefStr(modeParameters, num.get(), "mod"));
				modeParam.append(modifyModePar);
			}
			return modeParam.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} catch (IOException e) {
			throw new SystemException("SYS_DBCOMPARE_WRITE_MENU_FILE_EXCEPTION");
		} finally {
			closeResultSet(new_mode_rs);
			closeResultSet(old_mode_rs);
		}
	}
	
	/**
	 * �Ƚ������ֵ�
	 */
	private static String compareDict(String dictCh){
		samelist.clear();
		StringBuilder dict = new StringBuilder();
		String sql="SELECT DICT_CODE FROM SYS_DICT";
		ResultSet old_dict_rs = null;
		ResultSet new_dict_rs = null;
		try {
			menuwriter.write("&nbsp;<a href=\""+reportfile.getName()+"#"+dictCh+"\" target=\"show\">"+dictCh+"</a></br>");
			//��ý����
			old_dict_rs = oldst.executeQuery(sql);
			new_dict_rs = newst.executeQuery(sql);
			//����������array��
			List<String> old_dict_list = new ArrayList<String>();
			List<String> new_dict_list = new ArrayList<String>();
			while(old_dict_rs.next()){
				old_dict_list.add(old_dict_rs.getString("DICT_CODE"));
			}
			while(new_dict_rs.next()){
				new_dict_list.add(new_dict_rs.getString("DICT_CODE"));
			}
			//�Ƿ�������,ɾ��
			StringBuilder addDict = new StringBuilder();
			AtomicInteger num = new AtomicInteger(0);
			for (String string : new_dict_list) {
				if(!old_dict_list.contains(string)){
					num.incrementAndGet();
					addDict.append("["+string+"] ");
				}else{
					samelist.add(string);
				}
			}
			if(addDict.length()>0){
				addDict.insert(0, "<tr id=\""+dictCh+"_add\"><td>���������ֵ�</td><td colspan=\"2\">");
				addDict.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;����"+gethrefStr(dictCh, num.get(), "add"));
				dict.append(addDict);
			}
			StringBuilder delDict = new StringBuilder();
			num.set(0);
			for (String string : old_dict_list) {
				if(!new_dict_list.contains(string)){
					num.incrementAndGet();
					delDict.append("["+string+"] ");
				}
			}
			if(delDict.length()>0){
				delDict.insert(0, "<tr class=\"delete\" id=\""+dictCh+"_del\"><td>ɾ�������ֵ�</td><td colspan=\"2\">");
				delDict.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;ɾ��"+gethrefStr(dictCh, num.get(), "del"));
				dict.append(delDict);
			}
			return dict.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} catch (IOException e) {
			throw new SystemException("SYS_DBCOMPARE_WRITE_MENU_FILE_EXCEPTION");
		} finally {
			closeResultSet(new_dict_rs);
			closeResultSet(old_dict_rs);
		}
	}

	/**
	 * �Ƚ������ֵ���ϸ�ֶ�
	 */
	private static String compareDictDetail(String dictParameters){
		samelist.clear();
		StringBuilder dictDetail = new StringBuilder();
		String[] columnNames = {"DICT_CODE", "FIELD_CODE", "FIELD_NAME", "FIELD_TYPE", "FIELD_LENGTH", "FIELD_SCALE"};
		String[] columnNamesCh = {"�����ֵ����", "�ֶα���", "�ֶ�����", "�ֶ�����", "�ֶγ���", "�ֶ�С��λ��"};
		String sql="SELECT dict_code||'>'||field_code as code FROM SYS_DICT_DETAIL";
		ResultSet old_dict_detail_rs = null;
		ResultSet new_dict_detail_rs = null;
		try {
			menuwriter.write("&nbsp;<a href=\""+reportfile.getName()+"#"+dictParameters+"\" target=\"show\">"+dictParameters+"</a></br>");
			//��ý����
			old_dict_detail_rs = oldst.executeQuery(sql);
			new_dict_detail_rs = newst.executeQuery(sql);
			//����������array��
			List<String> old_dict_detail_list = new ArrayList<String>();
			List<String> new_dict_detail_list = new ArrayList<String>();
			while(old_dict_detail_rs.next()){
				old_dict_detail_list.add(old_dict_detail_rs.getString("CODE"));
			}
			while(new_dict_detail_rs.next()){
				new_dict_detail_list.add(new_dict_detail_rs.getString("CODE"));
			}
			//�Ƿ�������,ɾ��
			StringBuilder addDictDetail = new StringBuilder();
			AtomicInteger num = new AtomicInteger(0);
			for (String string : new_dict_detail_list) {
				if(!old_dict_detail_list.contains(string)){
					num.incrementAndGet();
					addDictDetail.append("["+string+"] ");
				}else{
					samelist.add(string);
				}
			}
			if(addDictDetail.length()>0){
				addDictDetail.insert(0, "<tr id=\""+dictParameters+"_add\"><td>���������ֶ�</td><td colspan=\"2\">");
				addDictDetail.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;����"+gethrefStr(dictParameters, num.get(), "add"));
				dictDetail.append(addDictDetail);
			}
			StringBuilder delDictDetail = new StringBuilder();
			num.set(0);
			for (String string : old_dict_detail_list) {
				if(!new_dict_detail_list.contains(string)){
					num.incrementAndGet();
					delDictDetail.append("["+string+"] ");
				}
			}
			if(delDictDetail.length()>0){
				delDictDetail.insert(0, "<tr class=\"delete\" id=\""+dictParameters+"_del\"><td>ɾ�������ֶ�</td><td colspan=\"2\">");
				delDictDetail.append("</td></tr>");
				menuwriter.write("&nbsp;&nbsp;ɾ��"+gethrefStr(dictParameters, num.get(), "del"));
				dictDetail.append(delDictDetail);
			}
			
			StringBuilder modifyDictDetail = new StringBuilder();
			num.set(0);
			modifyDictDetail.append(modifytableBegin);
			String oldcolumn = "";
			String newcolumn = "";
			for (String string : samelist) {
				sql = "SELECT * FROM SYS_DICT_DETAIL where DICT_CODE ='"+splitTrab(string)[0]+"' and field_code='"+splitTrab(string)[1]+"'";
				old_dict_detail_rs = oldst.executeQuery(sql);
				new_dict_detail_rs = newst.executeQuery(sql);
				old_dict_detail_rs.next();new_dict_detail_rs.next();
				for (int i = 0; i < columnNames.length; i++) {
					oldcolumn = old_dict_detail_rs.getString(columnNames[i]);
					newcolumn = new_dict_detail_rs.getString(columnNames[i]);
					if(!oldcolumn.equals(newcolumn)){
						num.incrementAndGet();
						modifyDictDetail.append("<tr class=\"modify\"><td>["+string+"]>["+columnNamesCh[i]+"]</td><td>"+oldcolumn+"</td><td>"+newcolumn+"</td></tr>");
					}
				}
			}
			if(modifyDictDetail.length()>modifytableBegin.length()){
				modifyDictDetail.insert(0, "<tr id=\""+dictParameters+"_mod\"><td>�޸������ֶ�</td><td colspan=\"2\">");
				modifyDictDetail.append(tableEnd);
				menuwriter.write("&nbsp;&nbsp;�޸�"+gethrefStr(dictParameters, num.get(), "mod"));
				dictDetail.append(modifyDictDetail);
			}
			return dictDetail.toString();
		} catch (SQLException e) {
			throw new SystemException("EXECUTE_SQL_ERROR", e).addScene("sql", sql);
		} catch (IOException e) {
			throw new SystemException("SYS_DBCOMPARE_WRITE_MENU_FILE_EXCEPTION");
		} finally {
			closeResultSet(new_dict_detail_rs);
			closeResultSet(old_dict_detail_rs);
		}
	}
	
	private static String[] splitTrab(String tran){
		return tran.split(">");
	}
	
	private static String tableBegin(String channelName){
		return "<table id=\"tables\"><th colspan=\"3\" id=\""+channelName+"\">"+channelName + "</th>";
	}
	
	private static void closeResultSet(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				throw new SystemException("SYS_CLOSE_RESULT_SET_ERROR", e).addScene("ResultSet", rs);
			}
		}
	}
	
	private static void isArgumentEmpty(String string){
		if(StringUtil.isEmpty(string)){
			throw new SystemException("SYS_Argument_IS_NULL").addScene("Argument", string);
		}
	}
	
	/**
	 * �����ļ�,���ļ�����Ŀ¼������ʱ,�ȴ���Ŀ¼,�ڴ����ļ���
	 * @param filePathName
	 */
	public static File createFile(String filePathName){
		filePathName = filePathName.replaceAll("\\\\", "/");
		int lastSep = filePathName.lastIndexOf("/");
		String fileDirStr = filePathName.substring(0, lastSep);
		File fileDir = new File(fileDirStr);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File file = new File(filePathName);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	private static void startH2DB(){
		System.out.println("start old db");
		startH2DB(prop.getProperty("oldDb.jdbc.basedir"), oldTcpPort);
		System.out.println("start new db");
		startH2DB(prop.getProperty("newDb.jdbc.basedir"), newTcpPort);
	}
	
	private static void startH2DB(String dbDir, String dbTcpPort){
		String[] H2MainParameters = getH2MainParameters(dbDir, dbTcpPort);
		try {
			Server.main(H2MainParameters);
		} catch (SQLException e) {
			throw new SystemException("SYS_DBCOMPARE_H2DB_START_EXCEPTION")
			.addScene("dbDir:", dbDir)
			.addScene("dbTcpPort:", dbTcpPort);
		}
	}
	
	private static String[] getH2MainParameters(String dir, String tcpPort) {
		return ("-ifExists -baseDir " + dir + " -tcp -tcpPort " + tcpPort + " -tcpAllowOthers").split(" ");
	}
	
	private static void stopH2DB(){
		System.out.println("\nstop old db");
		stopH2DB(oldTcpPort);
		System.out.println("stop new db");
		stopH2DB(newTcpPort);
	}
	
	private static void stopH2DB(String dbTcpPort){
		String[] StopStr = getH2StopParameters(dbTcpPort);
		try {
			Server.main(StopStr);
		} catch (SQLException e) {
			throw new SystemException("SYS_DBCOMPARE_H2DB_STOP_EXCEPTION")
			.addScene("dbTcpPort:", dbTcpPort);
		}
	}
	
	private static String[] getH2StopParameters(String tcpPort){
		return ("-tcpShutdown tcp://localhost:"+tcpPort).split(" ");
	}
	
	private static String gethrefStr(String channelName, int num, String idName){
		return "<a href=\""+reportfile.getName()+"#"+channelName+"_"+idName+"\" target=\"show\"><b>"+num+"</b></a>��<br/>";
	}
	
	private static String liHref(String channelNameCh){
		return "<li><a href=\""+reportfile.getName()+"#"+channelNameCh+"\" target=\"show\">"+channelNameCh+"</a></li>";
	}
	
	private static String liNoHref(String channelNameCh){
		return "<li>"+channelNameCh+"</li>";
	}
	
	private static void printMark(){
		new Thread() {
			@Override
			public void run() {
				System.out.print("comparing.");
				while(isComparing){
					System.out.print(".");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}.start();
	}

	/**
	private static void writeTempDataToFile(){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(menufile)));
			String line = "";
			while((line = reader.readLine())!=null){
				menuwriter.write(line);
			}
		} catch (FileNotFoundException e) {
			throw new SystemException("SYS_DBCOMPARE_FILE_NOT_FOUND").addScene("FileName", menufile);
		} catch (IOException e) {
			throw new SystemException("SYS_DBCOMPARE_READE_FILE_ERROR").addScene("FileName", menufile);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				menuwriter.flush();
				if(menuwriter!=null){
					menuwriter.close();
				}
			} catch (IOException e) {
				throw new SystemException("SYS_DBCOMPARE_CLOSE_FILE_ERROR");
			}
		}
	}
	*/
	
	public static void main(String[] args) throws Exception {
		long time = System.currentTimeMillis();
		compareData();
		System.out.println("compare done!it takes:"+(System.currentTimeMillis()-time)/1000L+"s");
		System.exit(0);
	}
	
}
