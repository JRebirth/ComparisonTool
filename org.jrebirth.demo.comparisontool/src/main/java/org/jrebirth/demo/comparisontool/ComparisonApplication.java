package org.jrebirth.demo.comparisontool;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.jrebirth.af.api.application.Configuration;
import org.jrebirth.af.api.resource.ResourceItem;
import org.jrebirth.af.api.resource.font.FontItem;
import org.jrebirth.af.api.ui.Model;
import org.jrebirth.af.core.application.DefaultApplication;
import org.jrebirth.demo.comparisontool.resources.Fonts;
import org.jrebirth.demo.comparisontool.resources.Styles;
import org.jrebirth.demo.comparisontool.ui.CheckerModel;

/**
 * The class <strong>ComparisonApplication</strong>.
 *
 * @author
 */
@Configuration(value = "(.*settings|.*jrebirth)", extension = "properties")
public final class ComparisonApplication extends DefaultApplication<StackPane> {

    /**
     * Application launcher.
     *
     * @param args the command line arguments
     */
    public static void main(final String... args) {
        launch(args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Model> firstModelClass() {
        return CheckerModel.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeStage(final Stage stage) {
        stage.setFullScreen(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void customizeScene(final Scene scene) {
        addCSS(scene, Styles.MAIN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<? extends ResourceItem<?, ?, ?>> getResourceToPreload() {
        return Arrays.asList(new FontItem[] {
                Fonts.MAIN,
        });
    }

}
