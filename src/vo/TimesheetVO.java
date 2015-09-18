package vo;

public class TimesheetVO {
	
	public int id; 
	public String Date;
	public String Start;
	public String Break1_Start;
	public String Break1_End;
	public String Break2_Start;
	public String Break2_End;
	public String EOD;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getStart() {
		return Start;
	}
	public void setStart(String start) {
		Start = start;
	}
	public String getBreak1_Start() {
		return Break1_Start;
	}
	public void setBreak1_Start(String break1_Start) {
		Break1_Start = break1_Start;
	}
	public String getBreak1_End() {
		return Break1_End;
	}
	public void setBreak1_End(String break1_End) {
		Break1_End = break1_End;
	}
	public String getBreak2_Start() {
		return Break2_Start;
	}
	public void setBreak2_Start(String break2_Start) {
		Break2_Start = break2_Start;
	}
	public String getBreak2_End() {
		return Break2_End;
	}
	public void setBreak2_End(String break2_End) {
		Break2_End = break2_End;
	}
	public String getEOD() {
		return EOD;
	}
	public void setEOD(String eOD) {
		EOD = eOD;
	}

}
