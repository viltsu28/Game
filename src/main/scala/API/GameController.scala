package API
import akka.actor.ActorSystem
import akka.http.scaladsl.{Http, server}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.StatusCodes
import akka.actor._
import akka.util.Timeout
import akka.pattern.ask
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import scala.concurrent.duration._
import scala.io.StdIn
import Session._
import Session.GameSessions._

import scala.concurrent.Await





object GameController extends App{

  val host = "localhost"
  val port = 8080

  implicit val timeout = Timeout(5 seconds)
  implicit val system = ActorSystem("actor-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  /*
  case class SessionID(id: String, msg: String)
  object SessionIdJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val PortofolioFormats = jsonFormat2(SessionID)
  }
  import SessionIdJsonSupport._
  var gameSessions =  system.actorOf(Props[GameSessions], name = "GameSessionList")
  */




  val route : server.Route =
    get {
      pathPrefix("joinGame" / IntNumber){ sessionId =>
        //join to game session if there is game with given id
        println("Client join game request. SessionId: " + sessionId.toString())
        complete("Joined to game :" + sessionId.toString()  + " " +  StatusCodes.OK)
      }
    } ~
    delete {
      pathPrefix("deleteGame" / IntNumber) { sessionId =>
        println("Client request to delete game. SessionId: " + sessionId.toString())
        complete("Game deleted. SessionId: " + sessionId.toString() + " " + StatusCodes.OK)
      }
    }

  /*~
    post {
      path("createGame"){
        entity(as[SessionID]) { session =>
          println("Client create game request. SessionId: " + session.id)
          val future = gameSessions ? sessionExist(session.id)
          val result = Await.result(future, timeout.duration).asInstanceOf[Boolean]
          if (result){
            complete("Game session id exist already choose different id: " + session.id  + " " + StatusCodes.BadRequest)
          }else{
            val newSession =  system.actorOf(Props(new GameSessionActor(session.id)), name = "GameSession")
            gameSessions ! addGameSession(newSession)

            complete("Game created. SessionId: " + session.id  + " " + StatusCodes.OK)
          }
        }
      }
    }*/


  val bindingFuture = Http().bindAndHandle(route, host, port)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done


}
