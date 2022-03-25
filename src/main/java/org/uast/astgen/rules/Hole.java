/*
 * MIT License Copyright (c) 2022 unified-ast
 * https://github.com/unified-ast/ast-generator/blob/master/LICENSE.txt
 */
package org.uast.astgen.rules;

/**
 * Hole, i.e. #1, #2, etc.
 *
 * @since 1.0
 */
public final class Hole implements Data {
    /**
     * Value of the hole.
     */
    private final int value;

    /**
     * Constructor.
     * @param value Value of the hole
     */
    public Hole(final int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append('#')
            .append(this.value)
            .toString();
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
