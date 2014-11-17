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
 * @description ɾ��ָ�����������,Ŀǰ��ɾ��EndPoint������ϵͳ�����񡢲�����ص�����
 * @author raoliang
 * @version 2014��11��4�� ����2:55:17
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
	* @description ɾ��һ��EndPoint,�Լ����µĹ�������
	* @param channel_code EndPoint����
	* @return �ɹ�ɾ������
	* @author raoliang
	* @version 2014��11��4�� ����3:09:57
	*/
	public int deleteOneEndPoint(String channel_code){
		return channelDaoService.deleteOneChannel(channel_code);
	}
	
	/**
	* @description ɾ��һ��EndPoint��������
	* @param channel_code EndPoint����
	* @param tran_code ������������
	* @return �ɹ�ɾ������
	* @author raoliang
	* @version 2014��11��4�� ����3:08:33
	*/
	public int deleteOneTranEndPoint(String channel_code, String tran_code){
		return tranChannelPackageDaoService.deleteOneTran(channel_code, tran_code);
	}
	
	/**
	* @description ����EndPoint���ƺ͹�����������ɾ����صĹ�������
	* @param channel_code EndPoint����
	* @param tran_codes ����������������
	* @return �ɹ�ɾ������
	* @author raoliang
	* @version 2014��11��4�� ����3:36:18
	*/
	public int deleteTranEndPoints(String channel_code, String... tran_codes){
		int count = 0;
		for (String tran_code : tran_codes) {
			count += deleteOneTranEndPoint(channel_code, tran_code);
		}
		return count;
	}
	
	/**
	* @description ɾ��һ������ϵͳ
	* @param server_code ����ϵͳ����
	* @return �ɹ�ɾ������
	* @author raoliang
	* @version 2014��11��4�� ����3:08:05
	*/
	public int deleteOneServer(String server_code){
		return serverDaoService.deleteOneServer(server_code);
	}

	/**
	* @description ���ݽ�������ɾ��Server��һ����������
	* @param tran_code ��������
	* @return �ɹ�ɾ������
	* @author raoliang
	* @version 2014��11��4�� ����3:07:01
	*/
	public int deleteOneTranServerByTranCode(String tran_code){
		return tranServerPackageDaoService.deleteOneTran(tran_code);
	}
	
	/**
	* @description ���ݽ�������ɾ������ϵͳ��صĹ�������
	* @param tran_codes ������������
	* @return �ɹ�ɾ������
	* @author raoliang
	* @version 2014��11��4�� ����3:30:14
	*/
	public int deleteTranServerByTranCodeList(String... tran_codes){
		int count = 0;
		for (String code : tran_codes) {
			count += deleteOneTranServerByTranCode(code);
		}
		return count;
	}
	
	/**
	* @description ɾ��һ��Service���������Ϸ��񻹻�ɾ������Ϸ�������ͼ
	* @param service_code
	* @return
	* @author raoliang
	* @version 2014��11��15�� ����2:53:35
	*/
	public int deleteOneService(String service_code){
		return serviceDaoService.deleteOneService(service_code);
	}
	
	/**
	* @description ���ݷ���������ɾ���������Լ��������Ľ����б�Ͳ���Ľ��̡�
	* @param machine_codes ��������룬�ö��ŷָ�
	* @return
	* @author raoliang
	* @version 2014��11��17�� ����2:55:12
	*/
	public int deleteOneMachine(String machine_codes){
		return machineDaoService.deleteOneMachine(machine_codes);
	}
	
	/**
	* @description ���������ֵ����ɾ�������ֵ䣬ͬʱ��ɾ���������ֵ��µ����������ֶ�
	* @param dict_code
	* @return
	* @author raoliang
	* @version 2014��11��17�� ����6:56:42
	*/
	public int deleteOneDict(String dict_code){
		return dictService.deleteDict(dict_code);
	}
	
	/**
	* @description ���������ֵ������ֶα���ɾ��һ�������ֶ�
	* @param dict_code �����ֵ����
	* @param field_code �ֶα���
	* @return
	* @author raoliang
	* @version 2014��11��17�� ����7:00:18
	*/
	public int deleteOneDictDetail(String dict_code, String field_code){
		return dictDetailDaoService.deleteOneField(dict_code, field_code);
	}
	
	/**
	* @description ����ģʽ���ͺͱ���ɾ��ģʽ���������µ�����ģʽ����
	* @param mode_type
	* @param mode_codes
	* @return
	* @author raoliang
	* @version 2014��11��17�� ����8:15:03
	*/
	public int deleteOneMode(String mode_type, String mode_codes){
		return modeService.deleteModes(mode_type, mode_codes);
	}
	
	/**
	* @description ɾ��һ��ģʽ����
	* @param mode_code ģʽ����
	* @param mode_type ģʽ����
	* @param params_codes ģʽ��������
	* @return
	* @author raoliang
	* @version 2014��11��17�� ����8:18:00
	*/
	public int deleteOneModeParam(String mode_code, String mode_type, String params_codes){
		return modeParamDaoService.deleteParams(mode_code, mode_type, params_codes);
	}
}
