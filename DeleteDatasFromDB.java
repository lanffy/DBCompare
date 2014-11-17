package compare;

import com.wk.eai.webide.dao.ChannelDaoService;
import com.wk.eai.webide.dao.DictDetailDaoService;
import com.wk.eai.webide.dao.MachineDaoService;
import com.wk.eai.webide.dao.ModeParamDaoService;
import com.wk.eai.webide.dao.ServerDaoService;
import com.wk.eai.webide.dao.ServiceDaoService;
import com.wk.eai.webide.dao.TranChannelPackageDaoService;
import com.wk.eai.webide.dao.TranServerPackageDaoService;
import com.wk.eai.webide.service.DictService;
import com.wk.eai.webide.service.ModeService;
import com.wk.lang.Inject;


/**
 * @description 删除指定的相关数据,目前能删除EndPoint、服务系统、服务、部署相关的数据
 * @author raoliang
 * @version 2014年11月4日 下午2:55:17
 */
public class DeleteDatasFromDB {
	@Inject static TranChannelPackageDaoService tranChannelPackageDaoService;
	@Inject static TranServerPackageDaoService tranServerPackageDaoService;
	@Inject static ServiceDaoService serviceDaoService;
	@Inject static MachineDaoService machineDaoService;
	@Inject static ChannelDaoService channelDaoService;
	@Inject static ServerDaoService serverDaoService;
	@Inject static DictService dictService;
	@Inject static DictDetailDaoService dictDetailDaoService;
	@Inject static ModeService modeService;
	@Inject static ModeParamDaoService modeParamDaoService;
	
	/**
	* @description 删除一条EndPoint,以及其下的关联交易
	* @param channel_code EndPoint名称
	* @return 成功删除条数
	* @author raoliang
	* @version 2014年11月4日 下午3:09:57
	*/
	public int deleteOneEndPoint(String channel_code){
		return channelDaoService.deleteOneChannel(channel_code);
	}
	
	/**
	* @description 删除一条EndPoint关联交易
	* @param channel_code EndPoint名称
	* @param tran_code 关联交易名称
	* @return 成功删除条数
	* @author raoliang
	* @version 2014年11月4日 下午3:08:33
	*/
	public int deleteOneTranEndPoint(String channel_code, String tran_code){
		return tranChannelPackageDaoService.deleteOneTran(channel_code, tran_code);
	}
	
	/**
	* @description 根据EndPoint名称和关联交易名称删除相关的关联交易
	* @param channel_code EndPoint名称
	* @param tran_codes 关联交易名称数组
	* @return 成功删除条数
	* @author raoliang
	* @version 2014年11月4日 下午3:36:18
	*/
	public int deleteTranEndPoints(String channel_code, String... tran_codes){
		int count = 0;
		for (String tran_code : tran_codes) {
			count += deleteOneTranEndPoint(channel_code, tran_code);
		}
		return count;
	}
	
	/**
	* @description 删除一个服务系统
	* @param server_code 服务系统名称
	* @return 成功删除条数
	* @author raoliang
	* @version 2014年11月4日 下午3:08:05
	*/
	public int deleteOneServer(String server_code){
		return serverDaoService.deleteOneServer(server_code);
	}

	/**
	* @description 根据交易名称删除Server的一个关联交易
	* @param tran_code 交易名称
	* @return 成功删除条数
	* @author raoliang
	* @version 2014年11月4日 下午3:07:01
	*/
	public int deleteOneTranServerByTranCode(String tran_code){
		return tranServerPackageDaoService.deleteOneTran(tran_code);
	}
	
	/**
	* @description 根据交易名称删除服务系统相关的关联交易
	* @param tran_codes 交易名称数组
	* @return 成功删除条数
	* @author raoliang
	* @version 2014年11月4日 下午3:30:14
	*/
	public int deleteTranServerByTranCodeList(String... tran_codes){
		int count = 0;
		for (String code : tran_codes) {
			count += deleteOneTranServerByTranCode(code);
		}
		return count;
	}
	
	/**
	* @description 删除一个Service，如果是组合服务还会删除其组合服务流程图
	* @param service_code
	* @return
	* @author raoliang
	* @version 2014年11月15日 下午2:53:35
	*/
	public int deleteOneService(String service_code){
		return serviceDaoService.deleteOneService(service_code);
	}
	
	/**
	* @description 根据服务器编码删除服务器以及服务器的进程列表和部署的进程。
	* @param machine_codes 多个机器码，用逗号分割
	* @return
	* @author raoliang
	* @version 2014年11月17日 下午2:55:12
	*/
	public int deleteOneMachine(String machine_codes){
		return machineDaoService.deleteOneMachine(machine_codes);
	}
	
	/**
	* @description 根据数据字典编码删除数据字典，同时会删除该数据字典下的所有数据字段
	* @param dict_code
	* @return
	* @author raoliang
	* @version 2014年11月17日 下午6:56:42
	*/
	public int deleteOneDict(String dict_code){
		return dictService.deleteDict(dict_code);
	}
	
	/**
	* @description 根据数据字典编码和字段编码删除一条数据字段
	* @param dict_code 数据字典编码
	* @param field_code 字段编码
	* @return
	* @author raoliang
	* @version 2014年11月17日 下午7:00:18
	*/
	public int deleteOneDictDetail(String dict_code, String field_code){
		return dictDetailDaoService.deleteOneField(dict_code, field_code);
	}
	
	/**
	* @description 根据模式类型和编码删除模式，包括其下的所有模式参数
	* @param mode_type
	* @param mode_codes
	* @return
	* @author raoliang
	* @version 2014年11月17日 下午8:15:03
	*/
	public int deleteOneMode(String mode_type, String mode_codes){
		return modeService.deleteModes(mode_type, mode_codes);
	}
	
	/**
	* @description 删除一条模式参数
	* @param mode_code 模式编码
	* @param mode_type 模式类型
	* @param params_codes 模式参数编码
	* @return
	* @author raoliang
	* @version 2014年11月17日 下午8:18:00
	*/
	public int deleteOneModeParam(String mode_code, String mode_type, String params_codes){
		return modeParamDaoService.deleteParams(mode_code, mode_type, params_codes);
	}
}
