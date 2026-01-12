https://github.com/user-attachments/assets/43150f50-94e5-40a5-add6-a253cb582bb9

Full Stack application using Spring Boot, InfluxDB, and HTML/CSS/JavaScript.

Current telemetric data from a solar car is being stored in an InfluxDB database and not currently being diplayed anywhere. This application uses Spring Boot to implment an API that can pull the telemetric data from InfluxDB. The retreived data is displayed on a simple front end page written using HTML/CSS/JS. Every 150ms JavaScript on the front-end calls the API to retireve and display up to date telemetric data.

The video shows sample data being fed into a local InfluxDB instance being displayed on the front end. 
