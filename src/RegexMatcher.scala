/*
 * Copyright 2013 Marconi Lanna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package matcher

object RegexMatcher {
  def apply(regex: String, text: String): Boolean = {
    regex.isEmpty ||
    (if (regex(0) == '^') mtch(regex drop 1, text)
    else scan(regex, text, !_.isEmpty))
  }

  private def mtch(regex: String, text: String): Boolean = {
    regex.isEmpty ||
    (if (regex(0) == '$' && regex.size == 1) text.isEmpty
    else if (next(regex, '?')) match_?(regex(0), regex drop 2, text)
    else if (next(regex, '+')) match_+(regex(0), regex drop 2, text)
    else if (next(regex, '*')) match_*(regex(0), regex drop 2, text)
    else if (matchChar(regex(0), text)) mtch(regex drop 1, text drop 1)
    else false)
  }

  private def matchChar(c: Char, text: String): Boolean = {
    !text.isEmpty && (c == '.' || c == text(0))
  }

  private def match_?(c: Char, regex: String, text: String): Boolean = {
    mtch(regex, text) ||
    (matchChar(c, text) && mtch(regex, text drop 1))
  }

  private def match_+(c: Char, regex: String, text: String): Boolean = {
    matchChar(c, text) && match_*(c, regex, text drop 1)
  }

  private def match_*(c: Char, regex: String, text: String): Boolean = {
    scan(regex, text, matchChar(c, _))
  }

  private def next(regex: String, c: Char): Boolean = {
    regex.size > 1 && regex(1) == c
  }

  private def scan(regex: String, text: String, cond: String => Boolean): Boolean = {
    var t = text
    var mtc = mtch(regex, t)
    while (!mtc && cond(t)) {
      t = t drop 1
      mtc = mtch(regex, t)
    }
    mtc
  }
}
