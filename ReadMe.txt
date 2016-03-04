Database
----------------------------------------
DROP DATABASE IF EXISTS businesskaro;

CREATE DATABASE businesskaro;

use businesskaro;

CREATE USER 'businesskaro'@'localhost' IDENTIFIED BY 'Businesskaro@123';

GRANT ALL PRIVILEGES ON *.* TO 'businesskaro'@'localhost';

28-Feb-2015 and before
------------------------
Try to understand the code of businesskaro which was written by Mr.Anirban.

29-Feb-2015
--------------------------
Working on authorized links
Read UnRegistered temp table using salt value of the user
select * from UnRegisterd table where salt="xxx"
Where xxx value comes from the user activation link
once we get the values using GET HTTP request read those values and insert the all the information in RegistedTable.


1-Mar-2016
--------------------------------
Worked on Registration and activation process of Businesskaro user.


2-Mar-2016
---------------------------------------
Worked on activation link expired process of Businesskaro User.

3-Mar-2016
--------------------------------------------
As per yesterday everning call he told me work on Activation link of the user
Present we added only salt of the user
You have to generate one salt id using userid+salt+password
To achieve this task apply your own algorithm for encryption and decryption of salt

4-Mar-2016
--------------------------------------------

