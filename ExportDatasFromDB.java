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
 * @description �������ݱ��е����ݵ��ļ���
 * @author raoliang
 * @version 2014��10��30�� ����11:49:46
 */
public class ExportDatasFromDB {
	private static final Log logger = LogFactory.getLog("dbcompare");
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
	
	/**
	* @description �õ�����EndPoint��channel_code
	* @return
	* @author raoliang
	* @version 2014��11��9�� ����2:21:46
	*/
	public List<String> getAllChannelCode(){
		List<ChannelInfo> itinfo = channelSevice.getAllChannelInfo();
		List<String> retList = new ArrayList<String>();
		for (ChannelInfo channelInfo : itinfo) {
			retList.add(channelInfo.getChannel_code());
		}
		return retList;
	}
	
	/**
	* @description �õ����з���ϵͳ��server_code
	* @return
	* @author raoliang
	* @version 2014��11��9�� ����2:22:07
	*/
	public List<String> getAllServerCode(){
		List<ServerInfo> serverCodelist = serverSevice.getAllServerInfo();
		List<String> list = new ArrayList<String>();
		for (ServerInfo serverInfo : serverCodelist) {
			list.add(serverInfo.getServer_code());
		}
		return list;
	}
	
	/**
	* @description �õ�����EndPoint�������ױ������,������channel_code��tran_code,�м���">"�ָ�
	* @return װ�������Ķ���
	* @author raoliang
	* @version 2014��11��9�� ����2:33:40
	*/
	public List<String> getAllChannelTranChannelCodeAndTranCode(){
		List<TranChannelPackageInfo> it = tranChannelPackageDaoService.getAllChannelTranInfo();
		List<String> list = new ArrayList<String>();
		for (TranChannelPackageInfo info : it) {
			list.add(info.getChannel_code()+">"+info.getTran_code());
		}
		return list;
	}
	
	/**
	* @description �õ����з���ϵͳ�������ױ������,������server_code��tran_code,�м���">"�ָ�
	* @return װ�������Ķ���
	* @author raoliang
	* @version 2014��11��9�� ����2:43:43
	*/
	public List<String> getAllServerTranServerCodeAndTranCode(){
		List<TranServerPackageInfo> it = tranServerPackageDaoService.getAllServerTranInfo();
		List<String> list = new ArrayList<String>();
		for (TranServerPackageInfo info : it) {
			list.add(info.getServer_code()+">"+info.getTran_code());
		}
		return list;
	}
	
	/**
	* @description �õ����з���ķ������
	* @return
	* @author raoliang
	* @version 2014��11��9�� ����3:25:09
	*/
	public List<String> getAllServiceCode(){
		List<ServiceInfo> it = serviceDaoService.getAllServiceInfo();
		List<String> list = new ArrayList<String>();
		for (ServiceInfo info : it) {
			list.add(info.getService_code());
		}
		return list;
	}
	
	/**
	* @description �õ����еĻ�������
	* @return
	* @author raoliang
	* @version 2014��11��9�� ����3:30:22
	*/
	public List<String> getAllMachineCode(){
		List<MachineInfo> machineList = machineDaoService.getAllMachineInfo();
		List<String> list = new ArrayList<String>();
		for (MachineInfo info : machineList) {
			list.add(info.getMachine_code());
		}
		return list;
	}
	
	/**
	* @description �õ����е������ֵ����
	* @return
	* @author raoliang
	* @version 2014��11��9�� ����3:35:28
	*/
	public List<String> getAllDictCode(){
		List<DictInfo> listInfo = dictDaoService.getAllDictInfo();
		List<String> list = new ArrayList<String>();
		for (DictInfo info : listInfo) {
			list.add(info.getDict_code());
		}
		return list;
	}
	
