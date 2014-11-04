package compare;

import com.wk.Controller;
import com.wk.beans.Injector;
import com.wk.db.DBSource;
import com.wk.db.Session;
import com.wk.eai.webide.dao.DictDaoService;
import com.wk.eai.webide.dao.DictDetailDaoService;

/**
 * @description
 * @author raoliang
 * @version 2014年10月16日 上午11:23:25
 */
public class TestH2 {
	private static final Injector injector = Controller.getInstance().getInjector();
	private static final DictDetailDaoService dser = injector.getBean(DictDetailDaoService.class);
	private static final DictDaoService ser = injector.getBean(DictDaoService.class);
	public static void main(String[] args) {
		System.out.println(ser.deleteOneDict("test"));
		DBSource d = DBSource.getDefault();
		Session s = d.getSession();
		s.commit();
//		DictDetailInfo d = new DictDetailInfo();
//		d.setDict_code("global");
//		d.setField_code("aaaaaa");
//		d.setField_length(1);
//		d.setField_name("a测试");
//		d.setField_scale(0);
//		d.setField_type("string");
//		d.setVerno("1.00");
//		
//		DictDetailInfo detail = new DictDetailInfo();
//		detail.setDict_code("dict1");
//		detail.setField_code("field_float");
//		detail.setField_length(7);
//		detail.setField_scale(4);
//		detail.setField_type("float");
//		System.out.println(dser.insertOneDictDetail(detail));
	}
}
