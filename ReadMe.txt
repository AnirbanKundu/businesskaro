Database
----------------------------------------
DROP DATABASE IF EXISTS businesskaro;

CREATE DATABASE businesskaro;

use businesskaro;

CREATE USER 'businesskaro'@'localhost' IDENTIFIED BY 'Businesskaro@123';

GRANT ALL PRIVILEGES ON *.* TO 'businesskaro'@'localhost';


29-Feb-2015
--------------------------
Working on authorized links
Read UnRegistered temp table using salt value of the user
select * from UnRegisterd table where salt="xxx"
Where xxx value comes from the user activation link
once we get the values using GET HTTP request read those values and insert the all the information in RegistedTable.