	/**
	* @description �õ����е�ģʽ����
	* @return
	* @author raoliang
	* @version 2014��11��9�� ����3:39:23
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
	* @description �õ�ָ��EndPoint�ĵ�Ԫ����
	* @author raoliang
	* @version 2014��11��2�� ����3:02:47
	*/
	public ServiceData getOneEndPoint(String channel_code){
		ChannelInfo channelInfo = channelSevice.getOneChannel(channel_code);
		if(channelInfo == null){
			logger.error("��ENdPoint������,channel_code:{}", channel_code);
			return null;
		}
		ServiceData channelData = new ServiceData();
		putChannelBasicPar(channelData, channelInfo);
		//ͨѶ
		ServiceData commData = getCommData(channelInfo.getComm_id());
		if(commData == null){
			logger.error("��EndPoint({})��ͨѶ����Ϊ��,������Ӧ�ó���", channelInfo.getChannel_code());
		}
		channelData.putServiceData("COMM_ID", commData);
		//����������
		ServiceData reqConfData = getStructure(channelInfo.getReq_package_config());
		if(!StringUtil.isEmpty(channelInfo.getReq_package_config()) && reqConfData == null){
			logger.error("EndPoint({})���������ô���,���REQ_PACKAGE_CONFIG�Ƿ�����", channelInfo.getChannel_code());
		}
		channelData.putServiceData("REQ_PACKAGE_CONFIG", reqConfData);
		//��Ӧ��������
		ServiceData respConfData = getStructure(channelInfo.getResp_package_config());
		if(!StringUtil.isEmpty(channelInfo.getResp_package_config()) && respConfData == null){
			logger.error("EndPoint({})��Ӧ�������ô���,���RESP_PACKAGE_CONFIG�Ƿ�����", channelInfo.getChannel_code());
		}
		channelData.putServiceData("RESP_PACKAGE_CONFIG", respConfData);
		//����������
		ServiceData errConfData = getStructure(channelInfo.getErr_package_config());
		if(!StringUtil.isEmpty(channelInfo.getErr_package_config()) && errConfData == null){
			logger.error("EndPoint({})���������ô���,���ERR_PACKAGE_CONFIG�Ƿ�����", channelInfo.getChannel_code());
		}
		channelData.putServiceData("ERR_PACKAGE_CONFIG", errConfData);
		
		//����ӳ��
		ServiceData inMapData = getMap(channelInfo.getIn_mapping());
		if(!StringUtil.isEmpty(channelInfo.getIn_mapping()) && inMapData == null){
			logger.error("EndPoint({})ϵͳ����ӳ�����ô���,���IN_MAPPING�Ƿ�����", channelInfo.getChannel_code());
		}
		channelData.putServiceData("IN_MAPPING", inMapData);
		//��Ӧӳ��
		ServiceData outMapData = getMap(channelInfo.getOut_mapping());
		if(!StringUtil.isEmpty(channelInfo.getOut_mapping()) && outMapData == null){
			logger.error("EndPoint({})ϵͳ��Ӧӳ�����ô���,���OUT_MAPPING�Ƿ�����", channelInfo.getChannel_code());
		}
		channelData.putServiceData("OUT_MAPPING", outMapData);
		//����ӳ��
		ServiceData errMapData = getMap(channelInfo.getError_mapping());
		if(!StringUtil.isEmpty(channelInfo.getError_mapping()) && errMapData == null){
			logger.error("EndPoint({})ϵͳ����ӳ�����ô���,���ERROR_MAPPING�Ƿ�����", channelInfo.getChannel_code());
		}
		channelData.putServiceData("ERROR_MAPPING", errMapData);
		return channelData;
	}
	
