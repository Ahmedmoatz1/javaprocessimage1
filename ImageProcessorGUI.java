import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageProcessorGUI extends JFrame {
    private final JLabel[] imageLabels;
    private final JButton loadButton, processButton, saveButton;
    private final JRadioButton singleThreadButton, multiThreadButton;
    private final JTextArea outputArea;
    private BufferedImage originalImage;
    private BufferedImage[] processedImages;

    public ImageProcessorGUI() {
        setTitle("Image Processing with Multithreading");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        imageLabels = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            imageLabels[i] = new JLabel();
            imageLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
        }

        loadButton = new JButton("Load Image");
        processButton = new JButton("Process Image");
        saveButton = new JButton("Save Image");

        singleThreadButton = new JRadioButton("Single Thread");
        multiThreadButton = new JRadioButton("Multi-Thread");
        ButtonGroup threadGroup = new ButtonGroup();
        threadGroup.add(singleThreadButton);
        threadGroup.add(multiThreadButton);
        multiThreadButton.setSelected(true);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(processButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(singleThreadButton);
        buttonPanel.add(multiThreadButton);

        JPanel imagePanel = new JPanel(new GridLayout(2, 2));
        for (JLabel label : imageLabels) {
            imagePanel.add(new JScrollPane(label));
        }

        add(buttonPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        loadButton.addActionListener(e -> loadImage());
        processButton.addActionListener(e -> processImage());
        saveButton.addActionListener(e -> saveImage());
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                originalImage = ImageIO.read(file);
                imageLabels[0].setIcon(new ImageIcon(originalImage));
                for (int i = 1; i < 4; i++) {
                    imageLabels[i].setIcon(null);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void processImage() {
        if (originalImage == null) {
            JOptionPane.showMessageDialog(this, "Please load an image first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        processedImages = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            processedImages[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }

        outputArea.setText("");

        long startTime = System.currentTimeMillis();
 
        if (singleThreadButton.isSelected()) {
            processSingleThread();
        } else {
            processMultiThread();
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        outputArea.append("Total processing completed in " + executionTime + " ms\n");
    }

    private void processSingleThread() {
        long totalTime = 0;
    
        for (int i = 0; i < 4; i++) {
            long startTime = System.currentTimeMillis();
    
            
            final int filterIndex = i;
    
            
            Thread thread = new Thread(() -> applyFilter(filterIndex));
            thread.start(); 
            try {
                thread.join(); 
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(this, "Thread execution interrupted!", "Error", JOptionPane.ERROR_MESSAGE);
            }
    
            long endTime = System.currentTimeMillis();
            long filterTime = endTime - startTime;
            totalTime += filterTime;
    
            outputArea.append("Filter " + filterIndex + " applied in " + filterTime + " ms\n");
        }
    
        outputArea.append("Total time for single thread: " + totalTime + " ms\n");
    }
    private void processMultiThread() {
        Thread[] threads = new Thread[4];
        long[] filterTimes = new long[4];

        for (int i = 0; i < 4; i++) {
            int filterIndex = i;
            threads[i] = new Thread(() -> {
                long startTime = System.currentTimeMillis();
                applyFilter(filterIndex);
                long endTime = System.currentTimeMillis();
                filterTimes[filterIndex] = endTime - startTime;

                SwingUtilities.invokeLater(() -> {
                    outputArea.append("Filter " + filterIndex + " applied in " + filterTimes[filterIndex] + " ms\n");
                });
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join(); // Wait for all threads to finish
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(this, "Thread execution interrupted!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        long totalTime = 0;
        for (long time : filterTimes) {
            totalTime += time;
        }

        outputArea.append("Total time for multi-thread: " + totalTime + " ms\n");
    }

    private void applyFilter(int filterIndex) {
        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                Color color = new Color(originalImage.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int newRed = red, newGreen = green, newBlue = blue;

                switch (filterIndex) {
                    case 0 -> { // Grayscale
                        int gray = (int) (red * 0.299 + green * 0.587 + blue * 0.114);
                        newRed = newGreen = newBlue = gray;
                    }
                    case 1 -> { // Invert Colors
                        newRed = 255 - red;
                        newGreen = 255 - green;
                        newBlue = 255 - blue;
                    }
                    case 2 -> { // Reduce Brightness
                        newRed = Math.max(0, red - 50);
                        newGreen = Math.max(0, green - 50);
                        newBlue = Math.max(0, blue - 50);
                    }
                    case 3 -> { // Increase Brightness
                        newRed = Math.min(255, red + 50);
                        newGreen = Math.min(255, green + 50);
                        newBlue = Math.min(255, blue + 50);
                    }
                }

                Color filteredColor = new Color(newRed, newGreen, newBlue);
                processedImages[filterIndex].setRGB(x, y, filteredColor.getRGB());
            }
        }

        SwingUtilities.invokeLater(() -> {
            imageLabels[filterIndex].setIcon(new ImageIcon(processedImages[filterIndex]));
        });
    }

    private void saveImage() {
        if (processedImages == null) {
            JOptionPane.showMessageDialog(this, "No processed image to save!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                for (int i = 0; i < 4; i++) {
                    File outputFile = new File(file.getParent(), file.getName() + "_filter_" + i + ".jpg");
                    ImageIO.write(processedImages[i], "jpg", outputFile);
                }
                JOptionPane.showMessageDialog(this, "Images saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void showGUI() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageProcessorGUI gui = new ImageProcessorGUI();
            gui.showGUI();
        });
    }
}