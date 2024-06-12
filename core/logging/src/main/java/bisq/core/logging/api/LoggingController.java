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

package bisq.core.logging.api;

import bisq.core.api.ApiController;
import bisq.core.logging.LoggingCategory;
import bisq.core.logging.LoggingService;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;

import java.util.List;

@Controller("/logging")
public class LoggingController implements ApiController {

    @Get("/categories")
    public List<LoggingCategory> getLoggingCategories() {
        return LoggingService.getLoggingCategories();
    }

    @Get("/categories/{name}")
    public LoggingCategory getLoggingCategory(String name) {
        return LoggingService.getLoggingCategory(name);
    }

    @Put("/categories")
    public void updateLoggingCategory(LoggingCategory loggingCategory) {
        LoggingService.updateLoggingCategory(loggingCategory);
    }
}

