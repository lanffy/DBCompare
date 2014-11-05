package compare;

import java.util.List;

import com.wk.eai.webide.dao.ChannelDaoService;
import com.wk.eai.webide.dao.CommDaoService;
import com.wk.eai.webide.dao.GroupSvcChartDaoService;
import com.wk.eai.webide.dao.MappingDaoService;
import com.wk.eai.webide.dao.SaveDatasDao;
import com.wk.eai.webide.dao.SaveDatasDaoService;
import com.wk.eai.webide.dao.ServerDaoService;
import com.wk.eai.webide.dao.ServiceDaoService;
import com.wk.eai.webide.dao.StructureDaoService;
import com.wk.eai.webide.dao.TranChannelPackageDaoService;
import com.wk.eai.webide.dao.TranServerPackageDaoService;
import com.wk.eai.webide.info.ChannelInfo;
import com.wk.eai.webide.info.CommInfo;
import com.wk.eai.webide.info.MappingInfo;
import com.wk.eai.webide.info.SaveDatasInfo;
import com.wk.eai.webide.info.ServerInfo;
import com.wk.eai.webide.info.ServiceInfo;
import com.wk.eai.webide.info.StructureInfo;
import com.wk.eai.webide.info.TranChannelPackageInfo;
import com.wk.eai.webide.info.TranServerPackageInfo;
import com.wk.lang.Inject;
import com.wk.lang.SystemException;
import com.wk.sdo.ServiceData;
import com.wk.util.StringUtil;

/**
 * @description 导出数据表中的数据到文件中
 * @author raoliang
 * @version 2014年10月30日 上午11:49:46
 */
public class ExportDatasFromDB {
	@Inject static CommDaoService commDaoSevice;
	@Inject static StructureDaoService structureDaoSevice;
	@Inject static SaveDatasDaoService saveDatasDaoSevice;
	@Inject static SaveDatasDao saveDatasDao;
	@Inject static MappingDaoService mapDaoService;
	@Inject static TranChannelPackageDaoService tranChannelPackageDaoService;
	@Inject static TranServerPackageDaoService tranServerPackageDaoService;
	@Inject static ChannelDaoService channelSevice;
	@Inject static ServerDaoService serverSevice;
	@Inject static ServiceDaoService serviceDaoService;
	@Inject static GroupSvcChartDaoService groupSvcChartDaoService;
	
	public static void main(String[] args) {
	}
	
	/**
	* @description 得到指定EndPoint的单元数据
	* @author raoliang
	* @version 2014年11月2日 下午3:02:47
	*/
	public ServiceData getEndPoint(String channel_code){
		ChannelInfo channelInfo = channelSevice.getOneChannel(channel_code);
		if(channelInfo == null){
			throw new SystemException("SYS_DB_COMPARE_ENDPOINT_IS_NOT_EXIST").addScene("channel_code", channel_code);
		}
		ServiceData channelData = new ServiceData();
		putChannelBasicPar(channelData, channelInfo);
		//通讯
		channelData.putServiceData("COMM_ID", putComm(channelInfo.getComm_id()));
		//接口
		channelData.putServiceData("REQ_PACKAGE_CONFIG", getStructure(channelInfo.getReq_package_config()));
		channelData.putServiceData("RESP_PACKAGE_CONFIG", getStructure(channelInfo.getResp_package_config()));
		channelData.putServiceData("ERR_PACKAGE_CONFIG", getStructure(channelInfo.getErr_package_config()));
		//映射
		channelData.putServiceData("IN_MAPPING", getMap(channelInfo.getIn_mapping()));
		channelData.putServiceData("OUT_MAPPING", getMap(channelInfo.getOut_mapping()));
		channelData.putServiceData("ERROR_MAPPING", getMap(channelInfo.getError_mapping()));
		return channelData;
	}

	/**
	* @description 得到指定服务系统的单元数据
	* @author raoliang
	* @version 2014年11月2日 下午3:03:13
	*/
	public ServiceData getServer(String server_code){
		ServerInfo serverInfo = serverSevice.getOneServer(server_code);
		if(serverInfo == null){
			throw new SystemException("SYS_DB_COMPARE_SERVER_IS_NOT_EXIST").addScene("server_code", server_code);
		}
		ServiceData serverData = new ServiceData();
		putServerBasicPar(serverData, serverInfo);
		//通讯
		serverData.putServiceData("COMM_ID", putComm(serverInfo.getComm_id()));
		//接口
		serverData.putServiceData("REQ_PACKAGE_CONFIG", getStructure(serverInfo.getReq_package_config()));
		serverData.putServiceData("RESP_PACKAGE_CONFIG", getStructure(serverInfo.getResp_package_config()));
		serverData.putServiceData("ERR_PACKAGE_CONFIG", getStructure(serverInfo.getErr_package_config()));
		//映射
		serverData.putServiceData("IN_MAPPING", getMap(serverInfo.getIn_mapping()));
		serverData.putServiceData("OUT_MAPPING", getMap(serverInfo.getOut_mapping()));
		serverData.putServiceData("ERROR_MAPPING", getMap(serverInfo.getError_mapping()));
		return serverData;
	}
	
