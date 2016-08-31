package workshop3.test

import java.io.File

import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}
import resource.Using
import workshop3.assignments.{Customer, FileIO, Order, Product}

class FileIOSpec extends FlatSpec with Matchers with OneInstancePerTest {

  val fileIO = FileIO

  "readCustomers" should "read a customer input and convert it to the appropriate objects" in {
    val file = new File(getClass.getResource("/workshop3/testcustomerinput.csv").toURI)
    val customers = Using.fileInputStream(file).acquireAndGet(fileIO.readCustomers)

    customers should contain (Customer("123","Alice", "Anderson"))
    customers should contain (Customer("456","Bob", "Baboon"))
    customers should contain (Customer("789","Chris", "Carlson"))
    customers should not contain Customer("customer_id", "first_name", "last_name")
  }

  "readProducts" should "read a product input and convert it to the appropriate objects" in {
    val file = new File(getClass.getResource("/workshop3/testproductinput.csv").toURI)
    val products = Using.fileInputStream(file).acquireAndGet(fileIO.readProducts)

    products should contain (Product("12345", "product1", 123.57))
    products should contain (Product("23456", "product2", 234.68))
    products should contain (Product("34567", "product3", 345.79))
    products should contain (Product("45678", "product4", 456.80))
    products should contain (Product("56789", "product5", 567.91))
    products should contain (Product("67890", "product6", 678.02))
  }

  "readOrders" should "read an order input and convert it to the appropriate objects" in {
    val file = new File(getClass.getResource("/workshop3/testorderinput.csv").toURI)
    val orders = Using.fileInputStream(file).acquireAndGet(fileIO.readOrders)

    orders should contain (Order("34567", "123", 5))
    orders should contain (Order("12345", "123", 1))
    orders should contain (Order("23456", "789", 2))
    orders should contain (Order("67890", "456", 3))
    orders should contain (Order("67890", "123", 1))
    orders should contain (Order("34567", "789", 3))
  }
}
