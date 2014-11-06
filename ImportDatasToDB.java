package compare;

import com.wk.eai.webide.dao.ChannelDaoService;
import com.wk.eai.webide.dao.GroupSvcChartDaoService;
import com.wk.eai.webide.dao.InstanceDaoService;
import com.wk.eai.webide.dao.MachineDaoService;
import com.wk.eai.webide.dao.MappingDaoService;
import com.wk.eai.webide.dao.ProcessInstanceDaoService;
import com.wk.eai.webide.dao.ServerDaoService;
import com.wk.eai.webide.dao.ServiceDaoService;
import com.wk.eai.webide.dao.TranChannelPackageDaoService;
import com.wk.eai.webide.dao.TranServerPackageDaoService;
import com.wk.eai.webide.info.ChannelInfo;
import com.wk.eai.webide.info.CommInfo;
import com.wk.eai.webide.info.InstanceInfo;
import com.wk.eai.webide.info.MachineInfo;
import com.wk.eai.webide.info.MappingInfo;
import com.wk.eai.webide.info.ProcessInstanceInfo;
import com.wk.eai.webide.info.ServerInfo;
import com.wk.eai.webide.info.ServiceInfo;
import com.wk.eai.webide.info.TranChannelPackageInfo;
import com.wk.eai.webide.info.TranServerPackageInfo;
import com.wk.lang.Inject;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;
import com.wk.sdo.ServiceData;
import com.wk.util.StringUtil;

/**
 * @description 从文件中读取数据导入到数据库表中
 * @author raoliang
 * @version 2014年10月30日 下午7:51:06
 */
public class ImportDatasToDB {
	private final Log logger = LogFactory.getLog("dbcompare");
	@Inject static TranServerPackageDaoService tranServerPackageDaoService;
	@Inject static MappingDaoService mappingDaoService;
	@Inject static ChannelDaoService channelDaoService;
	@Inject static TranChannelPackageDaoService tranChannelPackageDaoService;
	@Inject static ServerDaoService serverDaoService;
	@Inject static ServiceDaoService serviceDaoService;
	@Inject static GroupSvcChartDaoService groupSvcChartDaoService;
	@Inject static ProcessInstanceDaoService processInstanceDaoService;
	@Inject static MachineDaoService machineDaoService;
	@Inject static InstanceDaoService instanceDaoService;
	
	/**
	* @description 向数据库插入一个EndPoint
	* @author raoliang
	* @version 2014年11月2日 下午2:38:40
	*/
	public int insertEndPoint(ServiceData endPointData){
		ChannelInfo channelInfo = getChannelInfo(endPointData);
		CommInfo commInfo = getCommInfo(endPointData);
		return channelDaoService.insertOneChannel(channelInfo, commInfo);
	}
	
	/**
	* @description 向数据库插入指定EndPoint的一个关联交易
	* @author raoliang
	* @version 2014年11月2日 下午2:37:21
	*/
	public int insertTranEndPoint(ServiceData tranEndPointData){
		TranChannelPackageInfo info = getTranChannelPackageInfo(tranEndPointData);
		return tranChannelPackageDaoService.insertOneTranAll(info);
	}
	
	/**
	* @description 更新数据中的一个EndPoint
	* @author raoliang
	* @version 2014年11月3日 上午10:49:04
	* @return
	*/
	public int updateEndPoint(ServiceData endPointData){
		ChannelInfo channelInfo = getChannelInfo(endPointData);
		CommInfo commInfo = getCommInfo(endPointData);
		return channelDaoService.updateOneChannelAll(channelInfo, commInfo);
	}
	
	/**
	* @description 更新数据库中一个EndPoint的关联交易
	* @author raoliang
	* @version 2014年11月3日 上午10:53:56
	* @return
	*/
	public int updateTranEndPoint(ServiceData tranEndPointData){
		TranChannelPackageInfo tranEndPointInfo = getTranChannelPackageInfo(tranEndPointData);
		return tranChannelPackageDaoService.updateOneTranAll(tranEndPointInfo);
	}
	
