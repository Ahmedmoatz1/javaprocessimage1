### README for ImageProcessorGUI

#### Overview
This Java application, `ImageProcessorGUI`, is a graphical user interface (GUI) for processing images using multithreading. It allows users to load an image, apply four different filters (Grayscale, Invert Colors, Reduce Brightness, and Increase Brightness), and save the processed images. The application supports both single-threaded and multi-threaded processing modes.

---

#### Features
1. **Load Image**:
   - Load an image from your local file system using the "Load Image" button.
   - Supported formats: JPEG, PNG, BMP, etc.

2. **Process Image**:
   - Apply four filters to the loaded image:
     - **Grayscale**: Converts the image to grayscale.
     - **Invert Colors**: Inverts the colors of the image.
     - **Reduce Brightness**: Reduces the brightness of the image.
     - **Increase Brightness**: Increases the brightness of the image.
   - Choose between **Single Thread** or **Multi-Thread** processing modes.

3. **Save Processed Images**:
   - Save the processed images to your local file system using the "Save Image" button.
   - Each filter's output is saved as a separate file.

4. **Performance Metrics**:
   - Displays the time taken to apply each filter and the total processing time.

---

#### How to Use
1. **Run the Application**:
   - Compile and run the `ImageProcessorGUI` class.
   - The GUI window will open.

2. **Load an Image**:
   - Click the "Load Image" button to select an image file from your computer.

3. **Process the Image**:
   - Select either **Single Thread** or **Multi-Thread** mode using the radio buttons.
   - Click the "Process Image" button to apply the filters.

4. **Save the Processed Images**:
   - Click the "Save Image" button to save the processed images.
   - Each filter's output will be saved as a separate file with a suffix indicating the filter type.

---

#### Code Structure
- **Main Class**: `ImageProcessorGUI`
  - Handles the GUI setup and user interactions.
  - Manages image loading, processing, and saving.

- **Filters**:
  - **Grayscale**: Converts the image to grayscale.
  - **Invert Colors**: Inverts the colors of the image.
  - **Reduce Brightness**: Reduces the brightness of the image.
  - **Increase Brightness**: Increases the brightness of the image.

- **Multithreading**:
  - **Single Thread**: Applies all filters sequentially using one thread.
  - **Multi-Thread**: Applies each filter in a separate thread for parallel processing.

---

#### Requirements
- **Java Development Kit (JDK)**: Version 8 or higher.
- **Dependencies**: None. The application uses standard Java libraries (`java.awt`, `javax.swing`, `javax.imageio`).

---

#### How to Compile and Run
1. **Compile the Code**:
   ```bash
   javac ImageProcessorGUI.java
   ```

2. **Run the Application**:
   ```bash
   java ImageProcessorGUI
   ```

---

#### Example Output
- **Output in the GUI**:
  - The original image and the four processed images are displayed in a 2x2 grid.
  - The processing times for each filter and the total time are displayed in the output area.

- **Saved Files**:
  - If the original image is named `image.jpg`, the saved files will be:
    - `image_filter_0.jpg` (Grayscale)
    - `image_filter_1.jpg` (Invert Colors)
    - `image_filter_2.jpg` (Reduce Brightness)
    - `image_filter_3.jpg` (Increase Brightness)

---

#### Notes
- The application is designed for educational purposes to demonstrate multithreading in Java.
- For large images, multi-threaded processing may significantly improve performance.

---

#### License
This project is open-source and available under the MIT License. Feel free to modify and distribute it as needed.


Enjoy using the ImageProcessorGUI! 🚀
