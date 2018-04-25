import Session.GameSessionActor
import org.scalatest.{FunSuite, Matchers}

class test extends FunSuite with Matchers{

  test("should be able to make game session."){
    new GameSessionActor()
  }
}
