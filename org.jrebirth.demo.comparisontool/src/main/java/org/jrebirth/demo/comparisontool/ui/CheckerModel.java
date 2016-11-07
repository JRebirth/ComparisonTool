package org.jrebirth.demo.comparisontool.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import org.jrebirth.af.api.wave.Wave;
import org.jrebirth.af.api.wave.annotation.OnWave;
import org.jrebirth.af.core.ui.object.DefaultObjectModel;
import org.jrebirth.demo.comparisontool.bean.FileComparison;
import org.jrebirth.demo.comparisontool.bean.PathToCompare;
import org.jrebirth.demo.comparisontool.resources.ComparisonParameters;
import org.jrebirth.demo.comparisontool.service.ComparatorService;
import org.jrebirth.demo.comparisontool.service.ExportCSVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class <strong>SampleModel</strong>.
 *
 * @author
 */
public final class CheckerModel extends DefaultObjectModel<CheckerModel, CheckerView, PathToCompare> {

    /** The class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckerModel.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initModel() {
        LOGGER.debug("Init Sample Model");

        // Initialize default values
        object().sourcePath(new File(ComparisonParameters.sourcePath.get()));
        object().targetPath(new File(ComparisonParameters.targetPath.get()));

        object().missing(false);
        object().newer(false);
        object().upgraded(false);
        object().downgraded(false);

        object().filteredContent(new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initInnerComponents() {
        // Put the code to initialize inner models here (if any)
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void bind() {

        Bindings.bindBidirectional(view().sourceText().textProperty(), object().pSourcePath(), new FileStringConverter());
        Bindings.bindBidirectional(view().targetText().textProperty(), object().pTargetPath(), new FileStringConverter());

        Bindings.bindBidirectional(view().getSame().selectedProperty(), object().pSame());
        Bindings.bindBidirectional(view().getUpdated().selectedProperty(), object().pUpdated());
        Bindings.bindBidirectional(view().getMissing().selectedProperty(), object().pMissing());
        Bindings.bindBidirectional(view().getNewer().selectedProperty(), object().pNewer());
        Bindings.bindBidirectional(view().getUpgraded().selectedProperty(), object().pUpgraded());
        Bindings.bindBidirectional(view().getDowngraded().selectedProperty(), object().pDowngraded());

        Bindings.bindContentBidirectional(view().getTable().getItems(), object().pFilteredContent());

        view().getSame().textProperty().bind(object().pSameCount().asString("Same (%s)"));
        view().getUpdated().textProperty().bind(object().pUpdatedCount().asString("Updated (%s)"));
        view().getMissing().textProperty().bind(object().pMissingCount().asString("Missing (%s)"));
        view().getNewer().textProperty().bind(object().pNewerCount().asString("Newer (%s)"));
        view().getUpgraded().textProperty().bind(object().pUpgradedCount().asString("Upgraded (%s)"));
        view().getDowngraded().textProperty().bind(object().pDowngradedCount().asString("Downgraded (%s)"));

        final BooleanBinding bexpr = new BooleanBinding() {

            @Override
            protected boolean computeValue() {
                return !(object().sourcePath() != null && object().sourcePath().exists() && object().sourcePath().isDirectory()
                        && object().targetPath() != null && object().targetPath().exists() && object().targetPath().isDirectory());
            }
        };

        final ChangeListener<File> cl = new ChangeListener<File>() {

            @Override
            public void changed(ObservableValue<? extends File> observable, File oldValue, File newValue) {
                bexpr.invalidate();
            }
        };

        object().pSourcePath().addListener(cl);
        object().pTargetPath().addListener(cl);

        view().getStartButton().disableProperty().bind(bexpr);
        
        //view().getExportCSV().disableProperty().bind(Bindings.createBooleanBinding(()->object().pFilteredContent().isEmpty()));

        object().pSame().addListener(this::updateFilter);
        object().pDowngraded().addListener(this::updateFilter);
        object().pMissing().addListener(this::updateFilter);
        object().pNewer().addListener(this::updateFilter);
        object().pUpdated().addListener(this::updateFilter);
        object().pUpgraded().addListener(this::updateFilter);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void processWave(final Wave wave) {
        // Process a wave action, you must listen the wave type before
    }

    @OnWave(ComparatorService.COMPARISON_DONE)
    public void displayResult(final List<FileComparison> result, final Wave wave) {

        object().lastResult(result);
        applyFilter();

        view().hideProgress();
    }
    
    @OnWave(ExportCSVService.EXPORT_CSV_DONE)
    public void achieveExport(final boolean result, final Wave wave) {
    	
    	// manage boolean

        view().hideProgress();
    }

    public void updateFilter(final ObservableValue<? extends Boolean> observable, final Boolean oldValue, final Boolean newValue) {
        applyFilter();
    }

    private void applyFilter() {

        if (object().filteredContent() == null) {
            object().filteredContent(new ArrayList<FileComparison>());
        }
        object().pFilteredContent().clear();

        object().pSameCount().set(0);
        object().pUpdatedCount().set(0);
        object().pMissingCount().set(0);
        object().pNewerCount().set(0);
        object().pUpgradedCount().set(0);
        object().pDowngradedCount().set(0);

        if (object().pLastResult() != null) {
            for (final FileComparison fc : object().pLastResult()) {

                if (fc.isMissing() && object().missing() ||
                        fc.isNewer() && object().newer() ||
                        fc.isUpgraded() && object().upgraded() ||
                        fc.isDowngraded() && object().downgraded() ||
                        fc.isSame() && object().same() ||
                        fc.isUpdated() && object().updated() ||
                        !object().updated() && !object().same() && !object().missing() && !object().newer() && !object().upgraded() && !object().downgraded()) {
                    object().pFilteredContent().add(fc);
                }

                if (fc.isSame()) {
                    object().pSameCount().set(object().pSameCount().get() + 1);
                } else if (fc.isUpdated()) {
                    object().pUpdatedCount().set(object().pUpdatedCount().get() + 1);
                } else if (fc.isMissing()) {
                    object().pMissingCount().set(object().pMissingCount().get() + 1);
                } else if (fc.isNewer()) {
                    object().pNewerCount().set(object().pNewerCount().get() + 1);
                } else if (fc.isUpgraded()) {
                    object().pUpgradedCount().set(object().pUpgradedCount().get() + 1);
                } else if (fc.isDowngraded()) {
                    object().pDowngradedCount().set(object().pDowngradedCount().get() + 1);
                }

            }
        }
    }

}
