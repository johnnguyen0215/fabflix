import java.io.IOException; 
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.Movie;
import com.Star;

public class Suggestions extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html"); // Response mime type
		PrintWriter out = response.getWriter();

		Connection dbcon = null;
		Statement statement = null;
		ResultSet rs = null;		
		
		try {

			Context initCtx = new InitialContext();
			
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			if (envCtx == null) out.println("envCtx is NULL");
			
			// Look up our data source
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

			if (ds == null) out.println("ds is null.");
			
			 dbcon = ds.getConnection();

			if (dbcon == null) out.println("dbcon is null.");

			statement = dbcon.createStatement();
			
			String searchQuery = request.getParameter("q");
			
			String[] keywordsSplit = searchQuery.split(" ");
			
			String keywordsSql = "MATCH(title) AGAINST(\"";
			String prefixSql = "MATCH(title) AGAINST(\"";
			for (int i = 0; i < keywordsSplit.length; i++){
				if (i == keywordsSplit.length-1){
					prefixSql += keywordsSplit[i] + "*\" IN BOOLEAN MODE)";
				}
				else{
					if(i == keywordsSplit.length-2){
						keywordsSql += keywordsSplit[i];
					}
					else{
						keywordsSql += keywordsSplit[i] + " ";
					}
				}
			}
			
			keywordsSql += "\") AND ";
			
			String sqlQuery = "SELECT * FROM movies WHERE ";
			
			if (keywordsSplit.length > 1){
				sqlQuery += (keywordsSql + prefixSql);
			}
			else{
				sqlQuery += prefixSql;
			}
			
			
			rs = statement.executeQuery(sqlQuery);
			
			while (rs.next()){
				String movieTitle = rs.getString("title");
				String htmlTitle = "<li>" + movieTitle + "</li>";
				out.println(htmlTitle);
			}
			
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			} // end while
		} // end catch SQLException

		catch (java.lang.Exception ex) {
			out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error"
					+ "</TITLE></HEAD>\n<BODY>" + "<P>SQL error in doGet: "
					+ ex.getMessage() + "</P></BODY></HTML>");
			return;
		}
		finally{
			try{
				if (dbcon != null){
					dbcon.close();
				}
				if (statement != null){
					statement.close();
				}
				if (rs != null){
					rs.close();
				}
				if (out != null){
					out.close();
				}
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
}
