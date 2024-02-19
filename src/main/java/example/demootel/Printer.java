package example.demootel;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Printer {

    private final Tracer tracer;

    public Printer(OpenTelemetry openTelemetry) {
        tracer = openTelemetry.getTracer(this.getClass().getName(), "0.0.1");
    }

    public void print(int rolledValue) {
        Span printSpan = tracer.spanBuilder("print").startSpan();
        try (Scope scope = printSpan.makeCurrent()) {
            log.info("Printer->print value of rolled dice is {}", rolledValue);
        } finally {
            printSpan.end();
        }
    }
}
