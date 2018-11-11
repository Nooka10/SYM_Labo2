package com.labo2.sym.symlabo2;

import SymComManager.CommunicationEventListener;
import SymComManager.DelayedSymComManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DelayedSendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DelayedSendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DelayedSendFragment extends MainFragment {
	
	private Button delayedSendButton = null;
	private EditText editTextToSend = null;
	private TextView responseTextView = null;
	private OnFragmentInteractionListener mListener;
	
	public DelayedSendFragment() {
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
		View view = inflater.inflate(R.layout.fragment_delayed_send, container, false);
		
		// on récupère les éléments du fragment
		delayedSendButton = (Button) view.findViewById(R.id.delayedFragmentSendButton);
		editTextToSend = (EditText) view.findViewById(R.id.delayedFragmentInputEditText);
		responseTextView = (TextView) view.findViewById(R.id.delayedFragmentResponseFromServerTextView);
		
		// On set l'action à effectuer lorsque le bouton est pressé.
		delayedSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					DelayedSymComManager scm = new DelayedSymComManager(getActivity());
					
					// On set l'action qui sera effectuée lorsqu'on recevra la réponse à la requête au serveur.
					scm.setCommunicationEventListener(new CommunicationEventListener() {
						@Override
						public boolean handleServerResponse(final String response) {
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									responseTextView.setText(response);
								}
							});
							return true;
						}
					});
					
					// On envoit la requête au serveur
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
