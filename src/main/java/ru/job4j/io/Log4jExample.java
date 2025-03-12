package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jExample {
    private static final Logger LOG = LoggerFactory.getLogger(Log4jExample.class);

    public static void main(String[] args) {
        LOG.trace("trace message");
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.warn("warn message");
        LOG.error("error message");
    }
}
