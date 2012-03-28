# DeepCover 
## Extended coverage for Java projects

DeepCover is a library that tracks and reports on the coverage of your tests, but instead of focusing on type, method, line coverage as normal test coverage tools do, DeepCover looks for the values
of method arguments that are tested. For instance, for numeric arguments, it finds out if tests are written that check for null, zero and non-null values. In general, DeepCover finds out if the developer
added tests for trivial and non-trivial values. 

## Motivation

The standard approach to test coverage is to instrument your classes, and look for the coverage in your tests in three categories:
* Classes
* Methods
* Lines

If the coverage does not exceed some agreed thresholds, this step will fail. 
While this approach looks sensible, it doesn't say much
about code quality or stability. Deepcover takes a different approach, providing feedback on whether the developer has tested
or not trivial and non trivial values for an argument in any public method of his code. This seems to be the cause of a high
number of source code errors. 

Lets look at a simple example:

```
public double divide(double x, double y) {
  return x / y;
}
```

In general, this code will work for non-trivial cases (x and y non zero), but when y is zero (trivial case) 
it will throw and arithmetic exception. If we write test code, the standard code coverage solutions would give us
thumbs up, saying that we have added a test for this method, and we are also testing the all the source lines. However,
they won't report if we have tested the case where y = 0, or when both x and y are zero. Also, what happens, when I pass
a Double object to this method that is null?

Deepcover will look at your tests and report if you are tested for x and y being not null, null, and zero. 


## Usage
Download the latest JAR. Run your tests passing these arguments:
```
-javaagent:DEEPCOVER_LOCATION/deepcover.jar -Dorg.deepcover.pkg=com.your.package.here
```

### Reports
Deepcover generates two outputs: an XML report, that makes it easy to integrate it with build reports, and an HTML report 
that displays the results in a more user friendly format.

### Optional arguments

If you set the system property *org.deepcover.dump* to any value, you will see a bytecode dump of all the transformed classes.

## LICENCE

The GPL version 3, read it at http://www.gnu.org/licenses/gpl.txt



