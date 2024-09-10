package ca.toadapp.common.service;

import org.springframework.stereotype.Component;

@Component
public interface ServiceNotification {
	public boolean sendMessage( Long providerId, Long agentId, String message );
}
