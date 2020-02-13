package net.rusnet.rxmoviessearch.favorites.presentation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.rusnet.rxmoviessearch.R;

public class ConfirmationDialogFragment extends DialogFragment {

    private static final String KEY_TITLE = "KEY_TITLE";

    private ConfirmationDialogListener mCallback;

    public interface ConfirmationDialogListener {
        void onPositiveResponse();
    }

    public static ConfirmationDialogFragment newInstance(@NonNull String text) {
        ConfirmationDialogFragment frag = new ConfirmationDialogFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, text);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ConfirmationDialogListener) {
            mCallback = (ConfirmationDialogListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ConfirmationDialogFragment.ConfirmationDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext()); //getContext()?
        builder.setMessage(getArguments().getString(KEY_TITLE));
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCallback.onPositiveResponse();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}