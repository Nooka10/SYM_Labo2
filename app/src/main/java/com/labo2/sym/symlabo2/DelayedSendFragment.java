package com.labo2.sym.symlabo2;

import SymComManager.CommunicationEventListener;
import SymComManager.DelayedSymComManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Classe gérant le fragment affiché lorsque l'utilisateur sélectionne "Delayed Transmission" dans le menu ou sur le fragment "Home".
 */
public class DelayedSendFragment extends MainFragment {
	
	private Button delayedSendButton = null;
	private EditText editTextToSend = null;
	private TextView responseTextView = null;
	private DelayedSymComManager scm;
	private OnFragmentInteractionListener mListener;
	
	public DelayedSendFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_delayed_send, container, false);
		
		// on récupère les éléments du fragment
		delayedSendButton = view.findViewById(R.id.delayedFragmentSendButton);
		editTextToSend = view.findViewById(R.id.delayedFragmentInputEditText);
		responseTextView = view.findViewById(R.id.delayedFragmentResponseFromServerTextView);
		responseTextView.setMovementMethod(new ScrollingMovementMethod());
		
		scm = new DelayedSymComManager(getActivity());
		
		// on set l'action qui sera effectuée lorsqu'on recevra la réponse à la requête au serveur
		scm.setCommunicationEventListener(new CommunicationEventListener() {
			// FIXME: plante si des requêtes sont en attente mais qu'on a changé de fragment lorsqu'on rétablit la connexion internet...!
			@Override
			public boolean handleServerResponse(final String response) {
				Activity activity = getActivity();
				if (activity != null) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							delayedSendButton.setText(R.string.receivedAnswer);
							responseTextView.setText(response);
						}
					});
					return true;
				} else {
					return false;
				}
			}
		});
		
		// on set l'action à effectuer lorsque le bouton est pressé.
		delayedSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					delayedSendButton.setText(R.string.waitingForResponse);
					
					// on envoit la requête au serveur
					scm.sendRequest(editTextToSend.getText().toString(), "http://sym.iict.ch/rest/txt");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return view;
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
