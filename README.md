# Welcome to My Framework

This repository includes utility classes which can be used to create a automation test framework. As build management, Maven is used. All dependencies are organized with the help of pom.xml file. 

## pom.xml file

There are several dependencies and two plugins. 

### Dependencies

| Dependency Name | Explanation |
|--|--|
| 1. Selenium | Browser automation  |
| 2. javafaker | Test Data |
| 3. bonigarcia | WebDriverManager (Browser setup) |
| 4. testNG | Test (Assertion) tool |
| 5. extent reports | Report for TestNG framework |
| 6. Apache poi | Microsoft office programs |
| 7. REST Assured | Rest API testing |
| 8. Cucumber - Java |  |
| 9. Cucumber - JUnit |  |
| 10. Json Schema Validator | To validate schema of a Rest API |
| 11. itextPDF | To generate a pdf format |
| 12. Jackson Databind| Serialization and de-serialization |
| 13. Gson | Serialization and de-serialization |
| 14. PostGreSQL| Driver for JDBC, special to postgreSQL database |
| 15. MySQL| Driver for JDBC, special to MySQL database |
| 16. Oracle| Driver for JDBC, special to Oracle database |
| 17. Passay| to generate strong password |

> #### Note about Oracle Driver:  
> Oracle connection driver does not exist in mvnrepository.com. You should define its repository url seperately. You can see it in the pom.xml file within `<repository>` tags.


### Plugins

| Plugin Name | Explanation |
|--|--|
| 1. Surefire | to execute test classes with maven  |
| 2. Maven - Cucumber - Reporting| to generate fancy report for cucumber framework |

> #### Note about Surefire:  
> 1.  parallel test can be configured under `<configuration>` tags
> 2. test class names should be the same as in the project
> 3. test class for failed test cases
> 4. test classes in alphabetical order

> #### Note about cucumber report:  
> 1.  with only verify phase
> 2. under  `Cucumber HTML Reports` folder

## Util Classes

These classes can be extended in time by experience.

| Class Name | Explanation |
|--|--|
| 1. Driver	| WebDriver in singleton structure  |
| 2. DriverWithSingletonAndRemoteAndGrid| singleton, parallel and remote|
| 3. WebDriverFactoryBasic| basic WebDriver |
| 4. BrowserUtils| Wait, click, JSExecutor methods|
| 5. ConfigurationReader | Configuration.properties read and write |
| 6. DateUtils |  |
| 7. DbUtils | database methods |
| 8. ExcelUtils | excel methods |
| 9. PasswordGenerator | to generate a strong password (passay dependency) |
| 10. PrintOutTableAsPdf | To generate a pdf with table |
| 11. StringUtils| Common string methods |

## Files

1. Configuration.properties: You can store some common information here. Key-value structured java file. Locate it main folder of the project. 
2. feature files: under resources folder (resources folder java klasorunun kardesi)
3. excel files: under resources folder 
