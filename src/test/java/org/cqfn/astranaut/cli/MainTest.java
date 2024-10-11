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
package org.cqfn.astranaut.cli;

import java.io.ByteArrayOutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import org.cqfn.astranaut.exceptions.BaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests covering {@link Main} class.
 * @since 1.0.0
 */
class MainTest {
    /**
     * Expected error string.
     */
    private static final String NO_PARAMETERS = "Parameters are not specified";

    @Test
    void main() {
        final Logger logger = Logger.getLogger(Main.class.getName());
        final ByteArrayOutputStream content = new ByteArrayOutputStream();
        final Handler handler = new StreamHandler(content, new SimpleFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
        String output = "";
        try {
            Main.main(new String[0]);
            handler.flush();
            output = content.toString();
        } finally {
            logger.removeHandler(handler);
        }
        Assertions.assertTrue(output.contains(MainTest.NO_PARAMETERS));
    }

    @Test
    void runWithoutParameters() {
        boolean oops = false;
        try {
            Main.run();
        } catch (final BaseException exception) {
            oops = true;
            Assertions.assertEquals("Command line interface", exception.getInitiator());
            Assertions.assertEquals(MainTest.NO_PARAMETERS, exception.getErrorMessage());
        }
        Assertions.assertTrue(oops);
    }
}