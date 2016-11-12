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
import com.google.gson.Gson;

public class SearchMobile extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html"); // Response mime type
		PrintWriter out = response.getWriter();
		
		Connection dbcon = null;
		Statement statement = null;
		ResultSet rs = null;
		ResultSet findStars = null;
		PreparedStatement genreStat = null;
		PreparedStatement starStat = null;
		
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
			String Jtitle = request.getParameter("title");
			String Jyear = request.getParameter("year");
			String Jdirector = request.getParameter("director");
			String Jf_n = request.getParameter("f_n");
			String Jl_n = request.getParameter("l_n");
	        
	        if (Jtitle.equals("") && Jyear == null && Jdirector.equals("") && Jf_n.equals("") && Jl_n.equals("")){
	        	response.getWriter().write("Error");
	        }
	        
	        String orderQuery = "title ASC";
	        
			boolean cTitle = false, cYear = false, cDirector = false, cF_n = false, cL_n = false; 

			String query = "Select distinct * from movies ";
			if (!Jtitle.equals(""))
				cTitle = true;
			if (Jyear != null)
				cYear = true;
			if (!Jdirector.equals(""))
				cDirector = true;
			if (!Jf_n.equals(""))
				cF_n = true;
			if (!Jl_n.equals(""))
				cL_n = true;
			
			if(cTitle || cYear || cDirector || cF_n || cL_n){
				query += "Where id = Any (Select stars_in_movies.movie_id "
						+ "From movies, stars, stars_in_movies Where stars.id = stars_in_movies.star_id"
						+ " And movies.id = stars_in_movies.movie_id ";  
				if(cTitle)
					query += " And movies.title like\"%" + Jtitle + "%\"";
				if(cYear)
					query += " And movies.year =" + Jyear;
				if(cDirector)
					query += " And movies.director like \"%" + Jdirector + "%\"";
				if(cF_n)
					query += " And stars.first_name like \"%" + Jf_n + "%\"";
				if(cL_n)
					query += " And stars.last_name like \"%" + Jl_n + "%\"";
				
				query += ") " + "ORDER BY " + orderQuery + " LIMIT 15";
				
			}
			
			rs = statement.executeQuery(query);
			
			ArrayList<String> movies = new ArrayList<String>();
			
			while (rs.next()) {
				String title = rs.getString("title");
				movies.add(title);
			}
			
			for (String movie : movies){
				System.out.print(movie + " ");
			}
			
			if (movies.size() == 0){
				response.getWriter().write("Empty");
				return;
			}
			
			String json = new Gson().toJson(movies);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

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
				if (genreStat != null){
					genreStat.close();
				}
				if (starStat != null){
					starStat.close();
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
