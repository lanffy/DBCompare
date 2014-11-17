package compare;

import com.wk.Controller;
import com.wk.eai.webide.dao.ChannelDaoService;
import com.wk.eai.webide.dao.CommDaoService;
import com.wk.eai.webide.dao.DictDaoService;
import com.wk.eai.webide.dao.DictDetailDao;
import com.wk.eai.webide.dao.DictDetailDaoService;
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
import com.wk.lang.Inject;
import com.wk.logging.Log;
import com.wk.logging.LogFactory;

/**
 * @description
 * @author raoliang
 * @version 2014年11月17日 下午2:45:59
 */
public class DBImpl {
	static final Log logger = LogFactory.getLog("dbcompare");
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
	@Inject static MappingDaoService mappingDaoService;
	@Inject static ChannelDaoService channelDaoService;
	@Inject static ServerDaoService serverDaoService;
	@Inject static DictDetailDaoService dictDetailDaoService;
	@Inject static DeleteDatasFromDB deleter;
//	static DeleteDatasFromDB deleter = Controller.getInstance().getInjector().getBean(DeleteDatasFromDB.class);
}
