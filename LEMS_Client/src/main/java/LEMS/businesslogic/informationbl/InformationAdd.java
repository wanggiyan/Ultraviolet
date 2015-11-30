package LEMS.businesslogic.informationbl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import LEMS.businesslogicservice.informationblservice.InformationAddService;
import LEMS.dataservice.factory.DatabaseFactory;
import LEMS.dataservice.factory.InformationFactory;
import LEMS.dataservice.informationdataservice.InformationInsertDataService;
import LEMS.dataservice.inquiredataservice.DiaryDataService;
import LEMS.po.informationpo.DriverPO;
import LEMS.po.informationpo.InstitutionPO;
import LEMS.po.informationpo.StaffPO;
import LEMS.po.inquirepo.DiaryPO;
import LEMS.po.userpo.UserPO;
import LEMS.po.userpo.UserRole;
import LEMS.vo.informationvo.AccountVO;
import LEMS.vo.informationvo.DriverVO;
import LEMS.vo.informationvo.InstitutionVO;
import LEMS.vo.informationvo.StaffVO;
import LEMS.vo.informationvo.VehicleVO;
import LEMS.vo.uservo.UserVO;

public class InformationAdd implements InformationAddService{
	/**
	 * 增加司机信息
	 */
	public void addDriver(DriverVO drivervo){
		try {
			DatabaseFactory database=(DatabaseFactory)Naming.lookup("rmi://localhost:1099/data");
			InformationFactory inf=database.getInformationFactory();
			InformationInsertDataService infoinsert=inf.getInformationInsertData();
			DriverPO dp=new DriverPO(drivervo.getId(),drivervo.getName(),drivervo.getDateOfBirth(),drivervo.getIDcardNumber(),drivervo.getPhoneNumber(),drivervo.getDrivingPeriod(),drivervo.getGender());
			infoinsert.insert(dp);;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 增加车辆信息
	 */
	public void addVehicle(VehicleVO vehiclevo){
		try{
			DatabaseFactory database=(DatabaseFactory)Naming.lookup("rmi://localhost:1099/data");
			InformationFactory inf=database.getInformationFactory();
			InformationInsertDataService infoinsert=inf.getInformationInsertData();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 增加机构信息
	 */
	public void addInstitution(InstitutionVO institutionvo){
		try {
			DatabaseFactory database=(DatabaseFactory)Naming.lookup("rmi://localhost:1099/data");
			InformationFactory inf=database.getInformationFactory();
			InformationInsertDataService infoinsert=inf.getInformationInsertData();
			InstitutionPO ip=new InstitutionPO(institutionvo.getID(),institutionvo.getLocation());
			infoinsert.insert(ip);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 增加人员信息
	 */
	public void addStaff(UserVO uservo){
		try {
			DatabaseFactory database=(DatabaseFactory)Naming.lookup("rmi://localhost:1099/data");
			InformationFactory inf=database.getInformationFactory();
			InformationInsertDataService infoinsert=inf.getInformationInsertData();
			UserPO sp=new UserPO(uservo.getId(),uservo.getPassword(),uservo.getRole(),uservo.getName(),uservo.getInstitution());
			infoinsert.insert(sp);;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 增加账户信息
	 */
	public void addAccout(AccountVO accoutvo){
		
	}
	public static void main(String[] args){
		InformationAdd ia=new InformationAdd();
		InstitutionPO ipo=new InstitutionPO("","");
		ia.addStaff(new UserVO("fc00000","123456",UserRole.FinanceClerk,"苏燕子",ipo));		
	}
}
