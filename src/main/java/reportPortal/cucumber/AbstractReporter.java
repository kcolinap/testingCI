package reportPortal.cucumber;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.Launch;
import com.epam.reportportal.service.ReportPortal;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import com.epam.ta.reportportal.ws.model.log.SaveLogRQ.File;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import io.reactivex.Maybe;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rp.com.google.common.base.Supplier;
import rp.com.google.common.base.Suppliers;

import java.util.Calendar;
import java.util.Date;

public abstract class AbstractReporter implements ConcurrentEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractReporter.class);

    protected static final String COLON_INFIX = ": ";

    /* feature context */
    protected RunningContext.FeatureContext currentFeatureContext;

    /* scenario context */
    protected RunningContext.ScenarioContext currentScenarioContext;

    protected Supplier<Launch> RP;

    /**
     * Registers an event handler for a specific event.
     * <p>
     * The available events types are:
     * <ul>
     * <li>{@link TestRunStarted} - the first event sent.
     * <li>{@link TestSourceRead} - sent for each feature file read, contains the feature file source.
     * <li>{@link TestCaseStarted} - sent before starting the execution of a Test Case(/Pickle/Scenario), contains the Test Case
     * <li>{@link TestStepStarted} - sent before starting the execution of a Test Step, contains the Test Step
     * <li>{@link TestStepFinished} - sent after the execution of a Test Step, contains the Test Step and its Result.
     * <li>{@link TestCaseFinished} - sent after the execution of a Test Case(/Pickle/Scenario), contains the Test Case and its Result.
     * <li>{@link TestRunFinished} - the last event sent.
     * <li>{@link EmbedEvent} - calling scenario.embed in a hook triggers this event.
     * <li>{@link WriteEvent} - calling scenario.write in a hook triggers this event.
     * </ul>
     */
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, getTestRunStartedHandler());
        publisher.registerHandlerFor(TestSourceRead.class, getTestSourceReadHandler());
        publisher.registerHandlerFor(TestCaseStarted.class, getTestCaseStartedHandler());
        publisher.registerHandlerFor(TestStepStarted.class, getTestStepStartedHandler());
        publisher.registerHandlerFor(TestStepFinished.class, getTestStepFinishedHandler());
        publisher.registerHandlerFor(TestCaseFinished.class, getTestCaseFinishedHandler());
        publisher.registerHandlerFor(TestRunFinished.class, getTestRunFinishedHandler());
        publisher.registerHandlerFor(EmbedEvent.class, getEmbedEventHandler());
        publisher.registerHandlerFor(WriteEvent.class, getWriteEventHandler());
    }

    /**
     * Manipulations before the launch starts
     */
    protected void beforeLaunch() {
        startLaunch();
    }

    /**
     * Finish RP launch
     */
    protected void afterLaunch() {
        FinishExecutionRQ finishLaunchRq = new FinishExecutionRQ();
        finishLaunchRq.setEndTime(Calendar.getInstance().getTime());
        RP.get().finish(finishLaunchRq);
    }

    /**
     * Manipulations before the feature starts
     */
    protected void beforeFeature() {
        startFeature();
    }

    /**
     * Finish Cucumber feature
     */
    protected void afterFeature() {
        Utils.finishTestItem(RP.get(), currentFeatureContext.getFeatureId());
        currentFeatureContext = null;
    }

    /**
     * Start Cucumber scenario
     */
    protected void beforeScenario() {
        Maybe<String> id = Utils.startNonLeafNode(RP.get(),
                currentFeatureContext.getFeatureId(),
                Utils.buildNodeName(currentScenarioContext.getKeyword(), AbstractReporter.COLON_INFIX, currentScenarioContext.getName(), currentScenarioContext.getOutlineIteration()),
                currentFeatureContext.getUri() + ":" + currentScenarioContext.getLine(),
                currentScenarioContext.getTags(),
                getScenarioTestItemType()
        );
        currentScenarioContext.setId(id);
    }

    /**
     * Finish Cucumber scenario
     */
    protected void afterScenario(TestCaseFinished event) {
        Utils.finishTestItem(RP.get(), currentScenarioContext.getId(), event.getResult().getStatus().toString());
        currentScenarioContext = null;
    }

    /**
     * Start Cucumber feature
     */
    protected void startFeature() {
        StartTestItemRQ rq = new StartTestItemRQ();
        Maybe<String> root = getRootItemId();
        rq.setDescription(currentFeatureContext.getUri());
        rq.setName(Utils.buildNodeName(currentFeatureContext.getFeature().getKeyword(), AbstractReporter.COLON_INFIX, currentFeatureContext.getFeature().getName(), null));
        rq.setTags(currentFeatureContext.getTags());
        rq.setStartTime(Calendar.getInstance().getTime());
        rq.setType(getFeatureTestItemType());
        if (null == root) {
            currentFeatureContext.setFeatureId(RP.get().startTestItem(rq));
        } else {
            currentFeatureContext.setFeatureId(RP.get().startTestItem(root, rq));
        }
    }

    /**
     * Start RP launch
     */
    protected void startLaunch() {
        RP = Suppliers.memoize(new Supplier<Launch>() {

            /* should no be lazy */
            private final Date startTime = Calendar.getInstance().getTime();

            @Override
            public Launch get() {
                final ReportPortal reportPortal = ReportPortal.builder().build();
                ListenerParameters parameters = reportPortal.getParameters();

                StartLaunchRQ rq = new StartLaunchRQ();
                rq.setName(parameters.getLaunchName());
                rq.setStartTime(startTime);
                rq.setMode(parameters.getLaunchRunningMode());
                rq.setTags(parameters.getTags());
                rq.setDescription(parameters.getDescription());

                Launch launch = reportPortal.newLaunch(rq);
                return launch;
            }
        });
    }

    /**
     * Start Cucumber step
     *
     * @param step Step object
     */
    protected abstract void beforeStep(TestStep step);

    /**
     * Finish Cucumber step
     *
     * @param result Step result
     */
    protected abstract void afterStep(Result result);

    /**
     * Called when before/after-hooks are started
     *
     * @param isBefore - if true, before-hook is started, if false - after-hook
     */
    protected abstract void beforeHooks(Boolean isBefore);

    /**
     * Called when before/after-hooks are finished
     *
     * @param isBefore - if true, before-hook is finished, if false - after-hook
     */
    protected abstract void afterHooks(Boolean isBefore);

    /**
     * Called when a specific before/after-hook is finished
     *
     * @param step     TestStep object
     * @param result   Hook result
     * @param isBefore - if true, before-hook, if false - after-hook
     */
    protected abstract void hookFinished(TestStep step, Result result, Boolean isBefore);

    /**
     * Return RP test item name mapped to Cucumber feature
     *
     * @return test item name
     */
    protected abstract String getFeatureTestItemType();

    /**
     * Return RP test item name mapped to Cucumber scenario
     *
     * @return test item name
     */
    protected abstract String getScenarioTestItemType();

    /**
     * Report test item result and error (if present)
     *
     * @param result  - Cucumber result object
     * @param message - optional message to be logged in addition
     */
    protected void reportResult(Result result, String message) {
        String cukesStatus = result.getStatus().toString();
        String level = Utils.mapLevel(cukesStatus);
        String errorMessage = null;
        if (result.getError() != null)
            errorMessage = result.getError().getMessage();
        if (errorMessage != null) {
            Utils.sendLog(errorMessage, level, null);
        }
        if (message != null) {
            Utils.sendLog(message, level, null);
        }
    }

    protected void embedding(String mimeType, byte[] data) {
        File file = new File();
        String embeddingName;
        try {
            embeddingName = MimeTypes.getDefaultMimeTypes().forName(mimeType).getType().getType();
        } catch (MimeTypeException e) {
            LOGGER.warn("Mime-type not found", e);
            embeddingName = "embedding";
        }
        file.setName(embeddingName);
        file.setContent(data);
        Utils.sendLog(embeddingName, "UNKNOWN", file);
    }

    protected void write(String text) {
        Utils.sendLog(text, "INFO", null);
    }

    protected boolean isBefore(TestStep step) {
        return HookType.BEFORE == ((HookTestStep) step).getHookType();
    }

    protected abstract Maybe<String> getRootItemId();


    /**
     * Private part that responsible for handling events
     */

    private EventHandler<TestRunStarted> getTestRunStartedHandler() {
        return new EventHandler<TestRunStarted>() {
            @Override
            public void receive(TestRunStarted event) {
                beforeLaunch();
            }
        };
    }

    private EventHandler<TestSourceRead> getTestSourceReadHandler() {
        return new EventHandler<TestSourceRead>() {
            @Override
            public void receive(TestSourceRead event) {
                RunningContext.FeatureContext.addTestSourceReadEvent(event.getUri().toString(), event);
            }
        };
    }

    private EventHandler<TestCaseStarted> getTestCaseStartedHandler() {
        return this::handleStartOfTestCase;
    }

    private EventHandler<TestStepStarted> getTestStepStartedHandler() {
        return new EventHandler<TestStepStarted>() {
            @Override
            public void receive(TestStepStarted event) {
                handleTestStepStarted(event);
            }
        };
    }

    private EventHandler<TestStepFinished> getTestStepFinishedHandler() {
        return new EventHandler<TestStepFinished>() {
            @Override
            public void receive(TestStepFinished event) {
                handleTestStepFinished(event);
            }
        };
    }

    private EventHandler<TestCaseFinished> getTestCaseFinishedHandler() {
        return this::afterScenario;
    }

    private EventHandler<TestRunFinished> getTestRunFinishedHandler() {
        return new EventHandler<TestRunFinished>() {
            @Override
            public void receive(TestRunFinished event) {
                if (currentFeatureContext != null) {
                    handleEndOfFeature();
                }
                afterLaunch();
            }
        };
    }

    private EventHandler<EmbedEvent> getEmbedEventHandler() {
        return new EventHandler<EmbedEvent>() {
            @Override
            public void receive(EmbedEvent event) {
                embedding(event.getMediaType(), event.getData());
            }
        };
    }

    private EventHandler<WriteEvent> getWriteEventHandler() {
        return new EventHandler<WriteEvent>() {
            @Override
            public void receive(WriteEvent event) {
                write(event.getText());
            }
        };
    }

    private void handleStartOfFeature(TestCase testCase) {
        currentFeatureContext = new RunningContext.FeatureContext().processTestSourceReadEvent(testCase);
        beforeFeature();
    }

    private void handleEndOfFeature() {
        afterFeature();
    }

    private void handleStartOfTestCase(TestCaseStarted event) {
        TestCase testCase = event.getTestCase();
        if (currentFeatureContext != null && !testCase.getUri().toString().equals(currentFeatureContext.getUri())) {
            handleEndOfFeature();
        }
        if (currentFeatureContext == null) {
            handleStartOfFeature(testCase);
        }
        if (!currentFeatureContext.getUri().equals(testCase.getUri().toString())) {
            throw new IllegalStateException("Scenario URI does not match Feature URI.");
        }
        if (currentScenarioContext == null) {
            currentScenarioContext = currentFeatureContext.getScenarioContext(testCase);
        }
        beforeScenario();
    }

    private void handleTestStepStarted(TestStepStarted event) {
        TestStep testStep = event.getTestStep();
        if (testStep instanceof HookTestStep) {
            beforeHooks(isBefore(testStep));
        } else {
            if (currentScenarioContext.withBackground()) {
                currentScenarioContext.nextBackgroundStep();
            }
            beforeStep(testStep);
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof HookTestStep) {
            hookFinished(event.getTestStep(), event.getResult(), isBefore(event.getTestStep()));
            afterHooks(isBefore(event.getTestStep()));
        } else {
            afterStep(event.getResult());
        }
    }

}
