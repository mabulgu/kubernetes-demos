package com.systemcraftsman.demo.kubernetes.springcloud.greetingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Greeting service controller.
 *
 */
@RestController
public class GreetingController {

	@Autowired
	private NameService nameService;

	/**
	 * Endpoint to get a greeting. This endpoint uses a name server to get a name for the greeting.
	 *
	 * Request to the name service is guarded with a circuit breaker. Therefore if a name service is not available or is too
	 * slow to response fallback name is used.
	 *
	 * Delay parameter can me used to make name service response slower.
	 *
	 * @param delay Milliseconds for how long the response from name service should be delayed.
	 * @return Greeting string.
	 */
	@RequestMapping("/greeting")
	public String getGreeting(@RequestParam(value = "delay", defaultValue = "0") int delay) {
		return String.format("Hello from %s!", this.nameService.getName(delay));
	}

}
