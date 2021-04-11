package com.roblox.ipo.onboarding.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roblox.ipo.R
import com.roblox.ipo.data.usecase.QuizUseCase
import com.roblox.ipo.navigation.Coordinator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_fund.*
import javax.inject.Inject

@AndroidEntryPoint
class QuizFundFragment : Fragment() {
    @Inject
    lateinit var coordinator: Coordinator
    @Inject
    lateinit var quizUseCase: QuizUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_quiz_fund,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quiz_step.text = resources.getString(R.string.text_quiz_step, 2)
        btn_arrow_back.setOnClickListener {
            coordinator.pop()
        }
        quiz_next_btn.setOnClickListener {
            quizUseCase.saveFund(idToSavingMapping[quiz_card_fund_group.checkedRadioButtonId] ?: 0)
            coordinator.navigateToQuizExperience()
        }
        quiz_skip_btn.setOnClickListener {
            quizUseCase.clear()
            coordinator.navigateToDeals()
        }
        quiz_card_fund_group.setOnCheckedChangeListener { group, checkedId ->
            quiz_next_btn.isEnabled = checkedId != -1
        }
        quiz_next_btn.isEnabled = quiz_card_fund_group.checkedRadioButtonId != -1
    }

    companion object {
        private val idToSavingMapping = mapOf(
            R.id.quiz_card_fund_1 to 0,
            R.id.quiz_card_fund_2 to 1,
            R.id.quiz_card_fund_3 to 2,
            R.id.quiz_card_fund_4 to 3
        )
    }
}