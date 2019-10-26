package ai.turbochain.ipex.utils;

public class StyleUtil {
	
	public String getSuccess(String message, String date) {
		String success = "<div style='width:100%;text-align:center;margin-top:5%'><h1>" + message + "</h1><h2>" + date + "</h2></div>";
		return success;
	}
	
	public String getError(String message, String detail, String date) {
		String error = "<div style='width:100%;text-align:center;margin-top:5%;color:red'><h1>" + message + "</h1><h1>" + detail + "</h1><h2>" + date + "</h2></div>";
		return error;
	}

}
