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

import java.util.List;
import org.cqfn.astranaut.exceptions.ParserException;
import org.cqfn.astranaut.rules.Instruction;
import org.cqfn.astranaut.rules.Program;
import org.cqfn.astranaut.rules.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link InstructionParser} class.
 *
 * @since 0.1.5
 */
class InstructionParserTest {
    /**
     * Test parsed 2 instructions.
     */
    @Test
    void parseTwoInstructions() {
        boolean oops = false;
        final Program program = new Program();
        final InstructionParser parser = new InstructionParser(program);
        try {
            parser.parse("Addition <- Expression, Expression");
            parser.parse("java:");
            parser.parse("Synchronized <- Expression, StatementBlock");
            final List<Instruction<Rule>> list = program.getAllRules();
            Assertions.assertEquals(2, list.size());
            Assertions.assertEquals(
                "green: Addition <- Expression, Expression",
                list.get(0).toString()
            );
            Assertions.assertEquals(
                "java: Synchronized <- Expression, StatementBlock",
                list.get(1).toString()
            );
        } catch (final ParserException ignored) {
            oops = true;
        }
        Assertions.assertFalse(oops);
    }
}
