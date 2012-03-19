# DeepCover 
## Extended coverage for Java projects

DeepCover is a library that tracks and reports on the coverage of your tests, but instead of focusing on type, method, line coverage as normal test coverage tools do, DeepCover looks for the values
of method arguments that are tested. For instance, for numeric arguments, it finds out if tests are written that check for null, zero and non-null values. In general, DeepCover finds out if the developer
added tests for trivial and non-trivial values. 

## Usage
Download the latest JAR. Run your tests passing these arguments:
```
-javaagent:DEEPCOVER_LOCATION/deepcover.jar -Dorg.deepcover.pkg=com.your.package.here
```
### Optional arguments

If you set the system property *org.deepcover.dump* to any value, you will see a bytecode dump of all the transformed classes.





