/*
 * TranslatorSpec.scala
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
 * Test suite for the number translator.
 */
class TranslatorSpec extends FlatSpec with Matchers {

  "The translator" should "translate numbers into strings" in {
    Translator(0) shouldBe "zero"
    Translator(1) shouldBe "one"
    Translator(-5) shouldBe "negative five"
    Translator(13) shouldBe "thirteen"
    Translator(85) shouldBe "eighty five"
    Translator(5237) shouldBe "five thousand two hundred and thirty seven"
    Translator(301493015) shouldBe "three hundred and one million four hundred and ninety three thousand and fifteen"
    Translator(42) shouldBe "forty two"
    Translator(600) shouldBe "six hundred"
    Translator(1000000005) shouldBe "one billion and five"
    Translator(3000005) shouldBe "three million and five"
    Translator(5005) shouldBe "five thousand and five"
    Translator(Int.MinValue) shouldBe
      "negative two billion " +
        "one hundred and forty seven million " +
        "four hundred and eighty three thousand " +
        "six hundred and forty eight"
  }

}
