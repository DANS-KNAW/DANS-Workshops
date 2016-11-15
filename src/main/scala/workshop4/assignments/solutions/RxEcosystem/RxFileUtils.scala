package workshop4.assignments.solutions.RxEcosystem

import java.io.File
import java.nio.file.Path
import java.util

import rx.Observer
import rx.fileutils.{FileSystemEvent, FileSystemEventKind, FileSystemWatcher}
import rx.schedulers.Schedulers
import scala.collection.JavaConverters._

import scala.language.implicitConversions

class FileSystemEventObserver extends Observer[FileSystemEvent] {


  override def onCompleted(): Unit =
    println("completed")

  override def onError(throwable: Throwable): Unit =
    println(s"got exception $throwable ${throwable.getMessage}")

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
  override def onNext(event: FileSystemEvent): Unit =
  println(s"Got event ${event.getFileSystemEventKind} for ${event.getPath}")
}

/**
  * partial copy-paste-translate of the java testcase,
  * no more documentation found, not even JavaDoc
  */
object FileSystemEventObserver {

  def main(args: Array[String]): Unit = {


    val dir = new File("target/test/FileUtils")
    dir.mkdirs()

    val subscription = FileSystemWatcher
      .newBuilder()
      .addPaths(Map((dir.toPath, FileSystemEventKind.values()), (dir.toPath, FileSystemEventKind.values())).asJava)
      .addPath(dir.toPath, FileSystemEventKind.values(): _*) // triple time the same key, uses the last
      //.addPath(dir.toPath, FileSystemEventKind.ENTRY_DELETE) // would overwrite the previous
      .withScheduler(Schedulers.io())
      .withCurrentFsScanning(false) // did not see different behaviour with either value
      .build().subscribe(new FileSystemEventObserver)

    // run until receiving input (demonize?), causes InterruptedException (when executed with IDE)
    System.in.read()

    // comes before processing the interrupt
    println("Received input, shutting down")

    subscription.unsubscribe()
  }
}
