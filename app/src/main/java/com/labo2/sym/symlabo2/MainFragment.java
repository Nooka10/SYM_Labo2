package com.labo2.sym.symlabo2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Classe gérant le fragment principal affiché au démarrage de l'application (chargé par la MainActivity) ou lorsque l'utilisateur sélectionne
 * "Home" dans le menu.
 */
public class MainFragment extends Fragment {
	
	private OnFragmentInteractionListener mListener;
	
	public MainFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		// on récupère les éléments du fragment
		Button asyncSendButton = view.findViewById(R.id.mainFragment_AsyncButton);
		Button delayedButton = view.findViewById(R.id.mainFragment_DelayedButton);
		Button jsonObjectButton = view.findViewById(R.id.mainFragment_JsonObjectButton);
		Button xmlObjectButton = view.findViewById(R.id.mainFragment_XmlObjectButton);
		Button graphQlObjectButton = view.findViewById(R.id.mainFragment_GraphQLObjectButton);
		Button compressedButton = view.findViewById(R.id.mainFragment_CompressedButton);
		
		// on lie les boutons avec les boutons du drawer associés afin d'ouvrir le fragment correspondant au bouton cliqué
		setButtonOnClickListener(asyncSendButton, 1);
		setButtonOnClickListener(delayedButton, 2);
		setButtonOnClickListener(jsonObjectButton, 3);
		setButtonOnClickListener(xmlObjectButton, 4);
		setButtonOnClickListener(graphQlObjectButton, 5);
		setButtonOnClickListener(compressedButton, 6);
		
		return view;
	}
	
	private void setButtonOnClickListener(Button button, final int idButton) {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity mainActivity = (MainActivity) getActivity();
				if (mainActivity != null) {
					mainActivity.setSelectedNavigationItem(idButton);
				}
			}
		});
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
	
	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		void onFragmentInteraction(Uri uri);
	}
}
