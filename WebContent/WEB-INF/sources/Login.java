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


public class Login extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html"); // Response mime type
		PrintWriter out = response.getWriter();
		
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
		if(!valid){
			response.sendRedirect("login.jsp?recaptcha=fail"); 
			return;
		}
		
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
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String query = "SELECT * from customers where email = '" + email
					+ "' and password = '" + password + "'";
			statement.executeQuery(query);
			rs = statement.getResultSet();

			if (!rs.next()) {
				response.sendRedirect("login.jsp?submit=fail");
			} else {
				int customerId = rs.getInt("id");
				HttpSession session = request.getSession();
				HashMap<String, String> movieTitles = new HashMap<String, String>();
				HashMap<String, String> shoppingCart = new HashMap<String, String>();
				session.setAttribute("customerId", customerId);
				session.setAttribute("movieTitles",  movieTitles);
				session.setMaxInactiveInterval(30 * 60);
				session.setAttribute("shoppingCart", shoppingCart);
				response.sendRedirect("main.jsp");
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
