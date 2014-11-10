package compare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wk.eai.webide.dao.ChannelDaoService;
import com.wk.eai.webide.dao.CommDaoService;
import com.wk.eai.webide.dao.DictDaoService;
import com.wk.eai.webide.dao.DictDetailDao;
import com.wk.eai.webide.dao.GroupSvcChartDaoService;
import com.wk.eai.webide.dao.InstanceDaoService;
import com.wk.eai.webide.dao.MachineDaoService;
import com.wk.eai.webide.dao.MappingDaoService;
import com.wk.eai.webide.dao.ModeDaoService;
import com.wk.eai.webide.dao.ModeParamDaoService;
import com.wk.eai.webide.dao.ProcessInstanceDaoService;
import com.wk.eai.webide.dao.SaveDatasDao;
import com.wk.eai.webide.dao.SaveDatasDaoService;
import com.wk.eai.webide.dao.ServerDaoService;
import com.wk.eai.webide.dao.ServiceDaoService;
import com.wk.eai.webide.dao.StructureDaoService;
import com.wk.eai.webide.dao.TranChannelPackageDaoService;
import com.wk.eai.webide.dao.TranServerPackageDaoService;
import com.wk.eai.webide.info.ChannelInfo;
import com.wk.eai.webide.info.CommInfo;
import com.wk.eai.webide.info.DictDetailInfo;
import com.wk.eai.webide.info.DictInfo;
import com.wk.eai.webide.info.InstanceInfo;
import com.wk.eai.webide.info.MachineInfo;
import com.wk.eai.webide.info.MappingInfo;
import com.wk.eai.webide.info.ModeInfo;
import com.wk.eai.webide.info.ModeParamInfo;
import com.wk.eai.webide.info.ProcessInstanceInfo;
import com.wk.eai.webide.info.SaveDatasInfo;
import com.wk.eai.webide.info.ServerInfo;
import com.wk.eai.webide.info.ServiceInfo;
import com.wk.eai.webide.info.StructureInfo;
import com.wk.eai.webide.info.TranChannelPackageInfo;
import com.wk.eai.webide.info.TranServerPackageInfo;
import com.wk.lang.Inject;
import com.wk.lang.SystemException;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;
import com.wk.sdo.ServiceData;
import com.wk.util.StringUtil;

/**
 * @description 导出数据表中的数据到文件中
 * @author raoliang
 * @version 2014年10月30日 上午11:49:46
 */
public class ExportDatasFromDB {
	private final Log logger = LogFactory.getLog("dbcompare");
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
	@Inject static MachineDaoService machineDaoService;
	@Inject static ProcessInstanceDaoService processInstanceDaoService;
	@Inject static InstanceDaoService instanceDaoService;
	@Inject static DictDaoService dictDaoService;
	@Inject static DictDetailDao dictDetailDao;
	@Inject static ModeDaoService modeDaoService;
	@Inject static ModeParamDaoService modeParamDaoService;
	
	public static void main(String[] args) {
	}
	
	/**
	* @description 得到所有EndPoint的channel_code
	* @return
	* @author raoliang
	* @version 2014年11月9日 下午2:21:46
	*/
	public List<String> getAllChannelCode(){
		return channelSevice.getAllChannelCode();
	}
	
	/**
	* @description 得到所有服务系统的server_code
	* @return
	* @author raoliang
	* @version 2014年11月9日 下午2:22:07
	*/
	public List<String> getAllServerCode(){
		return serverSevice.getAllServerCode();
	}
	
	/**
	* @description 得到所有EndPoint关联交易表的主键，主键有channel_code和tran_code，中间用">"分隔
	* @return 装有主键的队列
	* @author raoliang
	* @version 2014年11月9日 下午2:33:40
	*/
	public List<String> getAllChannelTranChannelCodeAndTranCode(){
		return tranChannelPackageDaoService.getAllChannelCodeAndTranCode();
	}
	
	/**
	* @description 得到所有服务系统关联交易表的主键，主键有server_code和tran_code，中间用">"分隔
	* @return 装有主键的队列
	* @author raoliang
	* @version 2014年11月9日 下午2:43:43
	*/
	public List<String> getAllServerTranServerCodeAndTranCode(){
		return tranServerPackageDaoService.getAllServerCodeAndeTranCode();
	}
	
	/**
	* @description 得到所有服务的服务编码
	* @return
	* @author raoliang
	* @version 2014年11月9日 下午3:25:09
	*/
	public List<String> getAllServiceCode(){
		return serviceDaoService.getAllServiceCode();
	}
	
	/**
	* @description 得到所有的机器编码
	* @return
	* @author raoliang
	* @version 2014年11月9日 下午3:30:22
	*/
	public List<String> getAllMachineCode(){
		List<MachineInfo> machineList = machineDaoService.getAllMachines();
		List<String> list = new ArrayList<String>();
		for (MachineInfo info : machineList) {
			list.add(info.getMachine_code());
		}
		return list;
	}
	
