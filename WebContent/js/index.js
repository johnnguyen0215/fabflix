$(document).ready(function(){
	/*
	$("#suggestions li").click(function(event){
		var movieTitle = $(this).index();
		$("#searchInput").val(movieTitle);
	});*/
	
	$('#suggestions').on('click', 'li', function(e){
		e.preventDefault();
		var movieTitle = $(this).text();
		console.log(movieTitle);
		$("#searchInput").val(movieTitle);
		$("#searchDropDown").hide();
	});
	
	$(document).mouseup(function(e){
		var container = $("#searchDropDown");
		if (!container.is(e.target) && container.has(e.target).length === 0){
			container.hide();
		}
	});
	
	
	$('.titleLink').mouseover(function(){
		var movieTitle = $(this).text();
		
		movieTitle = encodeURIComponent(movieTitle);

		$.ajax({
			context: this,
			async:false,
			type: 'GET',
			url: "movieDetails?title="+movieTitle,
			success: function(data){
				$(this).next().html(data);
				$(this).next().show();
			}
		});
	});
	
	$('.moviePopUp').hover(function(){
		$(this).show();
	}, function(){
		$(this).hide();
	});
	
	
});

function openCartWindow(movieId, movieTitle){
	var url = "shoppingCartWindow.jsp?movieId="+movieId+"&movieTitle="+movieTitle;
	window.open(url, "newWindow", "top=100,left=100,location=no,resizable=yes,height=300,width=400");
	
}


function generateSuggestions(str){
	str = encodeURIComponent(str);
	$.get("suggestions?q="+str, function(data){
		console.log(data);
		if (data){
			$("#searchDropDown").show();
		}
		else{
			$("#searchDropDown").hide();
		}
		$("#suggestions").html(data);
	});
}

function fullTextSearch(){
	
	var title = $("#searchInput").val();
	
	var titleSplit = title.split(" ");
	
	var queryTitle = "";
	for (var i = 0; i < titleSplit.length; i++){
		if (i == (titleSplit.length-1)){
			queryTitle += titleSplit[i];
		}
		else{
			queryTitle += titleSplit[i] + "+";
		}
	}
	
	var url = "search?title=" + queryTitle+ "&director=&f_n=&l_n=&order=t_asc&rpp=5&page=1";
	window.location.href = url;
}

function displayMovieInfo(movieTitle){

	var queryTitle = "";
	
	var titleSplit = movieTitle.split(" ");
	
	for (var i = 0; i < titleSplit.length; i++){
		if (i == (titleSplit.length-1)){
			queryTitle += titleSplit[i];
		}
		else{
			queryTitle += titleSplit[i] + "+";
		}
	}
	
	$.get("movieDetails?title="+queryTitle, function(data){
		if (data){
			$(this).next("a").next(".moviePopUp:first").html(data);
			$(this).next(".moviePopUp:first").show();
		}
		else{
			$(this).next().hide();
		}
	});
}
