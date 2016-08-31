package workshop3.assignments.solutions

import workshop3.assignments.{Developer, Employee, Manager}

object EmployeeSolution {
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
}

object EmployeeSolutionRefactored {
  def transform[A](employee: Employee)(dev: Developer => A, man: Manager => A): A = {
    employee match {
      case developer: Developer => dev(developer)
      case manager: Manager => man(manager)
    }
  }

  def changeDeveloperSalary(f: Int => Int) = {
    (developer: Developer) => developer.copy(salary = f(developer.salary))
  }

  def changeManagerSalary(f: Int => Int) = {
    (manager: Manager) => manager.copy(salary = f(manager.salary))
  }

  def increaseByAmount(amount: Int)(current: Int) = current + amount

  def increaseByPercent(percent: Int)(current: Int) = (current * (1 + percent / 100.0)).toInt

  // returns the salary of the employee
  def salary(employee: Employee): Int = {
    transform(employee)(_.salary, _.salary)
  }

  // returns the sum of all salaries
  // HINT: use the previous function
  def totalSalary(employees: List[Employee]): Int = {
    employees.map(salary).sum
  }

  // returns the name of the employee
  def name(employee: Employee): String = {
    transform(employee)(_.name, _.name)
  }

  // returns the employee, but with the new salary
  def setSalary(salary: Int, employee: Employee): Employee = {
    transform(employee)(changeDeveloperSalary(_ => salary),
      changeManagerSalary(_ => salary))
  }

  // returns the employee, but with the increased salary
  def raiseSalary(increase: Int, employee: Employee): Employee = {
    transform(employee)(changeDeveloperSalary(increaseByAmount(increase)),
      changeManagerSalary(increaseByAmount(increase)))
  }

  // returns the list of employees, but with a raise for the manager's salary
  def raiseManagerSalaries(increase: Int, employees: List[Employee]): List[Employee] = {
    employees.map(transform(_)(identity,
      changeManagerSalary(increaseByAmount(increase))))
  }

  // returns the list of employees, but with a raise for the employee's salary
  def raiseDeveloperSalaries(increase: Int, employees: List[Employee]): List[Employee] = {
    employees.map(transform(_)(changeDeveloperSalary(increaseByAmount(increase)),
      identity))
  }

  // returns the list of employees, but with a percentage raise for the manager's salary
  def raiseManagerSalariesByPercent(percent: Int, employees: List[Employee]): List[Employee] = {
    employees.map(transform(_)(identity,
      changeManagerSalary(increaseByPercent(percent))))
  }

  // returns the list of employees, but with a percentage raise for the employee's salary
  def raiseDeveloperSalariesByPercent(percent: Int, employees: List[Employee]): List[Employee] = {
    employees.map(transform(_)(changeDeveloperSalary(increaseByPercent(percent)),
      identity))
  }
}
