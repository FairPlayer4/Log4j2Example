# Under Construction not finished yet
# You may use existing code as you like

# log4j2 Example
How to easily set up logging with log4j2.

This indented for Java developers who want to use advanced logging but don't want dig through the complex documentation to set it up.

I am not an expert by any means but it took me a lot of time to learn all this so I want provide a tutorial that covers everything that I think is important.

This repository aims to provide all information that is necessary to set up log4j2 logging in your Java projects.

###Included: 

- Documented log4j2 configuration
- Explanation on how to make sure logging is asynchronous
- Simple JTextPane Appender that explains how to add custom appenders
- Some code for log4j2 reconfiguration at runtime
- Using the log4j2 String Formatter for log messages (and other strings) 
- Logging Examples

###Not Included: 

- Filtering besides Log Levels
- Everything else

There are a lot of aspects of log4j2 that are not covered because I only gathered the information that I deemed necessary and use myself.

This tutorial shows only advanced things that I think are necessary and provides an all-in-one setup that is designed for minimal overhead and maximum performance logging.

### Introduction

In most cases you should not use System.out or System.err for logging because they don't provide any flexibility.

In larger projects you need logging to keep track of the status of the application and find errors.

Your logging should be flexible meaning there should be different message types (error, warning, information,...) and the ability to write logs to a file.

log4j2 is a powerful logging framework that makes logging very easy and efficient too. 

The problem is that it takes a lot of reading and understanding to configure log4j2 correctly. 


### Setup log4j2

Put the following files in the resources folder of your Java project.

- log4j2.xml
- log4j2.component.properties (only for asynchronous logging)

Samples of these files are available in the Log4j2SampleConfig folder of this repository.

Add the following dependencies to the class path of your Java project

- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api
- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
- https://mvnrepository.com/artifact/com.lmax/disruptor (only for asynchronous logging)

Always pick the newest version if you don't have specific requirements

Now you can add the following line to your classes to give them a logger (You could also use one logger for all classes but then it is difficult to track where the log originated)

private static final Logger log = LogManager.getLogger(LoggerName); // I use the class name as LoggerName e.g. YourClass.class 

Now logging is very simple

log.info("Test");

