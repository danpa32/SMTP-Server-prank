# Prank SMTP client

## Description

**Prank SMTP client** is a program that allows you to send emails to a list of addresses that you provide. The list of mail addresses are split in group and the addresses of each group is picked as the sender, the rest are the receivers.
The user of the application can defined :

* the number of group
* the smpt server used
* the email contents

## Installation

* Install or update JAVA RE: https://www.java.com/en/download/
* Clone the repository and head to the config directory
    * **config.smtp.txt** is the file where you can define the smtp server address, port and witness.
    * **messages.utf8.txt** is the file that contains the Subjects and contents the mails you want to send. Each Subjects and contents are separated by **===** (VERY IMPORTANT).
    * **victims.txt** is the file where you write the list of all emails addresses that you wish to use in your prank.
* Once you have filled all the previous files, open a terminal and head to the project directory. Here, you run the following command:
```
$ java -jar SMTP-prank.jar
```

Youn can also set up a test environement by using SMTP server on your own computer:

* Download MockMock: https://github.com/tweakers-dev/MockMock
* Open a teminal in the folder that contains the **jar** file and enter the following line:
```
$ java -jar MockMock.jar -h xxxx -p yyyy
```

Where:

* $-h xxxx$ stands for the local port for the SMTP server, default is 25.
* $-p yyyy$ stands for the port used by the web interface, default is 8282.

## Implementation

The project is divided in 5 packages :

* **config** that contains the configuration files
* **mail** that contains the $JAVA$ files relate to a mail, a group and a person.
* **parsers** that contains the $JAVA$ files that parse the files in the **config** directory.
* **prank** that contains the prank generator file (main program).
* **smtp** that contains the files that takes care of the communication with the server (sending a mail).

## Class diagram

* **mail**
* **parsers**
* **prank**
* **smtp**
* Overall
