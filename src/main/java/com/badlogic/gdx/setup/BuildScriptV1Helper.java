package com.badlogic.gdx.setup;


import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.badlogic.gdx.setup.DependencyBank.ProjectType;

public class BuildScriptV1Helper {

	private static int indent = 0;

	public static void addBuildScript(List<ProjectType> projects, BufferedWriter wr, Release release) throws IOException {
		write(wr, "buildscript {");
		//repos
		write(wr, "repositories {");
		write(wr, DependencyBank.mavenLocal);
		write(wr, DependencyBank.mavenCentral);
		write(wr, DependencyBank.jCenter);
		write(wr, "maven { url \"" + DependencyBank.libGDXSnapshotsUrl + "\" }");
		write(wr, "maven { url \"" + DependencyBank.libGDXReleaseUrl + "\" }");
		write(wr, "maven { url \"" + DependencyBank.google + "\" }");
		write(wr, "}");
		//dependencies
		write(wr, "dependencies {");
		if (projects.contains(ProjectType.DESKTOP)) {
			write(wr, "classpath '" + DependencyBank.parclPluginImport + release.getParclVersion() + "'");
			write(wr, "classpath '" + DependencyBank.gradleButlerPluginImport + release.getGradleButlerPluginVersion() + "'");
		}
		if (projects.contains(ProjectType.ANDROID)) {
			write(wr, "classpath '" + DependencyBank.androidPluginImport + "'");
		}
		if (projects.contains(ProjectType.IOS)) {
			write(wr, "classpath '" + DependencyBank.roboVMPluginImport + release.getRoboVMVersion() + "'");
		}
		write(wr, "}");
		write(wr, "}");
		space(wr);
	}

	public static void addAllProjects(BufferedWriter wr, Release release) throws IOException {
		write(wr, "allprojects {");
		write(wr, "apply plugin: \"eclipse\"");
		write(wr, "apply plugin: \"idea\"");
		space(wr);
		write(wr, "version = '1.0.0'");
		write(wr, "ext {");
		write(wr, "appName = '%APP_NAME%'");
		write(wr, "mini2DxVersion = '" + release.getMini2DxVersion() + "'");
		write(wr, "gdxVersion = '" + release.getLibGDXVersion() + "'");
		write(wr, "roboVMVersion = '" + release.getRoboVMVersion() + "'");
		write(wr, "minibusVersion = '" + release.getMinibusVersion() + "'");
		write(wr, "miniscriptVersion = '" + release.getMiniscriptVersion() + "'");
		write(wr, "box2DLightsVersion = '" + DependencyBank.box2DLightsVersion + "'");
		write(wr, "ashleyVersion = '" + DependencyBank.ashleyVersion + "'");
		write(wr, "aiVersion = '" + DependencyBank.aiVersion + "'");
		write(wr, "androidBsfVersion = '3.1.3'");
		write(wr, "}");
		space(wr);
		write(wr, "repositories {");
		write(wr, DependencyBank.mavenLocal);
		write(wr, DependencyBank.mavenCentral);
		write(wr, DependencyBank.jCenter);
		write(wr, "maven { url \"" + DependencyBank.libGDXSnapshotsUrl + "\" }");
		write(wr, "maven { url \"" + DependencyBank.libGDXReleaseUrl + "\" }");
		write(wr, "maven { url \"" + DependencyBank.google + "\" }");
		write(wr, "}");
		write(wr, "}");
	}

	public static void addProject(ProjectType project, List<Dependency> dependencies, BufferedWriter wr) throws IOException {
		space(wr);
		write(wr, "project(\":" + project.getName() + "\") {");
		for (String plugin : project.getPlugins()) {
			write(wr, "apply plugin: \"" + plugin + "\"");
		}
		space(wr);
		addConfigurations(project, wr);
		space(wr);
		addDependencies(project, dependencies, wr);
		if(project.equals(ProjectType.DESKTOP)) {
			addParclConfig(project, wr);
			addButlerConfig(project, wr);
		}
		write(wr, "}");
	}
	
