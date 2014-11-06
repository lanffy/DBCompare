package compare;

import com.wk.eai.webide.dao.ChannelDaoService;
import com.wk.eai.webide.dao.ServerDaoService;
import com.wk.eai.webide.dao.TranChannelPackageDaoService;
import com.wk.eai.webide.dao.TranServerPackageDaoService;
import com.wk.lang.Inject;

/**
 * @description ɾ��ָ�����������,Ŀǰ��ɾ��EndPoint�ͷ���ϵͳ��ص�����
 * @author raoliang
 * @version 2014��11��4�� ����2:55:17
 */
public class DeleteDatasFromDB {
	@Inject ChannelDaoService channelDaoService;
	@Inject ServerDaoService serverDaoService;
	@Inject TranChannelPackageDaoService tranChannelPackageDaoService; 
	@Inject TranServerPackageDaoService tranServerPackageDaoService;
	
	/**
	* @description ɾ��һ��EndPoint
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
	* @description ���ݷ���ϵͳServer����ɾ�����µĹ�������
	* @param server_code ����ϵͳ����
	* @return �ɹ�ɾ������
	* @author raoliang
	* @version 2014��11��4�� ����3:22:50
	*/
	public int deleteTranServerByServerCode(String server_code){
		return tranServerPackageDaoService.deleteTranByServerCode(server_code);
	}
	
	/**
	* @description ���ݽ�������ɾ������ϵͳ��صĹ�������
	* @param tran_codes �������Ƽ��ϣ��ö��ŷָ�
	* @return �ɹ�ɾ������
	* @author raoliang
	* @version 2014��11��4�� ����3:24:37
	*/
	public int deleteTranServerByTranCodes(String tran_codes){
		return tranServerPackageDaoService.deleteTrans(tran_codes);
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
}
