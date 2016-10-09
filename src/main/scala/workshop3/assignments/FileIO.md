FileIO
======

For this assignment you are given three CSV files with 'tables' of data:

* **customer.csv** contains the ID, firstname and lastname of a *customer*
* **product.csv** contains the ID, title and price of a *product*
* **order.csv** contains the productID, customerID and amount

We will assume that these CSV fiels do not contain 'weird' data and that we can read and split the data just by 
looking for `','` characters.

We have already defined three classes `Customer`, `Product` and `Order` that hold the data from 1 line of the CSV files.

The assignment is to read the data from these CSV files and to generate two reports, which are going to be written 
into two separate files.

* `report1.txt` will contain text formatted as follows: `<customer_name> wants <order_amount>x <product_name>`
* `report2.txt` will contain text formatted as follows: `<customer_name> has to pay <total_price>`

To read from file and write to file, we use the *Scala-ARM* library to do automatic resource management. Since this library
defines `map` and `flatMap` on its resources, you can use this in a for-comprehension (which you are going to do later in this assignment).

In order to keep things simple, the assignment is split into various subtasks.

1. Implement the functions `readCustomers`, `readProducts` and `readOrders` that each take an `InputStream` and return 
   a `List` of `Customer`, `Product` and `Order` respectively. To read from the `InputStream`s, use `scala.io.Source.fromInputStream` 
   and do some transformations with (higher-order) operators. You may assume that the input is not corrupted, so don't bother with 
   error handling and such things at this point. If the implementations are correct, the corresponding tests in `FileIOSpec` will pass.
2. (optional) The three functions defined in (1) look quite similar. Can you refactor these three functions to call one function 
   `read` that abstracts over the common stuff? Make sure the tests still pass (you don't need to write extra tests, though). 
   Hint: this new function can be higher-order.
3. Implement the function `report1` that takes the three lists that were read in (1) as its input parameters and returns a 
   `List[String]` containing lines formatted like `<customer_name> wants <order_amount>x <product_name>`. Hint: you can use 
   the `find` operator to search for a specific element in a list. If you're feeling fancy, you can add tests for this and 
   all following functions by yourself to `FileIOSpec`.
4. Implement the function `report2` that returns a `List[String]` containing lines formatted like 
   `<customer_name> has to pay <total_price>`.
5. Implement the function `generateReports` that combines the functions that you defined in 1-4 and returns a tuple of two 
   `List[String]`, resembling the reports as in `(report1, report2)`. Hint: only read the files once and use the references to the 
   lists twice!
6. Implement the function `writeReport` that given a report (`List[String]`) and an output (`BufferedWriter`) writes the report to 
   the output. Make sure that every element in the report starts on a new line. Hint: which aggregation operator would you use here 
   to make a `List[String]` into a single `String`?
7. Finally we will bring all the pieces together. To acquire the resources (input and output) we use the *Scala-ARM* library. One 
   of the central objects here is `Using`, which contains various functions to get resources. For the input you want to use 
   `Using.fileInputStream`, whereas for output you want to use `Using.fileWriter`. As mentioned before, *Scala-ARM* defines `map` 
   and `flatMap` on its types, so you can use a for-comprehension!
   1. Implement the value `reports` to get the input resources and generate the reports, such that the type of this value is 
      `ManagedResource[(List[String], List[String])]`. Hint: use `Using.fileInputStream`s inside the `for` block of the for-comprehension 
      and `generateReports` in the `yield`.
   2. Implement the value `reportWriting`, which is again a for-comprehension. Draw the tuple of reports from the `reports` value 
      you just implemented and use `Using.fileWriter` to get access to the output files. Given these reports and output files, you 
      can now call `writeReport` to write each report to file. **NOTE:** due to a bug in the Scala compiler, you cannot pattern match 
      the tuple in `reports` inside the for-comprehension!
8. The `ManagedResource` is a so-called lazy structure, meaning that it will not do anything unless you call some kind of `run` operator. 
   This is useful because you only want to read and write once everything is set up. We have already done this part for you in the 
   template using `acquireAndGet`. Since we have written everything to file, which returns a `Unit`, we will only print a message 
   saying that we are done with the job. If you have implemented all former subtasks correctly, you should be able to run the code 
   and see the two output files including the correct content.
