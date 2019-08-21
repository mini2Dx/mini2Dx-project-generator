package com.badlogic.gdx.setup;


import java.util.HashMap;

public class DependencyBank {

	//Repositories
	static String mavenLocal = "mavenLocal()";
	static String mavenCentral = "mavenCentral()";
	static String jCenter = "jcenter()";
	static String google = "https://maven.google.com/";
	static String jitpack = "https://jitpack.io";
	static String libGDXSnapshotsUrl = "https://oss.sonatype.org/content/repositories/snapshots/";
	static String libGDXReleaseUrl = "https://oss.sonatype.org/content/repositories/releases/";

	//Project plugins
	static String parclPluginImport = "org.mini2Dx:parcl:";
	static String gradleButlerPluginImport = "org.mini2Dx:butler:";
	static String androidPluginImport = "com.android.tools.build:gradle:3.4.2";
	static String roboVMPluginImport = "com.mobidevelop.robovm:robovm-gradle-plugin:";
	
	//Extension versions
	static String box2DLightsVersion = "1.4";
	static String ashleyVersion = "1.7.0";
	static String aiVersion = "1.8.0";

	HashMap<ProjectDependency, Dependency> gdxDependencies = new HashMap<ProjectDependency, Dependency>();

	public DependencyBank() {
		for (ProjectDependency projectDep : ProjectDependency.values()) {
			Dependency dependency = new Dependency(projectDep.name(),
					projectDep.getDependencies(ProjectType.CORE),
					projectDep.getDependencies(ProjectType.DESKTOP),
					projectDep.getDependencies(ProjectType.ANDROID),
					projectDep.getDependencies(ProjectType.IOS));
			gdxDependencies.put(projectDep, dependency);
		}
	}

	public Dependency getDependency(ProjectDependency gdx) {
		return gdxDependencies.get(gdx);
	}

