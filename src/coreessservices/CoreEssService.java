package coreessservices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;










import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import vo.OOOVO;
import vo.TimesheetVO;
import vo.UserdetailsVO;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import dao.CoreEssDAO;


@Path("/getdata")
public class CoreEssService {
	
	@GET
	public void defaultFunction(){
		
		System.out.println("Default Function");
	}
	
	@POST
	  @Path(value = "/userdetails")
	  @Produces(MediaType.APPLICATION_JSON)
		public String userdetails(String strng) throws SQLException{
		CoreEssDAO ced = new CoreEssDAO();
		HashMap<String, String> hashmapC = new HashMap<String, String>();
		boolean verifiedUser = false;
		String al = null;
		try {
			JSONObject json = new JSONObject(strng);
			System.out.println(json+"\n"+json.getString("username")+" and "+json.getString("password"));
			verifiedUser = ced.verifyuser(json.getString("username"), json.getString("password"));
			if(verifiedUser){
				hashmapC = ced.getuserdetails(json.getString("username"), json.getString("password"));
				Gson gson = new Gson();
				al = gson.toJson(hashmapC);
			}
			else
				return null;
			
			System.out.println( "result is ");
		} catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return al;
	}
	
	@POST
	  @Path(value = "/timesheetdetails")
	  @Produces(MediaType.APPLICATION_JSON)
		public String timesheetData(String strng) throws SQLException{
		CoreEssDAO ced = new CoreEssDAO();
		ArrayList<TimesheetVO> timesheetsAl = new ArrayList<TimesheetVO>();
		String al = null;
		try {
			JSONObject json = new JSONObject(strng);
			System.out.println(json+"\n"+json.getString("username"));
			timesheetsAl = ced.gettimesheetdetails(json.getString("username"));
			
			Gson gson = new Gson();
			al = gson.toJson(timesheetsAl);

			
			System.out.println( "result is ");
			
		} catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return al;
	}
	
	@POST
	  @Path(value = "/inserttimesheetdetails")
	  @Produces(MediaType.APPLICATION_JSON)
		public boolean inserttimesheetData(String strng) throws SQLException{
		CoreEssDAO ced = new CoreEssDAO();
		TimesheetVO timesheetvo = new TimesheetVO();
		boolean al = false;
		try {
			JSONObject json = new JSONObject(strng);
			System.out.println(json+"\n"+json.getString("username"));
			timesheetvo.setDate(json.getString("Date"));
			timesheetvo.setStart(json.getString("Start"));
			timesheetvo.setBreak1_Start(json.getString("Break1_Start"));
			timesheetvo.setBreak1_End(json.getString("Break1_End"));
			timesheetvo.setBreak2_Start(json.getString("Break2_Start"));
			timesheetvo.setBreak2_End(json.getString("Break2_End"));
			timesheetvo.setEOD(json.getString("EOD"));
			
			al = ced.posttimesheetdetails(timesheetvo, json.getString("username"));
			
			System.out.println( "result is ");
			
		} catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return al;
	}
	
	@POST
	  @Path(value = "/updatetimesheetdetails")
	  @Produces(MediaType.APPLICATION_JSON)
		public boolean updatetimesheetData(String strng) throws SQLException{
		CoreEssDAO ced = new CoreEssDAO();
		TimesheetVO timesheetvo = new TimesheetVO();
		boolean al = false;
		try {
			JSONObject json = new JSONObject(strng);
			timesheetvo.setDate(json.getString("Date"));
			timesheetvo.setStart(json.getString("Start"));
			timesheetvo.setBreak1_Start(json.getString("Break1_Start"));
			timesheetvo.setBreak1_End(json.getString("Break1_End"));
			timesheetvo.setBreak2_Start(json.getString("Break2_Start"));
			timesheetvo.setBreak2_End(json.getString("Break2_End"));
			timesheetvo.setEOD(json.getString("EOD"));

			al = ced.updatetimesheetdetails(timesheetvo, json.getString("username"));
			
			System.out.println( "result is ");
			
		} catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return al;
	}
	
	@POST
	  @Path(value = "/deletetimesheetdetails")
	  @Produces(MediaType.APPLICATION_JSON)
		public boolean deletetimesheetData(String strng) throws SQLException{
		CoreEssDAO ced = new CoreEssDAO();
		boolean al = false;
		try {
			JSONObject json = new JSONObject(strng);
			al = ced.deletetimesheetdetails(json.getString("date"));
			
		} catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return al;
	}
	
	@POST
	  @Path(value = "/ooodetails")
	  @Produces(MediaType.APPLICATION_JSON)
		public String OOOData(String strng) throws SQLException{
		CoreEssDAO ced = new CoreEssDAO();
		ArrayList<OOOVO> oooAl = new ArrayList<OOOVO>();		
		String al = null;
		try {
			JSONObject json = new JSONObject(strng);
			System.out.println(json+"\n"+json.getString("username"));
			oooAl = ced.getooodetails(json.getString("username"));
			
			Gson gson = new Gson();
			al = gson.toJson(oooAl);

			
		} catch (JSONException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return al;
	}
}
