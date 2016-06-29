package rholang.transforming

/*
import scalaz.State
import scalaz.State._
import scala.collection.immutable.HashMap
import scala.collection.immutable.HashMap._
import scalaz.std.map

/**
  * Created by weeeeeew on 2016-06-08.
  */
object NameGen {
  type NameGen[A] = State[Int, A]

  def NameGen[A](a : A) = State[Int,A](i => (i, a))

  def newName(name : String) : NameGen[String] = for {
    i <- get[Int]
    modify[Int](_ + 1)
  } yield name + i

  val newName : NameGen[String] = newName("var_")
}
 */
