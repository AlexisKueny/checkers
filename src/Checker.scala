import Main.{checker, display}
import java.awt.Color

class Checker {
  val diam: Int = 100
  val spaceOccupancy: Array[Array[Int]] = Array.ofDim[Int](8, 4)
  val spaceCenterX: Array[Array[Int]] = Array.ofDim[Int](8, 4)
  val spaceCenterY: Array[Array[Int]] = Array.ofDim[Int](8, 4)
  val colW: Color = Color.RED //spaceOccupancy = 1
  val colB: Color = new Color(192, 192, 192) //spaceOccupancy = 2
  val colG: Color = Color.green

  def checkerInit(): Unit = {
    var x: Int = 100
    var y: Int = -100
    for (i <- 0 to 7) {
      y += 100
      if (i % 2 == 0) x = 100
      if (i % 2 != 0) x = 0
      for (j <- 0 to 3) {
        x += 200
        spaceCenterX(i)(j) = x
        spaceCenterY(i)(j) = y
        //        println(x,y)
        if (i < 3) spaceOccupancy(i)(j) = 1
        if (i > 4) spaceOccupancy(i)(j) = 2
      }
    }
  }

  //Returns i and j based on px and py mouse position
  def checkSpace(px: Int, py: Int, player: Int): (Boolean, Int, Int) = {
    for (i <- 0 to 7; j <- 0 to 3) {
      if ((px >= spaceCenterX(i)(j)) && (px <= spaceCenterX(i)(j) + 100)) {
        if ((py >= spaceCenterY(i)(j)) && (py <= spaceCenterY(i)(j) + 100)) {
          if (math.abs(spaceOccupancy(i)(j)) == player) return (true, i, j)
          else return (false, i, j)
        }
      }
    }
    (false, -1, -1)
  }

  def checkGreen(i: Int, j: Int, badj: Boolean, player: Int, bKing : Boolean): Int = {
    println("checkGreen",bKing)
    var adversePlayer = 2
    if (player == 2) adversePlayer = 1
    var ir = 0
    var bip = false
    var bSwitch = false
    if (i % 2 == 0) bip = true
    if (player == 2) {
      //player black
      if (badj) {
        //scan adjacent empty spaces
        if (i == 0) return 0
        if (bip) {
          //i is even
          if (spaceOccupancy(i - 1)(j) == 0) {
            ir = 10
            spaceOccupancy(i - 1)(j) = 3
          }
          if (j + 1 <= 3) {
            if (spaceOccupancy(i - 1)(j + 1) == 0) {
              ir += 20
              spaceOccupancy(i - 1)(j + 1) = 3
            }
          }
        }
        else {
          //i is odd
          if (j - 1 >= 0) {
            if (spaceOccupancy(i - 1)(j - 1) == 0) {
              ir = 11
              spaceOccupancy(i - 1)(j - 1) = 3
            }
          }
          if (spaceOccupancy(i - 1)(j) == 0) {
            ir += 21
            spaceOccupancy(i - 1)(j) = 3
          }
        }
        return ir
      }
      else {
        //hop over a checker
        if (i <= 1) return 0
        if (bip) {
          //i is even
          bSwitch = false
          if (bKing) {
            if (math.abs(spaceOccupancy(i - 1)(j)) == player) bSwitch = true
            println("toto",spaceOccupancy(i-1)(j),player)
          }
          else {
            if (math.abs(spaceOccupancy(i - 1)(j)) == adversePlayer) bSwitch = true
          }
          println("BooleanTest",bSwitch,bKing)
          if (bSwitch) { //?
            if (spaceOccupancy(i - 2)(j-1) == 0) {
              ir = 40
              spaceOccupancy(i - 2)(j-1) = 3
            }
          }
          if (j != 3) {
            bSwitch = false
            if (bKing){
              if (math.abs(spaceOccupancy(i - 1)(j + 1)) == player) bSwitch = true
              println("toto2",spaceOccupancy(i-1)(j),player)
            }
            else{
              if (math.abs(spaceOccupancy(i - 1)(j + 1)) == adversePlayer) bSwitch = true
            }
            if (bSwitch) {
              if (spaceOccupancy(i - 2)(j + 1) == 0) {
                if (ir == 0) ir = 42
                if (ir == 40) ir = 50
                spaceOccupancy(i - 2)(j + 1) = 3
              }
            }
          }
        }
        else {
          //i is odd
          if (j - 1 >= 0) {
            bSwitch = false
            if (bKing){
              if (math.abs(spaceOccupancy(i - 1)(j - 1)) == player) bSwitch = true
              println("toto3",spaceOccupancy(i-1)(j),player)
            }
            else{
              if (math.abs(spaceOccupancy(i - 1)(j - 1)) == adversePlayer) bSwitch = true
            }
            if (bSwitch) {
              if (spaceOccupancy(i - 2)(j - 1) == 0) {
                ir = 41
                spaceOccupancy(i - 2)(j - 1) = 3
              }
            }
          }
          if (j < 3) {
            bSwitch = false
            if (bKing){
              if (math.abs(spaceOccupancy(i - 1)(j)) == player) bSwitch = true
            }
            else{
              if (math.abs(spaceOccupancy(i - 1)(j)) == adversePlayer) bSwitch = true
            }
            if (bSwitch) {
              if (spaceOccupancy(i - 2)(j + 1) == 0) {
                spaceOccupancy(i - 2)(j + 1) = 3
                if (ir == 41) ir = 51
                else ir = 43
              }
            }
          }
        }
      }
    }
    else {
      //player white
      if (badj) {
        //test if neighbouring spaces are green
        if (i == 7) return 0
        else {
          if (bip) {
            //case i is even
            if (spaceOccupancy(i + 1)(j) == 0) {
              ir = 10
              spaceOccupancy(i + 1)(j) = 3
            }
            if (j + 1 < 4) {
              if (spaceOccupancy(i + 1)(j + 1) == 0) {
                ir += 20
                spaceOccupancy(i + 1)(j + 1) = 3
              }
            }
          }
          else {
            //case i is odd
            if (j - 1 >= 0) {
              if (spaceOccupancy(i + 1)(j - 1) == 0) {
                ir = 11
                spaceOccupancy(i + 1)(j - 1) = 3
              }
            }
            if (spaceOccupancy(i + 1)(j) == 0) {
              ir += 21
              spaceOccupancy(i + 1)(j) = 3
            }
          }
          return ir
        }
      }
      //not adjacent
      if (i >= 6) return 0
      if (bip) {
        //i is even
        if (j >= 1) {
          bSwitch = false
          if(bKing){
            if (math.abs(spaceOccupancy(i + 1)(j)) == player) bSwitch = true
          }
          else{
            if (math.abs(spaceOccupancy(i + 1)(j)) == adversePlayer) bSwitch = true
          }
          if (bSwitch) {
            if (spaceOccupancy(i + 2)(j - 1) == 0) {
              ir = 40
              spaceOccupancy(i + 2)(j - 1) = 3
            }
          }
        }
        if (j != 3) {
          bSwitch = false
          if(bKing){
            if (math.abs(spaceOccupancy(i + 1)(j + 1)) == player) bSwitch = true
          }
          else{
            if (math.abs(spaceOccupancy(i + 1)(j+1)) == adversePlayer) bSwitch = true
          }
          if (bSwitch) {
            if (spaceOccupancy(i + 2)(j + 1) == 0) {
              if (ir == 40) ir = 50
              else ir = 42
              spaceOccupancy(i + 2)(j + 1) = 3
            }
          }
        }
      }
      else {
        //i is odd
        if (j != 3) {
          bSwitch = false
          if(bKing){
            if (math.abs(spaceOccupancy(i + 1)(j)) == player) bSwitch = true
          }
          else{
            if (math.abs(spaceOccupancy(i + 1)(j)) == adversePlayer) bSwitch = true
          }
          if (bSwitch) {
            if (spaceOccupancy(i + 2)(j + 1) == 0) {
              ir = 41
              spaceOccupancy(i + 2)(j + 1) = 3
            }
          }
        }
        if (j - 1 >= 0) {
          bSwitch = false
          if(bKing){
            if (math.abs(spaceOccupancy(i + 1)(j - 1)) == player) bSwitch = true
          }
          else{
            if (math.abs(spaceOccupancy(i + 1)(j - 1)) == adversePlayer) bSwitch = true
          }
          if (bSwitch) {
            if (spaceOccupancy(i + 2)(j - 1) == 0) {
              spaceOccupancy(i + 2)(j - 1) = 3
              if (ir == 41) ir = 51
              else ir = 43
            }
          }
        }
      }
    }
    ir
  }

