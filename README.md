# Chocoholics Anonymous Data Processing Software
This project is a simulation of a data processing software for a health care system.
This software is not intended for real use.
This is Group 6's term project for CS 300 at Portland State University.

## Group 6 Team Members
* Belén Bustamante
* Bounnoy Phanthavong
* Bradley Odell
* Nick Battalia
* Matthew Bui
* Ryan Campbell

## Table of Contents
1. Installation
2. How to Use
3. Main Menu
4. Provider Menu
5. Manager Menu
6. Viewing Service Records
7. Viewing Member Reports
8. Viewing Provider Reports
9. Viewing Manager Reports
10. Viewing EFT Records
11. Test Accounts

### 1. Installation
In order to run the program, it needs to be compiled. Download IntelliJ here: https://www.jetbrains.com/idea/download/

This is a Maven project so it will install all the dependencies required for the program.

_Compile the program with IntelliJ:_
```
- Import the project. (Using the pom.xml file.)
- Build > Build Project
```

### 2. How to Use
`Run > Run 'Main'`

The program uses a series of text menus. You will be prompted to input a number associated with the menu items. Simply type in a number to make your choice.

### 3.Main Menu

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

First at all, the provider menu asks users to use the provider ID to verify their identification.

`Please enter a provider ID:`

If users input the invalid ID, the program prints out the following error massage until users input the valid IDs.

`Please enter 9 digit. Provider ID's begin with '7': `

Since users input valid ID, the provider menu will be shown up.

```-----------------------------------------
Provider Menu
-----------------------------------------
1) Create Service Record
2) View Provider Directory
3) Logout (Return to Main Menu)
Please select an option: 

```
4.1) Create Service Record
Providers need to put valid member IDs in the member database.

`Please enter member ID: `

If the providers enter invalid IDs, the program prompts the error massages to guide them.

`Please enter 9 digits. Member ID's begin with '6': `

Since member ID is verified, providers need to enter the valid service record date.

`Please enter a date (MM-DD-YYYY):`

After the valid date is put, the list of the available service information will show up. Here is an example.

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

Provider need to provide the valid service code.

`Please enter the service code: `

Invalid service codes cause an error massage to guide providers.

`Please enter 6 digits. Service ID's begin with '5': `

To make sure that service records are created correctly, the program prompts a confirming massage.

`Is this the correct service? (Y/N):`

The providers can enter yes, y , n, or no. The inputs are not case sensitive.

If the providers confirm right information, the providers are asked to put some optional comments.

`Please enter any comments (optional):`

After entering the comments, the program brings provider back to the provider menu.

In case that the providers input wrong information about the service records, they have to repeat the process until they enter the right information.

4.2) View Provider Directory

This function shows all the available service information.

4.3) Logout (Return to Main Menu)

This function ends the program session.


### 5. Manager Menu

First at all, the manager menu asks manager to use the manager ID to verify their identification.

`Please enter a manager ID: `

If users input the invalid ID, the program prints out the following error massage until users input the valid IDs.

`Please enter 9 digit. Provider ID's begin with '8': `

After the valid ID is input, the manager will be pop up.

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

5.1) Add Member

This function adds new members to the member database.

The member information needs to enter correctly following the form:

```
Name:
Address:
City:
State:
Zip Code:
```

If the information is invalid, the program guides users to enter the valid input.

5.2) Add Provider

This function adds new providers to the provider database.

The information needs to enter correctly following the form:


```
Name:
Address:
City:
State:
Zip Code:
```

If the information is invalid, the program guides users to enter the valid input.

5.3) Add Manager

This function adds new managers to the manager database.

The information needs to enter correctly following the form:

```
Name:
Address:
City:
State:
Zip Code:
```
If the information is invalid, the program guides users to enter the valid input.

5.4) Activate/Suspend Status For Members, Providers, Managers.

Option 4, 5, 6 on the menu behave at the same way to change activation status of members, providers, or managers.

The users only need to enter the valid ID for members, providers, or managers to change the status.

5.5) Delete Members, Providers, Managers

Option 7, 8, 9 on the menu behave at the same way to delete members, providers, or managers from its database.

The users only need to enter the valid ID for members, providers, or managers.

5.6) Generate Member Reports

This function shows member reports.

5.7) Generate Provider Reports and EFT Records

This function shows provider reports which are same with EFT records.

5.8) Generate Manager Report

This function shows manager reports.

5.9) Logout (Return to Main Menu)

This function ends the program session.






### 6. Viewing Service Records
The service records are located in the following directory: `src/main/java/chocan/db/services.txt`                           Under file name services.txt

### 7. Viewing Member Reports
The member reports can are located in the following directory: `src/main/java/chocan/report`
Under the file name

### 8.  Viewing Provider Reports
The provider reports are located in the following directory: `src/main/java/chocan/report`
Under the file name

### 9.  Viewing Manager Reports
The manager reports are located in the follwoing directory: `src/main/java/chocan/report`
Under the file name

### 10.  Viewing EFT Records
The EFT records are located in the following directory: `src/main/java/chocan/db/services.txt`
Under the file name EFT.txt




