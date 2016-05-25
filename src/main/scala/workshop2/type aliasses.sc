/*
see also: "http://twitter.github.io/effectivescala/#Types and Generics-Type aliases"
 */

import java.util.concurrent.{ConcurrentHashMap, ConcurrentLinkedQueue}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/*
  use type aliasses to have
    - more expressiveness in the types and communicate meaning/purpose
    - less verbose code to write
 */

/* example 1:
  ex1 returns a List[String], but from the context it may not be clear
  what these Strings represent
 */
def ex1(age: Int): List[String] = {
  // do something
  ???
}


/*
  defining Name to be a String, your type becomes more expressive.
  now it is more clear that given an age, it will return a list of names,
    most likely of people that have that age.
 */
type Name = String
def ex1_improved(age: Int): List[Name] = {
  // do something
  ???
}


/* example 2:
    the code below is taken from Easy-Ingest-Flow;
    getDoi takes an xml element and some settings
    But what does it return?
      + The String is most likely the DOI.
      + But what does the Boolean mean?
        > When is it true or false?
        > Is it related to the DOI or is it independent?
      + This would be an excellent opportunity to make a type alias
        that gives a more descriptive name to the Boolean.
*/
/*
def getDoi(xml: Elem)(implicit s: Settings): Try[(String, Boolean)] = {
  // some implementation here
  ???
}
*/


/* example 3:
    if we refer in this class to a queue, we always mean a ConcurrentLinkedQueue
    and a map is always a ConcurrentHashMap[K, ConcurrentLinkedQueue[V]]
    why not simplify this by adding type aliasses to
      - communicate the purpose of these objects (being a Queue and
        a Map respectively)
      - add brevity for the reader
      - don't write the same code/types over and over
 */
class ConcurrentPool[K, V] {
  type Queue = ConcurrentLinkedQueue[V]
  type Map = ConcurrentHashMap[K, Queue]
  // do stuff
}

/* example 4:
    the code below is taken from Easy-Split-Multi-Deposit;
    - a dataset is a mapping from a key to a list of values
    - multiple datasets with their corresponding ID can be in a list
    - as these types are the backbone of this project, it is useful to
      give them names and be more expressive in that way.
 */
type DatasetID = String
type MultiDepositKey = String
type MultiDepositValues = List[String]
type Dataset = mutable.HashMap[MultiDepositKey, MultiDepositValues]
type Datasets = ListBuffer[(DatasetID, Dataset)]

/*
  in general:
    - do not alias types that are self-explanatory
    - don't use subclassing when an alias will do:
      > don't do: trait Datasets extends ListBuffer[(DatasetID, Dataset)]
      >   but do: type Datasets = ListBuffer[(DatasetID, Dataset)]
    - a type alias is NOT a new type like you do with trait, class and object
      they are just substitutes for the aliassed type
    - the best place to define a type alias is a package object
        when they need to be visible to the whole package (and beyond)
      if the type alias is only used internally, define it locally in a class, object or function
 */
