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

package org.cqfn.astranaut.scanner;

import org.cqfn.astranaut.exceptions.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link Scanner} and {@link StringToken} classes.
 *
 * @since 0.1.5
 */
class StringTokenTest {
    /**
     * Source string (correct).
     */
    private static final String CORRECT = "  \"test \\n 123\"";

    /**
     * Output example.
     */
    private static final String EXPECTED = "\"test \\n 123\"";

    /**
     * Source string (incorrect).
     */
    private static final String INCORRECT = "  \"test";

    /**
     * Test scanner with string literal.
     */
    @Test
    void correctString() {
        final Scanner scanner = new Scanner(StringTokenTest.CORRECT);
        Token token = null;
        boolean oops = false;
        try {
            token = scanner.getToken();
            Assertions.assertTrue(token instanceof StringToken);
            Assertions.assertEquals(token.toString(), StringTokenTest.EXPECTED);
        } catch (final ParserException ignored) {
            oops = true;
        }
        Assertions.assertFalse(oops);
    }

    /**
     * Test scanner with incorrect string literal.
     */
    @Test
    void incorrectString() {
        final Scanner scanner = new Scanner(StringTokenTest.INCORRECT);
        boolean oops = false;
        try {
            scanner.getToken();
        } catch (final ParserException ignored) {
            oops = true;
        }
        Assertions.assertTrue(oops);
    }
}
