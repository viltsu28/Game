package Session

import akka.actor.{Actor, ActorRef, ActorSystem}

import scala.collection.mutable.ArrayBuffer

object GameSessions{
  var gameSessions: Map[String, GameSession] = Map.empty[String, GameSession]

  def findOrCreate(id: String)(implicit actorSystem: ActorSystem): GameSession = gameSessions.getOrElse(id, createNewGameSession(id))

  private def createNewGameSession(id: String)(implicit actorSystem: ActorSystem): GameSession = {
    val gameSession = GameSession(id)
    gameSessions += id -> gameSession
    gameSession
  }
}






/*
object GameSessions{
  case class addGameSession(actorref: ActorRef)
  case class deleteGameSession(actorref: ActorRef)
  case class sessionExist(id: String)
  val sessionList: ArrayBuffer[ActorRef]  = ArrayBuffer()
}




class GameSessions extends Actor{
  import GameSessions._


  def lookForSession(id: String, buf: ArrayBuffer[ActorRef]): Any ={
  buf !
  }

  override def receive : Receive = {
    //case addGameSession(sessionId) => sessionList += sessionId
    case addGameSession(actorref) => sessionList += actorref
    case deleteGameSession(actorref) => sessionList -= actorref
    case sessionExist(sessionId) => if (sessionList.contains(sessionId)) sender() ! true else sender() ! false
  }

}
*/
