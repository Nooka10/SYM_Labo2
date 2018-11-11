package com.labo2.sym.symlabo2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
	
	private Button asyncSendButton = null;
	private Button delayedButton = null;
	private Button objectButton = null;
	private Button compressedButton = null;
	
	private OnFragmentInteractionListener mListener;
	
	public MainFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment MainFragment.
	 */
	public static MainFragment newInstance(NavigationView navView) {
		MainFragment fragment = new MainFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		asyncSendButton = (Button) view.findViewById(R.id.mainFragment_AsyncButton);
		delayedButton = (Button) view.findViewById(R.id.mainFragment_DelayedButton);
		objectButton = (Button) view.findViewById(R.id.mainFragment_ObjectButton);
		compressedButton = (Button) view.findViewById(R.id.mainFragment_CompressedButton);
		
		setButtonOnClickListener(asyncSendButton, 1);
		setButtonOnClickListener(delayedButton, 2);
		setButtonOnClickListener(objectButton, 3);
		setButtonOnClickListener(compressedButton, 4);
		
		return view;
	}
	
	private void setButtonOnClickListener(Button button, final int idButton) {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((MainActivity)getActivity()).setSelectedNavigationItem(idButton);
			}
		});
	}
	
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
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
