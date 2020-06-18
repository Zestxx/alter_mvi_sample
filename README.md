# alter_mvi_sample

В проекте реализовано взаимодействие `Fragment/Activity` и `ViewModel` через `State` модель. Все изменения внутри `Fragment/Activity` делаются через эту модель.

Ипспользуется подход с подписками на 2 LiveData внутри ViewModel.
1. Подписка на `eventsQueue`- используется для обработки событийных изменений на экране, которые не требуют сохранения состояния. Отображение ошибок в `Snackbar`, навигация.
2. Подписка на `state`- используется для обработки изменений `State` модели.

В проекте есть несколько базовых классов для удобной работы со `State` внутри `ViewModel`. 
1. `SingleStateViewModel<T>` - используется для экранов которые использую одну `State` модель и все изменения состояния экрана происходят в рамках этой модели. В качестве типа для `SingleStateViewModel<T>` передается `State` модель. Для обновления `state` используются методы `updateState`.

***Пример:***
```kotlin
data class ScreenState(val title: String, val subtitle: String)
```

```kotlin
class ScreenViewModel(private val storage: Storage) : SingleStateViewModel<ScreenState>() {

    override fun getInitialState(): ScreenState {
        return ScreenState(title = "", subtitle = "")//Создаем первичный State экрана
    }

    /*Обновление отдельных полей state*/
    fun loadDataSample() {
        val title = storage.getTitle()
        val subtitle = storage.getSubtitle()

        updateState {
            copy(title = title, subtitle = subtitle)
        }
    }

    /*Обновление всего state*/
    fun additionalLoadDataSample() {
        val title = storage.getTitle()
        val subtitle = storage.getSubtitle()

        val newState = ScreenState(title, subtitle)
        
        updateState(newState)
    }

}
```
2. `MultipleStateViewModel<T>` - Используется для экранов где важно переключение состояний (конечный автомат) и не может быть одновременного отображение загрузки и данных. В качестве переключаемых состояний используется [LoadingState<T>](app/src/main/kotlin/com/iphk/mobile/app/ui/base/LoadingState.kt). В качестве `T` используется тип данных для отображения, или же `State` класс экрана. Работа с этим типом `ViewModel` максимально похожа на работу с  `SingleStateViewModel<T>` за исключением некоторых дополнительных методов используемых для упрощения изменений State. 

***Пример:***
```kotlin
class ScreenViewModel(
    private val userRepository: UserRepository
) : MultipleStateViewModel<User>() {

    fun loadDataSample() {
        userRepository.getUser()
            .schedulersIoToMain()
            .doOnSubscribe { showLoading() } //Переключаемся на состояние загрузки
            .subscribe(
                { user ->
                    showContent(user)//Переключаемся на отображение контента
                },
                {
                    showStub(it.localizedMessage)//Переключаемся на состояние ошибки
                }
            )
            .autoDispose()
    }

}

```
3. `BaseViewModel` - **ViewModel** в которой реализованы базовые методы работы с `eventsQueue` и освобождения Rx подписок. Использовать если первые 2 типа ViewModel не подходят.
