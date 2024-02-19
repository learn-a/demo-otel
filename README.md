# Getting Started with Open Telemetry

## OTEL Reference Documentation
[Open Telemetry Docs](https://opentelemetry.io/docs/)

[OpenTelemetry Metrics Primer for Java Developers](https://medium.com/@asafmesika/opentelemetry-metrics-primer-for-java-developers-884bea88f5c4)


[Deep Dive into Open Telemetry Metrics](https://www.timescale.com/blog/a-deep-dive-into-open-telemetry-metrics/)

[Context Propagation](https://signoz.io/blog/opentelemetry-context-propagation/)


[A guide to Delta vs. Cumulative temporality trade-offs](https://grafana.com/blog/2023/09/26/opentelemetry-metrics-a-guide-to-delta-vs.-cumulative-temporality-trade-offs/#:~:text=Cumulative%20temporality%20means%20that%20the,last%20time%20it%20was%20reported)

[Stackoverflow How to create context using traceId in open telemetry](https://stackoverflow.com/questions/72668718/how-to-create-context-using-traceid-in-open-telemetry)

[Propagate OTEL gRPC Contexts](https://github.com/open-telemetry/opentelemetry-java-instrumentation/discussions/9139)

[Testing](https://github.com/open-telemetry/opentelemetry-java/blob/main/sdk/testing/src/main/java/io/opentelemetry/sdk/testing/junit5/OpenTelemetryExtension.java)

[YouTube Playlist](
https://www.youtube.com/playlist?list=PLNxnp_rzlqf6z1cC0IkIwp6yjsBboX945)

## Run the Application
```shell
./run.sh
```
The Open Telemetry instance is AutoConfigured. The run.sh sets all the exporters to `logging` but can be changed to point to any valid endpoint.

open http://localhost:8080/rolldice?rolls=12 in your web browser or use the following curl command to invoke the Rest API endpoint:

```shell
curl http://localhost:8080/rolldice?rolls=12
```
Observe the logs in the console and the metrics in the console.