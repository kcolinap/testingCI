package reportPortal.cucumber;

import io.cucumber.core.internal.gherkin.AstBuilder;
import io.cucumber.core.internal.gherkin.Parser;
import io.cucumber.core.internal.gherkin.ParserException;
import io.cucumber.core.internal.gherkin.TokenMatcher;
import io.cucumber.core.internal.gherkin.ast.*;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestSourceRead;
import io.cucumber.plugin.event.TestStep;
import io.reactivex.Maybe;

import java.util.*;

@SuppressWarnings("unchecked")
public class RunningContext {

    public static class FeatureContext {

        private static Map<String, TestSourceRead> pathToReadEventMap = new HashMap<String, TestSourceRead>();

        private String currentFeatureUri;
        private Maybe<String> currentFeatureId;
        private Feature currentFeature;
        private Set<String> tags;

        FeatureContext() {
            tags = new HashSet<String>();
        }

        static void addTestSourceReadEvent(String path, TestSourceRead event) {
            pathToReadEventMap.put(path, event);
        }

        ScenarioContext getScenarioContext(TestCase testCase) {
            ScenarioDefinition scenario = getScenario(testCase);
            ScenarioContext context = new ScenarioContext();
            context.processScenario(scenario);
            context.setTestCase(testCase);
            context.processBackground(getBackground());
            context.processScenarioOutline(scenario);
            context.processTags(testCase.getTags());
            return context;
        }


        FeatureContext processTestSourceReadEvent(TestCase testCase) {
            TestSourceRead event = pathToReadEventMap.get(testCase.getUri().toString());
            currentFeature = getFeature(event.getSource());
            currentFeatureUri = event.getUri().toString();
            tags = Utils.extractTags(currentFeature.getTags());
            return this;
        }

        Feature getFeature(String source) {
            Parser<GherkinDocument> parser = new Parser<GherkinDocument>(new AstBuilder());
            TokenMatcher matcher = new TokenMatcher();
            GherkinDocument gherkinDocument;
            try {
                gherkinDocument = parser.parse(source, matcher);
            } catch (ParserException e) {
                // Ignore exceptions
                return null;
            }
            return gherkinDocument.getFeature();
        }

        Background getBackground() {
            ScenarioDefinition background = getFeature().getChildren().get(0);
            if (background instanceof Background) {
                return (Background) background;
            } else {
                return null;
            }
        }

        Feature getFeature() {
            return currentFeature;
        }

        Set<String> getTags() {
            return tags;
        }

        String getUri() {
            return currentFeatureUri;
        }

        Maybe<String> getFeatureId() {
            return currentFeatureId;
        }

        void setFeatureId(Maybe<String> featureId) {
            this.currentFeatureId = featureId;
        }

        <T extends ScenarioDefinition> T getScenario(TestCase testCase) {
            List<ScenarioDefinition> featureScenarios = getFeature().getChildren();
            for (ScenarioDefinition scenario : featureScenarios) {
                if (scenario instanceof Background) {
                    continue;
                }
                if (testCase.getLine() == scenario.getLocation().getLine() && testCase.getName().equals(scenario.getName())) {
                    return (T) scenario;
                } else {
                    if (scenario instanceof ScenarioOutline) {
                        for (Examples example : ((ScenarioOutline) scenario).getExamples()) {
                            for (TableRow tableRow : example.getTableBody()) {
                                if (tableRow.getLocation().getLine() == testCase.getLine()) {
                                    return (T) scenario;
                                }
                            }
                        }
                    }
                }
            }
            throw new IllegalStateException("Scenario can't be null!");
        }
    }


    public static class ScenarioContext {

        private static Map<Integer, ArrayDeque<String>> outlineIterationsMap = new HashMap<Integer, ArrayDeque<String>>();

        private Maybe<String> id = null;
        private Background background;
        private ScenarioDefinition scenario;
        private Queue<Step> backgroundSteps;
        private Map<Integer, Step> scenarioLocationMap;
        private Set<String> tags;
        private TestCase testCase;
        private boolean hasBackground = false;

        ScenarioContext() {
            backgroundSteps = new ArrayDeque<Step>();
            scenarioLocationMap = new HashMap<Integer, Step>();
            tags = new HashSet<String>();
        }

