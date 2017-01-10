/**
 * Copyright (C) 2016 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package workshop4.assignments

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.{Button, ListView, TextField}
import javafx.scene.layout.{HBox, VBox}
import javafx.stage.{Stage, WindowEvent}

import com.sun.deploy.net.URLEncoder
import org.apache.commons.lang3.StringUtils
import org.json4s.JsonAST.{JArray, JString}
import org.json4s.native.JsonMethods
import rx.lang.scala.JavaConverters._
import rx.lang.scala.{Observable, Subscription}
import rx.observables.JavaFxObservable

import scala.io.Source
import scala.language.{implicitConversions, postfixOps}

class WikipediaSuggestion extends Application {

  def parseJSON(json: String): List[String] = {
    for {
      JArray(child) <- JsonMethods.parse(json)(1)
      JString(word) <- child
    } yield word
  }

  def searchWikipedia(word: String): Observable[List[String]] = {
    val url = s"https://en.wikipedia.org/w/api.php?action=opensearch&search=${URLEncoder.encode(word, "UTF-8")}"
    Observable.using(Source.fromURL(url))(r => Observable.just(r.mkString), _.close(), disposeEagerly = true)
      .map(parseJSON)
  }

  override def start(stage: Stage): Unit = {
    val textField = new TextField {
      setPromptText("search...")
      setMinHeight(30)
    }
    val button = new Button("search")
    val items = FXCollections.observableArrayList[String]

    val root = new VBox(8, new HBox(8, textField, button), new ListView[String](items)) {
      setPadding(new Insets(10))
    }

    // remove the lazy once you start implementing.
    // this is only here to make sure that application is initially running
    lazy val searchSubscription: Subscription = ???

    JavaFxObservable.fromWindowEvents(stage, WindowEvent.WINDOW_CLOSE_REQUEST).asScala
      .subscribe(_ => {
        println("bye bye")
        if (searchSubscription == null) {
          println("boom")
        }
        searchSubscription.unsubscribe()
      })

    stage.setScene(new Scene(root, 300, 400))
    stage.setTitle("Dictionary Suggestions")
    stage.show()
  }
}

object WikipediaSuggestion {
  def main(args: Array[String]) {
    Application.launch(classOf[WikipediaSuggestion])
  }
}
