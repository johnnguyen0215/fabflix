/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;


public class CustomerInfo extends HttpServlet {

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

			String Jf_n = request.getParameter("first_name");
			String Jl_n = request.getParameter("last_name");
			String Jcc_n = request.getParameter("cc_n");
			String Jyy = request.getParameter("year");
			String Jmm = request.getParameter("month");
			String Jdd = request.getParameter("day");

			String query = "SELECT * from creditcards where first_name = '"
					+ Jf_n + "' and last_name = '" + Jl_n + "' and id = '"
					+ Jcc_n + "' and expiration = '" + Jyy + "/" + Jmm + "/"
					+ Jdd + "'";

			statement.executeQuery(query);
			rs = statement.getResultSet();

			if (!rs.next()) {

				response.sendRedirect("creditCardForm.jsp?submit=fail");
			} else {
				HttpSession session = request.getSession();
				HashMap<String, String> shoppingCart = (HashMap<String, String>) session.getAttribute("shoppingCart");
				int customerId = (int) session.getAttribute("customerId");

				Statement insertStatement = dbcon.createStatement();

				String insertQuery = "INSERT INTO sales (customer_id, movie_id, sale_date) VALUES ";

				java.sql.Date date = new java.sql.Date(Calendar.getInstance()
						.getTime().getTime());

				for (String id : shoppingCart.keySet()) {
					int qty = Integer.valueOf(shoppingCart.get(id));
					for (int i = 0; i < qty; i++) {
						insertQuery += "('" + customerId + "', '" + id + "', '"
								+ date + "'), ";
					}
				}

				insertQuery = insertQuery
						.substring(0, insertQuery.length() - 2);

				insertStatement.executeUpdate(insertQuery);

				shoppingCart.clear();

				response.sendRedirect("confirmation.jsp");
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
			catch( SQLException e){
				e.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
}