	/**
	* @description �õ�ָ������ϵͳ�ĵ�Ԫ����
	* @author raoliang
	* @version 2014��11��2�� ����3:03:13
	*/
	public ServiceData getOneServer(String server_code){
		ServerInfo serverInfo = serverSevice.getOneServer(server_code);
		if(serverInfo == null){
			logger.error("����ϵͳ������,server_code:{}", server_code);
			return null;
		}
		ServiceData serverData = new ServiceData();
		putServerBasicPar(serverData, serverInfo);
		//ͨѶ
		ServiceData commData = getCommData(serverInfo.getComm_id());
		if(commData == null){
			logger.error("�˷���ϵͳ({})��ͨѶ����Ϊ��,������Ӧ�ó���", serverInfo.getServer_code());
		}
		serverData.putServiceData("COMM_ID", commData);
		
		//����������
		ServiceData reqConfData = getStructure(serverInfo.getReq_package_config());
		if(!StringUtil.isEmpty(serverInfo.getReq_package_config()) && reqConfData == null){
			logger.error("����ϵͳ({})���������ô���,���REQ_PACKAGE_CONFIG�Ƿ�����", serverInfo.getServer_code());
		}
		serverData.putServiceData("REQ_PACKAGE_CONFIG", reqConfData);
		//��Ӧ��������
		ServiceData respConfData = getStructure(serverInfo.getResp_package_config());
		if(!StringUtil.isEmpty(serverInfo.getResp_package_config()) && respConfData == null){
			logger.error("����ϵͳ({})��Ӧ�������ô���,���RESP_PACKAGE_CONFIG�Ƿ�����", serverInfo.getServer_code());
		}
		serverData.putServiceData("RESP_PACKAGE_CONFIG", respConfData);
		//����������
		ServiceData errConfData = getStructure(serverInfo.getErr_package_config());
		if(!StringUtil.isEmpty(serverInfo.getErr_package_config()) && errConfData == null){
			logger.error("����ϵͳ({})���������ô���,���ERR_PACKAGE_CONFIG�Ƿ�����", serverInfo.getServer_code());
		}
		serverData.putServiceData("ERR_PACKAGE_CONFIG", errConfData);
		
		//����ӳ��
		ServiceData inMapData = getMap(serverInfo.getIn_mapping());
		if(!StringUtil.isEmpty(serverInfo.getIn_mapping()) && inMapData == null){
			logger.error("����ϵͳ({})ϵͳ����ӳ�����ô���,���IN_MAPPING�Ƿ�����", serverInfo.getServer_code());
		}
		serverData.putServiceData("IN_MAPPING", inMapData);
		//��Ӧӳ��
		ServiceData outMapData = getMap(serverInfo.getOut_mapping());
		if(!StringUtil.isEmpty(serverInfo.getOut_mapping()) && outMapData == null){
			logger.error("����ϵͳ({})ϵͳ��Ӧӳ�����ô���,���OUT_MAPPING�Ƿ�����", serverInfo.getServer_code());
		}
		serverData.putServiceData("OUT_MAPPING", outMapData);
		//����ӳ��
		ServiceData errMapData = getMap(serverInfo.getError_mapping());
		if(!StringUtil.isEmpty(serverInfo.getError_mapping()) && errMapData == null){
			logger.error("����ϵͳ({})ϵͳ����ӳ�����ô���,���ERROR_MAPPING�Ƿ�����", serverInfo.getServer_code());
		}
		serverData.putServiceData("ERROR_MAPPING", errMapData);
		return serverData;
	}
	
