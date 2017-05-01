CS6367-JavaAgent

Software Testing - Prioritize tests based on statement coverage

This is a naive tool to prioritize test classes based on their statement coverage. It involves some manual steps in between.

To collect the coverage, add the below information in the pom.xml in the target folder

	1. Add a dependency for ASM 
	
		<dependency>
			<groupId>org.ow2.asm</groupId>
			<artifactId>asm</artifactId>
			<version>5.0.3</version>
		</dependency>
		
	2. Add java agent details in the surefire plugin in <build>
		<argLine>-javaagent:agent.jar=org/joda/time</argLine>
			<properties>
				<property>
					<name>listener</name>
					<value>org.cs6367.listener.FailureListener</value>
				</property>
		</properties>

Select a target project -

https://github.com/apache/commons-dbutils.git, 633749db5b0fd25b9a3ca133e7496a353de4fd5d

https://github.com/JodaOrg/joda-time.git, acff94148b2110b95f7aeae6a1bdcafb756061f0
		
