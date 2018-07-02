package ru.production.ssobolevsky.notepadtest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    private EditText mEditTextTitle;
    private EditText mEditTextSubtitle;
    private Button mButtonUpdate;

    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mEditTextTitle = view.findViewById(R.id.et_edit_title);
        mEditTextSubtitle = view.findViewById(R.id.et_edit_subtitle);
        mButtonUpdate = view.findViewById(R.id.btn_update);
        mButtonUpdate.setOnClickListener(new ButtonUpdateOnClickListener());
    }

    public static final Fragment newInstance() {
        Fragment fragment = new EditFragment();
        return fragment;
    }

    private class ButtonUpdateOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            
        }
    }

}
