package workshop3.assignments

import java.io.{BufferedInputStream, BufferedWriter, File, InputStream}

import resource.{ManagedResource, Using}

import scala.io.Source

case class Customer(id: String, firstName: String, lastName: String)
case class Product(id: String, title: String, price: Double)
case class Order(productId: String, customerId: String, amount: Int)

object ReadWrite extends App {

  val customerFile = new File(getClass.getResource("/workshop3/customer.csv").toURI)
  val orderFile = new File(getClass.getResource("/workshop3/order.csv").toURI)
  val productFile = new File(getClass.getResource("/workshop3/product.csv").toURI)

  val report1File = new File("report1.txt")
  val report2File = new File("report2.txt")

  def read[T](in: InputStream)(transform: String => T): List[T] = {
    Source.fromInputStream(in)
      .getLines()
      .drop(1)
      .map(transform)
      .toList
  }

  def readCustomers(in: InputStream): List[Customer] = {
    read(in)(line => {
      val Array(id, first, last) = line.split(',')
      Customer(id, first, last)
    })
  }

  def readProducts(in: InputStream): List[Product] = {
    read(in)(line => {
      val Array(id, title, price) = line.split(',')
      Product(id, title, price.toDouble)
    })
  }

  def readOrders(in: InputStream): List[Order] = {
    read(in)(line => {
      val Array(product, customer, amount) = line.split(',')
      Order(product, customer, amount.toInt)
    })
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

    val r1 = report1(orders, products, customers)
    val r2 = report2(orders, products, customers)

    (r1, r2)
  }

  def writeReport(report: List[String], output: BufferedWriter): Unit = {
    output.write(report.mkString("\n"))
  }

  val reports: ManagedResource[(List[String], List[String])] = for {
    cIn <- Using.fileInputStream(customerFile)
    pIn <- Using.fileInputStream(productFile)
    oIn <- Using.fileInputStream(orderFile)
  } yield generateReports(cIn, pIn, oIn)

  val reportWriting: ManagedResource[Unit] = for {
    (report1, report2) <- reports
    r1 <- Using.fileWriter()(report1File)
    r2 <- Using.fileWriter()(report2File)
  } yield {
    writeReport(report1, r1)
    writeReport(report2, r2)
  }

  reportWriting.acquireAndGet(_ => println("done"))
}
