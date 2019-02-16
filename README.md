# slate_assignment
Todoist Android Assignment

Tools used: Appium with Java, Selenium, TestNG, Extent report for report, http client and Rest Assured for API.

Details:

I have designed the framework using POM (Page object model).

I have created util classes such as ApiUtils, Logger util, wait util, Appium utils for code reusability.

Each test case is made atomic and dynamic –

1.	First test case: test project is created via API, then response assertion is made, and once response is 200, then login to mobile app and verify the test project on the UI.

•	Note: Each time a unique and random test project name is created to make the case more robust.

2.	Second test case: A test project is created via API which is again unique and random (serves as test data) then login to mobile app, navigate to the created project , create an unique and random test task, then validate if the task is created on the UI, then now call the getTask API parse the response and verify if the task exists in the response.

3.	Third test case: A test project and test task is created via API which is again unique and random (serves as test data) then login to mobile app, navigate to the created task , Complete the task on the ui, Now call reopen API to reopen the task, Now validate on mobile to check if reopened correctly.

•	Note: I am fetching the taskid from active task API and then applying logic to fetch the required task id.

**Note:	Once Test suite is complete, i fetch all the project id via get projects API and then fetch each project id and use them to delete the project using delete a project API, this results in more atomic and independent framework and test data is not shared with other test cases (this is very important during parallel execution) 

**Addon which could be added:

1. We can add slack integration so that whenever the test case fails we can have notification on the slack, this can be done    via an API call, which needs to be called in method logTestResultsForFail in ExtentReportListener class.
2. Sause lab integration / Appium grid for parallel execution (Can explain more during face to face or code review round)
NOTE: Please feel free to reach out for any clarification during code review.