	/**
	 * 得到指定EndPoint关联交易单元数据
	 * @param channel_code 渠道名称
	 * @param tran_code 关键交易名称
	 * @return 相关数据
	 */
	public ServiceData getChannelTran(String channel_code, String tran_code){
		TranChannelPackageInfo info = tranChannelPackageDaoService.getOneTran(channel_code, tran_code);
		if(info == null){
			throw new SystemException("SYS_DB_COMPARE_ENDPOINT_RELATED_TRANSACTION_IS_NOT_EXIST")
				.addScene("channel_code", channel_code)
				.addScene("tran_code", tran_code);
		}
		ServiceData data = new ServiceData();
		data.putString("CHANNEL_CODE", info.getChannel_code());
		data.putString("TRAN_CODE", info.getTran_code());
		data.putString("TRAN_NAME", info.getTran_name());
		data.putServiceData("REQ_PACKAGE_CONFIG", getStructure(info.getReq_package_config()));
		data.putServiceData("RESP_PACKAGE_CONFIG", getStructure(info.getResp_package_config()));
		data.putServiceData("IN_MAPPING", getMap(info.getIn_mapping()));
		data.putServiceData("OUT_MAPPING", getMap(info.getOut_mapping()));
		data.putServiceData("ERROR_MAPPING", getMap(info.getError_mapping()));
		data.putString("VERNO", info.getVerno());
		return data;
	}

	/**
	 * 得到服务系统指定关联交易单元数据
	 * @param server_code 服务系统名称
	 * @param tran_code 关联交易名称
	 * @return 关联交易相关数据
	 */
	public ServiceData getServerTran(String server_code, String tran_code){
		TranServerPackageInfo info = tranServerPackageDaoService.getOneTran(server_code, tran_code);
		if(info == null){
			throw new SystemException("SYS_DB_COMPARE_SERVER_RELATED_TRANSACTION_IS_NOT_EXIST")
				.addScene("server_code", server_code)
				.addScene("tran_code", tran_code);
		}
		ServiceData data = new ServiceData();
		data.putString("SERVER_CODE", info.getServer_code());
		data.putString("TRAN_CODE", info.getTran_code());
		data.putString("TRAN_NAME", info.getTran_name());
		data.putServiceData("REQ_PACKAGE_CONFIG", getStructure(info.getReq_package_config()));
		data.putServiceData("RESP_PACKAGE_CONFIG", getStructure(info.getResp_package_config()));
		data.putServiceData("IN_MAPPING", getMap(info.getIn_mapping()));
		data.putServiceData("OUT_MAPPING", getMap(info.getOut_mapping()));
		data.putServiceData("ERROR_MAPPING", getMap(info.getError_mapping()));
		data.putString("VERNO", info.getVerno());
		return data;
	}
	
	/**
	* @description 得到指定服务名称的单元数据
	* @param service_code 服务名称
	* @return 服务单元数据
	* @author raoliang
	* @version 2014年11月4日 下午7:40:42
	*/
	public ServiceData getOneService(String service_code) {
		ServiceInfo info = serviceDaoService.getOneServiceByCode(service_code);
		if (info == null) {
			throw new SystemException("SYS_DB_COMPARE_SERVICE_IS_NOT_EXIST")
					.addScene("service_code", service_code);
		}
		ServiceData data = new ServiceData();
		data.putString("SERVICE_CODE", info.getService_code());
		String service_type = info.getService_type();
		data.putString("SERVICE_TYPE", service_type);
		data.putString("CATEGORY_CODE", info.getCategory_code());
		data.putString("SERVICE_NAME", info.getService_name());
		data.putString("SERVER_CODE", info.getServer_code());
		data.putString("EXTEND_SERVICE_NAME", info.getExtend_service_name());
		data.putServiceData("REQ_STRU", getStructure(info.getReq_stru()));
		data.putServiceData("RESP_STRU", getStructure(info.getResp_stru()));
		data.putServiceData("ERR_STRU", getStructure(info.getErr_stru()));
		data.putString("VERNO", info.getVerno());
		data.putString("IS_PUBLISHED", info.getIs_published());
		//组合服务
		if("2".equals(service_type)){
			data.putString("CONTENT", groupSvcChartDaoService.getChartContent(service_code));
		}
		return data;
	}

	private static void putChannelBasicPar(ServiceData data, ChannelInfo info){
		data.putString("CHANNEL_CODE", info.getChannel_code());
		data.putString("CHANNEL_NAME", info.getChannel_name());
		data.putString("GET_SVC_EXP", info.getGet_svc_exp());
		data.putString("PUT_SVC_EXP", info.getPut_svc_exp());
		data.putString("MSG_CLASS", info.getMsg_class());
		data.putString("VERNO", info.getVerno());
		data.putString("BIZ_CHANNEL_EXP", info.getBiz_channel_exp());
	}
	
