package org.examples.pbk.otus.l21homework;

import java.lang.instrument.Instrumentation;

public class InstrumentationAgent {
    private static volatile Instrumentation globalInstrumentation;

    public static void premain(final String agentArgs, final Instrumentation inst)
    {
        globalInstrumentation = inst;
    }

    public static long getObjectSize(final Object object)
    {
        if (globalInstrumentation == null)
        {
            throw new IllegalStateException("Agent not initialized.");
        }
        return globalInstrumentation.getObjectSize(object);
    }
}
