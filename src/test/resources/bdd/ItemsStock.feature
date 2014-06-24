Feature: Items validation feature
  Shows examples how item's name validation works

  Scenario Outline: Validating Item's name in REST API
    Given Standard items stock
    When Adding item with <Name>
    Then API return status is <httpReturnCode>
  Examples:
    | Name | httpReturnCode |
    | t    | 400            |
    | te   | 400            |
    | tes  | 201            |
    | test | 201            |