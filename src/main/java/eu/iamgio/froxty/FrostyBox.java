package eu.iamgio.froxty;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This {@link Pane} contains the target of the {@link FrostyEffect} and the blurred background
 * @author Giorgio Garofalo
 */
public class FrostyBox extends Pane {

    FrostyBox(FrostyEffect effect) {
        getStyleClass().add("frosty-box");

        Node node = effect.getTarget();
        node.setVisible(false);
        getChildren().add(node);

        Platform.runLater(() -> {
            // Get screen position and size of the node
            Bounds bounds = localToScreen(getBoundsInLocal());
            Rectangle snapshotArea = new Rectangle(
                    (int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getWidth(), (int) bounds.getHeight()
            );
            try {
                Robot robot = new Robot();
                // Take screenshot of the area without the node
                BufferedImage image = robot.createScreenCapture(snapshotArea);

                // Set screenshot as background of the box
                ImageView imageView = new ImageView(SwingFXUtils.toFXImage(image, null));
                node.setVisible(true);
                getChildren().add(0, new Pane(imageView));
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }
}