package compare;

import com.wk.eai.webide.dao.ChannelDaoService;
import com.wk.eai.webide.dao.GroupSvcChartDaoService;
import com.wk.eai.webide.dao.MappingDaoService;
import com.wk.eai.webide.dao.ServerDaoService;
import com.wk.eai.webide.dao.ServiceDaoService;
import com.wk.eai.webide.dao.TranChannelPackageDaoService;
import com.wk.eai.webide.dao.TranServerPackageDaoService;
import com.wk.eai.webide.info.ChannelInfo;
import com.wk.eai.webide.info.CommInfo;
import com.wk.eai.webide.info.MappingInfo;
import com.wk.eai.webide.info.ServerInfo;
import com.wk.eai.webide.info.ServiceInfo;
import com.wk.eai.webide.info.TranChannelPackageInfo;
import com.wk.eai.webide.info.TranServerPackageInfo;
import com.wk.lang.Inject;
import com.wk.sdo.ServiceData;
import com.wk.util.StringUtil;

/**
 * @description ���ļ��ж�ȡ���ݵ��뵽���ݿ����
 * @author raoliang
 * @version 2014��10��30�� ����7:51:06
 */
public class ImportDatasToDB {
	@Inject static TranServerPackageDaoService tranServerPackageDaoService;
	@Inject static MappingDaoService mappingDaoService;
	@Inject static ChannelDaoService channelDaoService;
	@Inject static TranChannelPackageDaoService tranChannelPackageDaoService;
	@Inject static ServerDaoService serverDaoService;
	@Inject static ServiceDaoService serviceDaoService;
	@Inject static GroupSvcChartDaoService groupSvcChartDaoService;
	
	/**
	* @description �����ݿ����һ��EndPoint
	* @author raoliang
	* @version 2014��11��2�� ����2:38:40
	*/
	public int insertEndPoint(ServiceData endPointData){
		ChannelInfo channelInfo = getChannelInfoFromserviceData(endPointData);
		CommInfo commInfo = getCommInfoFromServiceData(endPointData);
		return channelDaoService.insertOneChannel(channelInfo, commInfo);
	}
	
	/**
	* @description �����ݿ����ָ��EndPoint��һ����������
	* @author raoliang
	* @version 2014��11��2�� ����2:37:21
	*/
	public int insertTranEndPoint(ServiceData tranEndPointData){
		TranChannelPackageInfo info = getTranChannelPackageInfoFromserviceData(tranEndPointData);
		return tranChannelPackageDaoService.insertOneTranAll(info);
	}
	
	/**
	* @description ���������е�һ��EndPoint
	* @author raoliang
	* @version 2014��11��3�� ����10:49:04
	* @return
	*/
	public int updateEndPoint(ServiceData endPointData){
		ChannelInfo channelInfo = getChannelInfoFromserviceData(endPointData);
		CommInfo commInfo = getCommInfoFromServiceData(endPointData);
		return channelDaoService.updateOneChannelAll(channelInfo, commInfo);
	}
	
	/**
	* @description �������ݿ���һ��EndPoint�Ĺ�������
	* @author raoliang
	* @version 2014��11��3�� ����10:53:56
	* @return
	*/
	public int updateTranEndPoint(ServiceData tranEndPointData){
		TranChannelPackageInfo tranEndPointInfo = getTranChannelPackageInfoFromserviceData(tranEndPointData);
		return tranChannelPackageDaoService.updateOneTranAll(tranEndPointInfo);
	}
	
	/**
	* @description �����ݿ��в���һ������ϵͳServer
	* @author raoliang
	* @version 2014��11��3�� ����2:33:14
	* @return
	*/
	public int insertServer(ServiceData serverData){
		ServerInfo serverInfo = getServerInfoFromServiceData(serverData);
		CommInfo commInfo = getCommInfoFromServiceData(serverData);
		return serverDaoService.insertOneServerAll(serverInfo, commInfo);
	}
	
