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

        byte b = 10;
        short s = 100;
        int i = 1000;
        long l = 10000L;
        float f = 3.14F;
        double d = 99.12;
        boolean isTrue = true;
        char c = 'A';
        LOG.info("byte: {}, short: {}, int: {}, long: {}", b, s, i, l);
        LOG.info("float: {}, double: {}", f, d);
        LOG.info("boolean: {}", isTrue);
        LOG.info("char: {}", c);
    }
}
