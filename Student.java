import java.util.ArrayList;

public class Student implements Comparable<Student>
{
  //Defining variables
  private String name;
  private String id;
  public ArrayList<CreditCourse> courses;
  
  //student constuctor
  public Student(String name, String id)
  {
    //Assigning values to variables
	  this.name = name;
	  this.id   = id;
	  courses   = new ArrayList<CreditCourse>();
  }
  
  //returns Student ID
  public String getId()
  {
	  return id;
  }
  
  //returns Student Name
  public String getName()
  {
	  return name;
  }
  
  //Adds a course to a student's list of courses
  public void addCourse(String courseName, String courseCode, String descr, String format,String sem, double grade)
  {
    //New credit course object, set as active, added to arrayList
    CreditCourse newCourse = new CreditCourse(courseName, courseCode, descr, format, sem, Double.toString(grade)); //parameters string recieved double
    newCourse.setActive();
    this.courses.add(newCourse);
  }


  //Prints out a students transcript all courses completed with grade and semester
  public void printTranscript(){ 
    String transcript = "";
	  for (int x = 0; x < courses.size(); x++){
      transcript += courses.get(x).displayGrade() + "\n";
    }
    System.out.println(transcript);
  }
  

  //Prints all active courses this student is enrolled in
  public void printActiveCourses(){
    String output = "";
    for(int x =0; x<courses.size();x++){
      if(courses.get(x).getActive()){
        output += courses.get(x).displayGrade(); 
      }
    }
    System.out.println(output);
  }

  //Drops a course, remove only if course is active
  public void removeActiveCourse(String courseCode){
    for(int x= 0; x< courses.size();x++){
      if(courses.get(x).getCode().equals(courseCode)){
        if(courses.get(x).getActive()){
          courses.remove(x);
        }
      }
    }
  }
  
  @Override
  //Overides the to string methods
  public String toString()
  {
	  return "Student ID: " + id + " Name: " + name;
  }
  

  //Method checks if 2 student objects have the same id
  public boolean equals(Object other) 
  {
    Student otherStudent = (Student) other;
    
    if(this.id.equals(otherStudent.getId()) && this.name.toLowerCase().equals(otherStudent.getName().toLowerCase())){
      return true;
    }
    
	  return false;
  }

  //Method for comparator, used for sorting students by name
  public int compareTo(Student a){
    return (this.name.compareTo(a.name));
  }
}
