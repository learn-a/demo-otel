package example.demootel;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    private final Tracer tracer;
    private final Printer printer;
    private final int min;
    private final int max;

    public Dice(int min, int max, OpenTelemetry openTelemetry, Printer printer) {
        this.min = min;
        this.max = max;
        this.printer = printer;
        tracer = openTelemetry.getTracer(this.getClass().getName(), "0.0.1");
    }

    public List<Integer> rollTheDice(int rolls) {
        Span parentSpan = tracer.spanBuilder("parent").startSpan();
        try (Scope scope = parentSpan.makeCurrent()) {
            List<Integer> results = new ArrayList<>();
            for (int i = 0; i < rolls; i++) {
                int rolledValue = this.rollOnce();
                results.add(rolledValue);
                printer.print(rolledValue);
            }
            return results;
        } finally {
            parentSpan.end();
        }
    }

    private int rollOnce() {
        Span childSpan = tracer.spanBuilder("rollOnce")
//                .setParent(Context.current().with(parentSpan))
                .startSpan();
        try {
            return ThreadLocalRandom.current().nextInt(this.min, this.max + 1);
        } finally {
            childSpan.end();
        }
    }


    private static void maybeRunWithSpan(Runnable runnable, boolean withSpan) {
        if (!withSpan) {
            runnable.run();
            return;
        }
        Span span = GlobalOpenTelemetry.getTracer("my-tracer").spanBuilder("my-span").startSpan();
        try (Scope unused = span.makeCurrent()) {
            runnable.run();
        } finally {
            span.end();
        }
    }
}
