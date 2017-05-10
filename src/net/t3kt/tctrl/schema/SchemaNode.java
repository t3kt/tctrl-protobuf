package net.t3kt.tctrl.schema;

import com.google.protobuf.Message;

import java.io.IOException;

public abstract class SchemaNode<S extends Message> {
    protected final S spec;

    protected SchemaNode(S spec) {
        this.spec = spec;
    }

    public S getSpec() {
        return spec;
    }

    public String toJson() {
        return Schemas.toJson(getSpec());
    }

    public void writeJson(Appendable output) throws IOException {
        Schemas.writeJson(getSpec(), output);
    }
}
