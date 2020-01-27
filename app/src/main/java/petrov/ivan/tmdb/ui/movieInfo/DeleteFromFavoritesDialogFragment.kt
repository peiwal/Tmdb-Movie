package petrov.ivan.tmdb.ui.movieInfo

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import petrov.ivan.tmdb.R

class DeleteFromFavoritesDialogFragment(val clickListener: DialogClickListener): DialogFragment() {

    interface DialogClickListener {
        fun onDeleteClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(getContext())
            .setTitle(R.string.dialog_delete_favorites_title)
            .setPositiveButton(R.string.dialog_delete_button) { dialogInterface, i -> clickListener.onDeleteClick() }
            .setNegativeButton(R.string.dialog_cancel_buttion) { dialogInterface, i ->  }
            .create()
    }
}