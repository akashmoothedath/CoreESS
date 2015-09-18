package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import vo.OOOVO;
import vo.TimesheetVO;
import vo.UserdetailsVO;

import com.google.gson.Gson;

public class CoreEssDAO {

	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/world", "nikhil", "nik9849131035");
		}

		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}
	
	public boolean verifyuser(String username, String password) throws SQLException{
		Connection conn = getConnection();
		boolean result = false;
		String query = "select * from user where username = ? and password = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		result = pstmt.execute();
		return result;
	}
	
	
	public HashMap<String, String> getuserdetails(String username, String password) throws SQLException{
		Connection conn = getConnection();
		UserdetailsVO userdetailsvo = new UserdetailsVO();
		HashMap<String, String> hashmapC = new HashMap<String, String>();
		ArrayList<UserdetailsVO> userdetailsAl = new ArrayList<UserdetailsVO>();
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try {
				String query1 = "select  OutOfOffice, Days_Remaining, Cycle_End from ooo_employee where username = ?";
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(query1);
				pstmt.setString(1, username);
				rs1 = pstmt.executeQuery();

				String query2 = "select  Approved_By, Start_Date, End_Date, Duration from ooo_employee_details where username = ?";
				pstmt = conn.prepareStatement(query2);
				pstmt.setString(1, username);
				rs2 = pstmt.executeQuery();
				
				while (rs1.next()) {
					userdetailsvo.setDaysOffTaken(rs1.getString("OutOfOffice"));
					userdetailsvo.setDaysOffRemaining(rs1.getString("Days_Remaining"));
					userdetailsvo.setEndDate(rs1.getString("Cycle_End"));
				}
				
				while (rs2.next()) {
					userdetailsvo.setRollOver("2");
					userdetailsvo.setManager(rs2.getString("Approved_By"));
					userdetailsvo.setRecentStartDate(rs2.getString("Start_Date"));
					userdetailsvo.setRecentEndDate(rs2.getString("End_Date"));
					userdetailsvo.setRecentDuration(rs2.getString("Duration"));
					userdetailsvo.setRecentApprovedBy(rs2.getString("Approved_By"));
				}
				
				userdetailsAl.add(userdetailsvo);
				hashmapC.put("daysOffTaken", userdetailsvo.getDaysOffTaken());
				hashmapC.put("daysOffRemaining", userdetailsvo.getDaysOffRemaining());
				hashmapC.put("endDate", userdetailsvo.getEndDate());
				hashmapC.put("rollOver", "2");
				hashmapC.put("manager", userdetailsvo.getManager());
				hashmapC.put("recentStartDate", userdetailsvo.getRecentStartDate());
				hashmapC.put("recentEndDate", userdetailsvo.getRecentEndDate());
				hashmapC.put("recentDuration", userdetailsvo.getRecentDuration());
				
				
				
				
				
				
				//
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs1 != null || rs2 != null){
				rs1.close();
				rs2.close();
			}
			conn.close();
		}
		return hashmapC;
	}
	
	
	public ArrayList<TimesheetVO> gettimesheetdetails(String username) throws SQLException{
		Connection conn = getConnection();
		
		ArrayList<TimesheetVO> timesheetsAl = new ArrayList<TimesheetVO>();
		ResultSet rs1 = null;
		
		try {
				String query1 = "select * from employee_timesheet where username = ?";
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(query1);
				pstmt.setString(1, username);
				rs1 = pstmt.executeQuery();
				int i=0;
				while (rs1.next()) {
					TimesheetVO timesheetvo = new TimesheetVO();
					i++;
					timesheetvo.setId(i);
					timesheetvo.setDate(rs1.getString("Today_Date"));
					timesheetvo.setStart(rs1.getString("Start_Time"));
					timesheetvo.setBreak1_Start(rs1.getString("Break1_Start"));
					timesheetvo.setBreak1_End(rs1.getString("Break1_End"));
					timesheetvo.setBreak2_Start(rs1.getString("Break2_Start"));
					timesheetvo.setBreak2_End(rs1.getString("Break2_End"));
					timesheetvo.setEOD(rs1.getString("End_Of_Day"));
					timesheetsAl.add(timesheetvo);
				}
				
				System.out.println(timesheetsAl);
				
				
				//
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs1 != null){
				rs1.close();
			}
			conn.close();
		}
		return timesheetsAl;
	}
	
	public boolean posttimesheetdetails(TimesheetVO timesheetvo, String username) throws SQLException{
		Connection conn = getConnection();
		boolean rs1 = false;
		
		try {
				String query1 = "insert into employee_timesheet values(?,?,?,?,?,?,?,?)";
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(query1);
				pstmt.setString(1, username);
				pstmt.setString(2, timesheetvo.getDate());
				pstmt.setString(3, timesheetvo.getStart());
				pstmt.setString(4, timesheetvo.getBreak1_Start());
				pstmt.setString(5, timesheetvo.getBreak1_End());
				pstmt.setString(6, timesheetvo.getBreak2_Start());
				pstmt.setString(7, timesheetvo.getBreak2_End());
				pstmt.setString(8, timesheetvo.getEOD());

				rs1 = pstmt.execute();
				//
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			conn.close();
		}
		return rs1;
	}
	
	

	public boolean updatetimesheetdetails(TimesheetVO timesheetvo, String username) throws SQLException{
		Connection conn = getConnection();
		boolean rs1 = false;
		
		try {
				String query1 = "update employee_timesheet set Start_Time=?, Break1_Start=?, Break1_End=?, Break2_Start=?, Break2_End=?, End_Of_Day=? where username=? AND Today_Date=?";
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(query1);
				pstmt.setString(7, username);
				pstmt.setString(8, timesheetvo.getDate());
				pstmt.setString(1, timesheetvo.getStart());
				pstmt.setString(2, timesheetvo.getBreak1_Start());
				pstmt.setString(3, timesheetvo.getBreak1_End());
				pstmt.setString(4, timesheetvo.getBreak2_Start());
				pstmt.setString(5, timesheetvo.getBreak2_End());
				pstmt.setString(6, timesheetvo.getEOD());

				rs1 = pstmt.execute();
				//
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			conn.close();
		}
		return rs1;
	}
	

	
	public boolean deletetimesheetdetails(String date) throws SQLException{
		Connection conn = getConnection();
		boolean rs1 = false;
		
		try {
				String query1 = "delete from employee_timesheet where Today_Date=?";
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(query1);
				pstmt.setString(1, date);
				
				rs1 = pstmt.execute();
				//
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			conn.close();
		}
		return rs1;
	}

	
	
	public ArrayList<OOOVO> getooodetails(String username) throws SQLException{
		Connection conn = getConnection();
		ArrayList<OOOVO> oooAl = new ArrayList<OOOVO>();
		ResultSet rs1 = null;
		
		try {
			
			if(username.equalsIgnoreCase("manager")){
				String query1 = "select * from ooo_employee_details where Approved_By = ?";
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(query1);
				pstmt.setString(1, username);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					OOOVO ooovo= new OOOVO();
					ooovo.setUsername(rs1.getString("username"));
					ooovo.setStart_Date(rs1.getString("Start_Date"));
					ooovo.setEnd_Date(rs1.getString("End_Date"));
					ooovo.setDuration(rs1.getString("Duration"));
					ooovo.setApproved_By(rs1.getString("Approved_By"));
					ooovo.setComments(rs1.getString("Comments"));
					ooovo.setStatus(rs1.getString("Status"));
					oooAl.add(ooovo);
				}
			}
			else{
				String query1 = "select * from ooo_employee_details where username = ?";
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(query1);
				pstmt.setString(1, username);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					OOOVO ooovo= new OOOVO();
					ooovo.setUsername(rs1.getString("username"));
					ooovo.setStart_Date(rs1.getString("Start_Date"));
					ooovo.setEnd_Date(rs1.getString("End_Date"));
					ooovo.setDuration(rs1.getString("Duration"));
					ooovo.setApproved_By(rs1.getString("Approved_By"));
					ooovo.setComments(rs1.getString("Comments"));
					ooovo.setStatus(rs1.getString("Status"));
					oooAl.add(ooovo);
				
			}
		}	
				System.out.println(oooAl);
				
				
				//
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs1 != null){
				rs1.close();
			}
			conn.close();
		}
		return oooAl;
	}
	
	
	
	
}
