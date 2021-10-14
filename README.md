# ATM Program

## About

This program simulates the functionalities of an ATM software application.

## Installing the Program

To download and run the program
- Install Java 11.0.0 or later
- Download the ATM.jar [here](https://github.sydney.edu.au/SOFT2412-2021S2/w14_c3_group4/raw/master/ATM.jar)
- Place in suitable folder
- Open to run!

Note: Ensure that the file is in a directory that allows files to be created/written

This program contains two states: as a normal user, and as an admin user.

## Using the Program (User)

The user can use the ATM by entering their card credentials into the system. The user is prompted to enter their card number and card pin, which will be validated by the ATM.

If the user’s card details are valid, the user then has three options they can choose from:
- **Withdraw**: 
  - The user is prompted to enter an amount they would like to withdraw. As long as both the user’s card and the ATM have sufficient cash, there is no limit to the amount the user would like to withdraw.
- **Deposit**:
  - The user is prompted to enter an amount they would like to deposit using several buttons on the screen (as the ATM does not accept coins).
- **Balance**:
  - The user is shown their card’s current available balance.
 
Users are able to cancel their transaction at any time before finalisation.
	
## Using the Program (Admin)

The admin can login to their account using a set of admin id and passcode (the default is ID: 12345, passcode: 12345). 

Once logged in, the admin has access to 3 different options to change/view the ATM state:

- **Adding Atm Funds**:
  - The admin can add funds to the ATM at any time, these funds can then be accessed by the customer through the withdraw option
- **Viewing/Editing Accounts**:
  - The admin can view all the current accounts in the ATM, along with its balance
  - Within that screen, the admin can choose to remove current existing accounts or add a new account with $0 balance and a randomly generated account number

- **Viewing Transactions**:
  - The admin can view the transactions that take place within the ATM, including the transaction and account number along with the transaction amount and type
- **Viewing/Editing Cards**:
  - The admin can view the list of cards along with the account it is associated with, the card status, the issue date and the expiry date
  - The admin can refresh the system to log in any extra card being added
  - The admin can create a new card with an admin generated pin number which can be linked to an existing account number

		
## Testing the Program
The program can be tested using this set of gradle commands. These commands show the status of which tests have been passed
- ```Gradle clean build```
- ```Gradle test```

For a further summary, the jacoco report can be accessed through *build/reports/jacoco/test/html/index.html*

## Contribute/Collaborate to the program
Users can contribute and collaborate to the codebase by cloning the repository on their local machines and submitting pull requests. These requests will be reviewed by the team and approved accordingly. 

Users who are interested in conducting further testing can also add their own to the present testing framework.
