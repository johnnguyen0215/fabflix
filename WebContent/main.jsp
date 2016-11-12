<%@include file="checkSession.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Main Page</title>
<%@include file="dependencies.jsp"%>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<!--div id="mainPage">
  <div class="jumbotron">
    <h1> Main page under construction </h1>
</div-->

	<div id="mainPage">
		<div id="browse">
			<div id="genres">
				<div>
					<h4>Browse by Genre</h4>
				</div>
				<hr>
				<table id="genreTable" border="0" cellspacing="1"
					style="width: 300px;" cellpadding="5">
					<tbody>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=action&page=1&order=t_asc&rpp=5">Action</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=history&page=1&order=t_asc&rpp=5">History</a></td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=adventure&page=1&order=t_asc&rpp=5">Adventure</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=horror&page=1&order=t_asc&rpp=5">Horror</a></td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=animation&page=1&order=t_asc&rpp=5">Animation</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=music&page=1&order=t_asc&rpp=5">Music</a></td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=biography&page=1&order=t_asc&rpp=5">Biography</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=musical&page=1&order=t_asc&rpp=5">Musical</a></td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=comedy&page=1&order=t_asc&rpp=5">Comedy</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=mystery&page=1&order=t_asc&rpp=5">Mystery</a></td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=crime&page=1&order=t_asc&rpp=5">Crime</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=romance&page=1&order=t_asc&rpp=5">Romance</a></td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=documentary&page=1&order=t_asc&rpp=5">Documentary</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=scifi&page=1&order=t_asc&rpp=5">Sci-Fi</a></td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=drama&page=1&order=t_asc&rpp=5">Drama</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=sport&page=1&order=t_asc&rpp=5">Sport</a></td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=family&page=1&order=t_asc&rpp=5">Family</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=thriller&page=1&order=t_asc&rpp=5">Thriller</a></td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=genre&genre=fantasy&page=1&order=t_asc&rpp=5">Fantasy</a></td>
							<td><a
								class="mainLink" href="browse?by=genre&genre=war&page=1&order=t_asc&rpp=5">War</a></td>
						</tr>
						<tr>
							<td><a
							class="mainLink" href="browse?by=genre&genre=adventure&page=1&order=t_asc&rpp=5">Film-Noir</a></td>
							<td><a
							class="mainLink" href="browse?by=genre&genre=adventure&page=1&order=t_asc&rpp=5">Western</a></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div id="searchTitle">
				<div>
					<h4>Browse by Title</h4>
				</div>
				<hr>
				<table id="titleTable" border="0" cellpadding="1" cellspacing="1"
					style="width: 300px;">
					<tbody>
						<tr>
							<td><a
								class="mainLink" href="browse?by=title&title=0&page=1&order=t_asc&rpp=5">0</a> |
								<a class="mainLink" href="browse?by=title&title=1&page=1&order=t_asc&rpp=5">1</a>
								| <a class="mainLink" href="browse?by=title&title=2&page=1&order=t_asc&rpp=5">2</a>
								| <a class="mainLink" href="browse?by=title&title=3&page=1&order=t_asc&rpp=5">3</a>
								| <a class="mainLink" href="browse?by=title&title=4&page=1&order=t_asc&rpp=5">4</a>
								| <a class="mainLink" href="browse?by=title&title=5&page=1&order=t_asc&rpp=5">5</a>
								| <a class="mainLink" href="browse?by=title&title=6&page=1&order=t_asc&rpp=5">6</a>
								| <a class="mainLink" href="browse?by=title&title=7&page=1&order=t_asc&rpp=5">7</a>
								| <a class="mainLink" href="browse?by=title&title=8&page=1&order=t_asc&rpp=5">8</a>
								| <a class="mainLink" href="browse?by=title&title=9&page=1&order=t_asc&rpp=5">9</a>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=title&title=A&page=1&order=t_asc&rpp=5">A</a> |
								<a class="mainLink" href="browse?by=title&title=B&page=1&order=t_asc&rpp=5">B</a>
								| <a class="mainLink" href="browse?by=title&title=C&page=1&order=t_asc&rpp=5">C</a>
								| <a class="mainLink" href="browse?by=title&title=D&page=1&order=t_asc&rpp=5">D</a>
								| <a class="mainLink" href="browse?by=title&title=E&page=1&order=t_asc&rpp=5">E</a>
								| <a class="mainLink" href="browse?by=title&title=F&page=1&order=t_asc&rpp=5">F</a>
								| <a class="mainLink" href="browse?by=title&title=G&page=1&order=t_asc&rpp=5">G</a>
								| <a class="mainLink" href="browse?by=title&title=H&page=1&order=t_asc&rpp=5">H</a>
								| <a class="mainLink" href="browse?by=title&title=I&page=1&order=t_asc&rpp=5">I</a>
								|</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=title&title=J&page=1&order=t_asc&rpp=5">J</a> |
								<a class="mainLink" href="browse?by=title&title=K&page=1&order=t_asc&rpp=5">K</a>
								| <a class="mainLink" href="browse?by=title&title=L&page=1&order=t_asc&rpp=5">L</a>
								| <a class="mainLink" href="browse?by=title&title=M&page=1&order=t_asc&rpp=5">M</a>
								| <a class="mainLink" href="browse?by=title&title=N&page=1&order=t_asc&rpp=5">N</a>
								| <a class="mainLink" href="browse?by=title&title=O&page=1&order=t_asc&rpp=5">O</a>
								| <a class="mainLink" href="browse?by=title&title=P&page=1&order=t_asc&rpp=5">P</a>
								| <a class="mainLink" href="browse?by=title&title=Q&page=1&order=t_asc&rpp=5">Q</a>
								| <a class="mainLink" href="browse?by=title&title=R&page=1&order=t_asc&rpp=5">R</a>
								|</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><a
								class="mainLink" href="browse?by=title&title=S&page=1&order=t_asc&rpp=5">S</a> |
								<a class="mainLink" href="browse?by=title&title=T&page=1&order=t_asc&rpp=5">T</a>
								| <a class="mainLink" href="browse?by=title&title=U&page=1&order=t_asc&rpp=5">U</a>
								| <a class="mainLink" href="browse?by=title&title=V&page=1&order=t_asc&rpp=5">V</a>
								| <a class="mainLink" href="browse?by=title&title=W&page=1&order=t_asc&rpp=5">W</a>
								| <a class="mainLink" href="browse?by=title&title=X&page=1&order=t_asc&rpp=5">X</a>
								| <a class="mainLink" href="browse?by=title&title=Y&page=1&order=t_asc&rpp=5">Y</a>
								| <a class="mainLink" href="browse?by=title&title=Z&page=1&order=t_asc&rpp=5">Z</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div id="popularMovies">
			<table id="popularMoviesTable" align="center" border="0"
				cellpadding="5" cellspacing="5" style="width: 1000px">
				<H1 style="color:white; text-align:center">Popular Movies</H1>
				<hr>
				<tbody>
					<tr>
						<td>
							<div style="width:250px; text-align:center">
							<div>
								<img style="padding: 10px" height="280" width="200"
									src="http://images.amazon.com/images/P/B000065U1N.01.LZZZZZZZ.jpg"/>
							</div>
							<div>
								<label style="color:white">Black Hawk Down</label>
							</div>
							<button onClick="openCartWindow(683009, 'Black Hawk Down')"
								class="btn btn-success" style="margin-top:10px">
								<i class="fa fa-cart-plus fa-lg"></i> Add to Cart
							</button>
							</div>
						</td>
						<td style="text-align: center;">
							<div style="width:250px; text-align:center">
							<div>
								<img style="padding: 10px" height="280" width="200"
									src="http://www.microsoft.com/windows/windowsmedia/images/mediaandent/t2DVD.jpg"/>
							</div>
							<div>
								<label style="color:white">Terminator 2</label>
							</div>
							<button onClick="openCartWindow(755010, 'Terminator 2')"
								class="btn btn-success" style="margin-top:10px">
								<i class="fa fa-cart-plus fa-lg"></i> Add to Cart
							</button>
							</div>
						</td>
						<td style="text-align: center;">
							<div style="width:250px; text-align:center">
							<div>
								<img style="padding: 10px" height="280" width="200"
									src="http://trailers.apple.com/trailers/miramax/images/bride_and_prejudice_poster.jpg"/>
							</div>
							<div>
								<label style="color:white">Bride and Prejudice</label>
							</div>
							<button onClick="openCartWindow(140009, 'Bride and Prejudice')"
								class="btn btn-success" style="margin-top:10px">
								<i class="fa fa-cart-plus fa-lg"></i> Add to Cart
							</button>
							</div>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;">
							<div style="width:250px; text-align:center">
							<div>
								<img style="padding: 10px" height="280" width="200"
									src="http://images.amazon.com/images/P/0780631536.01.LZZZZZZZ.jpg"/>
							</div>
							<div>
								<label style="color:white">Boiler Room</label>
							</div>
							<button onClick="openCartWindow(683003, 'Boiler Room')"
								class="btn btn-success" style="margin-top:10px">
								<i class="fa fa-cart-plus fa-lg"></i> Add to Cart
							</button>
							</div>
						</td>
						<td style="text-align: center;">
							<div style="width:250px; text-align:center">
							<div>
								<img style="padding: 10px" height="280" width="200"
									src="http://www.markheadrick.com/dvd/images/IndianaJones-RaidersOfTheLostArk.jpg"/>
							</div>
							<div>
								<label style="color:white">Raiders of the Lost Ark</label>
							</div>
							<button onClick="openCartWindow(755007, 'Raiders of the Lost Ark')"
								class="btn btn-success" style="margin-top:10px">
								<i class="fa fa-cart-plus fa-lg"></i> Add to Cart
							</button>
							</div>
						</td>
						<td style="text-align: center;">
							<div style="width:250px; text-align:center">
							<div>
								<img style="padding: 10px" height="280" width="200"
									src="http://images.amazon.com/images/P/B00005R87Q.01.LZZZZZZZ.jpg"/>
							</div>
							<div>
								<label style="color:white">The Fast and the Furious</label>
							</div>
							<button onClick="openCartWindow(683007, 'The Fast and the Furious')"
								class="btn btn-success" style="margin-top:10px">
								<i class="fa fa-cart-plus fa-lg"></i> Add to Cart
							</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
