package com.labo2.sym.symlabo2;

import SymComManager.AsyncSymComManager;
import SymComManager.CommunicationEventListener;
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
 * Classe gérant le fragment affiché lorsque l'utilisateur sélectionne "Asynchronous Transmission" dans le menu ou sur le fragment "Home".
 */
public class AsyncSendFragment extends MainFragment {
	
	private Button asyncSendButton = null;
	private EditText editTextToSend = null;
	private TextView responseTextView = null;
	private final AsyncSymComManager scm = new AsyncSymComManager();
	private OnFragmentInteractionListener mListener;
	
	public AsyncSendFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_async_send, container, false);
		
		// on récupère les éléments du fragment
		asyncSendButton = view.findViewById(R.id.asyncFragmentSendButton);
		editTextToSend = view.findViewById(R.id.asyncFragmentInputEditText);
		responseTextView = view.findViewById(R.id.asyncFragmentResponseFromServerTextView);
		responseTextView.setMovementMethod(new ScrollingMovementMethod());
		
		// on set l'action qui sera effectuée lorsqu'on recevra la réponse à la requête au serveur
		scm.setCommunicationEventListener(new CommunicationEventListener() {
			@Override
			public boolean handleServerResponse(final String response) {
				Activity activity = getActivity();
				if (activity != null) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							asyncSendButton.setText(R.string.receivedAnswer);
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
		asyncSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					asyncSendButton.setText(R.string.waitingForResponse);
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
