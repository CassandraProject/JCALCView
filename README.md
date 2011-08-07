# CALCView #
### CALCView is a program for creating, editing, and debugging CALC blocks  ###

`CALCView` is a program that makes it easier to create, edit and debug CALC, 
CALCA, MATH and LOGIC Blocks in the Foxboro I/A system in a simple and intuitive way. 
If you find it annoying that you cannot easily insert a line or always wished that you 
could actually see how one of these blocks work from the inside, this program is for you.

Some features:
*Complete graphical interface including copy and paste
*Can emulate CALC, CALCA, LOGIC, and MATH blocks
*Completely interactive. You can step through code one line at a time and change anything on the fly.
*Connect to live process variables (via omset/omget) (Java version only)
*Visual stack representation.
*Shows all registers used in the program as well as their statuses (Bad, Out of service, Error)
*Online editing of blocks (via ICC Driver Task)
*Displays all block parameters. You can even change block types.

## Installation ##

Clone the repo from GitHub:

    git clone git://github.com/CassandraProject/CALCView_java.git

If you didn't use git on an AW, then you will need to transfer the directory to
a Foxboro AW

1. This version of the program is written in Java and requires a Java VM to be installed. You
will also need the Java developer tools to compile, but only the runtime to actually run it once
compiled.
2. run the MakeJar.sh script to create the JAR file for the CALCView application
3. run the MakeJarAct.sh script to create the JAR file for the CALCView Activation Code application

## Configuration ##
1. Run the CALCViewAct program to create an activation code
2. Add the resulting activation code and userbame to the Settings.txt file
3. Modify any other settigs in the Settings.txt file as neccesary
4. run the CALCView program

## Contributing ##

Contributions to CALCView are welcome. 

## Thanks ##

CALCView was written by [Tod Baudais](mailto:tod_baudais@mac.com) who generously donated the code to the Cassandra Project 
after he decided to no longer maintain it. 

The following people have contributed patches to  CALCView - thanks!

* [Jeremy Milum](http://github.com/jmilum)
