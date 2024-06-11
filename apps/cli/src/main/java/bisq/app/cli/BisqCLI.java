/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.app.cli;

import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.endpoint.InfoEndpoint;
import bisq.client.oas.endpoint.LoggingEndpoint;
import bisq.client.oas.endpoint.OfferEndpoint;
import bisq.client.oas.model.CategorySpec;
import bisq.client.oas.model.UpdateCategoryRequest;

import joptsimple.OptionParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.emptyList;

public class BisqCLI {

    private static final String APP_NAME_AND_VERSION = "Bisq API client v2.1.0";

    public static void main(String... args) {
        try {
            new BisqCLI().execute(args);
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("Error: " + t.getMessage());
            System.exit(1);
        }
    }

    private ApiClient bisqClient;

    private BisqCLI() {
    }

    private void execute(String... argv) throws Exception {

        var args = Arrays.asList(argv);
        var cmd = args.stream().filter(arg -> !arg.startsWith("-")).findFirst();
        var opts = cmd.map(s -> args.subList(0, args.indexOf(s))).orElse(args);
        var cmdArgs = cmd.map(s -> args.subList(args.indexOf(s) + 1, args.size())).orElse(emptyList());

        var parser = new OptionParser();

        var helpOpt = parser.acceptsAll(Arrays.asList("?", "h", "help"), "Print this help message and exit").forHelp();
        var versionOpt = parser.acceptsAll(Arrays.asList("v", "version"), "Print version and exit");
        var nodeOpt = parser.accepts("node", "Specify target Bisq node")
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("url")
                .defaultsTo("http://localhost:2141");

        var options = parser.parse(opts.toArray(new String[]{}));

        var calledBare = args.isEmpty();
        if (calledBare) {
            help(parser);
            throw new IllegalArgumentException("too few parameters");
        }
        if (options.has(helpOpt)) {
            help(parser);
            return;
        }
        if (options.has(versionOpt)) {
            System.out.println(APP_NAME_AND_VERSION);
            return;
        }
        if (cmd.isEmpty()) {
            help(parser);
            throw new IllegalArgumentException("no command provided");
        }

        bisqClient = Configuration.getDefaultApiClient();
        bisqClient.setBasePath(options.valueOf(nodeOpt));

        var command = cmd.get();
        switch (command) {
            case "info" -> info(cmdArgs);
            case "showlog" -> showlog(cmdArgs);
            case "debuglog" -> debuglog(cmdArgs);
            case "infolog" -> infolog(cmdArgs);
            case "listoffers" -> listoffers(cmdArgs);
            default -> throw new IllegalStateException(format("'%s' is not a bisq command. see --help", command));
        }
    }

    private void help(OptionParser parser) throws IOException {
        System.out.println(APP_NAME_AND_VERSION);
        System.out.print("""

                Usage:  bisq-cli [options] <command> [<args>]        Send command to Bisq

                """);
        parser.printHelpOn(System.out);
        System.out.println();
    }

    private void info(List<String> args) {
        try {
            var infoEndpoint = new InfoEndpoint(bisqClient);
            var info = infoEndpoint.getInfo();
            System.out.println(info.toJson());
        } catch (ApiException e) {
            System.err.println("Exception when calling api");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace(System.err);
        }
    }

    private void showlog(List<String> args) {
        try {
            var loggingEndpoint = new LoggingEndpoint(bisqClient);
            var http = loggingEndpoint.getCategory("http");
            System.out.println(http);
        } catch (ApiException e) {
            System.err.println("Exception when calling api");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace(System.err);
        }
    }

    private void debuglog(List<String> args) {
        try {
            new LoggingEndpoint(bisqClient)
                    .updateCategory(new UpdateCategoryRequest().categorySpec(new CategorySpec().name("http").level("debug")));
        } catch (ApiException e) {
            System.err.println("Exception when calling api");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace(System.err);
        }
    }

    private void infolog(List<String> args) {
        try {
            new LoggingEndpoint(bisqClient).updateCategory(new UpdateCategoryRequest().categorySpec(new CategorySpec().name("http").level("info")));
        } catch (ApiException e) {
            System.err.println("Exception when calling api");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace(System.err);
        }
    }

    private void listoffers(List<String> args) {
        try {
            System.out.println(new OfferEndpoint(bisqClient).listAll());
        } catch (ApiException e) {
            System.err.println("Exception when calling api");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace(System.err);
        }
    }
}
