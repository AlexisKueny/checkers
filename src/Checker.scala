import java.awt.Color

class Checker {
  val diam: Int = 100
  val spaceOccupancy: Array[Array[Int]] = Array.ofDim[Int](8, 4)
  val spaceCenterX: Array[Array[Int]] = Array.ofDim[Int](8, 4)
  val spaceCenterY: Array[Array[Int]] = Array.ofDim[Int](8, 4)
  val colW: Color = Color.RED //spaceOccupancy = 1
  val colB: Color = new Color(192,192,192) //spaceOccupancy = 2
  val colG : Color = Color.green

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
        if ( i < 3) spaceOccupancy(i)(j) = 1
        if ( i > 4) spaceOccupancy(i)(j) = 2
      }
    }
  }
  def checkSpace(px: Int,py: Int, player : Int):(Boolean,Int,Int) ={
    for (i <- 0 to 7;j <- 0 to 3){
      if ((px >= spaceCenterX(i)(j)) && (px <= spaceCenterX(i)(j) + 100)){
        if ((py >= spaceCenterY(i)(j)) && (py <= spaceCenterY(i)(j) + 100)){
          if (spaceOccupancy(i)(j) == player) return (true,i,j)
        }
      }
    }
    (false,0,0)
  }
  def colorMovesCheckerBlack(i : Int, j : Int): Unit = {

  }
}
