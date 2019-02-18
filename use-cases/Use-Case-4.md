# USE CASE: 4 Population Report

## CHARACTERISTIC INFORMATION

### Goal in Context

As an employee of my organisation with basic computer skills, I want to choose from continent, country, or region in order to create a list of the population of people living from my selection, and to list the population of people living in cities and to list people not living in cities. Columns: name, total population, total population living in cities (including percentage value) and total population not living in cities and total population not living in cities.

#### Scope

Company.

#### Level

Primary Task.

#### Preconditions

We know which columns to choose. Database contains continent, country and region.

#### Success End Condition 

A list can be produced containing people who live in cities and don't live in cities, showing continent, country and region.

#### Failed End Condition

No list is produced.

#### Primary Actor

Employee

#### Trigger

A request for list information is sent to the employee.

## Main Success Scenario

1. Employee chooses from continent, country or region.
2. Application requests data from database using SQL statements
3. Application generates report from returned data

## Extensions

None.

## Sub-Variations

None.

## Schedule

**DUE DATE**: Release 1.0