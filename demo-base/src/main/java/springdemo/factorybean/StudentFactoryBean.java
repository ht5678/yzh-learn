package springdemo.factorybean;

import org.springframework.beans.factory.FactoryBean;

import springdemo.po.Student;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年12月10日 下午8:01:33  
 *
 */
public class StudentFactoryBean implements FactoryBean<Student> {

    private String studentInfo;

    @Override
    public Student getObject() throws Exception {
        if (this.studentInfo == null) {
            throw new IllegalArgumentException("'studentInfo' is required");
        }

        String[] splitStudentInfo = studentInfo.split(",");
        if (null == splitStudentInfo || splitStudentInfo.length != 3) {
            throw new IllegalArgumentException("'studentInfo' config error");
        }

        Student student = new Student();
        student.setName(splitStudentInfo[0]);
        student.setAge(Integer.valueOf(splitStudentInfo[1]));
        student.setClassName(splitStudentInfo[2]);
        return student;
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }

    public void setStudentInfo(String studentInfo) {
        this.studentInfo = studentInfo;
    }

	@Override
	public boolean isSingleton() {
		return false;
	}
}