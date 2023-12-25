import Mouse.fg
import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{MouseAdapter, MouseEvent}

object Main extends App {
  //Declarations/////////////////////////////////////////
  val display: FunGraphics = new FunGraphics(1200, 800)
  var gamePhase: Int = 0
  val checker: Checker = new Checker
  var px = 0
  var py = 0
  var mb = 0

  //Run/////////////////////////////////////////////////
  drawEmptyBoard()
  display.addMouseListener(new MouseAdapter() {
    override def mouseClicked(e: MouseEvent): Unit = {
      mb = e.getButton
      // Get the mouse position from the event
      px = e.getX
      py = e.getY
      //println(s"Mouse position $px - $py")
      // Draws a circle where the mouse was during click
      //display.drawFilledCircle(px, py, 5)

      //gamephase = 0 until start button pressed
      if ((gamePhase == 0) && (50 to 150).contains(px) && (50 to 150).contains(py)){
        gamePhase = 1
        //checker initialization
        checker.checkerInit()
        //boardWithCheckers()
        //draw all buttons
      }
    }
  })

  //methods///////////////////////////////////////////////
  /*def mouse(): (Int, Int, Int) = {
    var posx: Int = 0
    var posy: Int = 0
    var mouseButton: Int = 0
    display.addMouseListener(new MouseAdapter() {
      override def mouseClicked(e: MouseEvent): Unit = {
        val event = e
        mouseButton = e.getButton

        // Get the mouse position from the event
        posx = event.getX
        posy = event.getY
      }
    })
    (posx, posy, mouseButton)
  }*/
  def drawEmptyBoard(): Unit = {
    for (x <- 0 to 200; y <- 0 to 800) display.setPixel(x, y, new Color(150, 121, 105))
    for (x <- 301 to 999 by 200; y <- 0 to 800 by 200)
      for (x <- x to x + 99; y <- y to y + 99) display.setPixel(x, y)
    for (x <- 201 to 999 by 200; y <- 100 to 800 by 200)
      for (x <- x to x + 99; y <- y to y + 99) display.setPixel(x, y)
    for (x <- 1000 to 1200; y <- 0 to 800) display.setPixel(x, y, new Color(150, 121, 105))
    //start button
    for (x <- 50 to 150; y <- 50 to 150) display.setPixel(x, y, new Color(92, 64, 51))
    display.drawString(60, 100, "Start", Color.black, 40)
  }
  //Draw board with checkers
  def boardWithCheckers() : Unit ={
    drawEmptyBoard()
    for (i <- 0 to 7; j <- 0 to 3){
      if (checker.spaceOccupancy(i)(j) != 0){
        if (checker.spaceOccupancy(i)(j) == 1) display.setColor(checker.colB)
        else display.setColor(checker.colW)
        px = checker.spaceCenterX(i)(j)
        py = checker.spaceCenterY(i)(j)
        display.drawFilledCircle(px,py,checker.diam)
      }
    }
  }
}
