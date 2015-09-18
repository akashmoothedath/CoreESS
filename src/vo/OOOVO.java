package vo;

public class OOOVO {
	public String Username;
	public String  Start_Date;
	public String  End_Date;
	public String  Duration;
	public String  Approved_By;
	public String  Comments;
	public String  Status;
	
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getStart_Date() {
		return Start_Date;
	}
	public void setStart_Date(String start_Date) {
		this.Start_Date = start_Date;
	}
	public String getEnd_Date() {
		return End_Date;
	}
	public void setEnd_Date(String end_Date) {
		this.End_Date = end_Date;
	}
	public String getDuration() {
		return Duration;
	}
	public void setDuration(String duration) {
		this.Duration = duration;
	}
	public String getApproved_By() {
		return Approved_By;
	}
	public void setApproved_By(String approved_By) {
		this.Approved_By = approved_By;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		this.Comments = comments;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		this.Status = status;
	}
	
}
