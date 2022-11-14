package com.java.ejb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CabDAO {
	Connection connection;
	PreparedStatement pst;
	
	public String updateCab(Cab cabNew) 
			throws ClassNotFoundException, SQLException {
		Cab cab = searchCab(cabNew.getBookingID());
		if (cab!=null) {
			String cmd = "update cab set BookingDate=?, UserId=?, DriverId=?,StartingLocation=?,EndingLocation=?,Kilometers=?,BookingStatus=?, "
					+ " BillAmount =? where BookingID=?";
			connection = ConnectionHelper.getConnection();
			pst = connection.prepareStatement(cmd);
			pst.setDate(1, cabNew.getBookingDate());
			pst.setInt(2, cabNew.getUserId());
			pst.setInt(3, cabNew.getDriverId());
			pst.setString(4, cabNew.getStartingLocation());
			pst.setString(5, cabNew.getEndingLocation());
			pst.setInt(6, cabNew.getKilometers());
			pst.set(7, cabNew.getBookingStatus());
			pst.setInt(8, cabNew.getBookingID());
			pst.executeUpdate();
			return "Cab Record Updated...";
		}
		return "Record Not Found...";
	} 
	public String deleteCab(int BookingID)
			throws ClassNotFoundException, SQLException {
		Cab cab = searchCab(BookingID);
		if (cab!=null) {
			connection = ConnectionHelper.getConnection();
			String cmd = "delete from Cab where BookingID=?";
			pst = connection.prepareStatement(cmd);
			pst.setInt(1,BookingID);
			pst.executeUpdate();
			return "Record Deleted...";
		}
		return "Cab Record Not Found...";
	}
	public String addCab(Cab cab) 
			throws ClassNotFoundException, SQLException {
		connection = ConnectionHelper.getConnection();
		String cmd = "insert into Cab(BookingDate, UserId, DriverId,StartingLocation,EndingLocation,Kilometers,BookingStatus,)" 
				+ " values(?,?,?,?,?,?,?,?)";
		pst = connection.prepareStatement(cmd);
		pst.setDate(1, cab.getBookingDate());
		pst.setInt(2, cab.getUserId());
		pst.setInt(3, cab.getDriverId());
		pst.setString(4, cab.getStartingLocation());
		pst.setString(5, cab.getEndingLocation());
		pst.setInt(6, cab.getKilometers());
		pst.set(7, cab.getBookingStatus());
		pst.executeUpdate();
		return "Record Inserted...";
	}
	
	public Cab searchCab(int BookingID)
			throws ClassNotFoundException, SQLException {
		connection = ConnectionHelper.getConnection();
		String cmd = "select * from Cab where BookingID=?";
		pst = connection.prepareStatement(cmd);
		pst.setInt(1, BookingID);
		ResultSet rs = pst.executeQuery();
		Cab cab = null;
		if(rs.next()) {
			cab = new Cab();
			cab.setBookingDate(rs.getDate("bookingdate"));
			cab.setUserId(rs.getInt("userid"));
			cab.setDriverId(rs.getInt("driverid"));
			cab.setStartingLocation(rs.getString("startinglocation"));
			cab.setEndingLocation(rs.getString("endinglocation"));
			cab.setKilometers(rs.getInt("kilometers"));
			cab.setBookingStatus(rs.getString("bookingstatus"));
			
		}
		return cab;
	}
	public List<Cab> showCab() throws 
				ClassNotFoundException, SQLException {
		List<Cab> cabList = new ArrayList<Cab>();
		connection = ConnectionHelper.getConnection();
		String cmd = "select * from Cab";
		pst = connection.prepareStatement(cmd);
		ResultSet rs = pst.executeQuery();
		Cab cab = null;
		while(rs.next()) {
			cab = new Cab();
			cab.setBookingDate(rs.getDate("bookingid"));
			cab.setUserId(rs.getInt("userid"));
			cab.setDriverId(rs.getInt("driverid"));
			cab.setStartingLocation(rs.getString("startinglocation"));
			cab.setEndingLocation(rs.getString("endinglocation"));
			cab.setKilometers(rs.getInt("kilometers"));
			cab.setBookingStatus( rs.getBookingStatus("bookingstatus"));
			cabList.add(cab);
		}
		return cabList;
	}
}

