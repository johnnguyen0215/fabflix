----------- AWS IP address -----------
54.153.20.72
Public IP Address: http://54.153.20.72

----------- Resources used -----------
Used TomcatForm.war, TomcatTest.war, Session.war, TomcatPooling.war, TomcatFormRecaptcha, the html for jmeter and jspTest.war as a template to start creating FabFlix. The war files were provided by the professor for this class. 
bootstrap: https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css
https://fonts.googleapis.com/css?family=Lato
https://fonts.googleapis.com/css?family=Raleway
https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css
JQuery: https://code.jquery.com/jquery-2.2.0.min.js
https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js

----------- How to run on local host and aws -----------------
- Go to tomcat manager 
- Deploy to project3_04.war 
- HTTPS:
- Open https://<ip or localhost>:8443/project3_04
- HTTP:
	- Open http://<ip or localhost>:8080/project3_04
** Note: Tomcat7 must be configured properly for https to work on localhost ** 

---------- Username and Password for Tomcat Manager -----------
- HTTPS:
Tomcat 8443: https://54.153.20.72:8443/manager/html
- HTTP:
	Tomcat 8080: https://54.153.20.72:8080/manager/html
username: tomcat
password: s3cret

------------------- Assumptions about add_movie --------------------
To check if a movie exists, we checked for the movie title (verified with professor)
To check if a star exists, we checked for the star using first name and last name (verified with professor)
User will always input first name and last name of star
If a movie already exists, then no change to the movie values will be made. Only genres or stars may be linked to the movie
To add a movie, a user must enter the required values for both star info and genre info 
If a movie already exists, then a new movie will not be added (since we are just checking by title even if the user inputs different director, banner url, trailer url none of the values will be changed/updated for the movie. This was all confirmed by the professor on Piazza). 

----------- Java Version -----------
Java: 8
Tomcat Version: 7