	/**
	 * This enum will hold all dependencies available for libgdx, allowing the setup to pick the ones needed by default,
	 * and allow the option to choose extensions as the user wishes.
	 * <p/>
	 * These depedency strings can be later used in a simple gradle plugin to manipulate the users project either after/before
	 * project generation
	 *
	 * @see Dependency for the object that handles sub-module dependencies. If no dependency is found for a sub-module, ie
	 * FreeTypeFont for gwt, an exception is thrown so the user can be notified of incompatability
	 */	
	public enum ProjectDependency {
		MINI2DX(
			new String[]{"org.mini2Dx:mini2Dx-core:$mini2DxVersion"},
			new String[]{"org.mini2Dx:mini2Dx-desktop:$mini2DxVersion"},
			new String[]{"org.mini2Dx:mini2Dx-android:$mini2DxVersion", 
					"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi", 
					"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi-v7a",
					"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-arm64-v8a",
					"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86",
					"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86_64",
					"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-armeabi", 
					"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-armeabi-v7a",
					"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-arm64-v8a",
					"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86",
					"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86_64"},
			new String[]{"org.mini2Dx:mini2Dx-ios:$mini2DxVersion", 
					"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-ios"},
			null,

				"Core Library for mini2Dx"
		),
		MINI2DXV2(
				new String[]{"org.mini2Dx:mini2Dx-core:$mini2DxVersion"},
				new String[]{"org.mini2Dx:mini2Dx-libgdx-desktop:$mini2DxVersion",
						"com.badlogicgames.gdx:gdx-tools:$gdxVersion"},
				new String[]{"org.mini2Dx:mini2Dx-libgdx-android:$mini2DxVersion",
						"com.android.support:multidex:$multiDexVersion",
						"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi",
						"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi-v7a",
						"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-arm64-v8a",
						"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86",
						"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86_64",
						"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-armeabi",
						"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-armeabi-v7a",
						"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-arm64-v8a",
						"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86",
						"com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-x86_64"},
				new String[]{"org.mini2Dx:mini2Dx-libgdx-ios:$mini2DxVersion",
						"com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-ios"},
				null,

				"Core Library for mini2Dx"
		),
		TILED(
				new String[]{"org.mini2Dx:mini2Dx-tiled:$mini2DxVersion"},
				new String[]{},
				new String[]{},
				new String[]{},
				new String[]{},

				"Tiled support for mini2Dx"
		),
		ARTEMIS (
				new String[]{"org.mini2Dx:mini2Dx-artemis-odb:$mini2DxVersion"},
				new String[]{},
				new String[]{},
				new String[]{},
				new String[]{},

				"Artemis ODB support for mini2Dx"
		),
		UI (
				new String[]{"org.mini2Dx:mini2Dx-ui:$mini2DxVersion"},
				new String[]{},
				new String[]{},
				new String[]{},
				new String[]{},

				"UI framework for mini2Dx"
		),
		MESSAGE_BUS (
				new String[]{"org.mini2Dx:mini2Dx-core:$minibusVersion"},
				new String[]{},
				new String[]{},
				new String[]{},
				new String[]{},

				"Message Bus via minibus"
		),
		LUA_SCRIPTING (
				new String[]{"org.mini2Dx:miniscript-lua:$miniscriptVersion"},
				new String[]{},
				new String[]{"com.wu-man:android-bsf-api:$androidBsfVersion"},
				new String[]{},
				new String[]{},

				"Lua scripting via miniscript"
		),
		GROOVY_SCRIPTING (
				new String[]{"org.mini2Dx:miniscript-groovy:$miniscriptVersion"},
				new String[]{},
				new String[]{"com.wu-man:android-bsf-api:$androidBsfVersion"},
				new String[]{},
				new String[]{},

				"Groovy scripting via miniscript"
		),
		PYTHON_SCRIPTING (
				new String[]{"org.mini2Dx:miniscript-python:$miniscriptVersion"},
				new String[]{},
				new String[]{"com.wu-man:android-bsf-api:$androidBsfVersion"},
				new String[]{},
				new String[]{},

				"Python scripting via miniscript"
		),
		RUBY_SCRIPTING (
				new String[]{"org.mini2Dx:miniscript-ruby:$miniscriptVersion"},
				new String[]{},
				new String[]{"com.wu-man:android-bsf-api:$androidBsfVersion"},
				new String[]{},
				new String[]{},

				"Ruby scripting via miniscript"
		),
		TOOLS(
			new String[]{},
			new String[]{"com.badlogicgames.gdx:gdx-tools:$gdxVersion"},
			new String[]{},
			new String[]{},
			new String[]{},

				"Collection of tools, including 2D/3D particle editors, texture packers, and file processors"
		),
		AI(
			new String[]{"com.badlogicgames.gdx:gdx-ai:$aiVersion"},
			new String[]{},
			new String[]{"com.badlogicgames.gdx:gdx-ai:$aiVersion"},
			new String[]{},
			new String[]{"com.badlogicgames.gdx:gdx-ai:$aiVersion:sources"},

				"Artificial Intelligence framework"
		);

		private String[] coreDependencies;
		private String[] desktopDependencies;
		private String[] androidDependencies;
		private String[] iosDependencies;
		private String description;

		ProjectDependency(String[] coreDeps, String[] desktopDeps, String[] androidDeps, String[] iosDeps, String[] gwtDeps, String description) {
			this.coreDependencies = coreDeps;
			this.desktopDependencies = desktopDeps;
			this.androidDependencies = androidDeps;
			this.iosDependencies = iosDeps;
			this.description = description;
		}

		public String[] getDependencies(ProjectType type) {
			switch (type) {
				case CORE:
					return coreDependencies;
				case DESKTOP:
					return desktopDependencies;
				case ANDROID:
					return androidDependencies;
				case IOS:
					return iosDependencies;
			}
			return null;
		}

		public String getDescription() {
			return description;
		}
	}


	public enum ProjectType {
		CORE("core", new String[]{"java"}),
		DESKTOP("desktop", new String[]{"java", "application", "org.mini2Dx.parcl", "org.mini2Dx.butler"}),
		ANDROID("android", new String[]{"android"}),
		IOS("ios", new String[]{"java", "robovm"});

		private final String name;
		private final String[] plugins;

		ProjectType(String name, String plugins[]) {
			this.name = name;
			this.plugins = plugins;
		}

		public String getName() {
			return name;
		}

		public String[] getPlugins() {
			return plugins;
		}
	}

}
