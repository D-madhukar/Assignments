import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//import javax.xml.*;

public class ImportEmployeeFromDatabase {
	public static final String SELECT_QUERY="select * from employee";
	public ArrayList<Employee> getEmployees() throws Exception{

		MysqlConnector mysqlconn=new MysqlConnector();
		Connection connection=mysqlconn.getConnection();
		Statement s=connection.createStatement();
		ResultSet rs =s.executeQuery(SELECT_QUERY);
		ArrayList<Employee> employees=new ArrayList<>();
		while(rs.next()) {
			Employee e=new Employee();
			e.setId(rs.getInt("id"));
			e.setName(rs.getString("name"));
			e.setSalary(rs.getFloat("salary"));
			e.setDept_id(rs.getInt("dept_id"));
			employees.add(e);
		}
		connection.close();
		return employees;
		
	}
	public void importData()throws Exception{
		ArrayList<Employee> employees =getEmployees();
		DocumentBuilderFactory f=DocumentBuilderFactory.newInstance(); 
		DocumentBuilder db=f.newDocumentBuilder();
		Document doc=db.newDocument();
		Element rootElement=doc.createElement("Employees");
		employees.stream()
				.forEach(e->{

					Element employeeElement=doc.createElement("Employee");
					Element idElement=doc.createElement("Id");
					Element nameElement=doc.createElement("Name");
					Element salaryElement=doc.createElement("Salary");
					Element deptidElement=doc.createElement("DeptId");
					
					Text id=doc.createTextNode(String.valueOf(e.getId()));
					Text name=doc.createTextNode(String.valueOf(e.getName()));
					Text salary=doc.createTextNode(String.valueOf(e.getSalary()));
					Text deptid=doc.createTextNode(String.valueOf(e.getDept_id()));
					
					idElement.appendChild(id);
					nameElement.appendChild(name);
					salaryElement.appendChild(salary);
					deptidElement.appendChild(deptid);
					
					employeeElement.appendChild(idElement);
					employeeElement.appendChild(nameElement);
					employeeElement.appendChild(salaryElement);
					employeeElement.appendChild(deptidElement);
					
					rootElement.appendChild(employeeElement);
				});
		doc.appendChild(rootElement);
		Transformer t=TransformerFactory.newInstance().newTransformer();
		t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream("C:\\\\Users\\\\Madhukar\\\\eclipse-workspace\\\\Jdbs_Project\\\\src\\\\Employeeoutput.xml")));
		System.out.println("XML created");
	}
	
}
