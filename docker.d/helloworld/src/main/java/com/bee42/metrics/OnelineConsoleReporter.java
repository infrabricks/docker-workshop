package com.bee42.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import com.codahale.metrics.*;

/**
 * A reporter which creates a comma-separated values file of the measurements for each metric.
 */
public class OnelineConsoleReporter extends ScheduledReporter {
    /**
     * Returns a new {@link Builder} for {@link OnelineConsoleReporter}.
     *
     * @param registry the registry to report
     * @return a {@link Builder} instance for a {@link OnelineConsoleReporter}
     */
    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    /**
     * A builder for {@link OnelineConsoleReporter} instances. Defaults to using the default locale, converting
     * rates to events/second, converting durations to milliseconds, and not filtering metrics.
     */
    public static class Builder {
        private final MetricRegistry registry;
        private Locale locale;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private Clock clock;
        private MetricFilter filter;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.locale = Locale.getDefault();
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.clock = Clock.defaultClock();
            this.filter = MetricFilter.ALL;
        }

        /**
         * Format numbers for the given {@link Locale}.
         *
         * @param locale a {@link Locale}
         * @return {@code this}
         */
        public Builder formatFor(Locale locale) {
            this.locale = locale;
            return this;
        }

        /**
         * Convert rates to the given time unit.
         *
         * @param rateUnit a unit of time
         * @return {@code this}
         */
        public Builder convertRatesTo(TimeUnit rateUnit) {
            this.rateUnit = rateUnit;
            return this;
        }

        /**
         * Convert durations to the given time unit.
         *
         * @param durationUnit a unit of time
         * @return {@code this}
         */
        public Builder convertDurationsTo(TimeUnit durationUnit) {
            this.durationUnit = durationUnit;
            return this;
        }

        /**
         * Use the given {@link Clock} instance for the time.
         *
         * @param clock a {@link Clock} instance
         * @return {@code this}
         */
        public Builder withClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        /**
         * Only report metrics which match the given filter.
         *
         * @param filter a {@link MetricFilter}
         * @return {@code this}
         */
        public Builder filter(MetricFilter filter) {
            this.filter = filter;
            return this;
        }

        /**
         * Builds a {@link OnelineConsoleReporter} with the given properties, writing {@code .csv} to console.
         * @return a {@link OnelineConsoleReporter}
         */
        public OnelineConsoleReporter build() {
            return new OnelineConsoleReporter(registry,
                                   locale,
                                   rateUnit,
                                   durationUnit,
                                   clock,
                                   filter);
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(OnelineConsoleReporter.class);
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Locale locale;
    private final Clock clock;
    private final PrintWriter out ;

    private OnelineConsoleReporter(MetricRegistry registry,
                        Locale locale,
                        TimeUnit rateUnit,
                        TimeUnit durationUnit,
                        Clock clock,
                        MetricFilter filter) {
        super(registry, "oneline-console-reporter", filter, rateUnit, durationUnit);
        this.locale = locale;
        this.clock = clock;
        out = new PrintWriter(new OutputStreamWriter(System.out, UTF_8));
    }

    @Override
    public void report(SortedMap<String, Gauge> gauges,
                       SortedMap<String, Counter> counters,
                       SortedMap<String, Histogram> histograms,
                       SortedMap<String, Meter> meters,
                       SortedMap<String, Timer> timers) {
        final long stamp = clock.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSSZ");
        String timestamp = df.format(stamp);

        for (Map.Entry<String, Gauge> entry : gauges.entrySet()) {
            reportGauge(timestamp, entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Counter> entry : counters.entrySet()) {
            reportCounter(timestamp, entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Histogram> entry : histograms.entrySet()) {
            reportHistogram(timestamp, entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Meter> entry : meters.entrySet()) {
            reportMeter(timestamp, entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Timer> entry : timers.entrySet()) {
            reportTimer(timestamp, entry.getKey(), entry.getValue());
        }
    }

    private void reportTimer(String timestamp, String name, Timer timer) {
        final Snapshot snapshot = timer.getSnapshot();

        report(timestamp,
               name,
               "count,max,mean,min,stddev,p50,p75,p95,p98,p99,p999,mean_rate,m1_rate,m5_rate,m15_rate,rate_unit,duration_unit",
               "%d,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,calls/%s,%s",
               timer.getCount(),
               convertDuration(snapshot.getMax()),
               convertDuration(snapshot.getMean()),
               convertDuration(snapshot.getMin()),
               convertDuration(snapshot.getStdDev()),
               convertDuration(snapshot.getMedian()),
               convertDuration(snapshot.get75thPercentile()),
               convertDuration(snapshot.get95thPercentile()),
               convertDuration(snapshot.get98thPercentile()),
               convertDuration(snapshot.get99thPercentile()),
               convertDuration(snapshot.get999thPercentile()),
               convertRate(timer.getMeanRate()),
               convertRate(timer.getOneMinuteRate()),
               convertRate(timer.getFiveMinuteRate()),
               convertRate(timer.getFifteenMinuteRate()),
               getRateUnit(),
               getDurationUnit());
    }

    private void reportMeter(String timestamp, String name, Meter meter) {
        report(timestamp,
               name,
               "count,mean_rate,m1_rate,m5_rate,m15_rate,rate_unit",
               "%d,%f,%f,%f,%f,events/%s",
               meter.getCount(),
               convertRate(meter.getMeanRate()),
               convertRate(meter.getOneMinuteRate()),
               convertRate(meter.getFiveMinuteRate()),
               convertRate(meter.getFifteenMinuteRate()),
               getRateUnit());
    }

    private void reportHistogram(String timestamp, String name, Histogram histogram) {
        final Snapshot snapshot = histogram.getSnapshot();

        report(timestamp,
               name,
               "count,max,mean,min,stddev,p50,p75,p95,p98,p99,p999",
               "%d,%d,%f,%d,%f,%f,%f,%f,%f,%f,%f",
               histogram.getCount(),
               snapshot.getMax(),
               snapshot.getMean(),
               snapshot.getMin(),
               snapshot.getStdDev(),
               snapshot.getMedian(),
               snapshot.get75thPercentile(),
               snapshot.get95thPercentile(),
               snapshot.get98thPercentile(),
               snapshot.get99thPercentile(),
               snapshot.get999thPercentile());
    }

    private void reportCounter(String timestamp, String name, Counter counter) {
        report(timestamp, name, "count", "%d", counter.getCount());
    }

    private void reportGauge(String timestamp, String name, Gauge gauge) {
        report(timestamp, name, "value", "%s", gauge.getValue());
    }

    private void report(String timestamp, String name, String header, String line, Object... values) {
      // ToDo print header at debug onetime!
      out.printf(locale,
        String.format(locale, "%s %s metrics INFO: %s%n", timestamp, name, line),
        values);
      out.flush();
    }

    protected String sanitize(String name) {
        return name;
    }
}
