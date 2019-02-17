# Use Case 3# Produce a Capital City Report

## Characteristic Information

### Goal in Context
As an company *Employee* I want to *view/produce a report of all capital cities* so that
I can show the *capital cities listed from largest to smallest populations*.

### Scope
Company

### Level
Primary Task

### Preconditions
We know the role. The database contains the information required to generate this report.

### Success End Condition
A report of this nature is available for company employees to view.

### Failed End Condition
No report is created.

### Primary Actor
Employee

### Trigger
A request for the report is sent to the system.

## Main Success Scenario
1. Employee selects "Capital City Report".
2. Employee selects "World", "Continent" or "Region".
3. Employee selects how many "Capital Cities" should be returned.
4. Application takes input and requests data from database through SQL statements.
5. Application receives data from DB and creates report and provides it to employee.

## Extensions
None

## Sub-Variations
None

## Schedule
Due Date: Release 1.0