package com.labo2.sym.symlabo2.GraphQLFragment;

import SymComManager.CommunicationEventListener;
import SymComManager.GraphQLObjectSymComManager;
import SymComManager.Objects.Author;
import SymComManager.Objects.Post;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.labo2.sym.symlabo2.MainFragment;
import com.labo2.sym.symlabo2.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Classe gérant le fragment affiché lorsque l'utilisateur sélectionne "GraphQL Object Transmission" dans le menu ou sur le fragment "Home".
 */
public class GraphQLSendFragment extends MainFragment {
	
	private Spinner listAuthors;
	private RecyclerView allPostByAuthor;
	private final GraphQLObjectSymComManager scm = new GraphQLObjectSymComManager();
	private OnFragmentInteractionListener mListener;
	
	public GraphQLSendFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_graph_ql, container, false);
		
		// on récupère les éléments du fragment
		listAuthors = view.findViewById(R.id.graphqlFragment_authorsSpinner);
		allPostByAuthor = view.findViewById(R.id.graphqlFragment_authorPostsRecyclerView);
		allPostByAuthor.setLayoutManager(new LinearLayoutManager(getContext()));
		allPostByAuthor.setAdapter(new RecyclerViewAdapter());
		
		// on charge la liste des auteurs à afficher dans le spinner
		populateAuthorsSpinner();
		
		// on set l'action à effectuer lorsque l'utilisateur sélectionne un nouvel auteur dans le spinner
		listAuthors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Author author = (Author) listAuthors.getSelectedItem();
				
				// on affiche tous les posts de l'auteur sélectionné
				populateAllPostByAuthor(author);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				allPostByAuthor.removeAllViews();
			}
		});
		
		return view;
	}
	
	/**
	 * Charge la liste de tous les auteurs et les ajoute au spinner.
	 */
	private void populateAuthorsSpinner() {
		try {
			// on défini l'action qui sera effectuée lorsqu'on recevra la réponse à la requête au serveur
			scm.setCommunicationEventListener(new CommunicationEventListener() {
				@Override
				public boolean handleServerResponse(final String response) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								// on transforme la réponse en JSON et on récupère le tableau d'auteurs
								JSONArray allAuthors = new JSONObject(response).getJSONObject("data").getJSONArray("allAuthors");
								ArrayList<Author> authorsList = new ArrayList<>();
								
								// on transforme chaque objet JSON en un Author qu'on ajoute à notre liste d'auteurs.
								for (int i = 0; i < allAuthors.length(); i++) {
									JSONObject o = allAuthors.getJSONObject(i);
									authorsList.add(new Author(o.getInt("id"), o.getString("first_name"), o.getString("last_name")));
								}
								
								// on crée un ArrayAdapter en utilisant le layout par défaut et en le populant avec le contenu de 'authorsList'
								ArrayAdapter<Author> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, authorsList);
								// on spécifie le layout à utiliser pour afficher les choix des éléments du spinner
								adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								// on applique l'ArrayAdapter au spinner
								listAuthors.setAdapter(adapter);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
					return true;
				}
			});
			
			// on envoit la requête graphQL au serveur
			scm.getAllAuthors();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Charge et affiche tous les posts de l'auteur sélectionné passé en paramètre.
	 * @param selectedAuthor, l'auteur sélectionné dans le spinner.
	 */
	private void populateAllPostByAuthor(Author selectedAuthor) {
		try {
			// on set l'action qui sera effectuée lorsqu'on recevra la réponse à la requête au serveur
			scm.setCommunicationEventListener(new CommunicationEventListener() {
				@Override
				public boolean handleServerResponse(final String response) {
					Activity activity = getActivity();
					if (activity != null) {
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								try {
									// on transforme la réponse en JSON  et on récupère le tableau contenant tous les posts de l'auteur
									JSONArray allPosts = new JSONObject(response).getJSONObject("data").getJSONArray("allPostByAuthor");
									ArrayList<Post> authorPostsList = new ArrayList<>();
									
									// on transforme chaque objet JSON en un Post qu'on ajoute à notre liste de posts
									for (int i = 0; i < allPosts.length(); i++) {
										JSONObject o = allPosts.getJSONObject(i);
										authorPostsList.add(new Post(o.getString("title"), o.getString("description"),
												o.getString("content"), o.getString("date")));
									}
									
									// FIXME: si on incline le natel, la vue est reconstruite et donc les appels à l'API refait...! Ya-t-il moyen de faire autrement?
									// --> il faudrait mettre une base de donnée temporaire (cache) pour stoquer les résultats et ne pas refaire la requête
									// -> à mettre dans le rapport
									
									
									// on crée un RecyclerViewAdapter à partir de l'adapter du RecyclerView 'allPostByAuthor'
									RecyclerViewAdapter r = (RecyclerViewAdapter) allPostByAuthor.getAdapter();
									// on ajoute le tableau des posts de l'auteur sélectionné au RecyclerView
									if (r != null) {
										r.setListPosts(authorPostsList);
									} else {
										throw new RuntimeException("A problem occurs with the RecyclerView.");
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						});
						return true;
					} else {
						return false;
					}
				}
			});
			
			// on envoit la requête au serveur
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
