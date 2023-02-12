module TFXSAMPLE {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires tornadofx;
    requires kotlin.stdlib;
    requires java.sql;
    requires kotlin.stdlib.jdk8;
    requires kotlin.reflect;
    requires generex;
    requires kotlinx.coroutines.core.jvm;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.boot;
    requires spring.beans;
    exports by.varyvoda.bankapp to javafx.graphics, tornadofx;
    exports by.varyvoda.bankapp.ui.view to tornadofx;
}