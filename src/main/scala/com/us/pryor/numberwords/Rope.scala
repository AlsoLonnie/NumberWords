/*
 * Rope.scala
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
 * A simple rope of words and spaces that optimally constructs a string from its components.
 *
 * Rope concatenation is provided by the `++` operator and exhibits O(1) complexity. Ropes constructed with the
 * concatenation operator will have a single space character between their non-empty components in the resulting
 * string.
 *
 * The conversion of a rope to a string, while impure in it's implementation, encapsulates that impurity behind a
 * purely functional interface. The implementation ensures that only a single buffer of the exact size needed is
 * constructed per invocation. String conversion exhibits O(n) complexity.
 */
sealed trait Rope {

  /** Returns true if this rope has no characters. */
  def isEmpty: Boolean

  /** Returns true if this rope has at least one character. */
  final def isDefined: Boolean = !isEmpty

  /** Returns the length of this rope in characters. */
  def length: Int

  /**
   * Returns a rope that represents the concatenation of `this` rope followed by `that` rope.
   *
   * @param that The rope to append to this rope.
   * @return A Rope that represents the concatenation of `this` rope followed by `that` rope.
   */
  def ++(that: Rope): Rope

}

/**
 * Factories for and implementations of the rope interface.
 */
object Rope {

  /** Returns the empty rope. */
  def empty: Rope = Empty

  /**
   * Constructs a new word rope.
   *
   * @param value The value of the word rope to create.
   * @return A new word rope.
   */
  def apply(value: String): Rope =
    if (value.isEmpty) Empty else Word(value)

  /**
   * Implementation of the empty rope.
   */
  case object Empty extends Rope {

    /* The empty rope is always empty. */
    override def isEmpty: Boolean = true

    /* The length of the empty rope is always zero. */
    override def length: Int = 0

    /* Appending to the empty rope always returns the other rope. */
    override def ++(that: Rope): Rope = that

    /* The empty rope always returns the empty string. */
    override def toString: String = ""

  }

  /**
   * Base type for non-empty rope implementations.
   */
  sealed trait NonEmpty extends Rope {

    /* Non-empty ropes are never empty. */
    final override def isEmpty: Boolean = false

    /* Append that rope to this rope if that rope is non-empty. */
    final override def ++(that: Rope): Rope = that match {
      case nonEmpty: NonEmpty => Rope.Concatenation(this, nonEmpty)
      case Empty => this
    }

  }

  /**
   * Implementation of a rope that contains exactly one word.
   *
   * @param value The value of the word in this rope.
   */
  case class Word(value: String) extends NonEmpty {

    /* The length of word ropes is the length of their value. */
    override def length: Int = value.length

    /* Word ropes always return their value. */
    override def toString: String = value

  }

  /**
   * Implementation of a rope that concatenates to non-empty ropes.
   *
   * @param first  The first non-empty rope in the concatenation.
   * @param second The second non-empty rope in the concatenation.
   */
  case class Concatenation(first: NonEmpty, second: NonEmpty) extends NonEmpty {

    /* The length of concatenation ropes is the sum of the length of their component ropes plus one. */
    override def length: Int =
      first.length + second.length + 1

    /* Concatenation ropes use a precisely sized buffer and recursively append all nested word ropes. */
    override def toString: String = {
      val builder = new StringBuilder(length)
      Concatenation.render(builder, first, second)
      builder.toString
    }

  }

  /**
   * Contains recursive utility functions for rendering trees of words and concatenations.
   */
  object Concatenation {

    /**
     * Renders a single non-empty rope as a string using the specified string builder.
     *
     * @param builder The string builder to append characters to.
     * @param rope    The non-empty rope to render.
     */
    private def render(builder: StringBuilder, rope: NonEmpty): Unit = rope match {
      case Word(value) => builder.append(value)
      case Concatenation(f, s) => render(builder, f, s)
    }

    /**
     * Renders a pair of non-empty ropes as a string with a single space character separating them using the specified
     * string builder.
     *
     * @param builder The string builder to append characters to.
     * @param first   The first non-empty rope to render.
     * @param second  The second non-empty rope to render.
     */
    private def render(builder: StringBuilder, first: NonEmpty, second: NonEmpty): Unit = {
      render(builder, first)
      builder.append(' ')
      render(builder, second)
    }

  }

}
