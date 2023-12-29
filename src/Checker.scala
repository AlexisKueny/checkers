import Main.{checker, display}

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
  //Returns i and j based on px and py mouse position
  def checkSpace(px: Int,py: Int, player : Int):(Boolean,Int,Int) ={
    for (i <- 0 to 7;j <- 0 to 3){
      if ((px >= spaceCenterX(i)(j)) && (px <= spaceCenterX(i)(j) + 100)){
        if ((py >= spaceCenterY(i)(j)) && (py <= spaceCenterY(i)(j) + 100)){
          if (spaceOccupancy(i)(j) == player) return (true,i,j)
          else return  (false,i,j)
        }
      }
    }
    return (false,-1,-1)
  }
  def checkGreen(i: Int,j: Int,badj: Boolean,player : Int): Int = {
    var ir = 0
    var bip = false
    if (i % 2 == 0) bip = true
    if (player == 2){
      //player black
      if (badj){
        //scan adjacent empty spaces
        if (i == 0) return 0
        if (bip){
          //i is even
          if (spaceOccupancy(i-1)(j) == 0){
            ir = 10
            spaceOccupancy(i)(j) = 3
          }
          if (j + 1 <= 3){
            if (spaceOccupancy(i-1)(j+1) == 0){
              ir += 20
              spaceOccupancy(i-1)(j+1)= 3
            }
          }
        }
        else{
          //i is odd
          if (j -1 >= 0){
            if (spaceOccupancy(i-1)(j-1) == 0){
              ir = 11
              spaceOccupancy(i-1)(j-1) = 3
            }
          }
          if (spaceOccupancy(i-1)(j)==0){
            ir += 21
            spaceOccupancy(i-1)(j)=3
          }
        }
        return ir
      }
      else{
        //hop over a checker
      }
    }
    else{
      //player white
      if (badj){
        //test if neighbouring spaces are green
        if (i == 7) return 0
        else{
          if (bip){
            //case i is even
            if (spaceOccupancy(i+1)(j)==0){
              ir = 10
              spaceOccupancy(i + 1)(j) = 3
            }
            if (j + 1 < 4){
              if (spaceOccupancy(i+1)(j+1) == 0){
                ir += 20
                spaceOccupancy(i+1)(j+1) = 3
              }
            }
          }
          else{
            //case i is odd
            if (j - 1 >= 0){
              if (spaceOccupancy(i+1)(j-1) == 0){
                ir = 11
                spaceOccupancy(i+1)(j-1) = 3
              }
            }
            if (spaceOccupancy(i+1)(j)==0){
              ir += 21
              spaceOccupancy(i+1)(j)=3
            }
          }
          return ir
        }
      }
      //not adjacent
    }
    return ir
  }
  def clearGreen() : Unit ={
    for (i <- 0 to 7;j <- 0 to 3){
      if (spaceOccupancy(i)(j) == 3){
        println(i,j)
        spaceOccupancy(i)(j) = 0
        val xstart = checker.spaceCenterX(i)(j)
        val ystart = checker.spaceCenterY(i)(j)
        for (x <- xstart to (xstart + 100); y <- ystart to ystart + 100) display.setPixel(x,y,0)
      }
    }
  }
}
