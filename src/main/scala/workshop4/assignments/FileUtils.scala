package workshop4.assignments

import java.io.File
import java.nio.file.Path
import java.util

import rx.fileutils.{FileSystemEvent, FileSystemEventKind, FileSystemWatcher}
import rx.functions.Action1
import rx.schedulers.Schedulers

import scala.language.implicitConversions
import rx.lang.scala.JavaConverters._
import rx.lang.scala.observers.TestSubscriber

/** partial copy-paste-translate of java testcase */
object FileUtils {

  val dir = new File("target/test/FileUtils")
  dir.mkdirs()


  val action: Action1[_ >: FileSystemEvent] = {
    // TOD fix compilation
    case huh => println(s"$huh ?")
  }

  def main(args: Array[String]): Unit = {

    // wanted
    val paths = Map((dir.toPath, FileSystemEventKind.values()))

    // what compiles
    val pathss = new util.HashMap[Path, Array[FileSystemEventKind]]()
    pathss.put(dir.toPath, FileSystemEventKind.values())

    FileSystemWatcher
      .newBuilder()
      .addPaths(pathss)
      .withScheduler(Schedulers.io())
      .build()
      .doOnNext(action).subscribe()
  }
}
