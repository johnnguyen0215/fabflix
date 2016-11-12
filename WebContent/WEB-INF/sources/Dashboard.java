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


public class Dashboard extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html"); // Response mime type
		PrintWriter out = response.getWriter();
		
		Connection dbcon = null;
		Statement insertStatement = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		try {


			Context initCtx = new InitialContext();
			
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			if (envCtx == null) out.println("envCtx is NULL");
			
			// Look up our data source
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

			if (ds == null) out.println("ds is null.");
			

			dbcon = ds.getConnection();

			if (dbcon == null) out.println("dbcon is null.");
			
			
			String type = request.getParameter("type");
			
			if (type.equals("addStar")){
				boolean emptyDob = true; 
				String name = request.getParameter("name");
				String yy = request.getParameter("year");
				String mm = request.getParameter("month");
				String dd = request.getParameter("day");
				String dob = null;
			
				if (name == null || name.equals("")){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
	
				String photo_url = request.getParameter("photo_url");
				String[] splitName = name.split(" ");
				String f_n = "", l_n = "";
				if (splitName.length == 1){
					l_n = splitName[0];
				}
				else if (splitName.length > 1){
					f_n = splitName[0];
					for (int i = 1; i< splitName.length; i++){
						if (i == splitName.length-1)
							l_n += splitName[i];
						else
							l_n += splitName[i] + " ";
					}
				}
				
				if(yy != null && mm != null && dd != null){
					dob = yy + "/" + mm + "/" + dd; 
					emptyDob = false;
				}
				else if (yy != null && mm == null && dd == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				else if (yy != null && mm != null && dd == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				else if (yy != null && mm == null && dd != null){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				else if (mm != null && yy == null && dd == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				else if (mm != null && yy != null && dd == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				else if (mm != null && yy == null && dd != null){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				else if (dd != null && mm == null && yy == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				else if (dd != null && mm != null && yy == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				else if (dd != null && mm == null && yy != null){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				
				try{
					insertStatement = dbcon.createStatement();
					String sql = "";
					if (emptyDob){
						sql = String.format("INSERT INTO stars (first_name, last_name, "
								+ "photo_url) VALUES('%s', '%s', '%s')", f_n, l_n, photo_url);
					}
					else {
						sql = String.format("INSERT INTO stars (first_name, last_name, "
							+ "dob, photo_url) VALUES('%s', '%s', '%s', '%s')", f_n, l_n, dob, photo_url);
					}
					insertStatement.executeUpdate(sql);
					insertStatement.close();
					response.sendRedirect("dashboard.jsp?success=addStar");
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
			else if (type.equals("addMovie")){
				String title = request.getParameter("title");
				String movieYear = request.getParameter("movie_year");
				String director = request.getParameter("director");
				String bannerUrl = request.getParameter("banner_url");
				String trailerUrl = request.getParameter("trailer_url");
				String firstName = request.getParameter("f_n");
				String lastName = request.getParameter("l_n");
				String dobMonth = request.getParameter("month");
				String dobDay = request.getParameter("day");
				String dobYear = request.getParameter("year");
				String photoUrl = request.getParameter("photo_url");
				String genreName = request.getParameter("genre_name");
				
				if (title == null || movieYear == null || director == null || firstName == null
						|| lastName == null || genreName == null || director.equals("") || firstName.equals("")  || lastName.equals("") 
						|| title.equals("")){
					response.sendRedirect("dashboard.jsp?submit=fail");
					return;
				}
				System.out.println("First Name:" + firstName);
				System.out.println("Last Name:" + lastName);
				System.out.println("Director:" + lastName);
				
				java.sql.Date dob = null;
				if (dobYear != null && dobMonth != null && dobDay != null){
					dob =  java.sql.Date.valueOf(dobYear + "-" + dobMonth + "-" + dobDay);
				}
				else if (dobYear != null && dobMonth == null && dobDay == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
				}
				else if (dobYear != null && dobMonth != null && dobDay == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
				}
				else if (dobYear != null && dobMonth == null && dobDay != null){
					response.sendRedirect("dashboard.jsp?submit=fail");
				}
				else if (dobMonth != null && dobYear == null && dobDay == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
				}
				else if (dobMonth != null && dobYear != null && dobDay == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
				}
				else if (dobMonth != null && dobYear == null && dobDay != null){
					response.sendRedirect("dashboard.jsp?submit=fail");
				}
				else if (dobDay != null && dobMonth == null && dobYear == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
				}
				else if (dobDay != null && dobMonth != null && dobYear == null){
					response.sendRedirect("dashboard.jsp?submit=fail");
				}
				else if (dobDay != null && dobMonth == null && dobYear != null){
					response.sendRedirect("dashboard.jsp?submit=fail");
				}
				
				String addMovieCall = "{call add_movie(?,?,?,?,?,?,?,?,?,?)}";
				cs = null;
				try{
					System.out.println("Adding movie");
					cs = dbcon.prepareCall(addMovieCall);
					cs.setString(1, title);
					cs.setInt(2, Integer.valueOf(movieYear));
					cs.setString(3, director);
					cs.setString(4, bannerUrl);
					cs.setString(5, trailerUrl);
					cs.setString(6, firstName);
					cs.setString(7, lastName);
					cs.setDate(8, dob);
					cs.setString(9, photoUrl);
					cs.setString(10, genreName);
					cs.executeUpdate();
					
					
					response.sendRedirect("dashboard.jsp?success=addMovie");
				}
				catch (SQLException e){
					System.out.println(e.getMessage());
				}
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
				if (insertStatement != null){
					insertStatement.close();
				}
				if (rs != null){
					rs.close();
				}
				if (cs != null){
					cs.close();
				}
				if (out != null){
					out.close();
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
}
