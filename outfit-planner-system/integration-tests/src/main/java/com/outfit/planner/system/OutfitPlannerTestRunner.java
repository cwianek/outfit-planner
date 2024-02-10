package com.outfit.planner.system;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

public class OutfitPlannerTestRunner {

    public static void main(String[] args) {
        String packageName = "com.outfit.planner.system";
        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectPackage(packageName))
                .build();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();

        try (PrintWriter printWriter = new PrintWriter(System.out)) {
            summary.printTo(printWriter);
        }

        if (summary.getTestsFailedCount() > 0) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

}
