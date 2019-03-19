package by.courses.java.plugin.checkup.util;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public final class FileSystemFacade {

  private static FileSystemFacade instance = new FileSystemFacade();

  public static FileSystemFacade getInstance() {
    return instance;
  }

  private FileSystemFacade() {
  }

  public Map<String, List<String>> getAllRuntimeClasspathElements(List<MavenProject> allProjects) throws DependencyResolutionRequiredException {
    Map<String, List<String>> allRuntimeClasspathElements = new HashMap<>();
    for (MavenProject mavenProject : allProjects) {
      allRuntimeClasspathElements.put(mavenProject.getArtifactId(), mavenProject.getRuntimeClasspathElements());
    }
    return allRuntimeClasspathElements;
  }

  public Map<String, List<String>> getAllCompileSourceRoots(List<MavenProject> allProjects) {
    Map<String, List<String>> allCompileSourceRoots = new HashMap<>();
    for (MavenProject mavenProject : allProjects) {
      allCompileSourceRoots.put(mavenProject.getArtifactId(), mavenProject.getCompileSourceRoots());
    }
    return allCompileSourceRoots;
  }

  /*
   * Extension point for URLClassLoader
   *
   * Example: URLClassLoader newClassLoader = new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader());
   *          Class clazz = newClassLoader.loadClass("com.test.Test");
   *  */
  public URL[] createURLs(Collection<List<String>> allValueRuntimeClasspathElements) throws MalformedURLException {
    List<String> runtimeClasspathElements = new ArrayList<>();
    for (List<String> element : allValueRuntimeClasspathElements) {
      runtimeClasspathElements.addAll(element);
    }
    List<URL> urls = new ArrayList<>();
    for (String element : runtimeClasspathElements) {
      urls.add(new File(element).toURI().toURL());
    }
    URL[] runtimeUrls = new URL[urls.size()];
    runtimeUrls = urls.toArray(runtimeUrls);
    return runtimeUrls;
  }
}