        ScenarioContext processScenario(ScenarioDefinition scenario) {
            this.scenario = scenario;
            for (Step step : scenario.getSteps()) {
                scenarioLocationMap.put(step.getLocation().getLine(), step);
            }
            return this;
        }

        void processBackground(Background background) {
            if (background != null) {
                this.background = background;
                hasBackground = true;
                backgroundSteps.addAll(background.getSteps());
                mapBackgroundSteps(background);
            }
        }

        void processScenarioOutline(ScenarioDefinition scenarioOutline) {
            if (isScenarioOutline(scenarioOutline)) {
                if (!hasOutlineSteps()) {
                    int num = 0;
                    outlineIterationsMap.put(scenario.getLocation().getLine(), new ArrayDeque<String>());
                    for (Examples example : ((ScenarioOutline) scenarioOutline).getExamples()) {
                        num += example.getTableBody().size();
                    }
                    int headerSize = ((ScenarioOutline) scenarioOutline).getExamples().get(0).getTableHeader().getCells().size();
                    String name = scenarioOutline.getName();
                    for (int i = 1; i <= num; i++) {
                        for (int j = 0; j < headerSize; j++) {
                            String headerValue = ((ScenarioOutline) scenarioOutline).getExamples().get(0).getTableHeader().getCells().get(j).getValue();
                            if (name.contains("<" + headerValue + ">")) {
                                String currentValue = ((ScenarioOutline) scenarioOutline).getExamples().get(0).getTableBody().get(i - 1).getCells().get(j).getValue();
                                name = name.replace("<" + headerValue + ">", currentValue);
                            }
                        }
                        if (!name.equals(scenarioOutline.getName()))
                            outlineIterationsMap.get(scenario.getLocation().getLine()).add(" " + name + " [" + i + "]");
                        else
                            outlineIterationsMap.get(scenario.getLocation().getLine()).add(" [" + i + "]");

                        name = scenarioOutline.getName();
                    }
                }
            }
        }

        void processTags(List<String> pickleTags) {
            tags = Utils.extractPickleTags(pickleTags);
        }

        void mapBackgroundSteps(Background background) {
            for (Step step : background.getSteps()) {
                scenarioLocationMap.put(step.getLocation().getLine(), step);
            }
        }

        String getName() {
            return scenario.getName();
        }

        String getKeyword() {
            return scenario.getKeyword();
        }

        int getLine() {
            if (isScenarioOutline(scenario)) {
                return testCase.getLine();
            }
            return scenario.getLocation().getLine();
        }

        Set<String> getTags() {
            return tags;
        }

        String getStepPrefix() {
            if (hasBackground() && withBackground()) {
                return background.getKeyword().toUpperCase() + AbstractReporter.COLON_INFIX;
            } else {
                return "";
            }
        }

        Step getStep(TestStep testStep) {
            PickleStepTestStep pickleStepTestStep = (PickleStepTestStep) testStep;
            Step step = scenarioLocationMap.get(pickleStepTestStep.getStep().getLine());
            if (step != null) {
                return step;
            }
            throw new IllegalStateException(String.format("Trying to get step for unknown line in feature. Scenario: %s, line: %s", scenario.getName(), getLine()));
        }

        Maybe<String> getId() {
            return id;
        }

        void setId(Maybe<String> newId) {
            if (id != null) {
                throw new IllegalStateException("Attempting re-set scenario ID for unfinished scenario.");
            }
            id = newId;
        }

        void setTestCase(TestCase testCase) {
            this.testCase = testCase;
        }

        void nextBackgroundStep() {
            backgroundSteps.poll();
        }

        boolean isScenarioOutline(ScenarioDefinition scenario) {
            if (scenario != null) {
                return scenario instanceof ScenarioOutline;
            }
            return false;
        }

        boolean withBackground() {
            return !backgroundSteps.isEmpty();
        }

        boolean hasBackground() {
            return hasBackground && background != null;
        }

        boolean hasOutlineSteps() {
            return outlineIterationsMap.get(scenario.getLocation().getLine()) != null && !outlineIterationsMap.get(scenario.getLocation().getLine()).isEmpty();
        }

        String getOutlineIteration() {
            if (hasOutlineSteps()) {
                return outlineIterationsMap.get(scenario.getLocation().getLine()).poll();
            }
            return null;
        }
    }
}