	/**
	 * �õ�ָ��EndPoint�������׵�Ԫ����
	 * @param channel_code ��������
	 * @param tran_code �ؼ���������
	 * @return �������
	 */
	public ServiceData getOneChannelTran(String channel_code, String tran_code){
		TranChannelPackageInfo info = tranChannelPackageDaoService.getOneTran(channel_code, tran_code);
		if(info == null){
			logger.error("ENdPoint���޴˹�������,channel_code:{},tran_code:{}", channel_code, tran_code);
			return null;
		}
		ServiceData data = new ServiceData();
		data.putString("CHANNEL_CODE", info.getChannel_code());
		data.putString("TRAN_CODE", info.getTran_code());
		data.putString("TRAN_NAME", info.getTran_name());
		//����������
		ServiceData reqConfData = getStructure(info.getReq_package_config());
		if(!StringUtil.isEmpty(info.getReq_package_config()) && reqConfData == null){
			logger.error("EndPoint({})��������({})���������ô���,���REQ_PACKAGE_CONFIG�Ƿ�����", channel_code, tran_code);
		}
		data.putServiceData("REQ_PACKAGE_CONFIG", reqConfData);
		//��Ӧ��������
		ServiceData respConfData = getStructure(info.getResp_package_config());
		if(!StringUtil.isEmpty(info.getResp_package_config()) && respConfData == null){
			logger.error("EndPoint({})��������({})��Ӧ�������ô���,���RESP_PACKAGE_CONFIG�Ƿ�����", channel_code, tran_code);
		}
		data.putServiceData("RESP_PACKAGE_CONFIG", respConfData);
		
		//����ӳ��
		ServiceData inMapData = getMap(info.getIn_mapping());
		if(!StringUtil.isEmpty(info.getIn_mapping()) && inMapData == null){
			logger.error("EndPoint({})��������({})����ӳ�����ô���,���IN_MAPPING�Ƿ�����", channel_code, tran_code);
		}
		data.putServiceData("IN_MAPPING", inMapData);
		//��Ӧӳ��
		ServiceData outMapData = getMap(info.getOut_mapping());
		if(!StringUtil.isEmpty(info.getOut_mapping()) && outMapData == null){
			logger.error("EndPoint({})��������({})��Ӧӳ�����ô���,���OUT_MAPPING�Ƿ�����", channel_code, tran_code);
		}
		data.putServiceData("OUT_MAPPING", outMapData);
		//����ӳ��
		ServiceData errMapData = getMap(info.getError_mapping());
		if(!StringUtil.isEmpty(info.getError_mapping()) && errMapData == null){
			logger.error("EndPoint({})��������({})����ӳ�����ô���,���ERROR_MAPPING�Ƿ�����", channel_code, tran_code);
		}
		data.putServiceData("ERROR_MAPPING", errMapData);
		return data;
	}

	/**
	 * �õ�����ϵͳָ���������׵�Ԫ����
	 * @param server_code ����ϵͳ����
	 * @param tran_code ������������
	 * @return ���������������
	 */
	public ServiceData getOneServerTran(String server_code, String tran_code){
		TranServerPackageInfo info = tranServerPackageDaoService.getOneTran(server_code, tran_code);
		if(info == null){
			logger.error("����ϵͳ�������ײ�����,server_code:{},tran_code:{}", server_code, tran_code);
			return null;
		}
		ServiceData data = new ServiceData();
		data.putString("SERVER_CODE", info.getServer_code());
		data.putString("TRAN_CODE", info.getTran_code());
		data.putString("TRAN_NAME", info.getTran_name());
		//����������
		ServiceData reqConfData = getStructure(info.getReq_package_config());
		if(!StringUtil.isEmpty(info.getReq_package_config()) && reqConfData == null){
			logger.error("����ϵͳ({})��������({})���������ô���,���REQ_PACKAGE_CONFIG�Ƿ�����", server_code, tran_code);
		}
		data.putServiceData("REQ_PACKAGE_CONFIG", reqConfData);
		//��Ӧ��������
		ServiceData respConfData = getStructure(info.getResp_package_config());
		if(!StringUtil.isEmpty(info.getResp_package_config()) && respConfData == null){
			logger.error("����ϵͳ({})��������({})��Ӧ�������ô���,���RESP_PACKAGE_CONFIG�Ƿ�����", server_code, tran_code);
		}
		data.putServiceData("RESP_PACKAGE_CONFIG", respConfData);
		
		//����ӳ��
		ServiceData inMapData = getMap(info.getIn_mapping());
		if(!StringUtil.isEmpty(info.getIn_mapping()) && inMapData == null){
			logger.error("����ϵͳ({})��������({})����ӳ�����ô���,���IN_MAPPING�Ƿ�����", server_code, tran_code);
		}
		data.putServiceData("IN_MAPPING", inMapData);
		//��Ӧӳ��
		ServiceData outMapData = getMap(info.getOut_mapping());
		if(!StringUtil.isEmpty(info.getOut_mapping()) && outMapData == null){
			logger.error("����ϵͳ({})��������({})��Ӧӳ�����ô���,���OUT_MAPPING�Ƿ�����", server_code, tran_code);
		}
		data.putServiceData("OUT_MAPPING", outMapData);
		//����ӳ��
		ServiceData errMapData = getMap(info.getError_mapping());
		if(!StringUtil.isEmpty(info.getError_mapping()) && errMapData == null){
			logger.error("����ϵͳ({})��������({})����ӳ�����ô���,���ERROR_MAPPING�Ƿ�����", server_code, tran_code);
		}
		data.putServiceData("ERROR_MAPPING", errMapData);
		return data;
	}
	
