/**
 * Copyright 2015 Thomas Cashman
 */
package com.badlogic.gdx.setup;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thomas Cashman
 */
public class Release {
	private static final String MINI2DX_KEY = "mini2Dx";
	private static final String LIBGDX_KEY = "libGDX";
	private static final String ROBOVM_KEY = "roboVM";
	private static final String ANDROID_BUILD_TOOLS_KEY = "androidBuildTools";
	private static final String ANDROID_API_KEY = "androidApi";
	private static final String PARCL_KEY = "parcl";
	
	private Map<String, String> versions;
	
	public Release(String releaseData) {
		versions = new HashMap<String, String>();
		String [] properties = releaseData.split(",");
		for(String property : properties) {
			String [] mapping = property.split(":");
			String key = mapping[0].trim();
			String value = mapping[1].trim();
			versions.put(key, value);
		}
	}
	
	public Release(String mini2DxVersion, String libGDXVersion, 
			String roboVMVersion, String androidBuildToolsVersion, 
			String androidApiVersion, String parclVersion) {
		versions = new HashMap<String, String>();
		versions.put(MINI2DX_KEY, mini2DxVersion);
		versions.put(LIBGDX_KEY, libGDXVersion);
		versions.put(ROBOVM_KEY, roboVMVersion);
		versions.put(ANDROID_BUILD_TOOLS_KEY, androidBuildToolsVersion);
		versions.put(ANDROID_API_KEY, androidApiVersion);
		versions.put(PARCL_KEY, parclVersion);
	}
	
	public String getMini2DxVersion() {
		return versions.get(MINI2DX_KEY);
	}
	
	public String getLibGDXVersion() {
		return versions.get(LIBGDX_KEY);
	}
	
	public String getRoboVMVersion() {
		return versions.get(ROBOVM_KEY);
	}
	
	public String getAndroidBuildToolsVersion() {
		return versions.get(ANDROID_BUILD_TOOLS_KEY);
	}
	
	public void setAndroidBuildToolsVersion(String androidBuildToolsVersion) {
		versions.put(ANDROID_BUILD_TOOLS_KEY, androidBuildToolsVersion);
	}
	
	public String getAndroidApiVersion() {
		return versions.get(ANDROID_API_KEY);
	}
	
	public void setAndroidApiVersion(String androidApiVersion) {
		versions.put(ANDROID_API_KEY, androidApiVersion);
	}
	
	public String getParclVersion() {
		return versions.get(PARCL_KEY);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Release ");
		for(String key : versions.keySet()) {
			builder.append("[" + key + ": " + versions.get(key) + "]");
		}
		return builder.toString();
	}
}