	private static void putServerBasicPar(ServiceData data, ServerInfo info){
		data.putString("SERVER_CODE", info.getServer_code());
		data.putString("SERVER_NAME", info.getServer_name());
		data.putString("SUCCESS_EXP", info.getSuccess_exp());
		data.putString("PUT_SVC_EXP", info.getPut_svc_exp());
		data.putString("MSG_CLASS", info.getMsg_class());
		data.putString("SERVER_ACTOR_CLASS", info.getServer_actor_class());
		data.putString("VERNO", info.getVerno());
	}

  	private static ServiceData putComm(String comm_id){
  		return getCommData(commDaoSevice.findOneComm(comm_id));
  	}
  	
  	private static ServiceData getStructure(String structure_id){
  		return StringUtil.isEmpty(structure_id) ? null : getStructureServiceData(structureDaoSevice.get(structure_id));
  	}
  	
  	private static ServiceData getMap(String map_id){
  		return StringUtil.isEmpty(map_id) ? null : getMapData(mapDaoService.getAllMappingById(map_id));
  	}
  	
	private static ServiceData getCommData(CommInfo info){
		ServiceData commData = new ServiceData();
		commData.putString("CATEGORY", info.getCategory());
		commData.putString("CCODE", info.getCcode());
		commData.putString("COMM_TYPE", info.getComm_type());
		commData.putString("CONNECTION_MODE", info.getConnection_mode());
		commData.putString("PACKET_TYPE", info.getPacket_type());
		commData.putString("BIND_ADDRESS", info.getBind_address());
		commData.putString("REMOTE_ADDRESS", info.getRemote_address());
		commData.putString("MATCH_REQUEST", info.getMatch_request());
		commData.putString("MATCH_PARTNER", info.getMatch_partner());
		commData.putString("MAX_ACTIVE", info.getMax_active());
		commData.putString("MIN_ACTIVE", info.getMin_active());
		commData.putString("MAX_CONN_LIVE_TIME", info.getMax_conn_live_time());
		commData.putString("MAX_CONN_IDLE_TIME", info.getMax_conn_idle_time());
		commData.putString("MAX_CONN_INACTIVE_TIME", info.getMax_conn_inactive_time());
		commData.putString("CONN_CHECK_INTERVAL", info.getConn_check_interval());
		commData.putString("WORKER", info.getWorker());
		commData.putString("TIMEOUT", info.getTimeout());
		commData.putString("ROUND_ROBIN", info.getRound_robin());
		commData.putString("MAX_PARTNER_WAIT_TIME", info.getMax_partner_wait_time());
		commData.putString("MSG_CLASS", info.getMsg_class());
		commData.putString("IMPL_CLASS", info.getImpl_class());
		commData.putString("TRUST_HOSTS", info.getTrust_hosts());
		commData.putString("VERNO", info.getVerno());
		return commData;
	}

	private static ServiceData getStructureServiceData(StructureInfo info){
		ServiceData structureData = new ServiceData();
		structureData.putString("SERVICE_CODE", info.getService_code());
		structureData.putString("STRUCTURE_TYPE", info.getStructure_type());
		structureData.putString("STRUCTURE_CATEGORY", info.getStructure_category());
		structureData.putServiceData("STRUCTURE_CONTENT", getDataServiceData(info.getStructure_content()));
		structureData.putString("VERNO", info.getVerno());
		return structureData;
	}

	private static ServiceData getMapData(MappingInfo info){
		ServiceData data = new ServiceData();
		data.putString("MAPPING_CATEGORY", info.getMapping_category());
		data.putString("REF_CODE", info.getRef_code());
		data.putString("MAPPING_CODE", info.getMapping_code());
		data.putString("MAPPING_NAME", info.getMapping_name());
		data.putString("MAPPING_ATTR", info.getMapping_attr());
		data.putString("MAPPING_SCRIPT", info.getMapping_script());
		data.putString("MAPPING_EXPR", info.getMapping_expr());
		data.putString("CTX_DATAS", info.getCtx_datas());
		data.putString("IN_DATAS", info.getIn_datas());
		data.putString("OUT_DATAS", info.getOut_datas());
		data.putString("VERNO", info.getVerno());
		return data;
	}
	
	private static ServiceData getDataServiceData(String sid){
		String type = "";
		List<SaveDatasInfo> savedatasInfoList = saveDatasDao.getSaveDatas(sid);
		for (SaveDatasInfo saveDatasInfo : savedatasInfoList) {
			type = saveDatasInfo.getStype();
			if(!"".equals(type)){
				break;
			}
		}
		if("".equals(type)){
			throw new SystemException("SYS_DBCOMPARE_DATA_FIELD_ERROR");
		}
		ServiceData datasData = new ServiceData();
		datasData.putString("SDATAS", saveDatasDaoSevice.getOneDataById(sid));
		datasData.putString("STYPE", type);
		
		return datasData;
	}
	
//	private void init(){
//		Controller.getInstance().getInjector().inject(this);
//	}
//	
//	static {
//		new ExportDatasFromTable().init();
//	}
}
