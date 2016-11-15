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

  val path1 = new File("target/test/FileUtils1/*").toPath // no events received
  val path2 = new File("target/test/FileUtils2").toPath

  val subscription = FileSystemWatcher
    .newBuilder()
    .addPaths(Map((path1, Array(FileSystemEventKind.ENTRY_CREATE)),(path2, FileSystemEventKind.values())).asJava)
    .addPath(path1, FileSystemEventKind.values(): _*) // same key, overrides previous
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
