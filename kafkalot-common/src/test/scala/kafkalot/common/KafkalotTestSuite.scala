package kafkalot.common

import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuiteLike, Matchers}

trait KafkalotTestSuite extends FunSuiteLike
  with Matchers with BeforeAndAfter with BeforeAndAfterAll
