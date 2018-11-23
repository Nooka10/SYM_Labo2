package com.labo2.sym.symlabo2;

import SymComManager.CommunicationEventListener;
import SymComManager.CompressedSymComManager;
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
 * Classe gérant le fragment affiché lorsque l'utilisateur sélectionne "Compressed Transmission" dans le menu ou sur le fragment "Home".
 */
public class CompressedSendFragment extends MainFragment {
	
	private EditText computerNameEditText = null;
	private EditText computerManufacturerEditText = null;
	private Button compressedSendButton = null;
	private TextView responseTextView = null;
	private final CompressedSymComManager scm = new CompressedSymComManager();
	private OnFragmentInteractionListener mListener;
	
	public CompressedSendFragment() {
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
		View view = inflater.inflate(R.layout.fragment_compressed_send, container, false);
		
		// on récupère les éléments du fragment
		computerNameEditText = view.findViewById(R.id.compressedFragmentComputerNameEditText);
		computerManufacturerEditText = view.findViewById(R.id.compressedFragmentComputerManufacturerEditText);
		compressedSendButton = view.findViewById(R.id.compressedFragmentSendJsonObjectButton);
		responseTextView = view.findViewById(R.id.compressedFragmentResponseFromServerTextView);
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
							compressedSendButton.setText(R.string.receivedAnswer);
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
		compressedSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					compressedSendButton.setText(R.string.waitingForResponse);
					
					String computerObject = scm.createComputerObject(computerNameEditText.getText().toString(),
							computerManufacturerEditText.getText().toString());
					
					// on envoit la requête au serveur
					scm.sendRequest(computerObject, "http://sym.iict.ch/rest/json");
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
