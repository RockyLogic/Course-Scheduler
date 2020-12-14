import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;

public class Registry {

	// Defining TreeMaps
	private TreeMap<String, Student> students = new TreeMap<String, Student>();
	private TreeMap<String, ActiveCourse> courses = new TreeMap<String, ActiveCourse>();

	public Registry() throws IOException {

		// Defining Variables for importing students
		Student tempStudent;
		String[] studentInfo;

		// Attempts to read file and add student objects to students arrayList
		try {
			File file = new File("students.txt");
			Scanner sc1 = new Scanner(file);
			while (sc1.hasNextLine()) {

				// reads a single line and splits info into array
				studentInfo = sc1.nextLine().split(" ");

				for (int x = 0; x < studentInfo[0].length(); x++) {
					if (!Character.isLetter(studentInfo[0].charAt(x))) {
						throw new IOException();
					}
				}

				for (int y = 0; y < studentInfo[1].length(); y++) {
					if (!Character.isDigit(studentInfo[1].charAt(y))) {
						throw new IOException();
					}
				}

				// creates object student
				tempStudent = new Student(studentInfo[0], studentInfo[1]);

				// adds object student to arraylist
				students.put(studentInfo[1], tempStudent);

			}
			sc1.close();
		} catch (FileNotFoundException e) {
			System.out.println("student.txt File Not Found");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Bad File Format student.txt");
		} catch (IOException e) {
			System.out.println("IOException Caught");
		}

		// Defining Variables for importing courses
		String courseName, courseCode, descr, format;
		ActiveCourse tempCourse;
		ArrayList<Student> list = new ArrayList<Student>();

		// Attempts to read file and add activeCourses objects to courses arrayList
		try {
			File file = new File("courses.txt");
			Scanner sc2 = new Scanner(file);
			while (sc2.hasNextLine()) {

				// stores information on 4 lines
				courseName = sc2.nextLine();
				courseCode = sc2.nextLine();
				descr = sc2.nextLine();
				format = sc2.nextLine();

				// creates active course object
				tempCourse = new ActiveCourse(courseName, courseCode, descr, format, "W2020", list);

				// adds active course object to arraylist
				courses.put(courseCode, tempCourse);
			}
			sc2.close();
		} catch (FileNotFoundException e) {
			System.out.println("courses.txt File Not Found");
		} catch (IOException e) {
			System.out.println("IOException Caught");
		}

		// sort the students alphabetically - see class Student
		// Collections.sort(students);

		// Add Students to courses and courses to students
		addCourse("38467", "CPS209");
		addCourse("98345", "CPS209");
		addCourse("57643", "CPS209");
		addCourse("34562", "CPS511");
		addCourse("25347", "CPS511");
		addCourse("46532", "CPS511");
		addCourse("34562", "CPS643");
		addCourse("38467", "CPS643");
		addCourse("57643", "CPS643");
		addCourse("46532", "CPS643");
	}

	// Add new student to the registry (students Arraylist)
	public boolean addNewStudent(String name, String id) {

		// Checks if already registered
		if (students.get(id) != null)
			return false;

		// New student object created and added to students arraylist
		Student studentTemp = new Student(name, id);
		students.put(id, studentTemp);
		return true;
	}

	// Remove student from registry
	public boolean removeStudent(String id) {
		// Find student in students arraylist
		// If not found, return false
		if (students.get(id) == null)
			return false;

		// remove if found
		students.remove(id);
		return false;
	}

	// Print all info of all registered students
	public void printAllStudents() {

		Set<String> studentIDsList = students.keySet();

		for (String i : studentIDsList) {
			System.out.println("ID: " + students.get(i).getId() + " Name: " + students.get(i).getName());
		}

	}

	// Given a studentId and a course code, add student to the active course
	public void addCourse(String studentId, String courseCode) {
		Boolean takingCourse = false;

		Set<String> studentIDsList = students.keySet();
		// Finds students checks if hes taking course already
		for (String x : studentIDsList) {
			if (students.get(x).getId().equals(studentId)) {
				for (int y = 0; y < students.get(x).courses.size(); y++) {
					if (students.get(x).courses.get(y).getCode().equals(courseCode.toUpperCase())) {
						takingCourse = true;
						break; // If in already taking course break out of loop.
					}
				}

				// if not taking course
				if (!takingCourse) {
					Set<String> courseCodeList = courses.keySet();

					// Find active Course in arrayList
					for (String z : courseCodeList) {

						// if course matches and student is not enlisted.
						if (courses.get(z).getCode().equals(courseCode.toUpperCase())
								&& !(courses.get(z).findStudent(studentId))) {

							// adds student to courses list.
							courses.get(z).addStudent(students.get(x));

							// adds courses to students list.
							students.get(x).addCourse(courses.get(z).getName(), courses.get(z).getCode(),
									courses.get(z).getOnlyDescription(), courses.get(z).getFormat(),
									courses.get(z).getSemester(), 0);
						}
					}
				}
			}
		}
	}

