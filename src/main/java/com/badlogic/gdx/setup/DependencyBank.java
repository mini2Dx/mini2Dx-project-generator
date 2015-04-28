package com.badlogic.gdx.setup;


import java.util.HashMap;

public class DependencyBank {

	//Versions
	static String libGDXVersion = "1.5.4";
	static String mini2DxVersion = "1.0.0-SNAPSHOT";
	static String roboVMVersion = "1.0.0";
	static String buildToolsVersion = "20.0.0";
	static String androidAPILevel = "20";
	static String gwtVersion = "2.6.0";

	//Repositories
	static String mavenLocal = "mavenLocal()";
	static String mavenCentral = "mavenCentral()";
	static String jCenter = "jcenter()";
	static String libGDXSnapshotsUrl = "https://oss.sonatype.org/content/repositories/snapshots/";
	static String libGDXReleaseUrl = "https://oss.sonatype.org/content/repositories/releases/";
	static String mini2DxThirdPartyUrl = "http://maven.mini2dx.org/content/repositories/thirdparty/";
	static String mini2DxSnapshotsUrl = "http://maven.mini2dx.org/content/repositories/snapshots/";
	static String mini2DxReleaseUrl = "http://maven.mini2dx.org/content/repositories/releases/";

	//Project plugins
	static String gwtPluginImport = "de.richsource.gradle.plugins:gwt-gradle-plugin:0.6";
	static String androidPluginImport = "com.android.tools.build:gradle:1.0.0";
	static String roboVMPluginImport = "org.robovm:robovm-gradle-plugin:" + roboVMVersion;
	
	//Extension versions
	static String box2DLightsVersion = "1.3";
	static String ashleyVersion = "1.4.0";
	static String aiVersion = "1.5.0";

	HashMap<ProjectDependency, Dependency> gdxDependencies = new HashMap<ProjectDependency, Dependency>();

	public DependencyBank() {
		for (ProjectDependency projectDep : ProjectDependency.values()) {
			Dependency dependency = new Dependency(projectDep.name(),
					projectDep.getGwtInherits(),
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
			new String[]{"org.mini2Dx:mini2Dx-android:$mini2DxVersion", "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi", "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-armeabi-v7a", "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-x86"},
			new String[]{"org.mini2Dx:mini2Dx-ios:$mini2DxVersion", "com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-ios"},
			null,
			null,
			
			"Core Library for mini2Dx"
		),
		BULLET(
			new String[]{"com.badlogicgames.gdx:gdx-bullet:$gdxVersion"},
			new String[]{"com.badlogicgames.gdx:gdx-bullet-platform:$gdxVersion:natives-desktop"},
			new String[]{"com.badlogicgames.gdx:gdx-bullet:$gdxVersion", "com.badlogicgames.gdx:gdx-bullet-platform:$gdxVersion:natives-armeabi", "com.badlogicgames.gdx:gdx-bullet-platform:$gdxVersion:natives-armeabi-v7a", "com.badlogicgames.gdx:gdx-bullet-platform:$gdxVersion:natives-x86"},
			new String[]{"com.badlogicgames.gdx:gdx-bullet-platform:$gdxVersion:natives-ios"},
			null,
			null,
			
			"3D Collision Detection and Rigid Body Dynamics"
		),
		TOOLS(
			new String[]{},
			new String[]{"com.badlogicgames.gdx:gdx-tools:$gdxVersion"},
			new String[]{},
			new String[]{},
			new String[]{},
			new String[]{},
			
			"Collection of tools, including 2D/3D particle editors, texture packers, and file processors"
		),
		BOX2D(
			new String[]{"com.badlogicgames.gdx:gdx-box2d:$gdxVersion"},
			new String[]{"com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-desktop"},
			new String[]{"com.badlogicgames.gdx:gdx-box2d:$gdxVersion", "com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-armeabi", "com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-armeabi-v7a", "com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-x86"},
			new String[]{"com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-ios"},
			new String[]{"com.badlogicgames.gdx:gdx-box2d:$gdxVersion:sources", "com.badlogicgames.gdx:gdx-box2d-gwt:$gdxVersion:sources"},
			new String[]{"com.badlogic.gdx.physics.box2d.box2d-gwt"},
			
			"2D Physics Library"
		),	
		BOX2DLIGHTS(
			new String[]{"com.badlogicgames.box2dlights:box2dlights:$box2DLightsVersion"},
			new String[]{},
			new String[]{"com.badlogicgames.box2dlights:box2dlights:$box2DLightsVersion"},
			new String[]{},
			new String[]{"com.badlogicgames.box2dlights:box2dlights:$box2DLightsVersion:sources"},
			new String[]{"Box2DLights"},
			
			"2D Lighting framework that utilises Box2D"
		),
		ASHLEY(
			new String[]{"com.badlogicgames.ashley:ashley:$ashleyVersion"},
			new String[]{},
			new String[]{"com.badlogicgames.ashley:ashley:$ashleyVersion"},
			new String[]{},
			new String[]{"com.badlogicgames.ashley:ashley:$ashleyVersion:sources"},
			new String[]{"com.badlogic.ashley_gwt"},
			
			"Lightweight Entity framework"
		),
		AI(
			new String[]{"com.badlogicgames.gdx:gdx-ai:$aiVersion"},
			new String[]{},
			new String[]{"com.badlogicgames.gdx:gdx-ai:$aiVersion"},
			new String[]{},
			new String[]{"com.badlogicgames.gdx:gdx-ai:$aiVersion:sources"},
			new String[]{"com.badlogic.gdx.ai"},
			
			"Artificial Intelligence framework"
		);

		private String[] coreDependencies;
		private String[] desktopDependencies;
		private String[] androidDependencies;
		private String[] iosDependencies;
		private String[] gwtDependencies;
		private String[] gwtInherits;
		private String description;

		ProjectDependency(String[] coreDeps, String[] desktopDeps, String[] androidDeps, String[] iosDeps, String[] gwtDeps, String[] gwtInhertis, String description) {
			this.coreDependencies = coreDeps;
			this.desktopDependencies = desktopDeps;
			this.androidDependencies = androidDeps;
			this.iosDependencies = iosDeps;
			this.gwtDependencies = gwtDeps;
			this.gwtInherits = gwtInhertis;
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
				//				case HTML:
				//					return gwtDependencies;
			}
			return null;
		}
		
		public String[] getGwtInherits() {
			return gwtInherits;
		}
		
		public String getDescription() {
			return description;
		}
	}


	public enum ProjectType {
		CORE("core", new String[]{"java"}),
		DESKTOP("desktop", new String[]{"java"}),
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
