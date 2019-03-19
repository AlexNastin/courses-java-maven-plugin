package by.courses.java.plugin.checkup.mojo;

import by.courses.java.plugin.checkup.analysis.EmptyCatchProcessor;
import by.courses.java.plugin.checkup.analysis.EmptyMethodBodyProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.ConstantFieldNameProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.LocalFiledNameProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.MethodNameProcessor;
import by.courses.java.plugin.checkup.analysis.jcc.StaticFiledNameProcessor;
import by.courses.java.plugin.checkup.exception.JavaCodeConventionException;
import by.courses.java.plugin.checkup.util.BuildChecker;
import by.courses.java.plugin.checkup.util.FileSystemFacade;
import by.courses.java.plugin.checkup.util.SpoonFacade;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.util.List;
import java.util.Map;

@Mojo(requiresDependencyResolution = ResolutionScope.COMPILE,
        requiresDirectInvocation = true, name = "jcc")
public class JavaCodeConventionMojo extends AbstractMojo {

  private final Log LOG = getLog();

  @Component
  private MavenSession mavenSession;

  private FileSystemFacade fileSystemFacade = FileSystemFacade.getInstance();

  private SpoonFacade spoonFacade = new SpoonFacade();

  private EmptyMethodBodyProcessor emptyMethodBodyProcessor = EmptyMethodBodyProcessor.getInstance();

  private EmptyCatchProcessor emptyCatchProcessor = EmptyCatchProcessor.getInstance();

  private StaticFiledNameProcessor staticFiledNameProcessor = StaticFiledNameProcessor.getInstance();

  private MethodNameProcessor methodNameProcessor = MethodNameProcessor.getInstance();

  private LocalFiledNameProcessor filedNameProcessor = LocalFiledNameProcessor.getInstance();

  private ConstantFieldNameProcessor constantFieldNameProcessor = ConstantFieldNameProcessor.getInstance();

  private BuildChecker buildChecker = new BuildChecker(emptyCatchProcessor, emptyMethodBodyProcessor, staticFiledNameProcessor, methodNameProcessor, filedNameProcessor, constantFieldNameProcessor);

  public void execute() throws MojoFailureException {

    Map<String, List<String>> allCompileSourceRoots = fileSystemFacade.getAllCompileSourceRoots(mavenSession.getAllProjects());

    LOG.info("Checking has started.");

    spoonFacade.start(allCompileSourceRoots);

    LOG.info("Checking has finished.");

    LOG.info("Build checking has started.");

    boolean isCheck = buildChecker.checkBuild();

    LOG.info("Build checking has finished.");

    if (!isCheck) {
      throw new JavaCodeConventionException("");
    }
  }
}