	/**
	* @description ������·�в���һ������ϵͳ�Ĺ�������
	* @author raoliang
	* @version 2014��11��3�� ����2:34:48
	* @return
	*/
	public int insertTranServer(ServiceData tranServerData){
		TranServerPackageInfo tranServerInfo = getTranServerPackageInfoFromServiceData(tranServerData);
		return tranServerPackageDaoService.insertOneTranAll(tranServerInfo);
	}
	
	/**
	* @description �������ݿ��еķ���ϵͳServer
	* @author raoliang
	* @version 2014��11��3�� ����2:37:06
	* @return
	*/
	public int updateServer(ServiceData serverData){
		ServerInfo serverInfo = getServerInfoFromServiceData(serverData);
		CommInfo commInfo = getCommInfoFromServiceData(serverData);
		return serverDaoService.updateOneServerAll(serverInfo, commInfo);
	}
	
	/**
	* @description �������ݿ��з���ϵͳ��ĳ����������
	* @author raoliang
	* @version 2014��11��3�� ����2:37:50
	* @return
	*/
	public int updateTranServer(ServiceData tranServerData){
		TranServerPackageInfo tranServerInfo = getTranServerPackageInfoFromServiceData(tranServerData);
		return tranServerPackageDaoService.updateOneTranAll(tranServerInfo);
	}
	
	/**
	* @description �����ݿ����һ����������
	* @param serviceData ����Ԫ����
	* @return �ɹ���������
	* @author raoliang
	* @version 2014��11��4�� ����8:00:14
	*/
	public int insertOneService(ServiceData serviceData){
		ServiceInfo info = getServiceInfoFromServiceData(serviceData);
		//�������Ϸ���
		if("2".equals(info.getService_type())){
			groupSvcChartDaoService.insertChartContent(serviceData.getString("CONTENT"), info.getService_code());
		}
		return serviceDaoService.insertOneServiceAll(info);
	}
	
	private ChannelInfo getChannelInfoFromserviceData(ServiceData data){
		ChannelInfo info = new ChannelInfo();
		info.setChannel_code(data.getString("CHANNEL_CODE"));
		info.setChannel_name(data.getString("CHANNEL_NAME"));
		info.setGet_svc_exp(data.getString("GET_SVC_EXP"));
		info.setPut_svc_exp(data.getString("PUT_SVC_EXP"));
		info.setMsg_class(data.getString("MSG_CLASS"));
		info.setBiz_channel_exp(data.getString("BIZ_CHANNEL_EXP"));
		//�ӿ�����
		info.setReq_package_config(getReqConfigStr(data));
		info.setResp_package_config(getRespConfigStr(data));
		info.setErr_package_config(getErrConfigStr(data));
		//ӳ��
		info.setIn_mapping(getMappingId(data.getServiceData("IN_MAPPING")));
		info.setOut_mapping(getMappingId(data.getServiceData("OUT_MAPPING")));
		info.setError_mapping(getMappingId(data.getServiceData("ERROR_MAPPING")));
		
		return info;
	}
	
	private TranChannelPackageInfo getTranChannelPackageInfoFromserviceData(ServiceData tranEndPointData){
		TranChannelPackageInfo tranInfo = new TranChannelPackageInfo();
		tranInfo.setChannel_code(tranEndPointData.getString("CHANNEL_CODE"));
		tranInfo.setTran_code(tranEndPointData.getString("TRAN_CODE"));
		tranInfo.setTran_name(tranEndPointData.getString("TRAN_NAME"));
		//�ӿ�����
		tranInfo.setReq_package_config(getReqConfigStr(tranEndPointData));
		tranInfo.setResp_package_config(getRespConfigStr(tranEndPointData));
		//ӳ��
		tranInfo.setIn_mapping(getMappingId(tranEndPointData.getServiceData("IN_MAPPING")));
		tranInfo.setOut_mapping(getMappingId(tranEndPointData.getServiceData("OUT_MAPPING")));
		tranInfo.setError_mapping(getMappingId(tranEndPointData.getServiceData("ERROR_MAPPING")));
		
		return tranInfo;
	}
	
