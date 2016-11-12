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

public class Search extends HttpServlet {

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
			String order = request.getParameter("order");
			String resultsPerPage = request.getParameter("rpp");
	        String page = request.getParameter("page");
	        
	        if (Jtitle.equals("") && Jyear == null && Jdirector.equals("") && Jf_n.equals("") && Jl_n.equals("")){
	        	response.sendRedirect("searchForm.jsp?submit=fail");
	        }
	        
	
	        int pageVal = Integer.valueOf(page);
	        int rpp = Integer.valueOf(resultsPerPage);
	        int offsetVal = (pageVal-1)* rpp;
	        
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
				
				query += ") " + "ORDER BY " + orderQuery + " LIMIT " + resultsPerPage + " OFFSET " + offset;
				
			}
			
			rs = statement.executeQuery(query);
			
			ArrayList<Movie> movies = new ArrayList<Movie>();
			String genreQuery = "Select genres.name from movies, genres_in_movies, genres "
					+ "where movies.id = genres_in_movies.movie_id AND genres.id = genres_in_movies.genre_id "
					+ "And movies.id = ?";
			genreStat = dbcon.prepareStatement(genreQuery);
			
			while (rs.next()) {
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

				/*Statement starStat = dbcon.createStatement();
				String starQuery = "Select movies.title, stars.first_name, stars.last_name, stars.id, stars.dob, stars.photo_url from stars, stars_in_movies, movies where movies.id = stars_in_movies.movie_id AND stars.id = stars_in_movies.star_id and movies.id = "
						+ id + ";";
				ResultSet findStars = starStat.executeQuery(starQuery);*/
				String starQuery = "Select movies.title, stars.first_name, stars.last_name, "
						+ "stars.id, stars.dob, stars.photo_url from stars, "
						+ "stars_in_movies, movies where movies.id = stars_in_movies.movie_id "
						+ "AND stars.id = stars_in_movies.star_id and movies.id = ?";
				starStat = dbcon.prepareStatement(starQuery); 
				
				ArrayList<Star> allStars = new ArrayList<Star>();
				int starId; 
				String starFN, starLN, starDob, starPhoto; 
				try {
					starStat.setInt(1, id);
					findStars = starStat.executeQuery();
					while (findStars.next()) {
						starId = findStars.getInt("id"); 
						starFN = findStars.getString("first_name");
						starLN = findStars.getString("last_name"); 
						starDob = findStars.getString("dob");
						starPhoto = findStars.getString("photo_url");
						
						if (starPhoto == null){
							starPhoto = "";
						}
						
						Star oneStar = new Star(starId, starFN, starLN, starDob, starPhoto); 
						allStars.add(oneStar);
					}
				} catch (Exception e) {
					allStars.clear();
				}
				Movie movie = new Movie(id, title, year, director, bannerUrl,
						trailerUrl, allGenres, allStars);

				movies.add(movie);
			}
			
			Jtitle = Jtitle.replace(' ', '+');
			Jf_n = Jf_n.replace(' ', '+');
			Jl_n = Jl_n.replace(' ', '+');
			Jdirector = Jdirector.replace(' ', '+');
			
	        String rootUrl = "search?title="+Jtitle;
	        
	        if (Jyear != null){
	        	rootUrl += "&year="+Jyear;
	        }
	        rootUrl +="&director="+Jdirector+"&f_n="+Jf_n+"&l_n="+Jl_n;
	        
	        
			if (movies.size() == 0 && pageVal != 1){
				response.sendRedirect(rootUrl + "&page=" + (pageVal -1) + "&order=" + order + "&rpp=" + rpp);
			}
			else{
	        
		        String[] rppArr = {rootUrl+"&order="+order+"&rpp=5"+"&page="+page,
		        		rootUrl+"&order="+order+"&rpp=10"+"&page="+page,
		        		rootUrl+"&order="+order+"&rpp=15"+"&page="+page,
		        		rootUrl+"&order="+order+"&rpp=20"+"&page="+page,
		        		rootUrl+"&order="+order+"&rpp=25"+"&page="+page};
		        
		        String[] orderArr = {rootUrl+"&order=t_asc"+"&rpp="+rpp+"&page="+page,
		        		rootUrl+"&order=t_desc"+"&rpp="+rpp+"&page="+page,
		        		rootUrl+"&order=y_asc"+"&rpp="+rpp+"&page="+page,
		        		rootUrl+"&order=y_desc"+"&rpp="+rpp+"&page="+page};
		        
		        String nextUrl = rootUrl+"&order="+order+"&rpp="+rpp+"&page="+(pageVal+1);
		        String prevUrl = rootUrl+"&order="+order+"&rpp="+rpp+"&page="+(pageVal-1);
		        if (pageVal == 1){
		        	prevUrl = rootUrl+"&order="+order+"&rpp="+rpp+"&page="+pageVal;
		        }
		        
		    	
		        
		        request.setAttribute("sortByHtml", sortByHtml);
		        request.setAttribute("nextUrl", nextUrl);
		        request.setAttribute("prevUrl", prevUrl);
		        request.setAttribute("rppArr", rppArr);
		        request.setAttribute("orderArr", orderArr);
		        request.setAttribute("movies", movies);
		        RequestDispatcher rd = request.getRequestDispatcher("search.jsp");
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
