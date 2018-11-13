package com.labo2.sym.symlabo2;

import SymComManager.CommunicationEventListener;
import SymComManager.GraphQLObjectSymComManager;
import SymComManager.Objects.Author;
import SymComManager.Objects.Post;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GraphQLFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GraphQLFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphQLFragment extends MainFragment {
	
	private Spinner listAuthors;
	private RecyclerView allPostByAuthor;
	private OnFragmentInteractionListener mListener;
	private GraphQLObjectSymComManager scm = new GraphQLObjectSymComManager();
	
	public GraphQLFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_graph_ql, container, false);
		
		// on récupère les éléments du fragment
		listAuthors = (Spinner) view.findViewById(R.id.graphqlFragment_authorsSpinner);
		allPostByAuthor = (RecyclerView) view.findViewById(R.id.graphqlFragment_authorPostsRecyclerView);
		allPostByAuthor.setLayoutManager(new LinearLayoutManager(getContext()));
		allPostByAuthor.setAdapter(new RecyclerViewAdapter());
		populateAuthorsSpinner();
		
		listAuthors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Author author = (Author) listAuthors.getSelectedItem();
				
				// on affiche les posts de l'auteur sélectionné.
				populateAllPostByAuthor(author);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				allPostByAuthor.removeAllViews();
			}
		});
		
		
		return view;
	}
	
	private void populateAuthorsSpinner() {
		try {
			// On set l'action qui sera effectuée lorsqu'on recevra la réponse à la requête au serveur.
			scm.setCommunicationEventListener(new CommunicationEventListener() {
				@Override
				public boolean handleServerResponse(final String response) {
					
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								// On transforme la réponse en JSON
								JSONArray allAuthors = new JSONObject(response).getJSONObject("data").getJSONArray("allAuthors");
								ArrayList<Author> authorsList = new ArrayList<>();
								
								// On transforme chaque objet JSON en un Author qu'on ajoute à notre liste d'auteurs.
								for (int i = 0; i < allAuthors.length(); i++) {
									JSONObject o = allAuthors.getJSONObject(i);
									authorsList.add(new Author(o.getInt("id"), o.getString("first_name"), o.getString("last_name")));
								}
								
								// On crée un ArrayAdapter en utilisant le layout par défaut et en le populant avec le contenu de 'authorsList'.
								ArrayAdapter<Author> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, authorsList);
								// On spécifie le layout à utiliser pour afficher les choix des éléments du spinner.
								adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								// On applique l'ArrayAdapter au spinner.
								listAuthors.setAdapter(adapter);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
					return true;
				}
			});
			
			// On envoit la requête au serveur
			scm.getAllAuthors();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void populateAllPostByAuthor(Author selectedAuthor) {
		try {
			// On set l'action qui sera effectuée lorsqu'on recevra la réponse à la requête au serveur.
			scm.setCommunicationEventListener(new CommunicationEventListener() {
				@Override
				public boolean handleServerResponse(final String response) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								// On transforme la réponse en JSON
								JSONArray allPosts = new JSONObject(response).getJSONObject("data").getJSONArray("allPostByAuthor");
								ArrayList<Post> authorPostsList = new ArrayList<>();
								
								// On transforme chaque objet JSON en un Post qu'on ajoute à notre liste de posts.
								for (int i = 0; i < allPosts.length(); i++) {
									JSONObject o = allPosts.getJSONObject(i);
									authorPostsList.add(new Post(o.getString("title"), o.getString("description"),
											o.getString("content"), o.getString("date")));
								}
								
								// FIXME: si on incline le natel, la vue est reconstruite et donc les appels à l'API refait...! Ya-t-il moyen de faire autrement?
								// --> il faudrait mettre une base temporaire pour stoquer les résultats et ne pas refaire la requête
								// -> à mettre dans le rapport
								RecyclerViewAdapter r = (RecyclerViewAdapter) allPostByAuthor.getAdapter();
								r.setListPosts(authorPostsList);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
					return true;
				}
			});
			
			// On envoit la requête au serveur
			scm.getAllAuthorsPosts(selectedAuthor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}
}
