package LEMS.data.orderdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import LEMS.data.Connect;
import LEMS.data.TransferID;
import LEMS.dataservice.orderdataservice.VehicleLoadDataService;
import LEMS.po.financepo.DocumentState;
import LEMS.po.orderpo.OrderPO;
import LEMS.po.orderpo.VehicleLoadNotePO;

public class VehicleLoadData extends UnicastRemoteObject implements VehicleLoadDataService {
	private static final long serialVersionUID = 1L;

	private Connect connect;
	
	private TransferID transferID;
	
	/**
	 * ID长度
	 * 营业厅编号+20150921日期+00000编码 、五位数字
	 */
	private final int ID_LENGTH = 20;
	
	public VehicleLoadData() throws RemoteException {
		super();
		
		connect = new Connect();
		transferID = new TransferID();
	}


	@Override
	public VehicleLoadNotePO find(String id) throws RemoteException {
		VehicleLoadNotePO vehicleLoadNotePO = new VehicleLoadNotePO();
		
		String sql = "SELECT * FROM vehicleloadnote WHERE id = " + id;
		
		ResultSet result = connect.getResultSet(sql);
		try {
			result.next();
			vehicleLoadNotePO.setId(id);
			vehicleLoadNotePO.setState(DocumentState.valueOf(result.getString(2)));
			vehicleLoadNotePO.setDate(result.getString(3));
			vehicleLoadNotePO.setVehicle(result.getString(4));
			vehicleLoadNotePO.setDeparture(result.getString(5));
			vehicleLoadNotePO.setDestination(result.getString(6));
			vehicleLoadNotePO.setSuperVision(result.getString(7));
			vehicleLoadNotePO.setSuperCargo(result.getString(8));
			vehicleLoadNotePO.setOrders(transferID.transferOrder(result.getString(9)));
			vehicleLoadNotePO.setPassage(result.getDouble(10));
			
			connect.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vehicleLoadNotePO;
	}

	@Override
	public void insert(VehicleLoadNotePO vehicleLoadNotePO) throws RemoteException {
		String sql = "INSERT INTO vehicleloadnote VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstm = connect.getPreparedStatement(sql);
		
		try {
			pstm.setString(1, vehicleLoadNotePO.getId());
			pstm.setString(2, vehicleLoadNotePO.getState() + "");
			pstm.setString(3, vehicleLoadNotePO.getDate());
			pstm.setString(4, vehicleLoadNotePO.getVehicle());
			pstm.setString(5, vehicleLoadNotePO.getDeparture());
			pstm.setString(6, vehicleLoadNotePO.getDestination());
			pstm.setString(7, vehicleLoadNotePO.getSupervision());
			pstm.setString(8, vehicleLoadNotePO.getSuperCargo());
			pstm.setString(9, transferID.transferOrder(vehicleLoadNotePO.getOrders()));
			pstm.setDouble(10, vehicleLoadNotePO.getPassage());
			
			pstm.executeUpdate();
			
			connect.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(VehicleLoadNotePO vehicleLoadNotePO) throws RemoteException {
		this.delete(vehicleLoadNotePO.getId());
		this.insert(vehicleLoadNotePO);
	}

	@Override
	public void delete(String id) throws RemoteException {
		String sql = "DELETE FROM vehicleloadnote WHERE id = " + id;
		
		PreparedStatement pstm = connect.getPreparedStatement(sql);
		
		try {
			pstm.executeUpdate();
			
			connect.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String createID(String institution, String date) throws RemoteException {
		String id = new CreateID().createID("vehicleloadnote", ID_LENGTH, institution + date);
		
		return id;
	}
	
	public static void main(String[] args) {
		VehicleLoadNotePO po = new VehicleLoadNotePO();
		po.setId("02502012015121300001");
		po.setState(DocumentState.waiting);
		po.setVehicle("025201001");
		po.setDate("20151213");
		po.setDeparture("南京市仙林大道");
		po.setDestination("北京市海淀区");
		po.setSuperVision("0250201143");
		po.setSuperCargo("0250201154");
		ArrayList<OrderPO> o=new ArrayList<OrderPO>();
		OrderPO opo=new OrderPO();
		opo.setId("1234567890");
		o.add(opo);
		po.setOrders(o);
		po.setPassage(400);
		
		try {
			VehicleLoadData data = new VehicleLoadData();
			data.insert(po);
			VehicleLoadNotePO vehicleLoadNotePO = data.find(po.getId());
			System.out.println(vehicleLoadNotePO.getPassage());
			System.out.println(vehicleLoadNotePO.getDeparture());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
