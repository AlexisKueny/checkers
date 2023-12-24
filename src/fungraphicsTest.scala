import Mouse.fg
import hevs.graphics.FunGraphics
import java.awt.event.{MouseAdapter, MouseEvent}

object fungraphicsTest extends App {
  val board = new Board
  board.drawGame
  board.mouse
}
