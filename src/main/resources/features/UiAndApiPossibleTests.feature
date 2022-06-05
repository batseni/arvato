Feature: Web portal UI and API tests
  Description: additional tests that can be automated


  Scenario: Customer sees error message when invalid bank id is used on mobile
    Given customer is in a login page
    When customer selects login with bank id on mobile
    And customer inserts invalid bank id
    Then customer redirected to invalid bank id page
    And error message is shown

  Scenario: Customer loges in when valid bank id is used on mobile
    Given customer is in a login page
    When customer selects login with bank id on mobile
    And customer inserts valid bank id
    Then customer redirected to authentication confirmation page

  Scenario: Customer sees error message when invalid bank id is used on desktop
    Given customer is in a login page
    When customer selects login with bank id on desktop
    And customer inserts invalid bank id
    Then customer redirected to invalid bank id page
    And error message is shown

  Scenario: Customer loges in when valid bank id is used on desktop
    Given customer is in a login page
    When customer selects login with bank id on desktop
    And customer inserts valid bank id
    Then customer redirected to authentication confirmation page

  Scenario: Customer loges in when valid email and password is used
    Given customer is in a login page
    When customer selects login with email and password
    And customer inserts valid email and password
    Then customer redirected to authentication confirmation page

  Scenario: Customer sees message that log in link is sent to email
    Given customer is in a login page
    When customer selects send me login link
    And customer inserts email address
    Then customer sees link is sent confirmation

  Scenario: Customer logges in when valid bank id is used on desktop
    Given customer is in a login page
    When customer selects login with bank id on desktop
    And customer inserts valid bank id
    Then customer redirected to authentication confirmation page

  Scenario: Bank account validation has negative response, if request is sent to incorrect uri
    Given the bank account validation request with incorrect uri
    When sample request is sent to the server
    Then server returns negative response
    And response body contains error message