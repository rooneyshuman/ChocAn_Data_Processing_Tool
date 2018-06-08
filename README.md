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








### 6. Viewing Service Records
The service records are located in the following directory: `src/main/java/chocan/report`                                  Under file name  

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
Under the file name EFT.txt


### 11.  Testing Accounts
All user accounts and service codes can be found in the following directories:
  * Member accounts: `src/main/java/chocan/db/members.txt`
  * Manager accounts: `src/main/java/chocan/db/manangers.txt`
  * Provider accounts: `src/main/java/chocan/db/providers.txt`
  * Service Data: `src/main/java/chocan/db/provider directory.txt`

The following specific ID numbers and codes can also be used for testing:

Provider Terminal
  * Login: use Provider ID 700000001
  * Create Service Record: use Member ID 600000001, use Service Code 500001
Manager Terminal
  * Login: use Manager ID 800000001
  * Activate/Suspend Member: use Member ID 600000002
  * Activate/Suspend Provider: use Provider ID 700000002
  * Activate/Suspend Manager: use Manager ID 800000002 
  * Delete Member: use Member ID 600000002
  * Delete Provider: use Provider ID 700000002
  * Delete Manager: use Manager ID 800000002 

