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

public class MovieDetails extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html"); // Response mime type
		PrintWriter out = response.getWriter();

		Connection dbcon = null;
		Statement statement = null;
		Statement starStat = null;
		ResultSet rs = null;
		ResultSet starsRs = null;
		
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
			
			String movieTitle = request.getParameter("title");
			
			String query = "SELECT s.id, s.first_name, s.last_name, s.dob, s.photo_url, "
					+ "m.id AS movie_id, m.banner_url, m.year from stars s, stars_in_movies sm, movies m "
					+ "where s.id = sm.star_id and m.id = sm.movie_id and m.title = \"" + movieTitle + "\"";
			
			int movieId = 0;
			String bannerUrl = "";
			int year = 0;
			
			int starId;
			String starFN = "";
			String starLN = "";
			String starDob = "";
			String starPhoto = "";
			ArrayList<Star> stars = new ArrayList<Star>();
			
			rs = statement.executeQuery(query);
			while (rs.next()){
				if (movieId == 0){
					movieId = rs.getInt("movie_id");
				}
				if (bannerUrl == ""){
					bannerUrl = rs.getString("banner_url");
				}
				if (year == 0){
					year = rs.getInt("year");
				}
				starId = rs.getInt("id");
				starFN = rs.getString("first_name");
				starLN = rs.getString("last_name");
				starDob = rs.getString("dob");
				Star star = new Star(starId, starFN, starLN, starDob, starPhoto); 
				
				stars.add(star);
			}
			
			out.println("<div class='col-sm-8'>");
			out.println("<h4><strong><a href='singleMovie?movieId=" + movieId + "'>" + movieTitle + "</a></strong></h4>");
			out.println("<div>");
			out.println("<label>Year: " + year + "</label>");
			out.println("</div>");
			out.println("<div>");
			out.println("<label>Stars: </label>");
				
			for (int i = 0; i < stars.size(); i++){
				if (i == (stars.size()-1)){
					out.println("<span><a href='singleStar?id=" + stars.get(i).getId() + "'>" + stars.get(i).getFullName() + "</a></span>");
				}
				else{
					out.println("<span><a href='singleStar?id=" + stars.get(i).getId() + "'>" + stars.get(i).getFullName() + ", </a></span>");
				}
			}
			out.println("</div>");
			out.println("</div>");
			out.println("<div class='col-sm-4'>");
			out.println("<img height='150' width='100' src='" + bannerUrl + "'/>");
			out.println("</div>");
			
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
