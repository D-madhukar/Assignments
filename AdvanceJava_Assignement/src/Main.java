public class Main {

	public static void main(String[] args)throws Exception {
		//Inserting data into database from EmployeeInput.xml
		new ExportEmployeeToDatabase().export();
		
		//data from database to Employeeoutput.xml
		new ImportEmployeeFromDatabase().importData();
	}

}