  def clearGreen(): Unit = {
    for (i <- 0 to 7; j <- 0 to 3) {
      if (spaceOccupancy(i)(j) == 3) {
        spaceOccupancy(i)(j) = 0
        val xstart = checker.spaceCenterX(i)(j)
        val ystart = checker.spaceCenterY(i)(j)
        for (x <- xstart to (xstart + 100); y <- ystart to ystart + 100) display.setPixel(x, y, 0)
      }
    }
  }

  //Multiple hops
  def hopLeftClick(player: Int, iStart: Int, jStart: Int,bKing: Boolean): Int = {
    var iRet: Int = 0
    var ir: Int = 0
    iRet = checkGreen(iStart, jStart, badj = false, player,bKing)
    if (iRet == 0) return iRet
    if (player == 2) { //red?
      for (is <- iStart - 2 to 2 by -2) {
        iRet = 0
        for (js <- 0 to 3) {
          if (spaceOccupancy(is)(js) == 3) {
            ir = checkGreen(is, js, badj = false, player,bKing)
            iRet += ir
          }
        }
        if (iRet == 0) return 1
      }
      1
    }
    else {
      //player 1
      for (is <- iStart + 2 to 5 by 2) {
        iRet = 0
        for (js <- 0 to 3) {
          if (spaceOccupancy(is)(js) == 3) {
            ir = checkGreen(is, js, badj = false, player,bKing)
            iRet += ir
          }
        }
        if (iRet == 0) return 1
      }
      1
    }
  }
  def kingPiece (i: Int,j: Int,player: Int): Unit = {
    if (player == 2) spaceOccupancy(i)(j) = -2
    else spaceOccupancy(i)(j) = -1
  }

  def checkerTest(): Unit = {
    for (i <- 0 to 7; j <- 0 to 3) {
      if (spaceOccupancy(i)(j) != 3) spaceOccupancy(i)(j) = 0
    }
    spaceOccupancy(4)(2) = -2
    spaceOccupancy(3)(1) = -2
    spaceOccupancy(1)(1) = 1
    spaceOccupancy(2)(0) = 1
    spaceOccupancy(5)(3) = 1
    spaceOccupancy(5)(2) = 1
    spaceOccupancy(3)(2) = 1
    spaceOccupancy(3)(3) = 1
    spaceOccupancy(4)(1) = 2
    spaceOccupancy(6)(1) = 2
  }
}
