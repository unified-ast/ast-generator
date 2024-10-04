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
package org.cqfn.astranaut.parser;

import java.util.Map;
import org.cqfn.astranaut.core.utils.MapUtils;
import org.cqfn.astranaut.exceptions.BaseException;

/**
 * Scanner splits the DSL line into tokens.
 * @since 1.0.0
 */
public final class Scanner {
    /**
     * Collection of tokens representing a single character.
     */
    private static final Map<Character, SingleCharToken> TOKENS =
        new MapUtils<Character, SingleCharToken>()
            .put(',', Comma.INSTANCE)
            .make();

    /**
     * Line of DSL code.
     */
    private final String line;

    /**
     * Current index.
     */
    private int index;

    /**
     * Constructor.
     * @param line Line of DSL code
     */
    public Scanner(final String line) {
        this.line = line;
        this.index = 0;
    }

    /**
     * Extracts the next token from the line. If the line has ended (no more tokens),
     *  returns {@code null}.
     * @return A token
     * @throws BaseException If the scanner cannot extract the next token
     */
    public Token getToken() throws BaseException {
        char chr = this.getChar();
        while (Character.isWhitespace(chr)) {
            chr = this.nextChar();
        }
        final Token token;
        do {
            if (chr == '\0') {
                token = null;
                break;
            }
            if (Scanner.TOKENS.containsKey(chr)) {
                this.nextChar();
                token = Scanner.TOKENS.get(chr);
                break;
            }
            if (Character.isLetter(chr)) {
                final StringBuilder builder = new StringBuilder();
                do {
                    builder.append(chr);
                    chr = this.nextChar();
                } while (Character.isLetterOrDigit(chr));
                token = new Identifier(builder.toString());
                break;
            }
            throw new UnknownSymbol(chr);
        } while (false);
        return token;
    }

    /**
     * Returns the current symbol.
     * @return A symbol
     */
    private char getChar() {
        final char chr;
        if (this.index < this.line.length()) {
            chr = this.line.charAt(this.index);
        } else {
            chr = '\0';
        }
        return chr;
    }

    /**
     * Returns the next symbol.
     * @return A symbol
     */
    private char nextChar() {
        this.index = this.index + 1;
        return this.getChar();
    }

    /**
     * Exception 'Unknown symbol'.
     * @since 1.0.0
     */
    private static final class UnknownSymbol extends BaseException {
        /**
         * Version identifier.
         */
        private static final long serialVersionUID = -1;

        /**
         * Symbol that cannot be processed by the parser.
         */
        private final char chr;

        /**
         * Constructor.
         * @param chr Symbol that cannot be processed by the parser.
         */
        private UnknownSymbol(final char chr) {
            this.chr = chr;
        }

        @Override
        public String getInitiator() {
            return "Parser";
        }

        @Override
        public String getErrorMessage() {
            return String.format("Unknown symbol: '%c'", this.chr);
        }
    }
}