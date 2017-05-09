package net.t3kt.tctrl.schema;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import net.t3kt.tctrl.schema.TctrlSchemaProto.AppSpec;

import java.io.IOException;
import java.io.Reader;

public final class Schemas {
    private Schemas() {}

    public static AppSpec parseAppSchemaJson(String json) throws IOException {
        AppSpec.Builder builder = AppSpec.newBuilder();
        JsonFormat.parser().merge(json, builder);
        return builder.build();
    }

    public static AppSpec parseAppSchemaJson(Reader json) throws IOException {
        AppSpec.Builder builder = AppSpec.newBuilder();
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
