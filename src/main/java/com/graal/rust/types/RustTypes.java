package com.graal.rust.types;

import com.oracle.truffle.api.dsl.TypeSystem;

@TypeSystem({
        int.class,
        double.class,
        UnitType.class
})
public class RustTypes {

}
