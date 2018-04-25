package top.gotoeasy.framework.core.util;

import java.lang.management.ManagementFactory;

public class CmnRuntime {

	public static boolean isDebug() {
		String inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
		return inputArguments.contains("-Xdebug") || inputArguments.contains("-agentlib:jdwp=");
	}
}
