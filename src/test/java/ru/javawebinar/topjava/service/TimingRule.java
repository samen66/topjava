package ru.javawebinar.topjava.service;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TimingRule {
    public static final Logger LOGGER = LoggerFactory.getLogger("result");
    public static final StringBuilder RESULTS = new StringBuilder();
    public static final Stopwatch STOPWATCH = new Stopwatch(){
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            RESULTS.append(result).append('\n');
            LOGGER.info(result + " ms\n");
        }
    };
    
    public static final ExternalResource RESOURCE = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            RESULTS.setLength(0);
        }

        @Override
        protected void after() {
            LOGGER.info("\n" + LOGGER + "\n");
        }
    };
}
