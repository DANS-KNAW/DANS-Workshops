package workshop3.test

import java.io.{BufferedInputStream, File}

import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}
import resource.{ManagedResource, Using}
import workshop3.assignments.{Customer, FileIO, Order, Product}

class FileIOSpec extends FlatSpec with Matchers with OneInstancePerTest {

  def mis(s: String): ManagedResource[BufferedInputStream] =
    Using.fileInputStream(new File(getClass.getResource(s).toURI))

  "readCustomers" should "read a customer input and convert it to the appropriate objects" in {
    val customers = mis("/workshop3/testcustomerinput.csv").acquireAndGet(FileIO.readCustomers)

    customers should contain (Customer("123","Alice", "Anderson"))
    customers should contain (Customer("456","Bob", "Baboon"))
    customers should contain (Customer("789","Chris", "Carlson"))
    customers should not contain Customer("customer_id", "first_name", "last_name")
  }

  "readProducts" should "read a product input and convert it to the appropriate objects" in {
    val products = mis("/workshop3/testproductinput.csv").acquireAndGet(FileIO.readProducts)

    products should contain (Product("12345", "product1", 123.57))
    products should contain (Product("23456", "product2", 234.68))
    products should contain (Product("34567", "product3", 345.79))
    products should contain (Product("45678", "product4", 456.80))
    products should contain (Product("56789", "product5", 567.91))
    products should contain (Product("67890", "product6", 678.02))
  }

  "readOrders" should "read an order input and convert it to the appropriate objects" in {
    val orders = mis("/workshop3/testorderinput.csv").acquireAndGet(FileIO.readOrders)

    orders should contain (Order("34567", "123", 5))
    orders should contain (Order("12345", "123", 1))
    orders should contain (Order("23456", "789", 2))
    orders should contain (Order("67890", "456", 3))
    orders should contain (Order("67890", "123", 1))
    orders should contain (Order("34567", "789", 3))
  }

  "report1" should "write some lines" in {
    val orders = mis("/workshop3/testorderinput.csv").acquireAndGet(FileIO.readOrders)
    val products = mis("/workshop3/testproductinput.csv").acquireAndGet(FileIO.readProducts)
    val customers = mis("/workshop3/testcustomerinput.csv").acquireAndGet(FileIO.readCustomers)
    val report = FileIO.report1(orders, products, customers)

    report shouldBe List(
      "Alice Anderson wants 5 x product1",
      "Alice Anderson wants 1 x product1",
      "Chris Carlson wants 2 x product1",
      "Bob Baboon wants 3 x product1",
      "Alice Anderson wants 1 x product1",
      "Chris Carlson wants 3 x product1")
  }

  "report2" should "write some lines" in {
    val orders = mis("/workshop3/testorderinput.csv").acquireAndGet(FileIO.readOrders)
    val products = mis("/workshop3/testproductinput.csv").acquireAndGet(FileIO.readProducts)
    val customers = mis("/workshop3/testcustomerinput.csv").acquireAndGet(FileIO.readCustomers)
    val report = FileIO.report2(orders, products, customers)

    report shouldBe List(
      "Bob Baboon has to pay 370.71",
      "Alice Anderson has to pay 864.9899999999998",
      "Chris Carlson has to pay 617.8499999999999")
  }
}
