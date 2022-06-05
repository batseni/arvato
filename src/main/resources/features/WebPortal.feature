Feature: Web portal UI automated tests
  Description: positive and negative cases based of the next scenarios:


  Scenario: Customer sees message that e-mail is sent
    Given customer is in a login page
    And and privacy settings accepted
    When customer selects create account link
    And customer inserts ’test@gmail.com’ e-mail address
    Then customer is redirected to the authentication confirmation email page
    And you got mail message is shown

  Scenario: Customer sees error message when invalid information inserted
    Given customer is in a login page
    When customer selects login with email and password option
    And customer inserts invalid email address
    Then error validation message is shown

  Scenario: Customer sees error message when information of non-existing customer is inserted
    Given customer is in a login page
    When customer selects login with email and password option
    And customer inserts test email address and some password
    Then customer is redirected to the email and password error page
    And error message is shown
