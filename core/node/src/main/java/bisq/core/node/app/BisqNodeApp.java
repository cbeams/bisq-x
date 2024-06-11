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

package bisq.core.node.app;

import bisq.core.node.BisqNode;
import ch.qos.logback.classic.Logger;

/**
 * Indicates that an implementing class configures and runs a {@link BisqNode}, typically
 * from a main method entry point.
 */
public interface BisqNodeApp {

    int EXIT_SUCCESS = 0;
    int EXIT_FAILURE = 1;

    Logger log = AppCategory.log;

    /**
     * Unwraps excessive exception nesting for better log output
     */
    static Throwable unwrap(Throwable t) {
        while (t.getCause() != null && t.getMessage().contains("Exception")) {
            t = t.getCause();
        }
        return t;
    }
}
