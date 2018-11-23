package com.labo2.sym.symlabo2;

import SymComManager.CommunicationEventListener;
import SymComManager.Objects.Person;
import SymComManager.XmlObjectSymComManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Objects;

/**
 * Classe gérant le fragment affiché lorsque l'utilisateur sélectionne "XML Object Transmission" dans le menu ou sur le fragment "Home".
 */
public class XmlObjectSendFragment extends MainFragment {
	
	private EditText firstname = null;
	private EditText lastname = null;
	private RadioButton isMale= null;
	private EditText middlename = null;
	private EditText phonenumber = null;
	private Spinner phoneType = null;
	private Button xmlSendButton = null;
	private TextView responseTextView = null;
	private final XmlObjectSymComManager scm = new XmlObjectSymComManager();
	private OnFragmentInteractionListener mListener;
	
	public XmlObjectSendFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_xml_object_send, container, false);
		
		// on récupère les éléments du fragment
		firstname = view.findViewById(R.id.xmlObjectFragmentFirstnameEditText);
		lastname = view.findViewById(R.id.xmlObjectFragmentLastnameEditText);
		middlename = view.findViewById(R.id.xmlObjectFragmentMiddlenameEditText);
		isMale = view.findViewById(R.id.isMale);
		phonenumber = view.findViewById(R.id.xmlObjectFragmentPhoneEditText);
		phoneType = view.findViewById(R.id.xmlObjectFragmentPhoneTypeSpinner);
		xmlSendButton = view.findViewById(R.id.xmlObjectFragmentSendJsonObjectButton);
		responseTextView = view.findViewById(R.id.xmlObjectFragmentResponseFromServerTextView);
		responseTextView.setMovementMethod(new ScrollingMovementMethod());
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.phoneTypeArray,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		phoneType.setAdapter(adapter);
		
		// on set l'action qui sera effectuée lorsqu'on recevra la réponse à la requête au serveur
		scm.setCommunicationEventListener(new CommunicationEventListener() {
			@Override
			public boolean handleServerResponse(final String response) {
				Activity activity = getActivity();
				if (activity != null) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							responseTextView.setText(response);
						}
					});
					return true;
				} else {
					return false;
				}
			}
		});
		
		// on set l'action à effectuer lorsque le bouton est pressé
		xmlSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					xmlSendButton.setText(R.string.xmlObjectFragment_ResponseContentTextView);
					
					Person person = new Person(firstname.getText().toString(), lastname.getText().toString(),
							isMale.isSelected(), middlename.getText().toString(), phonenumber.getText().toString(),
							phoneType.getSelectedItem().toString());
					// on envoit la requête au serveur
 					scm.sendRequest(person, "http://sym.iict.ch/rest/xml");
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
