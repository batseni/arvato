Feature: Tests for bank account verification API
  Description: The purpose of these tests is to cover flows for bank account verification


Scenario: Bank account validation returns isValid flag = true for valid IBANs
  Given the bank account validation request with a valid IBAN
  And valid authentication token
  When bank account validation request is sent to the server
  Then server responds with HTTP response 200 code
  And response body contains isValid true


Scenario: Bank account validation responds with 401 response code, if authentication token was not provided.
  Given the bank account validation request without a JWT token
  When sample request is sent to the server
  Then server responds with HTTP response 401 code
  And response body contains „Authorization has been denied for this request.“ message