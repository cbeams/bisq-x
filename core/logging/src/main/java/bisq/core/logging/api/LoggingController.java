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

import bisq.core.logging.CategorySpec;
import bisq.core.logging.Logging;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;

import java.util.List;


@Controller("/logging")
public class LoggingController implements ApiController {

    @Get("/categories")
    public List<CategorySpec> getCategories() {
        return Logging.getCategorySpecs();
    }

    @Get("/categories/{name}")
    public CategorySpec getCategory(String name) {
        return Logging.getCategorySpec(name);
    }

    @Put("/categories")
    public void updateCategory(CategorySpec categorySpec) {
        Logging.update(categorySpec);
    }
}

