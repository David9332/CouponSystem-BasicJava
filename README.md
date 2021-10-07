# CouponSystem-BasicJava
A coupon system written with basic java (POJO).

Run the program from the "Program" class in the "Tester" package.
The "Program" class activates the "Test" class, that has 3 parts in it: Admin methods,
Company methods and Customer methods. You should decide what part you want to see,
and shut the other two. The default is Admin methods.

If, for some reason, the program does not run, try to do the follows:

1. go to: file -> program structure -> project  
   in Project SDK choose: 11 Amazon Correctto version 11.0.11
2. go to: file -> program structure -> modules -> sources
   under "Add Content root" on the right, delete all Source folders
   of the 3 modules.
   For the "CouponSystem-BasicJava-main (1)" module, add the matching source
   folder by browsing. Press apply and then exit that window.
3. On the left side of the screen of the intelliJ, go to the "idea" folder in the 
   "CouponSystem-BasicJava-main (1)" module, and open misc.xml.
   Change the "languageLevel" and "project-jdk-name" to JDK_11 and 11, respectively.
4. go to: file -> program structure -> Libraries and add
   mysql:mysql-connector-java:8.0.25 the "CouponSystem-BasicJava-main (1)" module.
   Press apply, and run the program.
