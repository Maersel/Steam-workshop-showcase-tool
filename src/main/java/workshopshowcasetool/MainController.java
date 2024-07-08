package workshopshowcasetool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;


import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainController {

    @FXML
    private Button uploadImgBtn;
    @FXML
    private Label fileNameLabel;
    @FXML
    private ImageView previewImageview;
    @FXML
    private Button startBtn;
    @FXML
    private TextField imageNameTxt;

    Image image;
    File selectedFile;
    BufferedImage bufferedImage;
    int sliceWidth;
    String fileName;
    @FXML
    void onUploadImgClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg"));

        this.selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            fileNameLabel.setText(selectedFile.getName());

            // Load the image and display it in the ImageView
            this.image = new Image(selectedFile.toURI().toString());
            previewImageview.setImage(image);
        } else {
            fileNameLabel.setText("No file selected");
            previewImageview.setImage(null); // Clear the image view if no file is selected
        }
    }


    @FXML
    void onStartClicked(ActionEvent event) throws IOException {
        fileName = imageNameTxt.getText();

        if (fileName.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a file name");
            alert.showAndWait();
            return;
        }

        bufferedImage = ImageIO.read(selectedFile);

        int height = (int) image.getHeight();

        if (selectedFile == null) {
            return;
        }

        //ensures the image width is divisible by 5 so the output will be 5 images of equal width
        while ((bufferedImage.getWidth() % 5) != 0) {
            this.bufferedImage = bufferedImage.getSubimage(0, 0, (bufferedImage.getWidth() - 1), height);
            System.out.println(bufferedImage.getWidth());
            if ((bufferedImage.getWidth() % 5) != 0) {
                this.bufferedImage = this.bufferedImage.getSubimage(1, 0, bufferedImage.getWidth() - 1, height);
                System.out.println(bufferedImage.getWidth());
            }
            if ((bufferedImage.getWidth() % 5) == 0) {
                sliceWidth = bufferedImage.getWidth() / 5;
                break;
            }
        }

        previewImageview.setImage(bufferedImageToImage(bufferedImage));

        //prompt user to select a directory for the sliced image
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select folder to save sliced image");
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            for (int i = 0; i < 5; i++) {
                int x = i * sliceWidth;
                int sliceNumber = i + 1;

                BufferedImage sliceImage = bufferedImage.getSubimage(x, 0, sliceWidth, height);

                //hex editing the slices so the last byte is changed to 0x21



                //save all slices as a separate file in the selected directory
                File outputFile = new File(selectedDirectory, fileName + "_slice_" + sliceNumber + ".png");
                ImageIO.write(sliceImage, "png", outputFile);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("No directory selected. Please select a directory to save the sliced images.");
            alert.showAndWait();
        }
    }

    public static Image bufferedImageToImage(BufferedImage bufferedImage) {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
}

