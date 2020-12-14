public class CreditCourse extends Course{

	//variables for object CreditCourse
	private String semester;
	private String grade;
	public boolean active = true;

	//constructor for credit course
	public CreditCourse(String name, String code, String descr, String fmt, String semester, String grade) {

		//assigns values to variables
		//calls super constructor to init
		super(name, code, descr, fmt);
		this.grade = grade;
		this.semester = semester;
	}

	//changes final grade
	public void setFinalGrade(String grade){
		this.grade = grade;
	}

	//gets the active status
	public boolean getActive() {
		return this.active;
	}

	//sets status to active
	public void setActive() {
		this.active = true;
	}

	//sets status to inactive
	public void setInactive() {
		this.active = false;
	}

	//Added to return numeric grade 
	public double getGradeNumeric(){
		return Double.parseDouble(this.grade);
	}

	//Displays info about course and grade
	public String displayGrade() {
		return super.getCode() + " " + super.getName() + " " +  this.semester + " Grade " + convertNumericGrade(Double.parseDouble(this.grade));
	}

}