package it.smc.aperitech.reactive.aggregator.util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ReactorUtil {

	private ReactorUtil() {}

	public static <T> Flux<T> delay(
		Flux<T> flux, Duration duration, int buffer) {

		return flux
			.concatMap(v ->
				Mono.just(v)
					.delayUntil(dsse -> Mono
						.delay(duration)), buffer);

	}

	public static <T> Flux<T> delay(Flux<T> flux, Duration duration) {
		return delay(flux, duration, Integer.MAX_VALUE);
	}

	public static <T> Flux<T> delay(
		Flux<T> flux, TimeUnit timeUnit, long delay, int buffer) {
		return delay(flux, Duration.ofMillis(timeUnit.toMillis(delay)), buffer);
	}

	public static <T> Flux<T> delay(
		Flux<T> flux, TimeUnit timeUnit, long delay) {
		return delay(
			flux, Duration.ofMillis(timeUnit.toMillis(delay)),
			Integer.MAX_VALUE);
	}


}
