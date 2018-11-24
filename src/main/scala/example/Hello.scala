package example

import com.slikit.utils


trait Greeting {
  lazy val greeting: String = "hello"
}


object Hello extends Greeting with App {
  println(greeting)

  utils.helperOne.open
  utils.helperOne.close
}