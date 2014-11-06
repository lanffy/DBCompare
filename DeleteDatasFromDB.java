package compare;

import com.wk.eai.webide.dao.ChannelDaoService;
import com.wk.eai.webide.dao.ServerDaoService;
import com.wk.eai.webide.dao.TranChannelPackageDaoService;
import com.wk.eai.webide.dao.TranServerPackageDaoService;
import com.wk.lang.Inject;

/**
 * @description 删除指定的相关数据,目前能删除EndPoint和服务系统相关的数据
 * @author raoliang
 * @version 2014年11月4日 下午2:55:17
 */
public class DeleteDatasFromDB {
	@Inject ChannelDaoService channelDaoService;
	@Inject ServerDaoService serverDaoService;
	@Inject TranChannelPackageDaoService tranChannelPackageDaoService; 
	@Inject TranServerPackageDaoService tranServerPackageDaoService;
	
	/**
	* @description 删除一条EndPoint
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
	* @description 根据服务系统Server名称删除其下的关联交易
	* @param server_code 服务系统名称
	* @return 成功删除条数
	* @author raoliang
	* @version 2014年11月4日 下午3:22:50
	*/
	public int deleteTranServerByServerCode(String server_code){
		return tranServerPackageDaoService.deleteTranByServerCode(server_code);
	}
	
	/**
	* @description 根据交易名称删除服务系统相关的关联交易
	* @param tran_codes 交易名称集合，用逗号分割
	* @return 成功删除条数
	* @author raoliang
	* @version 2014年11月4日 下午3:24:37
	*/
	public int deleteTranServerByTranCodes(String tran_codes){
		return tranServerPackageDaoService.deleteTrans(tran_codes);
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
}
