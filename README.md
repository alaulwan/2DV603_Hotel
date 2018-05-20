The software is a hotel management system as a project of the Software Engineering - Design course (2DV603) in Linnaeus University.

The software designed to be used by Linnaeus Hotel to manage the front-desk activities. The hotel clerk will use it to enter reservations as well as to check guests in and out in both hotel’s building in Växjö and Kalmar.



Everything you need to run the program is included in the files HotelClient.jar and HotelServer.jar. The program tested on WINDOWS 10.
You should run HotelServer.jar file and then run HotelClient.jar. The server uses the port 4444. So, it should to be available.
You have two ways to run them:
  -  Double click to open.
OR
  - In a console, in the same directory as jar files is stored, type
      o java -jar HotelServer.jar
      o java -jar HotelClient.jar
      
      
In case you want to run the server and the client in different computers, you should run the client with Remote’s IP parameters, i.e.:
    o java -jar HotelClient.jar Remote_IP
    
    
OBS: It requires a JRE-8 (Java Runtime Environment) or later to be installed on your host machine. You find the latest version to download at https://www.java.com/inc/BrowserRedirect1.jsp?locale=en
