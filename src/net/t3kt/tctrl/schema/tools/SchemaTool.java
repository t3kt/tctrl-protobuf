package net.t3kt.tctrl.schema.tools;

import com.google.common.collect.ImmutableMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import net.t3kt.tctrl.schema.AppSchema;
import net.t3kt.tctrl.schema.Schemas;
import net.t3kt.tctrl.schema.TctrlSchemaProto.AppSpec;
import net.t3kt.tctrl.schema.json.LegacyJsonBuilder;
import net.t3kt.tctrl.schema.json.LegacyJsonParser;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

final class SchemaTool {

    public enum SchemaEncoding {
        json,
        pbjson,
    }

    private File schemaFile;

    @Option(name = "-schema", usage = "Sets the schema source json file", required = true)
    public void setSchemaFile(File file) {
        if (file.exists()) {
            this.schemaFile = file;
        }
    }

    @Option(name = "-infmt", usage = "Sets the schema source format")
    public SchemaEncoding inputFormat;

    @Option(name = "-outfmt", usage = "Sets the schema output format")
    public SchemaEncoding outputFormat;

    private void run() throws IOException {
        dumpSchema();
    }

    private AppSpec readAppSpec() throws IOException {
        try (Reader reader = new FileReader(schemaFile)) {
            switch (inputFormat) {
                case pbjson:
                    return Schemas.parseAppSpecJson(reader);
                case json:
                default:
                    return LegacyJsonParser.parseAppSpec(reader);
            }
        }
    }

    private void writeAppSpec(AppSpec appSpec, OutputStream output) throws IOException {
        switch (outputFormat) {
            case pbjson:
                try (Writer writer = new OutputStreamWriter(output)) {
                    Schemas.writeJson(appSpec, writer);
                }
                break;
            case json:
            default:
                JsonObject obj = LegacyJsonBuilder.toJsonObject(appSpec);
                Json.createWriterFactory(ImmutableMap.of(JsonGenerator.PRETTY_PRINTING, true)).createWriter(output).writeObject(obj);
                break;
        }
    }

    private void dumpSchema() throws IOException {
        AppSpec appSpec = readAppSpec();
//        AppSchema appSchema = new AppSchema(appSpec);
//        appSchema.writeJson(System.out);
        writeAppSpec(appSpec, System.out);
    }

    public static void main(String[] args) {
        SchemaTool tool = new SchemaTool();
        CmdLineParser parser = new CmdLineParser(tool);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.exit(1);
        }
        try {
            tool.run();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
