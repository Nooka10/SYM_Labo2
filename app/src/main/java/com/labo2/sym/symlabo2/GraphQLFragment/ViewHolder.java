package com.labo2.sym.symlabo2.GraphQLFragment;

import SymComManager.Objects.Post;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.labo2.sym.symlabo2.R;

/**
 * ViewHolder utilisé pour afficher cahque post de l'auteur sélectionné dans le RecyclerView du fragment GraphQLFrament affiché lorsque l'utilisateur
 * sélectionne "GraphQL Object Transmission" dans le menu ou sur le fragment "Home".
 */
class ViewHolder extends RecyclerView.ViewHolder {
	
	private final TextView titleTextView;
	private final TextView dateTextView;
	private final TextView descriptionTextView;
	private final TextView contentTextView;
	
	/**
	 * Constructeur
	 * @param itemView, la vue à utiliser pour afficher une cellule du RecyclerView.
	 */
	ViewHolder(View itemView) {
		super(itemView);
		
		// on récupère les éléments de la view
		titleTextView = itemView.findViewById(R.id.cardTitle);
		dateTextView = itemView.findViewById(R.id.cardDate);
		descriptionTextView = itemView.findViewById(R.id.cardDescription);
		contentTextView = itemView.findViewById(R.id.cardContent);
	}
	
	/**
	 * Bind les données du Post reçu en paramètre afin de les afficher au bon endroit dans la cellule du RecyclerView.
	 * @param post, le post à afficher.
	 */
	void bind(Post post) {
		titleTextView.setText(post.getTitle());
		dateTextView.setText(dateTextView.getContext().getString(R.string.ViewHolderDateText, post.getDateString()));
		descriptionTextView.setText(post.getDescription());
		contentTextView.setText(post.getContent());
	}
}
