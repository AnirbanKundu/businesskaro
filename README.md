# businesskaro

## Install all softwares

To run the app from your local the following softwares need to be installed in your machine :

1. Git [https://git-scm.com/download/win]
2. Smart Git [for pull and push requests] (http://www.syntevo.com/smartgit/)
3. STS [Spring Tool Suite] (https://spring.io/tools/sts/all)
4. Maven (https://maven.apache.org/download.cgi)
5. Java 7.0 
5 Node & npm (https://nodejs.org/en/download/)

## Run from local

Open a terminal and type the following commands :
`npm install`
`bower install`
###### go to pom.xml file and comment out the following (but do not check in this part)
     <resources>
      <resource>
        <directory>src/main/resources</directory>
          <excludes>
            `<exclude>**/${uifoldername}/</exclude>`
          </excludes>
      </resource>
    </resources>

Assuming JAVA and Maven is installed already, open a terminal and go to project folder 

`mvn clean install`
`java -jar target/businesskaro-1.jar`
