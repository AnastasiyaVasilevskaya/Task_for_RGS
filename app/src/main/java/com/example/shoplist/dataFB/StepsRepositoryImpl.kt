package com.example.shoplist.dataFB

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.StepItem
import com.example.shoplist.domain.StepsRepository
import com.google.firebase.database.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class StepsRepositoryImpl(private val firebaseRef: DatabaseReference) : StepsRepository {

    override fun getList(): LiveData<List<StepItem>> {
        val resultLiveData = MutableLiveData<List<StepItem>>()
        addDefaultSteps()

        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val stepsList = mutableListOf<StepItem>()
                for (stepSnapshot in snapshot.children) {
                    val step = stepSnapshot.getValue(StepItem::class.java)
                    step?.let {
                        stepsList.add(it)
                    }
                }
                resultLiveData.value = stepsList
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return resultLiveData
    }

    override suspend fun updateItem(item: StepItem) {
        firebaseRef.child(item.id).setValue(item)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Успешное обновление
                } else {
                    // Обработка ошибок при обновлении
                }
            }
    }

    override suspend fun getItem(itemId: Int): StepItem? {
        return suspendCoroutine { continuation ->
            firebaseRef.child(itemId.toString())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val step = snapshot.getValue(StepItem::class.java)
                        continuation.resume(step)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Обработка ошибок при чтении данных из Firebase
                        continuation.resume(null)
                    }
                })
        }
    }

    override suspend fun resetSteps() {
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (stepSnapshot in snapshot.children) {
                    val step = stepSnapshot.getValue(StepItem::class.java)
                    step?.let {
                        val updatedStep = it.copy(enabled = true)
                        firebaseRef.child(it.id).setValue(updatedStep)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок
            }
        })
    }

    fun addDefaultSteps() {
        val defaultStepsList = listOf(
            StepItem("1", "TEST2 Получить от приемщика заказов извещение об инциденте, аварии.", true),
            StepItem(
                "2",
                "Инструктаж  бригады, доведение информации по объекту и характеру.",
                true
            ),
            StepItem(
                "3",
                "Установка автомобиля, обеспечение освещения, и проезда спецтранспорта.",
                true
            ),
            StepItem("4", "Проверка подвала на наличие запаха газа, приборным методом.", true),
            StepItem(
                "5",
                "Доклад в АДС о проделанной работе.\nПовторная проверка на загазованность",
                true
            ),
            StepItem(
                "6",
                "При отсутствии загазованности  обследовать подвала в следующих точках:\n" +
                        "\n– под лестничным маршем;" +
                        "\n– у ввода и выхода теплотрассы;" +
                        "\n– у ввода водопровода;" +
                        "\n– у ввода телефонного кабеля;" +
                        "\n– у ввода электрокабеля;" +
                        "\n– у ввода газопровода;" +
                        "\n– в месте выхода газопровода из гильзы на вводе;" +
                        "\n– в месте пересечения газопроводом стен и перекрытий.", true
            ),
            StepItem(
                "7",
                "Доклад в АДС о проделанной работе.\nПовторная проверка на загазованность",
                true
            ),
            StepItem(
                "8",
                "При обнаружении в подвале баллонов с газом сообщает в ЖЭС и пожарную охрану.",
                true
            ),
            StepItem(
                "9",
                "При обнаружении загазованности подвала здания  более 1%  / 0,4 % вызывает:\n" +
                        "\n– главного инженера;" +
                        "\n– представителя МВД для принятия мер по эвакуации жильцов;" +
                        "\n– представителя электросетей.", true
            ),
            StepItem(
                "10",
                "Доклад в АДС о проделанной работе.\nПовторная проверка на загазованность",
                true
            ),
            StepItem(
                "11",
                "При загазованности подвала выставляет предупредительные знаки, ограждение,  дежурство:",
                true
            ),
            StepItem(
                "12",
                "Проводит осмотр вероятных мест проникновения газа в подвал:\n" +
                        "\n– в квартирах 1 этажа;" +
                        "\n– на газопроводе – вводе, вводном газопроводе, газопроводе;" +
                        "\n– на внутреннем газопроводе;" +
                        "\n– в местах пересечения газопроводов со стенами и;" +
                        "\n– в местах пересечения смежных коммуникаций с фундаментом, стенами здания.",
                true
            ),
            StepItem(
                "13",
                "Доклад в АДС о проделанной работе.\nПовторная проверка на загазованность",
                true
            ),
            StepItem("14", "Периодический контроль (не реже 1 раза в 10 минут)", true),
            StepItem(
                "15",
                "Отключает подачу газа при сохранении концентрации  или ее повышении,",
                true
            ),
            StepItem(
                "16",
                "Доклад в АДС о проделанной работе.\nПовторная проверка на загазованность",
                true
            ),
            StepItem(
                "17",
                "Осуществляет поиск места повреждения:\n" +
                        "\n– подземного газопровода – методом мелкоглубинного бурения по оси газопровода;" +
                        "\n– надземного газопровода – приборным методом или мыльной эмульсией;" +
                        "\n– сооружений на газопроводах – путем их вскрытия и устранения неисправности;" +
                        "\n– ВДГО – приборным методом или мыльной эмульсией, внешним осмотром." +
                        "\n– ИБУ – приборным методом или мыльной эмульсией, внешним осмотром.",
                true
            ),
            StepItem(
                "18",
                "Доклад в АДС о проделанной работе.\nПовторная проверка на загазованность",
                true
            ),
            StepItem("19", "Устранение выявленных утечек газа на внутреннем газопроводе.", true),
            StepItem(
                "20",
                "Доклад в АДС о проделанной работе.\nПовторная проверка на загазованность",
                true
            ),
            StepItem("21", "Оформление документов по вызову.", true)
        )

        var n = 0

        defaultStepsList.forEach { step ->
            val numberedName = "${++n}. ${step.name}"
            val newStep = StepItem(step.id, numberedName, step.enabled)
            firebaseRef.child(step.id).setValue(newStep)
        }
    }
}
