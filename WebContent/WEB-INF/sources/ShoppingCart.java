
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

public class ShoppingCart extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html"); // Response mime type
		PrintWriter out = response.getWriter();

		Connection dbcon = null;
		Statement statement = null;
		
		try {

			Context initCtx = new InitialContext();
			
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			if (envCtx == null) out.println("envCtx is NULL");
			
			// Look up our data source
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

			if (ds == null) out.println("ds is null.");
			

			dbcon = ds.getConnection();

			if (dbcon == null) out.println("dbcon is null.");
			
			String movieId = request.getParameter("movieId");
			String quantity = request.getParameter("qty");
			String type = request.getParameter("type");
			
			statement = dbcon.createStatement();
			String password = request.getParameter("password");
			HttpSession session = request.getSession();
		
			HashMap<String, String> movieTitles = (HashMap<String, String>)session.getAttribute("movieTitles");
			HashMap<String, String> shoppingCart = (HashMap<String, String>)session.getAttribute("shoppingCart");
			
			if (type.equals("update")){
				
				try{
					int quantityVal = Integer.parseInt(quantity);
					
					if (quantityVal < 0){
						response.sendRedirect("shoppingCart.jsp?submit=fail");
					}
					else if (quantityVal == 0){
						shoppingCart.remove(movieId);
					}
					else{
						shoppingCart.put(movieId, quantity);
					}
				}
				catch (NumberFormatException ex){
					response.sendRedirect("shoppingCart.jsp?submit=fail");
				}
			}
			else if (type.equals("remove")){
				shoppingCart.remove(movieId);
				movieTitles.remove(movieId);
			}
			else if (type.equals("empty")){
				shoppingCart.clear();
			}
			else if (type.equals("checkout")){
				if (shoppingCart.size() > 0)
					response.sendRedirect("creditCardForm.jsp");
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("shoppingCart.jsp");
			rd.forward(request, response);

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
				if (out != null){
					out.close();
				}
			}
			catch (SQLException e){
				e.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
