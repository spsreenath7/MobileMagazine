# Mobile App Devvelopment Assignment

Name: Sreenath S P


# Mobile Magazine

## Overview.

This is a mobile application with facility to read news articles from multiple sources. This can serve as a single window solution, through this audience must be able to read the news they are interested and also find reliable from multiple sources.

 . . . . . List of features implemented. . . . 
 
 + User SignUp/Login or sign-in using google account
 + Update user profile
 + Update user preferences for location(google maps) and articles display
 + Add bookmarks for articles
 + Apply search and filter over the bookmarked articles
 + Connect to friends
 + Views the list of articles shared by friends in feed
 
 
## Data Model Design.

![][model]


## App Component Design.

![][comp]

## App UI Design.

## Authentication process

Mobile magazine application uses firebase authentication libraries to handle user's sign-up and login. Firebase offers 5 different authentication options like Email and password based authentication, Federated identity provider integration, Phone number authentication, Custom auth system integration and Anonymous auth.
Here only the following are used,

+Email and password based authentication : Authenticate users with their email addresses and passwords. The Firebase Authentication SDK provides methods to create and manage users that use their email addresses and passwords to sign in.
+Federated identity provider integration : Authenticate users by integrating with federated identity providers, allowing users to sign in with their Google/ Facebook/ Twitter/ GitHub accounts. Here only Google authentication is used. 

libraries added for authentication
    implementation 'com.google.firebase:firebase-auth:16.0.3'
	implementation 'com.google.android.gms:play-services-auth:15.0.1'
	implementation 'com.google.firebase:firebase-core:16.0.1'
	
## Data Persistance approach

Since the application's most functionality like reading articles, sharing articles with friends etc. require articles and friends information readily available all time, a cloud based persistence approach is implemented. 
Also since hosting developing and hosting a middleware application to serve data is cost and time overhead, a cloud-hosted database is used. Firebase Realtime Database SDK for android allows all of the clients share one Realtime Database instance and automatically receive updates with the newest data.

    implementation 'com.google.firebase:firebase-database:16.0.1'
    implementation 'com.google.firebase:firebase-core:16.0.1'
	
### Article's master data
The master data of the articles are fetched from News API. This allows us to search worldwide news with their registered API key. Below is the URL for the API docs,
https://newsapi.org/docs

The application uses retrofit library to make API calls.
implementation 'com.squareup.retrofit2:retrofit:2.3.0'



## Implentation of main features

### Navigation Drawer 
The side navigation drawer is added to the MainActivity this provides a useful way to present options to the user. It provide access to destinations of all the main features of the App.
It's implemented using the widget 'android.support.v4.widget.DrawerLayout' .Once the user chooses an option the the corresponding feature's fragment is rendered inside MainActivity.
![][image1] 

### Update user preferences
This option UI is a ConstraintLayout comprises of 
+checkboxes to choose the articles catogery,
+A dropdown box to fix the count of articles per page and
+A image button that opens a Map Activity where users can fix the preffered region.

![][image2] ![][image3]

### Bookmark and share articles
Whenever a user clicks an article or bookmark, it renders a new fragment in the home activity. This contains,
+ A webview that displays the article's web page.
+ Image buttons for share and bookmark.
+ Input fields like dropdown and textboxes to add more info to bookmark.

![][image5]

### Connect friends, social feed and articles page
The above features are displayed using RecylerView. Also each of the view holders are equipped with buttons and are clickable with listerns attached. This make the recylcer view interactive.
![][image4] ![][image6]

Artical page is displayed using tab layout each tab renders data using seperate API calls based on the user preference saved.
![][image7]

### Bookmark list and explore bookmark
List view is used to build the bookmarks list. Also there is a seperate provision for searching the bookmarks and filtering them based on catogery.
![][image8]


## Extra features

### Google plus API
	Integrating with firebase, google plus API allows to sign in into the using google account.
    implementation 'com.google.android.gms:play-services-auth:15.0.1'
    implementation 'com.shobhitpuri.custombuttons:google-signin:1.0.0'
	
### Google maps API
	Thsi is used to help the user updating their region based on google maps.
    implementation 'com.google.android.gms:play-services-maps:15.0.1'
    implementation 'com.google.android.gms:play-services-location:15.0.1'
	
### Glide 
	A third party library to load images into imageview using an remote http URL.
	implementation 'com.github.bumptech.glide:glide:4.6.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.6.1'

[model]: ./images/datamodel.PNG
[comp]: ./images/compdesign.png
[image1]: ./images/img1.jpg
[image2]: ./images/img2.jpg
[image3]: ./images/img3.jpg
[image4]: ./images/img4.jpg
[image5]: ./images/img5.jpg
[image6]: ./images/img6.jpg
[image7]: ./images/img7.jpg
[image8]: ./images/img8.jpg
