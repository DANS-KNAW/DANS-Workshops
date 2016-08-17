// translated from https://github.com/frode-carlsen/scala-workshop/blob/master/scala-workshop/src/main/scala/oppgave6/FP.scala

sealed trait Employee
case class Developer(name: String, salary: Int) extends Employee
case class Manager(name: String, salary: Int, title: String) extends Employee

def fold[A](employee: Employee)(dev: Developer => A, man: Manager => A): A = {
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
  fold(employee)(_.salary, _.salary)
}

// returns the sum of all salaries
// HINT: use the previous function
def totalSalary(employees: List[Employee]): Int = {
  employees.map(salary).sum
}

// returns the name of the employee
def name(employee: Employee): String = {
  fold(employee)(_.name, _.name)
}

// returns the employee, but with the new salary
def setSalary(salary: Int, employee: Employee): Employee = {
  fold(employee)(changeDeveloperSalary(_ => salary),
                 changeManagerSalary(_ => salary))
}

// returns the employee, but with the increased salary
def raiseSalary(increase: Int, employee: Employee): Employee = {
  fold(employee)(changeDeveloperSalary(increaseByAmount(increase)),
                 changeManagerSalary(increaseByAmount(increase)))
}

// returns the list of employees, but with a raise for the manager's salary
def raiseManagerSalaries(increase: Int, employees: List[Employee]): List[Employee] = {
  employees.map(fold(_)(identity,
                        changeManagerSalary(increaseByAmount(increase))))
}

// returns the list of employees, but with a raise for the employee's salary
def raiseDeveloperSalaries(increase: Int, employees: List[Employee]): List[Employee] = {
  employees.map(fold(_)(changeDeveloperSalary(increaseByAmount(increase)),
                        identity))
}

// returns the list of employees, but with a percentage raise for the manager's salary
def raiseManagerSalariesByPercent(percent: Int, employees: List[Employee]): List[Employee] = {
  employees.map(fold(_)(identity,
                        changeManagerSalary(increaseByPercent(percent))))
}

// returns the list of employees, but with a percentage raise for the employee's salary
def raiseDeveloperSalariesByPercent(percent: Int, employees: List[Employee]): List[Employee] = {
  employees.map(fold(_)(changeDeveloperSalary(increaseByPercent(percent)),
                        identity))
}
