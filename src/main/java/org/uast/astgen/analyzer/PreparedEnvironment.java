/*
 * MIT License Copyright (c) 2022 unified-ast
 * https://github.com/unified-ast/ast-generator/blob/master/LICENSE.txt
 */
package org.uast.astgen.analyzer;

import java.util.List;
import org.uast.astgen.codegen.java.Environment;
import org.uast.astgen.codegen.java.License;
import org.uast.astgen.exceptions.GeneratorException;
import org.uast.astgen.rules.Node;
import org.uast.astgen.rules.Statement;

/**
 * Prepared environment, with preliminary analysis of the set of rules.
 *
 * @since 1.0
 */
public final class PreparedEnvironment implements Environment {
    /**
     * The base environment.
     */
    private final Environment base;

    /**
     * The analyzer to get additional data.
     */
    private final Analyzer analyzer;

    /**
     * Constructor.
     * @param base The base environment
     * @param descriptors The list of descriptors
     * @param language The name of programming language that will limit a set of nodes
     * @throws GeneratorException If the environment can't be built for proposed rule set
     */
    public PreparedEnvironment(final Environment base, final List<Statement<Node>> descriptors,
        final String language) throws GeneratorException {
        this.base = base;
        this.analyzer = new Analyzer(descriptors, language);
    }

    @Override
    public License getLicense() {
        return this.base.getLicense();
    }

    @Override
    public String getVersion() {
        return this.base.getVersion();
    }

    @Override
    public String getRootPackage() {
        return this.base.getRootPackage();
    }

    @Override
    public String getBasePackage() {
        return this.base.getBasePackage();
    }

    @Override
    public boolean isTestMode() {
        return this.base.isTestMode();
    }

    @Override
    public List<String> getHierarchy(final String name) {
        return this.analyzer.getHierarchy(name);
    }
}