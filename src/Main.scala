import hevs.graphics.FunGraphics
import java.awt.Color
import java.awt.event.{MouseAdapter, MouseEvent}

object Main extends App {
  //Declarations/////////////////////////////////////////
  val display: FunGraphics = new FunGraphics(1201, 801)
  var gamePhase: Int = 0
  val checker: Checker = new Checker
  var player: Int = 0
  var adversePlayer: Int = 0
  var px = 0
  var py = 0
  var mb = 0
  var currentI: Int = 0
  var currentJ: Int = 0
  var bSwitch: Boolean = false
  var bLock: Boolean = false
  var bInGame: Boolean = false
  var bKingSwitch : Boolean = false

  //Run/////////////////////////////////////////////////
  drawEmptyBoard()
  display.addMouseListener(new MouseAdapter() {
    override def mouseClicked(e: MouseEvent): Unit = {
      mb = e.getButton
      // Get the mouse position from the event
      px = e.getX
      py = e.getY
      val tuple = checker.checkSpace(px, py, player) //returns boolean,i,j
      bInGame = tuple._1
      val i = tuple._2
      val j = tuple._3

      //gamephase = 0 until start button pressed
      if ((gamePhase == 0) && (50 to 150).contains(px) && (50 to 150).contains(py)) {
        //checker initialization
        checker.checkerInit()
        gamePhase = 1
        checker.checkerTest()
        boardWithCheckers()
        player = 2
        adversePlayer = 1
        switchplayer()
      }
      else {
        if (gamePhase == 1) {
          //gamePhase = 1
          //Test if px and py are in the board
          if (bInGame) {
            //Left click
            if (mb == 1) {
              //Don't change
              if (!bLock) {
                currentI = i
                currentJ = j
                checker.clearGreen()
                boardWithCheckers()
                if (tuple._1) {
                  //Test if an adjacent space is green
                  if (math.abs(checker.spaceOccupancy(i)(j)) == player) {
                    var ira = 0
                    ira = checker.checkGreen(i, j, badj = true, player, bKing = false)
                    if (checker.spaceOccupancy(i)(j) < 0) checker.checkGreen(i, j, badj = true, adversePlayer, bKing = false)
                    //Test if hop over a checker
                    var ir1 = 0
                    checker.hopLeftClick(player, currentI, currentJ, bKing = false)
                    if (checker.spaceOccupancy(i)(j) < 0) checker.hopLeftClick(adversePlayer, currentI, currentJ, bKing = true)
                    boardWithCheckers()
                  }
                }
              }
            }
            //mouse right click
            if (mb == 3) {
              var iHop: Int = 2
              var iHopS2: Int = 1
              if (player == 2) {
                iHop = -2
                iHopS2 = -1
              }
              var booleanSwitch: Boolean = false
              if (checker.spaceOccupancy(currentI)(currentJ) < 0) {
                if ((i + 1 == currentI) || (i - 1 == currentI)) booleanSwitch = true
              }
              else {
                if (((player == 2) && (i + 1 == currentI)) || ((player == 1) && (i - 1 == currentI))) booleanSwitch = true
              }

              if (booleanSwitch) {
                //Only for adjacent movement
                if (checker.spaceOccupancy(i)(j) == 3) {
                  checker.clearGreen()
                  if (checker.spaceOccupancy(currentI)(currentJ) < 0) checker.spaceOccupancy(i)(j) = -player
                  else checker.spaceOccupancy(i)(j) = player
                  checker.spaceOccupancy(currentI)(currentJ) = 0
                  bSwitch = true
                }
                if ((player == 1) && (i == 7)) checker.kingPiece(i, j, player)
                if ((player == 2) && (i == 0)) checker.kingPiece(i, j, player)
              }
              else {
                //Movement with hops
                if (checker.spaceOccupancy(i)(j) == 3) {
                  if (math.abs(currentI - i) <= 2) {
                    bSwitch = false
                    booleanSwitch = false
                    if (checker.spaceOccupancy(currentI)(currentJ) < 0) {
                      if ((i == currentI + iHop) || (i == currentI - iHop)) booleanSwitch = true
                    }
                    else {
                      if (i == currentI + iHop) {
                        booleanSwitch = true
                      }
                    }
                    if (booleanSwitch) {
                      var fac: Int = 1
                      if ((checker.spaceOccupancy(currentI)(currentJ) == -2) && (i > currentI)) fac = -1
                      if ((checker.spaceOccupancy(currentI)(currentJ) == -1) && (i < currentI)) fac = -1

                      if (checker.spaceOccupancy(i)(j) == 3) {
                        if (j > currentJ) {
                          if (currentI % 2 == 0) checker.spaceOccupancy(currentI + iHopS2 * fac)(j) = 0
                          else checker.spaceOccupancy(currentI + iHopS2 * fac)(currentJ) = 0 //opposing checker
                        }
                        else {
                          if (currentI % 2 == 0) checker.spaceOccupancy(currentI + iHopS2 * fac)(currentJ) = 0
                          else checker.spaceOccupancy(currentI + iHopS2 * fac)(j) = 0 //opposing checker
                        }
                      }
                      checker.clearGreen()
                      if (checker.spaceOccupancy(currentI)(currentJ) < 0) checker.spaceOccupancy(i)(j) = -player
                      else checker.spaceOccupancy(i)(j) = player
                      checker.spaceOccupancy(currentI)(currentJ) = 0
                      currentI = i
                      currentJ = j
                      println("bugg",player,checker.spaceOccupancy(i)(j))

                      if (checker.hopLeftClick(player, currentI, currentJ, bKing = false) == 0) {
                        bSwitch = true
                        bLock = false
                      }
                      else bLock = true
                    }
                    if ((player == 1) && (i == 7)) checker.kingPiece(i, j, player)
                    if ((player == 2) && (i == 0)) checker.kingPiece(i, j, player)

                  }
                }
              }
              boardWithCheckers()
              if (bSwitch) {
                //Switch player
                if (player == 1) {
                  player = 2
                  adversePlayer = 1
                  switchplayer()
                }
                else {
                  player = 1
                  adversePlayer = 2
                  switchplayer()
                }
                bSwitch = false
              }
            }
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
        if (math.abs(checker.spaceOccupancy(i)(j)) == 1) display.setColor(checker.colB)
        else display.setColor(checker.colW)
        if (math.abs(checker.spaceOccupancy(i)(j)) == 2) display.setColor(checker.colW)
        if (checker.spaceOccupancy(i)(j) == 3) display.setColor(checker.colG)
        x = checker.spaceCenterX(i)(j)
        y = checker.spaceCenterY(i)(j)
        display.drawFilledCircle(x, y, checker.diam)
        if (checker.spaceOccupancy(i)(j) < 3) display.drawFilledCircle(x, y, checker.diam)
        else colorSpaceGreen(i, j)
        if (checker.spaceOccupancy(i)(j) < 0) display.drawFancyString(checker.spaceCenterX(i)(j) + 15, checker.spaceCenterY(i)(j) + 80, "K", Color.black, 80)
      }
      else {
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
  def switchplayer(): Unit = {
    for (x <- 50 to 200; y <- 150 to 250) display.setPixel(x, y, new Color(150, 121, 105))
    display.drawFancyString(10, 200, "Player", Color.black, 40)
    if (player == 2) display.setColor(checker.colW)
    else display.setColor(checker.colB)
    display.drawFilledCircle(155, 170, 30)
  }

  //...
}
