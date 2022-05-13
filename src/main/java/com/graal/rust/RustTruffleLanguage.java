package com.graal.rust;

import com.graal.rust.nodes.RustNode;
import com.graal.rust.nodes.RustRootNode;
import com.graal.rust.parser.RustLangParser;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;

@TruffleLanguage.Registration(id = "rs", name = "Rust")
public class RustTruffleLanguage extends TruffleLanguage<Void> {

    @Override
    protected RootCallTarget parse(ParsingRequest request) throws Exception {
        RustNode exprNode = RustLangParser.parse(request.getSource().getReader());
        var rootNode = new RustRootNode(exprNode);
        return rootNode.getCallTarget();
    }

    @Override
    protected Void createContext(Env env) {
        return null;
    }
}
