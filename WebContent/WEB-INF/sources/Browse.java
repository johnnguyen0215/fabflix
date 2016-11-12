/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.sql.PreparedStatement;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.Date;

import com.Movie;
import com.Star;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class Browse extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		response.setContentType("text/html"); // Response mime type
		PrintWriter out = response.getWriter();
		
		Connection dbcon = null;
		Statement statement = null;
		ResultSet rs = null;
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

			String browseBy = request.getParameter("by");

			String genre = request.getParameter("genre");
			String order = request.getParameter("order");
			String resultsPerPage = request.getParameter("rpp");
			String page = request.getParameter("page");
			String browseTitle = request.getParameter("title");

			int pageVal = Integer.valueOf(page);
			int rpp = Integer.valueOf(resultsPerPage);
			int offsetVal = (pageVal - 1) * rpp;

			String offset = Integer.toString(offsetVal);

			String orderQuery = "title ASC";

			String sortByHtml = "Title Ascending<i class=\"fa fa-chevron-up\"></i>";

			if (order.equals("t_asc")) {
				orderQuery = "title ASC";
				sortByHtml = "Title Ascending<i class=\"fa fa-chevron-up\"></i>";
			} else if (order.equals("t_desc")) {
				orderQuery = "title DESC";
				sortByHtml = "Title Descending <i class=\"fa fa-chevron-down\"></i>";
			} else if (order.equals("y_asc")) {
				orderQuery = "year ASC";
				sortByHtml = "Year Ascending <i class=\"fa fa-chevron-up\"></i>";
			} else if (order.equals("y_desc")) {
				orderQuery = "year DESC";
				sortByHtml = "Year Descending <i class=\"fa fa-chevron-down\"></i>";
			}

			ArrayList<Movie> movies = new ArrayList<Movie>();

			String query = "";
			String rootUrl = "";

			if (browseBy.equals("genre")) {
				rootUrl = "browse?by=genre&genre=" + genre;
				query = "Select * from movies, genres_in_movies, genres where movies.id = genres_in_movies.movie_id AND genres.id = genres_in_movies.genre_id AND genres.name = \""
						+ genre
						+ "\""
						+ "ORDER BY "
						+ orderQuery
						+ " LIMIT "
						+ resultsPerPage + " OFFSET " + offset;
			} else if (browseBy.equals("title")) {
				rootUrl = "browse?by=title&title=" + browseTitle;
				query = "Select * from movies where movies.title LIKE \"%"
						+ browseTitle + "%\" " + "ORDER BY " + orderQuery
						+ " LIMIT " + resultsPerPage + " OFFSET " + offset;
			}
			rs = statement.executeQuery(query);
			String genreQuery = "Select genres.name from movies, genres_in_movies, genres "
					+ "where movies.id = genres_in_movies.movie_id "
					+ "AND genres.id = genres_in_movies.genre_id And movies.id = ?";
			genreStat = dbcon.prepareStatement(genreQuery);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String movieTitle = rs.getString("title");
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

				String oneGenre;
				ArrayList<String> allGenres = new ArrayList<String>();
				try {
					genreStat.setInt(1, id);
					ResultSet findGenres = genreStat.executeQuery();
					while (findGenres.next()) {
						oneGenre = findGenres.getString("name");

						allGenres.add(oneGenre);
					}
				} catch (Exception e) {
					allGenres.clear();
				}

				String starQuery = "Select movies.title, stars.first_name, stars.last_name, "
						+ "stars.id, stars.dob, stars.photo_url "
						+ "from stars, stars_in_movies, movies "
						+ "where movies.id = stars_in_movies.movie_id "
						+ "AND stars.id = stars_in_movies.star_id and movies.id = ?";
				
				starStat = dbcon.prepareStatement(starQuery);
				
				ArrayList<Star> allStars = new ArrayList<Star>();
				int starId;
				String starFN, starLN, starDob, starPhoto;
				try {
					starStat.setInt(1, id);
					ResultSet findStars = starStat.executeQuery();
					while (findStars.next()) {
						starId = findStars.getInt("id");
						starFN = findStars.getString("first_name");
						starLN = findStars.getString("last_name");
						starDob = findStars.getString("dob");
						starPhoto = findStars.getString("photo_url");
						if (starPhoto == null){
							starPhoto = "";
						}

						Star oneStar = new Star(starId, starFN, starLN,
								starDob, starPhoto);
						allStars.add(oneStar);
					}
				} catch (Exception e) {
					allStars.clear();
				}

				Movie movie = new Movie(id, movieTitle, year, director,
						bannerUrl, trailerUrl, allGenres, allStars);

				movies.add(movie);
			}

			if (movies.size() == 0 && pageVal != 1) {
				response.sendRedirect(rootUrl + "&page=" + (pageVal - 1)
						+ "&order=" + order + "&rpp=" + rpp);
			} else {

				String[] rppArr = {
						rootUrl + "&page=" + page + "&order=" + order
								+ "&rpp=5",
						rootUrl + "&page=" + page + "&order=" + order
								+ "&rpp=10",
						rootUrl + "&page=" + page + "&order=" + order
								+ "&rpp=15",
						rootUrl + "&page=" + page + "&order=" + order
								+ "&rpp=20",
						rootUrl + "&page=" + page + "&order=" + order
								+ "&rpp=25" };

				String[] orderArr = {
						rootUrl + "&page=" + page + "&order=t_asc" + "&rpp="
								+ rpp,
						rootUrl + "&page=" + page + "&order=t_desc" + "&rpp="
								+ rpp,
						rootUrl + "&page=" + page + "&order=y_asc" + "&rpp="
								+ rpp,
						rootUrl + "&page=" + page + "&order=y_desc" + "&rpp="
								+ rpp };

				String nextUrl = rootUrl + "&page=" + (pageVal + 1) + "&order="
						+ order + "&rpp=" + rpp;
				String prevUrl = rootUrl + "&page=" + (pageVal - 1) + "&order="
						+ order + "&rpp=" + rpp;

				if (pageVal == 1) {
					prevUrl = rootUrl + "&page=" + pageVal + "&order=t_desc"
							+ "&rpp=" + rpp;
				}

				request.setAttribute("sortByHtml", sortByHtml);
				request.setAttribute("nextUrl", nextUrl);
				request.setAttribute("prevUrl", prevUrl);
				request.setAttribute("rppArr", rppArr);
				request.setAttribute("orderArr", orderArr);
				request.setAttribute("movies", movies);

				RequestDispatcher rd = request
						.getRequestDispatcher("browse.jsp");
				rd.forward(request, response);
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
