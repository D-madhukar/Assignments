
public class Employee {
	private int id;
	private String name;
	private float salary;
	private int dept_id;
	public void setId(int id) {
		this.id=id;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setSalary(float salary) {
		this.salary=salary;
	}
	public void setDept_id(int dept_id) {
		this.dept_id=dept_id;
	}
	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public float getSalary() {
		return this.salary;
	}
	public int getDept_id() {
		return this.dept_id;
	}
}
