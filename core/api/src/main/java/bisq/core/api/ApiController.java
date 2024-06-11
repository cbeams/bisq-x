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

package bisq.core.api;

import ch.qos.logback.classic.Logger;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.UriMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface ApiController {

    default void logEndpoints(Logger log) {
        if (!log.isDebugEnabled())
            return;

        var basePath = getClass().getAnnotation(Controller.class).value();
        for (Method method : getClass().getMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                String relPath;
                Class<? extends Annotation> annoType = annotation.annotationType();
                if (annoType.isAssignableFrom(Get.class))
                    relPath = ((Get) annotation).value();
                else if (annoType.isAssignableFrom(Put.class))
                    relPath = ((Put) annotation).value();
                else if (annoType.isAssignableFrom(Post.class))
                    relPath = ((Post) annotation).value();
                else if (annoType.isAssignableFrom(Delete.class))
                    relPath = ((Delete) annotation).value();
                else
                    continue;

                String fqPath;
                if (UriMapping.DEFAULT_URI.equals(relPath))
                    fqPath = basePath;
                else if (UriMapping.DEFAULT_URI.equals(basePath))
                    fqPath = relPath;
                else
                    fqPath = basePath + relPath;

                log.debug("Accepting requests to {} {}", annoType.getSimpleName().toUpperCase(), fqPath);
            }
        }
    }
}
