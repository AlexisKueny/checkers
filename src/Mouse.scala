import hevs.graphics.FunGraphics
import java.awt.event.{MouseAdapter, MouseEvent}

object Mouse extends App {
  // Let's make a window
  val fg = new FunGraphics(300, 300)

  // This will handle the mouse
  fg.addMouseListener(new MouseAdapter() {
    override def mouseClicked(e: MouseEvent): Unit = {
      val event = e
      val whichButton = e.getButton

      // Get the mouse position from the event
      val posx = event.getX
      val posy = event.getY

      println(s"Mouse position $posx - $posy with $whichButton")

      // Draws a circle where the mouse was during click
      fg.drawFilledCircle(posx, posy, 5)
    }
  })
}