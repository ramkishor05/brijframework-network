package org.brijframework.network.group;

import java.util.concurrent.ConcurrentHashMap;

import org.brijframework.asm.group.DefaultGroup;

public class MailGroup implements DefaultGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailGroup(Object groupKey) {
	}

	@Override
	public Object getGroupKey() {
		return null;
	}

	@Override
	public ConcurrentHashMap<?, ?> getCache() {
		return null;
	}

	@Override
	public <T> T find(String parentID, Class<?> type) {
		return null;
	}

}
