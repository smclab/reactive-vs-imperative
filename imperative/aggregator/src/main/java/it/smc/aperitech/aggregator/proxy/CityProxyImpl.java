package it.smc.aperitech.aggregator.proxy;

import it.smc.aperitech.aggregator.fallback.CityProxyFallbackFactory;
import it.smc.aperitech.proxy.CityProxy;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Mauro Celani
 */
@FeignClient(
	name = "city",
	decode404 = true,
	fallbackFactory = CityProxyFallbackFactory.class
)
public interface CityProxyImpl extends CityProxy {
}