	private ServerInfo getServerInfoFromServiceData(ServiceData data){
		ServerInfo info = new ServerInfo();
		info.setServer_code(data.getString("SERVER_CODE"));
		info.setServer_name(data.getString("SERVER_NAME"));
		info.setSuccess_exp(data.getString("SUCCESS_EXP"));
		info.setPut_svc_exp(data.getString("PUT_SVC_EXP"));
		info.setMsg_class(data.getString("MSG_CLASS"));
		info.setServer_actor_class(data.getString("SERVER_ACTOR_CLASS"));
		//�ӿ�����
		info.setReq_package_config(getReqConfigStr(data));
		info.setResp_package_config(getRespConfigStr(data));
		info.setErr_package_config(getErrConfigStr(data));
		//ӳ��
		info.setIn_mapping(getMappingId(data.getServiceData("IN_MAPPING")));
		info.setOut_mapping(getMappingId(data.getServiceData("OUT_MAPPING")));
		info.setError_mapping(getMappingId(data.getServiceData("ERROR_MAPPING")));
		return info;
	}
	
	private TranServerPackageInfo getTranServerPackageInfoFromServiceData(ServiceData tranServerData){
		TranServerPackageInfo tranInfo = new TranServerPackageInfo();
		tranInfo.setServer_code(tranServerData.getString("SERVER_CODE"));
		tranInfo.setTran_code(tranServerData.getString("TRAN_CODE"));
		tranInfo.setTran_name(tranServerData.getString("TRAN_NAME"));
		//�ӿ�����
		tranInfo.setReq_package_config(getReqConfigStr(tranServerData));
		tranInfo.setResp_package_config(getRespConfigStr(tranServerData));
		//ӳ��
		tranInfo.setIn_mapping(getMappingId(tranServerData.getServiceData("IN_MAPPING")));
		tranInfo.setOut_mapping(getMappingId(tranServerData.getServiceData("OUT_MAPPING")));
		tranInfo.setError_mapping(getMappingId(tranServerData.getServiceData("ERROR_MAPPING")));
		return tranInfo;
	}
	
	private ServiceInfo getServiceInfoFromServiceData(ServiceData serviceData){
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setService_code(serviceData.getString("SERVICE_CODE"));
		serviceInfo.setService_type(serviceData.getString("SERVICE_TYPE"));
		serviceInfo.setCategory_code(serviceData.getString("CATEGORY_CODE"));
		serviceInfo.setService_name(serviceData.getString("SERVICE_NAME"));
		serviceInfo.setServer_code(serviceData.getString("SERVER_CODE"));
		serviceInfo.setExtend_service_name(StringUtil.isEmpty(serviceData.getString("EXTEND_SERVICE_NAME")) ? "" : serviceData.getString("EXTEND_SERVICE_NAME"));
		//�ӿ�����
		serviceInfo.setReq_stru(getServiceReqConfigStr(serviceData));
		serviceInfo.setResp_stru(getServiceRespConfigStr(serviceData));
		serviceInfo.setErr_stru(getServiceErrConfigStr(serviceData));
		//ǿ�Ʒ�������
		serviceInfo.setIs_published("1");
		return serviceInfo;
	}
	
