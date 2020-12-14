import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class Scheduler {
	
	private TreeMap<String, ActiveCourse> schedule;
	String[][] scheduleDisplay = new String[10][6];
	String[] weekdays = new String[] { "Mon", "Tue", "Wed", "Thu", "Fri" };

	public Scheduler(TreeMap<String, ActiveCourse> courses) {
		schedule = courses;

		// formatting grid
		for (int y = 1; y < 10; y++) {
			for (int x = 1; x < 6; x++) {
				scheduleDisplay[y][x] = " ";
			}
		}

		// Setting Days of the Week
		scheduleDisplay[0][0] = " ";
		scheduleDisplay[0][1] = "Mon";
		scheduleDisplay[0][2] = "Tue";
		scheduleDisplay[0][3] = "Wed";
		scheduleDisplay[0][4] = "Thu";
		scheduleDisplay[0][5] = "Fri";

		// Setting Hourse
		scheduleDisplay[1][0] = "0800";
		scheduleDisplay[2][0] = "0900";
		scheduleDisplay[3][0] = "1000";
		scheduleDisplay[4][0] = "1100";
		scheduleDisplay[5][0] = "1200";
		scheduleDisplay[6][0] = "1300";
		scheduleDisplay[7][0] = "1400";
		scheduleDisplay[8][0] = "1500";
		scheduleDisplay[9][0] = "1600";
	}

	public void setDayAndTime(String courseCode, String day, int startTime, int duration)
			throws UnknownCourseException {
		// Throwing Exceptions
		// --------------------------------------------------------------------------
		// Checks for valid courseCode
		if (!schedule.containsKey(courseCode.toUpperCase())) {
			throw new UnknownCourseException("Unknown course: " + courseCode);
		}

		// checks for valid weekday
		// iterates through array of weekdays
		Boolean isWeekDay = false;
		for (String x : weekdays) {
			// if day found
			if (x.toLowerCase().equals(day.toLowerCase())) {
				isWeekDay = true;
				break;
			}
		}

		// if day not found
		if (!isWeekDay) {
			throw new InvalidDayException("Invalid Lecture Day");
		}

		// Checks for valid time,
		// (1700-duration*100)-> check start time based on duration
		if (startTime < 800 || startTime > (1700 - duration * 100)) {
			throw new InvalidTimeException("Invalid Lecture Start Time");
		}

		// Checks for valid duration
		if (duration > 3 || duration < 1) {
			throw new InvalidDurationException("Invalid Lecture Duration");
		}

		// Setting New Values----------------------------------------------------
		ActiveCourse temp = schedule.get(courseCode.toUpperCase());

		// if course already on the grid, clear course from grid
		if (temp.getLectureStart() != 0) {
			for (int y = 0; y < scheduleDisplay.length; y++) {
				for (int x = 0; x < scheduleDisplay[y].length; x++) {
					if (scheduleDisplay[y][x].equalsIgnoreCase(courseCode)) {
						scheduleDisplay[y][x] = "";
					}
				}
			}
		}

		// set new values to the Active Course
		temp.setLectureDay(day);
		temp.setLectureStart(startTime);
		temp.setLectureDuration(duration);

		// Adding to grid----------------------------------------------------------

		// initalizing variables
		int row = 0;
		int col = 0;

		// finding index to add courses to 2d array

		// if theres a day set for course
		if (schedule.get(courseCode.toUpperCase()).getLectureDay() != "") {

			// calculate row 800 is earliest, + 1 to account for labels on first line
			row = ((startTime - 800) / 100) + 1;

			// finding col, iterate through days on the first row, stores col # if same days
			for (int x = 0; x < scheduleDisplay[0].length; x++) {
				if (day.toLowerCase().equals(scheduleDisplay[0][x].toLowerCase())) {
					col = x;

				}
			}
		}

		// Check for collision
		for (int index = 0; index < duration; index++) {
			if (scheduleDisplay[row + index][col] != " ") {
				throw new LectureTimeCollisionException("Lecture Time Collision");
			}
		}

		//
		for (int time = 0; time < duration; time++) {
			scheduleDisplay[row + time][col] = courseCode;
		}
	}

	public void clearSchedule(String courseCode) {

		// clears course from grid
		for (int row = 0; row < scheduleDisplay.length; row++) {
			for (int col = 0; col < scheduleDisplay[row].length; col++) {
				if (scheduleDisplay[row][col].equals(courseCode.toLowerCase())) {
					scheduleDisplay[row][col] = " ";
				}
			}
		}

		// resets values
		ActiveCourse temp = schedule.get(courseCode.toUpperCase());
		temp.setLectureDay("");
		temp.setLectureStart(0);
		temp.setLectureDuration(0);
	}

	public void printSchedule() {

		// printing grid
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print("\t" + scheduleDisplay[i][j]);
			}
			System.out.println("");
		}
		System.out.println("\n");
	}

	// ========================================================

	public class UnknownCourseException extends RuntimeException {
		public UnknownCourseException() {
		}

		public UnknownCourseException(String message) {
			super(message);
		}
	}

	public class InvalidDayException extends RuntimeException {
		public InvalidDayException() {
		}

		public InvalidDayException(String message) {
			super(message);
		}
	}

	public class InvalidTimeException extends RuntimeException {
		public InvalidTimeException() {
		}

		public InvalidTimeException(String message) {
			super(message);
		}
	}

	public class InvalidDurationException extends RuntimeException {
		public InvalidDurationException() {
		}

		public InvalidDurationException(String message) {
			super(message);
		}
	}

	public class LectureTimeCollisionException extends RuntimeException {
		public LectureTimeCollisionException() {
		}

		public LectureTimeCollisionException(String message) {
			super(message);
		}
	}
}