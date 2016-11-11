package org.jrebirth.demo.comparisontool.command;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.jrebirth.af.api.wave.Wave;
import org.jrebirth.af.core.command.single.ui.DefaultUICommand;
import org.jrebirth.af.core.exception.CommandException;
import org.jrebirth.af.core.wave.JRebirthItems;
import org.jrebirth.demo.comparisontool.service.ExportCSVService;

public class DisplayExceptionCommand extends DefaultUICommand {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void perform(final Wave wave) throws CommandException {

        final Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Impossible to write file");

        final StringBuilder sb = new StringBuilder();

        if (wave.containsNotNull(JRebirthItems.waveItem) && wave.get(JRebirthItems.waveItem).containsNotNull(ExportCSVService.EXPORTED_FILE)) {
            sb.append(String.format("The file %s cannot be written", wave.get(JRebirthItems.waveItem).get(ExportCSVService.EXPORTED_FILE).getAbsolutePath()));
            sb.append("\r\n");
            sb.append("\r\n");
        }
        sb.append(wave.get(JRebirthItems.exceptionItem).getMessage());

        alert.setContentText(sb.toString());

        alert.show();
    }

}
