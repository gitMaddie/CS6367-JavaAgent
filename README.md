# CS6367-JavaAgentCS6367-JavaAgent

Software Testing - Prioritize tests based on statement coverage

This is a naive tool to prioritize test classes based on their statement coverage. It involves some manual steps in between.

1. To collect the coverage, add the below information in the pom.xml in the target folder

	1. Add a dependency for ASM 
	
		<dependency>
			<groupId>org.ow2.asm</groupId>
			<artifactId>asm</artifactId>
			<version>5.0.3</version>
		</dependency>
		
	2. Add java agent details in the surefire plugin in <build>
		<argLine>-javaagent:agent.jar=org/joda/time</argLine> <!--Mention the package name whose classes you would like to instrument-->
			<properties>
				<property>
					<name>listener</name>
					<value>org.cs6367.listener.FailureListener</value>
				</property>
		</properties>

2. Select a target project -

https://github.com/apache/commons-dbutils.git, 633749db5b0fd25b9a3ca133e7496a353de4fd5d

https://github.com/JodaOrg/joda-time.git, acff94148b2110b95f7aeae6a1bdcafb756061f0

3. Run mvn test.

Two test suites must be create in the target project under the same path as the agentArgs (org.joda.time) in this case.

4. To run this test suite you can create a new pom or modify the existing pom to exclude default test suite and include the new test suite.
Lets create pom-total.xml which is same as pom.xml but with below modification to the surefire plugin. 
// Shown for joda-time-master project

	<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<configuration>
					<properties>
						<property>
							<name>listener</name>
							<value>org.cs6367.listener.FailureListener</value>
						</property>
					</properties>
					<testFailureIgnore>true</testFailureIgnore>
					<includes>
						<include>**/TotalStrategyTestSuite.java</include>
					</includes>
					<additionalClasspathElements> <additionalClasspathElement>${project.build.sourceDirectory}</additionalClasspathElement> 
						<additionalClasspathElement>${project.build.testSourceDirectory}</additionalClasspathElement> 
						</additionalClasspathElements> <useManifestOnlyJar>false</useManifestOnlyJar> 
						<forkMode>always</forkMode>
				</configuration>
	</plugin>
  
  5. Now run mvn -f pom-total.xml to run test cases prioritized by the Total Test Prioritization
  
  6. For additional strategy, change the test suite name in the pom.xml. We include an additional JUnit listener to track time for first failure.
  
     <plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<configuration>
					<!-- <argLine>-javaagent:agent.jar=org/apache/commons/dbutils</argLine> -->
					<properties>
						<property>
							<name>listener</name>
							<value>org.cs6367.listener.FailureListener</value>
						</property>
					</properties>
					<testFailureIgnore>true</testFailureIgnore>
					<excludes>
						<exclude>**/BaseTestCase.java</exclude>
						<exclude>**/*Test.java</exclude>
					</excludes>
					<includes> <include>**/TotalStrategyTestSuite.java</include> 
						</includes> <additionalClasspathElements> <additionalClasspathElement>${project.build.sourceDirectory}</additionalClasspathElement> 
						<additionalClasspathElement>${project.build.testSourceDirectory}</additionalClasspathElement> 
						</additionalClasspathElements> <useManifestOnlyJar>false</useManifestOnlyJar> 
						<forkMode>always</forkMode>
				</configuration>

			</plugin>
     
     Run mvn test -f pom-additional