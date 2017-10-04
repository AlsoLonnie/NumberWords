/*
 * RopeSpec.scala
 *
 * Copyright 2017 Lonnie Pryor <lonnie@pryor.us.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.us.pryor.numberwords

import org.scalatest._

/**
 * Test suite for the rope ADT.
 */
class RopeSpec extends FlatSpec with Matchers {

  "The empty rope" should "be empty, have zero length, never concatenate and render the empty string" in {
    Rope("") shouldBe Rope.empty
    Rope.empty.isEmpty shouldBe true
    Rope.empty.isDefined shouldBe false
    Rope.empty.length shouldBe 0
    Rope.empty ++ Rope("hi") shouldBe Rope("hi")
    Rope.empty.toString shouldBe ""
  }

  "Word ropes" should "never be empty, have the length of their value and render that value" in {
    val word = Rope("hi")
    word.isEmpty shouldBe false
    word.isDefined shouldBe true
    word.length shouldBe 2
    word.toString shouldBe "hi"
  }

  it should "concatenate with any non-empty rope" in {
    val word = Rope("hi")
    word ++ Rope.empty shouldBe word
    word ++ Rope("there") shouldBe Rope.Concatenation(Rope.Word("hi"), Rope.Word("there"))
  }

  "Concatenation ropes" should "never be empty, have the length of their delimited components" in {
    val concatenation = Rope("hi") ++ Rope("there") ++ Rope("buddy")
    concatenation.isEmpty shouldBe false
    concatenation.isDefined shouldBe true
    concatenation.length shouldBe 14
  }

  it should "render its delimited components" in {
    val concatenation = Rope("hi") ++ Rope("there") ++ Rope("buddy")
    concatenation.toString shouldBe "hi there buddy"
  }

  it should "concatenate with any non-empty rope" in {
    val concatenation = Rope.Concatenation(Rope.Word("hi"), Rope.Word("there"))
    concatenation ++ Rope.empty shouldBe concatenation
    concatenation ++ Rope("buddy") shouldBe Rope.Concatenation(concatenation, Rope.Word("buddy"))
  }

}
