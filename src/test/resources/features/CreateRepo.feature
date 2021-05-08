Feature: Create new repository

  @wip
  Scenario: create new repo with min requirement and verify repo info
    Given request for creating new repo
    When request for retrieving the new repo
    Then new repo information matches expected