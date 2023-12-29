import Mouse.fg
import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{MouseAdapter, MouseEvent}

object Main extends App {
  //Declarations/////////////////////////////////////////
  val display: FunGraphics = new FunGraphics(1200, 800)
  var gamePhase: Int = 0
  val checker: Checker = new Checker
  var player: Int = 0
  var px = 0
  var py = 0
  var mb = 0
  var currentI: Int = 0
  var currentJ: Int = 0

  //Run/////////////////////////////////////////////////
  drawEmptyBoard()
  display.addMouseListener(new MouseAdapter() {
    override def mouseClicked(e: MouseEvent): Unit = {
      mb = e.getButton
      // Get the mouse position from the event
      px = e.getX
      py = e.getY
      var tuple = checker.checkSpace(px, py, player) //returns boolean,i,j
      var i = tuple._2
      var j = tuple._3


      //gamephase = 0 until start button pressed
      if ((gamePhase == 0) && (50 to 150).contains(px) && (50 to 150).contains(py)) {
        gamePhase = 1
        //checker initialization
        checker.checkerInit()
        boardWithCheckers()
        player = 2
        switchplayer()
      }
      //gamePhase = 1
      if (mb == 1) {
        currentI = i
        currentJ = j
        checker.clearGreen()
        boardWithCheckers()
        println(checker.spaceOccupancy(4)(0), checker.spaceOccupancy(4)(1))
        //var tuple = checker.checkSpace(px,py,player)
        if (tuple._1) {
          //Test if an adjacent space is green
          var ira = 0
          ira = checker.checkGreen(i, j, badj = true, player)
          //Test if hop over a checker
          var ir1 = 0
          println(ira, ir1)
          //ir1 = checker.checkGreen(tuple._2,tuple._3,false,player)
          if (ira + ir1 != 0) {
            boardWithCheckers()
          }
        }
      }
      if (mb == 3) {
        //println(i,j,checker.spaceOccupancy(i)(j),tuple._1)
        if (checker.spaceOccupancy(i)(j) == 3) {
          checker.clearGreen()
          checker.spaceOccupancy(i)(j) = player
          checker.spaceOccupancy(currentI)(currentJ) = 0
          println(checker.spaceOccupancy(currentI)(currentJ), currentI, currentJ)
          println(checker.spaceOccupancy(i)(j), i, j, player)
          boardWithCheckers()
          if (player == 1){
            player = 2
            switchplayer()
          }
          else{
            player = 1
            switchplayer()
          }
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
  def boardWithCheckers(): Unit = {
    //drawEmptyBoard()
    for (i <- 0 to 7; j <- 0 to 3) {
      var x = 0
      var y = 0
      if (checker.spaceOccupancy(i)(j) != 0) {
        if (checker.spaceOccupancy(i)(j) == 1) display.setColor(checker.colB)
        else display.setColor(checker.colW)
        if (checker.spaceOccupancy(i)(j) == 2) display.setColor(checker.colW)
        if (checker.spaceOccupancy(i)(j) == 3) display.setColor(checker.colG)
        x = checker.spaceCenterX(i)(j)
        y = checker.spaceCenterY(i)(j)
        //        println(i,j)
        display.drawFilledCircle(x, y, checker.diam)
        if (checker.spaceOccupancy(i)(j) < 3) display.drawFilledCircle(x, y, checker.diam)
        else colorSpaceGreen(i, j)
      }
      else{
        display.setColor(Color.BLACK)
        val xstart = checker.spaceCenterX(i)(j)
        val ystart = checker.spaceCenterY(i)(j)
        for (x <- xstart to (xstart + 100); y <- ystart to ystart + 100) display.setPixel(x, y)
      }
    }
  }
  def colorSpaceGreen(i: Int, j: Int): Unit = {
    val xstart = checker.spaceCenterX(i)(j)
    val ystart = checker.spaceCenterY(i)(j)
    for (x <- xstart to (xstart + 100); y <- ystart to ystart + 100) display.setPixel(x, y)
  }
  //Player change
  def switchplayer() : Unit = {
    for (x <- 50 to 200; y <- 150 to 250) display.setPixel(x, y, new Color(150, 121, 105))
    display.drawFancyString(10, 200,"Player", Color.black, 40)
    if (player == 2) display.setColor(checker.colW)
    else display.setColor(checker.colB)
    display.drawFilledCircle(155,170,30)
  }
  //...
}