	/**
	* @description 向数据库中插入一个服务系统Server
	* @author raoliang
	* @version 2014年11月3日 下午2:33:14
	* @return
	*/
	public int insertServer(ServiceData serverData){
		ServerInfo serverInfo = getServerInfo(serverData);
		CommInfo commInfo = getCommInfo(serverData);
		return serverDaoService.insertOneServerAll(serverInfo, commInfo);
	}
	
	/**
	* @description 向数据路中插入一个服务系统的关联交易
	* @author raoliang
	* @version 2014年11月3日 下午2:34:48
	* @return
	*/
	public int insertTranServer(ServiceData tranServerData){
		TranServerPackageInfo tranServerInfo = getTranServerPackageInfo(tranServerData);
		return tranServerPackageDaoService.insertOneTranAll(tranServerInfo);
	}
	
	/**
	* @description 更新数据库中的服务系统Server
	* @author raoliang
	* @version 2014年11月3日 下午2:37:06
	* @return
	*/
	public int updateServer(ServiceData serverData){
		ServerInfo serverInfo = getServerInfo(serverData);
		CommInfo commInfo = getCommInfo(serverData);
		return serverDaoService.updateOneServerAll(serverInfo, commInfo);
	}
	
	/**
	* @description 更新数据库中服务系统的某个关联交易
	* @author raoliang
	* @version 2014年11月3日 下午2:37:50
	* @return
	*/
	public int updateTranServer(ServiceData tranServerData){
		TranServerPackageInfo tranServerInfo = getTranServerPackageInfo(tranServerData);
		return tranServerPackageDaoService.updateOneTranAll(tranServerInfo);
	}
	
	/**
	* @description 向数据库插入一条服务数据
	* @param serviceData 服务单元数据
	* @return 成功插入条数
	* @author raoliang
	* @version 2014年11月4日 下午8:00:14
	*/
	public int insertOneService(ServiceData serviceData){
		ServiceInfo info = getServiceInfo(serviceData);
		//如果是组合服务
		if("2".equals(info.getService_type())){
			groupSvcChartDaoService.insertChartContent(serviceData.getString("CONTENT"), info.getService_code());
			logger.info("成功插入一条组合服务流程图,服务名称:{}", info.getService_code());
		}
		return serviceDaoService.insertOneServiceAll(info);
	}
	
	/**
	* @description 插入服务器信息
	* @param machineData 服务器单元数据
	* @return 成功插入条数
	* @author raoliang
	* @version 2014年11月6日 下午1:08:02
	*/
	public int insertOneMachine(ServiceData machineData){
		int count = 0;
		MachineInfo info = getMachineInfo(machineData);
		count = machineDaoService.saveOneMachine(info);
		//插入服务器下的进程列表
		if(machineData.size() > 5){
			insertAllInstance(machineData.getServiceData("INSTANCE"));
		}
		//如果此服务器下部署了进程
		if(machineData.size() == 6){
			insertAllProcessInstance(machineData.getServiceData("PROCESSINSTANCE"));
		}
		//TODO:
//		logger.info("插入服务器条数:{}", count);
		return count;
	}
	
	/**
	* @description 插入服务器下的进程列表
	* @param instanceData 进程列表数据源
	* @return 成功插入条数
	* @author raoliang
	* @version 2014年11月6日 下午3:29:07
	*/
	public int insertAllInstance(ServiceData instanceData){
		int count = 0;
		String[] keys = instanceData.getKeys();
		for (String key : keys) {
			ServiceData data = instanceData.getServiceData(key);
			//得到单个进程列表的Info
			InstanceInfo info = getInstanceInfo(data);
			//插入单个进程列表
			count += instanceDaoService.saveOneInstance(info);
		}
		logger.info("成功插入进程列表{}个", count);
		return count;
	}
	
	/**
	* @description 插入某个服务器下部署的所有进程
	* @param instanceData 进程列表数据源
	* @return 成功插入条数
	* @author raoliang
	* @version 2014年11月6日 上午11:38:43
	*/
	public int insertAllProcessInstance(ServiceData datas){
		int count = 0;
		String[] keys = datas.getKeys();
		String keysStr = "";
		for (String key : keys) {
			keysStr += key+" ";
			ServiceData data = datas.getServiceData(key);
			//得到单个进程的Info
			ProcessInstanceInfo info = getProcessInstanceInfo(data);
			//插入单个进程
			count += processInstanceDaoService.insertOneRecord(info);
		}
		logger.info("成功插入部署进程{}个,部署渠道有:{}", count, keysStr);
		return count;
	}
	
