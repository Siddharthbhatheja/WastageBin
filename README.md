# WastageBin
Wastage Bin is an Android app for managing cleaning of wastages around the city.
There are 3 roles in the app namely: 
 a) Detector(who detects and resolves wastage)
 b) Resolver(Who resolves wastage) 
 c) Financier(who sponsors cleaning of wastage)

Description:
As soon as someone who signed in as a detector detects the wastage, he posts the information on the app, that is visible to
anyone who signed in as a resolver. The resolver can either choose to clean it for free or he can make an offer stating the 
amount he will charge for his service. If he makes an offer, then that offer will be shown to anyone who signed in as a 
financer. If the financer chooses to sponsor an offer then the respective resolver will know that some financer has accepted
the offer and now he can start cleaning the wastage.The app also supports location and filtration. The app uses basic authentication and data from the server is fetched using Rest APIs that are built in Java using Spring Boot and the database used is MongoDB.
