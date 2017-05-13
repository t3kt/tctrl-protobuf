package net.t3kt.tctrl.schema;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import net.t3kt.tctrl.schema.TctrlSchemaProto.AppSpec;
import net.t3kt.tctrl.schema.json.LegacyJsonParser;

public final class SchemaLoader {
    private SchemaEncoding encoding = SchemaEncoding.JSON;
    private AppSpec appSpec;

    private SchemaLoader() {}

    public SchemaLoader withEncoding(SchemaEncoding encoding) {
        this.encoding = encoding;
        return this;
    }

    public SchemaLoader loadAppSpec(Reader reader) throws IOException {
        switch (encoding) {
            case JSON:
                appSpec = LegacyJsonParser.parseAppSpec(reader);
                break;
            case PROTO_JSON:
                appSpec = Schemas.parseAppSpecJson(reader);
            default:
                throw new IllegalStateException("Unsupported encoding: " + encoding);
        }
        return this;
    }

    public AppSpec getAppSpec() {
        Preconditions.checkState(appSpec != null, "App spec not loaded");
        return appSpec;
    }

    public AppSchema buildSchema() {
        return new AppSchema(getAppSpec());
    }

    public static SchemaLoader create() {
        return new SchemaLoader();
    }
}
