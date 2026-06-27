#!/bin/sh
# Gradle wrapper script
GRADLE_OPTS="${GRADLE_OPTS:-""}"
exec "$JAVACMD" $GRADLE_OPTS -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" \
  org.gradle.wrapper.GradleWrapperMain "$@"
