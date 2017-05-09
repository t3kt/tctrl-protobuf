package net.t3kt.tctrl.schema;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import net.t3kt.tctrl.schema.TctrlSchemaProto.AppSchema;

import java.io.IOException;
import java.io.Reader;

public final class Schemas {
    private Schemas() {}

    public static AppSchema parseAppSchemaJson(String json) throws IOException {
        AppSchema.Builder builder = AppSchema.newBuilder();
        JsonFormat.parser().merge(json, builder);
        return builder.build();
    }

    public static AppSchema parseAppSchemaJson(Reader json) throws IOException {
        AppSchema.Builder builder = AppSchema.newBuilder();
        JsonFormat.parser().merge(json, builder);
        return builder.build();
    }

    public static String toJson(MessageOrBuilder message) throws IOException {
        return printer().print(message);
    }

    public static void writeJson(MessageOrBuilder message, Appendable output) throws IOException {
        printer().appendTo(message, output);
    }

    private static JsonFormat.Printer printer() {
        return JsonFormat.printer()
                .preservingProtoFieldNames();
    }
}
