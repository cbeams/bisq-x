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

package bisq.app.daemon;

import bisq.core.logging.Logging;
import bisq.core.node.BisqNode;
import bisq.core.node.Options;
import bisq.core.node.app.BisqNodeApp;
import bisq.core.node.app.CommandLine;

import static bisq.core.node.app.BisqNodeApp.unwrap;
import static ch.qos.logback.classic.Level.*;

public class BisqDaemon implements BisqNodeApp {

    private static final String APP_NAME_AND_VERSION = "Bisq daemon version 2.1.0";

    public static void main(String... args) {
        int status;
        try {
            run(args);
            status = EXIT_SUCCESS;
        } catch (Throwable t) {
            log.error("Error: {}", unwrap(t).getMessage());
            status = EXIT_FAILURE;
        }
        log.info("Exiting with status {}", status);
        System.exit(status);
    }

    private static void run(String... args) {

        // ------------------------------------------------------------------
        // Init logging
        // ------------------------------------------------------------------

        Logging.setLevel(INFO);

        var cli = new CommandLine(args);
        var helpRequested = cli.helpRequested();
        var debugRequested = cli.debugRequested();

        // Suppress normal log output if we know help output is coming
        if (helpRequested && !debugRequested)
            Logging.setLevel(WARN);

        // Identify what is running
        log.info(APP_NAME_AND_VERSION);

        // Enable debug logging as early as possible if requested
        if (debugRequested)
            Logging.setLevel(DEBUG);

        // ------------------------------------------------------------------
        // Configure node
        // ------------------------------------------------------------------

        log.debug("Configuring node options");
        var options = Options.withDefaultValues();
        try {
            cli.parseAndLoad(options);
        } catch (CommandLine.HelpRequest request) {
            var helpText = request.getHelpText(
                    "Bisq daemon", "bisqd", "2.1.0", "Start Bisq");
            System.out.print(helpText);
            return;
        }
        log.debug("Finished configuring node options");

        // ------------------------------------------------------------------
        // Start node
        // ------------------------------------------------------------------

        BisqNode.withOptions(options).start();

        // ------------------------------------------------------------------
        // Keep alive
        // ------------------------------------------------------------------

        try {
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}