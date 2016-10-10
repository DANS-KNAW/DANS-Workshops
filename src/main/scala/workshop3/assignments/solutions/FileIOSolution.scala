package workshop3.assignments.solutions

import java.io._

import resource.{ManagedResource, Using}

import scala.io.BufferedSource

import workshop3.assignments.{Customer, Order, Product}

/** a 'view' on the classes above, assuming customers have different names */
case class OrderLine(amount: Int, price: Double, title: String, firstName: String, lastName: String)

object OrderLine {
  def apply(orders: List[Order], products: List[Product], customers: List[Customer]
           ): List[OrderLine] = for {
    order <- orders
    customer = customers.find(customer => customer.id == order.customerId).get
    product = products.find(product => product.id == order.productId).get
  } yield OrderLine(order.amount, product.price, product.title, customer.firstName, customer.lastName)
}

/** another view */
case class LineTotal(firstName: String, lastName: String, total: Double)

object LineTotal {
  def apply(line: OrderLine): LineTotal =
    LineTotal(line.firstName, line.lastName, line.amount * line.price)

  def apply(orders: List[Order], products: List[Product], customers: List[Customer]): List[LineTotal] =
    OrderLine(orders, products, customers).map(LineTotal(_))
}

object FileIO {

  val customerFile = new File(getClass.getResource("/workshop3/customer.csv").toURI)
  val orderFile = new File(getClass.getResource("/workshop3/order.csv").toURI)
  val productFile = new File(getClass.getResource("/workshop3/product.csv").toURI)

  val report1File = new File("report1.txt")
  val report2File = new File("report2.txt")

  def readCustomers(in: InputStream): List[Customer] = {
    def create(l: Array[String]) = Customer(l(0), l(1), l(2))
    read(in, create)
  }

  def readProducts(in: InputStream): List[Product] = {
    def create(l: Array[String]) = Product(l(0), l(1), l(2).toDouble)
    read(in, create)
  }

  def readOrders(in: InputStream): List[Order] = {
    read(in, (l: Array[String]) => Order(l(0), l(1), l(2).toInt))
  }

  def read[T](in: InputStream, f: Array[String] => T): List[T] = {
    new BufferedSource(in).getLines().toList.tail.map { line =>
      f(line.split(","))
    }
  }

  /** @return lines formatted as: <customer_name> wants <order_amount>x <product_name> */
  def report1(orders: List[Order], products: List[Product], customers: List[Customer]): List[String] =
  OrderLine(orders, products, customers)
    .map(line => s"${line.firstName} ${line.lastName} wants ${line.amount} x ${line.title}")

  /** @return lines formatted as: <customer_name> has to pay <total_price> */
  def report2(orders: List[Order], products: List[Product], customers: List[Customer]): List[String] = {
    for {
      person <- LineTotal(orders, products, customers).groupBy(rec => (rec.firstName, rec.lastName))
      (firstName, lastName) = person._1
      total = person._2.map(_.total).sum
    } yield s"$firstName $lastName has to pay $total"
  }.toList

  def generateReports(customerInput: BufferedInputStream,
                      productInput: BufferedInputStream,
                      orderInput: BufferedInputStream): (List[String], List[String]) = {
    val orders = readOrders(orderInput)
    val products = readProducts(productInput)
    val customers = readCustomers(customerInput)
    val wants = report1(orders, products, customers)
    val hasToPay = report2(orders, products, customers)
    (wants, hasToPay)
  }

  def writeReport(report: List[String], output: BufferedWriter): Unit =
    output.write(report.mkString("\n"))

  val reports: ManagedResource[(List[String], List[String])] = for {
    orders <- Using.fileInputStream(orderFile) //.acquireAndGet(FileIO.readOrders)
    customers <- Using.fileInputStream(customerFile) //.acquireAndGet(FileIO.readCustomers)
    products <- Using.fileInputStream(productFile) //.acquireAndGet(FileIO.readProducts)
  } yield generateReports(customers, products, orders)

  val reportWriting: ManagedResource[Unit] = for {
    reps <- reports
    (r1, r2) = reps
    w1 <- Using.fileWriter()(new File("target/report1.txt"))
    w2 <- Using.fileWriter()(new File("target/report2.txt"))
    _ = writeReport(r1, new BufferedWriter(w1))
    _ = writeReport(r2, new BufferedWriter(w2))
  } yield ()

  reportWriting.acquireAndGet(_ => println("done"))
}
