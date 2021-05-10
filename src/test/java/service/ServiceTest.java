package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.*;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

	private static Service service;

	@BeforeAll
	public static void setUp() {
		Validator<Student> studentValidator = new StudentValidator();
		Validator<Homework> homeworkValidator = new HomeworkValidator();
		Validator<Grade> gradeValidator = new GradeValidator();

		StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
		HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
		GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

		service = new Service(fileRepository1, fileRepository2, fileRepository3);
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void findAllStudents() {
	}

	@Test
	void findAllHomework() {
	}

	@Test
	void findAllGrades() {
	}

	@Test
	@DisplayName("Student valid add")
	void saveStudent() {
		Student st = new Student("100","Sara Smith",200);
		int result = service.saveStudent(st.getID(),st.getName(),st.getGroup());
		Assertions.assertTrue(result==0);
		service.deleteStudent(st.getID());

	}

	@Test
	@DisplayName("Homework valid add")
	void saveHomework() {
		Homework hw = new Homework("12","sdbck", 6,1);
		int result = service.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());
		//Assertions.assertTrue(result == 0);
		Assertions.assertEquals(0,result);
		service.deleteHomework(hw.getID());
	}

	@Test
	void saveGrade() {
	}

	@Test
	@DisplayName("Student validation delete")
	void deleteStudent() {
		Student st = new Student("100","Sara Smith",200);
		service.saveStudent(st.getID(),st.getName(),st.getGroup());
		int result = service.deleteStudent(st.getID());
		Assertions.assertTrue(result==1);
	}

	@Test
	@DisplayName("Homework validation delete")
	void deleteHomework() {
		Homework hw = new Homework("13","sdbck", 2,1);
		service.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());
		int result = service.deleteHomework(hw.getID());
		Assertions.assertTrue(result == 1);


	}

	@Test
	@DisplayName("Student valid update")
	void updateStudent() {
		Student st = new Student("100","Sara Smith",200);
		service.saveStudent(st.getID(),st.getName(),st.getGroup());
		int result = service.updateStudent(st.getID(),"Sara Lane",290);
		Assertions.assertTrue(result==1);

	}

	@Test
	@DisplayName("Homework valid update")
	void updateHomework() {
		Homework hw = new Homework("13","sdbck", 2,1);
		service.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());
		int result = service.updateHomework(hw.getID(),"easy homework",10,1);
		Assertions.assertAll(()->assertTrue(result==1));
	}

	@Test
	void extendDeadline() {
		Homework hw = new Homework("19","sdbck", 33,1);
		service.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());
		int result = service.extendDeadline("19", 3);
		Assertions.assertTrue(result==1);
		service.deleteHomework("19");
	}

	@Test
	void extendDeadline2() {
		Homework hw = new Homework("20","sdbck", 8,1);
		service.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());
		int result = service.extendDeadline("20", 3);
		Assertions.assertTrue(result==1);
		service.deleteHomework("20");
	}

	@Test
	void createStudentFile() {
		Student st = new Student("100","Sara Smith",200);
		service.saveStudent(st.getID(),st.getName(),st.getGroup());
		Homework hw = new Homework("13","sdbck", 2,1);
		service.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());
		Assertions.assertThrows(NullPointerException.class, ()->service.createStudentFile(st.getID(),hw.getID()));
		service.deleteStudent(st.getID());
		service.deleteHomework(hw.getID());
	}
}