package springdemo.po;



/**
 * 
 * @author yuezh2
 *
 * @date 2020年12月10日 下午8:00:50  
 *
 */
public class Student {
	/** 姓名 */
    private String name;
    /** 年龄 */
    private int age;
    /** 班级名称 */
    private String className;

    public Student() {
    }

    public Student(String name, int age, String className) {
        this.name = name;
        this.age = age;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + name + '\'' + ", age=" + age + ", className='" + className + '\'' + '}';
    }
}
