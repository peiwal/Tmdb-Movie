package petrov.ivan.tmdb.ui.movieInfo

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import petrov.ivan.tmdb.R

class DeleteFromFavoritesDialogFragment(val clickListener: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(getContext())
            .setTitle(R.string.dialog_delete_favorites_title)
            .setPositiveButton(R.string.dialog_delete_button) { _, _ -> clickListener() }
            .setNegativeButton(R.string.dialog_cancel_buttion) { _, _ -> }
            .create()
    }
}
