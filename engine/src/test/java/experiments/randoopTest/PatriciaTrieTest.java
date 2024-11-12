/*
 * Copyright (c) 2019, The Regents of the University of California
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package experiments.randoopTest;


import net.jqwik.api.*;

import net.jqwik.api.randoop.*;

import net.jqwik.engine.hooks.lifecycle.*;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.assertj.core.api.Assertions;

import java.util.*;
import java.util.concurrent.*;

/*
 *Experimentos extraidos de JQF
 * https://rohan.padhye.org/files/jqf-issta19.pdf
 */
public class PatriciaTrieTest{

	// Array with prefixes matching more than one word
	String[] prefixes = {
		"app", // Matches "apple", "applet", "application"
		"ban", // Matches "banana", "bandana"
		"blue", // Matches "blueberry", "bluefish"
		"grape" // Matches "grapefruit", "grapevine"
	};

	@Provide
	Arbitrary<String> prefixesProvider() {
		return Arbitraries.of(prefixes);
	}

	@Property
	public void testPrefixMap(@ForAll
							  @RandoopStrings(strings = {"apple", "applet", "application", "banana", "bandana",
														"blueberry", "bluefish", "grapefruit", "grapevine", "peach"})
							  @UseMethods(methods = {"put"}) TreeMap<String, Integer>  map,
							  @ForAll("prefixesProvider") String prefix) {
		// Create new trie with input `map`
		PatriciaTrie trie = new PatriciaTrie(map);
		// Get sub-map whose keys start with `prefix`
		Map prefixMap = trie.prefixMap(prefix);
		// Ensure that it contains all keys that start with `prefix`
		for (String key : map.keySet()) {
			if (key.startsWith(prefix)) {
				Assertions.assertThat(prefixMap.containsKey(key)).isTrue();
			}
		}
	}

	String[] strings = {"hola", "chau", "mundo", "hello", "bay"};

	@Provide
	Arbitrary<String> randoopStrings() {
		return Arbitraries.of(strings);
	}

	@Property
	public void testCopy(@ForAll @RandoopStrings(strings = {"hola", "chau", "mundo", "hello", "bay"})
						 @UseMethods(methods = {"put"}) TreeMap<String, Integer> map,
						 @ForAll("randoopStrings") String key) {
		Assume.that(map.containsKey(key));
		// Create new trie with input `map`
		Trie trie = new PatriciaTrie(map);
		// The key should exist in the trie as well
		Assertions.assertThat(trie.containsKey(key)).isTrue();
	}

}