	private CommInfo getCommInfoFromServiceData(ServiceData data){
		ServiceData commData = data.getServiceData("COMM_ID");
		CommInfo info = new CommInfo();
		info.setCategory(commData.getString("CATEGORY"));
		info.setCcode(commData.getString("CCODE"));
		info.setComm_type(commData.getString("COMM_TYPE"));
		info.setConnection_mode(commData.getString("CONNECTION_MODE"));
		info.setPacket_type(commData.getString("PACKET_TYPE"));
		info.setBind_address(commData.getString("BIND_ADDRESS"));
		info.setRemote_address(commData.getString("REMOTE_ADDRESS"));
		info.setMatch_request(commData.getString("MATCH_REQUEST"));
		info.setMatch_partner(commData.getString("MATCH_PARTNER"));
		info.setMax_active(commData.getString("MAX_ACTIVE"));
		info.setMin_active(commData.getString("MIN_ACTIVE"));
		info.setMax_conn_live_time(commData.getString("MAX_CONN_LIVE_TIME"));
		info.setMax_conn_idle_time(commData.getString("MAX_CONN_IDLE_TIME"));
		info.setMax_conn_inactive_time(commData.getString("MAX_CONN_INACTIVE_TIME"));
		info.setConn_check_interval(commData.getString("CONN_CHECK_INTERVAL"));
		info.setWorker(commData.getString("WORKER"));
		info.setTimeout(commData.getString("TIMEOUT"));
		info.setRound_robin(commData.getString("ROUND_ROBIN"));
		info.setMax_partner_wait_time(commData.getString("MAX_PARTNER_WAIT_TIME"));
		info.setMsg_class(commData.getString("MSG_CLASS"));
		info.setImpl_class(commData.getString("IMPL_CLASS"));
		info.setTrust_hosts(commData.getString("TRUST_HOSTS"));
		return info;
	}
	
	private String getMappingId(ServiceData mapData){
		return (mapData == null || mapData.size() == 0) ? "" : mappingDaoService.insertOneMappingInfo(serviceDataToInMappingInfo(mapData));
	}
	
	private MappingInfo serviceDataToInMappingInfo(ServiceData data){
		return packMappingInfo(data);
	}
	
	private MappingInfo packMappingInfo(ServiceData mapData){
		MappingInfo info = new MappingInfo();
		info.setMapping_category(mapData.getString("MAPPING_CATEGORY"));
		info.setRef_code(mapData.getString("REF_CODE"));
		info.setMapping_code(mapData.getString("MAPPING_CODE"));
		info.setMapping_name(mapData.getString("MAPPING_NAME"));
		info.setMapping_attr(mapData.getString("MAPPING_ATTR"));
		info.setMapping_script(mapData.getString("MAPPING_SCRIPT"));
		info.setMapping_expr(mapData.getString("MAPPING_EXPR"));
		info.setCtx_datas(mapData.getString("CTX_DATAS"));
		info.setIn_datas(mapData.getString("IN_DATAS"));
		info.setOut_datas(mapData.getString("OUT_DATAS"));
		return info;
	}
	
	/**
	* @description �õ�����ӿ�����
	* @author raoliang
	* @version 2014��10��31�� ����3:31:53
	*/
	private String getReqConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("REQ_PACKAGE_CONFIG"));
	}
	
	/**
	* @description �õ���Ӧ�ӿ�����
	* @author raoliang
	* @version 2014��10��31�� ����3:34:41
	*/
	private String getRespConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("RESP_PACKAGE_CONFIG"));
	}
	
	/**
	* @description �õ�����ӿ�����
	* @author raoliang
	* @version 2014��10��31�� ����3:34:43
	*/
	private String getErrConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("ERR_PACKAGE_CONFIG"));
	}
	
	/**
	 * @description �õ���������ӿ�����
	 * @author raoliang
	 * @version 2014��10��31�� ����3:31:53
	 */
	private String getServiceReqConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("REQ_STRU"));
	}
	
	/**
	 * @description �õ�������Ӧ�ӿ�����
	 * @author raoliang
	 * @version 2014��10��31�� ����3:34:41
	 */
	private String getServiceRespConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("RESP_STRU"));
	}
	
	/**
	 * @description �õ��������ӿ�����
	 * @author raoliang
	 * @version 2014��10��31�� ����3:34:43
	 */
	private String getServiceErrConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("ERR_STRU"));
	}
	
	private String getConfigSaveData(ServiceData data){
		return (data == null || data.size() == 0) ? "" : data.getServiceData("STRUCTURE_CONTENT").getString("SDATAS");
	}
	
}
