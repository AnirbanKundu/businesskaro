# businesskaro

This project is generated with [yo angular generator](https://github.com/yeoman/generator-angular)
version 0.11.1.

## Build & development

`npm install`
`bower install`
###go to pom.xml file and comment out the following [but do not check in this part]
<resources>
 <resource>
   <directory>src/main/resources</directory>
    <excludes>
     <exclude>**/${uifoldername}/</exclude>
    </excludes>
  </resource>
</resources>

Assuming JAVA and Maven is installed already, 

`mvn clean install`
`java -jar target/businesskaro-1.jar`
