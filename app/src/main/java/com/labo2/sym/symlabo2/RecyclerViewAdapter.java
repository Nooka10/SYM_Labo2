package com.labo2.sym.symlabo2;

import SymComManager.Objects.Post;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
	
	private ArrayList<Post> listPosts;
	
	public RecyclerViewAdapter() {
		listPosts = new ArrayList<>();
	}
	
	public RecyclerViewAdapter(ArrayList<Post> listPosts) {
		this.listPosts = listPosts;
	}
	
	public void setListPosts(ArrayList<Post> listPosts) {
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
