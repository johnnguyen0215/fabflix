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

public class SingleMovie extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html"); // Response mime type
		PrintWriter out = response.getWriter();
		
		Connection dbcon = null;
		Statement statement = null;
		ResultSet rs = null;
		Statement starStat = null;
		
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
			String movieId = request.getParameter("movieId");
			
	        String query = "SELECT * from movies Where id = " + movieId;
			
			rs = statement.executeQuery(query);
			
			if (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				int year = rs.getInt("year");
				String director = rs.getString("director");
				String bannerUrl = rs.getString("banner_url");
				String trailerUrl = rs.getString("trailer_url");
				
				if (bannerUrl == null){
					bannerUrl = "";
				}
				if (trailerUrl == null){
					trailerUrl = "";
				}

				Statement genreStat = dbcon.createStatement();
				String genreQuery = "Select genres.name from movies, genres_in_movies, genres where movies.id = genres_in_movies.movie_id AND genres.id = genres_in_movies.genre_id And movies.id = "
						+ id + ";";
				ResultSet findGenres = genreStat.executeQuery(genreQuery);
				String oneGenre;
				ArrayList<String> allGenres = new ArrayList<String>();
				try {
					while (findGenres.next()) {
						oneGenre = findGenres.getString("name");

						allGenres.add(oneGenre);
					}
				} catch (Exception e) {
					allGenres.clear();
				}

				starStat = dbcon.createStatement();
				String starQuery = "Select movies.title, stars.first_name, stars.last_name, stars.id, stars.dob, stars.photo_url from stars, stars_in_movies, movies where movies.id = stars_in_movies.movie_id AND stars.id = stars_in_movies.star_id and movies.id = "
						+ id + ";";
				ResultSet findStars = starStat.executeQuery(starQuery);
				ArrayList<Star> allStars = new ArrayList<Star>();
				int starId; 
				String starFN, starLN, starDob, starPhoto; 
				try {
					while (findStars.next()) {
						starId = findStars.getInt("id"); 
						starFN = findStars.getString("first_name");
						starLN = findStars.getString("last_name"); 
						starDob = findStars.getString("dob");
						starPhoto = findStars.getString("photo_url");
						
						Star oneStar = new Star(starId, starFN, starLN, starDob, starPhoto); 
						allStars.add(oneStar);
					}
				} catch (Exception e) {
					allStars.clear();
				}
				

				Movie movie = new Movie(id, title, year, director, bannerUrl,
						trailerUrl, allGenres, allStars);
				
		        request.setAttribute("movie", movie);
		        
		        RequestDispatcher rd = request.getRequestDispatcher("singleMovie.jsp");
		        rd.forward(request,response);
		        
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
