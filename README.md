# Prank SMTP client

## Description

**Prank SMTP client** is a program that allows you to send emails to a list of addresses that you provide. The list of mail addresses are split in group and the addresses of each group is picked as the sender, the rest are the receivers.
The user of the application can defined :

* the number of group
* the smpt server used
* the email contents

## Installation

* Install Java (OpenJDK or Oracle) and Apache Maven.
* Clone the repository and head to the config directory
    * **config.smtp.txt** is the file where you can define the number of groups, the smtp server address and port.
    * **messages.utf8.txt** is the file that contains the Subjects and contents of the mails you want to send.
    The format is as follows:
    ```
    Subject: [your subject here]
    
    [your message here]
    
    ===
    ```
    * **victims.txt** is the file where you write the list of all email addresses that you wish to use in your prank.
* Once you have edited or completed all the previous files, open a terminal and head to the project directory.
* Compile the project with the following command:
```
mvn clean package
```
* Run it with
```
$ java -jar target/SMTP_server_prank-1.0-SNAPSHOT.jar
```

Youn can also set up a test environement by using a mock SMTP server on your own computer:

* MockMock is a mock SMTP server: https://github.com/tweakers-dev/MockMock
* Open a teminal in the folder that contains the **jar** file and run:
```
$ java -jar MockMock.jar -p xxxx -ĥ yyyy
```

Where:

* ```-p xxxx``` stands for the local port for the SMTP server, default is 25.
* ```-h yyyy``` stands for the port used by the web interface, default is 8282.

* Connect to the http interface by browsing to 
```
127.0.0.1:yyyy
```

## Implementation

The project is divided in 4 packages :

* **mail** that contains the $JAVA$ files related to a mail, a group or a person.
* **parsers** that contains the $JAVA$ files that parse the files in the **config** directory.
* **prank** that contains the prank generator file (main program).
* **smtp** that contains the files that take care of the communication with the server (sending a mail).