	/**
	* @description �õ�ָ���������Ƶĵ�Ԫ����
	* @param service_code ��������
	* @return ����Ԫ����
	* @author raoliang
	* @version 2014��11��4�� ����7:40:42
	*/
	public ServiceData getOneService(String service_code) {
		ServiceInfo info = serviceDaoService.getOneServiceByCode(service_code);
		if (info == null) {
			logger.error("���񲻴���,service_code:{}", service_code);
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
		//����������
		ServiceData reqConfData = getStructure(info.getReq_stru());
		if(!StringUtil.isEmpty(info.getReq_stru()) && reqConfData == null){
			logger.error("����({})���������ô���,���REQ_STRU�Ƿ�����", info.getService_code());
		}
		data.putServiceData("REQ_STRU", reqConfData);
		//��Ӧ��������
		ServiceData respConfData = getStructure(info.getResp_stru());
		if(!StringUtil.isEmpty(info.getResp_stru()) && respConfData == null){
			logger.error("����({})��Ӧ�������ô���,���RESP_STRU�Ƿ�����", info.getService_code());
		}
		data.putServiceData("RESP_STRU", respConfData);
		//����������
		ServiceData errConfData = getStructure(info.getErr_stru());
		if(!StringUtil.isEmpty(info.getErr_stru()) && errConfData == null){
			logger.error("����({})���������ô���,���ERR_STRU�Ƿ�����", info.getService_code());
		}
		data.putServiceData("ERR_STRU", errConfData);
		data.putString("IS_PUBLISHED", info.getIs_published());
		//��Ϸ���
		if("2".equals(service_type)){
			data.putString("CONTENT", groupSvcChartDaoService.getChartContent(service_code));
		}
		return data;
	}

	/**
	* @description �õ��������б�Ԫ���ݺͲ�������
	* @param machine_code ������
	* @return ��Ԫ����
	* @author raoliang
	* @version 2014��11��6�� ����10:05:46
	*/
	public ServiceData getOneMachine(String machine_code){
		MachineInfo info = machineDaoService.getOneMachine(machine_code);
		if (info == null) {
			logger.error("�������б����޴˷�����,machine_code:{}", machine_code);
			return null;
		}
		ServiceData data = new ServiceData();
		data.putString("MACHINE_CODE", info.getMachine_code());
		data.putString("MACHINE_IP", info.getMachine_ip());
		data.putString("MACHINE_NAME", info.getMachine_name());
		ServiceData instanceData = getOneInstance(info.getMachine_code());
		if(instanceData != null && instanceData.size() > 0){
			data.putServiceData("INSTANCE", instanceData);
			logger.info("����������:{}�µĽ����б�����{}��", machine_code, instanceData.size());
		}
		
		String[] skeycs = instanceData.getKeys();
		ServiceData pid = new ServiceData();
		for (String skeyc : skeycs) {
			ServiceData processInstanceData = getAllProcessInstance(skeyc);
			if(processInstanceData != null && processInstanceData.size() > 0){
				pid.putServiceData(skeyc, processInstanceData);
				logger.info("����������:{}�µĽ���{}�²����EndPoint����{}��", machine_code, skeyc, processInstanceData.size());
			}
		}
		if(pid.size() > 0){
			data.putServiceData("PROCESSINSTANCE", pid);
		}
		return data;
	}
	
	/**
	* @description �õ��������µĽ����б�
	* @param machineCode ����������
	* @return ��Ԫ����
	* @author raoliang
	* @version 2014��11��6�� ����3:18:21
	*/
	public ServiceData getOneInstance(String machineCode){
		List<InstanceInfo> infos = instanceDaoService.getInstances(machineCode);
		if(infos == null){
			logger.warn("�������½����б�����,����������:{}", machineCode);
			return null;
		}
		ServiceData datas = new ServiceData();
		for (InstanceInfo info : infos) {
			ServiceData data = new ServiceData();
			data.putString("MACHINE_CODE", info.getMachine_code());
			data.putString("SKEYC", info.getSkeyc());
			data.putString("SKEYD", info.getSkeyd());
			datas.putServiceData(info.getSkeyc(), data);
		}
		return datas;
	}
	
	/**
	* @description ���ش˻������²����EndPoint
	* @param skeyc ����������
	* @return ��Ԫ����
	* @author raoliang
	* @version 2014��11��6�� ����11:16:16
	*/
	public ServiceData getAllProcessInstance(String skeyc){
		List<ProcessInstanceInfo> infos = processInstanceDaoService.getInstancesByInsCode(skeyc);
		if (infos == null || infos.size() == 0) {
			logger.warn("�����б�������,���̱��:{}", skeyc);
			return null;
		}
		ServiceData datas = new ServiceData();
		for (ProcessInstanceInfo info : infos) {
			ServiceData data = new ServiceData();
			data.putString("SKEYC", info.getSkeyc());
			data.putString("CHANNEL_CODE", info.getChannel_code());
			data.putString("BIND_ADDRESS", info.getBind_address());
			data.putString("REMOTE_ADDRESS", info.getRemote_address());
			
			datas.putServiceData(info.getChannel_code(), data);
		}
		return datas;
	}
	
	/**
	* @description �õ�һ�������ֵ��Լ����ֵ��µ����������ֶ�
	* @param dict_code �����ֵ����
	* @return ��Ԫ����
	* @author raoliang
	* @version 2014��11��7�� ����9:30:16
	*/
	public ServiceData getOneDict(String dict_code){
		DictInfo info = dictDaoService.findOneDict(dict_code);
		if(info == null){
			logger.error("�����ֵ�{}������", dict_code);
			return null;
		}
		ServiceData data = new ServiceData();
		data.putString("DICT_CODE", info.getDict_code());
		data.putString("DICT_NAME", info.getDict_name());
		data.putString("IS_GLOBAL", info.getIs_global());
		
		ServiceData dictDetail = getAllDictDetail(info.getDict_code());
		if(dictDetail != null && dictDetail.size() > 0){
			data.putServiceData("DICT_DETAIL", dictDetail);
		}
		logger.info("���������ֵ�:{}�µ��ֶ�{}��", dict_code, dictDetail.size());
		return data;
	}
	
	/**
	* @description �õ�ָ�������ֵ��µ����������ֶεĵ�Ԫ����
	* @param dict_code �����ֵ����
	* @return ��ϸ�����ֶε�Ԫ����
	* @author raoliang
	* @version 2014��11��7�� ����9:30:54
	*/
	public ServiceData getAllDictDetail(String dict_code){
		Iterator<DictDetailInfo> iterator = dictDetailDao.iteratorFeildsByDictCode(dict_code);
		if(iterator == null){
			logger.warn("�����ֵ�{}�����ֶ�", dict_code);
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
			
			datas.putServiceData(info.getField_code(), data);
		}
		return datas;
	}
	
	/**
	* @description ����mode_code�õ�ģʽ�Ͷ�Ӧ��ģʽ����
	* @param mode_code ģʽ����
	* @return ��Ԫ����
	* @author raoliang
	* @version 2014��11��7�� ����3:24:01
	*/
	public ServiceData getOneMode(String mode_code) {
		ModeInfo info = modeDaoService.getOneMode(mode_code);
		if(info == null){
			logger.error("ģʽ������,ģʽ����:{}", mode_code);
			return null;
		}
		ServiceData data = new ServiceData();
		data.putString("MODE_CODE", info.getMode_code());
		data.putString("MODE_NAME", info.getMode_name());
		data.putString("MODE_TYPE", info.getMode_type());
		data.putString("MODE_CLASS", info.getMode_class());
		data.putString("IS_SYS_MODE", info.getIs_sys_mode());
		
		//ģʽ����
		ServiceData modeParam = getModeParam(mode_code);
		if(modeParam != null && modeParam.size() > 0){
			data.putServiceData("MODE_PARAM", modeParam);
		}
		logger.info("����ģʽ:{}�Ĳ���{}��", mode_code, modeParam.size());
		return data;
	}
	
	/**
	* @description ����ģʽ�����ѯ��ģʽ�����е�ģʽ����
	* @param mode_code ģʽ����
	* @return ��ģʽ�µ�����ģʽ��������
	* @author raoliang
	* @version 2014��11��7�� ����5:04:50
	*/
	public ServiceData getModeParam(String mode_code){
		Iterator<ModeParamInfo> iterator = modeParamDaoService.getModeParamByModeCode(mode_code);
		if(iterator == null){
			logger.warn("��ģʽ����ģʽ����,ģʽ����:{}", mode_code);
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
		data.putString("BIZ_CHANNEL_EXP", info.getBiz_channel_exp());
	}
	
	private static void putServerBasicPar(ServiceData data, ServerInfo info){
		data.putString("SERVER_CODE", info.getServer_code());
		data.putString("SERVER_NAME", info.getServer_name());
		data.putString("SUCCESS_EXP", info.getSuccess_exp());
		data.putString("PUT_SVC_EXP", info.getPut_svc_exp());
		data.putString("MSG_CLASS", info.getMsg_class());
		data.putString("SERVER_ACTOR_CLASS", info.getServer_actor_class());
	}

  	private static ServiceData getCommData(String comm_id){
  		return getCommData(commDaoSevice.findOneComm(comm_id));
  	}
  	
  	private static ServiceData getStructure(String structure_id){
  		return StringUtil.isEmpty(structure_id) ? null : getStructureServiceData(structureDaoSevice.get(structure_id));
  	}
  	
  	private static ServiceData getMap(String map_id){
  		return StringUtil.isEmpty(map_id) ? null : getMapData(mapDaoService.getAllMappingById(map_id));
  	}
  	
	private static ServiceData getCommData(CommInfo info){
		if(info == null){
			return null;
		}
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
		return commData;
	}

	private static ServiceData getStructureServiceData(StructureInfo info){
		if(info == null){
			return null;
		}
		ServiceData structureData = new ServiceData();
		structureData.putString("SERVICE_CODE", info.getService_code());
		structureData.putString("STRUCTURE_TYPE", info.getStructure_type());
		structureData.putString("STRUCTURE_CATEGORY", info.getStructure_category());
		structureData.putServiceData("STRUCTURE_CONTENT", getDataServiceData(info.getStructure_content()));
		return structureData;
	}

	private static ServiceData getMapData(MappingInfo info){
		if(info == null){
			return null;
		}
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