	private ChannelInfo getChannelInfo(ServiceData data){
		ChannelInfo info = new ChannelInfo();
		info.setChannel_code(data.getString("CHANNEL_CODE"));
		info.setChannel_name(data.getString("CHANNEL_NAME"));
		info.setGet_svc_exp(data.getString("GET_SVC_EXP"));
		info.setPut_svc_exp(data.getString("PUT_SVC_EXP"));
		info.setMsg_class(data.getString("MSG_CLASS"));
		info.setBiz_channel_exp(data.getString("BIZ_CHANNEL_EXP"));
		//接口配置
		info.setReq_package_config(getReqConfigStr(data));
		info.setResp_package_config(getRespConfigStr(data));
		info.setErr_package_config(getErrConfigStr(data));
		//映射
		info.setIn_mapping(getMappingId(data.getServiceData("IN_MAPPING")));
		info.setOut_mapping(getMappingId(data.getServiceData("OUT_MAPPING")));
		info.setError_mapping(getMappingId(data.getServiceData("ERROR_MAPPING")));
		
		return info;
	}
	
	private TranChannelPackageInfo getTranChannelPackageInfo(ServiceData tranEndPointData){
		TranChannelPackageInfo tranInfo = new TranChannelPackageInfo();
		tranInfo.setChannel_code(tranEndPointData.getString("CHANNEL_CODE"));
		tranInfo.setTran_code(tranEndPointData.getString("TRAN_CODE"));
		tranInfo.setTran_name(tranEndPointData.getString("TRAN_NAME"));
		//接口配置
		tranInfo.setReq_package_config(getReqConfigStr(tranEndPointData));
		tranInfo.setResp_package_config(getRespConfigStr(tranEndPointData));
		//映射
		tranInfo.setIn_mapping(getMappingId(tranEndPointData.getServiceData("IN_MAPPING")));
		tranInfo.setOut_mapping(getMappingId(tranEndPointData.getServiceData("OUT_MAPPING")));
		tranInfo.setError_mapping(getMappingId(tranEndPointData.getServiceData("ERROR_MAPPING")));
		
		return tranInfo;
	}
	
	private ServerInfo getServerInfo(ServiceData data){
		ServerInfo info = new ServerInfo();
		info.setServer_code(data.getString("SERVER_CODE"));
		info.setServer_name(data.getString("SERVER_NAME"));
		info.setSuccess_exp(data.getString("SUCCESS_EXP"));
		info.setPut_svc_exp(data.getString("PUT_SVC_EXP"));
		info.setMsg_class(data.getString("MSG_CLASS"));
		info.setServer_actor_class(data.getString("SERVER_ACTOR_CLASS"));
		//接口配置
		info.setReq_package_config(getReqConfigStr(data));
		info.setResp_package_config(getRespConfigStr(data));
		info.setErr_package_config(getErrConfigStr(data));
		//映射
		info.setIn_mapping(getMappingId(data.getServiceData("IN_MAPPING")));
		info.setOut_mapping(getMappingId(data.getServiceData("OUT_MAPPING")));
		info.setError_mapping(getMappingId(data.getServiceData("ERROR_MAPPING")));
		return info;
	}
	
	private TranServerPackageInfo getTranServerPackageInfo(ServiceData tranServerData){
		TranServerPackageInfo tranInfo = new TranServerPackageInfo();
		tranInfo.setServer_code(tranServerData.getString("SERVER_CODE"));
		tranInfo.setTran_code(tranServerData.getString("TRAN_CODE"));
		tranInfo.setTran_name(tranServerData.getString("TRAN_NAME"));
		//接口配置
		tranInfo.setReq_package_config(getReqConfigStr(tranServerData));
		tranInfo.setResp_package_config(getRespConfigStr(tranServerData));
		//映射
		tranInfo.setIn_mapping(getMappingId(tranServerData.getServiceData("IN_MAPPING")));
		tranInfo.setOut_mapping(getMappingId(tranServerData.getServiceData("OUT_MAPPING")));
		tranInfo.setError_mapping(getMappingId(tranServerData.getServiceData("ERROR_MAPPING")));
		return tranInfo;
	}
	
