1. Download the LiveWorkoutFeed.zip file. This file contains the html and javaScript code to run the feed off of a web host

2. Go to https://nodejs.org/en and download the LTS package for Node.js

3. Go to https://dashboard.ngrok.com/get-started/setup/windows and follow the directions to install ngrok. Do not deploy the app.

4. Open up a terminal window. Navigate to the LiveWorkoutFeed directory

5. run node server.js

6. Open a second terminal window

7. Run ngrok http 8080

8. The ngrok window should show up. You should see a forwarding address. This will change every time ngrok is run so you need to update the code every time as there is no permanent server we are using. Update the second lines in both the streamer.js and viewer.js. Keep the wss and then copy everything past and including the ':' after the wss. Then open up the FitnessApplication and navigate to the LiveWorkoutViewModel class and update the URL variable at the top of the class. this time copy the entire forwarding address including the "https"

9. To run the live feed you can either do it through the app or directly go to the forwarding address + "/viewer.html" or "/streamer.html" depending on what role you want to assume on a local browser

10. For optimal testing I would recommend logging in as the trainer on the app and launching the live workout. Then directly go to the forwarding address on another device to view the live feed

