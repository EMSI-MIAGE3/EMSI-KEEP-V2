package org.skafcommunity.emsikeep.prez.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.skafcommunity.emsikeep.R;
import org.skafcommunity.emsikeep.dao.AdministratorDAO;
import org.skafcommunity.emsikeep.dao.filtersListners.EntityListner;
import org.skafcommunity.emsikeep.models.Administrator;
import org.skafcommunity.emsikeep.prez.adapters.AdministratorAdapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageAdministratorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageAdministratorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ListView listView;
    private AdministratorAdapter aAdapter;
    private Context context;

    private OnFragmentInteractionListener mListener;

    public ManageAdministratorFragment() {
        // Required empty public constructor
        this.context = getContext();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageProfessorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageAdministratorFragment newInstance(String param1, String param2) {
        ManageAdministratorFragment fragment = new ManageAdministratorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        return inflater.inflate(R.layout.fragment_manage_administrator, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getActivity().findViewById(R.id.list_administrator);
        final Context context = this.getContext();
        AdministratorDAO.getAll(new EntityListner<List<Administrator>>() {
            @Override
            public void doStuff(List<Administrator> administrators) {
                aAdapter = new AdministratorAdapter(context, administrators);
                listView.setAdapter(aAdapter);
            }
        });

        Fragment fragment = this;
        FloatingActionButton button = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.user_popup);
                dialog.setTitle(getString(R.string.add_professor));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
                Button cancelBtn = (Button) dialog.findViewById(R.id.cancel_popup);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });
                final Context context = getContext();
                Button valideBtn = (Button) dialog.findViewById(R.id.save_class);
                valideBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText firstnameEdit = (EditText) dialog.findViewById(R.id.firstname_dialog);
                        EditText lastnameEdit = (EditText) dialog.findViewById(R.id.lastname_dialog);
                        EditText phoneEdit = (EditText) dialog.findViewById(R.id.phone_dialog);
                        EditText emailEdit = (EditText) dialog.findViewById(R.id.email_dialog);
                        EditText passwordEdit = (EditText) dialog.findViewById(R.id.password_dialog);
                        String lastname = lastnameEdit.getText().toString();
                        String firstname = firstnameEdit.getText().toString();
                        String phone = phoneEdit.getText().toString();
                        String email = emailEdit.getText().toString();
                        String password = passwordEdit.getText().toString();
                        if(firstname.equals("") || lastname.equals("") || phone.equals("") || email.equals("") || password.equals("")){
                            Toast.makeText(getContext(), getString(R.string.required_fields),(int)6).show();
                        }
                        else {
                            final ProgressDialog dialog2 = new ProgressDialog(getActivity()); // this = YourActivity
                            dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            dialog2.setTitle("Loading");
                            dialog2.setMessage("Loading. Please wait...");
                            dialog2.setIndeterminate(true);
                            dialog2.setCanceledOnTouchOutside(false);
                            dialog2.show();
                            Administrator administratorX = new Administrator(lastname, firstname, email, phone);
                            AdministratorDAO.add(administratorX, password);
                            Toast.makeText(getContext(), getString(R.string.added),(int)6).show();
                            dialog2.dismiss();
                            dialog.dismiss();
                            aAdapter.notifyDataSetChanged();
                        }
                    }
                });

            }
        });
        ((TextView)getActivity().findViewById(R.id.top_bar_title_texte)).setText("GESTION DES ADMINISTRATEURS");
        ViewCompat.setNestedScrollingEnabled(listView, true);

    }
}
