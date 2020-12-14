import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StudentRegistrySimulator {
	public static void main(String[] args) {

		// Creating registry and scanner object
		Registry registry = null;
		Scheduler scheduler = null;

		try {
			registry = new Registry();
			scheduler = new Scheduler(registry.getCourses());
		} catch (IOException e) {
			System.out.println("Bad File Format student.txt");
		}

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// defining course variables
		String name, id, courseCode, grade, lectureDay;
		int lectureStart, lectureDuration;

		// Reads Commands entered by user
		while (scanner.hasNextLine()) {

			// stores input line
			String inputLine = scanner.nextLine();

			if (inputLine == null || inputLine.equals(""))
				continue;

			// new scanner object for input line
			Scanner commandLine = new Scanner(inputLine);

			// stores command
			String command = commandLine.next();

			// Nothing entered
			if (command == null || command.equals(""))
				continue;

			// prints out all students if 'L'
			else if (command.equalsIgnoreCase("L") || command.equalsIgnoreCase("LIST")) {
				registry.printAllStudents();

				// exits if 'q'
			} else if (command.equalsIgnoreCase("Q") || command.equalsIgnoreCase("QUIT")) {
				return;
			}

			// If command is 'Reg' registers a new student
			else if (command.equalsIgnoreCase("REG")) {
				try {

					// stores info for student
					name = commandLine.next();
					id = commandLine.next();

					// checks validity of name and id
					if (!(isStringOnlyAlphabet(name))) {
						System.out.println("Invalid Characters in Name " + name);
					} else {
						if (!(isNumeric(id))) {
							System.out.println("Invalid Characters in ID " + id);
						} else {
							// adds new student if valid
							registry.addNewStudent(name, id);
						}
					}

					// if invalid
				} catch (IllegalArgumentException e) {

					// catches missing parameters error
				} catch (Exception e) {
					System.out.println("Please double check parameters command:'reg name id'");
				}

				// if command is 'del' deletes student from registry
			} else if (command.equalsIgnoreCase("DEL")) {

				// takes in student id to remove
				id = commandLine.next();

				// checks validity
				if (!(isNumeric(id))) {
					System.out.println("Invalid Characters in ID " + id);
				} else {
					// if valid removes student
					registry.removeStudent(id);
				}
			}

			// if command is 'addc' adds student to a course
			else if (command.equalsIgnoreCase("ADDC")) {

				// takes and records parameters
				id = commandLine.next();
				courseCode = commandLine.next();

				// adds student to course
				registry.addCourse(id, courseCode);

				// if command is 'dropc' removes student from a course
			} else if (command.equalsIgnoreCase("DROPC")) {

				// takes and records parameters
				id = commandLine.next();
				courseCode = commandLine.next();

				// checks validity
				if (!(isNumeric(id))) {
					System.out.println("Invalid Characters in ID " + id);
				} else {
					// removes student from course
					registry.dropCourse(id, courseCode.toUpperCase());
				}

				// if command is "pac" prints details of active courses
			} else if (command.equalsIgnoreCase("PAC")) {
				registry.printActiveCourses();

				// if command is 'pcl' prints class list for a specific course
			} else if (command.equalsIgnoreCase("PCL")) {
				courseCode = commandLine.next();
				registry.printClassList(courseCode.toUpperCase());

				// if command is 'pgr' print grades for a specific course
			} else if (command.equalsIgnoreCase("PGR")) {
				courseCode = commandLine.next();
				registry.printGrades(courseCode.toUpperCase());

				// if command is 'psc' print courses of a specific student
			} else if (command.equalsIgnoreCase("PSC")) {
				id = commandLine.next();

				// checks validity
				if (!(isNumeric(id))) {
					System.out.println("Invalid Characters in ID " + id);
				} else {
					registry.printStudentCourses(id);
				}

				// if command is 'pst' print the student transcript of a specific student
			} else if (command.equalsIgnoreCase("PST")) {
				id = commandLine.next();

				// Checks validity
				if (!(isNumeric(id))) {
					System.out.println("Invalid Characters in ID " + id);
				} else {
					registry.printStudentTranscript(id);
				}

				// if command is 'sfg' set final grade for a specific student in a specific
				// course, to the inputted value
			} else if (command.equalsIgnoreCase("SFG")) {
				courseCode = commandLine.next();
				id = commandLine.next();
				grade = commandLine.next();

				// Checks validity
				try {
					if (!(isNumeric(id))) {
						System.out.println("Invalid Characters in ID " + id);
					} else {
						registry.setFinalGrade(courseCode.toUpperCase(), id, Double.parseDouble(grade));
					}

					// if invalid grade entered
				} catch (IllegalArgumentException e) {
					System.out.println("Please enter a grade between 0 - 100.");
				}

				// if command is 'scn' sort students in a specific course by name
			} else if (command.equalsIgnoreCase("SCN")) {
				courseCode = commandLine.next();
				registry.sortCourseByName(courseCode.toUpperCase());

				// if command is 'sci' sort students in a specific course by id
			} else if (command.equalsIgnoreCase("SCI")) {
				courseCode = commandLine.next();
				registry.sortCourseById(courseCode.toUpperCase());

				// if command is 'sch' takes paramenters and calls scheduler function to set
				// info
			} else if (command.equalsIgnoreCase("SCH")) {

				try {
					courseCode = commandLine.next();
					lectureDay = commandLine.next();
					lectureStart = Integer.parseInt(commandLine.next());
					lectureDuration = Integer.parseInt(commandLine.next());

					scheduler.setDayAndTime(courseCode, lectureDay, lectureStart, lectureDuration);
					
				} catch (RuntimeException e) {
					System.out.println(e.getMessage());
				}

				// if command is 'csch' takes course code parameter and call scheduler to clear
				// course
			} else if (command.equalsIgnoreCase("CSCH")) {
				courseCode = commandLine.next();
				scheduler.clearSchedule(courseCode);

				// if command is 'psch' prints out schedule
			} else if (command.equalsIgnoreCase("PSCH")) {

				scheduler.printSchedule();
			}

			System.out.print("\n>");

		}
	}

	// checks if string is only letters
	private static boolean isStringOnlyAlphabet(String str) {
		for (int y = 0; y < str.length(); y++) {
			if (!(Character.isLetter(str.charAt(y)))) {
				return false;
			}
		}
		return true;
	}

	// checks if string is only numbers
	public static boolean isNumeric(String str) {
		for (int x = 0; x < str.length(); x++) {
			if (!(Character.isDigit(str.charAt(x)))) {
				return false;
			}
		}
		return true;
	}

}