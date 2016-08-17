// translated from https://github.com/frode-carlsen/scala-workshop/blob/master/scala-workshop/src/main/scala/oppgave6/FP.scala

sealed abstract class Employee
case class Developer(name: String, salary: Int) extends Employee
case class Manager(name: String, salary: Int, title: String) extends Employee

// returns the salary of the employee
def salary(employee: Employee): Int = ???

// returns the sum of all salaries
// HINT: use the previous function
def totalSalary(employees: List[Employee]): Int = ???

// returns the name of the employee
def name(employee: Employee): String = ???

// returns the employee, but with the new salary
def setSalary(salary: Int, employee: Employee): Employee = ???

// returns the employee, but with the increased salary
def raiseSalary(increase: Int, employee: Employee): Employee = ???

// returns the list of employees, but with a raise for the manager's salary
def raiseManagerSalaries(increase: Int, employees: List[Employee]): List[Employee] = ???

// returns the list of employees, but with a raise for the employee's salary
def raiseDeveloperSalaries(increase: Int, employees: List[Employee]): List[Employee] = ???

// returns the list of employees, but with a percentage raise for the manager's salary
def raiseManagerSalariesByPercent(percent: Int, employees: List[Employee]): List[Employee] = ???

// returns the list of employees, but with a percentage raise for the employee's salary
def raiseDeveloperSalariesByPercent(percent: Int, employees: List[Employee]): List[Employee] = ???
