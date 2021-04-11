package com.roblox.ipo.onboarding.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.roblox.ipo.R
import com.roblox.ipo.data.usecase.QuizUseCase
import com.roblox.ipo.navigation.Coordinator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_target.*
import javax.inject.Inject

@AndroidEntryPoint
class QuizTargetFragment : Fragment() {
    @Inject
    lateinit var coordinator: Coordinator

    @Inject
    lateinit var quizUseCase: QuizUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_quiz_target,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quiz_step.text = resources.getString(R.string.text_quiz_step, 5)
        btn_arrow_back.setOnClickListener {
            coordinator.pop()
        }
        quiz_next_btn.setOnClickListener {
            quizUseCase.saveTarget(
                resources.getStringArray(R.array.text_quiz_target_options)
                    .indexOf(quiz_card_input.text.toString())
            )
            coordinator.navigateToQuizProfitability()
        }
        quiz_skip_btn.setOnClickListener {
            quizUseCase.clear()
            coordinator.navigateToDeals()
        }
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown,
            resources.getStringArray(R.array.text_quiz_target_options)
        )
        quiz_card_input.setAdapter(adapter)
        quiz_card_input.doOnTextChanged { text, _, _, _ ->
            quiz_next_btn.isEnabled = quiz_next_btn.text.isNotEmpty()
        }
        quiz_next_btn.isEnabled = quiz_next_btn.isSelected
    }
}