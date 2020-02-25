package it.smc.aperitech.aggregator.proxy;

import it.smc.aperitech.aggregator.fallback.WeatherProxyFallbackFactory;
import it.smc.aperitech.proxy.WeatherProxy;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Mauro Celani
 */
@FeignClient(
	name = "weather",
	decode404 = true,
	fallbackFactory = WeatherProxyFallbackFactory.class
)
public interface WeatherProxyImpl extends WeatherProxy {
}
