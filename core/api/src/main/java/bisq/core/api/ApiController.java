package bisq.core.api;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.UriMapping;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface ApiController {

    Logger log = ApiLog.log;

    default void report() {
        var basePath = getClass().getAnnotation(Controller.class).value();
        for (Method method : getClass().getMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                String relPath = "";
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
