package com.roblox.ipo.onboarding.quiz.risk

import androidx.fragment.app.viewModels
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_quiz_risk.*

@AndroidEntryPoint
class QuizRiskFragment : BaseFragment<QuizRiskViewState, QuizRiskIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_quiz_risk
    override val viewModel: QuizRiskViewModel by viewModels()

    override fun backStackIntent(): QuizRiskIntent = QuizRiskIntent.QuizRiskNothingIntent

    override fun initialIntent(): QuizRiskIntent = QuizRiskIntent.InitialIntent

    override fun initViews() {
        quiz_step.text = resources.getString(R.string.text_quiz_step, 7)
        quiz_skip_btn.setOnClickListener {
            _intentLiveData.value = QuizRiskIntent.SkipQuizClickIntent
        }
        quiz_next_btn.setOnClickListener {
            _intentLiveData.value = QuizRiskIntent.NextButtonClickIntent
        }
        btn_arrow_back.setOnClickListener {
            _intentLiveData.value = QuizRiskIntent.BackArrowClickIntent
        }

        var isCascade = false
        quiz_card_risk_group_1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && !isCascade) {
                isCascade = true
                quiz_card_risk_group_2.check(-1)
                _intentLiveData.value = QuizRiskIntent.CheckSelectedIntent(checkedId)
            }
            if (isCascade) {
                isCascade = false
            }
        }
        quiz_card_risk_group_2.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && !isCascade) {
                isCascade = true
                quiz_card_risk_group_1.check(-1)
                _intentLiveData.value = QuizRiskIntent.CheckSelectedIntent(checkedId)
            }
            if (isCascade) {
                isCascade = false
            }
        }
    }

    override fun render(viewState: QuizRiskViewState) {
        quiz_next_btn.isEnabled = viewState.selectedId != -1
    }
}