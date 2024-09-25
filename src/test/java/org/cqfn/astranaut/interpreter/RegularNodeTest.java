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
package org.cqfn.astranaut.interpreter;

import java.util.Collections;
import org.cqfn.astranaut.core.base.Builder;
import org.cqfn.astranaut.core.base.DummyNode;
import org.cqfn.astranaut.core.base.EmptyFragment;
import org.cqfn.astranaut.core.base.Node;
import org.cqfn.astranaut.dsl.ChildDescriptorExt;
import org.cqfn.astranaut.dsl.RegularNodeDescriptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests covering {@link RegularNode} and {@link RegularBuilder} classes.
 * @since 1.0.0
 */
class RegularNodeTest {
    @Test
    void nodeWithoutChildren() {
        final String name = "This";
        final RegularNodeDescriptor descriptor = new RegularNodeDescriptor(
            name,
            Collections.emptyList()
        );
        final Builder builder = descriptor.createBuilder();
        Assertions.assertTrue(builder.isValid());
        builder.setFragment(EmptyFragment.INSTANCE);
        Assertions.assertTrue(builder.setData(""));
        Assertions.assertFalse(builder.setData("test"));
        Assertions.assertTrue(builder.setChildrenList(Collections.emptyList()));
        Assertions.assertFalse(
            builder.setChildrenList(Collections.singletonList(DummyNode.INSTANCE))
        );
        final Node node = builder.createNode();
        Assertions.assertSame(descriptor, node.getType());
        Assertions.assertEquals(name, node.getTypeName());
        Assertions.assertEquals("", node.getData());
        Assertions.assertEquals(0, node.getChildCount());
    }

    @Test
    void nodeWithOneObligatoryChild() {
        final RegularNodeDescriptor expression = new RegularNodeDescriptor(
            "Expression",
            Collections.emptyList()
        );
        final RegularNodeDescriptor boolexpr = new RegularNodeDescriptor(
            "BooleanExpression",
            Collections.emptyList()
        );
        boolexpr.addBaseDescriptor(expression);
        final String name = "True";
        final RegularNodeDescriptor constant = new RegularNodeDescriptor(
            name,
            Collections.emptyList()
        );
        constant.addBaseDescriptor(boolexpr);
        final Node child = constant.createBuilder().createNode();
        final RegularNodeDescriptor stmt = new RegularNodeDescriptor(
            "StatementExpression",
            Collections.singletonList(
                new ChildDescriptorExt(false, "", "Expression")
            )
        );
        final Builder builder = stmt.createBuilder();
        Assertions.assertFalse(builder.isValid());
        Assertions.assertThrows(IllegalStateException.class, builder::createNode);
        builder.setFragment(EmptyFragment.INSTANCE);
        Assertions.assertTrue(builder.setData(""));
        Assertions.assertFalse(builder.setData("abc"));
        Assertions.assertFalse(builder.setChildrenList(Collections.emptyList()));
        Assertions.assertFalse(builder.isValid());
        Assertions.assertFalse(
            builder.setChildrenList(Collections.singletonList(DummyNode.INSTANCE))
        );
        Assertions.assertFalse(builder.isValid());
        Assertions.assertTrue(
            builder.setChildrenList(Collections.singletonList(child))
        );
        Assertions.assertTrue(builder.isValid());
        final Node node = builder.createNode();
        Assertions.assertEquals(1, node.getChildCount());
        Assertions.assertEquals(name, node.getChild(0).getTypeName());
        Assertions.assertEquals("StatementExpression(True)", node.toString());
    }
}
