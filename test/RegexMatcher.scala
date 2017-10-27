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
package test.matcher

import matcher.RegexMatcher

class RegexMatcherTest extends org.scalatest.FunSuite {
  test("Simple matches") {
    assert(RegexMatcher("", ""))
    assert(RegexMatcher("", "a"))
    assert(RegexMatcher("a", "a"))
    assert(RegexMatcher("ab", "ab"))
    assert(RegexMatcher("ab", "abc"))
    assert(RegexMatcher("ab", "zab"))
    assert(RegexMatcher("ab", "zabc"))
    assert(RegexMatcher("ab", "aabc"))
  }

  test("Non-matches") {
    assert(!RegexMatcher("a", ""))
    assert(!RegexMatcher("a", "b"))
    assert(!RegexMatcher("ab", "ba"))
    assert(!RegexMatcher("ab", "bac"))
    assert(!RegexMatcher("ab", "zba"))
    assert(!RegexMatcher("ab", "zbac"))
    assert(!RegexMatcher("ab", "baac"))
  }

  test("Start anchor") {
    assert(RegexMatcher("^", ""))
    assert(RegexMatcher("^a", "a"))
    assert(RegexMatcher("^ab", "ab"))
    assert(RegexMatcher("^ab", "abc"))

    assert(!RegexMatcher("^a", ""))
    assert(!RegexMatcher("^a", "za"))
  }

  test("End anchor") {
    assert(RegexMatcher("$", ""))
    assert(RegexMatcher("a$", "a"))
    assert(RegexMatcher("a$", "za"))
    assert(RegexMatcher("ab$", "ab"))
    assert(RegexMatcher("ab$", "zab"))

    assert(!RegexMatcher("a$", ""))
    assert(!RegexMatcher("a$", "ab"))
  }

  test("Dot") {
    assert(RegexMatcher(".", "."))
    assert(RegexMatcher(".", "a"))
    assert(RegexMatcher(".", "ab"))
    assert(RegexMatcher("a.", "ab"))
    assert(RegexMatcher(".b", "ab"))
    assert(RegexMatcher("a.c", "abc"))

    assert(!RegexMatcher(".", ""))
    assert(!RegexMatcher("a.", "a"))
    assert(!RegexMatcher(".a", "a"))
  }

  test("Question mark") {
    assert(RegexMatcher("a?", ""))
    assert(RegexMatcher("a?", "a"))
    assert(RegexMatcher("a?", "b"))
    assert(RegexMatcher("a?", "aa"))
    assert(RegexMatcher("a?", "za"))
    assert(RegexMatcher("a?", "zaa"))
    assert(RegexMatcher("a?b", "ab"))
    assert(RegexMatcher("a?b", "b"))
    assert(RegexMatcher("a?b", "aab"))
    assert(RegexMatcher("ab?c", "abc"))
    assert(RegexMatcher("ab?c", "ac"))
    assert(RegexMatcher("ab?c", "aabc"))

    assert(!RegexMatcher("ab?c", "abbc"))
  }

  test("Plus") {
    assert(RegexMatcher("a+", "a"))
    assert(RegexMatcher("a+", "aa"))
    assert(RegexMatcher("a+", "za"))
    assert(RegexMatcher("a+", "zaa"))
    assert(RegexMatcher("a+b", "ab"))
    assert(RegexMatcher("a+b", "aab"))
    assert(RegexMatcher("ab+c", "abc"))
    assert(RegexMatcher("ab+c", "aabc"))
    assert(RegexMatcher("ab+c", "abbbc"))

    assert(!RegexMatcher("a+", ""))
    assert(!RegexMatcher("a+", "z"))
    assert(!RegexMatcher("a+b", "b"))
  }

  test("Star") {
    assert(RegexMatcher("a*", ""))
    assert(RegexMatcher("a*", "a"))
    assert(RegexMatcher("a*", "aa"))
    assert(RegexMatcher("a*", "z"))
    assert(RegexMatcher("a*", "za"))
    assert(RegexMatcher("a*", "zaa"))
    assert(RegexMatcher("a*b", "b"))
    assert(RegexMatcher("a*b", "ab"))
    assert(RegexMatcher("a*b", "aab"))
    assert(RegexMatcher("ab*c", "abc"))
    assert(RegexMatcher("ab*c", "aabc"))
    assert(RegexMatcher("ab*c", "abbbc"))
  }

  test("Complex matches") {
    assert(RegexMatcher("^.*$", ""))
    assert(RegexMatcher("^.*$", "a"))
    assert(RegexMatcher("^.*$", "ab"))
    assert(RegexMatcher("^a*$", "a"))
    assert(RegexMatcher("^a*$", "aa"))
    assert(RegexMatcher("a.*c", "zaabcd"))

    assert(!RegexMatcher("^a*$", "ab"))
  }
}
