@vehicle
Feature: User should be able to read and verify the vehicle details

Scenario Outline: Read vehicle details from csv files and verify the details
  Given vehicle details are loaded from input directory "<inputFiles>"
  Given scanned and loaded the files for "<fileType>"
  Given the user is in dvla vehicle info page
  When user selects start search for the vehicle information
  And user enters registration number
  And user selects continue
  Then verify the vehicle details

  Examples:
  |inputFiles|fileType |
  |inputFiles|CSV      |
  |inputFiles|EXCEL    |