	private ServiceInfo getServiceInfo(ServiceData serviceData){
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setService_code(serviceData.getString("SERVICE_CODE"));
		serviceInfo.setService_type(serviceData.getString("SERVICE_TYPE"));
		serviceInfo.setCategory_code(serviceData.getString("CATEGORY_CODE"));
		serviceInfo.setService_name(serviceData.getString("SERVICE_NAME"));
		serviceInfo.setServer_code(serviceData.getString("SERVER_CODE"));
		serviceInfo.setExtend_service_name(StringUtil.isEmpty(serviceData.getString("EXTEND_SERVICE_NAME")) ? "" : serviceData.getString("EXTEND_SERVICE_NAME"));
		//接口配置
		serviceInfo.setReq_stru(getServiceReqConfigStr(serviceData));
		serviceInfo.setResp_stru(getServiceRespConfigStr(serviceData));
		serviceInfo.setErr_stru(getServiceErrConfigStr(serviceData));
		//强制发布服务
		serviceInfo.setIs_published("1");
		return serviceInfo;
	}
	
	private MachineInfo getMachineInfo(ServiceData machineData){
		MachineInfo info = new MachineInfo();
		info.setMachine_code(machineData.getString("MACHINE_CODE"));
		info.setMachine_ip(machineData.getString("MACHINE_IP"));
		info.setMachine_name(machineData.getString("MACHINE_NAME"));
		return info;
	}
	
	private InstanceInfo getInstanceInfo(ServiceData instanceData){
		InstanceInfo info = new InstanceInfo();
		info.setMachine_code(instanceData.getString("MACHINE_CODE"));
		info.setSkeyc(instanceData.getString("SKEYC"));
		info.setSkeyd(instanceData.getString("SKEYD"));
		return info;
	}
	
	private ProcessInstanceInfo getProcessInstanceInfo(ServiceData processInstanceData){
		ProcessInstanceInfo info = new ProcessInstanceInfo();
		info.setSkeyc(processInstanceData.getString("SKEYC"));
		info.setChannel_code(processInstanceData.getString("CHANNEL_CODE"));
		info.setBind_address(processInstanceData.getString("BIND_ADDRESS"));
		info.setRemote_address(processInstanceData.getString("REMOTE_ADDRESS"));
		return info;
	}
	
	private CommInfo getCommInfo(ServiceData data){
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
	* @description 得到请求接口数据
	* @author raoliang
	* @version 2014年10月31日 下午3:31:53
	*/
	private String getReqConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("REQ_PACKAGE_CONFIG"));
	}
	
	/**
	* @description 得到响应接口数据
	* @author raoliang
	* @version 2014年10月31日 下午3:34:41
	*/
	private String getRespConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("RESP_PACKAGE_CONFIG"));
	}
	
	/**
	* @description 得到错误接口数据
	* @author raoliang
	* @version 2014年10月31日 下午3:34:43
	*/
	private String getErrConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("ERR_PACKAGE_CONFIG"));
	}
	
	/**
	 * @description 得到服务请求接口数据
	 * @author raoliang
	 * @version 2014年10月31日 下午3:31:53
	 */
	private String getServiceReqConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("REQ_STRU"));
	}
	
	/**
	 * @description 得到服务响应接口数据
	 * @author raoliang
	 * @version 2014年10月31日 下午3:34:41
	 */
	private String getServiceRespConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("RESP_STRU"));
	}
	
	/**
	 * @description 得到服务错误接口数据
	 * @author raoliang
	 * @version 2014年10月31日 下午3:34:43
	 */
	private String getServiceErrConfigStr(ServiceData data){
		return getConfigSaveData(data.getServiceData("ERR_STRU"));
	}
	
	private String getConfigSaveData(ServiceData data){
		return (data == null || data.size() == 0) ? "" : data.getServiceData("STRUCTURE_CONTENT").getString("SDATAS");
	}
	
}
