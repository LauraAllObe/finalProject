package com.zybooks.finalproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zybooks.finalproject.model.Note;
import com.zybooks.finalproject.repo.MemoRepository;
import com.zybooks.finalproject.viewmodel.NoteListViewModel;

public class NoteDetailsFragment extends Fragment {
    public static final String ARG_NOTE_ID = "note_id";
    private Note mNote;
    private NoteListViewModel mNoteListViewModel;
    private TextView titleTextView;
    private TextView textTextView;
    private EditText titleTextEdit;
    private EditText textTextEdit;

    private Button mDeleteButton;
    private Button mSaveButton;

    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int noteId = 1;

        // Get the band ID from the fragment arguments
        Bundle args = getArguments();
        if (args != null) {
            noteId = args.getInt(ARG_NOTE_ID);
        }
        mNoteListViewModel = new ViewModelProvider(this).get(NoteListViewModel.class);
        // Get the selected band
        if(noteId > 0)
            mNote = mNoteListViewModel.getNote(noteId).getValue();

        if(noteId < 0 || mNote == null)
        {
            mNote = new Note("", "");
            mNoteListViewModel.addNote(mNote);
            noteId = (int)mNote.getId();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_details, container, false);

        if (mNote != null) {
            titleTextView = rootView.findViewById(R.id.note_title_desc);
            titleTextView.setVisibility(View.GONE);
            titleTextEdit = rootView.findViewById(R.id.note_title);
            titleTextEdit.setText(mNote.getTitle());

            textTextView = rootView.findViewById(R.id.note_text_desc);
            textTextEdit = rootView.findViewById(R.id.note_text);
            textTextEdit.setText(mNote.getText());
            textTextView.setVisibility(View.GONE);

            mDeleteButton = rootView.findViewById(R.id.delete_button);
            mDeleteButton.setOnClickListener( view -> {
                mNoteListViewModel.deleteNote(mNote);
                // Replace list with details
                onDetach();
            });

            mSaveButton = rootView.findViewById(R.id.save_button);
            mSaveButton.setOnClickListener( view -> {
                mNote.setTitle(titleTextEdit.getText().toString());
                mNote.setText(textTextEdit.getText().toString());
                mNoteListViewModel.updateNote(mNote);
                // Replace list with details
                onDetach();
            });
        }

        return rootView;
    }
}