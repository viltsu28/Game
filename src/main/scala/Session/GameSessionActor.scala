package Session

import akka.actor.{Actor, ActorRef}

import collection.mutable.LinkedHashMap


class GameSessionActor(id: String) extends Actor{

  val players = LinkedHashMap[String, PlayerWithActor]()
  var number = 0

  override def receive: Receive = {
    case PlayerJoined(player, actor) => {
      val newPlayer = Player(player.name)
      players += (player.name -> PlayerWithActor(newPlayer,actor))
    }
    case PlayerLeft(playerName) => {
      players -= playerName
    }
    case AddCounterRequest(player, num) => {
      number += num
      notifyNumChange()
    }
  }

  def notifyNumChange(): Unit = {players.values.foreach(_.actor ! NumberChanged(this.number) )}

}

trait GameEvent
case class PlayerJoined(player: Player, actorRef: ActorRef) extends  GameEvent
case class PlayerLeft(playerName: String) extends GameEvent
case class AddCounterRequest(playerName: String, num: Integer) extends GameEvent
case class NumberChanged(num: Integer) extends GameEvent

case class Player(name: String)
case class PlayerWithActor(player: Player, actor: ActorRef)

