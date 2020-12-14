public class Course{
	

	private String code;
	private String name;
	private String description;
	private String format;

	//constructor empty
	public Course() {
		this.code = "";
		this.name = "";
		this.description = "";
		this.format = "";
	}

	//constructor 
	public Course(String name, String code, String descr, String fmt) {

		//assigning variable values
		this.code = code;
		this.name = name;
		this.description = descr;
		this.format = fmt;
	}


	//retrieving course code
	public String getCode() {
		return code;
	}
	
	//retrieving course name
	public String getName() {
		return name;
	}	
	
	//retrieving course format
	public String getFormat() {
		return format;
	}

	//retrieving course description
	public String getDescription() {
		return code + " - " + name + "\n" + description + "\n" + format;
	}
	
	//retrieving course code + name
	public String getInfo() {
		return code + " - " + name;
	}

	// static method to convert numeric score to letter grade string
	// e.g. 91 --> "A+"
	public static String convertNumericGrade(double score) {
		if (score >= 90) {
			return "A+";
		} else if (score < 90 && score >= 85) {
			return "A";
		} else if (score < 85 && score >= 80) {
			return "A-";
		} else if (score < 80 && score >= 77) {
			return "B+";
		} else if (score < 77 && score >= 73) {
			return "B";
		} else if (score < 73 && score >= 70) {
			return "B-";
		} else if (score < 70 && score >= 67) {
			return "C+";
		} else if (score < 67 && score >= 63) {
			return "C";
		} else if (score < 63 && score >= 60) {
			return "C-";
		} else if (score < 60 && score >= 57) {
			return "D+";
		} else if (score < 57 && score >= 53) {
			return "D";
		} else if (score < 53 && score >= 50) {
			return "D-";
		} else if (score < 50) {
			return "F";
		}
		return "Please Enter A Valid Percentage(%)";
	}
}