package com.roblox.ipo.data.usecase

import android.content.Context
import androidx.core.content.edit
import com.roblox.ipo.data.remote.IpoApiService
import com.roblox.ipo.vo.inapp.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface QuizUseCase {
    fun saveAge(age: Int)
    fun saveExperience(experience: Int)
    fun saveFund(fund: Int)
    fun saveProfitability(profitability: Int)
    fun saveRisk(risk: Int)
    fun saveTarget(target: Int)
    fun saveTools(tools: List<Int>)
    suspend fun sendQuiz(): Result<Boolean>
    fun clear()
}

class QuizUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val authUseCase: AuthUseCase,
    private val apiService: IpoApiService
) : QuizUseCase {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun saveAge(age: Int) {
        pref.edit {
            putInt(AGE_FIELD, age)
        }
    }

    override fun saveExperience(experience: Int) {
        pref.edit {
            putInt(EXPERIENCE_FIELD, experience)
        }
    }

    override fun saveFund(fund: Int) {
        pref.edit {
            putInt(FUND_FIELD, fund)
        }
    }

    override fun saveProfitability(profitability: Int) {
        pref.edit {
            putInt(PROFITABILITY_FIELD, profitability)
        }
    }

    override fun saveRisk(risk: Int) {
        pref.edit {
            putInt(RISK_FIELD, risk)
        }
    }

    override fun saveTarget(target: Int) {
        pref.edit {
            putInt(TARGET_FIELD, target)
        }
    }

    override fun saveTools(tools: List<Int>) {
        pref.edit {
            putStringSet(TOOLS_FIELD, tools.map { it.toString() }.toSet())
        }
    }

    override suspend fun sendQuiz(): Result<Boolean> {
        val response = apiService.savePoll(
            token = authUseCase.getUserToken(),
            age = pref.getInt(AGE_FIELD, 18),
            capital = fundMapping[pref.getInt(FUND_FIELD, 0)] ?: "",
            experience = experienceMapping[pref.getInt(EXPERIENCE_FIELD, 0)] ?: "",
            goal = targetMapping[pref.getInt(TARGET_FIELD, 0)] ?: "",
            interest = profitabilityMapping[pref.getInt(PROFITABILITY_FIELD, 0)] ?: "",
            risk = riskMapping[pref.getInt(RISK_FIELD, 0)] ?: "",
            tools = pref.getStringSet(TOOLS_FIELD, emptySet())!!
                .map { toolsMapping[it.toInt()]!! }
                .toTypedArray()
        )
        return if (response.isSuccessful && response.body() != null && response.body()?.status == "OK") {
            clear()
            Result.Success(true)
        } else if (response.isSuccessful) {
            Result.Success(false)
        } else {
            Result.Error(Throwable(response.message()))
        }
    }

    override fun clear() {
        pref.edit {
            clear()
        }
    }

    companion object {
        private const val PREF_NAME = "QUIZ_PREF"
        private const val AGE_FIELD = "QUIZ_AGE"
        private const val EXPERIENCE_FIELD = "QUIZ_EXPERIENCE"
        private const val FUND_FIELD = "QUIZ_FUND"
        private const val PROFITABILITY_FIELD = "QUIZ_PROFITABILITY"
        private const val RISK_FIELD = "QUIZ_RISK"
        private const val TARGET_FIELD = "QUIZ_TARGET"
        private const val TOOLS_FIELD = "QUIZ_TOOLS"

        private val experienceMapping = mapOf(
            0 to "Нет",
            1 to "Меньше 1 года",
            2 to "1 – 3 года",
            3 to "Более 3 лет"
        )
        private val fundMapping = mapOf(
            0 to "Менее $10 000",
            1 to "$10 000 – $50 000",
            2 to "$50 000 – $250 000",
            3 to "Более $250 000"
        )
        private val profitabilityMapping = mapOf(
            0 to "15%",
            1 to "25%",
            2 to "50%",
            3 to "100%",
            4 to "Более 100%"
        )
        private val riskMapping = mapOf(
            0 to "Меньше 15%",
            1 to "До 25%",
            2 to "До 50%",
            3 to "До 100%"
        )
        private val targetMapping = mapOf(
            0 to "Сбережение",
            1 to "Основной доход",
            2 to "Дополнительный доход"
        )
        private val toolsMapping = mapOf(
            0 to "Облигации",
            1 to "Акции",
            2 to "Фьючерсы",
            3 to "Опционы",
            4 to "IPO",
            5 to "Pre-IPO",
            6 to "SPAC",
            7 to "Ресурсы"
        )
    }
}