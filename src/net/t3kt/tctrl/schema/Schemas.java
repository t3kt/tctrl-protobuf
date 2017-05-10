package net.t3kt.tctrl.schema;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import java.io.IOException;
import java.io.Reader;
import net.t3kt.tctrl.schema.TctrlSchemaProto.AppSpec;

public final class Schemas {
    private Schemas() {
    }

    public static AppSpec parseAppSpecJson(String json) throws IOException {
        AppSpec.Builder builder = AppSpec.newBuilder();
        JsonFormat.parser().merge(json, builder);
        return builder.build();
    }

    public static AppSpec parseAppSpecJson(Reader json) throws IOException {
        AppSpec.Builder builder = AppSpec.newBuilder();
        JsonFormat.parser().merge(json, builder);
        return builder.build();
    }

    public static String toJson(MessageOrBuilder message) {
        try {
            return printer().print(message);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeJson(MessageOrBuilder message, Appendable output) throws IOException {
        printer().appendTo(message, output);
    }

    private static JsonFormat.Printer printer() {
        return JsonFormat.printer()
                .preservingProtoFieldNames();
    }
}
