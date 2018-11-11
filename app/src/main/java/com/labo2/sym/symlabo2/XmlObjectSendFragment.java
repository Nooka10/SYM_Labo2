package com.labo2.sym.symlabo2;

import SymComManager.CommunicationEventListener;
import SymComManager.JsonObjectSymComManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link XmlObjectSendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link XmlObjectSendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XmlObjectSendFragment extends MainFragment {
	
	private EditText firstname = null;
	private EditText lastname = null;
	private RadioGroup sex = null;
	private EditText middlename = null;
	private EditText phonenumber = null;
	private Spinner phoneType = null;
	private Button xmlSendButton = null;
	private TextView responseTextView = null;
	
	private OnFragmentInteractionListener mListener;
	
	public XmlObjectSendFragment() {
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
		View view = inflater.inflate(R.layout.fragment_xml_object_send, container, false);
		
		// on récupère les éléments du fragment
		firstname = (EditText) view.findViewById(R.id.xmlObjectFragmentFirstnameEditText);
		lastname = (EditText) view.findViewById(R.id.xmlObjectFragmentLastnameEditText);
		middlename = (EditText) view.findViewById(R.id.xmlObjectFragmentMiddlenameEditText);
		sex = (RadioGroup) view.findViewById(R.id.xmlObjectFragmentSexRadioGroup);
		phonenumber = (EditText) view.findViewById(R.id.xmlObjectFragmentPhoneEditText);
		phoneType = (Spinner) view.findViewById(R.id.xmlObjectFragmentPhoneTypeSpinner);
		xmlSendButton = (Button) view.findViewById(R.id.xmlObjectFragmentSendJsonObjectButton);
		responseTextView = (TextView) view.findViewById(R.id.xmlObjectFragmentResponseFromServerTextView);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.phoneTypeArray, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		phoneType.setAdapter(adapter);
		
		
		// On set l'action à effectuer lorsque le bouton est pressé.
		xmlSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					JsonObjectSymComManager scm = new JsonObjectSymComManager();
					String computerObject = scm.createComputerObject(firstname.getText().toString(),
							lastname.getText().toString());
					
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
