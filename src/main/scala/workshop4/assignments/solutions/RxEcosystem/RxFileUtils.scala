package workshop4.assignments.solutions.RxEcosystem

import java.io.File

import rx.fileutils.{FileSystemEventKind, FileSystemWatcher}
import rx.lang.scala.JavaConverters._
import rx.schedulers.Schedulers

import scala.collection.JavaConverters._
import scala.language.implicitConversions

/**
  * If a file exists on startup and gets changed after startup,
  * you get a create for the folder and for the file both with an absolute path
  * then a delete of the file with a relative path though it still exists.
  *
  * Another change results in a modify for the folder and file with absolute paths.
  *
  * Deleting a file causes a modify of the folder and delete for the file.
  *
  * Copy pasting a file a create (3,28 GB) at folder and file level and just a modify at file level,
  * looks like all at once.
  */
object Example extends App {

  // val dir = new File("target/test/FileUtils/*") creates folder with name '*' !!!
  val dir = new File("target/test/FileUtils")
  dir.mkdirs()

  val subscription = FileSystemWatcher
    .newBuilder()
    .addPaths(Map((dir.toPath, FileSystemEventKind.values()), (dir.toPath, FileSystemEventKind.values())).asJava)
    .addPath(dir.toPath, FileSystemEventKind.values(): _*) // triple time the same keyvalue pairs, receiving events once
    //.addPath(dir.toPath, FileSystemEventKind.ENTRY_DELETE) // would overwrite the previous
    .withScheduler(Schedulers.io())
    .withCurrentFsScanning(false) // did not see different behaviour with either value
    .build()
    .asScala
    .doOnNext { e => println(s"Got event ${e.getFileSystemEventKind} for ${e.getPath}") }
    .doOnError { t => println(s"got exception $t with message ${t.getMessage}") }
    .doOnCompleted(println(s"got completed"))
    .subscribe()

  // run until receiving input (demonize?), causes InterruptedException (when executed with IDE)
  System.in.read()

  // comes before processing the interrupt mentioned above
  println("Received input, shutting down")

  subscription.unsubscribe()
}
