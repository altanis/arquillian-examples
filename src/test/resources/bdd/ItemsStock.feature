@WebService
Feature: Items stock feature
    Tests behavior of Items Stock

Scenario: Items stock should contains previously added items
    Given Empty items stock
    When Adding "Orange"
    Then Stock contains "1" element