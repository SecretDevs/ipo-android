package com.roblox.ipo.onboarding.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.roblox.ipo.R
import com.roblox.ipo.navigation.Coordinator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_age.*
import kotlinx.android.synthetic.main.fragment_quiz_age.quiz_next_btn
import kotlinx.android.synthetic.main.fragment_quiz_age.quiz_skip_btn
import kotlinx.android.synthetic.main.fragment_quiz_age.quiz_step
import kotlinx.android.synthetic.main.fragment_quiz_tools.*
import javax.inject.Inject

@AndroidEntryPoint
class QuizAgeFragment : Fragment() {
    @Inject
    lateinit var coordinator: Coordinator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_quiz_age,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quiz_step.text = resources.getString(R.string.text_quiz_step, 1)
        quiz_skip_btn.setOnClickListener {
            coordinator.navigateToDeals()
        }
        quiz_next_btn.setOnClickListener {
            coordinator.navigateToQuizFund()
        }
        quiz_card_input.doOnTextChanged { text, start, before, count ->

        }
    }
}