	// Given a studentId and a course code, drop student from the active course
	public void dropCourse(String studentId, String courseCode) {

		Set<String> courseCodeList = courses.keySet();
		// Looks for activeCourse in courses
		for (String x : courseCodeList) {
			if (courses.get(x).getCode().equals(courseCode)) {

				// if student found remove student form course
				if (courses.get(x).findStudent(studentId)) {
					courses.get(x).removeStudent(studentId);

					// Looks for student, in student TreeMap, then its credit courses, removes the
					// course
					Set<String> studentIDsList = students.keySet();
					for (String y : studentIDsList) {
						if (students.get(y).getId().equals(studentId)) {
							for (int z = 0; z < students.get(y).courses.size(); z++) {
								if (students.get(y).courses.get(z).getCode().equals(courseCode)) {
									students.get(y).courses.remove(z);
								}
							}
						}
					}
				}
			}
		}
	}

	// Print the description of all active courses
	public void printActiveCourses() {
		Set<String> courseCodeList = courses.keySet();

		for (String i : courseCodeList) {
			System.out.println(courses.get(i).getDescription() + "\n");
		}
	}

	// Prints the list of students in an active course
	public void printClassList(String courseCode) {
		Set<String> courseCodeList = courses.keySet();
		if (!courses.containsKey(courseCode)) {
			System.out.println("Invalid Course Code Bimbo");
		}
		for (String x : courseCodeList) {
			if (courses.get(x).getCode().equals(courseCode)) {
				courses.get(x).printClassList();
			}
		}
	}

	// Sorts the students of a specific course by name
	public void sortCourseByName(String courseCode) {
		Set<String> courseCodeList = courses.keySet();

		for (String x : courseCodeList) {
			if (courses.get(x).getCode().equals(courseCode)) {
				courses.get(x).sortByName();
			}
		}
	}

	/// Sorts the students of a specific course by Id
	public void sortCourseById(String courseCode) {
		Set<String> courseCodeList = courses.keySet();

		for (String x : courseCodeList) {
			if (courses.get(x).getCode().equals(courseCode)) {
				courses.get(x).sortById();
			}
		}
	}

	// Takes a parameter of a course code, find course and print student names and
	// grade
	public void printGrades(String courseCode) {
		Set<String> courseCodeList = courses.keySet();

		for (String x : courseCodeList) {
			if (courses.get(x).getCode().equals(courseCode)) {
				courses.get(x).printGrades();
			}
		}
	}

	// Takes a parameter of a StudentID, and prints all the active courses
	public void printStudentCourses(String studentId) {
		Set<String> studentIDsList = students.keySet();

		for (String x : studentIDsList) {
			if (students.get(x).getId().equals(studentId)) {
				for (int y = 0; y < students.get(x).courses.size(); y++) {
					if (students.get(x).courses.get(y).getActive()) {
						System.out.println(students.get(x).courses.get(y).getDescription());
					}
				}
			}
		}
	}

	// Takes a studentId as parameter, print all courses and grades of student
	public void printStudentTranscript(String studentId) {
		Set<String> studentIDsList = students.keySet();

		for (String x : studentIDsList) {
			if (students.get(x).getId().equals(studentId)) {
				students.get(x).printTranscript();
			}
		}
	}

	// Sets a students final grade in class with coursecode to grade given as
	// parameter
	public void setFinalGrade(String courseCode, String studentId, double grade) {

		if (grade < 0 || grade > 100) {
			throw new IllegalArgumentException();
		}

		// finds the active course
		Set<String> courseCodeList = courses.keySet();
		for (String x : courseCodeList) {
			if (courses.get(x).getCode().equals(courseCode)) {

				// finds the student in active course
				Set<String> studentIDsList = students.keySet();
				for (String y : studentIDsList) {
					if (students.get(y).getId().equals(studentId)) {

						// Goes through all the credits courses that student has
						for (int z = 0; z < students.get(y).courses.size(); z++) {

							// if student is taking courses and the course is active
							if (students.get(y).courses.get(z).getCode().equals(courseCode)
									&& students.get(y).courses.get(z).getActive()) {

								// set the grade and turn course inactive
								students.get(y).courses.get(z).setFinalGrade(Double.toString(grade));
								students.get(y).courses.get(z).setInactive();
							}
						}
					}
				}
			}
		}
	}

	public TreeMap<String, ActiveCourse> getCourses() {
		return courses;
	}
}