package petrov.ivan.tmdb.ui

import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import petrov.ivan.tmdb.R
import petrov.ivan.tmdb.databinding.FragmentAboutBinding
import petrov.ivan.tmdb.ui.custom_tab.CustomTabActivityHelper

class FragmentAbout : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spanTxt = SpannableStringBuilder(getString(R.string.fragment_about_information))
        val url = getString(R.string.api_url)
        spanTxt.append(url)
        spanTxt.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val customTabsIntent = CustomTabsIntent.Builder()
                        .setDefaultColorSchemeParams(
                            CustomTabColorSchemeParams
                                .Builder()
                                .setToolbarColor(resources.getColor(R.color.primaryColor))
                                .build()
                        ).build()
                    CustomTabActivityHelper.openCustomTab(
                        requireActivity(),
                        customTabsIntent,
                        Uri.parse(url),
                        fallback = null
                    )
                }
                override fun updateDrawState(ds: TextPaint) {
                    ds.linkColor = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                    super.updateDrawState(ds)
                }
            },
            spanTxt.length - url.length,
            spanTxt.length,
            0
        )

        binding.textVeiw.movementMethod = LinkMovementMethod.getInstance()
        binding.textVeiw.setText(spanTxt, TextView.BufferType.SPANNABLE)
    }
}
