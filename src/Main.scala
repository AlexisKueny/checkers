import Mouse.fg
import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{MouseAdapter, MouseEvent}

object Main extends App {
  //Declarations/////////////////////////////////////////
  val display: FunGraphics = new FunGraphics(1200, 800)
  var gamePhase: Int = 0
  val checker: Checker = new Checker
  var player : Int = 0
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


      //gamephase = 0 until start button pressed
      if ((gamePhase == 0) && (50 to 150).contains(px) && (50 to 150).contains(py)){
        gamePhase = 1
        //checker initialization
        checker.checkerInit()
        boardWithCheckers()
        player = 2
      }
      //gamePhase = 1
      val tuple = checker.checkSpace(px,py,player) //returns boolean,i,j
      if (tuple._1){
        //Test if an adjacent space is green
        var ira = 0
        ira = checker.checkGreen(tuple._2,tuple._3,badj = true,player)
        //Test if hop over a checker
        var ir1 = 0
        //ir1 = checker.checkGreen(tuple._2,tuple._3,false,player)
        if (ira + ir1 != 0){
          boardWithCheckers()
        }
      }
    }
  })

  //methods///////////////////////////////////////////////
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
    //drawEmptyBoard()
    for (i <- 0 to 7; j <- 0 to 3){
      if (checker.spaceOccupancy(i)(j) != 0) {
        if (checker.spaceOccupancy(i)(j) == 1) display.setColor(checker.colB)
        else display.setColor(checker.colW)
        if (checker.spaceOccupancy(i)(j) == 2) display.setColor(checker.colW)
        if (checker.spaceOccupancy(i)(j) == 3) display.setColor(checker.colG)
        px = checker.spaceCenterX(i)(j)
        py = checker.spaceCenterY(i)(j)
//        println(i,j)
        display.drawFilledCircle(px,py,checker.diam)
        if(checker.spaceOccupancy(i)(j) <3) display.drawFilledCircle(px,py,checker.diam)
        else colorSpaceGreen(px,py)
      }
    }
  }
  def colorSpaceGreen(i: Int, i1: Int): Unit = {
    for (x <- px to (px + 100); y <- py to py + 100) display.setPixel(x,y)
  }
  //...
}
