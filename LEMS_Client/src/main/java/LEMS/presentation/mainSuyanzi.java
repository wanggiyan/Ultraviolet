package LEMS.presentation;

import LEMS.po.informationpo.InstitutionPO;
import LEMS.po.userpo.UserRole;
import LEMS.presentation.financeui.SettingPriceUi;
import LEMS.vo.uservo.UserVO;

public class mainSuyanzi {

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		InstitutionPO i = new InstitutionPO("haha","zhutou");
		UserVO user = new UserVO("123123123123","susu",UserRole.StoreManager,"suyanzi",i);
//		ManagerUi a = new ManagerUi(mainFrame,user);
//		FinancialStaffUi a = new FinancialStaffUi(mainFrame,user);
//		GeneralManagerUi a = new GeneralManagerUi(mainFrame,user);
//		AccountManageUi a = new AccountManageUi(mainFrame,user);
//		TransferClerkUi a = new TransferClerkUi(mainFrame,user);
//		CourierUi a = new CourierUi(mainFrame);
//		ExamDocumentUi a = new ExamDocumentUi(mainFrame,user);
		SettingPriceUi a = new SettingPriceUi(mainFrame,user);
		mainFrame.setContentPane(a);
	}

}
