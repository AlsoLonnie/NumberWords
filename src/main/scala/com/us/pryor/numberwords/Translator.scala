/*
 * Translator.scala
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

/**
 * The implementation of the algorithm that translates numeric values to their english language counterparts.
 */
object Translator {

  /** Index of words for whole numbers in [0, 9]. */
  private val Ones = Vector(
    Rope("zero"),
    Rope("one"),
    Rope("two"),
    Rope("three"),
    Rope("four"),
    Rope("five"),
    Rope("six"),
    Rope("seven"),
    Rope("eight"),
    Rope("nine"))

  /** Index of words for whole numbers in [10, 19]. */
  private val Teens = Vector(
    Rope("ten"),
    Rope("eleven"),
    Rope("twelve"),
    Rope("thirteen"),
    Rope("fourteen"),
    Rope("fifteen"),
    Rope("sixteen"),
    Rope("seventeen"),
    Rope("eighteen"),
    Rope("nineteen"))

  /** Index of words for multiples of ten in [20, 90]. */
  private val Tens = Vector(
    Rope("twenty"),
    Rope("thirty"),
    Rope("forty"),
    Rope("fifty"),
    Rope("sixty"),
    Rope("seventy"),
    Rope("eighty"),
    Rope("ninety"))

  /** The hundreds suffix. */
  private val Hundred = Rope("hundred")
  /** The thousands suffix. */
  private val Thousand = Rope("thousand")
  /** The millions suffix. */
  private val Million = Rope("million")
  /** The billions suffix. */
  private val Billion = Rope("billion")

  /** The and separator. */
  private val And = Rope("and")
  /** The negative prefix. */
  private val Negative = Rope("negative")

  /** The constant for the number 10. */
  private val Ten = 10
  /** The constant for the number 100. */
  private val OneHundred = 100
  /** The constant for the number 1,000. */
  private val OneThousand = 1000L
  /** The constant for the number 1,000,000. */
  private val OneMillion = 1000000L
  /** The constant for the number 1,000,000,000. */
  private val OneBillion = 1000000000L

  /**
   * Translates the specified numeric value into its english language counterpart.
   *
   * @param number The numeric value to translate.
   * @return The english language counterpart of the specified numeric value.
   */
  def apply(number: Int): String = number match {
    case zero if zero == 0 => Ones(0).toString
    case negative if negative < 0 => (Negative ++ translateNumber(-negative.toLong)).toString
    case positive => translateNumber(positive.toLong).toString
  }

  /**
   * Translates a long in the range [1, Int.MaxValue + 1] to its english language counterpart.
   *
   * @param number The number to translate.
   * @return The english language counterpart of the specified number.
   */
  private def translateNumber(number: Long): Rope = {
    // Translate the billions into english.
    val billions = translateSegment((number / OneBillion).toInt, Rope.empty, Billion)
    // Translate the millions into english, prepending any billions.
    val millions = translateSegment((number % OneBillion / OneMillion).toInt, billions, Million)
    // Translate the thousands into english, prepending any millions & billions.
    val thousands = translateSegment((number % OneMillion / OneThousand).toInt, millions, Thousand)
    // Translate the remainder into english, prepending any thousands, millions & billions.
    translateSegment((number % OneThousand).toInt, thousands, Rope.empty)
  }

  /**
   * Translates a numeric segment in the range [1, 999] to its english language counterpart prepending and appending the
   * specified prefix and suffix respectively.
   *
   * If the specified segment is zero then the prefix is returned.
   *
   * @param segment The segment of the number to translate.
   * @param prefix  The prefix to prepend to the segment representation.
   * @param suffix  The suffix to append to the segment representation.
   * @return The english language counterpart of the specified segment.
   */
  private def translateSegment(segment: Int, prefix: Rope, suffix: Rope): Rope =
  // Skip empty segments and just return the prefix.
    if (segment == 0) prefix else {
      // Determine the digits in [0, 9] for each part of the segment.
      val hundredsDigit = segment / OneHundred
      val tensDigit = segment % OneHundred / Ten
      val onesDigit = segment % Ten
      // Translate parts into their respective english, allowing for the fact that teens are represented atomically.
      val hundreds = if (hundredsDigit == 0) Rope.empty else Ones(hundredsDigit) ++ Hundred
      val tens = if (tensDigit < 2) Rope.empty else Tens(tensDigit - 2)
      val ones = if (tensDigit == 1) Teens(onesDigit) else if (onesDigit == 0) Rope.empty else Ones(onesDigit)
      // Determine if we need to use the and separator.
      val and = if ((prefix.isDefined || hundreds.isDefined) && (tens.isDefined || ones.isDefined)) And else Rope.empty
      // Compose the result.
      prefix ++ hundreds ++ and ++ tens ++ ones ++ suffix
    }

}
