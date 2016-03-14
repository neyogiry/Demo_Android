package com.example.evoltime.application;

import android.app.Application;
import android.widget.ArrayAdapter;

import com.example.evoltime.modelo.Publicacion;
import com.example.evoltime.utils.AsyncConector;

public class EvolTimeApplication extends Application {
	private final static String URL = "http://feeds.feedburner.com/Evoltime?format=xml";

	public void updatePublicaciones(ArrayAdapter<Publicacion> lvAdapter) {

		AsyncConector conector = new AsyncConector(lvAdapter, URL);
		conector.execute();
	}

}
