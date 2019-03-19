package by.courses.java.plugin.checkup.util;

import by.courses.java.plugin.checkup.analysis.EmptyCatchProcessor;
import by.courses.java.plugin.checkup.analysis.EmptyMethodBodyProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.ConstantFieldNameProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.LocalFiledNameProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.MethodNameProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.StaticFiledNameProcessor;
import spoon.Launcher;
import spoon.processing.AbstractProcessor;

import java.util.*;
import java.util.stream.Collectors;

public final class SpoonFacade {

  private EmptyMethodBodyProcessor emptyMethodBodyProcessor = EmptyMethodBodyProcessor.getInstance();

  private MethodNameProcessor methodNameProcessor = MethodNameProcessor.getInstance();

  private EmptyCatchProcessor emptyCatchProcessor = EmptyCatchProcessor.getInstance();

  private LocalFiledNameProcessor filedNameProcessor = LocalFiledNameProcessor.getInstance();

  private ConstantFieldNameProcessor constantFieldNameProcessor = ConstantFieldNameProcessor.getInstance();

  private StaticFiledNameProcessor staticFiledNameProcessor = StaticFiledNameProcessor.getInstance();

  public List<Launcher> start(Map<String, List<String>> allCompileSourceRoots) {
    List<String> paths = allCompileSourceRoots.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    List<Launcher> launchers = createLaunchers(paths, emptyMethodBodyProcessor, emptyCatchProcessor, methodNameProcessor, filedNameProcessor, constantFieldNameProcessor, staticFiledNameProcessor);
    launchers.forEach(Launcher::run);
    return launchers;
  }

  public List<Launcher> start(List<String> paths) {
    List<Launcher> launchers = createLaunchers(paths);
    launchers.forEach(Launcher::run);
    return launchers;
  }

  public List<Launcher> start(List<String> paths, AbstractProcessor<?>... processors) {
    List<Launcher> launchers = createLaunchers(paths, processors);
    launchers.forEach(Launcher::run);
    return launchers;
  }

  private List<Launcher> createLaunchers(List<String> paths, AbstractProcessor<?>... processors) {
    List<Launcher> launchers = new ArrayList<>();
    paths.forEach(path -> {
      Launcher launcher = new Launcher();
      if (processors != null) {
        List<AbstractProcessor<?>> processorList = Arrays.asList(processors);
        processorList.forEach(launcher::addProcessor);
      }
      launcher.setArgs(createArgs(path));
      launchers.add(launcher);
    });
    return launchers;
  }

  private String[] createArgs(String path) {
    String[] args = new String[2];
    args[0] = "-i";
    args[1] = path;
    return args;
  }
}