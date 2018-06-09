# Chocoholics Anonymous Data Processing Software
This project is a simulation of a data processing software for a health care system.
This software is not intended for real use.
This is Group 6's term project for CS 300 at Portland State University.

## Group 6 Team Members
* [Belén Bustamante](https://github.com/rooneyshuman)
* [Bounnoy Phanthavong](https://github.com/Bounnoy)
* [Bradley Odell](https://github.com/BTOdell)
* [Nick Battalia](https://github.com/Nbattalia)
* [Matthew Bui](https://github.com/mbui0529)
* [Ryan Campbell](https://github.com/cam28)

## Table of Contents
1. [Installation](https://github.com/Gilmore-PDX-CS/Team6/blob/master/README.md#1-installation)
2. [How to Run](https://github.com/Gilmore-PDX-CS/Team6#2-how-to-use)
3. [Main Menu](https://github.com/Gilmore-PDX-CS/Team6#3main-menu)
4. [Provider Menu](https://github.com/Gilmore-PDX-CS/Team6#4-provider-menu)
5. [Manager Menu](https://github.com/Gilmore-PDX-CS/Team6#5-manager-menu)
6. [Viewing Service Records](https://github.com/Gilmore-PDX-CS/Team6#6-viewing-service-records)
7. [Viewing Member Reports](https://github.com/Gilmore-PDX-CS/Team6#7-viewing-member-reports)
8. [Viewing Provider Reports](https://github.com/Gilmore-PDX-CS/Team6#8--viewing-provider-reports)
9. [Viewing Manager Reports](https://github.com/Gilmore-PDX-CS/Team6#9--viewing-manager-reports)
10. [Viewing EFT Records](https://github.com/Gilmore-PDX-CS/Team6#10--viewing-eft-records)
11. [Test Accounts](https://github.com/Gilmore-PDX-CS/Team6#11--testing-accounts)

### 1. Installation
You can either compile the program or download the zip file.

#### 1.1) Compiling
In order to run the program, it needs to be compiled. Download IntelliJ here: https://www.jetbrains.com/idea/download/

_Compile the program with IntelliJ:_
```
- Import the project. (Using the pom.xml file.)
- Go to File > Project Structure
- Click on Artifacts, then +, JAR, From modules with dependencies...
- Under Main Class, click ..., and select Main (chocan)
- Click OK until all popups disappear.
- Click on Build, then Build Artifacts, then Build again.
- chocan.jar should now be in the 'out/artifacts/chocan_jar' folder.
```

You will need to copy the `/db` and `/reports` folder inside the project to the same directory as the `chocan.jar` file before running the program.

#### 1.2) Downloading
Alternatively, you can download a working file [here](https://github.com/Gilmore-PDX-CS/Team6/tree/master/release/chocan1.0.zip) and extract the contents to a folder of your choice.

### 2. How to Run
After you have the jar file, open up your OS's command line interface and navigate to the folder that contains the program.

_Type this command to run the program:_

`java -jar chocan.jar`

If this command does not work, you will need to download [Java](https://www.java.com/en/download/) and install it.

The program uses a series of text menus. You will be prompted to input a number associated with the menu items. Simply type in a number to make your choice.

### 3. Main Menu

The main menu asks users to verify either if they are a provider or the manager.

```

-----------------------------------------
Main Menu
-----------------------------------------
1) Login to Provider Terminal
2) Login to Manager Terminal
3) Exit Program
Please select an option: 

```

### 4. Provider Menu

The provider menu asks users to enter a provider ID to verify their identification.

`Please enter a provider ID:`

If a valid ID is entered, the provider menu will be shown:

```-----------------------------------------
Provider Menu
-----------------------------------------
1) Create Service Record
2) View Provider Directory
3) Logout (Return to Main Menu)
Please select an option: 

```
#### 4.1) Create Service Record
Providers will be prompted to enter the member ID of their client:

`Please enter member ID: `

After the member ID is verified, providers need to enter the service date:

`Please enter a date (MM-DD-YYYY):`

After a valid date has been entered, an alphabetical list of provider services will be displayed:

```--------------------------------------------
Service name: Addiction Consultation
Service code: 500005
Service fee: $89.00
--------------------------------------------
Service name: Aerobics Exercise Session
Service code: 500001
Service fee: $42.00
--------------------------------------------
Service name: Blood Pressure Exam
Service code: 500004
Service fee: $27.00
--------------------------------------------
Service name: Blood Work
Service code: 500002
Service fee: $84.00
--------------------------------------------
Service name: Chocolate Patch
Service code: 500006
Service fee: $23.00
--------------------------------------------
Service name: Chocolate Pill
Service code: 500007
Service fee: $21.00
--------------------------------------------
Service name: Dietician Session
Service code: 500000
Service fee: $125.00
--------------------------------------------
Service name: Group Therapy Session
Service code: 500003
Service fee: $55.00
-----------------------------------------
```

Providers will need to enter a service code from the list.

`Please enter the service code: `

The name of the service will be displayed and the provider will be asked to verify it's the correct service.

`Is this the correct service? (Y/N):`

Providers will be able to enter optional comments:

`Please enter any comments (optional):`

A service record will then be generated in `/db/services.txt`

#### 4.2) View Provider Directory

This function shows all the available provider services.

#### 4.3) Logout (Return to Main Menu)

The provider will be logged out and returned to the main menu.

### 5. Manager Menu

The manager menu will ask users to enter a manager ID to verify their identification:

`Please enter a manager ID: `

If a valid ID has been entered, the manager menu will be shown:

``` -----------------------------------------
Manager Menu
-----------------------------------------
01) Add Member
02) Add Provider
03) Add Manager
04) Activate/Suspend Member
05) Activate/Suspend Provider
06) Activate/Suspend Manager
07) Delete Member
08) Delete Provider
09) Delete Manager
10) Generate Member Reports
11) Generate Provider Reports and EFT Records
12) Generate Manager Report
13) Logout (Return to Main Menu)

```

#### 5.1) Add Member

This function adds new members to the member database.

The member information contains the following fields:

```
Name:
Address:
City:
State:
Zip Code:
```

#### 5.2) Add Provider

This function adds new providers to the provider database.

The provider information contains the following fields:

```
Name:
Address:
City:
State:
Zip Code:
```

#### 5.3) Add Manager

This function adds new managers to the manager database.

The manager information contains the following fields:

```
Name:
Address:
City:
State:
Zip Code:
```

#### 5.4) Activate/Suspend Status For Members, Providers, Managers.

Option 4, 5, 6 on the menu behave in the same way to change the status of members, providers, or managers.

The users only need to enter the valid ID for members, providers, or managers to change the status.

#### 5.5) Delete Members, Providers, Managers

Option 7, 8, 9 on the menu behave in the same way to delete members, providers, or managers.

The users only need to enter the valid ID for members, providers, or managers.

#### 5.6) Generate Member Reports

This function generates reports for all members.

#### 5.7) Generate Provider Reports and EFT Records

This function generates the EFT records and reports for all providers.

#### 5.8) Generate Manager Report

This function generates provider summary reports for the manager.

#### 5.9) Logout (Return to Main Menu)

The manager will be logged out and returned to the main menu.

### 6. Viewing Service Records
The service records are located in the following directory: `/db/services.txt`

Under file name **services.txt**

### 7. Viewing Member Reports
The member reports can are located in the following directory: `/report/member`

The files will be saved as **name_date.txt**


### 8. Viewing Provider Reports
The provider reports are located in the following directory: `/report/provider`

The files will be saved as **name_date.txt**

### 9. Viewing Manager Reports
The manager reports are located in the following directory: `/report/manager`

The files will be saved as **name_date.txt**

### 10. Viewing EFT Records
The EFT records are located in the following directory: `/db`

Under the file name **EFT.txt**

### 11. Testing Accounts
All user accounts and service codes can be found in the following directories:
  * Member accounts: `/db/members.txt`
  * Manager accounts: `/db/manangers.txt`
  * Provider accounts: `/db/providers.txt`
  * Service Data: `/db/provider directory.txt`

The following specific ID numbers and codes can also be used for testing:

**Provider Terminal**
  * Login: use Provider ID 700000001
  * Create Service Record: use Member ID 600000001, use Service Code 500001
  
**Manager Terminal**
  * Login: use Manager ID 800000001
  * Activate/Suspend Member: use Member ID 600000002
  * Activate/Suspend Provider: use Provider ID 700000002
  * Activate/Suspend Manager: use Manager ID 800000002 
  * Delete Member: use Member ID 600000002
  * Delete Provider: use Provider ID 700000002
  * Delete Manager: use Manager ID 800000002




