package com.labo2.sym.symlabo2;

import SymComManager.Objects.Post;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {
	
	private TextView titleTextView;
	private TextView dateTextView;
	private TextView descriptionTextView;
	private TextView contentTextView;
	
	//itemView est la vue correspondante à 1 cellule
	public ViewHolder(View itemView) {
		super(itemView);
		
		//c'est ici que l'on fait nos findView
		
		titleTextView = (TextView) itemView.findViewById(R.id.cardTitle);
		dateTextView = (TextView) itemView.findViewById(R.id.cardDate);
		descriptionTextView = (TextView) itemView.findViewById(R.id.cardDescription);
		contentTextView = (TextView) itemView.findViewById(R.id.cardContent);
	}
	
	//puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
	public void bind(Post post) {
		titleTextView.setText(post.getTitle());
		dateTextView.setText("Published the " + post.getDateString());
		descriptionTextView.setText(post.getDescription());
		contentTextView.setText(post.getContent());
	}
}
