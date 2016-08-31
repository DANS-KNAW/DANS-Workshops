// translated from https://github.com/frode-carlsen/scala-workshop/blob/master/scala-workshop/src/main/scala/oppgave6/FP.scala

sealed abstract class Employee
case class Developer(name: String, salary: Int) extends Employee
case class Manager(name: String, salary: Int, title: String) extends Employee

// returns the salary of the employee
def salary(employee: Employee): Int = {
  employee match {
    case Developer(_, salary) => salary
    case Manager(_, salary, _) => salary
  }
}

// returns the sum of all salaries
// HINT: use the previous function
def totalSalary(employees: List[Employee]): Int = {
  employees.map(salary).sum
}

// returns the name of the employee
def name(employee: Employee): String = {
  employee match {
    case Developer(name, _) => name
    case Manager(name, _, _) => name
  }
}

// returns the employee, but with the new salary
def setSalary(salary: Int, employee: Employee): Employee = {
  employee match {
    case Developer(name, _) => Developer(name, salary)
    case Manager(name, _, title) => Manager(name, salary, title)
  }
}

// returns the employee, but with the increased salary
def raiseSalary(increase: Int, employee: Employee): Employee = {
  employee match {
    case Developer(name, salary) => Developer(name, salary + increase)
    case Manager(name, salary, title) => Manager(name, salary + increase, title)
  }
}

// returns the list of employees, but with a raise for the manager's salary
def raiseManagerSalaries(increase: Int, employees: List[Employee]): List[Employee] = {
  employees.map(_ match {
    case Manager(name, salary, title) => Manager(name, salary + increase, title)
    case x => x
  })
}

// returns the list of employees, but with a raise for the employee's salary
def raiseDeveloperSalaries(increase: Int, employees: List[Employee]): List[Employee] = {
  employees.map(_ match {
    case Developer(name, salary) => Developer(name, salary + increase)
    case x => x
  })
}

// returns the list of employees, but with a percentage raise for the manager's salary
def raiseManagerSalariesByPercent(percent: Int, employees: List[Employee]): List[Employee] = {
  employees.map(_ match {
    case Manager(name, salary, title) => Manager(name, (salary * (1 + percent / 100.0)).toInt, title)
    case x => x
  })
}

// returns the list of employees, but with a percentage raise for the employee's salary
def raiseDeveloperSalariesByPercent(percent: Int, employees: List[Employee]): List[Employee] = {
  employees.map(_ match {
    case Developer(name, salary) => Developer(name, (salary * (1 + percent / 100.0)).toInt)
    case x => x
  })
}