	private static void addParclConfig(ProjectType project, BufferedWriter wr) throws IOException {
		write(wr, "parcl {");
		
		write(wr, "exe {");
		write(wr, "vmArgs = [\"-Xmx1g\"]");
		write(wr, "exeName = \"%APP_NAME%\"");
		write(wr, "}");
		
		write(wr, "app {");
		write(wr, "vmArgs = [\"-Xmx1g\"]");
		write(wr, "appName = \"%APP_NAME%\"");
		write(wr, "icon = \"icon.icns\"");
		write(wr, "applicationCategory = \"public.app-category.adventure-games\"");
		write(wr, "displayName = \"%APP_NAME%\"");
		write(wr, "identifier = \"%APP_NAME%\"");
		write(wr, "copyright = \"Copyright " + Calendar.getInstance().get(Calendar.YEAR) + " Your Company Name\"");
		write(wr, "}");
		
		write(wr, "linux {");
		write(wr, "vmArgs = [\"-Xmx1g\"]");
		write(wr, "binName = \"%APP_NAME%\"");
		write(wr, "}");
		
		write(wr, "}");
	}
	
	private static void addButlerConfig(ProjectType project, BufferedWriter wr) throws IOException {
		write(wr, "butler {");
		
		write(wr, "user = \"your-itchio-user\"");
		write(wr, "game = \"%APP_NAME%\"");
		
		write(wr, "windows {");
		write(wr, "binDirectory = \"$buildDir/windows\"");
		write(wr, "}");
		
		write(wr, "osx {");
		write(wr, "binDirectory = \"$buildDir/mac\"");
		write(wr, "}");
		
		write(wr, "linux {");
		write(wr, "binDirectory = \"$buildDir/linux\"");
		write(wr, "}");
		
		write(wr, "}");
	}

	private static void addDependencies(ProjectType project, List<Dependency> dependencyList, BufferedWriter wr) throws IOException {
		write(wr, "dependencies {");
		if (!project.equals(ProjectType.CORE)) {
			write(wr, "compile project(\":" + ProjectType.CORE.getName() + "\")");
		}
		for (Dependency dep : dependencyList) {
			if (dep.getDependencies(project) == null) continue;
			for (String moduleDependency : dep.getDependencies(project)) {
				if (moduleDependency == null) continue;
				if ((project.equals(ProjectType.ANDROID)) && moduleDependency.contains("native")) {
					write(wr, "natives \"" + moduleDependency + "\"");
				} else {
					write(wr, "compile \"" + moduleDependency + "\"");
				}
			}
		}
		write(wr, "}");
	}

	private static void addConfigurations(ProjectType project, BufferedWriter wr) throws IOException {
		if (project.equals(ProjectType.ANDROID)) {
			write(wr, "configurations { natives }");
		}
	}

	private static void write(BufferedWriter wr, String input) throws IOException {
		int delta = StringUtils.countMatches(input, '{') - StringUtils.countMatches(input, '}');
		indent += delta *= 4;
		indent = clamp(indent);
		if (delta > 0) {
			wr.write(StringUtils.repeat(" ", clamp(indent - 4)) + input + "\n");
		} else if (delta < 0) {
			wr.write(StringUtils.repeat(" ", clamp(indent)) + input + "\n");
		} else {
			wr.write(StringUtils.repeat(" ", indent) + input + "\n");
		}
	}

	private static void space(BufferedWriter wr) throws IOException {
		wr.write("\n");
	}

	private static int clamp(int indent) {
		if (indent < 0) {
			return 0;
		}
		return indent;
	}

	static class StringUtils {

		public static int countMatches(String input, char match) {
			int count = 0;
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) == match) {
					count++;
				}
			}
			return count;
		}

		public static String repeat(String toRepeat, int count) {
			String repeat = "";
			for (int i = 0; i < count; i++) {
				repeat += toRepeat;
			}
			return repeat;
		}
	}

}
