import java.awt.Color

class Checker {
  val diam : Int = 80
  val spaceOccupancy : Array[Array[Int]] = Array.ofDim[Int](4,8)
  val spaceCenterX : Array[Array[Int]] = Array.ofDim[Int](4,8)
  val spaceCenterY : Array[Array[Int]] = Array.ofDim[Int](4,8)
  val colB : Color = Color.RED //spaceOccupancy = 1
  val colW : Color = Color.WHITE //spaceOccupancy = 2

  def checkerInit() : Unit ={
    var x : Int = 50
    var y : Int = - 50
    for (j <- 0 to 7) {
      y += 100
      if (x == 50) x = 150
      if (x == 150) x = 50
      for (i <- 0 to 3) {
        x += 200
        println(x,y)
        spaceCenterX(i)(j) = x - diam/2
        spaceCenterY(i)(j) = y - diam/2
        if (j < 3) spaceOccupancy(i)(j) = 1
        if (j > 5) spaceOccupancy(i)(j) = 2
        println(x,i,y,j)
      }
    }
  }
}
