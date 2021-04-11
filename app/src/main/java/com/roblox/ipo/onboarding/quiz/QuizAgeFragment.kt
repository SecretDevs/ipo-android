package com.roblox.ipo.onboarding.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.roblox.ipo.R
import com.roblox.ipo.data.usecase.QuizUseCase
import com.roblox.ipo.navigation.Coordinator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_age.*
import javax.inject.Inject

@AndroidEntryPoint
class QuizAgeFragment : Fragment() {
    @Inject
    lateinit var coordinator: Coordinator

    @Inject
    lateinit var quizUseCase: QuizUseCase

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
            quizUseCase.clear()
            coordinator.navigateToDeals()
        }
        quiz_next_btn.setOnClickListener {
            quizUseCase.saveAge(quiz_card_input.text.toString().toIntOrNull() ?: 0)
            coordinator.navigateToQuizFund()
        }
        quiz_card_input.doOnTextChanged { text, _, _, _ ->
            quiz_next_btn.isEnabled = text.toString().toIntOrNull() ?: 0 >= 18
        }
        quiz_next_btn.isEnabled = quiz_card_input.text.toString().toIntOrNull() ?: 0 >= 18
    }
}