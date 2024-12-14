package com.abreuretto.busaopaulista;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class HelperSharedPreferences {

	
	
		private static final String PREF_FILE = "abreuretto";

		private static Map<Context, HelperSharedPreferences> instances = new HashMap<Context, HelperSharedPreferences>();

		private SharedPreferences settings;
		private SharedPreferences.Editor editor;

		HelperSharedPreferences(Context context) {
			settings = context
					.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
			editor = settings.edit();
		}

		public static HelperSharedPreferences getInstance(Context context) {
			if (!instances.containsKey(context))
				instances.put(context, new HelperSharedPreferences(context));
			return instances.get(context);
		}

		public String getString(String key, String defValue) {
			return settings.getString(key, defValue);
		}

		public HelperSharedPreferences setString(String key, String value) {
			editor.putString(key, value);
			editor.commit();

			return this;
		}

		public int getInt(String key, int defValue) {
			return settings.getInt(key, defValue);
		}

		public HelperSharedPreferences setInt(String key, int value) {
			editor.putInt(key, value);
			editor.commit();

			return this;
		}

		public boolean getBoolean(String key, boolean defValue) {
			return settings.getBoolean(key, defValue);
		}

		public HelperSharedPreferences setBoolean(String key, boolean value) {
			editor.putBoolean(key, value);
			editor.commit();
			return this;
		}
		
		public HelperSharedPreferences RemoveAll() {
			editor.clear();
			editor.commit();
			return this;
		}
		
		public HelperSharedPreferences Excluir(String key, boolean value) {
			editor.remove(key);
			editor.commit();
			return this;
		}
		

	}



/*
appPrefs = new HelperSharedPreferences(getApplicationContext());

// getting the string from prefs
String someString = appPrefs.getSomeString();

// storing the string in prefs
appPrefs.saveSomeString(someString);
*/

