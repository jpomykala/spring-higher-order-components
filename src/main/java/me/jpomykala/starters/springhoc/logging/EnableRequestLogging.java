package me.jpomykala.starters.springhoc.logging;

import me.jpomykala.starters.springhoc.api.EnableResponseImportWrapperAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SpringHocLoggingAutoConfiguration.class})
public @interface EnableRequestLogging {
}