	/**
	* @description 得到所有的数据字典编码
	* @return
	* @author raoliang
	* @version 2014年11月9日 下午3:35:28
	*/
	public List<String> getAllDictCode(){
		List<DictInfo> listInfo = dictDaoService.getAllDict();
		List<String> list = new ArrayList<String>();
		for (DictInfo info : listInfo) {
			list.add(info.getDict_code());
		}
		return list;
	}
	
	/**
	* @description 得到所有的模式编码
	* @return
	* @author raoliang
	* @version 2014年11月9日 下午3:39:23
	*/
	public List<String> getAllModeCode(){
		List<ModeInfo> listInfo = modeDaoService.getAllModeInfo();
		List<String> list = new ArrayList<String>();
		for (ModeInfo info : listInfo) {
			list.add(info.getMode_code());
		}
		return list;
	}
	
	/**
	* @description 得到指定EndPoint的单元数据
	* @author raoliang
	* @version 2014年11月2日 下午3:02:47
	*/
	public ServiceData getOneEndPoint(String channel_code){
		ChannelInfo channelInfo = channelSevice.getOneChannel(channel_code);
		if(channelInfo == null){
			logger.warn("此ENdPoint不存在，channel_code：{}", channel_code);
			return null;
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
	public ServiceData getOneServer(String server_code){
		ServerInfo serverInfo = serverSevice.getOneServer(server_code);
		if(serverInfo == null){
			logger.warn("服务系统不存在，server_code：{}", server_code);
			return null;
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
	public ServiceData getOneChannelTran(String channel_code, String tran_code){
		TranChannelPackageInfo info = tranChannelPackageDaoService.getOneTran(channel_code, tran_code);
		if(info == null){
			logger.warn("ENdPoint下无此关联交易，channel_code：{},tran_code:{}", channel_code, tran_code);
			return null;
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
	public ServiceData getOneServerTran(String server_code, String tran_code){
		TranServerPackageInfo info = tranServerPackageDaoService.getOneTran(server_code, tran_code);
		if(info == null){
			logger.warn("服务系统关联交易不存在，server_code：{}，tran_code：{}", server_code, tran_code);
			return null;
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
			logger.warn("服务不存在，service_code：{}", service_code);
			return null;
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

	/**
	* @description 得到服务器列表单元数据和部署数据
	* @param machine_code 机器号
	* @return 单元数据
	* @author raoliang
	* @version 2014年11月6日 上午10:05:46
	*/
	public ServiceData getOneMachine(String machine_code){
		MachineInfo info = machineDaoService.getOneMachine(machine_code);
		if (info == null) {
			logger.warn("服务器列表下无此服务器，machine_code：{}", machine_code);
			return null;
		}
		ServiceData data = new ServiceData();
		data.putString("MACHINE_CODE", info.getMachine_code());
		data.putString("MACHINE_IP", info.getMachine_ip());
		data.putString("MACHINE_NAME", info.getMachine_name());
		data.putString("VERNO", info.getVerno());
		ServiceData instanceData = getOneInstance(info.getMachine_code());
		if(instanceData != null && instanceData.size() > 0){
			data.putServiceData("INSTANCE", instanceData);
		}
		logger.info("导出服务器:{}下的进程列表数据{}条", machine_code, instanceData.size());
		ServiceData processInstanceData = getAllProcessInstance(info.getMachine_code());
		if(processInstanceData != null && processInstanceData.size() > 0){
			data.putServiceData("PROCESSINSTANCE", processInstanceData);
		}
		logger.info("导出机器号:{}下部署的EndPoint数据{}条", machine_code, processInstanceData.size());
		return data;
	}
	
	/**
	* @description 得到服务器下的进程列表
	* @param machineCode 服务器编码
	* @return 单元数据
	* @author raoliang
	* @version 2014年11月6日 下午3:18:21
	*/
	public ServiceData getOneInstance(String machineCode){
		List<InstanceInfo> infos = instanceDaoService.getInstances(machineCode);
		if(infos == null){
			logger.warn("vrouter实例不存在，进程标识代码:{}", machineCode);
			return null;
		}
		ServiceData datas = new ServiceData();
		for (InstanceInfo info : infos) {
			ServiceData data = new ServiceData();
			data.putString("MACHINE_CODE", info.getMachine_code());
			data.putString("SKEYC", info.getSkeyc());
			data.putString("SKEYD", info.getSkeyd());
			data.putString("VERNO", info.getVerno());
			datas.putServiceData(info.getSkeyc(), data);
		}
		return datas;
	}
	
	/**
	* @description 返回此机器号下部署的EndPoint
	* @param skeyc 服务器编码
	* @return 单元数据
	* @author raoliang
	* @version 2014年11月6日 上午11:16:16
	*/
	public ServiceData getAllProcessInstance(String skeyc){
		List<ProcessInstanceInfo> infos = processInstanceDaoService.getInstancesByInsCode(skeyc);
		if (infos == null || infos.size() == 0) {
			logger.warn("进程列表无数据，进程编号：{}", skeyc);
			return null;
		}
		ServiceData datas = new ServiceData();
		for (ProcessInstanceInfo info : infos) {
			ServiceData data = new ServiceData();
			data.putString("SKEYC", info.getSkeyc());
			data.putString("CHANNEL_CODE", info.getChannel_code());
			data.putString("BIND_ADDRESS", info.getBind_address());
			data.putString("REMOTE_ADDRESS", info.getRemote_address());
			data.putString("VERNO", info.getVerno());
			datas.putServiceData(info.getChannel_code(), data);
		}
		return datas;
	}
	
	/**
	* @description 得到一个数据字典以及该字典下的所有数据字段
	* @param dict_code 数据字典编码
	* @return 单元数据
	* @author raoliang
	* @version 2014年11月7日 上午9:30:16
	*/
	public ServiceData getOneDict(String dict_code){
		DictInfo info = dictDaoService.findOneDict(dict_code);
		if(info == null){
			logger.warn("数据字典{}不存在", dict_code);
			return null;
		}
		ServiceData data = new ServiceData();
		data.putString("DICT_CODE", info.getDict_code());
		data.putString("DICT_NAME", info.getDict_name());
		data.putString("IS_GLOBAL", info.getIs_global());
		data.putString("VERNO", info.getVerno());
		ServiceData dictDetail = getAllDictDetail(info.getDict_code());
		if(dictDetail != null && dictDetail.size() > 0){
			data.putServiceData("DICT_DETAIL", dictDetail);
		}
		logger.info("导出数据字典:{}下的字段{}条", dict_code, dictDetail.size());
		return data;
	}
	
	/**
	* @description 得到指定数据字典下的所有数据字段的单元数据
	* @param dict_code 数据字典编码
	* @return 详细数据字段单元数据
	* @author raoliang
	* @version 2014年11月7日 上午9:30:54
	*/
	public ServiceData getAllDictDetail(String dict_code){
		Iterator<DictDetailInfo> iterator = dictDetailDao.iteratorFeildsByDictCode(dict_code);
		if(iterator == null){
			logger.warn("数据字典{}中无字段", dict_code);
			return null;
		}
		ServiceData datas = new ServiceData();
		DictDetailInfo info = null;
		while(iterator.hasNext()){
			info = iterator.next();
			ServiceData data = new ServiceData();
			data.putString("DICT_CODE", info.getDict_code());
			data.putString("FIELD_CODE", info.getField_code());
			data.putString("FIELD_NAME", info.getField_name());
			data.putString("FIELD_TYPE", info.getField_type());
			data.putInt("FIELD_LENGTH", info.getField_length());
			data.putInt("FIELD_SCALE", info.getField_scale());
			data.putString("VERNO", info.getVerno());
			datas.putServiceData(info.getField_code(), data);
		}
		return datas;
	}
	
	/**
	* @description 根据mode_code得到模式和对应的模式参数
	* @param mode_code 模式编码
	* @return 单元数据
	* @author raoliang
	* @version 2014年11月7日 下午3:24:01
	*/
	public ServiceData getOneMode(String mode_code) {
		ModeInfo info = modeDaoService.getOneMode(mode_code);
		if(info == null){
			logger.warn("模式不存在，模式名称:{}", mode_code);
			return null;
		}
		ServiceData data = new ServiceData();
		data.putString("MODE_CODE", info.getMode_code());
		data.putString("MODE_NAME", info.getMode_name());
		data.putString("MODE_TYPE", info.getMode_type());
		data.putString("MODE_CLASS", info.getMode_class());
		data.putString("IS_SYS_MODE", info.getIs_sys_mode());
		data.putString("VERNO", info.getVerno());
		//模式参数
		ServiceData modeParam = getModeParam(mode_code);
		if(modeParam != null && modeParam.size() > 0){
			data.putServiceData("MODE_PARAM", modeParam);
		}
		logger.info("导出模式:{}的参数{}条", mode_code, modeParam.size());
		return data;
	}
	
	/**
	* @description 根据模式编码查询该模式下所有的模式参数
	* @param mode_code 模式编码
	* @return 该模式下的所有模式参数数据
	* @author raoliang
	* @version 2014年11月7日 下午5:04:50
	*/
	public ServiceData getModeParam(String mode_code){
		Iterator<ModeParamInfo> iterator = modeParamDaoService.getModeParamByModeCode(mode_code);
		if(iterator == null){
			logger.warn("模式下不模式参数，模式名称:{}", mode_code);
			return null;
		}
		ServiceData datas = new ServiceData();
		while(iterator.hasNext()){
			ModeParamInfo info = iterator.next();
			ServiceData data = new ServiceData();
			data.putString("MODE_CODE", info.getMode_code());
			data.putString("MODE_TYPE", info.getMode_type());
			data.putString("PARAM_CODE", info.getParam_code());
			data.putString("PARAM_CLASS", info.getParam_class());
			data.putString("PARAM_VALUE", info.getParam_value());
			data.putString("VERNO", info.getVerno());
			datas.putServiceData(info.getParam_code(), data);
		}
		return datas;
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
