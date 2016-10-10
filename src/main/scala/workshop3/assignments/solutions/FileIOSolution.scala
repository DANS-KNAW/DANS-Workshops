package workshop3.assignments.solutions

import java.io.{BufferedInputStream, BufferedWriter, File, InputStream}

import resource.{ManagedResource, Using}
import workshop3.assignments.{Customer, Order, Product}

import scala.io.Source

object FileIOSolution extends App {

  val customerFile = new File(getClass.getResource("/workshop3/customer.csv").toURI)
  val orderFile = new File(getClass.getResource("/workshop3/order.csv").toURI)
  val productFile = new File(getClass.getResource("/workshop3/product.csv").toURI)

  val report1File = new File("report1.txt")
  val report2File = new File("report2.txt")

  // as all three files need to be read in the same way, we can give a general function `read`
  // with a transformer function of type `Array[String] => T`
  def read[T](in: InputStream)(transform: Array[String] => T): List[T] = {
    Source.fromInputStream(in)
      .getLines()
      .drop(1)
      .map(_.split(','))
      .map(transform)
      .toList
  }

  def readCustomers(in: InputStream): List[Customer] = {
    read(in) {
      case Array(id, first, last) => Customer(id, first, last)
    }
  }

  def readProducts(in: InputStream): List[Product] = {
    read(in) {
      case Array(id, title, price) => Product(id, title, price.toDouble)
    }
  }

  def readOrders(in: InputStream): List[Order] = {
    read(in) {
      case Array(product, customer, amount) => Order(product, customer, amount.toInt)
    }
  }

  def report1(orders: List[Order], products: List[Product], customers: List[Customer]): List[String] = {
    orders.map(order => {
      val reportLine = for {
        product <- products.find(product => product.id == order.productId)
        customer <- customers.find(customer => customer.id == order.customerId)
      } yield s"${customer.firstName} ${customer.lastName} wants ${order.amount}x ${product.title}"

      reportLine.getOrElse(s"CORRUPT ORDER: $order")
    })
  }

  def report2(orders: List[Order], products: List[Product], customers: List[Customer]): List[String] = {
    orders.groupBy(_.customerId)
      .map { case (customerId, ordersOfCustomer) =>
        val totals = ordersOfCustomer
          .map(order => {
            products.find(product => product.id == order.productId)
              .map(_.price * order.amount)
          })

        val reportLine = for {
          customer <- customers.find(_.id == customerId)
          t <- if (totals.exists(_.isEmpty)) Option.empty else Option(totals.map(_.get).sum)
        } yield s"${customer.firstName} ${customer.lastName} has to pay $t"

        reportLine.getOrElse(s"CORRUPT ORDERS for customer $customerId")
      }
      .toList
  }

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

  def writeReport(report: List[String], output: BufferedWriter): Unit = {
    output.write(report.mkString("\n"))
  }

  val reports: ManagedResource[(List[String], List[String])] = for {
    customers <- Using.fileInputStream(customerFile)
    products <- Using.fileInputStream(productFile)
    orders <- Using.fileInputStream(orderFile)
  } yield generateReports(customers, products, orders)

  val reportWriting: ManagedResource[Unit] = for {
    rs <- reports
    (report1, report2) = rs
    r1 <- Using.fileWriter()(report1File)
    r2 <- Using.fileWriter()(report2File)
  } yield {
    writeReport(report1, r1)
    writeReport(report2, r2)
  }

  reportWriting.acquireAndGet(_ => println("done"))
}
