/*
 * Main.scala
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
 * Application that prints the english language counterpart of each numeric argument.
 */
object Main extends App {

  if (args.length <= 0) {
    println("Usage: java -jar NumberWords.jar NUMBER+")
    System.exit(1)
  } else {
    var exit = 0
    for (arg <- args) try println(Translator(arg.toInt)) catch {
      case _: NumberFormatException =>
        println(s"Invalid number: $arg")
        exit = 2
    }
    System.exit(exit)
  }

}
