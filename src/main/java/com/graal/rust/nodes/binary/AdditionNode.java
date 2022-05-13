package com.graal.rust.nodes.binary;

import com.oracle.truffle.api.dsl.Specialization;

public abstract class AdditionNode extends BinaryNode {
    @Specialization
    public int doIntegers(int left, int right) {
        return left + right;
    }
}
