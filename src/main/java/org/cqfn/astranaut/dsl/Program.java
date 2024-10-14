/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2024 Ivan Kniazkov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cqfn.astranaut.dsl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Program, that is, a set of rules described in DSL.
 * @since 1.0.0
 */
public final class Program {
    /**
     * List of all rules.
     */
    private final List<Rule> all;

    /**
     * List of all languages for which at least one rule is described.
     */
    private Set<String> languages;

    /**
     * Constructor.
     * @param all List of all rules
     */
    public Program(final List<Rule> all) {
        this.all = all;
    }

    /**
     * Returns a list of all languages for which at least one rule is described.
     * @return A list containing at least one element
     */
    public Set<String> getAllLanguages() {
        if (this.languages == null) {
            this.languages = new TreeSet<>();
            for (final Rule rule : this.all) {
                final String language = rule.getLanguage();
                if (language.isEmpty()) {
                    this.languages.add("common");
                } else {
                    this.languages.add(language);
                }
            }
        }
        return this.languages;
    }
}
