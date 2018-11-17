package com.labo2.sym.symlabo2.GraphQLFragment;

import SymComManager.Objects.Post;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.labo2.sym.symlabo2.R;

import java.util.ArrayList;

/**
 * Adapter utilisé pour gérer la liste des posts d'un auteur dans le fragment GraphQLSendFragment affiché lorsque l'utilisateur sélectionne
 * "GraphQL Object Transmission" dans le menu ou sur le fragment "Home".
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
	
	private ArrayList<Post> listPosts;
	
	/**
	 * Contructeur
	 */
	RecyclerViewAdapter() {
		listPosts = new ArrayList<>();
	}
	
	/**
	 * Affiche les posts contenus dans la liste reçue en paramètre.
	 * @param listPosts, un tableau contenant les Post à afficher.
	 */
	void setListPosts(ArrayList<Post> listPosts) {
		this.listPosts = listPosts;
		// on notifie l'adapter que ses données ont été modifiées afin qu'il mette à jour la RecyclerView et affiche les nouvelles données.
		notifyDataSetChanged();
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_post, viewGroup, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		Post post = listPosts.get(i);
		viewHolder.bind(post);
	}
	
	@Override
	public int getItemCount() {
		return listPosts.size();
	}
}
