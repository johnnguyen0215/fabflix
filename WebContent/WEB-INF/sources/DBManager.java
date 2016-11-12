

import java.sql.*;  

public class DBManager {
	Connection connection;
	
	public DBManager(Connection conn){
		connection = conn;
	}
	
	public void printResults(ResultSet result){
		try{
			
			ResultSetMetaData metadata = result.getMetaData();
			if(!result.next()){
				System.out.println("No records returned.");
				System.out.println("");
			}
			else {
				result.beforeFirst();
				while (result.next())
		        {
					
					for (int i = 1; i <= metadata.getColumnCount(); i++){
						int type = metadata.getColumnType(i);
						if (type == Types.VARCHAR || type == Types.CHAR){
							System.out.printf("%s = %s\n", metadata.getColumnName(i), result.getString(i));
						}
						else{
							System.out.printf("%s = %s\n", metadata.getColumnName(i), result.getLong(i));
						}
					}
					System.out.println();
		        }
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void queryFirstName(String firstName){
		try{
			Statement select = connection.createStatement();
			String sql = String.format("SELECT * FROM stars WHERE first_name = '%s'", firstName);
			ResultSet result = select.executeQuery(sql);
			
            printResults(result);
	       
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void queryLastName(String lastName){
		try{
			Statement select = connection.createStatement();
			String sql = String.format("SELECT * FROM stars WHERE last_name = '%s'", lastName);
			ResultSet result = select.executeQuery(sql);
			
			
            printResults(result);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void queryFullName(String firstName, String lastName){
		try{
			Statement select = connection.createStatement();
			String sql = String.format("SELECT * FROM stars WHERE first_name = '%s'", firstName);
			ResultSet result = select.executeQuery(sql);
			
            printResults(result);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void queryId(int id){
		try{
			Statement select = connection.createStatement();
			String sql = String.format("SELECT * FROM stars WHERE id = '%d'", id);
			ResultSet result = select.executeQuery(sql);
			
            printResults(result);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void insertStar(String firstName, String lastName, String dob, String url){
		try{
			Statement insertStatement = connection.createStatement();
			String sql = String.format("INSERT INTO stars (first_name, last_name, "
					+ "dob, photo_url) VALUES('%s', '%s', '%s', '%s')", firstName, lastName, dob, url);
			insertStatement.executeUpdate(sql);
			System.out.println("Successfully inserted the star.");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void insertCreditCardInfo(String firstName, String lastName, String cc, String ccExp){
		try{
			Statement insertStatement = connection.createStatement();
			String sql = String.format("INSERT INTO creditcards (id, first_name, last_name, expiration) "
					+ "VALUES('%s', '%s','%s', '%s')", cc, firstName, lastName, ccExp);
			insertStatement.executeUpdate(sql);
			System.out.println("Successfully inputted credit card information.");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void insertCustomer(String firstName, String lastName, String cc, String address, String email, String password){
		try{
			Statement insertStatement = connection.createStatement();
			String sql = String.format("SELECT * FROM creditcards WHERE "
					+ "id = '%s'", cc);
			//		+ "id = '%s' AND first_name = '%s' AND last_name = '%s'", cc, firstName, lastName);
			ResultSet result = insertStatement.executeQuery(sql);
			if (!result.next()){
				System.out.println("Customer's credit card info was not found.");
			}
			else{
				String insertSql = String.format("INSERT INTO customers (first_name, last_name, "
						+ "cc_id, address, email, password) VALUES('%s', '%s', '%s', '%s', '%s', '%s')",
						firstName, lastName, cc, address, email, password);
				insertStatement.executeUpdate(insertSql);
				System.out.println("Successfully inserted the customer.");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteCustomerById(int id){
		try{
			Statement deleteStatement = connection.createStatement();
			String sql = String.format("DELETE FROM customers WHERE id = '%d'", id);
			int retID = deleteStatement.executeUpdate(sql);
			if (retID == 1){
				System.out.println("Successfully deleted the customer.");
			}
			else if (retID == 0){
				System.out.println("The delete operation had no effect.");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteCustomerByName(String firstName, String lastName){
		try{
			Statement deleteStatement = connection.createStatement();
			String sql = String.format("DELETE FROM customers WHERE first_name = '%s' and last_name = '%s'", firstName, lastName);
			int retID = deleteStatement.executeUpdate(sql);
			if (retID == 1){
				System.out.println("Successfully deleted the customer.");
			}
			else if (retID == 0){
				System.out.println("The delete operation had no effect.");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public String printMD(ResultSetMetaData metadata){
		String result = "";
		try{
			result = "<p>";
			result += "Table Name: " + metadata.getTableName(1);
			result += "</p>";
			for (int i = 1; i <= metadata.getColumnCount(); i++){
				result += "<p>";
				result += String.format("Column %d; Attribute Name: %s; Type: %s\n",i, metadata.getColumnName(i), metadata.getColumnTypeName(i));
				result += "</p>";
			}
			result += "<p></p>";
			result += "<hr>"; 
			result += "<p></p>";
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public String showCustomersMD(){
		String r = "";
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * from customers");
			ResultSetMetaData metadata = result.getMetaData();
			r = printMD(metadata);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public String showCreditCardsMD(){
		String r = "";
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * from creditcards");
			ResultSetMetaData metadata = result.getMetaData();
			r = printMD(metadata);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public String showMoviesMD(){
		String r = "";
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * from movies");
			ResultSetMetaData metadata = result.getMetaData();
			r = printMD(metadata);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public String showGenresMD(){
		String r = "";
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * from genres");
			ResultSetMetaData metadata = result.getMetaData();
			r = printMD(metadata);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public String showGenresInMoviesMD(){
		String r = "";
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * from genres_in_movies");
			ResultSetMetaData metadata = result.getMetaData();
			r = printMD(metadata);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	};
	
	public String showSalesMD(){
		String r = "";
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * from sales");
			ResultSetMetaData metadata = result.getMetaData();
			r = printMD(metadata);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public String showStarsMD(){
		String r = "";
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * from stars");
			ResultSetMetaData metadata = result.getMetaData();
			r = printMD(metadata);
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public String showStarsInMoviesMD(){
		String r = "";
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * from stars_in_movies");
			ResultSetMetaData metadata = result.getMetaData();
			r = printMD(metadata);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public String showEmployeesMD(){
		String r = "";
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT * from employees");
			ResultSetMetaData metadata = result.getMetaData();
			r = printMD(metadata);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	
	public String showDatabaseMD(){
		String r = "";
		try{
			r += showCustomersMD();
			r += showCreditCardsMD();
			r += showMoviesMD();
			r += showGenresMD();
			r += showGenresInMoviesMD();
			r += showSalesMD();
			r += showStarsMD();
			r += showStarsInMoviesMD();
			r += showEmployeesMD();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return r;
	}
	
	public void sqlSelect(String statement){
		try{
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery(statement);
			if (!result.next()){
				System.out.println("No results were found.");
			}
			else{
				result.beforeFirst();
				printResults(result);
			}
		}
		catch (Exception e){
			//e.printStackTrace();
			System.out.println("Invalid SQL query.");
			System.out.println(""); 
		}
	}
	
	public void sqlUpdate(String statement){
		try{
			Statement update = connection.createStatement();
			int retId = update.executeUpdate(statement);
			int updateCount = update.getUpdateCount();
			if (retId == 0){
				System.out.println("The update statement had no affect.");
			}
			else{
				System.out.printf("%d row(s) were affected.\n", updateCount);
			}
		}
		catch(Exception e){
			//e.printStackTrace();
			System.out.println("Invalid SQL query.");
			System.out.println("");
		}
	}
	
	public void sqlInsert(String statement){
		try{
			Statement insert = connection.createStatement();
			int retId = insert.executeUpdate(statement);
			int updateCount = insert.getUpdateCount();
			if (retId == 0){
				System.out.println("The insert statement had no affect.");
			}
			else{
				System.out.printf("%d row(s) were affected.\n", updateCount);
			}
		}
		catch (Exception e){
			//e.printStackTrace();
			System.out.println("Invalid SQL query.");
			System.out.println("");
		}
	}
	
	public void sqlDelete(String statement){
		try{
			Statement delete = connection.createStatement();
			int retId = delete.executeUpdate(statement);
			int updateCount = delete.getUpdateCount();
			if (retId == 0){
				System.out.println("The delete statement had no affect.");
			}
			else{
				System.out.printf("%d row(s) were affected.\n", updateCount);
			}
		}
		catch (Exception e){
			//e.printStackTrace();
			System.out.println("Invalid SQL query.");
			System.out.println("");
		}
	}
	
	
	
	
}
