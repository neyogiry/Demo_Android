package com.example.evoltime.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.evoltime.HolderMain;
import com.example.evoltime.R;
import com.example.evoltime.modelo.Publicacion;

public class AdapterMain extends ArrayAdapter<Publicacion> {
	private Context context;

	public AdapterMain(Context context) {
		super(context, R.layout.list_publicacion_item,
				new ArrayList<Publicacion>());
		this.context = context;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderMain holder;
		if (convertView == null) {
			holder = new HolderMain();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_publicacion_item, null);

			holder.tvTitulo = (TextView) convertView
					.findViewById(R.id.tvTitulo);

			convertView.setTag(holder);
		}

		holder = (HolderMain) convertView.getTag();
			holder.tvTitulo.setText(getItem(position).getTitulo());

		return convertView;
	}

}
