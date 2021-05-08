Feature: Delete repo



  Scenario: delete a repo
    Given request for creating new repo
    When request for deleting the repo
    Then verify the repo is deleted