import ch.qos.logback.classic.filter.ThresholdFilter

def STANDARD_PATTERN = "%d [%t] %c: %replace(%replace(%msg){'\n',''}){'\r',''} %n"

appender("console", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = STANDARD_PATTERN
    }
    filter(ThresholdFilter) {
        level = TRACE
    }
}

appender("logfile", RollingFileAppender) {
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = 'groovy-sample-app_%d.log'
        maxHistory = 2
    }
    encoder(PatternLayoutEncoder) {
        pattern = STANDARD_PATTERN
    }
    filter(ThresholdFilter) {
        level = TRACE
    }
}

root(DEBUG, ["console", "logfile"])