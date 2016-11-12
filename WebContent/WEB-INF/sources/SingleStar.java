import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

public class SingleStar extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html"); // Response mime type
		PrintWriter out = response.getWriter();
		
		Connection dbcon = null;
		Statement statement = null;
		ResultSet rs = null;
		Statement starStat = null;
		ResultSet findMovies = null;
		
		try {

			Context initCtx = new InitialContext();
			
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			if (envCtx == null) out.println("envCtx is NULL");
			
			// Look up our data source
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

			if (ds == null) out.println("ds is null.");
			

			dbcon = ds.getConnection();

			if (dbcon == null) out.println("dbcon is null.");
			
			String jId = request.getParameter("id");
			statement = dbcon.createStatement();
			String query = "Select * from stars where id = " + jId; 
			rs = statement.executeQuery(query);
			if(rs.next()){
				int id = rs.getInt("id");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String dob = rs.getString("dob");
				String photo_url = rs.getString("photo_url");
				
				if (photo_url == null){
					photo_url = "";
				}
				
				starStat = dbcon.createStatement();
				String movieQuery = "Select movies.id, movies.title, stars.first_name, stars.last_name from stars, stars_in_movies, movies where movies.id = stars_in_movies.movie_id AND stars.id = stars_in_movies.star_id and stars.id = "
						+ jId + ";";
				findMovies = starStat.executeQuery(movieQuery);

				ArrayList<Movie> starsIn = new ArrayList<Movie>();
				try {
					while (findMovies.next()) {
						Movie oneMovie = new Movie(findMovies.getInt("id"), findMovies.getString("title"));
						starsIn.add(oneMovie);
					}
				} catch (Exception e) {
					starsIn.clear();
				}
				
				Star star = new Star(id, first_name, last_name, dob, photo_url);
				request.setAttribute("star", star);
				request.setAttribute("starsIn", starsIn);
				request.setAttribute("error", false);
			}
			else {
				request.setAttribute("error", true);
			}
						
	        RequestDispatcher rd = request.getRequestDispatcher("singleStar.jsp");
	        rd.forward(request,response);
		

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
				if (findMovies != null){
					findMovies.close();
				}
				if (starStat != null){
					starStat.close();
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
