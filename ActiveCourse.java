import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ActiveCourse extends Course{

   //Defining Variables and ArrayList
   private ArrayList<Student> students;
   private String semester, description;
   private int lectureStart, lectureDuration;
   private String lectureDay = "";
   
   //Constructor method for active courses
   public ActiveCourse(String name, String code, String descr, String fmt, String semester,ArrayList<Student> students) {

      //Calls super and assign values to variables
      super(name, code, descr, fmt);
      this.semester = semester;
      this.students = new ArrayList<Student>(students);
      this.description = descr;
   }

   //Returns only description of class
   public String getOnlyDescription(){
      return description;
   }

   //Returns semester
   public String getSemester() {
      return semester;
   }

   // Prints the students in this course (name and student id)
   public void printClassList() {
      for (int x = 0; x < this.students.size(); x++) {
         System.out.println("Student ID: " + students.get(x).getId() + " Name: " + students.get(x).getName());
      }
   }

   //Prints the id name and grade of all students
   public void printGrades() {
      for (int x = 0; x < this.students.size(); x++) {
         System.out.println(students.get(x).getId() + " " + students.get(x).getName() + " " +  this.getGrade(students.get(x).getId()));
      }
   }
   

   //Find if student is in an active course
   public boolean findStudent(String studentId){
      for (int x = 0; x < this.students.size(); x++){
         if (this.students.get(x).getId().equals(studentId)){
            return true;
         }
      }
      return false;
   }

   //adds student to an active course
   public void addStudent(Student freshMeat){
      this.students.add(freshMeat);
   }

   //removes student from an active course
   public void removeStudent(String studentId){
      for (int x = 0; x < this.students.size(); x++){
         if (this.students.get(x).getId().equals(studentId)){
            this.students.remove(x);
         }
      }
   }



   // Returns the numeric grade in this course for this student, if student not in course return 0
   public double getGrade(String studentId) {

      //Finds correct student, 
      for (int x = 0; x<students.size();x++){
         if(students.get(x).getId().equals(studentId)){

            //Finds the correct active course, 
            for (int y = 0; y < students.get(x).courses.size();y++){
               if (students.get(x).courses.get(y).getCode().equals(getCode())){

                   //returns grade for class
                  return students.get(x).courses.get(y).getGradeNumeric();
               }
            }
         }
      }
      //if not found
      return 0;
   }

   @Override
   //overides description in course to add more info
   public String getDescription() {
      return super.getDescription() + " " + this.semester + " Enrolment: " + students.size();
   }

   //Sorts students in course by name, using private comparator class
   public void sortByName() {
      Collections.sort(students, new NameComparator());

   }

   //Private class implements comparator interface to compare 2 students' name
   private class NameComparator implements Comparator<Student>{
      public int compare(Student a, Student b){
         return a.getName().compareTo(b.getName());
      }
   }

  //Sorts students in course by ID, using private comparator class
   public void sortById() {
      Collections.sort(students,new IdComparator());
   }

   //Class implements comparator interface to compare 2 students' IDs
   private class IdComparator implements Comparator<Student>{
      public int compare(Student a, Student b){
         return Integer.parseInt(a.getId()) - Integer.parseInt(b.getId());
      }
   }

   // For Scheduler get and set methods
   public void setLectureDay(String day){
      lectureDay = day;
   }

   public void setLectureStart (int start){
      lectureStart = start;
   }

   public void setLectureDuration(int duration){
      lectureDuration = duration;
   }

   public String getLectureDay(){
      return lectureDay;
   }

   public int getLectureStart (){
      return lectureStart;
   }

   public int getLectureDuration(){
      return lectureDuration;
   }
}