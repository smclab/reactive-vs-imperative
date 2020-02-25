package it.smc.aperitech.apigateway;

import io.netty.channel.ChannelOption;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter() {
			@Override
			public Mono<Void> filter(
				ServerWebExchange exchange, WebFilterChain chain) {
				return chain.filter(exchange);
			}
		};
	}

	@Bean
	public WebClient webClient(final ClientHttpConnector clientHttpConnector) {
		return WebClient.builder()
			.clientConnector(clientHttpConnector)
			.build();
	}

	@Bean
	public BeanPostProcessor beanPostProcessor(
		@Value("${webclient.enable-keep-alive}")
			final boolean clientKeepAlive,
		@Value("${httpserver.enable-keep-alive}")
			final boolean httpserverKeepAlive,
		@Value("${httpserver.so-backlog}") final int soBacklog) {
		return new BeanPostProcessor() {
			@Override
			public Object postProcessBeforeInitialization(
				Object bean, String beanName) throws BeansException {

				return bean;
			}

			@Override
			public Object postProcessAfterInitialization(
				Object bean, String beanName) throws BeansException {

				if (bean instanceof HttpClient) {
					return ((HttpClient)bean).keepAlive(clientKeepAlive);
				}

				if (bean instanceof NettyReactiveWebServerFactory) {
					((NettyReactiveWebServerFactory) bean)
						.addServerCustomizers(
							httpServer -> httpServer
								.tcpConfiguration(tcpServer ->
									tcpServer
										.bootstrap(serverBootstrap ->
											serverBootstrap
												.childOption(
													ChannelOption.SO_KEEPALIVE,
													httpserverKeepAlive)
												.option(
													ChannelOption.SO_BACKLOG,
													soBacklog)
										)
								)
						);
				}

				return bean;
			}
		};
	}

	private static boolean KEEP_ALIVE = true;

}
