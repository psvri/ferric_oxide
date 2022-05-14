package com.oxide.ferric;

import com.oxide.ferric.nodes.RustRootNode;
import com.oxide.ferric.nodes.RustNode;
import com.oxide.ferric.parser.RustLangParser;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;

@TruffleLanguage.Registration(id = RustTruffleLanguage.ID,
        name = RustTruffleLanguage.NAME,
        defaultMimeType = RustTruffleLanguage.MIME_TYPE,
        characterMimeTypes = RustTruffleLanguage.MIME_TYPE,
        contextPolicy = TruffleLanguage.ContextPolicy.EXCLUSIVE,
        interactive = false)
public class RustTruffleLanguage extends TruffleLanguage<Void> {

    public static final String ID = "rs";
    public static final String MIME_TYPE = "application/rs";
    public static final String NAME = "rust";
    public static final String RUST_SOURCE_NAME_SUFFIX = ".rs";

    @Override
    protected RootCallTarget parse(ParsingRequest request) throws Exception {
        var builder =  FrameDescriptor.newBuilder(1);
        RustNode exprNode = RustLangParser.parse(request.getSource().getReader(), builder);
        var rootNode = new RustRootNode(exprNode);
        return rootNode.getCallTarget();
    }

    @Override
    protected Void createContext(Env env) {
        return null;
    }
}
