package example.demootel;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class RollController {
    private static final Logger logger = LoggerFactory.getLogger(RollController.class);
    private final Tracer tracer;
    private final OpenTelemetry openTelemetry;
    private final Printer printer;

    public RollController(OpenTelemetry openTelemetry, Printer printer) {
        tracer = openTelemetry.getTracer(this.getClass().getName(), "0.0.1");
        this.openTelemetry = openTelemetry;
        this.printer = printer;
    }

    @GetMapping("/rolldice")
    public List<Integer> index(@RequestParam("player") Optional<String> player,
                               @RequestParam("rolls") Optional<Integer> rolls) {
        Span span = tracer.spanBuilder("rollTheDice")
                .setSpanKind(SpanKind.CLIENT)
                .startSpan();

        // Make the span the current span
        try (Scope scope = span.makeCurrent()) {
            if (rolls.isEmpty() || rolls.get() == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing rolls parameter", null);
            }

            List<Integer> result = new Dice(1, 6, openTelemetry, printer).rollTheDice(rolls.get());

            if (player.isPresent()) {
                logger.info("{} is rolling the dice: {}", player.get(), result);
            } else {
                logger.info("Anonymous player is rolling the dice: {}", result);
            }
            return result;
        } catch (Throwable t) {
            span.setStatus(StatusCode.ERROR);
            span.recordException(t);
            throw t;
        } finally {
            // This is required & u cannot set a span after this call
            span.end();
        }
    }

}
