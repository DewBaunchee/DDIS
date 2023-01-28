module TFXSAMPLE {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires tornadofx;
    requires kotlin.stdlib;
    requires java.sql;
    requires kotlin.stdlib.jdk8;
    exports by.varyvoda.bankapp to javafx.graphics, tornadofx;
    exports by.varyvoda.bankapp.ui.view to tornadofx;
}