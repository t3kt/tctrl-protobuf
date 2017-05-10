package net.t3kt.tctrl.schema.tools;

import net.t3kt.tctrl.schema.AppSchema;
import net.t3kt.tctrl.schema.Schemas;
import net.t3kt.tctrl.schema.TctrlSchemaProto.AppSpec;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

final class SchemaTool {

    private File schemaFile;

    @Option(name="schema", usage = "Sets the schema source json file", required = true)
    public void setSchemaFile(File file) {
        if (file.exists()) {
            this.schemaFile = file;
        }
    }

    private void run() throws IOException {
        dumpSchema();
    }

    private AppSpec readAppSpec() throws IOException {
        try (Reader reader = new FileReader(schemaFile)) {
            return Schemas.parseAppSpecJson(reader);
        }
    }

    private void dumpSchema() throws IOException {
        AppSpec appSpec = readAppSpec();
        AppSchema appSchema = new AppSchema(appSpec);
        appSchema.writeJson(System.out);
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
