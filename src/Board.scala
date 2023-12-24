import Mouse.fg
import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{MouseAdapter, MouseEvent}

class Board {
  val display: FunGraphics = new FunGraphics(1200, 800)
  var gamePhase : Int = 0
  val spaceOccupancy : Array[Array[Int]] = Array.ofDim[Int](4,8)
  val spaceCenterX : Array[Array[Int]] = Array.ofDim[Int](4,8)
  val spaceCenterY : Array[Array[Int]] = Array.ofDim[Int](4,8)

  def base : Unit = {
    drawGame
  }
  def mouse: Unit = {
    display.addMouseListener(new MouseAdapter() {
      override def mouseClicked(e: MouseEvent): Unit = {
        val event = e
        val whichButton = e.getButton

        // Get the mouse position from the event
        val posx = event.getX
        val posy = event.getY
        gamePhase match{
          //When left click
          case 0 => if ((posx > 50 && posx < 150)&&(posy > 50 && posy < 150)){
            //initial checker position
            var x : Int = 50
            var y : Int = - 50
            for (j <- 0 to 3) {
              y += 100
              x = 50
              for (i <- 0 to 3){
                x += 100
                spaceCenterX(i)(j) = x
                spaceCenterY(i)(j) = y
                if (j < 3) spaceOccupancy(i)(j) = 1
                if (j > 5) spaceOccupancy(i)(j) = 2
              }
            }
            //checker(400,400,Color.red)
            drawGame
          }

        }
        //println(s"Mouse position $posx - $posy with $whichButton")

        // Draws a circle where the mouse was during click
        //display.drawFilledCircle(posx, posy, 5)
      }
    })
  }
  def checker(x: Int,y: Int, color: Color) : Unit ={
    drawGame
    display.setColor(color)
    display.drawFilledCircle(x,y,80)
  }
  def drawGame : Unit ={
    for (x <- 0 to 200; y <- 0 to 800) display.setPixel(x, y, new Color(150, 121, 105))
    for (x <- 301 to 999 by 200; y <- 0 to 800 by 200)
      for (x <- x to x + 99; y <- y to y + 99) display.setPixel(x, y)
    for (x <- 201 to 999 by 200; y <- 100 to 800 by 200)
      for (x <- x to x + 99; y <- y to y + 99) display.setPixel(x, y)
    for (x <- 1000 to 1200; y <- 0 to 800) display.setPixel(x, y, new Color(150, 121, 105))
    //start button
    for (x <- 50 to 150; y <- 50 to 150) display.setPixel(x, y, new Color (92, 64, 51))
    display.drawString(60,100,"Start",Color.black,40)
    for (i <- 0 to 3;j <- 0 to 7) {
      if (spaceOccupancy(i)(j) == 1) {
        checker(spaceCenterX(i)(j), spaceCenterY(i)(j), Color.white)
      }
      if (spaceOccupancy(i)(j) == 2) {
        checker(spaceCenterX(i)(j), spaceCenterY(i)(j), Color.black)
      }
    }
  }
}


