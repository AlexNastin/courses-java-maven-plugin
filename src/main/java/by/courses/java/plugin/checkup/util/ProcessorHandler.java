package by.courses.java.plugin.checkup.util;

import by.courses.java.plugin.checkup.analysis.EmptyCatchProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.LocalFiledNameProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.MethodNameProcessor;
import by.courses.java.plugin.checkup.exception.UnsupportedProcessorException;
import by.courses.java.plugin.checkup.analysis.EmptyMethodBodyProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.ConstantFieldNameProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.StaticFiledNameProcessor;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class ProcessorHandler {

  private final static Log LOGGER = new SystemStreamLog();

  private static Map<Class<? extends AbstractProcessor>, Function<AbstractProcessor, Boolean>> container = new HashMap<>();

  static {
    container.put(ConstantFieldNameProcessor.class, ProcessorHandler::checkConstantFieldNameProcessor);
    container.put(LocalFiledNameProcessor.class, ProcessorHandler::checkLocalFiledNameProcessor);
    container.put(MethodNameProcessor.class, ProcessorHandler::checkMethodNameProcessor);
    container.put(StaticFiledNameProcessor.class, ProcessorHandler::checkStaticFiledNameProcessor);
    container.put(EmptyCatchProcessor.class, ProcessorHandler::checkEmptyCatchProcessor);
    container.put(EmptyMethodBodyProcessor.class, ProcessorHandler::checkEmptyMethodBodyProcessor);
  }

  private ProcessorHandler() {
  }

  public static Boolean handleProcessor(AbstractProcessor processor) {
    Function<AbstractProcessor, Boolean> processorConsumer = container.get(processor.getClass());
    if (processorConsumer == null) {
      throw new UnsupportedProcessorException();
    }
    return processorConsumer.apply(processor);
  }

  private static Boolean checkConstantFieldNameProcessor(AbstractProcessor processor) {
    ConstantFieldNameProcessor constantFieldNameProcessor = (ConstantFieldNameProcessor) processor;
    List<CtField> failConstantNames = constantFieldNameProcessor.getFailConstantNames();
    failConstantNames.forEach(ctField -> {
              LOGGER.error("Wrong name constant: " + ctField.getSimpleName() + ". Please, see to Java Code Convention in Chapter 9");
              LOGGER.error("Path to wrong element: " + ctField.getPath());
            }
    );
    return failConstantNames.isEmpty();
  }

  private static Boolean checkLocalFiledNameProcessor(AbstractProcessor processor) {
    LocalFiledNameProcessor localFiledNameProcessor = (LocalFiledNameProcessor) processor;
    List<CtField> failFiledNames = localFiledNameProcessor.getFailFiledNames();
    failFiledNames.forEach(ctField -> {
              LOGGER.error("Wrong name local variable: " + ctField.getSimpleName() + ". Please, see to Java Code Convention in Chapter 9");
              LOGGER.error("Path to wrong element: " + ctField.getPath());
            }
    );
    return failFiledNames.isEmpty();
  }

  private static Boolean checkMethodNameProcessor(AbstractProcessor processor) {
    MethodNameProcessor methodNameProcessor = (MethodNameProcessor) processor;
    List<CtMethod> failNameMethods = methodNameProcessor.getFailNameMethods();
    failNameMethods.forEach(ctMethod -> {
              LOGGER.error("Wrong method name: " + ctMethod.getSimpleName() + ". Please, see to Java Code Convention");
              LOGGER.error("Path to wrong element: " + ctMethod.getPath());
            }
    );
    return true;
  }

  private static Boolean checkStaticFiledNameProcessor(AbstractProcessor processor) {
    StaticFiledNameProcessor staticFiledNameProcessor = (StaticFiledNameProcessor) processor;
    List<CtField> failStaticFiledNames = staticFiledNameProcessor.getFailStaticFiledNames();
    failStaticFiledNames.forEach(ctField -> {
              LOGGER.error("Wrong static name variable: " + ctField.getSimpleName() + ". Please, see to Java Code Convention");
              LOGGER.error("Path to wrong element: " + ctField.getPath());
            }
    );
    return failStaticFiledNames.isEmpty();
  }

  private static Boolean checkEmptyCatchProcessor(AbstractProcessor processor) {
    EmptyCatchProcessor emptyCatchProcessor = (EmptyCatchProcessor) processor;
    List<CtCatch> emptyCatches = emptyCatchProcessor.getEmptyCatches();
    emptyCatches.forEach(ctCatch -> {
              LOGGER.error("You have empty catch: " + ctCatch.getBody());
              LOGGER.error("Path to catch: " + ctCatch.getPath());
            }
    );
    return true;
  }

  private static Boolean checkEmptyMethodBodyProcessor(AbstractProcessor processor) {
    EmptyMethodBodyProcessor emptyMethodBodyProcessor = (EmptyMethodBodyProcessor) processor;
    List<CtMethod> emptyMethods = emptyMethodBodyProcessor.getEmptyMethods();
    emptyMethods.forEach(ctMethod -> {
              LOGGER.warn("You have empty method: " + ctMethod.getSimpleName());
              LOGGER.warn("Path to method: " + ctMethod.getPath());
            }
    );
    return true;
  }
}