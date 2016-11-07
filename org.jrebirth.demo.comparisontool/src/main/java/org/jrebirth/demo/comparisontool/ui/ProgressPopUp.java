package org.jrebirth.demo.comparisontool.ui;

import org.jrebirth.af.core.concurrent.JRebirthThread;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressPopUp {

    private final Stage popup;

    private final Label title;
    private final Label message;

    private final ProgressBar pb;

    public ProgressPopUp() {

        this.popup = new Stage();
        this.popup.initStyle(StageStyle.UNDECORATED);
        this.popup.setAlwaysOnTop(true);
        this.popup.setResizable(false);
        this.popup.setHeight(100);
        this.popup.setWidth(250);
        this.popup.initModality(Modality.APPLICATION_MODAL);

        this.title = new Label();
        this.title.setAlignment(Pos.CENTER_LEFT);
        this.title.prefWidthProperty().bind(this.popup.widthProperty().subtract(20));

        this.message = new Label();
        this.message.setAlignment(Pos.CENTER);
        this.message.prefWidthProperty().bind(this.popup.widthProperty().subtract(20));

        this.pb = new ProgressBar();
        this.pb.prefWidthProperty().bind(this.popup.widthProperty().subtract(20));

        this.pb.setProgress(-1F);

        final VBox box = new VBox();
        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(this.title, this.pb, this.message);

        final BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(0));
        pane.setCenter(box);

        final Scene scene = new Scene(pane);
        this.popup.setScene(scene);
    }

    public ProgressBar getProgressBar() {
        return this.pb;
    }

    public void start() {
    	this.popup.initOwner(JRebirthThread.getThread().getApplication().stage());
    	this.popup.setX(this.popup.getOwner().getX() + this.popup.getOwner().getWidth() /2 - this.popup.getWidth()/2);
    	this.popup.setY(this.popup.getOwner().getY() + this.popup.getOwner().getHeight() /2 - this.popup.getHeight()/2);
        this.popup.show();
    }

    public void stop() {
        this.popup.close();
    }

    public StringProperty getProgressTitle() {
        return this.title.textProperty();
    }

    public StringProperty getProgressMessage() {
        return this.message.textProperty();
    }

}
