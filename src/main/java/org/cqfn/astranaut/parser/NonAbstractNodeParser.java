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

import java.util.LinkedList;
import java.util.List;
import org.cqfn.astranaut.exceptions.ExpectedDescriptor;
import org.cqfn.astranaut.exceptions.ExpectedTaggedName;
import org.cqfn.astranaut.exceptions.NodeNameCapitalLetter;
import org.cqfn.astranaut.exceptions.OnlyOneListDescriptor;
import org.cqfn.astranaut.exceptions.ParserException;
import org.cqfn.astranaut.rules.Child;
import org.cqfn.astranaut.rules.Descriptor;
import org.cqfn.astranaut.rules.DescriptorAttribute;
import org.cqfn.astranaut.rules.Empty;
import org.cqfn.astranaut.scanner.TokenList;
import org.cqfn.astranaut.utils.LabelFactory;

/**
 * Parses children list for non-abstract node.
 *
 * @since 0.1.5
 */
public class NonAbstractNodeParser {
    /**
     * The source token list.
     */
    private final TokenList tokens;

    /**
     * Constructor.
     * @param tokens The source token list
     */
    public NonAbstractNodeParser(final TokenList tokens) {
        this.tokens = tokens;
    }

    /**
     * Parses children list for non-abstract node.
     * @return A children list
     * @throws ParserException If the list of tokens can't be parsed as a children list.
     */
    public List<Child> parse() throws ParserException {
        final List<Descriptor> descriptors =
            new DescriptorsListParser(this.tokens, new LabelFactory()).parse();
        checkListNonAbstract(descriptors);
        final List<Child> result = new LinkedList<>();
        for (final Descriptor descriptor : descriptors) {
            final String name = descriptor.getType();
            if (descriptor.equals(Empty.INSTANCE)) {
                result.add(descriptor);
                break;
            }
            if (!descriptor.getParameters().isEmpty() || descriptor.getData().isValid()) {
                throw new ExpectedTaggedName(name);
            }
            final char first = name.charAt(0);
            if (first >= 'a' && first <= 'z') {
                throw new NodeNameCapitalLetter(name);
            }
            result.add(descriptor);
        }
        return result;
    }

    /**
     * Checks descriptors list for non-abstract node.
     * @param descriptors Descriptors list
     * @throws ParserException If checking failed
     */
    private static void checkListNonAbstract(final List<Descriptor> descriptors)
        throws ParserException {
        final int count = descriptors.size();
        if (count == 0) {
            throw new ExpectedDescriptor("... <- ?");
        } else if (count > 1) {
            for (final Descriptor descriptor : descriptors) {
                if (descriptor.getAttribute() == DescriptorAttribute.LIST) {
                    throw OnlyOneListDescriptor.INSTANCE;
                }
            }
        }
    }
}
