package Session

import akka.actor.{Props, ActorSystem}
import akka.http.scaladsl.model.ws.{Message}
import akka.stream.scaladsl._

class GameSession(sessionId: String, actorSystem: ActorSystem) {
  private[this] val gameSessionActor = actorSystem.actorOf(Props(classOf[GameSessionActor], sessionId))

  def websocketFlow(gameEvent: String): Flow[Message, Message, _] = ???

  def sendMessage(msg: Message): Unit = gameSessionActor ! msg
}

object GameSession {
  def apply(sessionId: String)(implicit actorSystem: ActorSystem) = new GameSession(sessionId, actorSystem)
}
