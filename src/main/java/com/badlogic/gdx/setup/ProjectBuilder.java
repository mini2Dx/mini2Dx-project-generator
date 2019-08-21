package com.badlogic.gdx.setup;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.setup.DependencyBank.ProjectType;

public class ProjectBuilder {
	String projectName;
	DependencyBank bank;
	List<ProjectType> modules = new ArrayList<ProjectType>();
	List<Dependency> dependencies = new ArrayList<Dependency>();
	File settingsFile;
	File buildFile;
	Release release;

	public ProjectBuilder(String projectName, DependencyBank bank, Release release) {
		this.projectName = projectName;
		this.bank = bank;
		this.release = release;
	}

	public List<String> buildProject(List<ProjectType> projects, List<Dependency> dependencies) {
		List<String> incompatibilities = new ArrayList<String>();
		for (Dependency dep : dependencies) {
			for (ProjectType type : projects) {
				dep.getDependencies(type);
				incompatibilities.addAll(dep.getIncompatibilities(type));
			}
		}
		this.modules = projects;
		this.dependencies = dependencies;
		return incompatibilities;
	}

	public boolean build() throws IOException {
		settingsFile = File.createTempFile("libgdx-setup-settings", ".gradle");
		buildFile = File.createTempFile("libgdx-setup-build", ".gradle");
		if (!settingsFile.exists()) {
			settingsFile.createNewFile();
		}
		if (!buildFile.exists()) {
			buildFile.createNewFile();
		}
		settingsFile.setWritable(true);
		buildFile.setWritable(true);
		try {
			FileWriter settingsWriter = new FileWriter(settingsFile.getAbsoluteFile());
			BufferedWriter settingsBw = new BufferedWriter(settingsWriter);
			String settingsContents = "include ";
			for (ProjectType module : modules) {
				settingsContents += "'" + module.getName() + "'";
				if (modules.indexOf(module) != modules.size() - 1) {
					settingsContents += ", ";
				}
			}
			settingsBw.write(settingsContents);
			settingsBw.write("\n");
			settingsBw.write("rootProject.name = '" + projectName + "'");
			settingsBw.close();
			settingsWriter.close();

			FileWriter buildWriter = new FileWriter(buildFile.getAbsoluteFile());
			BufferedWriter buildBw = new BufferedWriter(buildWriter);
			if (release.getMini2DxVersion().startsWith("1")) {
				BuildScriptV1Helper.addBuildScript(modules, buildBw, release);
				BuildScriptV1Helper.addAllProjects(buildBw, release);
				for (ProjectType module : modules) {
					BuildScriptV1Helper.addProject(module, dependencies, buildBw);
				}
			} else {
				BuildScriptV2Helper.addBuildScript(modules, buildBw, release);
				BuildScriptV2Helper.addAllProjects(buildBw, release);
				for (ProjectType module : modules) {
					BuildScriptV2Helper.addProject(module, dependencies, buildBw);
				}
			}

			//Add task here for now
			buildBw.write("\n");
			buildBw.write("tasks.eclipse.doLast {\n");
			buildBw.write("    delete \".project\"\n");
			buildBw.write("}");

			buildBw.close();
			buildWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void cleanUp() {
		settingsFile.deleteOnExit();
		buildFile.deleteOnExit();
	}

}
