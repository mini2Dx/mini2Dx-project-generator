/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.badlogic.gdx.setup;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import jdk.nashorn.internal.runtime.RewriteException;

/**
 * Retrieves mini2Dx release information from the mini2Dx repository and checks
 * if this tool is compatible with the releases listed.
 */
public class Releases {
	private static final String BASE_MINI2DX_VERSION = "1.0.0-RC1";
	private static final String BASE_LIBGDX_VERSION = "1.6.1";
	private static final String BASE_ROBOVM_VERSION = "1.2.0";
	private static final String BASE_ANDROID_BUILD_TOOLS_VERSION = "20.0.0";
	private static final String BASE_ANDROID_API_VERSION = "20";

	private static final Release BASE_RELEASE = new Release(
			BASE_MINI2DX_VERSION, BASE_LIBGDX_VERSION, BASE_ROBOVM_VERSION,
			BASE_ANDROID_BUILD_TOOLS_VERSION, BASE_ANDROID_API_VERSION);

	private static boolean COMPATIBLE_SETUP_TOOL;
	private static Release[] RELEASES;

	public static void fetchData() {
		Scanner urlScanner = null;
		try {
			urlScanner = new Scanner(
					new URL("https://raw.githubusercontent.com/mini2Dx/mini2Dx/master/RELEASES")
					.openStream(), "UTF-8");
			urlScanner.useDelimiter("\\A");
			String[] contents = urlScanner.next().split("\n");

			InputStream stream = Releases.class.getClassLoader().getResourceAsStream("VERSION");
			String setupToolVersion = new Scanner(stream).useDelimiter("\\A")
					.next();
			System.out.println("Project Generator Version: " + setupToolVersion);
			
			if (setupToolVersion.contains("SNAPSHOT")) {
				COMPATIBLE_SETUP_TOOL = true;
			} else {
				String requiredSetupToolVersion = contents[0].substring(
						contents[0].indexOf(':') + 1).trim();
				COMPATIBLE_SETUP_TOOL = requiredSetupToolVersion
						.equals(setupToolVersion);
				System.out.println("Required Project Generator Version: " + requiredSetupToolVersion);
			}

			RELEASES = new Release[contents.length - 1];
			for (int i = 0; i < RELEASES.length; i++) {
				Release release = new Release(contents[i + 1]);
				System.out.println("Found " + release);
				RELEASES[i] = release;
			}
		} catch (Exception e) {
			e.printStackTrace();

			COMPATIBLE_SETUP_TOOL = true;
			RELEASES = new Release[] { BASE_RELEASE };
		} finally {
			if (urlScanner != null) {
				urlScanner.close();
			}
		}
	}

	public static boolean isCompatibleSetupTool() {
		return COMPATIBLE_SETUP_TOOL;
	}

	public static Release[] getReleases() {
		return RELEASES;
	}
	
	public static Release getLatestRelease() {
		return RELEASES[0];
	}
	
	public static Release getRelease(String mini2DxVersion) {
		for(Release release : RELEASES) {
			if(release.getMini2DxVersion().equals(mini2DxVersion)) {
				return release;
			}
		}
		return null;
	}
}
