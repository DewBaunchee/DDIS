package by.varyvoda.bankapp.ui.view.service

import by.varyvoda.bankapp.ui.mode.Mode
import javafx.beans.property.SimpleObjectProperty
import org.springframework.stereotype.Service

@Service
class MainViewService {

    val selectedMode = SimpleObjectProperty(Mode.CLIENTS)

    fun selectMode(mode: Mode) {
        selectedMode.value = mode
    }
}